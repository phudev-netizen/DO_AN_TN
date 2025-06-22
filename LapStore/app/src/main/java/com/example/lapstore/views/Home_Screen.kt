import android.util.Log
import android.widget.Toast
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
import com.example.lapstore.CategoryMenuMain
import com.example.lapstore.R
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


    val context = LocalContext.current
    val yeuThichViewModel: YeuThichViewModel = viewModel()
    val danhSachYeuThich by yeuThichViewModel.danhSachYeuThich.observeAsState(emptyList())

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

    ModalNavigationDrawer(
        modifier = Modifier.background(Color.White),
        scrimColor = DrawerDefaults.scrimColor,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Red),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "DANH MỤC SẢN PHẨM",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White),
                        modifier = Modifier.padding(10.dp)
                    )
                    IconButton(
                        onClick = {
                            scope.launch { drawerState.close() }
                        }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
                CategoryMenuMain(
                    onItemClick = { category ->
                        scope.launch {
                            drawerState.close()
                            if (taikhoan != null) {
                                navController.navigate("${NavRoute.ACCESSORY.route}?category=${category}&tentaikhoan=${taikhoan.TenTaiKhoan}")
                            } else {
                                navController.navigate("${NavRoute.ACCESSORY.route}?category=${category}")
                            }
                        }
                    }
                )
                Text("Thông tin", modifier = Modifier.padding(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.SupportAgent, contentDescription = "")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("19001009")
                }
            }
        }
    ) {
        Scaffold(
            containerColor = Color.White,
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                keyboardController?.hide()
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    },
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
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "",
                                tint = Color.White
                            )
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
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                IconButton(
                                    modifier = Modifier.size(45.dp),
                                    onClick = {
                                        if (tentaikhoan != null) {
                                            navController.navigate("${NavRoute.ACCESSORY.route}?tentaikhoan=${tentaikhoan}") {
                                                popUpTo(0) { inclusive = true }
                                            }
                                        } else {
                                            navController.navigate(NavRoute.ACCESSORY.route)
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Outlined.Category,
                                        contentDescription = "Profile",
                                        tint = Color.Red
                                    )
                                }
                                Text(
                                    text = "Phụ kiện",
                                )
                            }
                            // Kiểm tra vai trò của người dùng
                            if (role == "admin") {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    IconButton(
                                        modifier = Modifier.size(45.dp),
                                        onClick = {
                                            // Truyền tentaikhoan nếu cần
                                            if (tentaikhoan != null) {
                                                navController.navigate("${NavRoute.THONGKE.route}?tentaikhoan=$tentaikhoan")
                                            } else {
                                                navController.navigate(NavRoute.THONGKE.route)
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Analytics,
                                            contentDescription = "Thống kê",
                                            tint = Color.Red
                                        )
                                    }
                                    Text(text = "Thống kê")
                                }
                            }

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
                    // BANNER: Thêm banner ở đầu trang, tự động chuyển
                    item {
                        BannerAuto()
                    }
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
            }
        }
    }
}



@Composable
fun BannerAuto() {
    val banners = listOf(
        R.drawable.lp1,   // Thay bằng tên ảnh banner của bạn
        R.drawable.lp2,         // Có thể thêm nhiều ảnh banner nếu muốn
        R.drawable.lp3,
    )
    val bannerCount = banners.size
    val bannerHeight = 160.dp
    val bannerShape = RoundedCornerShape(20.dp)
    val listState = rememberLazyListState()
    var currentIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    // Tự động chuyển banner mỗi 3 giây
    LaunchedEffect(currentIndex) {
        delay(3000)
        val next = (currentIndex + 1) % bannerCount
        currentIndex = next
        scope.launch { listState.animateScrollToItem(next) }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .height(bannerHeight + 8.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .height(bannerHeight),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            items(bannerCount) { idx ->
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(bannerHeight)
                        .clip(bannerShape)
                        .background(Color.White, bannerShape)
                        .border(
                            width = 2.dp,
                            color = Color(0xFFE53935),
                            shape = bannerShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = banners[idx]),
                        contentDescription = "Banner $idx",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.8f)
                    )
                }
            }
        }
        // Indicator Dots
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 7.dp)
        ) {
            repeat(bannerCount) { i ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(if (i == currentIndex) 12.dp else 8.dp)
                        .clip(RoundedCornerShape(50))
                        .background(if (i == currentIndex) Color.Red else Color.LightGray)
                )
            }
        }
    }
}


