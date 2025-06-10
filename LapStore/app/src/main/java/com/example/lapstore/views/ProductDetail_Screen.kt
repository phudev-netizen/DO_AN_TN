//package com.example.lapstore.views
//
//import HinhAnhViewModel
//import SanPhamViewModel
//import android.icu.text.DecimalFormat
//import android.util.Log
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AddShoppingCart
//import androidx.compose.material.icons.filled.ArrowBackIosNew
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonColors
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarDuration
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.SideEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import coil.compose.AsyncImage
//import com.example.lapstore.models.GioHang
//import com.example.lapstore.models.SanPham
//import com.example.lapstore.models.TaiKhoan
//import com.example.lapstore.viewmodels.GioHangViewModel
//import com.example.lapstore.viewmodels.TaiKhoanViewModel
//import com.google.accompanist.systemuicontroller.rememberSystemUiController
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import com.example.lapstore.R
//import com.example.lapstore.views.ThongSoRow
//import androidx.compose.foundation.Canvas
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.foundation.border
//import androidx.compose.ui.draw.shadow
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProductDetail_Screen(
//    navController: NavHostController,
//    id: String,
//    makhachhang: String?,
//    tentaikhoan: String?,
//    viewModel: SanPhamViewModel,
//    hinhAnhViewModel: HinhAnhViewModel,
//) {
//
//    Log.d("fdf", id.toString())
//    Log.d("fdf", makhachhang.toString())
//    Log.d("fdf", tentaikhoan.toString())
//    var isFocused by remember { mutableStateOf(false) }
//
//    var isLoading by remember { mutableStateOf(false) }
//
//    val systemUiController = rememberSystemUiController()
//    var gioHangViewModel: GioHangViewModel = viewModel()
//
//    val danhSachHinhAnh = hinhAnhViewModel.danhsachhinhanhtheosanpham
//    val danhsachgiohang = gioHangViewModel.listGioHang
//    val danhsachsanpham = viewModel.danhSachAllSanPham
//
//    viewModel.getAllSanPham()
//
//    val sanPham = viewModel.sanPham
//
//    var snackbarHostState = remember {
//        SnackbarHostState()
//    }
//
//
//    var scope = rememberCoroutineScope()
//
//    var hinhAnhHienTai by remember { mutableStateOf<String?>(null) }
//
//    LaunchedEffect(isFocused) {
//        if (isFocused) {
//            if (tentaikhoan != null && makhachhang != null)
//                navController.navigate("${NavRoute.SEARCHSCREEN.route}?makhachhang=${makhachhang}&tentaikhoan=${tentaikhoan}")
//            else
//                navController.navigate(NavRoute.SEARCHSCREEN.route)
//        }
//    }
//
//    if (makhachhang != null) {
//        LaunchedEffect(makhachhang) {
//            gioHangViewModel.getGioHangByKhachHang(makhachhang.toInt())
//        }
//    }
//
//    Log.d("fdf", danhsachgiohang.toString())
//
//    LaunchedEffect(id) {
//        if (id.isNotEmpty()) {
//            viewModel.getSanPhamById(id)
//            hinhAnhViewModel.getHinhAnhTheoSanPham(id.toInt())
//        }
//    }
//    SideEffect {
//        systemUiController.setStatusBarColor(color = Color.Red, darkIcons = false)
//    }
//
//
//    LaunchedEffect(danhSachHinhAnh) {
//        if (danhSachHinhAnh.isNotEmpty()) {
//            hinhAnhHienTai = danhSachHinhAnh.first().DuongDan
//        }
//    }
//    Scaffold(
//        containerColor = Color.White,
//        topBar = {
//            CenterAlignedTopAppBar(
//                navigationIcon = {
//                    IconButton(
//                        onClick = {
//                            navController.popBackStack()
//                        }) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBackIosNew,
//                            contentDescription = "",
//                            tint = Color.White
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = Color.Red
//                ),
//                actions = {
//                    IconButton(
//                        onClick = {
//                            if (makhachhang == null && tentaikhoan == null) {
//                                navController.navigate(NavRoute.LOGINSCREEN.route)
//                            } else {
//                                navController.navigate("${NavRoute.CART.route}?makhachhang=${makhachhang}&tentaikhoan=${tentaikhoan}")
//                            }
//                        }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.ShoppingCart,
//                            contentDescription = "",
//                            tint = Color.White
//                        )
//                    }
//                },
//                title = {
//                    // Search Bar
//                    Row(
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//
//                        OutlinedTextField(
//                            value = "",
//                            onValueChange = {
//
//                            },
//                            modifier = Modifier
//                                .height(50.dp)
//                                .fillMaxWidth()
//                                .onFocusChanged { focusState ->
//                                    isFocused = focusState.isFocused
//                                },
//                            textStyle = TextStyle(
//                                color = Color.Black,
//                                fontSize = 16.sp
//                            ),
//                            colors = OutlinedTextFieldDefaults.colors(
//                                focusedContainerColor = Color.White,
//                                unfocusedContainerColor = Color.White,
//                                focusedBorderColor = Color.White,
//                                unfocusedBorderColor = Color.White
//                            ),
//                            placeholder = {
//                                Text(
//                                    text = "Bạn cần tìm gì",
//                                    style = TextStyle(
//                                        color = Color.Black,
//                                        fontSize = 13.sp
//                                    ),
//                                )
//                            },
//                            trailingIcon = {
//                                Icon(
//                                    imageVector = Icons.Filled.Search,
//                                    contentDescription = "",
//                                    tint = Color.Black
//                                )
//                            },
//                            shape = RoundedCornerShape(50)
//                        )
//                    }
//                }
//
//            )
//
//        },
//    ) {
//        if (sanPham == null || danhSachHinhAnh.isEmpty() || hinhAnhHienTai == null) {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text("Đang tải dữ liệu...", fontSize = 18.sp)
//            }
//        } else {
//            LazyColumn(
//                modifier = Modifier
//                    .padding(it)
//                    .fillMaxSize()
//                    .background(Color.White)
//                    .padding(17.dp),
//                verticalArrangement = Arrangement.spacedBy(5.dp),
//            ) {
//                // Hình ảnh sản phẩm chính
//                item {
//                    Box(
//                        modifier = Modifier
//                            .padding(horizontal = 16.dp, vertical = 8.dp)
//                            .clip(RoundedCornerShape(12.dp)) // bo góc lớn hơn 1 chút
//                            .background(Color.White) // nền trắng sang hơn LightGray
//                            .border(
//                                width = 1.5.dp,
//                                color = Color.LightGray,
//                                shape = RoundedCornerShape(12.dp)
//                            )
//
//                    ) {
//                        AsyncImage(
//                            model = hinhAnhHienTai,
//                            contentDescription = "Hình ảnh sản phẩm",
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(300.dp)
//                                .clip(RoundedCornerShape(12.dp)),
//                            contentScale = ContentScale.Crop
//                        )
//                    }
//                }
//
//                // Danh sách hình ảnh nhỏ trong LazyRow
//                item {
//                    LazyRow(
//                        contentPadding = PaddingValues(horizontal = 16.dp),
//                        horizontalArrangement = Arrangement.spacedBy(12.dp),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        items(danhSachHinhAnh) { hinhanh ->
//                            Box(
//                                modifier = Modifier
//                                    .size(width = 100.dp, height = 90.dp)
//                                    .clip(RoundedCornerShape(12.dp))
//                                    .border(
//                                        width = 1.5.dp,
//                                        color = if (hinhanh.DuongDan == hinhAnhHienTai) Color(0xFF6200EE) else Color.LightGray,
//                                        shape = RoundedCornerShape(12.dp)
//                                    )
//                                    .clickable {
//                                        hinhAnhHienTai = hinhanh.DuongDan
//                                    }
//                                    .background(Color.White)
//                            ) {
//                                AsyncImage(
//                                    model = hinhanh.DuongDan,
//                                    contentDescription = "Hình ảnh sản phẩm",
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .clip(RoundedCornerShape(12.dp)),
//                                    contentScale = ContentScale.Crop
//                                )
//                            }
//                        }
//                    }
//                }
//                // Tên sản phẩm
//                item {
//                    Text(
//                        text = sanPham!!.TenSanPham,
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Left,
//                        lineHeight = 30.sp
//                    )
//                }
//
//                // Giá sản phẩm
//                item {
//
//                    Text(
//                        text = "Giá: ${formatGiaTien(sanPham.Gia)}",
//                        fontSize = 20.sp,
//                        color = Color.Black,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Left
//                    )
//                    if (sanPham.SoLuong == 0) {
//                        Text(
//                            text = "(Hết hàng)",
//                            fontSize = 17.sp,
//                            color = Color.Red,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier.padding(vertical = 4.dp)
//                        )
//                    }
//                }
//                // Nút thêm vào giỏ hàng
//                item {
//                    Column {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 8.dp),
//                            horizontalArrangement = Arrangement.spacedBy(12.dp)
//                        ) {
//                            Button(
//                                onClick = {
//                                    if (makhachhang == null) {
//                                        navController.navigate(NavRoute.LOGINSCREEN.route)
//                                    } else {
//                                        isLoading = true
//                                        scope.launch {
//                                            gioHangViewModel.getGioHangByKhachHang(makhachhang.toInt())
//                                            val gioHangHienTai = danhsachgiohang
//
//                                            val gioHangSanPham = gioHangHienTai.find { it.MaSanPham == sanPham.MaSanPham }
//                                            val sanPhamTonKho = danhsachsanpham.find { it.MaSanPham == sanPham.MaSanPham }
//
//                                            if (sanPhamTonKho == null || sanPhamTonKho.SoLuong <= 0) {
//                                                delay(500)
//                                                snackbarHostState.showSnackbar(
//                                                    "Sản phẩm đã hết hàng, không thể thêm vào giỏ hàng.",
//                                                    duration = SnackbarDuration.Short
//                                                )
//                                            } else if (gioHangSanPham != null) {
//                                                if (gioHangSanPham.SoLuong >= sanPhamTonKho.SoLuong) {
//                                                    delay(500)
//                                                    snackbarHostState.showSnackbar(
//                                                        "Số lượng trong kho chỉ còn ${sanPhamTonKho.SoLuong} sản phẩm, không thể thêm nữa.",
//                                                        duration = SnackbarDuration.Short
//                                                    )
//                                                } else {
//                                                    gioHangSanPham.SoLuong += 1
//                                                    gioHangViewModel.updateGioHang(gioHangSanPham)
//                                                    delay(500)
//                                                    snackbarHostState.showSnackbar(
//                                                        "Cập nhật số lượng sản phẩm trong giỏ hàng thành công.",
//                                                        duration = SnackbarDuration.Short
//                                                    )
//                                                }
//                                            } else {
//                                                val gioHangMoi = GioHang(
//                                                    MaGioHang = 0,
//                                                    MaKhachHang = makhachhang.toInt(),
//                                                    MaSanPham = sanPham.MaSanPham,
//                                                    SoLuong = 1,
//                                                    TrangThai = 1
//                                                )
//                                                gioHangViewModel.addToCart(gioHangMoi)
//                                                gioHangViewModel.getGioHangByKhachHang(makhachhang.toInt())
//                                                delay(500)
//                                                snackbarHostState.showSnackbar(
//                                                    "Thêm sản phẩm mới vào giỏ hàng thành công.",
//                                                    duration = SnackbarDuration.Short
//                                                )
//                                            }
//                                            isLoading = false
//                                        }
//                                    }
//                                },
//                                enabled = !isLoading,
//                                modifier = Modifier.weight(1f),
//                                shape = RoundedCornerShape(10.dp),
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = if (!isLoading) Color.LightGray else Color.LightGray
//                                )
//                            ) {
//                                Text(
//                                    "THÊM VÀO GIỎ HÀNG",
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 16.sp,
//                                    color = Color.Black,
//                                    modifier = Modifier.weight(1f),
//                                    textAlign = TextAlign.Center
//                                )
//                            }
//
//                            Button(
//                                onClick = { /* TODO: Xử lý mua ngay */ },
//                                modifier = Modifier.weight(1f),
//                                shape = RoundedCornerShape(10.dp),
//                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
//                            ) {
//                                Text(
//                                    "MUA NGAY",
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 18.sp,
//                                    color = Color.White
//                                )
//                            }
//                        }
//
//                        Spacer(modifier = Modifier.height(8.dp))
//
//                        SnackbarHost(
//                            modifier = Modifier.padding(4.dp),
//                            hostState = snackbarHostState
//                        )
//
//                        Spacer(modifier = Modifier.height(12.dp))
//
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(3.dp)
//                                .background(
//                                    brush = Brush.horizontalGradient(
//                                        colors = listOf(Color.Transparent, Color(0xFF9C27B0), Color.Transparent)
//                                    )
//                                )
//                        )
//
//                        Spacer(modifier = Modifier.height(12.dp))
//                    }
//                }
////                // Mô tả sản phẩm
//                item {
//                    Column(
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(
//                            text = "Mô tả sản phẩm",
//                            fontSize = 22.sp,
//                            textAlign = TextAlign.Justify,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                        Text(
//                            text = sanPham!!.MoTa,
//                            fontSize = 17.sp,
//                            textAlign = TextAlign.Justify,
//                            lineHeight = 28.sp,
//                            modifier = Modifier.padding(top = 8.dp)
//                        )
//                    }
//                }
//                item {
//                    Spacer(modifier = Modifier.height(12.dp))
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(3.dp)
//                            .background(
//                                brush = Brush.horizontalGradient(
//                                    colors = listOf(Color.Transparent, Color(0xFF9C27B0), Color.Transparent)
//                                )
//                            )
//                    )
//                    Spacer(modifier = Modifier.height(12.dp))
//                }
//                // Thông số kỹ thuật
//                item {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(12.dp))  // Thêm đoạn này
//                            .background(Color(0xFF1B263B))
//                    ) {
//                        Text(
//                            text = "Thông số kỹ thuật",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.SemiBold,
//                            color = Color(0xFFF5C518),
//                            modifier = Modifier
//                                .align(Alignment.CenterHorizontally)
//                                .padding(top = 16.dp, bottom = 12.dp)
//                        )
//                        // Dòng thông số
//                        ThongSoRow("CPU", sanPham!!.CPU)
//                        ThongSoRow("Card đồ họa", sanPham.CardManHinh)
//                        ThongSoRow("RAM", sanPham.RAM)
//                        ThongSoRow("SSD", sanPham.SSD)
//                        ThongSoRow("Màn Hình", sanPham.ManHinh)
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//fun formatGiaTien(gia: Int): String {
//    val formatter = DecimalFormat("#,###")
//    return "${formatter.format(gia)}đ"
//}

package com.example.lapstore.views

import BinhLuanDanhGia
import BinhLuanDanhGiaViewModel
import HinhAnhViewModel
import SanPhamViewModel
import android.icu.text.DecimalFormat
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lapstore.R
import com.example.lapstore.models.GioHang
import com.example.lapstore.viewmodels.GioHangViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetail_Screen(
    navController: NavHostController,
    id: String,
    makhachhang: String?,
    tentaikhoan: String?,
    viewModel: SanPhamViewModel,
    hinhAnhViewModel: HinhAnhViewModel,
) {

    Log.d("fdf", id.toString())
    Log.d("fdf", makhachhang.toString())
    Log.d("fdf", tentaikhoan.toString())
    var isFocused by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    var gioHangViewModel: GioHangViewModel = viewModel()

    val danhSachHinhAnh = hinhAnhViewModel.danhsachhinhanhtheosanpham
    val danhsachgiohang = gioHangViewModel.listGioHang
    val danhsachsanpham = viewModel.danhSachAllSanPham

    viewModel.getAllSanPham()

    val sanPham = viewModel.sanPham

    var snackbarHostState = remember {
        SnackbarHostState()
    }


    var scope = rememberCoroutineScope()

    var hinhAnhHienTai by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isFocused) {
        if (isFocused) {
            if (tentaikhoan != null && makhachhang != null)
                navController.navigate("${NavRoute.SEARCHSCREEN.route}?makhachhang=${makhachhang}&tentaikhoan=${tentaikhoan}")
            else
                navController.navigate(NavRoute.SEARCHSCREEN.route)
        }
    }

    if (makhachhang != null) {
        LaunchedEffect(makhachhang) {
            gioHangViewModel.getGioHangByKhachHang(makhachhang.toInt())
        }
    }

    Log.d("fdf", danhsachgiohang.toString())

    LaunchedEffect(id) {
        if (id.isNotEmpty()) {
            viewModel.getSanPhamById(id)
            hinhAnhViewModel.getHinhAnhTheoSanPham(id.toInt())
        }
    }
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Red, darkIcons = false)
    }


    LaunchedEffect(danhSachHinhAnh) {
        if (danhSachHinhAnh.isNotEmpty()) {
            hinhAnhHienTai = danhSachHinhAnh.first().DuongDan
        }
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
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
                            if (makhachhang == null && tentaikhoan == null) {
                                navController.navigate(NavRoute.LOGINSCREEN.route)
                            } else {
                                navController.navigate("${NavRoute.CART.route}?makhachhang=${makhachhang}&tentaikhoan=${tentaikhoan}")
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
                    // Search Bar
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        OutlinedTextField(
                            value = "",
                            onValueChange = {

                            },
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
    ) {
        if (sanPham == null || danhSachHinhAnh.isEmpty() || hinhAnhHienTai == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Đang tải dữ liệu...", fontSize = 18.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(17.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                // Hình ảnh sản phẩm chính
                item {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .border(
                                width = 1.5.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(12.dp)
                            )

                    ) {
                        AsyncImage(
                            model = hinhAnhHienTai,
                            contentDescription = "Hình ảnh sản phẩm",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Danh sách hình ảnh nhỏ trong LazyRow
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(danhSachHinhAnh) { hinhanh ->
                            Box(
                                modifier = Modifier
                                    .size(width = 100.dp, height = 90.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        width = 1.5.dp,
                                        color = if (hinhanh.DuongDan == hinhAnhHienTai) Color(0xFF6200EE) else Color.LightGray,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        hinhAnhHienTai = hinhanh.DuongDan
                                    }
                                    .background(Color.White)
                            ) {
                                AsyncImage(
                                    model = hinhanh.DuongDan,
                                    contentDescription = "Hình ảnh sản phẩm",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
                // Tên sản phẩm
                item {
                    Text(
                        text = sanPham!!.TenSanPham,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left,
                        lineHeight = 30.sp
                    )
                }

                // Giá sản phẩm
                item {

                    Text(
                        text = "Giá: ${formatGiaTien(sanPham.Gia)}",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left
                    )
                    if (sanPham.SoLuong == 0) {
                        Text(
                            text = "(Hết hàng)",
                            fontSize = 17.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
                // Nút thêm vào giỏ hàng
                item {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = {
                                    if (makhachhang == null) {
                                        navController.navigate(NavRoute.LOGINSCREEN.route)
                                    } else {
                                        isLoading = true
                                        scope.launch {
                                            gioHangViewModel.getGioHangByKhachHang(makhachhang.toInt())
                                            val gioHangHienTai = danhsachgiohang

                                            val gioHangSanPham = gioHangHienTai.find { it.MaSanPham == sanPham.MaSanPham }
                                            val sanPhamTonKho = danhsachsanpham.find { it.MaSanPham == sanPham.MaSanPham }

                                            if (sanPhamTonKho == null || sanPhamTonKho.SoLuong <= 0) {
                                                delay(500)
                                                snackbarHostState.showSnackbar(
                                                    "Sản phẩm đã hết hàng, không thể thêm vào giỏ hàng.",
                                                    duration = SnackbarDuration.Short
                                                )
                                            } else if (gioHangSanPham != null) {
                                                if (gioHangSanPham.SoLuong >= sanPhamTonKho.SoLuong) {
                                                    delay(500)
                                                    snackbarHostState.showSnackbar(
                                                        "Số lượng trong kho chỉ còn ${sanPhamTonKho.SoLuong} sản phẩm, không thể thêm nữa.",
                                                        duration = SnackbarDuration.Short
                                                    )
                                                } else {
                                                    gioHangSanPham.SoLuong += 1
                                                    gioHangViewModel.updateGioHang(gioHangSanPham)
                                                    delay(500)
                                                    snackbarHostState.showSnackbar(
                                                        "Cập nhật số lượng sản phẩm trong giỏ hàng thành công.",
                                                        duration = SnackbarDuration.Short
                                                    )
                                                }
                                            } else {
                                                val gioHangMoi = GioHang(
                                                    MaGioHang = 0,
                                                    MaKhachHang = makhachhang.toInt(),
                                                    MaSanPham = sanPham.MaSanPham,
                                                    SoLuong = 1,
                                                    TrangThai = 1
                                                )
                                                gioHangViewModel.addToCart(gioHangMoi)
                                                gioHangViewModel.getGioHangByKhachHang(makhachhang.toInt())
                                                delay(500)
                                                snackbarHostState.showSnackbar(
                                                    "Thêm sản phẩm mới vào giỏ hàng thành công.",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                            isLoading = false
                                        }
                                    }
                                },
                                enabled = !isLoading,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (!isLoading) Color.LightGray else Color.LightGray
                                )
                            ) {
                                Text(
                                    "THÊM VÀO GIỎ HÀNG",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                            }

                            Button(
                                onClick = { /* TODO: Xử lý mua ngay */ },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text(
                                    "MUA NGAY",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        SnackbarHost(
                            modifier = Modifier.padding(4.dp),
                            hostState = snackbarHostState
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(Color.Transparent, Color(0xFF9C27B0), Color.Transparent)
                                    )
                                )
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                // Mô tả sản phẩm
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Mô tả sản phẩm",
                            fontSize = 22.sp,
                            textAlign = TextAlign.Justify,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = sanPham!!.MoTa,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Justify,
                            lineHeight = 28.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color.Transparent, Color(0xFF9C27B0), Color.Transparent)
                                )
                            )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                // Thông số kỹ thuật
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1B263B))
                    ) {
                        Text(
                            text = "Thông số kỹ thuật",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFF5C518),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 16.dp, bottom = 12.dp)
                        )
                        ThongSoRow("CPU", sanPham!!.CPU)
                        ThongSoRow("Card đồ họa", sanPham.CardManHinh)
                        ThongSoRow("RAM", sanPham.RAM)
                        ThongSoRow("SSD", sanPham.SSD)
                        ThongSoRow("Màn Hình", sanPham.ManHinh)
                    }
                }

                // Bình luận & đánh giá
                item {
                    ProductCommentSection(
                        masp = sanPham!!.MaSanPham.toString(),
                        makhachhang = makhachhang,
                        tentaikhoan = tentaikhoan
                    )
                }
            }
        }
    }
}

fun formatGiaTien(gia: Int): String {
    val formatter = DecimalFormat("#,###")
    return "${formatter.format(gia)}đ"
}

@Composable
fun ProductCommentSection(
    masp: String,
    makhachhang: String?,
    tentaikhoan: String?,
    viewModel: BinhLuanDanhGiaViewModel = viewModel()
) {
    val binhLuanList by viewModel.list.observeAsState(initial = emptyList())
    val message by viewModel.message.observeAsState(initial = "")

    var noiDung by remember { mutableStateOf("") }
    var soSao by remember { mutableStateOf(5) }

    LaunchedEffect(masp) {
        viewModel.fetchAll()
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(
            "Bình luận & Đánh giá",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 260.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp)
        ) {
            items(binhLuanList.filter { it.MaSanPham == masp }.reversed()) { binhLuan ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        Modifier
                            .padding(12.dp)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = binhLuan.MaKhachHang,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Row {
                                repeat(binhLuan.SoSao) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFA000))
                                }
                                repeat(5 - binhLuan.SoSao) {
                                    Icon(Icons.Default.StarBorder, contentDescription = null, tint = Color.Gray)
                                }
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(text = binhLuan.NoiDung, style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Ngày: ${binhLuan.NgayDanhGia}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Divider(thickness = 1.dp)
        Spacer(Modifier.height(12.dp))

        Text("Viết bình luận của bạn", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = noiDung,
                onValueChange = { noiDung = it },
                label = { Text("Nội dung") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 4,
                shape = RoundedCornerShape(12.dp)
            )

            // Chọn số sao
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Đánh giá:", modifier = Modifier.padding(end = 8.dp))
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= soSao) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = "$i sao",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { soSao = i },
                        tint = if (i <= soSao) Color(0xFFFFA000) else Color.Gray
                    )
                }
            }

            Button(
                onClick = {
                    if (noiDung.isBlank() || makhachhang.isNullOrBlank()) return@Button

                    val newBinhLuan = BinhLuanDanhGia(
                        MaBinhLuan = null,      // Để null khi tạo mới, backend tự sinh
                        MaKhachHang = makhachhang,
                        MaSanPham = masp,
                        MaHoaDonBan = null,     // Nếu không cần, để null
                        SoSao = soSao,
                        NoiDung = noiDung,
                        NgayDanhGia = LocalDate.now().toString(),
                        TrangThai = "1"
                    )
                    //Log.d("DEBUG", Gson().toJson(newBinhLuan))
                    Log.d("DEBUG", newBinhLuan.toString())
                    viewModel.add(newBinhLuan)
                    noiDung = ""
                    soSao = 5
                },
                enabled = !makhachhang.isNullOrBlank() && noiDung.isNotBlank(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Gửi")
            }
        }

        if (makhachhang.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Bạn cần đăng nhập để bình luận!",
                color = Color.Red,
                fontSize = 13.sp,
                fontStyle = FontStyle.Italic
            )
        }

        if (message.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                color = Color.Blue,
                fontSize = 13.sp
            )
        }
    }
}


