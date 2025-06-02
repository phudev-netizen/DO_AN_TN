import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.lapstore.models.GioHang
import com.example.lapstore.models.SanPham
import com.example.lapstore.viewmodels.GioHangViewModel

@Composable
fun SanPhamCard(
    sanPham: SanPham,
    giohang: GioHang,
    tongtien:Int
) {
    var soLuong by remember { mutableStateOf(giohang.SoLuong) }
    var chieucaocard by remember { mutableStateOf(150) }

    var gioHangViewModel:GioHangViewModel = viewModel()

    fun updateCart(newSoLuong: Int) {
        if (newSoLuong != soLuong) {
            val giohangNew = GioHang(giohang.MaGioHang, giohang.MaKhachHang, giohang.MaSanPham, newSoLuong, 1)
            gioHangViewModel.updateGioHang(giohangNew)
            soLuong = newSoLuong
        }
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(chieucaocard.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = sanPham.HinhAnh,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp),
                contentScale = ContentScale.Fit
            )

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    sanPham.TenSanPham,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30.sp
                )
                Text(
                    "${formatGiaTien(sanPham.Gia)}",
                    color = Color.Red
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(
                            onClick = {
                                if (soLuong > 1) {
                                    updateCart(soLuong - 1)
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Filled.RemoveCircleOutline, contentDescription = "")
                        }

                        Text(soLuong.toString())

                        IconButton(
                            onClick = {
                                if (soLuong < sanPham.SoLuong) {
                                    updateCart(soLuong + 1)
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Filled.AddCircleOutline, contentDescription = "")
                        }
                    }

                    if (sanPham.SoLuong <= 5) {
                        Text(
                            "Còn lại: ${sanPham.SoLuong}",
                            color = Color.Red
                        )
                    }

                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "",
                            tint = Color.Red
                        )
                    }
                }

                // Hiển thị cảnh báo nếu số lượng vượt quá tồn kho
                if (soLuong > sanPham.SoLuong) {
                    Text(
                        "Số lượng sản phẩm chỉ còn ${sanPham.SoLuong}",
                        color = Color.Red
                    )
                    chieucaocard = 190
                } else {
                    chieucaocard = 150
                }
            }
        }
    }
}


















