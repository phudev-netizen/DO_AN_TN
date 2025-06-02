package com.example.lapstore.views

import NavRoute
import SanPhamViewModel
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lapstore.R
import com.example.lapstore.components.ProductCard
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

    LaunchedEffect(isFocused) {
        if (isFocused) {
            if (taikhoan != null)
                navController.navigate("${NavRoute.SEARCHSCREEN.route}?makhachhang=${taikhoan.MaKhachHang}&tentaikhoan=${taikhoan.TenTaiKhoan}")
            else
                navController.navigate(NavRoute.SEARCHSCREEN.route)
        }
    }

    if (tentaikhoan != null) {
        taiKhoanViewModel.getTaiKhoanByTentaikhoan(tentaikhoan)
    }
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Red, darkIcons = false)
    }
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
                com.example.lapstore.CategoryMenuMain()
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
                                        keyboardController?.hide()
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Outlined.Menu,
                                        contentDescription = "Profile",
                                        tint = Color.Red
                                    )
                                }
                                Text(
                                    text = "Danh mục",
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
                                        Icons.Outlined.Computer,
                                        contentDescription = "Profile",
                                        tint = Color.Red
                                    )
                                }
                                Text(
                                    text = "Phụ kiện",
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
                            items(danhSachSanPham) { sanpham ->
                                if (taikhoan != null) {
                                    ProductCard(sanpham, taikhoan.MaKhachHang.toString(), taikhoan.TenTaiKhoan, navController)
                                } else {
                                    ProductCard(sanpham, null, tentaikhoan, navController)
                                }
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
                            items(danhSachSanPhamVanPhong.value) { sanphamvp ->
                                if (taikhoan != null) {
                                    ProductCard(sanphamvp, taikhoan.MaKhachHang.toString(), taikhoan.TenTaiKhoan, navController)
                                } else {
                                    ProductCard(sanphamvp, null, tentaikhoan, navController)
                                }
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
                            items(danhSachSanPhamGaming.value) { sanphamgm ->
                                if (taikhoan != null) {
                                    ProductCard(sanphamgm, taikhoan.MaKhachHang.toString(), tentaikhoan, navController)
                                } else {
                                    ProductCard(sanphamgm, null, null, navController)
                                }
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