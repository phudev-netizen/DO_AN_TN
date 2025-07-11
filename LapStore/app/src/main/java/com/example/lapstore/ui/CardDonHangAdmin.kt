//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.example.lapstore.models.HoaDonBan
//import com.example.lapstore.models.SanPham
//import com.example.lapstore.viewmodels.ChiTietHoaDonBanViewmodel
//import com.example.lapstore.viewmodels.HoaDonBanVỉewModel
//import com.example.lapstore.views.formatDate
//import com.example.lapstore.views.formatGiaTien
//import kotlinx.coroutines.flow.MutableStateFlow
//
//@Composable
//fun CardDonHangAdmin(
//    navController: NavHostController,
//    hoaDonBan: HoaDonBan,
//    hoaDonBanVỉewModel: HoaDonBanVỉewModel
//) {
//    val chiTietVM: ChiTietHoaDonBanViewmodel = viewModel()
//    val sanPhamVM: SanPhamViewModel = viewModel()
//    var showConfirmDialog by remember { mutableStateOf(false) }
//
//    // Load chi tiết & sản phẩm
//    LaunchedEffect(hoaDonBan.MaHoaDonBan) {
//        chiTietVM.getChiTietHoaDonTheoMaHoaDon(hoaDonBan.MaHoaDonBan)
//        sanPhamVM.getSanPhamTrongHoaDon(hoaDonBan.MaHoaDonBan)
//    }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(5.dp),
//        elevation = CardDefaults.cardElevation(4.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        onClick = {
//            navController.navigate(
//                "${NavRoute.HOADONDETAILSCREEN.route}?madonhang=${hoaDonBan.MaHoaDonBan}&tongtien=${hoaDonBan.TongTien}"
//            )
//        }
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text("Mã Hóa Đơn: ${hoaDonBan.MaHoaDonBan}")
//                Text("Ngày Đặt Hàng: ${formatDate(hoaDonBan.NgayDatHang)}")
//                Text("Tổng Tiền: ${formatGiaTien(hoaDonBan.TongTien)}")
//
//                if (hoaDonBan.TrangThai == 7) {
//                    Spacer(modifier = Modifier.height(6.dp))
//                    Text("Lý do trả: ${hoaDonBan.LyDoTraHang ?: "Không có"}", color = Color.Red)
//                }
//            }
//
//            Column(horizontalAlignment = Alignment.End) {
//                if (hoaDonBan.TrangThai == 7) {
//                    Button(
//                        onClick = { showConfirmDialog = true },
//                        modifier = Modifier.height(40.dp),
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
//                        shape = RoundedCornerShape(10.dp)
//                    ) {
//                        Text("Hoàn tất trả hàng")
//                    }
//                }
//
//                if (hoaDonBan.TrangThai in 1..5 && hoaDonBan.TrangThai != 4) {
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Button(
//                        onClick = {
//                            if (hoaDonBan.TrangThai == 2) {
//                                // Giảm tồn kho nếu đang chuẩn bị giao
//                                chiTietVM.danhsachchitethoadon.forEach { ct ->
//                                    sanPhamVM.danhSachSanPhamTrongHoaDon.find { it.MaSanPham == ct.MaSanPham }
//                                        ?.let { sp ->
//                                            sp.SoLuong -= ct.SoLuong
//                                            sanPhamVM.updateSanPham(sp)
//                                        }
//                                }
//                            }
//                            XacNhan(hoaDonBan, hoaDonBanVỉewModel)
//                        },
//                        modifier = Modifier.height(40.dp),
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
//                        shape = RoundedCornerShape(10.dp)
//                    ) {
//                        Text(getButtonLabel(hoaDonBan.TrangThai))
//                    }
//                }
//            }
//        }
//    }
//
//    // ✅ Dialog xác nhận hoàn tất trả hàng
//    if (showConfirmDialog) {
//        AlertDialog(
//            onDismissRequest = { showConfirmDialog = false },
//            title = { Text("Xác nhận hoàn tất") },
//            text = { Text("Bạn có chắc muốn hoàn tất trả hàng cho đơn ${hoaDonBan.MaHoaDonBan}?") },
//            confirmButton = {
//                TextButton(onClick = {
//                    val updatedHoaDon = hoaDonBan.copy(TrangThai = 8)
//                    hoaDonBanVỉewModel.updateHoaDonBan(updatedHoaDon)
//
//                    // ✅ Gọi khôi phục tồn kho từ SanPhamViewModel
//                    sanPhamVM.khoiPhucTonKhoKhiTraHang(chiTietVM.danhsachchitethoadon)
//
//                    showConfirmDialog = false
//                }) {
//                    Text("Đồng ý")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showConfirmDialog = false }) {
//                    Text("Hủy")
//                }
//            }
//        )
//    }
//}
//
//// Xác nhận tiến trình đơn hàng
//fun XacNhan(
//    hoaDonBan: HoaDonBan,
//    hoaDonBanVỉewModel: HoaDonBanVỉewModel
//) {
//    val nextStatus = when (hoaDonBan.TrangThai) {
//        1 -> 2
//        2 -> 3
//        3 -> 4
//        4 -> 5
//        5 -> 6
//        else -> hoaDonBan.TrangThai
//    }
//    hoaDonBanVỉewModel.updateHoaDonBan(hoaDonBan.copy(TrangThai = nextStatus))
//}
//
//// Nhãn cho nút xác nhận
//fun getButtonLabel(status: Int): String {
//    return when (status) {
//        1 -> "Duyệt đơn"
//        2 -> "Xác nhận giao"
//        3 -> "Xác nhận đã giao"
//        4 -> "Hoàn thành"
//        5 -> "Xác nhận huỷ"
//        else -> "Cập nhật"
//    }
//}

package com.example.lapstore.views

import SanPhamViewModel
import android.util.Log
import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.flow.forEach

@Composable
fun CardDonHangAdmin(
    navController: NavHostController,
    hoaDonBan: HoaDonBan,
    hoaDonBanVỉewModel: HoaDonBanVỉewModel
) {
    // Lấy ViewModel
    val chiTietVM: ChiTietHoaDonBanViewmodel = viewModel()
    val sanPhamVM: SanPhamViewModel = viewModel()

    // State cho dialog
    var showReturnDialog by remember { mutableStateOf(false) }
    var showCancelDialog by remember { mutableStateOf(false) }

    LaunchedEffect(hoaDonBan.MaHoaDonBan) {
        chiTietVM.getChiTietHoaDonTheoMaHoaDon(hoaDonBan.MaHoaDonBan)
        sanPhamVM.getSanPhamTrongHoaDon(hoaDonBan.MaHoaDonBan)
        delay(300) // Chờ dữ liệu load tránh văng app khi thao tác nhanh
    }
    val chiTietDaLoad = chiTietVM.danhsachchitethoadon.isNotEmpty()

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thông tin đơn
            Column(modifier = Modifier.weight(1f)) {
                Text("Mã Hóa Đơn: ${hoaDonBan.MaHoaDonBan}")
                Text("Ngày Đặt Hàng: ${formatDate(hoaDonBan.NgayDatHang)}")
                Text("Tổng Tiền: ${formatGiaTien(hoaDonBan.TongTien)}")

                if (hoaDonBan.TrangThai == 7) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Lý do trả: ${hoaDonBan.LyDoTraHang ?: "Không có"}",
                        color = Color.Red
                    )
                }
            }

            // Các nút hành động
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            if (hoaDonBan.TrangThai == 2) {
                                // Khi chuyển từ 2->3 mới giảm tồn
                                chiTietVM.danhsachchitethoadon.forEach { ct ->
                                    sanPhamVM.danhSachSanPhamTrongHoaDon
                                        .find { it.MaSanPham == ct.MaSanPham }
                                        ?.let { sp ->
                                            sp.SoLuong -= ct.SoLuong
                                            sanPhamVM.updateSanPham(sp)
                                        }
                                }
                            }
                            XacNhan(hoaDonBan, hoaDonBanVỉewModel)
                        },
                        modifier = Modifier.height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(getButtonLabel(hoaDonBan.TrangThai))
                    }
                }

                // Hủy đơn khi chờ lấy hàng (status == 2)
