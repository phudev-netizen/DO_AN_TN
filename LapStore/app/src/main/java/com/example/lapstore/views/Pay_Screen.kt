import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.lapstore.R
import com.example.lapstore.models.ChiTietHoaDonBan
import com.example.lapstore.models.DiaChi
import com.example.lapstore.models.HoaDonBan
import com.example.lapstore.models.SanPham
import com.example.lapstore.ui.formatGiaTien
import com.example.lapstore.viewmodels.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayScreen(
//    navController: NavHostController,
//    selectedProducts: List<Triple<Int, Int, Int>>, // productId, quantity, cartId
//    tongtien: Int,
//    tentaikhoan: String,
    navController: NavHostController,
    selectedProducts: List<Triple<Int, Int, Int>>,
    tongtien: Int,
    tentaikhoan: String,
    makhachhang: String = "",
    hinhanh: String ,
    tensanpham: String = ""

) {
    val ngayhientai = LocalDate.now()
    val formattedDate = ngayhientai.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))

    val sanPhamViewModel: SanPhamViewModel = viewModel()
    val gioHangViewModel: GioHangViewModel = viewModel()
    val taiKhoanViewModel: TaiKhoanViewModel = viewModel()
    val diaChiViewmodel: DiaChiViewmodel = viewModel()
    val hoaDonBanVỉewModel: HoaDonBanVỉewModel = viewModel()
    val chiTietHoaDonBanViewmodel: ChiTietHoaDonBanViewmodel = viewModel()

    val danhsachsanpham by sanPhamViewModel.danhsachSanPham.collectAsState(initial = emptyList())
    val systemUiController = rememberSystemUiController()

    var selectedPaymentMethod by remember { mutableStateOf("Thanh toán khi nhận hàng") }
    var showQR by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val diachimacdinh = diaChiViewmodel.diachi
    val taikhoan = taiKhoanViewModel.taikhoan


    // Địa chỉ được chọn
    var selectedAddress by remember { mutableStateOf<DiaChi?>(null) }
    val addressToUse = selectedAddress ?: diachimacdinh

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    // Lấy ID địa chỉ đã chọn từ savedStateHandle
    LaunchedEffect(navBackStackEntry) {
        val id = navBackStackEntry?.savedStateHandle?.get<Int>("selectedAddressId")
        if (id != null) {
            // Gọi tới ViewModel để lấy DiaChi theo ID
            diaChiViewmodel.getDiaChiByMaDiaChi(id)
            navBackStackEntry.savedStateHandle.remove<Int>("selectedAddressId")
        }
    }

// Lắng nghe thay đổi của diachi trong ViewModel
    val diaChiFromVM = diaChiViewmodel.diachi
    LaunchedEffect(diaChiFromVM) {
        if (diaChiFromVM != null) {
            selectedAddress = diaChiFromVM
        }
    }

    LaunchedEffect(tentaikhoan) {
        taiKhoanViewModel.getTaiKhoanByTentaikhoan(tentaikhoan)
    }

    if (taikhoan != null) {
        LaunchedEffect(taikhoan) {
            diaChiViewmodel.getDiaChiMacDinh(taikhoan.MaKhachHang, 1)
        }
    }

    LaunchedEffect(selectedProducts) {
        selectedProducts.forEach { triple ->
            sanPhamViewModel.getSanPhamById2(triple.first.toString())
        }
    }

    SideEffect {
        systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                title = { Text("Thanh toán") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "",
                            tint = Color.Red
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = Color.White) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Tổng tiền: ${formatGiaTien(tongtien + 30000)}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        fontSize = 20.sp
                    )

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp),
                            color = Color.Red
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            selectedProducts.forEach { triple ->
                                gioHangViewModel.deleteGioHang(triple.third)
                            }

                            if (taikhoan != null && addressToUse != null) {
                                val maKhachHang = taikhoan.MaKhachHang ?: 0
                                val maDiaChi = addressToUse.MaDiaChi

                                val hoaDonBan = HoaDonBan(
                                    0,
                                    maKhachHang,
                                    formattedDate,
                                    maDiaChi,
                                    tongtien + 30000,
                                    selectedPaymentMethod,
                                    1
                                )

                                hoaDonBanVỉewModel.addHoaDon(hoaDonBan)
                                isLoading = true
                                // Chờ cho đến khi hoaDonBan được thêm thành công
                                selectedProducts.forEach { triple ->
                                    danhsachsanpham.forEach { sanpham ->
                                        if (sanpham.MaSanPham == triple.first) {
                                            val chitiethoadon = ChiTietHoaDonBan(
                                                0,
                                                0,
                                                sanpham.MaSanPham,
                                                triple.second,
                                                sanpham.Gia,
                                                sanpham.Gia + 30000,
                                                0,
                                                sanpham.TenSanPham ?: "" // Đã sửa chỗ này!
                                            )
                                            chiTietHoaDonBanViewmodel.addHoaDon(chitiethoadon)
                                        }
                                    }
                                }
                            }

                            navController.navigate("paysuccess_screen?tentaikhoan=${tentaikhoan}")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Đặt hàng")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            item {
                if (addressToUse != null) {
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(1.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocationOn, contentDescription = "",
                                    tint = Color.Red
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        addressToUse.TenNguoiNhan,
                                        fontWeight = FontWeight.Bold
                                    )
                                    TextButton(
                                        onClick = {
                                            navController.navigate("address_selection_screen?makhachhang=${taikhoan?.MaKhachHang ?: 0}")
                                        }
                                    ) {
                                        Text(
                                            "Thay đổi",
                                            color = Color.Red
                                        )
                                    }
                                }

                                Text(addressToUse.SoDienThoai)
                                Text(addressToUse.ThongTinDiaChi)
                            }
                        }
                    }
                }
            }

