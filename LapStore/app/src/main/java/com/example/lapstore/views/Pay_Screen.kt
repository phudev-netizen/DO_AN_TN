import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lapstore.R
import com.example.lapstore.models.ChiTietHoaDonBan
import com.example.lapstore.models.HoaDonBan
import com.example.lapstore.models.SanPham
import com.example.lapstore.ui.formatGiaTien
import com.example.lapstore.viewmodels.ChiTietHoaDonBanViewmodel
import com.example.lapstore.viewmodels.DiaChiViewmodel
import com.example.lapstore.viewmodels.GioHangViewModel
import com.example.lapstore.viewmodels.HoaDonBanVỉewModel
import com.example.lapstore.viewmodels.TaiKhoanViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayScreen(
    navController: NavHostController,
    selectedProducts: List<Triple<Int, Int, Int>>, // Thay đổi kiểu thành danh sách
    tongtien: Int,
    tentaikhoan:String
) {
    val ngayhientai = LocalDate.now() // Lấy ngày hiện tại
    val formattedDate = ngayhientai.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))


    val sanPhamViewModel: SanPhamViewModel = viewModel()
    val gioHangViewModel: GioHangViewModel = viewModel()
    val taiKhoanViewModel:TaiKhoanViewModel = viewModel()
    val diaChiViewmodel:DiaChiViewmodel = viewModel()
    val hoaDonBanVỉewModel:HoaDonBanVỉewModel = viewModel()
    val chiTietHoaDonBanViewmodel:ChiTietHoaDonBanViewmodel = viewModel()

    val danhsachsanpham by sanPhamViewModel.danhsachSanPham.collectAsState(initial = emptyList())
    val systemUiController = rememberSystemUiController()

    var selectedPaymentMethod by remember { mutableStateOf("Thanh toán khi nhận hàng") }
    var showQR by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) } // Biến trạng thái để hiển thị vòng tròn xoay

    val diachimacdinh = diaChiViewmodel.diachi
    val taikhoan = taiKhoanViewModel.taikhoan


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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text("Thanh toán")
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
                            // Xóa các sản phẩm khỏi giỏ hàng
                            selectedProducts.forEach { triple ->
                                gioHangViewModel.deleteGioHang(triple.third)
                            }

                            if (taikhoan != null && diachimacdinh != null) {
                                // Lấy mã khách hàng và địa chỉ
                                val maKhachHang = taikhoan.MaKhachHang ?: 0
                                val maDiaChi = diachimacdinh.MaDiaChi

                                // Tạo đối tượng HoaDonBan
                                val hoaDonBan = HoaDonBan(
                                    0, // MaHoaDonBan sẽ được tự động tạo khi insert vào DB
                                    maKhachHang,
                                    formattedDate,
                                    maDiaChi,
                                    tongtien + 30000, // Tổng tiền + phí vận chuyển
                                    selectedPaymentMethod,
                                    1 // Trạng thái thanh toán
                                )

                                // Thêm HoaDonBan trước
                                hoaDonBanVỉewModel.addHoaDon(hoaDonBan)


                                // Sau khi HoaDonBan đã được thêm, tiếp tục thêm ChiTietHoaDonBan
                                selectedProducts.forEach { triple ->
                                    danhsachsanpham.forEach { sanpham ->
                                        if (sanpham.MaSanPham == triple.first) {
                                            // Tạo đối tượng ChiTietHoaDonBan
                                            val chitiethoadon = ChiTietHoaDonBan(
                                                0, // MaChiTietHoaDonBan sẽ được tự động tạo khi insert vào DB
                                                0, // MaHoaDonBan cần phải lấy từ bảng HoaDonBan sau khi insert
                                                sanpham.MaSanPham,
                                                triple.second, // Số lượng sản phẩm
                                                sanpham.Gia, // Thanh toán
                                                sanpham.Gia + 30000, // Tổng thanh toán (bao gồm phí vận chuyển)
                                                0 // Trạng thái
                                            )

                                            // Thêm ChiTietHoaDonBan
                                            chiTietHoaDonBanViewmodel.addHoaDon(chitiethoadon)
                                        }
                                    }
                                }
                            }

                            // Chuyển sang màn hình thành công
                            navController.navigate("${NavRoute.PAYSUCCESS.route}?tentaikhoan=${tentaikhoan}")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Đặt hàng")
                    }

                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(10.dp)
        ) {
            item {
                if(diachimacdinh!=null){
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(1.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column (
                                modifier = Modifier.fillMaxHeight().padding(10.dp),
                                verticalArrangement = Arrangement.Top
                            ){
                                Icon(
                                    imageVector = Icons.Filled.LocationOn, contentDescription = "",
                                    tint = Color.Red
                                )
                            }
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        diachimacdinh.TenNguoiNhan,
                                        fontWeight = FontWeight.Bold
                                    )
                                    TextButton(
                                        onClick = {
                                            //chua thay doi dc
                                            //navController.navigate("${NavRoute.DIACHISCREEN.route}")
                                        }
                                    ) {
                                        Text(
                                            "Thay đổi",
                                            color = Color.Red
                                        )
                                    }
                                }

                                Text(
                                    diachimacdinh.SoDienThoai
                                )

                                Text(
                                    diachimacdinh.ThongTinDiaChi
                                )
                            }
                        }
                    }
                }

            }

            items(danhsachsanpham) { sanpham ->
                selectedProducts.forEach { triple ->
                    if (sanpham.MaSanPham == triple.first)
                        ProductItem(sanpham, triple.second) // triple.second là số lượng
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
                    "${formatGiaTien(sanPham.Gia *soLuong)}"
                )
            }
        }
    }
}
