import android.net.Uri
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lapstore.models.ThongKeScreen
import com.example.lapstore.viewmodels.KhachHangViewModel
import com.example.lapstore.viewmodels.KhuyenMaiViewModel
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
    object ACCESSORY: NavRoute("accessory_screen")
    object PRODUCTDETAIL_ACCESSORY  : NavRoute("productdetail_accesory")
    object FAVORITE : NavRoute("favorite_screen")
    object ADDRESS_SELECTION: NavRoute("address_selection_screen")
    object THONGKE : NavRoute("thongke_screen")
    object KHUYENMAI : NavRoute("khuyenmai_screen")
    object  ADMIN_KHUYENMAI : NavRoute("admin_khuyenmai_screen")
    object PRODUCT_MANAGEMENT : NavRoute("product_management_screen")
    object UPDATEPRODUCT : NavRoute("update_product_screen")


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
        composable(HOME.route) {
            HomeScreen(
                navController, viewmodel, null,
                role = null.toString()
            )  // Không có thông tin tài khoản
        }

        // HomeScreen đã đăng nhập (có tham số tentaikhoan)
        composable(
            route = HOME.route + "?tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("tentaikhoan") { type = NavType.StringType }
            )
        ) {
            val tentaikhoan = it.arguments?.getString("tentaikhoan")
            HomeScreen(
                navController, viewmodel, tentaikhoan,
                role = taiKhoanViewModel.getRole(tentaikhoan ?: "") ?: "user"
            )  // Hiển thị tên tài khoản khi đăng nhập
        }
        composable(PRODUCT_MANAGEMENT.route) {
            ProductManagementScreen(
                viewModel = viewmodel,
                navController = navController
            )
        }
//        composable(NavRoute.UPDATEPRODUCT.route) {
//            UpdateProductScreen(viewModel = viewmodel)
//        }

        // Trang phụ kiện - chưa đăng nhập
        composable(ACCESSORY.route) {
            AccessoryScreen(
                navController = navController,
                viewModel = viewmodel,
                tentaikhoan = null
            )
        }
        // Trang phụ kiện - đã đăng nhập
        composable(
            route = ACCESSORY.route + "?tentaikhoan={tentaikhoan}",
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
            route = "${ACCOUNT.route}?tentaikhoan={tentaikhoan}",
            arguments = listOf(navArgument("tentaikhoan") { type = NavType.StringType })
        ) { backStackEntry ->
            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan") ?: ""
            AcccountScreen(navController, tentaikhoan)
        }

        // Màn hình giỏ hàng
        composable(
            route = "${CART.route}?makhachhang={makhachhang}&tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("makhachhang") { type = NavType.StringType },
                navArgument("tentaikhoan") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val makhachhang = backStackEntry.arguments?.getString("makhachhang") ?: ""
            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan") ?: ""
            CartScreen(navController, makhachhang, tentaikhoan)
        }

        composable(LOGINSCREEN.route) {
            LoginScreen(navController, taiKhoanViewModel)
        }

