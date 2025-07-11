//import android.util.Log
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.example.lapstore.models.HoaDonBan
//import com.example.lapstore.models.SanPham
//import com.example.lapstore.viewmodels.ChiTietHoaDonBanViewmodel
//import com.example.lapstore.viewmodels.DiaChiViewmodel
//import com.example.lapstore.viewmodels.HoaDonBanVỉewModel
//import com.example.lapstore.views.formatDate
//import com.example.lapstore.views.formatGiaTien
//
//@Composable
//fun CardDonHang(
//    navController: NavHostController,
//    hoaDonBan: HoaDonBan,
//    isAdmin: Boolean // Dự phòng nếu cần phân quyền
//) {
//    val hoaDonBanViewModel: HoaDonBanVỉewModel = viewModel()
//
//    Card(
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(5.dp),
//        elevation = CardDefaults.cardElevation(4.dp),
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
//            // Thông tin hóa đơn
//            Column(modifier = Modifier.weight(1f)) {
//                Text(text = "Mã Hóa Đơn: ${hoaDonBan.MaHoaDonBan}")
//                Text(text = "Ngày Đặt Hàng: ${formatDate(hoaDonBan.NgayDatHang)}")
//                Text(text = "Tổng Tiền: ${formatGiaTien(hoaDonBan.TongTien)}")
//            }
//
//            // Các nút dành cho khách hàng
//            Column(horizontalAlignment = Alignment.End) {
//                // Nút Hủy đơn nếu đang chờ lấy hàng (TrangThai == 2)
//                if (hoaDonBan.TrangThai == 2) {
//                    Button(
//                        onClick = {
//                            val hoadonHuy = hoaDonBan.copy(TrangThai = 6) // Giả sử 6 là "Đã hủy"
//                            hoaDonBanViewModel.updateHoaDonBan(hoadonHuy)
//                        },
//                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
//                        shape = RoundedCornerShape(10.dp),
//                        modifier = Modifier.height(40.dp)
//                    ) {
//                        Text("Hủy đơn")
//                    }
//                }
//
//                // Nút Trả hàng nếu đã giao (TrangThai == 4)
//                if (hoaDonBan.TrangThai == 4) {
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Button(
//                        onClick = {
//                            val hoadonTra = hoaDonBan.copy(TrangThai = 7) // Giả sử 7 là "Trả hàng"
//                            hoaDonBanViewModel.updateHoaDonBan(hoadonTra)
//                        },
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688)),
//                        shape = RoundedCornerShape(10.dp),
//                        modifier = Modifier.height(40.dp)
//                    ) {
//                        Text("Trả hàng")
//                    }
//                }
//            }
//        }
//    }
//}
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
            confirmButton = {
                TextButton(
                    onClick = {
                        // Cập nhật trạng thái trả hàng với lý do
                        if (returnReason.isNotBlank()) {
                            val hoadonTra = hoaDonBan.copy(
                                TrangThai = 7, // Giả sử 7 là "Trả hàng"
                                LyDoTraHang = returnReason // Thêm trường lý do vào model nếu có
                            )
                            hoaDonBanViewModel.updateHoaDonBan(hoadonTra)
                            Log.d("CardDonHang", "Đã gửi yêu cầu trả hàng: $returnReason")
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
