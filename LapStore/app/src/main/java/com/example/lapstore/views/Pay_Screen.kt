import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.lapstore.R
import com.example.lapstore.models.ChiTietHoaDonBan
import com.example.lapstore.models.DiaChi
import com.example.lapstore.models.HoaDonBan
import com.example.lapstore.models.SanPham
import com.example.lapstore.viewmodels.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.bumptech.glide.Glide
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient.Thanhtoanapi
import com.example.lapstore.models.KhuyenMai
import com.example.lapstore.views.formatGiaTien
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayScreen(
    navController: NavHostController,
    selectedProducts: List<Triple<Int, Int, Int>>,
    tongtien: Int,
    tentaikhoan: String,
    makhachhang: String = "",
    hinhanh: String = "",
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
    val context = LocalContext.current

    val phiVanChuyen = 30000
    val khuyenMaiViewModel: KhuyenMaiViewModel = viewModel()
    val listKhuyenMai by remember { derivedStateOf { khuyenMaiViewModel.khuyenMaiList.toList() } }

    LaunchedEffect(Unit) {
        khuyenMaiViewModel.fetchTatCaKhuyenMai()
    }
    var soluongMuaNgay by remember { mutableIntStateOf(1) }

    // Chuyển đổi selectedProducts từ String sang List<Triple<Int, Int, Int>>
    val tongTienHang = remember(selectedProducts, danhsachsanpham) {
        selectedProducts.sumOf { triple ->
            val sp = danhsachsanpham.find { it.MaSanPham == triple.first }
            val giaGoc = sp?.Gia ?: 0
            giaGoc * triple.second
        }
    }
    // Tính tổng tiền hàng với giá đã giảm (nếu có)
    val tongGiamGia = remember(selectedProducts, danhsachsanpham, listKhuyenMai) {
        selectedProducts.sumOf { triple ->
            val sp = danhsachsanpham.find { it.MaSanPham == triple.first }
            val km = listKhuyenMai.find { it.MaSanPham == triple.first }
            val giaGoc = sp?.Gia ?: 0
            val giaSauGiam = getGiaSauGiam(giaGoc, km?.PhanTramGiam)
            (giaGoc - giaSauGiam) * triple.second
        }
    }

    val tongTienSauGiam = tongTienHang - tongGiamGia
    val tongTienThanhToan = if (tongTienSauGiam < 0) 0 else tongTienSauGiam
    val tongTien = tongTienThanhToan + phiVanChuyen



    // Địa chỉ được chọn
    var selectedAddress by remember { mutableStateOf<DiaChi?>(null) }
    val addressToUse = selectedAddress ?: diachimacdinh

    val navBackStackEntry = navController.currentBackStackEntryAsState().value



    LaunchedEffect(navBackStackEntry) {
        val id = navBackStackEntry?.savedStateHandle?.get<Int>("selectedAddressId")
        if (id != null) {
            diaChiViewmodel.getDiaChiByMaDiaChi(id)
            navBackStackEntry.savedStateHandle.remove<Int>("selectedAddressId")
        }
    }

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
//                        text = "Tổng tiền: ${formatGiaTien(tongtien + 30000)}",
                        text = "Tổng tiền: ${formatGiaTien(tongTienThanhToan + phiVanChuyen)}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp),
                            color = Color.Red
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))