//        //màn hình chi tiết trang chủ

        composable(
            route = PRODUCTDETAILSCREEN.route +
                    "?id={id}&makhachhang={makhachhang}&tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType; nullable = true },
                navArgument("makhachhang") { type = NavType.StringType; nullable = true },
                navArgument("tentaikhoan") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val makhachhang = backStackEntry.arguments?.getString("makhachhang")
            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan")

            if (id != null) {
                ProductDetail_Screen(
                    navController = navController,
                    id = id,
                    makhachhang = makhachhang,
                    tentaikhoan = tentaikhoan,
                    viewModel = viewmodel,
                    hinhAnhViewModel = hinhAnhViewModel,
                    hoaDonBanList = khachHangViewModel.hoaDonBanList ?: emptyList(),
                    chiTietHoaDonBanList = khachHangViewModel.chiTietHoaDonBanList ?: emptyList()
                )
            }
        }
        // Màn hình chi tiết phụ kiện (accessory)
        composable(
            route = PRODUCTDETAIL_ACCESSORY.route + "?id={id}&makhachhang={makhachhang}",
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
            route = PRODUCTDETAIL_ACCESSORY.route + "?id={id}&makhachhang={makhachhang}&tentaikhoan={tentaikhoan}",
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


//      //// màn hình thanh toán
        composable(
            route = PAYSCREEN.route +
                    "?selectedProducts={selectedProducts}" +
                    "&tongtien={tongtien}" +
                    "&tentaikhoan={tentaikhoan}" +
                    "&makhachhang={makhachhang}" +
                    "&hinhAnhHienTai={hinhAnhHienTai}" +   // Đúng tên này!
                    "&tensanpham={tensanpham}",
            arguments = listOf(
                navArgument("selectedProducts") { type = NavType.StringType },
                navArgument("tongtien") { type = NavType.StringType; nullable = true },
                navArgument("tentaikhoan") { type = NavType.StringType },
                navArgument("makhachhang") { type = NavType.StringType; nullable = true },
                navArgument("hinhAnhHienTai") { type = NavType.StringType; nullable = true },
                navArgument("tensanpham") { type = NavType.StringType; nullable = true }

            )
        ) { backStackEntry ->
            val selectedProductsString = backStackEntry.arguments?.getString("selectedProducts")
            val selectedProducts =
                selectedProductsString?.let { parseSelectedProducts(it) } ?: emptyList()

            val tongtien = backStackEntry.arguments?.getString("tongtien")?.toIntOrNull() ?: 0
            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan") ?: ""
            val makhachhang = backStackEntry.arguments?.getString("makhachhang") ?: ""
            val hinhAnhHienTai =
                backStackEntry.arguments?.getString("hinhAnhHienTai")?.let { Uri.decode(it) } ?: ""
            val tensanpham =
                backStackEntry.arguments?.getString("tensanpham")?.let { Uri.decode(it) } ?: ""

            PayScreen(
                navController = navController,
                selectedProducts = selectedProducts,
                tongtien = tongtien,
                tentaikhoan = tentaikhoan,
                hinhanh = hinhAnhHienTai,
                tensanpham = tensanpham // bạn có thể truyền nếu cần, hoặc bỏ luôn nếu không dùng
            )
        }

//màn hình thanh tóan thành công
        composable(
            route = "${PAYSUCCESS.route}?tentaikhoan={tentaikhoan}",
            arguments = listOf(navArgument("tentaikhoan") { type = NavType.StringType })
        ) { backStackEntry ->
            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan") ?: ""
            PaySuccess_Screen(navController, tentaikhoan)
        }

        composable(
            route = "${QUANLYDONHANG.route}?makhachhang={makhachhang}",
            arguments = listOf(navArgument("makhachhang") {
                type = NavType.IntType
            }) // Thay đổi StringType thành IntType
        ) { backStackEntry ->
            val makhachhang = backStackEntry.arguments?.getInt("makhachhang")
                ?: 0 // Lấy makhachhang dưới dạng Int
            CartManagementSection(navController, makhachhang)
        }

// màn hình địa chỉ
        composable(
            route = "${DIACHISCREEN.route}?makhachhang={makhachhang}",
            arguments = listOf(navArgument("makhachhang") { type = NavType.IntType })
        ) { backStackEntry ->
            val makhachhang = backStackEntry.arguments?.getInt("makhachhang") ?: 0
            AddressManagementScreen(navController, makhachhang)
        }

        composable(
            route = "${ADDDIACHI.route}?makhachhang={makhachhang}",
            arguments = listOf(navArgument("makhachhang") { type = NavType.IntType })
        ) { backStackEntry ->
            val makhachhang = backStackEntry.arguments?.getInt("makhachhang") ?: 0
            AddDiaChiScreen(navController, makhachhang)
        }

        composable(
            route = "${UPDATEDIACHI.route}?makhachhang={makhachhang}&madiachi={madiachi}",
            arguments = listOf(
                navArgument("makhachhang") { type = NavType.IntType },
                navArgument("madiachi") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val makhachhang = backStackEntry.arguments?.getInt("makhachhang") ?: 0
            val madiachi = backStackEntry.arguments?.getInt("madiachi") ?: 0
            UpdateDiaChiScreen(navController, makhachhang, madiachi)
        }
        composable(
            route = ADDRESS_SELECTION.route + "?makhachhang={makhachhang}",
            arguments = listOf(navArgument("makhachhang") { type = NavType.IntType })
        ) { backStackEntry ->
            val makhachhang = backStackEntry.arguments?.getInt("makhachhang") ?: 0
            AddressSelectionScreen(navController, makhachhang)
        }
//màn hình tìm kiếm
        composable(
            route = SEARCHSCREEN.route + "?makhachhang={makhachhang}&tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("makhachhang") { nullable = true },
                navArgument("tentaikhoan") { type = NavType.StringType }
            )
        ) {
            val tentaikhoan = it.arguments?.getString("tentaikhoan") ?: null
            val makhachhang = it.arguments?.getString("makhachhang") ?: null
            SearchScreen(navController, makhachhang, tentaikhoan)
        }

        composable(SEARCHSCREEN.route) {
            SearchScreen(navController, null, null)
        }
//hoa don
        composable(
            route = "${HOADONDETAILSCREEN.route}?madonhang={madonhang}&tongtien={tongtien}",
            arguments = listOf(
                navArgument("madonhang") { type = NavType.IntType },
                navArgument("tongtien") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val madonhang = backStackEntry.arguments?.getInt("madonhang") ?: 0
            val tongtien = backStackEntry.arguments?.getInt("tongtien") ?: 0
            DonHangDetailScreen(navController, madonhang, tongtien)
        }

        composable(ADMINSCREEN.route) {
            AdminScreen(navController)
        }

        composable(REGISTERSCREEN.route) { // Khởi tạo rõ ràng ViewModel
            RegisterScreen(
                navController = navController,
                taiKhoanViewModel = taiKhoanViewModel,
                khachHangViewModel
            )
        }
////        //yêu thích
        composable(
            route = FAVORITE.route + "?tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("tentaikhoan") { nullable = true; type = NavType.StringType }
            )
        ) { backStackEntry ->

            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan")
            val taiKhoanViewModel: TaiKhoanViewModel = viewModel()
            val taikhoan = taiKhoanViewModel.taikhoan

            // Gọi API lấy tài khoản nếu chưa có
            LaunchedEffect(tentaikhoan) {
                if (tentaikhoan != null && taiKhoanViewModel.taikhoan == null) {
                    taiKhoanViewModel.getTaiKhoanByTentaikhoan(tentaikhoan)
                }
            }

            // Chờ đến khi có tài khoản mới hiển thị
            if (taikhoan != null) {
                taikhoan.MaKhachHang?.let {
                    FavoriteScreen(
                        navController = navController,
                        makhachhang = it,
                        tentaikhoan = tentaikhoan
                    )
                }
            } else {
                // Có thể cho loading hoặc text
                Text("🔒 Đang tải tài khoản...")
            }
        }

        //thong ke
        composable(
            route = "${THONGKE.route}?tentaikhoan={tentaikhoan}",
            arguments = listOf(
                navArgument("tentaikhoan") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }
            )
        ) {
            val tentaikhoan = it.arguments?.getString("tentaikhoan")
            val thongKeViewModel: ThongKeViewModel = viewModel()
            ThongKeScreen(viewModel = thongKeViewModel, tentaikhoan = tentaikhoan)
        }

        // Quản lý khuyến mãi - Admin
        composable(
            route = ADMIN_KHUYENMAI.route + "?tentaikhoan={tentaikhoan}",
            arguments = listOf(navArgument("tentaikhoan") { type = NavType.StringType })
        ) { backStackEntry ->
            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan")
            val khuyenMaiViewModel: KhuyenMaiViewModel = viewModel()
            val sanPhamViewModel: SanPhamViewModel = viewModel()

            // ĐẢM BẢO ĐÃ LOAD DANH SÁCH SẢN PHẨM
            LaunchedEffect(Unit) {
                sanPhamViewModel.getAllSanPham()
            }
            val danhSachSanPham = sanPhamViewModel.danhSachAllSanPham

            AdminKhuyenMaiScreen(
                viewModel = khuyenMaiViewModel,
                danhSachSanPham = danhSachSanPham,
                //navController = navController,
            )
        }
//        // Khuyến mãi - Người dùng
//        composable(
//            route = NavRoute.KHUYENMAI.route + "?tentaikhoan={tentaikhoan}",
//            arguments = listOf(navArgument("tentaikhoan") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val tentaikhoan = backStackEntry.arguments?.getString("tentaikhoan")
//            val khuyenMaiViewModel: KhuyenMaiViewModel = viewModel()
//            LaunchedEffect(tentaikhoan) {
//                khuyenMaiViewModel.fetchTatCaKhuyenMai()
//            }
//            KhuyenMaiScreen(
//                navController = navController,
//                khuyenMaiViewModel = khuyenMaiViewModel,
//                tentaikhoan = tentaikhoan
//            )
//        }

      }
    }
}
