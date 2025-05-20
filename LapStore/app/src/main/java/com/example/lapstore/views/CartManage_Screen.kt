package com.example.lapstore.views

import CardDonHang
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lapstore.models.HoaDonBan
import com.example.lapstore.viewmodels.HoaDonBanVỉewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartManagementSection(navController: NavHostController, makhachhang: Int?) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)
    }

    var selectedTabIndexItem by remember { mutableStateOf(0) }
    val tabs = listOf("Chờ xác nhận", "Chờ lấy hàng", "Chờ giao hàng", "Đã giao", "Chờ xác nhận hủy","Đã hủy")
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text("Quản lý đơn hàng")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "", tint = Color.Red)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(3.dp),
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndexItem,
                edgePadding = 0.dp,
                modifier = Modifier.fillMaxWidth(),
                contentColor = Color.Red,
                containerColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndexItem]),
                        color = Color.Red // Đặt màu đỏ cho thanh di chuyển
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 4.dp)
                            .clip(shape = RectangleShape)
                            .clickable { selectedTabIndexItem = index }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (selectedTabIndexItem) {
                    0 -> ChoXacNhanScreen(navController,makhachhang)
                    1 -> ChoLayHangScreen(navController,makhachhang)
                    2 -> ChoGiaoHangScreen(navController,makhachhang)
                    3 -> DaGiaoHangScreen(navController,makhachhang)
                    4 -> ChoXacNhanHuy(navController,makhachhang)
                    5 -> HuyDonHangScreen(navController,makhachhang)
                }
            }
        }
    }
}