//                if (hoaDonBan.TrangThai == 2) {
//                    Spacer(modifier = Modifier.height(8.dp))
//                    OutlinedButton(
//                        onClick = { showCancelDialog = true },
//                        modifier = Modifier.height(40.dp),
//                        shape = RoundedCornerShape(10.dp)
//                    ) {
//                        Text("Hủy đơn")
//                    }
//                }
                if (hoaDonBan.TrangThai == 2) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            if (chiTietDaLoad) {
                                showCancelDialog = true
                            } else {
                                // Có thể hiển thị Toast hoặc Snackbar để thông báo chưa load xong
                                Log.w("CardDonHangAdmin", "Dữ liệu chi tiết hóa đơn chưa được tải xong.")
                            }
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

    // Dialog: hoàn tất trả hàng
    if (showCancelDialog && chiTietDaLoad) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Xác nhận hủy đơn") },
            text = { Text("Bạn có chắc muốn hủy đơn ${hoaDonBan.MaHoaDonBan}?") },
            confirmButton = {
                TextButton(onClick = {
                    hoaDonBanVỉewModel.updateHoaDonBan(hoaDonBan.copy(TrangThai = 5))
                    chiTietVM.danhsachchitethoadon
                        .takeIf { it.isNotEmpty() }
                        ?.let { sanPhamVM.khoiPhucTonKhoKhiTraHang(it) }
                    showCancelDialog = false
                }) {
                    Text("Đồng ý")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) { Text("Hủy") }
            }
        )
    }

    // Dialog: xác nhận hủy đơn
    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Xác nhận hủy đơn") },
            text = { Text("Bạn có chắc muốn hủy đơn ${hoaDonBan.MaHoaDonBan}?") },
            confirmButton = {
                TextButton(onClick = {
                    // 1. Cập nhật status 5 (Chờ xác nhận hủy)
                    hoaDonBanVỉewModel.updateHoaDonBan(hoaDonBan.copy(TrangThai = 5))
                    // 2. Khôi phục tồn kho nếu đã trừ trước đó
                    Log.d("DEBUG", "ChiTiet size: ${chiTietVM.danhsachchitethoadon.size}")
                    chiTietVM.danhsachchitethoadon
                        .filterNotNull()
                        .takeIf { it.isNotEmpty() }
                        ?.let { sanPhamVM.khoiPhucTonKhoKhiTraHang(it) }

                    showCancelDialog = false
                }) {
                    Text("Đồng ý")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) { Text("Hủy") }
            }
        )
    }
}

// Hàm cập nhật status tiếp theo
fun XacNhan(
    hoaDonBan: HoaDonBan,
    hoaDonBanVỉewModel: HoaDonBanVỉewModel
) {
    val nextStatus = when (hoaDonBan.TrangThai) {
        1 -> 2
        2 -> 3
        3 -> 4
        4 -> 5
        5 -> 6
        else -> hoaDonBan.TrangThai
    }
    hoaDonBanVỉewModel.updateHoaDonBan(hoaDonBan.copy(TrangThai = nextStatus))
}

// Nhãn nút tương ứng từng status
fun getButtonLabel(status: Int): String = when (status) {
    1 -> "Duyệt đơn"
    2 -> "Xác nhận giao"
    3 -> "Xác nhận đã giao"
    4 -> "Hoàn thành"
    5 -> "Xác nhận hủy"
    else -> "Cập nhật"
}
