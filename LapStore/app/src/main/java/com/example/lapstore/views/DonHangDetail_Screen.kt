import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lapstore.models.ChiTietHoaDonBan
import com.example.lapstore.models.SanPham
import com.example.lapstore.viewmodels.ChiTietHoaDonBanViewmodel
import com.example.lapstore.viewmodels.DiaChiViewmodel
import com.example.lapstore.viewmodels.HoaDonBanVỉewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonHangDetailScreen(
    navController: NavHostController,
    mahoadon: Int,
    tongtien: Int
) {

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(color = Color.White, darkIcons = false)
    }

    val hoaDonBanVỉewModel: HoaDonBanVỉewModel = viewModel()
    val chiTietHoaDonBanViewmodel: ChiTietHoaDonBanViewmodel = viewModel()
    val diaChiViewmodel: DiaChiViewmodel = viewModel()
    val sanPhamViewModel: SanPhamViewModel = viewModel()

    var hoadon = hoaDonBanVỉewModel.HoaDonBan
    val diachi = diaChiViewmodel.diachi
    var danhsachchitiethoadon = chiTietHoaDonBanViewmodel.danhsachchitethoadon
    val danhSachSanPham = sanPhamViewModel.danhSachSanPhamTrongHoaDon

    val tongTienchuaship = danhsachchitiethoadon.sumOf {
        val donGia = it.DonGia ?: 0
        val soLuong = it.SoLuong ?: 0
        donGia * soLuong
    }

    LaunchedEffect(mahoadon) {
        hoaDonBanVỉewModel.getHoaDonByMaHoaDon(mahoadon)
        sanPhamViewModel.getSanPhamTrongHoaDon(mahoadon)
    }
    if (hoadon != null) {
        LaunchedEffect(hoadon.MaDiaChi) {
            diaChiViewmodel.getDiaChiByMaDiaChi(hoadon.MaDiaChi)
        }
    }
    LaunchedEffect(mahoadon) {
        chiTietHoaDonBanViewmodel.getChiTietHoaDonTheoMaHoaDon(mahoadon)
    }


    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text("Thông tin đơn hàng")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            // Card hiển thị địa chỉ nhận hàng
            item {
                if (diachi != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Địa chỉ nhận hàng",
                                modifier = Modifier.padding(bottom = 10.dp),
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.LocationOn,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Red
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = diachi.TenNguoiNhan,
                                    )
                                    Text(
                                        text = "SDT: ${diachi.SoDienThoai}",
                                    )

                                    Text(
                                        text = diachi.ThongTinDiaChi,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Card lớn chứa tất cả sản phẩm
            item {
                if (danhSachSanPham.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                "Danh sách sản phẩm",
                                modifier = Modifier.padding(bottom = 10.dp),
                            )
                            // Hiển thị danh sách sản phẩm trong một cột
                            for (sanpham in danhSachSanPham) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    AsyncImage(
                                        model = sanpham.HinhAnh,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .padding(end = 8.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = sanpham.TenSanPham,
                                        )
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = formatGiaTien(sanpham.Gia),
                                            )
                                            for (cthd in danhsachchitiethoadon) {
                                                if (cthd.MaSanPham == sanpham.MaSanPham) {
                                                    Text(
                                                        text = "x${cthd.SoLuong}",
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        HorizontalDivider(
                            color = Color.Gray,
                            thickness = 0.5.dp,
                            modifier = Modifier.padding(3.dp)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Tổng tiền hàng"
                                )
                                Text(
                                    formatGiaTien(tongTienchuaship),
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Tổng tiền vận chuyển"
                                )
                                Text(
                                    formatGiaTien(30000),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Thành tiền"
                                )
                                Text(
                                    formatGiaTien(tongtien),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
