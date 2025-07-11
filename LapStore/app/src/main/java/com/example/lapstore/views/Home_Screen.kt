import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.lapstore.views.ProductCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lapstore.R
import com.example.lapstore.viewmodels.GioHangViewModel
import com.example.lapstore.viewmodels.TaiKhoanViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: SanPhamViewModel,
    tentaikhoan: String?,
    role: String
) {
    var isFocused by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val danhSachSanPham = viewModel.danhSachAllSanPham
    val danhSachSanPhamGaming = viewModel.danhSachSanPhamGaming.collectAsState()
    val danhSachSanPhamVanPhong = viewModel.danhSachSanPhamVanPhong.collectAsState()
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    val taiKhoanViewModel: TaiKhoanViewModel = viewModel()
    val taikhoan = taiKhoanViewModel.taikhoan

    val gioHangViewModel: GioHangViewModel = viewModel()

    val cartItemCount by remember {
        derivedStateOf { gioHangViewModel.listGioHang.sumOf { it.SoLuong } }
    }


    val context = LocalContext.current
    val yeuThichViewModel: YeuThichViewModel = viewModel()
    val danhSachYeuThich by yeuThichViewModel.danhSachYeuThich.observeAsState(emptyList())
    var showProductDialog by remember { mutableStateOf(false) }

    LaunchedEffect(taikhoan?.MaKhachHang) {
        taikhoan?.MaKhachHang?.let { maKH ->
            yeuThichViewModel.loadDanhSach(maKH)
        }
    }

    // Điều hướng đến SearchScreen khi trường tìm kiếm được focus

    LaunchedEffect(isFocused) {
        if (isFocused) {
            if (taikhoan?.MaKhachHang != null && taikhoan.TenTaiKhoan != null) {
            navController.navigate("${NavRoute.SEARCHSCREEN.route}?makhachhang=${taikhoan.MaKhachHang}&tentaikhoan=${taikhoan.TenTaiKhoan}")
        } else {
            navController.navigate(NavRoute.SEARCHSCREEN.route)
            }
        }
    }
    // Lấy thông tin tài khoản khi có tentaikhoan
    LaunchedEffect(tentaikhoan) {
        if (!tentaikhoan.isNullOrEmpty()) {
            taiKhoanViewModel.getTaiKhoanByTentaikhoan(tentaikhoan)
        }
    }
    // Thiết lập màu sắc của thanh trạng thái
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Red, darkIcons = false)
    }
    // Lấy danh sách sản phẩm yêu thích khi có MaKhachHang
    LaunchedEffect(Unit) {
        viewModel.getSanPhamTheoLoaiGaming()
        viewModel.getSanPhamTheoLoaiVanPhong()
        viewModel.getAllSanPham()
    }
    LaunchedEffect(taikhoan?.MaKhachHang) {
        taikhoan?.MaKhachHang?.let { maKH ->
            yeuThichViewModel.loadDanhSach(maKH)
            gioHangViewModel.getGioHangByKhachHang(maKH.toInt()) // 🛒 Gọi ViewModel giỏ hàng
        }
    }


    // Giao diện chính của HomeScreen
        Scaffold(
            containerColor = Color.White,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Red
                    ),
                    actions = {
                        IconButton(
                            onClick = {
                                if (taikhoan == null) {
                                    navController.navigate(NavRoute.LOGINSCREEN.route)
                                } else {
                                    navController.navigate("${NavRoute.CART.route}?makhachhang=${taikhoan.MaKhachHang}&tentaikhoan=${taikhoan.TenTaiKhoan}")
                                }
                            }
                        ) {
                            BadgedBox(
                                badge = {
                                    if (cartItemCount > 0) {
                                        Badge(
                                            containerColor = Color.Yellow,
                                            contentColor = Color.Black
                                        ) {
                                            Text(cartItemCount.toString(), fontSize = 10.sp)
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ShoppingCart,
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                            title = {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = "",
                                onValueChange = {},
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .onFocusChanged { focusState ->
                                        isFocused = focusState.isFocused
                                    },
                                textStyle = TextStyle(
                                    color = Color.Black,
                                    fontSize = 16.sp
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedBorderColor = Color.White,
                                    unfocusedBorderColor = Color.White
                                ),
                                placeholder = {
                                    Text(
                                        text = "Bạn cần tìm gì",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 13.sp
                                        ),
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "",
                                        tint = Color.Black
                                    )
                                },
                                shape = RoundedCornerShape(50)
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    tonalElevation = 4.dp
                ) {
                    Column {
                        HorizontalDivider(color = Color.Red)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                IconButton(
                                    modifier = Modifier.size(45.dp),
                                    onClick = {
                                        if (taikhoan != null) {
                                            navController.navigate("${NavRoute.HOME.route}?tentaikhoan=${tentaikhoan}") {
                                                popUpTo(0) { inclusive = true }
                                            }
                                        } else {
                                            navController.navigate(NavRoute.HOME.route)
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Outlined.Home,
                                        contentDescription = "Profile",
                                        tint = Color.Red
                                    )
                                }
                                Text(
                                    text = "Home",
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                IconButton(
                                    modifier = Modifier.size(45.dp),
                                    onClick = {
                                        if (tentaikhoan != null) {
                                            navController.navigate("${NavRoute.FAVORITE.route}?tentaikhoan=${tentaikhoan}") {
                                                popUpTo(0) { inclusive = true }
                                            }
                                        } else {
                                            navController.navigate(NavRoute.FAVORITE.route)
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Outlined.Favorite,
                                        contentDescription = "Profile",
                                        tint = Color.Red
                                    )
                                }
                                Text(
                                    text = "Yêu thích",
                                )
                            }
                            // Kiểm tra vai trò của người dùng

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                IconButton(
                                    modifier = Modifier.size(45.dp),
                                    onClick = {
                                        if (tentaikhoan != null) {
                                            navController.navigate("${NavRoute.ACCOUNT.route}?tentaikhoan=${taiKhoanViewModel.tentaikhoan}")
                                        } else {
                                            navController.navigate(NavRoute.LOGINSCREEN.route)
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Outlined.Person,
                                        contentDescription = "Profile",
                                        tint = Color.Red
                                    )
                                }
                                Text(text = "Tài khoản")
                            }
                        }
                    }
                }
            }
        ) {
            if (isLoading) {
                Text(text = "Đang tải dữ liệu...")
            } else if (errorMessage != null) {
                Text(text = "Lỗi: $errorMessage")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    item {
                        Text(
                            text = "Tất cả sản phẩm",
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Hiển thị danh sách sản phẩm
                            items(danhSachSanPham) { sanpham ->
                                val context = LocalContext.current
                                val isFavorite =
                                    danhSachYeuThich.any { it.maSanPham == sanpham.MaSanPham }
                                ProductCard(
                                    sanpham = sanpham,
                                    isFavorite = isFavorite,
                                    onFavoriteClick = {
                                        val maKH = taikhoan?.MaKhachHang
                                        if (maKH != null) {
                                            if (isFavorite) {
                                                yeuThichViewModel.xoaYeuThich(maKH, sanpham.MaSanPham) {
                                                    Toast.makeText(context, "Đã xoá khỏi yêu thích", Toast.LENGTH_SHORT).show()
                                                }
                                            } else {
                                                yeuThichViewModel.themYeuThich(maKH, sanpham.MaSanPham) {
                                                    Toast.makeText(context, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } else {
                                            // ✅ Hiển thị thông báo yêu cầu đăng nhập
                                            Toast.makeText(context, "Vui lòng đăng nhập để sử dụng chức năng yêu thích", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    navController = navController,
                                    makhachhang = taikhoan?.MaKhachHang?.toString(),
                                    tentaikhoan = taikhoan?.TenTaiKhoan
                                )
                            }

                        }
                    }
                    // LazyRow cho Laptop Văn Phòng
                    item {
                        Row {
                            Text(
                                text = "Laptop Văn Phòng",
                                modifier = Modifier.padding(10.dp),
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(danhSachSanPhamVanPhong.value) { sanpham ->
                                val context = LocalContext.current
                                val isFavorite = danhSachYeuThich.any { it.maSanPham == sanpham.MaSanPham }
                                ProductCard(
                                    sanpham = sanpham,
                                    isFavorite = isFavorite,
                                    onFavoriteClick = {
                                        val maKH = taikhoan?.MaKhachHang
                                        if (maKH != null) {
                                            if (isFavorite) {
                                                yeuThichViewModel.xoaYeuThich(maKH, sanpham.MaSanPham) {
                                                    Toast.makeText(context, "Đã xoá khỏi yêu thích", Toast.LENGTH_SHORT).show()
                                                }
                                            } else {
                                                yeuThichViewModel.themYeuThich(maKH, sanpham.MaSanPham) {
                                                    Toast.makeText(context, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } else {
                                            // ✅ Hiển thị thông báo yêu cầu đăng nhập
                                            Toast.makeText(context, "Vui lòng đăng nhập để sử dụng chức năng yêu thích", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    navController = navController,
                                    makhachhang = taikhoan?.MaKhachHang?.toString(),
                                    tentaikhoan = taikhoan?.TenTaiKhoan
                                )
                            }
                        }
                    }
                    // LazyRow cho Laptop Gaming
                    item {
                        Text(
                            text = "Laptop Gaming",
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(danhSachSanPhamGaming.value) { sanpham ->
                                val context = LocalContext.current

                                val isFavorite = danhSachYeuThich.any { it.maSanPham == sanpham.MaSanPham }
                                ProductCard(
                                    sanpham = sanpham,
                                    isFavorite = isFavorite,
                                    onFavoriteClick = {
                                        val maKH = taikhoan?.MaKhachHang
                                        if (maKH != null) {
                                            if (isFavorite) {
                                                yeuThichViewModel.xoaYeuThich(maKH, sanpham.MaSanPham) {
                                                    Toast.makeText(context, "Đã xoá khỏi yêu thích", Toast.LENGTH_SHORT).show()
                                                }
                                            } else {
                                                yeuThichViewModel.themYeuThich(maKH, sanpham.MaSanPham) {
                                                    Toast.makeText(context, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } else {
                                            // ✅ Hiển thị thông báo yêu cầu đăng nhập
                                            Toast.makeText(context, "Vui lòng đăng nhập để sử dụng chức năng yêu thích", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    navController = navController,
                                    makhachhang = taikhoan?.MaKhachHang?.toString(),
                                    tentaikhoan = taikhoan?.TenTaiKhoan
                                )

                            }

                        }
                    }
                }
                if (role == "admin") {
                    Box(Modifier.fillMaxSize()) {
                        FloatingActionButton(
                            onClick = { showProductDialog = true },
                            containerColor = Color.Red,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(20.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Quản lý sản phẩm", tint = Color.White)
                        }
                    }
                    ProductManagementDialog(
                        showDialog = showProductDialog,
                        onDismissRequest = { showProductDialog = false },
                        viewModel = viewModel,
                        onAddClick = {
                            navController.navigate(NavRoute.PRODUCT_MANAGEMENT.route)
                        },
                        onUpdateClick = {
                            navController.navigate(NavRoute.PRODUCT_MANAGEMENT.route)
                        }
                    )
                }
            }
        }
}

@Composable
fun ProductManagementDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onAddClick: () -> Unit,
    onUpdateClick: () -> Unit,
    viewModel: SanPhamViewModel
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = "Quản lý sản phẩm") },
            text = {
                Column {
                    Button(
                        onClick = {
                            onAddClick()
                            onDismissRequest()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Thêm sản phẩm mới")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            onUpdateClick()
                            onDismissRequest()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Cập nhật sản phẩm")
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                Button(onClick = onDismissRequest) {
                    Text("Đóng")
                }
            }
        )
    }
}