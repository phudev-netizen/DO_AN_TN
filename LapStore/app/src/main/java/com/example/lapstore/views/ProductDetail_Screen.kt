package com.example.lapstore.views

import BinhLuanDanhGia
import BinhLuanDanhGiaViewModel
import HinhAnhViewModel
import SanPhamViewModel
import android.icu.text.DecimalFormat
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lapstore.models.ChiTietHoaDonBan
import com.example.lapstore.models.GioHang
import com.example.lapstore.models.HoaDonBan
import com.example.lapstore.viewmodels.GioHangViewModel
import com.example.lapstore.viewmodels.KhachHangViewModel
import com.example.lapstore.viewmodels.KhuyenMaiViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetail_Screen(
    navController: NavHostController,
    id: String,
    makhachhang: String?,
    tentaikhoan: String?,
    viewModel: SanPhamViewModel,
    hinhAnhViewModel: HinhAnhViewModel,
    hoaDonBanList: List<HoaDonBan>,
    chiTietHoaDonBanList: List<ChiTietHoaDonBan>,
    khuyenMaiViewModel: KhuyenMaiViewModel = viewModel()
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

    val soluong = remember { mutableIntStateOf(1) }


    val cartItemCount by remember { derivedStateOf { gioHangViewModel.listGioHang.sumOf { it.SoLuong } } }

    LaunchedEffect(makhachhang) {
        if (!makhachhang.isNullOrEmpty()) {
            gioHangViewModel.getGioHangByKhachHang(makhachhang.toInt())
        }
    }


    var snackbarHostState = remember {
        SnackbarHostState()
    }

    var scope = rememberCoroutineScope()

    var hinhAnhHienTai by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(danhSachHinhAnh) {
        if (danhSachHinhAnh.isNotEmpty()) {
            hinhAnhHienTai = danhSachHinhAnh.first().DuongDan
        }
    }

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

    val khuyenMaiViewModel: KhuyenMaiViewModel = viewModel()
    val khuyenMai = khuyenMaiViewModel.khuyenMai.value

    if (sanPham != null) {
        LaunchedEffect(sanPham.MaSanPham) {
            khuyenMaiViewModel.fetchKhuyenMai(sanPham.MaSanPham)
        }
    }
    if (khuyenMai != null) {
        Text("Khuyến mãi: ${khuyenMai.TenKhuyenMai}")

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
                    Column(modifier = Modifier.fillMaxWidth()) {

                        // Nếu có khuyến mãi, hiển thị giá gốc có gạch ngang
                        if (khuyenMai != null) {
                            Text(
                                text = "Giá gốc: ${formatGiaTien(sanPham.Gia)}",
                                fontSize = 18.sp,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )

                            Text(
                                text = "Giảm ${khuyenMai.PhanTramGiam}%",
                                color = Color.Red,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = "Giá sau giảm: ${formatGiaTien((sanPham.Gia * (100 - khuyenMai.PhanTramGiam)) / 100)}",
                                color = Color.Red,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            // Nếu không có khuyến mãi
                            Text(
                                text = "Giá: ${formatGiaTien(sanPham.Gia)}",
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    // Hiển thị khuyến mãi (nên đặt ở đây)
                    khuyenMai?.let { km ->
                        Card(
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Khuyến mãi: ${km.TenKhuyenMai}")
                                Text("Giảm giá: ${km.PhanTramGiam}%")
                                Text("Từ ${km.NgayBatDau} đến ${km.NgayKetThuc}")
                            }
                        }
                    }
                    // Hiển thị ngày hết hạn khuyến mãi nếu có
                    if (khuyenMai != null) {
                        val currentDate = LocalDate.now()
                        val endDate = LocalDate.parse(khuyenMai.NgayKetThuc, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        if (currentDate.isAfter(endDate)) {
                            Text(
                                text = "Khuyến mãi đã kết thúc",
                                color = Color.Red,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                text = "Khuyến mãi còn hiệu lực đến: ${khuyenMai.NgayKetThuc}",
                                color = Color.Green,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
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
                // Nút thêm vào giỏ hàng và mua ngay
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
                            Spacer(modifier = Modifier.width(8.dp))

                            // Nút MUA NGAY
                            Button(
                                onClick = {
                                    if (makhachhang.isNullOrBlank() || tentaikhoan.isNullOrBlank()) {
                                        navController.navigate(NavRoute.LOGINSCREEN.route)
                                    } else {
                                        isLoading = true
                                        scope.launch {
                                            val sanPhamTonKho = danhsachsanpham.find { it.MaSanPham == sanPham.MaSanPham }
                                            if (sanPhamTonKho == null || sanPhamTonKho.SoLuong <= 0) {
                                                delay(500)
                                                snackbarHostState.showSnackbar(
                                                    "Sản phẩm đã hết hàng, không thể mua ngay.",
                                                    duration = SnackbarDuration.Short
                                                )
                                            } else {
                                                // TÍNH GIÁ SAU GIẢM
                                                val giaSauGiam = if (khuyenMai != null)
                                                    (sanPham.Gia * (100 - khuyenMai.PhanTramGiam)) / 100
                                                else
                                                    sanPham.Gia
                                                // Kiểm tra số lượng mua
                                                val tongTien  = giaSauGiam * soluong.value

                                                // CHỈ TRUYỀN BA TRƯỜNG: mã sản phẩm, số lượng, mã giỏ hàng (0 nếu không có)
//                                                val rawSelectedProducts = "${sanPham.MaSanPham},1,0"
                                                val soLuongMua = soluong.value
                                                val rawSelectedProducts = "${sanPham.MaSanPham},${soluong.value},0"
                                                val encodedSelectedProducts = Uri.encode(rawSelectedProducts)
                                                val encodedTenTaiKhoan = Uri.encode(tentaikhoan)
                                                val encodedImage = Uri.encode(hinhAnhHienTai ?: "")

                                                navController.navigate(
                                                    "${NavRoute.PAYSCREEN.route}?" +
                                                            "selectedProducts=$encodedSelectedProducts" +
                                                            "&tongtien=$giaSauGiam" + // giá đã giảm
                                                            "&tentaikhoan=$encodedTenTaiKhoan" +
                                                            "&makhachhang=$makhachhang" +
                                                            "&hinhAnhHienTai=$encodedImage" +
                                                            "&tensanpham=${Uri.encode(sanPham.TenSanPham)}"
                                                )
                                            }
                                            isLoading = false
                                        }
                                    }
                                },
                                enabled = !isLoading,
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
                // Khoảng cách giữa mô tả và thông số kỹ thuật
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
                        tentaikhoan = tentaikhoan,
                        hoaDonBanList = hoaDonBanList,
                        chiTietHoaDonBanList = chiTietHoaDonBanList
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
    hoaDonBanList: List<HoaDonBan>,
    chiTietHoaDonBanList: List<ChiTietHoaDonBan>,
    viewModel: BinhLuanDanhGiaViewModel = viewModel(),
    khachHangViewModel: KhachHangViewModel = viewModel(),

) {
    val binhLuanList by viewModel.list.observeAsState(initial = emptyList())
    val message by viewModel.message.observeAsState(initial = "")

    val isAdminUser = (tentaikhoan == "admin")
    val listToShow = if (isAdminUser) binhLuanList else binhLuanList.filter { it.TrangThai == "1" }

    var noiDung by remember { mutableStateOf("") }
    var soSao by remember { mutableStateOf(5) }

    val hoaDonBanList = khachHangViewModel.hoaDonBanList
    val chiTietHoaDonBanList = khachHangViewModel.chiTietHoaDonBanList

    val daMuaHang = remember(hoaDonBanList, chiTietHoaDonBanList, makhachhang, masp) {
        val mkh = makhachhang?.trim()
        if (mkh.isNullOrBlank()) {
            false
        } else {
            val hoaDons = hoaDonBanList.filter { it.MaKhachHang.toString() == mkh }
            val hoaDonIds = hoaDons.map { it.MaHoaDonBan }
            val found = chiTietHoaDonBanList.any {
                it.MaSanPham.toString() == masp && hoaDonIds.contains(it.MaHoaDonBan)
            }
            found
        }
    }

    LaunchedEffect(Unit) {
        khachHangViewModel.getAllHoaDonBan()
        khachHangViewModel.getAllChiTietHoaDonBan()
    }

    // Hiển thị loading nếu chưa có data
    if (hoaDonBanList.isEmpty() || chiTietHoaDonBanList.isEmpty()) {
        CircularProgressIndicator()
        return
    }

    LaunchedEffect(masp) { viewModel.fetchAll() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Bình luận & Đánh giá",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        RatingSummarySection(listToShow.filter { it.MaSanPham == masp })
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 280.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp)
        ) {
            items(listToShow.filter { it.MaSanPham == masp }.reversed()) { binhLuan ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(3.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = binhLuan.MaKhachHang,
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                val ngayGioFormatted = try {
                                    val dateTime = LocalDateTime.parse(binhLuan.NgayDanhGia)
                                    dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                                } catch (_: Exception) {
                                    binhLuan.NgayDanhGia
                                }
                                Text(
                                    "Ngày: $ngayGioFormatted",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }
                            Row {
                                repeat(binhLuan.SoSao) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFA000), modifier = Modifier.size(18.dp))
                                }
                                repeat(5 - binhLuan.SoSao) {
                                    Icon(Icons.Default.StarBorder, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = binhLuan.NoiDung,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (isAdminUser) {
                            Spacer(modifier = Modifier.height(6.dp))
                            if (binhLuan.TrangThai != "1") {
                                Text(
                                    "Đã bị ẩn",
                                    color = Color.Red,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = {
                                    val newTrangThai = if (binhLuan.TrangThai == "1") "0" else "1"
                                    viewModel.hideOrShow(binhLuan.MaBinhLuan ?: 0, newTrangThai)
                                }) {
                                    Icon(
                                        imageVector = if (binhLuan.TrangThai == "1") Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )
                                }
                                IconButton(onClick = {
                                    viewModel.delete(binhLuan.MaBinhLuan?.toString() ?: "")
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Xóa", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Divider(thickness = 1.dp)
        Spacer(Modifier.height(12.dp))

        if (!isAdminUser) {
            if (daMuaHang) {
                // Nếu đã mua hàng thì hiện khung nhập bình luận
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

                            // Lấy danh sách hóa đơn của khách hàng
                            val hoaDons = hoaDonBanList.filter { it.MaKhachHang.toString() == makhachhang }
                            // Lấy danh sách mã hóa đơn
                            val hoaDonIds = hoaDons.map { it.MaHoaDonBan }
                            // Tìm mã hóa đơn đầu tiên đã mua sản phẩm này
                            val maHoaDonBan = chiTietHoaDonBanList.firstOrNull {
                                it.MaSanPham.toString() == masp && hoaDonIds.contains(it.MaHoaDonBan)
                            }?.MaHoaDonBan

                            if (maHoaDonBan == null) {
                                // Không tìm thấy hóa đơn => không cho gửi
                                return@Button
                            }

                            val newBinhLuan = BinhLuanDanhGia(
                                MaBinhLuan = null,
                                MaKhachHang = makhachhang,
                                MaSanPham = masp,
                                MaHoaDonBan = maHoaDonBan, // Sửa ở đây!
                                SoSao = soSao,
                                NoiDung = noiDung,
                                NgayDanhGia = LocalDateTime.now().toString(),
                                TrangThai = "1"
                            )
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
            } else {
                // Nếu chưa mua thì hiện cảnh báo
                Text(
                    "Bạn cần mua sản phẩm này để bình luận!",
                    color = Color.Red,
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}
@Composable
fun RatingSummarySection(binhLuanList: List<BinhLuanDanhGia>) {
    if (binhLuanList.isEmpty()) return

    val filteredList = binhLuanList.filter { it.TrangThai == "1" }
    val totalReviews = filteredList.size

    if (totalReviews == 0) return

    val ratingCounts = (1..5).associateWith { star ->
        filteredList.count { it.SoSao == star }
    }

    val averageRating = filteredList.sumOf { it.SoSao }.toFloat() / totalReviews

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "⭐ Trung bình: %.1f ★".format(averageRating),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "Tổng số đánh giá: $totalReviews",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Hiển thị mỗi dòng sao (5★ đến 1★)
        for (i in 5 downTo 1) {
            val count = ratingCounts[i] ?: 0
            val percentage = if (totalReviews != 0) count / totalReviews.toFloat() else 0f

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                Text("$i ★", modifier = Modifier.width(24.dp))
                LinearProgressIndicator(
                    progress = percentage,
                    modifier = Modifier
                        .height(10.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp)),
                    color = Color(0xFFFFA000),
                    trackColor = Color.LightGray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("$count")
            }
        }
    }
}