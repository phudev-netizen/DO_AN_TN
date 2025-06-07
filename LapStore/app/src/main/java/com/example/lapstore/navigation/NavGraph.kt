import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lapstore.viewmodels.KhachHangViewModel
import com.example.lapstore.viewmodels.TaiKhoanViewModel
import com.example.lapstore.views.AcccountScreen
import com.example.lapstore.views.AccessoryScreen
import com.example.lapstore.views.AddDiaChiScreen
import com.example.lapstore.views.AddressManagementScreen
import com.example.lapstore.views.AdminScreen
import com.example.lapstore.views.CartManagementSection
import com.example.lapstore.views.LoginScreen
import com.example.lapstore.views.ProductDetail_AccessoryScreen
import com.example.lapstore.views.ProductDetail_Screen
import com.example.lapstore.views.RegisterScreen
import com.example.lapstore.views.UpdateDiaChiScreen

sealed class NavRoute(val route: String) {
    object HOME : NavRoute("home_screen")
    object ACCOUNT : NavRoute("account_screen")
    object CART : NavRoute("cart_screen")
    object PRODUCTDETAILSCREEN : NavRoute("productdetail_screen")
    object LOGINSCREEN : NavRoute("login_screen")
    object PAYSCREEN : NavRoute("pay_screen")
    object PAYSUCCESS : NavRoute("paysuccess_screen")
    object QUANLYDONHANG : NavRoute("quanlydonhang_screen")
    object DIACHISCREEN : NavRoute("diachi_screen")
    object ADDDIACHI : NavRoute("adddiachi_screen")
    object UPDATEDIACHI : NavRoute("updatediachi_screen")
    object SEARCHSCREEN : NavRoute("searchscreen_screen")
    object HOADONDETAILSCREEN : NavRoute("hoadondetail_screen")
    object ADMINSCREEN : NavRoute("admin_screen")
    object REGISTERSCREEN: NavRoute("register_screen")
    object ADDRESS_SELECTION: NavRoute("address_selection")
    object ACCESSORY: NavRoute("accessory_screen")
    object PRODUCTDETAIL_ACCESSORY  : NavRoute("productdetail_accesory")
    object FAVORITE : NavRoute("favorite_screen")
}


