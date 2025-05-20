import android.icu.text.DecimalFormat
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lapstore.models.SanPham

@Composable
fun ProductCard(
    sanpham: SanPham,
    makhachhang:String?,
    tentaikhoan:String?,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 260.dp, height = 480.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            if(tentaikhoan != null)
                navController.navigate(NavRoute.PRODUCTDETAILSCREEN.route + "?id=${sanpham.MaSanPham}&makhachhang=${makhachhang}&tentaikhoan=${tentaikhoan}")
            else
                navController.navigate(NavRoute.PRODUCTDETAILSCREEN.route + "?id=${sanpham.MaSanPham}&makhachhang=${makhachhang}")
        }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                model= sanpham.HinhAnh,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(200.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = sanpham.TenSanPham,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Card(
                modifier = Modifier.size(width = 270.dp, height = 120.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFCCCCCC)),
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text("CPU " + sanpham.CPU, fontWeight = FontWeight.Bold)
                    Text("Card " + sanpham.CardManHinh, fontWeight = FontWeight.Bold)
                    Text(sanpham.RAM, fontWeight = FontWeight.Bold)
                    Text(sanpham.SSD, fontWeight = FontWeight.Bold)
                }

            }
            // Giá sản phẩm
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Giá: ${formatGiaTien(sanpham.Gia)}",
                    fontSize = 16.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                if(sanpham.SoLuong == 0){
                    Text(
                        text = "(Hết hàng)",
                        fontSize = 16.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

        }
    }
}

fun formatGiaTien(gia: Int): String {
    val formatter = DecimalFormat("#,###")
    return "${formatter.format(gia)}đ"
}
