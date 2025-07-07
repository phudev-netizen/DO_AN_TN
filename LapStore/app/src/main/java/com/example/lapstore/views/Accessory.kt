//package com.example.lapstore.views
//
//import NavRoute
//import SanPhamViewModel
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material.icons.outlined.Home
//import androidx.compose.material.icons.outlined.Person
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.example.lapstore.ui.ProductCard_Accessory
//import com.example.lapstore.viewmodels.TaiKhoanViewModel
//import com.google.accompanist.systemuicontroller.rememberSystemUiController
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AccessoryScreen(
//    navController: NavHostController,
////    viewModel: SanPhamViewModel,
//    viewModel: PhuKienViewModel,
//    tentaikhoan: String?
//) {
//    var isFocused by remember { mutableStateOf(false) }
//
//    val systemUiController = rememberSystemUiController()
//    val keyboardController = LocalSoftwareKeyboardController.current
//    val scope = rememberCoroutineScope()
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//
//
//    val isLoading = viewModel.isLoading
//    val errorMessage = viewModel.errorMessage
//
//    val taiKhoanViewModel: TaiKhoanViewModel = viewModel()
//    val taikhoan = taiKhoanViewModel.taikhoan
//
//    //val danhSachSanPham = viewModel.danhSachAllSanPham
//    val danhSachBanPhim = viewModel.danhSachBanPhim.collectAsState()
//    val danhSachChuot = viewModel.danhSachChuot.collectAsState()
//
//
//    LaunchedEffect(viewModel) {
//        viewModel.getBanPhim()
//        viewModel.getChuot()
//    }
//
//    LaunchedEffect(isFocused) {
//        if (isFocused) {
//            if (taikhoan != null)
//                navController.navigate("${NavRoute.SEARCHSCREEN.route}?makhachhang=${taikhoan.MaKhachHang}&tentaikhoan=${taikhoan.TenTaiKhoan}")
//            else
//                navController.navigate(NavRoute.SEARCHSCREEN.route)
//        }
//    }
//
//    if (tentaikhoan != null) {
//        taiKhoanViewModel.getTaiKhoanByTentaikhoan(tentaikhoan)
//    }
//    SideEffect {
//        systemUiController.setStatusBarColor(color = Color.Red, darkIcons = false)
//    }
//
//    ModalNavigationDrawer(
//        modifier = Modifier.background(Color.White),
//        scrimColor = DrawerDefaults.scrimColor,
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet(
//                drawerContainerColor = Color.White,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .background(Color.White)
//            ) {
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//        }
//    ) {
//        Scaffold(
//            containerColor = Color.White,
//            topBar = {
//                CenterAlignedTopAppBar(
//                    navigationIcon = {
//                        IconButton(
//                            onClick = {
//                                keyboardController?.hide()
//                                scope.launch {
//                                    drawerState.apply {
//                                        if (isClosed) open() else close()
//                                    }
//                                }
//                            }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.Menu,
//                                contentDescription = "",
//                                tint = Color.White
//                            )
//                        }
//                    },
//                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                        containerColor = Color.Red
//                    ),
//                    actions = {
//                        IconButton(
//                            onClick = {
//                                if (taikhoan == null) {
//                                    navController.navigate(NavRoute.LOGINSCREEN.route)
//                                } else {
//                                    navController.navigate("${NavRoute.CART.route}?makhachhang=${taikhoan.MaKhachHang}&tentaikhoan=${taikhoan.TenTaiKhoan}")
//                                }
//                            }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.ShoppingCart,
//                                contentDescription = "",
//                                tint = Color.White
//                            )
//                        }
//                    },
//                    title = {
//                        Row(
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            OutlinedTextField(
//                                value = "",
//                                onValueChange = {},
//                                modifier = Modifier
//                                    .height(50.dp)
//                                    .fillMaxWidth()
//                                    .onFocusChanged { focusState ->
//                                        isFocused = focusState.isFocused
//                                    },
//                                textStyle = TextStyle(
//                                    color = Color.Black,
//                                    fontSize = 16.sp
//                                ),
//                                colors = OutlinedTextFieldDefaults.colors(
//                                    focusedContainerColor = Color.White,
//                                    unfocusedContainerColor = Color.White,
//                                    focusedBorderColor = Color.White,
//                                    unfocusedBorderColor = Color.White
//                                ),
//                                placeholder = {
//                                    Text(
//                                        text = "Bạn cần tìm gì",
//                                        style = TextStyle(
//                                            color = Color.Black,
//                                            fontSize = 13.sp
//                                        ),
//                                    )
//                                },
//                                trailingIcon = {
//                                    Icon(
//                                        imageVector = Icons.Filled.Search,
//                                        contentDescription = "",
//                                        tint = Color.Black
//                                    )
//                                },
//                                shape = RoundedCornerShape(50)
//                            )
//                        }
//                    }
//                )
//            },
//            bottomBar = {
//                BottomAppBar(
//                    containerColor = Color.White,
//                    contentColor = Color.Black,
//                    tonalElevation = 4.dp
//                ) {
//                    Column {
//                        HorizontalDivider(color = Color.Red)
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceEvenly,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.SpaceAround
//                            ) {
//                                IconButton(
//                                    modifier = Modifier.size(45.dp),
//                                    onClick = {
//                                        if (taikhoan != null) {
//                                            navController.navigate("${NavRoute.HOME.route}?tentaikhoan=${tentaikhoan}") {
//                                                popUpTo(0) { inclusive = true }
//                                            }
//                                        } else {
//                                            navController.navigate(NavRoute.HOME.route)
//                                        }
//                                    }
//                                ) {
//                                    Icon(
//                                        Icons.Outlined.Home,
//                                        contentDescription = "Profile",
//                                        tint = Color.Red
//                                    )
//                                }
//                                Text(
//                                    text = "Home",
//                                )
//                            }
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.SpaceAround
//                            ) {
//                                IconButton(
//                                    modifier = Modifier.size(45.dp),
//                                    onClick = {
//                                        if (tentaikhoan != null) {
//                                            navController.navigate("${NavRoute.ACCOUNT.route}?tentaikhoan=${taiKhoanViewModel.tentaikhoan}")
//                                        } else {
//                                            navController.navigate(NavRoute.LOGINSCREEN.route)
//                                        }
//                                    }
//                                ) {
//                                    Icon(
//                                        Icons.Outlined.Person,
//                                        contentDescription = "Profile",
//                                        tint = Color.Red
//                                    )
//                                }
//                                Text(text = "Tài khoản")
//                            }
//                        }
//                    }
//                }
//            }
//        ) {
//            if (isLoading) {
//                Text(text = "Đang tải dữ liệu...")
//            } else if (errorMessage != null) {
//                Text(text = "Lỗi: $errorMessage")
//            } else {
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(it)
//                ) {
////                    item {
////                        Text(
////                            text = "Tất cả phụ kiện",
////                            modifier = Modifier.padding(10.dp),
////                            fontWeight = FontWeight.Bold
////                        )
////                    }
//                    // phụ kiện
////                    item {
////                        Text(
////                            text = "Phụ kiện RAM và SSD",
////                            modifier = Modifier.padding(10.dp),
////                            fontWeight = FontWeight.Bold
////                        )
////                    }
//                    item {
//                        LazyRow(
//                            contentPadding = PaddingValues(horizontal = 8.dp),
//                            horizontalArrangement = Arrangement.spacedBy(8.dp)
//                        ) {
//                            items(danhSachChuot.value ?: emptyList()) { chuot ->
//                                if (taikhoan != null) {
//                                    ProductCard_Accessory(chuot, taikhoan.MaKhachHang?.toString(), tentaikhoan, navController)
//                                } else {
//                                    ProductCard_Accessory(chuot, null, null, navController)
//                                }
//                            }
//                        }
//                    }
//                    item {
//                        Text(
//                            text = "Phụ kiện Bàn phím và Chuột",
//                            modifier = Modifier.padding(10.dp),
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                    item {
//                        LazyRow(
//                            contentPadding = PaddingValues(horizontal = 8.dp),
//                            horizontalArrangement = Arrangement.spacedBy(8.dp)
//                        ) {
//                            items(danhSachBanPhim.value ?: emptyList()) { phim ->
//                                if (taikhoan != null) {
//                                    ProductCard_Accessory(phim, taikhoan.MaKhachHang?.toString(), tentaikhoan, navController)
//                                } else {
//                                    ProductCard_Accessory(phim, null, null, navController)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