@Composable
fun NavgationGraph(
    navController: NavHostController,
    viewmodel: SanPhamViewModel,
    hinhAnhViewModel: HinhAnhViewModel,
    khachHangViewModel: KhachHangViewModel,
    taiKhoanViewModel: TaiKhoanViewModel
) {
    NavHost(navController = navController, startDestination = NavRoute.HOME.route) {
        // HomeScreen không có tham số (Chưa đăng nhập)
        composable(NavRoute.HOME.route) {
            HomeScreen(navController,viewmodel, null)  // Không có thông tin tài khoản
        }

        // HomeScreen đã đăng nhập (có tham số tentaikhoan)
        composable(
            route = NavRoute.HOME.route + "?tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("tentaikhoan") { type = NavType.StringType }
            )
        ) {
            val tentaikhoan = it.arguments?.getString("tentaikhoan")
            HomeScreen(navController,viewmodel, tentaikhoan)  // Hiển thị tên tài khoản khi đăng nhập
        }

// Trang phụ kiện - chưa đăng nhập
        composable(NavRoute.ACCESSORY.route) {
            AccessoryScreen(
                navController = navController,
                viewModel = viewmodel,
                tentaikhoan = null
            )
        }
        // Trang phụ kiện - đã đăng nhập
        composable(
            route = NavRoute.ACCESSORY.route + "?tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("tentaikhoan") { type = NavType.StringType }
            )
        ) {
            val tentaikhoan = it.arguments?.getString("tentaikhoan")
            AccessoryScreen(
                navController = navController,
                viewModel = viewmodel,
                tentaikhoan = tentaikhoan
            )
        }

        // Màn hình tài khoản
        composable(
            route = "${NavRoute.ACCOUNT.route}?tentaikhoan={tentaikhoan}",
            arguments = listOf(navArgument("tentaikhoan") { type = NavType.StringType })
        ) { backStackEntry ->
            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan") ?: ""
            AcccountScreen(navController,tentaikhoan)
        }

        // Màn hình giỏ hàng
        composable(
            route = "${NavRoute.CART.route}?makhachhang={makhachhang}&tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("makhachhang") { type = NavType.StringType },
                navArgument("tentaikhoan") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val makhachhang = backStackEntry.arguments?.getString("makhachhang") ?: ""
            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan") ?: ""
            CartScreen(navController,makhachhang,tentaikhoan)
        }

        composable(NavRoute.LOGINSCREEN.route) {
            LoginScreen(navController,taiKhoanViewModel)
        }

        //màn hình chi tiết trang chủ
        composable(
            route = NavRoute.PRODUCTDETAILSCREEN.route + "?id={id}&makhachhang={makhachhang}",
            arguments = listOf(
                navArgument("id") { nullable = true },
                navArgument("makhachhang") { nullable = true },
            )
        ) {
            val id = it.arguments?.getString("id")
            val makhachhang = it.arguments?.getString("makhachhang")

            if (id != null) {
                ProductDetail_Screen(
                    navController = navController,
                    id = id,
                    makhachhang = makhachhang,
                    tentaikhoan = null,
                    viewModel = viewmodel,
                    hinhAnhViewModel = hinhAnhViewModel
                )
            }
        }

        composable(
            route = NavRoute.PRODUCTDETAILSCREEN.route + "?id={id}&makhachhang={makhachhang}&tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("id") { nullable = true },
                navArgument("makhachhang") { nullable = true },
                navArgument("tentaikhoan") { type = NavType.StringType }
            )
        ) {
            val id = it.arguments?.getString("id")
            val makhachhang = it.arguments?.getString("makhachhang")
            val tentaikhoan = it.arguments?.getString("tentaikhoan")

            if (id != null) {
                ProductDetail_Screen(
                    navController = navController,
                    id = id,
                    makhachhang = makhachhang,
                    tentaikhoan = tentaikhoan.toString(),
                    viewModel = viewmodel,
                    hinhAnhViewModel = hinhAnhViewModel
                )
            }
        }
        // Màn hình chi tiết phụ kiện (accessory)
        composable(
            route = NavRoute.PRODUCTDETAIL_ACCESSORY.route + "?id={id}&makhachhang={makhachhang}",
            arguments = listOf(
                navArgument("id") { nullable = true },
                navArgument("makhachhang") { nullable = true },
            )
        ) {
            val id = it.arguments?.getString("id")
            val makhachhang = it.arguments?.getString("makhachhang")

            if (id != null) {
                ProductDetail_AccessoryScreen(
                    navController = navController,
                    id = id,
                    makhachhang = makhachhang,
                    tentaikhoan = null,
                    viewModel = viewmodel,
                    hinhAnhViewModel = hinhAnhViewModel
                )
            }
        }

        composable(
            route = NavRoute.PRODUCTDETAIL_ACCESSORY.route + "?id={id}&makhachhang={makhachhang}&tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("id") { nullable = true },
                navArgument("makhachhang") { nullable = true },
                navArgument("tentaikhoan") { type = NavType.StringType }
            )
        ) {
            val id = it.arguments?.getString("id")
            val makhachhang = it.arguments?.getString("makhachhang")
            val tentaikhoan = it.arguments?.getString("tentaikhoan")

            if (id != null) {
                ProductDetail_AccessoryScreen(
                    navController = navController,
                    id = id,
                    makhachhang = makhachhang,
                    tentaikhoan = tentaikhoan,
                    viewModel = viewmodel,
                    hinhAnhViewModel = hinhAnhViewModel
                )
            }
        }


        //màn hình thanh toán
        composable(
            route = NavRoute.PAYSCREEN.route + "?selectedProducts={selectedProducts}&tongtien={tongtien}&tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("selectedProducts") { type = NavType.StringType },
                navArgument("tongtien") { nullable = true },
                navArgument("tentaikhoan") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Lấy chuỗi selectedProducts từ tham số điều hướng
            val selectedProductsString = backStackEntry.arguments?.getString("selectedProducts")

            // Gọi hàm parseSelectedProducts để chuyển chuỗi thành danh sách Triple<Int, Int, Int>
            val selectedProducts = selectedProductsString?.let { parseSelectedProducts(it) } ?: emptyList()

            // Chuyển đổi tongtien từ String sang Int, nếu không có giá trị thì mặc định là 0
            val tongtien = backStackEntry.arguments?.getString("tongtien")?.toIntOrNull() ?: 0
            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan") ?: ""

            // Chuyển sang màn hình PayScreen với các tham số cần thiết
            PayScreen(navController = navController, selectedProducts = selectedProducts, tongtien = tongtien,tentaikhoan)
        }

        composable(
            route = "${NavRoute.PAYSUCCESS.route}?tentaikhoan={tentaikhoan}",
            arguments = listOf(navArgument("tentaikhoan") { type = NavType.StringType })
        ) { backStackEntry ->
            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan") ?: ""
            PaySuccess_Screen(navController,tentaikhoan)
        }

        composable(
            route = "${NavRoute.QUANLYDONHANG.route}?makhachhang={makhachhang}",
            arguments = listOf(navArgument("makhachhang") { type = NavType.IntType }) // Thay đổi StringType thành IntType
        ) { backStackEntry ->
            val makhachhang = backStackEntry.arguments?.getInt("makhachhang") ?: 0 // Lấy makhachhang dưới dạng Int
            CartManagementSection(navController,makhachhang)
        }


        composable(
            route = "${NavRoute.DIACHISCREEN.route}?makhachhang={makhachhang}",
            arguments = listOf(navArgument("makhachhang") { type = NavType.IntType })
        ) { backStackEntry ->
            val makhachhang = backStackEntry.arguments?.getInt("makhachhang") ?: 0
            AddressManagementScreen(navController,makhachhang)
        }

        composable(
            route = "${NavRoute.ADDDIACHI.route}?makhachhang={makhachhang}",
            arguments = listOf(navArgument("makhachhang") { type = NavType.IntType })
        ) { backStackEntry ->
            val makhachhang = backStackEntry.arguments?.getInt("makhachhang") ?: 0
            AddDiaChiScreen(navController, makhachhang)
        }

        composable(
            route = "${NavRoute.UPDATEDIACHI.route}?makhachhang={makhachhang}&madiachi={madiachi}",
            arguments = listOf(
                navArgument("makhachhang") { type = NavType.IntType },
                navArgument("madiachi") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val makhachhang = backStackEntry.arguments?.getInt("makhachhang") ?: 0
            val madiachi = backStackEntry.arguments?.getInt("madiachi") ?: 0
            UpdateDiaChiScreen(navController,makhachhang,madiachi)
        }

        composable(
            route = NavRoute.SEARCHSCREEN.route + "?makhachhang={makhachhang}&tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("makhachhang") { nullable = true },
                navArgument("tentaikhoan") { type = NavType.StringType }
            )
        ) {
            val tentaikhoan = it.arguments?.getString("tentaikhoan")?:null
            val makhachhang = it.arguments?.getString("makhachhang")?:null
            SearchScreen(navController,makhachhang, tentaikhoan)
        }

        composable(NavRoute.SEARCHSCREEN.route) {
            SearchScreen(navController,null,null)
        }

        composable(
            route = "${NavRoute.HOADONDETAILSCREEN.route}?madonhang={madonhang}&tongtien={tongtien}",
            arguments = listOf(
                navArgument("madonhang") { type = NavType.IntType },
                navArgument("tongtien") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val madonhang = backStackEntry.arguments?.getInt("madonhang") ?: 0
            val tongtien = backStackEntry.arguments?.getInt("tongtien") ?: 0
            DonHangDetailScreen(navController, madonhang,tongtien)
        }

        composable(NavRoute.ADMINSCREEN.route) {
            AdminScreen(navController)
        }

        composable(NavRoute.REGISTERSCREEN.route) { // Khởi tạo rõ ràng ViewModel
            RegisterScreen(
                navController = navController,
                taiKhoanViewModel = taiKhoanViewModel,
                khachHangViewModel
            )
        }
        //yeu thích
//        // Thêm vào NavHost trong NavgationGraph
//        composable(
//            route = NavRoute.FAVORITE.route + "?makhachhang={makhachhang}",
//            arguments = listOf(navArgument("makhachhang") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val makhachhang = backStackEntry.arguments?.getInt("makhachhang") ?: 0
//            FavoriteScreen(
//                customerId = makhachhang,
//                navController = navController,
//                sanPhamViewModel = viewmodel,
//            )
//        }
    }
}




