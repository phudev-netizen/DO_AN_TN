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
import com.example.lapstore.viewmodels.HoaDonBanVỉewModel

import com.example.lapstore.views.formatDate
import com.example.lapstore.views.formatGiaTien

@Composable
fun CardDonHang(
    navController: NavHostController,
    hoaDonBan: HoaDonBan,
    isAdmin: Boolean // Dự phòng nếu cần phân quyền
) {
    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()

    // State for return reason dialog
    var showReturnDialog by remember { mutableStateOf(false) }
    var returnReason by remember { mutableStateOf("") }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(4.dp),
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
            // Thông tin hóa đơn
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Mã Hóa Đơn: ${hoaDonBan.MaHoaDonBan}")
                Text(text = "Ngày Đặt Hàng: ${formatDate(hoaDonBan.NgayDatHang)}")
                Text(text = "Tổng Tiền: ${formatGiaTien(hoaDonBan.TongTien)}")
            }

            // Các nút dành cho khách hàng
            Column(horizontalAlignment = Alignment.End) {
                // Nút Hủy đơn nếu đang chờ lấy hàng (TrangThai == 2)
                if (hoaDonBan.TrangThai == 2) {
                    Button(
                        onClick = {
                            val hoadonHuy = hoaDonBan.copy(TrangThai = 6) // Giả sử 6 là "Đã hủy"
                            hoaDonBanViewModel.updateHoaDonBan(hoadonHuy)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text("Hủy đơn")
                    }
                }

                // Nút Trả hàng nếu đã giao (TrangThai == 4)
                if (hoaDonBan.TrangThai == 4) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            // Hiện dialog nhập lý do trả hàng
                            showReturnDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text("Trả hàng")
                    }
                }
            }
        }
    }

    // Dialog nhập lý do trả hàng
    if (showReturnDialog) {
        AlertDialog(
            onDismissRequest = {
                showReturnDialog = false
                returnReason = ""
            },
            title = { Text("Lý do trả hàng") },
            text = {
                Column {
                    Text(text = "Vui lòng nhập lý do trả hàng của bạn:")
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = returnReason,
                        onValueChange = { returnReason = it },
                        placeholder = { Text("Ví dụ: Sản phẩm lỗi, kích thước không đúng...") },
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
            },
            // Nút xác nhận và hủy

            confirmButton = {
                TextButton(
                    onClick = {
                        if (returnReason.isNotBlank()) {
                            // Gửi API cập nhật lý do
                            hoaDonBanViewModel.capNhatLyDoTraHang(
                                maHoaDonBan = hoaDonBan.MaHoaDonBan,
                                lyDo = returnReason
                            )

                            // Nếu bạn muốn đổi trạng thái luôn:
                            val donCapNhat = hoaDonBan.copy(TrangThai = 7)
                            hoaDonBanViewModel.updateHoaDonBan(donCapNhat)

                            Log.d("CardDonHang", "Đã gửi lý do: $returnReason")

                            showReturnDialog = false
                            returnReason = ""
                        }
                    }
                ) {
                    Text("Gửi")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showReturnDialog = false
                        returnReason = ""
                    }
                ) {
                    Text("Hủy")
                }
            }

        )
    }
}
