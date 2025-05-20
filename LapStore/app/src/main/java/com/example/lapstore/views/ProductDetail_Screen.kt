package com.example.lapstore.views

import HinhAnhViewModel
import SanPhamViewModel
import android.icu.text.DecimalFormat
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lapstore.models.GioHang
import com.example.lapstore.models.SanPham
import com.example.lapstore.models.TaiKhoan
import com.example.lapstore.viewmodels.GioHangViewModel
import com.example.lapstore.viewmodels.TaiKhoanViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
                    AsyncImage(
                        model = hinhAnhHienTai,
                        contentDescription = "Hình ảnh sản phẩm",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                // Danh sách hình ảnh nhỏ trong LazyRow
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(danhSachHinhAnh) { hinhanh ->
                            AsyncImage(
                                model = hinhanh.DuongDan,
                                contentDescription = "Hình ảnh sản phẩm",
                                modifier = Modifier
                                    .height(90.dp)
                                    .width(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        hinhAnhHienTai = hinhanh.DuongDan
                                    },
                                contentScale = ContentScale.Crop,
                            )
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
                        color = Color.Red,
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
                    Button(
                        onClick = {
                            if (makhachhang == null) {
                                // Điều hướng tới màn hình đăng nhập nếu khách hàng chưa đăng nhập
                                navController.navigate(NavRoute.LOGINSCREEN.route)
                            } else {
                                isLoading = true // Bắt đầu hiệu ứng loading
                                scope.launch {
                                    gioHangViewModel.getGioHangByKhachHang(makhachhang.toInt())
                                    val gioHangHienTai = danhsachgiohang

                                    // Kiểm tra sản phẩm đã có trong giỏ hàng hay chưa
                                    val gioHangSanPham =
                                        gioHangHienTai.find { it.MaSanPham == sanPham.MaSanPham }

                                    val sanPhamTonKho =
                                        danhsachsanpham.find { it.MaSanPham == sanPham.MaSanPham }

                                    // Đảm bảo vòng loading kết thúc trước khi hiện snackbar
                                    if (sanPhamTonKho == null || sanPhamTonKho.SoLuong <= 0) {
                                        // Trường hợp sản phẩm hết hàng
                                        delay(500) // Hiệu ứng loading trong 1 giây
                                        snackbarHostState.showSnackbar(
                                            "Sản phẩm đã hết hàng, không thể thêm vào giỏ hàng.",
                                            duration = SnackbarDuration.Short
                                        )
                                    } else if (gioHangSanPham != null) {
                                        // Nếu sản phẩm đã có trong giỏ hàng, kiểm tra số lượng
                                        if (gioHangSanPham.SoLuong >= sanPhamTonKho.SoLuong) {
                                            // Đạt giới hạn kho
                                            delay(500) // Hiệu ứng loading trong 1 giây
                                            snackbarHostState.showSnackbar(
                                                "Số lượng trong kho chỉ còn ${sanPhamTonKho.SoLuong} sản phẩm, không thể thêm nữa.",
                                                duration = SnackbarDuration.Short
                                            )
                                        } else {
                                            // Tăng số lượng sản phẩm trong giỏ hàng
                                            gioHangSanPham.SoLuong += 1
                                            gioHangViewModel.updateGioHang(gioHangSanPham)
                                            delay(500) // Hiệu ứng loading trong 1 giây
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
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isLoading) Color(0XFF27A4F2) else Color.Gray // Màu nút thay đổi khi bị vô hiệu hóa
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AddShoppingCart,
                                contentDescription = "",
                                tint = Color.White
                            )
                            Text(
                                "THÊM VÀO GIỎ HÀNG",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
                // Nút mua ngay
                item {
                    Button(
                        onClick = { /* TODO: Xử lý mua ngay */ },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        )
                    ) {
                        Text(
                            "MUA NGAY",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
                item {
                    SnackbarHost(
                        modifier = Modifier.padding(4.dp),
                        hostState = snackbarHostState
                    )

                }
                item {
                    HorizontalDivider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
//                // Mô tả sản phẩm
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
                            fontSize = 20.sp,
                            textAlign = TextAlign.Justify,
                            lineHeight = 28.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
                item {
                    HorizontalDivider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
//                // Thông số kỹ thuật
                item {
                    Text(
                        text = "Thông số kỹ thuật",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("CPU: ${sanPham!!.CPU}", fontWeight = FontWeight.Bold)
                        Text("Card đồ họa: ${sanPham!!.CardManHinh}", fontWeight = FontWeight.Bold)
                        Text("${sanPham!!.RAM}", fontWeight = FontWeight.Bold)
                        Text("${sanPham!!.SSD}", fontWeight = FontWeight.Bold)
                        Text("Màn Hình: ${sanPham!!.ManHinh}", fontWeight = FontWeight.Bold)
                        Text("${sanPham!!.SSD}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}


fun formatGiaTien(gia: Int): String {
    val formatter = DecimalFormat("#,###")
    return "${formatter.format(gia)}đ"
}





