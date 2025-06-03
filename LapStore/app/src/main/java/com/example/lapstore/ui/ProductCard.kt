//
//import android.icu.text.DecimalFormat
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.outlined.FavoriteBorder
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import coil.compose.AsyncImage
//import com.example.lapstore.api.addToFavorite
//import com.example.lapstore.api.removeFromFavorite
//import com.example.lapstore.models.SanPham
//import kotlinx.coroutines.launch
//
//
//@Composable
//fun ProductCard(
//    maKhachHang: Int,
//    isFavorite: Boolean,
//    onFavoriteStateChanged: (Boolean) -> Unit,
//    addToFavorite: suspend (Int, Int) -> Unit,
//    removeFromFavorite: suspend (Int, Int) -> Unit,
//    sanpham: SanPham,
//    makhachhang: String?,
//    tentaikhoan: String?,
//    navController: NavHostController
//) {
//    var isFavorite by remember { mutableStateOf(false) }
//
//    Card(
//        modifier = Modifier
//            .padding(8.dp)
//            .size(width = 260.dp, height = 480.dp),
//        shape = RoundedCornerShape(10.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//        onClick = {
//            if (tentaikhoan != null)
//                navController.navigate(
//                    "product_detail_screen?id=${sanpham.MaSanPham}&makhachhang=$makhachhang&tentaikhoan=$tentaikhoan"
//                )
//            else
//                navController.navigate(
//                    "product_detail_screen?id=${sanpham.MaSanPham}&makhachhang=$makhachhang"
//                )
//        }
//    ) {
//        Box {
//            Column(modifier = Modifier.padding(10.dp)) {
//                AsyncImage(
//                    model = sanpham.HinhAnh,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .size(200.dp),
//                    contentScale = ContentScale.Fit
//                )
//
//                Text(
//                    text = sanpham.TenSanPham,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(vertical = 4.dp)
//                )
//
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(120.dp),
//                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE)),
//                    shape = RoundedCornerShape(8.dp)
//                ) {
//                    Column(
//                        modifier = Modifier.padding(10.dp),
//                        verticalArrangement = Arrangement.SpaceEvenly
//                    ) {
//                        Text("CPU: ${sanpham.CPU}", fontSize = 14.sp)
//                        Text("Card: ${sanpham.CardManHinh}", fontSize = 14.sp)
//                        Text("RAM: ${sanpham.RAM}", fontSize = 14.sp)
//                        Text("SSD: ${sanpham.SSD}", fontSize = 14.sp)
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = "Giá: ${formatGiaTien(sanpham.Gia)}",
//                        fontSize = 16.sp,
//                        color = Color.Red,
//                        fontWeight = FontWeight.Bold
//                    )
//                    if (sanpham.SoLuong == 0) {
//                        Text(
//                            text = "(Hết hàng)",
//                            fontSize = 16.sp,
//                            color = Color.Red,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//            }
//
//            // Nút yêu thích ở góc trái trên
//            val scope = rememberCoroutineScope()
//            IconButton(
//                onClick = { },
//                modifier = Modifier
//                    .align(Alignment.TopStart)
//                    .padding(8.dp)
//                    .size(32.dp)
//                    .clip(CircleShape)
//                    .background(Color.White.copy(alpha = 0.8f))
//            ) {
//                Icon(
//                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
//                    contentDescription = "Favorite",
//                    tint = if (isFavorite) Color.Red else Color.Gray
//                )
//            }
//        }
//    }
//}
//
//
//fun formatGiaTien(gia: Int): String {
//    val formatter = DecimalFormat("#,###")
//    return "${formatter.format(gia)}đ"
//}
//

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lapstore.models.SanPham
import kotlinx.coroutines.launch

@Composable
fun ProductCard(
    maKhachHang: Int?,
    isFavorite: Boolean,
    onFavoriteStateChanged: (Boolean) -> Unit,
    addToFavorite: suspend (Int, Int) -> Unit,
    removeFromFavorite: suspend (Int, Int) -> Unit,
    sanpham: SanPham,
    makhachhang: String?,
    tentaikhoan: String?,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 260.dp, height = 480.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            if (tentaikhoan != null)
                navController.navigate(
                    "productdetail_screen?id=${sanpham.MaSanPham}&makhachhang=$makhachhang&tentaikhoan=$tentaikhoan"
                )
            else
                navController.navigate(
                    "productdetail_screen?id=${sanpham.MaSanPham}&makhachhang=$makhachhang"
                )
        }
    ) {
        Box {
            Column(modifier = Modifier.padding(10.dp)) {
                AsyncImage(
                    model = sanpham.HinhAnh,
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text("CPU: ${sanpham.CPU}", fontSize = 14.sp)
                        Text("Card: ${sanpham.CardManHinh}", fontSize = 14.sp)
                        Text("RAM: ${sanpham.RAM}", fontSize = 14.sp)
                        Text("SSD: ${sanpham.SSD}", fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Giá: ${formatGiaTien(sanpham.Gia)}",
                        fontSize = 16.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                    if (sanpham.SoLuong == 0) {
                        Text(
                            text = "(Hết hàng)",
                            fontSize = 16.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

// Nút yêu thích ở góc trái trên
            IconButton(
                onClick = {
                    scope.launch {
                        if (isFavorite) {
                            maKhachHang?.let { removeFromFavorite(it, sanpham.MaSanPham) }
                            onFavoriteStateChanged(false)
                        } else {
                            maKhachHang?.let { addToFavorite(it, sanpham.MaSanPham) }
                            onFavoriteStateChanged(true)
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.8f))
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

fun formatGiaTien(gia: Int): String {
    val formatter = DecimalFormat("#,###")
    return "${formatter.format(gia)}đ"
}