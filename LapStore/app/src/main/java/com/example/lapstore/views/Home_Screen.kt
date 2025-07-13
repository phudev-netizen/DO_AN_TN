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
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.lapstore.models.SanPham
import com.example.lapstore.models.TaiKhoan
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
    val systemUiController = rememberSystemUiController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

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

    val yeuThichViewModel: YeuThichViewModel = viewModel()
    val danhSachYeuThich by yeuThichViewModel.danhSachYeuThich.observeAsState(emptyList())

    var isFocused by remember { mutableStateOf(false) }
    var showProductDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(tentaikhoan) {
        if (!tentaikhoan.isNullOrEmpty()) {
            taiKhoanViewModel.getTaiKhoanByTentaikhoan(tentaikhoan)
        }
    }

    LaunchedEffect(taikhoan?.MaKhachHang) {
        taikhoan?.MaKhachHang?.let { maKH ->
            yeuThichViewModel.loadDanhSach(maKH)
            gioHangViewModel.getGioHangByKhachHang(maKH.toInt())
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAllSanPham()
        viewModel.getSanPhamTheoLoaiGaming()
        viewModel.getSanPhamTheoLoaiVanPhong()
    }

    LaunchedEffect(isFocused) {
        if (isFocused) {
            if (taikhoan?.MaKhachHang != null && taikhoan.TenTaiKhoan != null) {
                navController.navigate("${NavRoute.SEARCHSCREEN.route}?makhachhang=${taikhoan.MaKhachHang}&tentaikhoan=${taikhoan.TenTaiKhoan}")
            } else {
                navController.navigate(NavRoute.SEARCHSCREEN.route)
            }
        }
    }

    SideEffect {
        systemUiController.setStatusBarColor(Color.Red, darkIcons = false)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Red
                ),
                title = {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Bạn cần tìm gì?", color = Color.Gray, fontSize = 12.sp) },
                        trailingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Black)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .onFocusChanged { isFocused = it.isFocused },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        textStyle = TextStyle(fontSize = 14.sp)
                    )
                },
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
                                    Badge(containerColor = Color.Yellow) {
                                        Text(cartItemCount.toString(), fontSize = 10.sp, color = Color.Black)
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Giỏ hàng", tint = Color.White)
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                tonalElevation = 5.dp
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BottomNavItem(Icons.Outlined.Home, "Home") {
                        navController.navigate("${NavRoute.HOME.route}?tentaikhoan=$tentaikhoan") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                    BottomNavItem(Icons.Outlined.Favorite, "Yêu thích") {
                        navController.navigate("${NavRoute.FAVORITE.route}?tentaikhoan=$tentaikhoan")
                    }
                    BottomNavItem(Icons.Outlined.Person, "Tài khoản") {
                        if (tentaikhoan != null) {
                            navController.navigate("${NavRoute.ACCOUNT.route}?tentaikhoan=${taiKhoanViewModel.tentaikhoan}")
                        } else {
                            navController.navigate(NavRoute.LOGINSCREEN.route)
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            if (role == "admin") {
                FloatingActionButton(
                    onClick = { showProductDialog = true },
                    containerColor = Color.Red
                ) {
                    Icon(Icons.Default.Edit, tint = Color.White, contentDescription = null)
                }
            }
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Red)
            }
        } else if (errorMessage != null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Lỗi: $errorMessage")
            }
        } else {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                item {
                    SectionTitle("Tất cả sản phẩm")
                }
                item {
                    ProductRow(danhSachSanPham, danhSachYeuThich, taikhoan, yeuThichViewModel, navController)
                }
                item {
                    SectionTitle("Laptop Văn Phòng")
                }
                item {
                    ProductRow(danhSachSanPhamVanPhong.value, danhSachYeuThich, taikhoan, yeuThichViewModel, navController)
                }
                item {
                    SectionTitle("Laptop Gaming")
                }
                item {
                    ProductRow(danhSachSanPhamGaming.value, danhSachYeuThich, taikhoan, yeuThichViewModel, navController)
                }
            }
        }

        if (showProductDialog) {
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

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun ProductRow(
    products: List<SanPham>,
    favorites: List<YeuThich>,
    taikhoan: TaiKhoan?,
    yeuThichViewModel: YeuThichViewModel,
    navController: NavHostController
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) { sanpham ->
            val context = LocalContext.current
            val isFavorite = favorites.any { it.maSanPham == sanpham.MaSanPham }

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

@Composable
fun BottomNavItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick) {
            Icon(icon, contentDescription = label, tint = Color.Red)
        }
        Text(label, fontSize = 12.sp)
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