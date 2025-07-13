package com.example.lapstore.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.lapstore.viewmodels.HoaDonBanVỉewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)
    }

    var selectedTabIndexItem by remember { mutableStateOf(0) }
    val tabs = listOf(
        "Chờ xác nhận",
        "Chờ lấy hàng",
        "Chờ giao hàng",
        "Đã giao",
        "Chờ xác nhận hủy",
        "Đã hủy",
        "Trả hàng"
    )
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
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "",
                            tint = Color.Red
                        )
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
                    0 -> DuyetDonChoXacNhan(navController)
                    1 -> DuyetChoLayHangScreen(navController)
                    2 -> DuyetChoGiaoHangScreen(navController)
                    3 -> DuyetDaGiaoHangScreen(navController)
                    4 -> DuyetChoXacNhanHuy(navController)
                    5 -> DuyetHuyDonHangScreen(navController)
                    6 -> DonHangTraHangScreen(navController)
                }
            }
        }
    }
}

@Composable
fun DuyetHuyDonHangScreen(navController: NavHostController) {
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    val danhSachHoaDonChoXacNhan by hoaDonBanViewModel.danhSachHoaDonTheoTrangThai.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Hàm gọi API để lấy danh sách hóa đơn

    val soluonghoadonchoxacnhan = remember { mutableStateOf(danhSachHoaDonChoXacNhan.count()) }

    LaunchedEffect(danhSachHoaDonChoXacNhan) {
        soluonghoadonchoxacnhan.value = danhSachHoaDonChoXacNhan.count()
    }

    isLoading.value = true // Bắt đầu tải dữ liệu
    errorMessage.value = null
    try {
        hoaDonBanViewModel.getHoaDonTheoTrangThai(6)
    } catch (e: Exception) {
        errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
    } finally {
        isLoading.value = false // Kết thúc tải dữ liệu
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
//                        CardDonHangAdmin(navController,hoadon,6,hoaDonBanViewModel)
                        CardDonHangAdmin(navController, hoadon, hoaDonBanViewModel)

                    }
                }
            }
        }
    }
}

@Composable
fun DuyetChoXacNhanHuy(navController: NavHostController) {
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    val danhSachHoaDonChoXacNhan by hoaDonBanViewModel.danhSachHoaDonTheoTrangThai.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Hàm gọi API để lấy danh sách hóa đơn

    val soluonghoadonchoxacnhan = remember { mutableStateOf(danhSachHoaDonChoXacNhan.count()) }

    LaunchedEffect(danhSachHoaDonChoXacNhan) {
        soluonghoadonchoxacnhan.value = danhSachHoaDonChoXacNhan.count()
    }

    isLoading.value = true // Bắt đầu tải dữ liệu
    errorMessage.value = null


    try {
        hoaDonBanViewModel.getHoaDonTheoTrangThai(5)
    } catch (e: Exception) {
        errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
    } finally {
        isLoading.value = false // Kết thúc tải dữ liệu
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
//                        CardDonHangAdmin(navController,hoadon,5,hoaDonBanViewModel)
                        CardDonHangAdmin(navController, hoadon, hoaDonBanViewModel)

                    }
                }
            }
        }
    }
}

@Composable
fun DuyetDaGiaoHangScreen(navController: NavHostController) {
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    val danhSachHoaDonChoXacNhan by hoaDonBanViewModel.danhSachHoaDonTheoTrangThai.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Hàm gọi API để lấy danh sách hóa đơn

    val soluonghoadonchoxacnhan = remember { mutableStateOf(danhSachHoaDonChoXacNhan.count()) }

    LaunchedEffect(danhSachHoaDonChoXacNhan) {
        soluonghoadonchoxacnhan.value = danhSachHoaDonChoXacNhan.count()
    }

    isLoading.value = true // Bắt đầu tải dữ liệu
    errorMessage.value = null
    try {
        hoaDonBanViewModel.getHoaDonTheoTrangThai(4)
    } catch (e: Exception) {
        errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
    } finally {
        isLoading.value = false // Kết thúc tải dữ liệu
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
                        //CardDonHangAdmin(navController,hoadon,4,hoaDonBanViewModel)
                        CardDonHangAdmin(navController, hoadon, hoaDonBanViewModel)

                    }
                }
            }
        }
    }
}