//            items(selectedProducts) { triple ->
//                val sanpham = danhsachsanpham.find { it.MaSanPham == triple.first }
//                if (sanpham != null) {
//                    ProductItem(sanpham, triple.second)
//                }
//            }
            // Hiển thị sản phẩm mua ngay (khi chỉ có 1 sản phẩm và có tên/hình)

            item {
                if (selectedProducts.size == 1 && tensanpham.isNotBlank() && hinhanh.isNotBlank()) {
                    val triple = selectedProducts.getOrNull(0) ?: return@item
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = hinhanh,
                                contentDescription = "Ảnh sản phẩm",
                                modifier = Modifier.size(90.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = tensanpham,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Giá: ${formatGiaTien(tongtien)}",
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Số lượng: ${triple.second}"
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            // Hiển thị sản phẩm từ giỏ hàng (nhiều sản phẩm)
            if (!(selectedProducts.size == 1 && tensanpham.isNotBlank() && hinhanh.isNotBlank())) {
                items(selectedProducts) { triple ->
                    val sanpham = danhsachsanpham.find { it.MaSanPham == triple.first }
                    if (sanpham != null) {
                        ProductItem(sanpham, triple.second)
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(1.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Text(
                        text = "Phương thức thanh toán",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 5.dp)
                    )

                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Thanh toán khi nhận hàng",
                                modifier = Modifier.padding(start = 25.dp)
                            )
                            RadioButton(
                                selected = selectedPaymentMethod == "Thanh toán khi nhận hàng",
                                onClick = {
                                    selectedPaymentMethod = "Thanh toán khi nhận hàng"
                                    showQR = false
                                },
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = Color.Red,
                                    selectedColor = Color.Red
                                )
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Chuyển khoản ngân hàng",
                                modifier = Modifier.padding(start = 25.dp)
                            )
                            RadioButton(
                                selected = selectedPaymentMethod == "Chuyển khoản ngân hàng",
                                onClick = {
                                    selectedPaymentMethod = "Chuyển khoản ngân hàng"
                                    showQR = true
                                },
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = Color.Red,
                                    selectedColor = Color.Red
                                )
                            )
                        }
                    }
                }
            }

            if (showQR) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("QR Thanh toán")
                        Image(
                            painter = painterResource(id = R.drawable.qr),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(300.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(140.dp),
                    elevation = CardDefaults.cardElevation(1.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Chi tiết thanh toán")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Tổng tiền hàng")
                            Text(text = formatGiaTien(tongtien))
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Tổng tiền vận chuyển")
                            Text("30,000đ")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Tổng thanh toán")
                            Text(text = formatGiaTien(tongtien + 30000))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(sanPham: SanPham, soLuong: Int) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = sanPham.HinhAnh,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        sanPham.TenSanPham
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            formatGiaTien(sanPham.Gia),
                            color = Color.Red
                        )
                        Text(
                            "x$soLuong"
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp, start = 3.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Tổng số tiền (${soLuong} sản phẩm)"
                )
                Text(
                    "${formatGiaTien(sanPham.Gia * soLuong)}"
                )
            }
        }
    }
}