//                    // NÚT ĐẶT HÀNG VÀ THANH TOÁN BẰNG MOMO
                    if (selectedPaymentMethod == "Thanh toán qua Momo") {
                        Button(
                            onClick = {
                                // Kiểm tra điều kiện
                                if (taikhoan == null || addressToUse == null) {
                                    Toast.makeText(context, "Thiếu tài khoản hoặc địa chỉ", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                if (selectedProducts.isEmpty()) {
                                    Toast.makeText(context, "Chưa chọn sản phẩm", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

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

                                selectedProducts.forEach { triple ->
                                    danhsachsanpham.forEach { sanpham ->
                                        if (sanpham.MaSanPham == triple.first) {
                                            val km = listKhuyenMai.find { it.MaSanPham == sanpham.MaSanPham }
                                            val giaGoc = sanpham.Gia
                                            val phanTramGiam = km?.PhanTramGiam ?: 0
                                            val giaSauGiam = getGiaSauGiam(giaGoc, phanTramGiam)
                                            val tongGiamGia = (giaGoc - giaSauGiam) * triple.second

                                            val chitiethoadon = ChiTietHoaDonBan(
                                                0,
                                                0,
                                                sanpham.MaSanPham,
                                                triple.second,
                                                giaGoc,
                                                giaSauGiam * triple.second,
                                                tongGiamGia,
                                                sanpham.TenSanPham ?: ""
                                            )
                                            chiTietHoaDonBanViewmodel.addHoaDon(chitiethoadon)
                                        }
                                    }
                                }

                                thanhToanQuaMomo(
                                    context = context,
                                    maKhachHang = maKhachHang.toString(),
                                    maDiaChi = maDiaChi.toString(),
                                    tongTien = (tongtien + 30000).toString()
                                )
                                isLoading = false
                            },
                            enabled = !isLoading,
                            modifier = Modifier
                                .height(48.dp)
                                .defaultMinSize(minWidth = 130.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 10.dp,
                                pressedElevation = 12.dp
                            )

                        ) {
                            Text(
                                text = "MOMO",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                } else {
                        // NÚT ĐẶT HÀNG BÌNH THƯỜNG
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
                                    selectedProducts.forEach { triple ->
                                        danhsachsanpham.forEach { sanpham ->
                                            if (sanpham.MaSanPham == triple.first) {
                                                val km = listKhuyenMai.find { it.MaSanPham == sanpham.MaSanPham }
                                                val giaGoc = sanpham.Gia
                                                val phanTramGiam = km?.PhanTramGiam ?: 0
                                                val giaSauGiam = getGiaSauGiam(giaGoc, phanTramGiam)
                                                val tongGiamGia = (giaGoc - giaSauGiam) * triple.second

                                                val chitiethoadon = ChiTietHoaDonBan(
                                                    0,
                                                    0,
                                                    sanpham.MaSanPham,
                                                    triple.second,
                                                    giaGoc,
                                                    giaSauGiam * triple.second,
                                                    tongGiamGia,
                                                    sanpham.TenSanPham ?: ""
                                                )
                                                chiTietHoaDonBanViewmodel.addHoaDon(chitiethoadon)
                                            }
                                        }
                                    }
                                }

                                navController.navigate("paysuccess_screen?tentaikhoan=${tentaikhoan}")
                            },
                            enabled = !isLoading,
                            modifier = Modifier
                                .height(48.dp)
                                .defaultMinSize(minWidth = 130.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Đặt hàng", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
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

            item {
                if (selectedProducts.size == 1 && tensanpham.isNotBlank() && hinhanh.isNotBlank()) {
                    val triple = selectedProducts.getOrNull(0) ?: return@item
                    val soluong = remember { mutableIntStateOf(triple.second) }
                    // Tìm giá trong danh sách sản phẩm
                    val sanpham = danhsachsanpham.find { it.MaSanPham == triple.first }
                    val km = listKhuyenMai.find { it.MaSanPham == triple.first }
                    val giaGoc = sanpham?.Gia ?: 0
                    val giaSauGiam = getGiaSauGiam(giaGoc, km?.PhanTramGiam)
                    val tongTien by remember {
                        derivedStateOf { soluong.value * giaSauGiam }
//                        val tongTien by remember { derivedStateOf { soluongMuaNgay * giaSauGiam } }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
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
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(4.dp),
                                contentScale = ContentScale.Crop,
                            )

                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = tensanpham,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Giá: ${formatGiaTien(giaSauGiam)}",
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Số lượng: ")

                                    IconButton(onClick = {
                                        if (soluong.value > 1) soluong.value--
                                    }) {
                                        Icon(Icons.Default.Remove, contentDescription = "Giảm")
                                    }

                                    Text(
                                        text = soluong.value.toString(),
                                        modifier = Modifier.width(32.dp),
                                        textAlign = TextAlign.Center
                                    )

                                    IconButton(onClick = {
                                        soluong.value++
                                    }) {
                                        Icon(Icons.Default.Add, contentDescription = "Tăng")
                                    }

                                }
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
                        ProductItem(
                            sanpham, triple.second,
                            listKhuyenMai = listKhuyenMai
                        )
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Thanh toán qua Momo",
                                modifier = Modifier.padding(start = 25.dp)
                            )
                            RadioButton(
                                selected = selectedPaymentMethod == "Thanh toán qua Momo",
                                onClick = {
                                    selectedPaymentMethod = "Thanh toán qua Momo"
                                    showQR = false // Ẩn QR chuyển khoản
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
            // Hiển thị QR Code nếu chọn chuyển khoản ngân hàng
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

            // Hiển thị tổng tiền hàng và chi tiết thanh toán
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
                            Text(text = formatGiaTien(tongTienHang))
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Giảm giá")
                            Text(text = "-${formatGiaTien(tongGiamGia)}")
                        }
                        if (tongGiamGia > 0) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Khuyến mãi đã áp dụng",
                                    color = Color(0xFF1ABC9C),
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    "-${formatGiaTien(tongGiamGia)}",
                                    color = Color(0xFF1ABC9C),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Tổng tiền vận chuyển")
                            Text(formatGiaTien(phiVanChuyen))
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Tổng thanh toán", fontWeight = FontWeight.Bold)
                            Text(
                                formatGiaTien(tongTienThanhToan + phiVanChuyen),
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

fun thanhToanQuaMomo(
    context: Context,
    maKhachHang: String,
    maDiaChi: String,
    tongTien: String
) {
    val scope = CoroutineScope(Dispatchers.IO)

    scope.launch {
        try {
            val response = Thanhtoanapi.taoThanhToanMomo(
                mapOf("amount" to tongTien)
            )
            if (response.success && response.payUrl != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(response.payUrl))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Lỗi: ${response.message ?: "Không thể tạo thanh toán"}", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Lỗi kết nối đến server", Toast.LENGTH_SHORT).show()
                Log.e("MoMo", "Lỗi tạo thanh toán", e)
            }
        }
    }
}


@Composable
fun ProductItem(sanPham: SanPham, soLuong: Int, listKhuyenMai: List<KhuyenMai>) {
    val khuyenMai = listKhuyenMai.find { it.MaSanPham == sanPham.MaSanPham }
    val giaHienThi = getGiaSauGiam(sanPham.Gia, khuyenMai?.PhanTramGiam)
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
                    modifier = Modifier.size(100.dp),
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
                        sanPham.TenSanPham,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (khuyenMai != null && khuyenMai.PhanTramGiam > 0) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    formatGiaTien(sanPham.Gia),
                                    color = Color.Gray,
                                    fontSize = 13.sp,
                                    textDecoration = TextDecoration.LineThrough,
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    formatGiaTien(giaHienThi),
                                    color = Color.Red,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text("x$soLuong")
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp, start = 3.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tổng số tiền (${soLuong} sản phẩm)")
                Text(
                    formatGiaTien(giaHienThi * soLuong),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
fun parseSelectedProducts(data: String): List<Triple<Int, Int, Int>> {
    if (data.isBlank()) return emptyList()
    return data
        .split("\n")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .mapNotNull { row ->
            val parts = row.split(",").map { it.trim() }
            when (parts.size) {
                2 -> try { Triple(parts[0].toInt(), parts[1].toInt(), 0) } catch (_: Exception) { null }
                in 3..Int.MAX_VALUE -> try { Triple(parts[0].toInt(), parts[1].toInt(), parts[2].toInt()) } catch (_: Exception) { null }
                else -> null
            }
        }
}