@Composable
fun DuyetChoGiaoHangScreen(navController: NavHostController) {
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    val danhSachHoaDonChoXacNhan by hoaDonBanViewModel.danhSachHoaDonTheoTrangThai.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Hàm gọi API để lấy danh sách hóa đơn

    val soluonghoadonchoxacnhan = remember { mutableStateOf(danhSachHoaDonChoXacNhan.count()) }

    LaunchedEffect(danhSachHoaDonChoXacNhan) {
        soluonghoadonchoxacnhan.value = danhSachHoaDonChoXacNhan.count()
    }

    isLoading.value = true // Bắt đầu tải dữ liệu
    errorMessage.value = null
    try {
        hoaDonBanViewModel.getHoaDonTheoTrangThai(3)
    } catch (e: Exception) {
        errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
    } finally {
        isLoading.value = false // Kết thúc tải dữ liệu
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
//                        CardDonHangAdmin(navController,hoadon,3,hoaDonBanViewModel)
                        CardDonHangAdmin(navController, hoadon, hoaDonBanViewModel)

                    }
                }
            }
        }
    }
}

@Composable
fun DuyetChoLayHangScreen(navController: NavHostController) {
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    val danhSachHoaDonChoXacNhan by hoaDonBanViewModel.danhSachHoaDonTheoTrangThai.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }

    val soluonghoadonchoxacnhan = remember { mutableStateOf(danhSachHoaDonChoXacNhan.count()) }

    LaunchedEffect(danhSachHoaDonChoXacNhan) {
        soluonghoadonchoxacnhan.value = danhSachHoaDonChoXacNhan.count()
    }

    isLoading.value = true // Bắt đầu tải dữ liệu
    errorMessage.value = null
    try {
        hoaDonBanViewModel.getHoaDonTheoTrangThai(2)
    } catch (e: Exception) {
        errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
    } finally {
        isLoading.value = false // Kết thúc tải dữ liệu
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
                            "Số lượng đơn hàng(${soluonghoadonchoxacnhan.value})",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    items(danhSachHoaDonChoXacNhan) { hoadon ->
//                        CardDonHangAdmin(navController,hoadon,2,hoaDonBanViewModel)
                        CardDonHangAdmin(navController, hoadon, hoaDonBanViewModel)

                    }
                }
            }
        }
    }
}

@Composable
fun DuyetDonChoXacNhan(navController: NavHostController) {
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    val danhSachHoaDonChoXacNhan by hoaDonBanViewModel.danhSachHoaDonTheoTrangThai.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Hàm gọi API để lấy danh sách hóa đơn
    val soluonghoadonchoxacnhan = remember { mutableStateOf(danhSachHoaDonChoXacNhan.count()) }

    hoaDonBanViewModel.getHoaDonTheoTrangThai(1)
    LaunchedEffect(danhSachHoaDonChoXacNhan) {
        soluonghoadonchoxacnhan.value = danhSachHoaDonChoXacNhan.count()
    }

    isLoading.value = true // Bắt đầu tải dữ liệu
    errorMessage.value = null
    try {
        hoaDonBanViewModel.getHoaDonTheoTrangThai(1)
    } catch (e: Exception) {
        errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
    } finally {
        isLoading.value = false // Kết thúc tải dữ liệu
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
//                        CardDonHangAdmin(navController,hoadon,1,hoaDonBanViewModel)
                        CardDonHangAdmin(navController, hoadon, hoaDonBanViewModel)

                    }
                }
            }
        }
    }
}

@Composable
fun DonHangTraHangScreen(navController: NavHostController) {
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    val danhSachHoaDonTraHang by hoaDonBanViewModel.danhSachHoaDonTheoTrangThai.collectAsState()

    // Trạng thái đang tải
    val isLoading = remember { mutableStateOf(false) }

    // Trạng thái lỗi (nếu có)
    val errorMessage = remember { mutableStateOf<String?>(null) }

    val soLuongHoaDonTraHang = remember { mutableStateOf(0) }

    // Gọi API lấy đơn trả hàng (TrangThai = 7)
    hoaDonBanViewModel.getHoaDonTheoTrangThai(7)

    LaunchedEffect(danhSachHoaDonTraHang) {
        soLuongHoaDonTraHang.value = danhSachHoaDonTraHang.count()
    }

    isLoading.value = true
    errorMessage.value = null
    try {
        hoaDonBanViewModel.getHoaDonTheoTrangThai(7)
    } catch (e: Exception) {
        errorMessage.value = "Lỗi khi tải dữ liệu: ${e.message}"
    } finally {
        isLoading.value = false
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

            danhSachHoaDonTraHang.isEmpty() -> {
                Text(
                    text = "Không có đơn hàng nào đang trả hàng.",
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
                            "Số lượng đơn trả hàng (${soLuongHoaDonTraHang.value})",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(danhSachHoaDonTraHang) { hoadon ->
                       // CardDonHangAdmin(navController, hoadon, 7, hoaDonBanViewModel)
                        CardDonHangAdmin(navController, hoadon, hoaDonBanViewModel)

                    }
                }
            }
        }
    }
}

