package com.example.lapstore.views

import SanPhamViewModel
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lapstore.models.HoaDonBan
import com.example.lapstore.viewmodels.ChiTietHoaDonBanViewmodel
import com.example.lapstore.viewmodels.HoaDonBanVỉewModel
import com.example.lapstore.views.formatDate
import com.example.lapstore.views.formatGiaTien
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CardDonHangAdmin(
    navController: NavHostController,
    hoaDonBan: HoaDonBan,
    hoaDonBanViewModel: HoaDonBanVỉewModel
) {
    // ViewModels
    val chiTietVM: ChiTietHoaDonBanViewmodel = viewModel()
    val sanPhamVM: SanPhamViewModel = viewModel()

    // State cho 2 dialog
    var showReturnDialog by remember { mutableStateOf(false) }
    var showCancelDialog by remember { mutableStateOf(false) }

    // Load chi tiết và sản phẩm kèm delay nhỏ
    LaunchedEffect(hoaDonBan.MaHoaDonBan) {
        chiTietVM.getChiTietHoaDonTheoMaHoaDon(hoaDonBan.MaHoaDonBan)
        sanPhamVM.getSanPhamTrongHoaDon(hoaDonBan.MaHoaDonBan)
        delay(300)
    }
    val chiTietDaLoad = chiTietVM.danhsachchitethoadon.isNotEmpty()

    // Thẻ hiển thị hóa đơn
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {
            navController.navigate(
                "${NavRoute.HOADONDETAILSCREEN.route}?madonhang=${hoaDonBan.MaHoaDonBan}&tongtien=${hoaDonBan.TongTien}"
            )
        }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thông tin
            Column(modifier = Modifier.weight(1f)) {
                Text("Mã Hóa Đơn: ${hoaDonBan.MaHoaDonBan}")
                Text("Ngày Đặt Hàng: ${formatDate(hoaDonBan.NgayDatHang)}")
                Text("Tổng Tiền: ${formatGiaTien(hoaDonBan.TongTien)}")

                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Lý do trả: ${hoaDonBan.LyDoTraHang?.takeIf { it.isNotBlank() } ?: "Chưa có"}",
                    color = if (hoaDonBan.LyDoTraHang.isNullOrBlank()) Color.Gray else Color.Red
                )
                Spacer(Modifier.height(6.dp))
            }

            // Nút hành động
            Column(horizontalAlignment = Alignment.End) {
                // Hoàn tất trả hàng (status 7)
                if (hoaDonBan.TrangThai == 7) {
                    Button(
                        onClick = { showReturnDialog = true },
                        modifier = Modifier.height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Hoàn tất trả hàng")
                    }
                }

                // Xác nhận tiến độ (status 1..5, trừ 4)
                if (hoaDonBan.TrangThai in 1..5 && hoaDonBan.TrangThai != 4) {
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            if (hoaDonBan.TrangThai == 2) {
                                // Chuyển 2->3: giảm tồn kho
                                chiTietVM.danhsachchitethoadon.forEach { ct ->
                                    sanPhamVM.danhSachSanPhamTrongHoaDon
                                        .find { it.MaSanPham == ct.MaSanPham }
                                        ?.let { sp ->
                                            sp.SoLuong -= ct.SoLuong
                                            sanPhamVM.updateSanPham(sp)
                                        }
                                }
                            }
                            XacNhan(hoaDonBan, hoaDonBanViewModel)
                            hoaDonBanViewModel.getAllHoaDonBan()
                        },
                        modifier = Modifier.height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(getButtonLabel(hoaDonBan.TrangThai))
                    }
                }

                // Hủy đơn (status 2)
                if (hoaDonBan.TrangThai == 2) {
                    Spacer(Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            if (chiTietDaLoad) showCancelDialog = true
                        },
                        modifier = Modifier.height(40.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Hủy đơn")
                    }
                }
            }
        }
    }
//    // Không cần thêm TextField ở đây!
//    if (showReturnDialog) {
//        AlertDialog(
//            onDismissRequest = { showReturnDialog = false },
//            title = { Text("Xác nhận hoàn tất trả hàng") },
//            text = {
//                Text("Bạn có chắc muốn đánh dấu đơn ${hoaDonBan.MaHoaDonBan} là đã hoàn tất trả hàng không?")
//            },
//            confirmButton = {
//                TextButton(onClick = {
//                    hoaDonBanViewModel.updateHoaDonBan(hoaDonBan.copy(TrangThai = 8))
//                    hoaDonBanViewModel.getAllHoaDonBan()
//                    showReturnDialog = false
//                }) {
//                    Text("Đồng ý")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showReturnDialog = false }) {
//                    Text("Hủy")
//                }
//            }
//        )
//    }
    // Dialog xác nhận hoàn tất trả hàng
    if (showReturnDialog) {
        AlertDialog(
            onDismissRequest = { showReturnDialog = false },
            title = { Text("Xác nhận hoàn tất trả hàng") },
            text = {
                Text(
                    "Bạn có chắc muốn đánh dấu đơn ${hoaDonBan.MaHoaDonBan} "
                            + "là đã hoàn tất trả hàng với lý do:\n\n"
                            + "${hoaDonBan.LyDoTraHang ?: "Chưa có lý do"}"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    // Gửi API update với cả lý do và trạng thái mới
                    val updated = hoaDonBan.copy(
                        TrangThai = 8,
                        LyDoTraHang = hoaDonBan.LyDoTraHang ?: ""
                    )
                    hoaDonBanViewModel.updateHoaDonBan(updated)
                    // Reload danh sách để hiển thị ngay
                    hoaDonBanViewModel.getHoaDonTheoTrangThai(8)
                    showReturnDialog = false
                }) {
                    Text("Hoàn tất")
                }
            },
            dismissButton = {
                TextButton(onClick = { showReturnDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }



    // Dialog 2: Xác nhận hủy đơn
    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Xác nhận hủy đơn") },
            text = { Text("Bạn có chắc muốn hủy đơn ${hoaDonBan.MaHoaDonBan}?") },
            confirmButton = {
                TextButton(onClick = {
                    hoaDonBanViewModel.updateHoaDonBan(hoaDonBan.copy(TrangThai = 5))
                    chiTietVM.danhsachchitethoadon
                        .takeIf { it.isNotEmpty() }
                        ?.let { sanPhamVM.khoiPhucTonKhoKhiTraHang(it) }
                    hoaDonBanViewModel.getAllHoaDonBan()
                    showCancelDialog = false
                }) {
                    Text("Đồng ý")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}

// Hàm chuyển trạng thái
fun XacNhan(
    hoaDonBan: HoaDonBan,
    hoaDonBanViewModel: HoaDonBanVỉewModel
) {
    val next = when (hoaDonBan.TrangThai) {
        1 -> 2; 2 -> 3; 3 -> 4; 4 -> 5; 5 -> 6
        else -> hoaDonBan.TrangThai
    }
    hoaDonBanViewModel.updateHoaDonBan(hoaDonBan.copy(TrangThai = next))
}

// Nhãn nút tương ứng status
fun getButtonLabel(status: Int): String = when (status) {
    1 -> "Duyệt đơn"
    2 -> "Xác nhận giao"
    3 -> "Xác nhận đã giao"
    4 -> "Hoàn thành"
    5 -> "Xác nhận hủy"
    else -> "Cập nhật"
}
