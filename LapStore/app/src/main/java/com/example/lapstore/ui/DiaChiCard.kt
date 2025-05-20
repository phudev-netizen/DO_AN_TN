import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lapstore.models.DiaChi
import com.example.lapstore.models.KhachHang

@Composable
fun DiaChiCard(
    diachi:DiaChi,
    navController: NavHostController,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {

        }
    ) {
        Column (
            modifier = Modifier.padding(5.dp).padding(7.dp),
            verticalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Họ Tên: ${diachi.TenNguoiNhan}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )

                TextButton(
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        navController.navigate("${NavRoute.UPDATEDIACHI.route}?makhachhang=${diachi.MaKhachHang}&madiachi=${diachi.MaDiaChi}")
                    }
                ) {
                    Text(
                        "Chỉnh sửa",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
            }

            Text(
                "Số điện thoai: ${diachi.SoDienThoai}",
                fontSize = 18.sp,
            )
            Text(
                "Địa chỉ: ${diachi.ThongTinDiaChi}",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = 18.sp,
            )

            if(diachi.MacDinh==1){
                Text(
                    "Mặc đinh",
                    fontSize = 13.sp,
                    modifier = Modifier.border(1.dp, Color.Red, shape = RoundedCornerShape(10.dp))
                        .padding(2.dp),
                    color = Color.Red
                )
            }
        }
    }
}