@Composable
fun DaGiaoHangScreen(navController: NavHostController, makhachhang: Int?) {
    // Lấy ViewModel
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    // Quan sát danh sách hóa đơn thông qua StateFlow
    val danhSachHoaDonBan by hoaDonBanViewModel.danhSachHoaDonCuaKhachHang.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }


    val soluonghoadondagiaohang = remember { mutableStateOf(danhSachHoaDonBan.count()) }

    LaunchedEffect(danhSachHoaDonBan) {
        soluonghoadondagiaohang.value = danhSachHoaDonBan.count()
    }
    // Hàm gọi API để lấy danh sách hóa đơn

    if (makhachhang != null) {
        isLoading.value = true // Bắt đầu tải dữ liệu
        errorMessage.value = null
        try {
            hoaDonBanViewModel.getHoaDonTheoKhachHang(
                makhachhang,
                4
            )
        } catch (e: Exception) {
            errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
        } finally {
            isLoading.value = false // Kết thúc tải dữ liệu
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        when {
            isLoading.value -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            errorMessage.value != null -> {
                Text(
                    text = errorMessage.value ?: "Đã xảy ra lỗi",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            danhSachHoaDonBan.isEmpty() -> {
                Text(
                    text = "Không có hóa đơn nào đã giao.",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    item {
                        Text(
                            "Số luượng đơn hàng(${soluonghoadondagiaohang.value})",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(danhSachHoaDonBan) { hoadon ->
                        CardDonHang(navController,hoadon, false)
                    }
                }
            }
        }
    }
}

@Composable
fun ChoGiaoHangScreen(navController: NavHostController, makhachhang: Int?) {
    // Lấy ViewModel
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    // Quan sát danh sách hóa đơn thông qua StateFlow
    val danhSachHoaDonBan by hoaDonBanViewModel.danhSachHoaDonCuaKhachHang.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Hàm gọi API để lấy danh sách hóa đơn

    val soluonghoadonchogiaohang = remember { mutableStateOf(danhSachHoaDonBan.count()) }

    LaunchedEffect(danhSachHoaDonBan) {
        soluonghoadonchogiaohang.value = danhSachHoaDonBan.count()
    }

    if (makhachhang != null) {
        isLoading.value = true // Bắt đầu tải dữ liệu
        errorMessage.value = null
        try {
            hoaDonBanViewModel.getHoaDonTheoKhachHang(
                makhachhang,
                3
            )
        } catch (e: Exception) {
            errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
        } finally {
            isLoading.value = false // Kết thúc tải dữ liệu
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        when {
            isLoading.value -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            errorMessage.value != null -> {
                Text(
                    text = errorMessage.value ?: "Đã xảy ra lỗi",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            danhSachHoaDonBan.isEmpty() -> {
                Text(
                    text = "Không có hóa đơn nào đang chờ giao.",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    item {
                        Text(
                            "Số luượng đơn hàng(${soluonghoadonchogiaohang.value})",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    items(danhSachHoaDonBan) { hoadon ->
                        CardDonHang(navController,hoadon, false)
                    }
                }
            }
        }
    }
}

@Composable
fun ChoXacNhanHuy(navController: NavHostController, makhachhang: Int?) {
    // Lấy ViewModel
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    // Quan sát danh sách hóa đơn thông qua StateFlow
    val danhSachHoaDonBan by hoaDonBanViewModel.danhSachHoaDonCuaKhachHang.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Hàm gọi API để lấy danh sách hóa đơn

    val soluonghoadonchoxacnhanhuy = remember { mutableStateOf(danhSachHoaDonBan.count()) }

    LaunchedEffect(danhSachHoaDonBan) {
        soluonghoadonchoxacnhanhuy.value = danhSachHoaDonBan.count()
    }

    if (makhachhang != null) {
        isLoading.value = true // Bắt đầu tải dữ liệu
        errorMessage.value = null
        try {
            hoaDonBanViewModel.getHoaDonTheoKhachHang(
                makhachhang,
                5
            )
        } catch (e: Exception) {
            errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
        } finally {
            isLoading.value = false // Kết thúc tải dữ liệu
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        when {
            isLoading.value -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            errorMessage.value != null -> {
                Text(
                    text = errorMessage.value ?: "Đã xảy ra lỗi",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            danhSachHoaDonBan.isEmpty() -> {
                Text(
                    text = "Không có hóa đơn nào đang chờ xác nhận hủy.",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    item {
                        Text(
                            "Số luượng đơn hàng(${soluonghoadonchoxacnhanhuy.value})",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(danhSachHoaDonBan) { hoadon ->
                        CardDonHang(navController,hoadon, false)
                    }
                }
            }
        }
    }

}


@Composable
fun HuyDonHangScreen(navController: NavHostController,makhachhang: Int?) {
    // Lấy ViewModel
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    // Quan sát danh sách hóa đơn thông qua StateFlow
    val danhSachHoaDonBan by hoaDonBanViewModel.danhSachHoaDonCuaKhachHang.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }


    val soluonghoadondahuy = remember { mutableStateOf(danhSachHoaDonBan.count()) }

    LaunchedEffect(danhSachHoaDonBan) {
        soluonghoadondahuy.value = danhSachHoaDonBan.count()
    }
    // Hàm gọi API để lấy danh sách hóa đơn

    if (makhachhang != null) {
        isLoading.value = true // Bắt đầu tải dữ liệu
        errorMessage.value = null
        try {
            hoaDonBanViewModel.getHoaDonTheoKhachHang(
                makhachhang,
                6
            )
        } catch (e: Exception) {
            errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
        } finally {
            isLoading.value = false // Kết thúc tải dữ liệu
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        when {
            isLoading.value -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            errorMessage.value != null -> {
                Text(
                    text = errorMessage.value ?: "Đã xảy ra lỗi",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            danhSachHoaDonBan.isEmpty() -> {
                Text(
                    text = "Không có hóa đơn nào đã hủy.",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    item {
                        Text(
                            "Số luượng đơn hàng(${soluonghoadondahuy.value})",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(danhSachHoaDonBan) { hoadon ->
                        CardDonHang(navController,hoadon, false)
                    }
                }
            }
        }
    }
}


@Composable
fun ChoLayHangScreen(navController: NavHostController,makhachhang: Int?) {
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    // Quan sát danh sách hóa đơn thông qua StateFlow
    val danhSachHoaDonBan by hoaDonBanViewModel.danhSachHoaDonCuaKhachHang.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }

    val soluonghoadoncholayhang = remember { mutableStateOf(danhSachHoaDonBan.count()) }

    LaunchedEffect(danhSachHoaDonBan) {
        soluonghoadoncholayhang.value = danhSachHoaDonBan.count()
    }

    // Hàm gọi API để lấy danh sách hóa đơn

    if (makhachhang != null) {
        isLoading.value = true // Bắt đầu tải dữ liệu
        errorMessage.value = null
        try {
            hoaDonBanViewModel.getHoaDonTheoKhachHang(
                makhachhang,
                2
            )
        } catch (e: Exception) {
            errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
        } finally {
            isLoading.value = false // Kết thúc tải dữ liệu
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        when {
            isLoading.value -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            errorMessage.value != null -> {
                Text(
                    text = errorMessage.value ?: "Đã xảy ra lỗi",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            danhSachHoaDonBan.isEmpty() -> {
                Text(
                    text = "Không có hóa đơn nào đang chờ lấy hàng.",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    item {
                        Text(
                            "Số luượng đơn hàng(${soluonghoadoncholayhang.value})",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(danhSachHoaDonBan) { hoadon ->
                        CardDonHang(navController,hoadon, false)
                    }
                }
            }
        }
    }
}

@Composable
fun ChoXacNhanScreen(navController: NavHostController,makhachhang: Int?) {
    // Lấy ViewModel
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    // Quan sát danh sách hóa đơn thông qua StateFlow
    val danhSachHoaDonChoXacNhan by hoaDonBanViewModel.danhSachHoaDonCuaKhachHang.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Hàm gọi API để lấy danh sách hóa đơn

    val soluonghoadonchoxacnhan = remember { mutableStateOf(danhSachHoaDonChoXacNhan.count()) }

    LaunchedEffect(danhSachHoaDonChoXacNhan) {
        soluonghoadonchoxacnhan.value = danhSachHoaDonChoXacNhan.count()
    }

    if (makhachhang != null) {
        isLoading.value = true // Bắt đầu tải dữ liệu
        errorMessage.value = null
        try {
            hoaDonBanViewModel.getHoaDonTheoKhachHang(
                makhachhang,
                1 // Trạng thái "Chờ xác nhận"
            )
        } catch (e: Exception) {
            errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
        } finally {
            isLoading.value = false // Kết thúc tải dữ liệu
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        when {
            isLoading.value -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            errorMessage.value != null -> {
                Text(
                    text = errorMessage.value ?: "Đã xảy ra lỗi",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            danhSachHoaDonChoXacNhan.isEmpty() -> {
                Text(
                    text = "Không có hóa đơn nào đang chờ xác nhận.",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    item {
                        Text(
                            "Số luượng đơn hàng(${soluonghoadonchoxacnhan.value})",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(danhSachHoaDonChoXacNhan) { hoadon ->
                        CardDonHang(navController,hoadon, true)
                    }
                }
            }
        }
    }
}


@Composable
fun formatDate(inputDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Định dạng từ API
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Định dạng đầu ra
        val date = inputFormat.parse(inputDate)
        date?.let { outputFormat.format(it) } ?: "Ngày không hợp lệ"
    } catch (e: Exception) {
        "Ngày không hợp lệ"
    }
}