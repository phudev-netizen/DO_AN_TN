import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lapstore.models.HoaDonBan
import com.example.lapstore.viewmodels.ChiTietHoaDonBanViewmodel
import com.example.lapstore.viewmodels.DiaChiViewmodel
import com.example.lapstore.viewmodels.HoaDonBanVỉewModel
import com.example.lapstore.views.formatDate

@Composable
fun CardDonHangAdmin(
    navController: NavHostController,
    hoaDonBan: HoaDonBan,
    trangthai: Int,
    hoaDonBanVỉewModel: HoaDonBanVỉewModel
) {
    val chiTietHoaDonBanViewModel: ChiTietHoaDonBanViewmodel = viewModel()
    val sanPhamViewModel: SanPhamViewModel = viewModel()

    // Lấy danh sách chi tiết hóa đơn
    val danhsachchitiethoadon = chiTietHoaDonBanViewModel.danhsachchitethoadon
    LaunchedEffect(hoaDonBan) {
        chiTietHoaDonBanViewModel.getChiTietHoaDonTheoMaHoaDon(hoaDonBan.MaHoaDonBan)
    }

    // Lấy danh sách sản phẩm
    val danhSachAllSanPham = sanPhamViewModel.danhSachSanPhamTrongHoaDon
    LaunchedEffect(hoaDonBan) {
        sanPhamViewModel.getSanPhamTrongHoaDon(hoaDonBan.MaHoaDonBan)
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            navController.navigate("${NavRoute.HOADONDETAILSCREEN.route}?madonhang=${hoaDonBan.MaHoaDonBan}&tongtien=${hoaDonBan.TongTien}")
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cột chứa thông tin hóa đơn
            Column(
                modifier = Modifier.weight(1f) // Cột chiếm không gian linh hoạt
            ) {
                Text(text = "Mã Hóa Đơn: ${hoaDonBan.MaHoaDonBan}")
                Text(text = "Ngày Đặt Hàng: ${formatDate(hoaDonBan.NgayDatHang)}")
                Text(text = "Tổng Tiền: ${formatGiaTien(hoaDonBan.TongTien)}")
            }

            // Nút Hủy
            if (trangthai != 4 && trangthai != 6) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp)
                ) {
                    Button(
                        modifier = Modifier.fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            if(trangthai==2){
                                for (chiTiet in danhsachchitiethoadon) {
                                    val sanPham = danhSachAllSanPham.find { it.MaSanPham == chiTiet.MaSanPham }
                                    if (sanPham != null) {
                                        // Cộng số lượng trong chi tiết hóa đơn vào sản phẩm
                                        sanPham.SoLuong -= chiTiet.SoLuong

                                        // Cập nhật lại sản phẩm
                                        sanPhamViewModel.updateSanPham(sanPham)
                                    }
                                }
                            }
                            XacNhan(trangthai, hoaDonBan, hoaDonBanVỉewModel)
                        }
                    ) {
                        Text("Xác nhận")
                    }
                }
            }
        }
    }
}

// Hàm xác nhận đơn hàng, sử dụng when thay vì if-else
fun XacNhan(
    trangthai: Int,
    hoaDonBan: HoaDonBan,
    hoaDonBanVỉewModel: HoaDonBanVỉewModel
) {
    val trangThaiMoi = when (trangthai) {
        1 -> 2
        2 -> 3
        3 -> 4
        4 -> 5
        5 -> 6
        else -> trangthai // Nếu không có trạng thái hợp lệ, không thay đổi
    }

    val hoadonbannew = HoaDonBan(
        hoaDonBan.MaHoaDonBan,
        hoaDonBan.MaKhachHang,
        hoaDonBan.NgayDatHang,
        hoaDonBan.MaDiaChi,
        hoaDonBan.TongTien,
        hoaDonBan.PhuongThucThanhToan,
        trangThaiMoi
    )

    hoaDonBanVỉewModel.updateHoaDonBan(hoadonbannew)
}
