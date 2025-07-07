package com.example.lapstore.views
import NavRoute
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.lapstore.models.KhachHang
import com.example.lapstore.models.TaiKhoan
import com.example.lapstore.viewmodels.KhachHangViewModel
import com.example.lapstore.viewmodels.TaiKhoanViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun VerifyOTPScreen(
    navController: NavHostController,
    taiKhoanViewModel: TaiKhoanViewModel,
    khachHangViewModel: KhachHangViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var otp by remember { mutableStateOf("") }

    val email = navController.previousBackStackEntry
        ?.savedStateHandle?.get<String>("email") ?: ""
    val username = navController.previousBackStackEntry
        ?.savedStateHandle?.get<String>("username") ?: ""
    val password = navController.previousBackStackEntry
        ?.savedStateHandle?.get<String>("password") ?: ""

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Nhập mã OTP", fontSize = 26.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("Mã OTP") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val queue = Volley.newRequestQueue(context)
                    val url = "http://10.0.2.2/lap_store_api/api/TaiKhoan/verify_email_otp.php"
                   // val url = "http://192.168.3.49/lap_store_api/api/TaiKhoan/verify_email_otp.php"

                    val body = JSONObject().apply {
                        put("email", email)
                        put("otp", otp)
                    }

                    val request = JsonObjectRequest(
                        com.android.volley.Request.Method.POST,
                        url,
                        body,
                        { response ->
                            val success = response.getBoolean("success")
                            if (success) {
                                // OTP đúng → tạo tài khoản
                                val khachhang = KhachHang(
                                    MaKhachHang = 0,
                                    HoTen = username,
                                    GioiTinh = "Nam",
                                    NgaySinh = "",
                                    Email = email,
                                    SoDienThoai = ""
                                )
                                khachHangViewModel.ThemKhachHang(khachhang)

                                val taikhoan = TaiKhoan(
                                    TenTaiKhoan = username,
                                    MaKhachHang = 0,
                                    MatKhau = password,
                                    LoaiTaiKhoan = 0,
                                    TrangThai = 1
                                )
                                taiKhoanViewModel.TaoTaiKhoan(taikhoan)

                                Toast.makeText(context, "Xác thực thành công!", Toast.LENGTH_SHORT).show()
                                navController.navigate(NavRoute.LOGINSCREEN.route) {
                                    popUpTo(NavRoute.REGISTERSCREEN.route) { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, "Mã OTP không đúng hoặc đã hết hạn!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        { error ->
                            Toast.makeText(context, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    )

                    queue.add(request)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Xác nhận", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
