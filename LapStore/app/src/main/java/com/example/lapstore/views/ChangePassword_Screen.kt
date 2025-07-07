import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lapstore.models.TaiKhoan
import com.example.lapstore.viewmodels.TaiKhoanViewModel
import kotlinx.coroutines.launch


@Composable
fun ChangePasswordSection(
    tentaikhoan: String
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var matkhaucu by remember { mutableStateOf("") }
    var matkhaumoi by remember { mutableStateOf("") }
    var kiemtramkmoi by remember { mutableStateOf("") }

    val taiKhoanViewModel: TaiKhoanViewModel = viewModel()
    val taikhoan = taiKhoanViewModel.taikhoan
    // Load thông tin tài khoản chỉ 1 lần
    LaunchedEffect(tentaikhoan) {
        taiKhoanViewModel.getTaiKhoanByTentaikhoan(tentaikhoan)
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Đổi mật khẩu", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = matkhaucu,
                onValueChange = { matkhaucu = it },
                label = { Text("Mật khẩu cũ") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Red,
                    focusedLabelColor = Color.Red
                ),
                shape = RoundedCornerShape(17.dp)
            )

            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = matkhaumoi,
                onValueChange = { matkhaumoi = it },
                label = { Text("Mật khẩu mới") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Red,
                    focusedLabelColor = Color.Red
                ),
                shape = RoundedCornerShape(17.dp)
            )

            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = kiemtramkmoi,
                onValueChange = { kiemtramkmoi = it },
                label = { Text("Nhập lại mật khẩu") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Red,
                    focusedLabelColor = Color.Red
                ),
                shape = RoundedCornerShape(17.dp)
            )

            Spacer(Modifier.height(8.dp))
            SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(4.dp))

            Button(
                onClick = {
                    // 1. Kiểm tra đã load tài khoản chưa
                    if (taikhoan == null) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Lỗi: không tải được thông tin tài khoản.")
                        }
                        return@Button
                    }

                    // 2. Nhập đủ thông tin?
                    if (matkhaucu.isBlank() || matkhaumoi.isBlank() || kiemtramkmoi.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Vui lòng nhập đầy đủ thông tin.")
                        }
                        return@Button
                    }

                    // 3. Sai mật khẩu cũ?
                    if (matkhaucu != taikhoan.MatKhau) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Mật khẩu cũ không đúng.")
                        }
                        return@Button
                    }

                    // 4. Mật khẩu mới trùng mật khẩu cũ?
                    if (matkhaumoi == matkhaucu) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Mật khẩu mới phải khác mật khẩu cũ.")
                        }
                        return@Button
                    }

                    // 5. Xác nhận mật khẩu mới khớp?
                    if (matkhaumoi != kiemtramkmoi) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Xác nhận mật khẩu không khớp.")
                        }
                        return@Button
                    }

                    // 6. Username không chứa khoảng trắng
                    if (tentaikhoan.contains(" ")) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Username không được chứa khoảng trắng.")
                        }
                        return@Button
                    }

                    // 7. Password không chứa username
                    if (matkhaumoi.contains(tentaikhoan)) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Password không được chứa Username.")
                        }
                        return@Button
                    }

                    // 8. Độ dài >= 8
                    if (matkhaumoi.length < 8) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Password phải từ 8 ký tự trở lên.")
                        }
                        return@Button
                    }

                    // Tất cả OK => Cập nhật
                    val updated = taikhoan.copy(
                        MatKhau = matkhaumoi
                        // giữ nguyên các trường khác như MaKhachHang, VaiTro, TrangThai...
                    )
                    taiKhoanViewModel.updateTaiKhoan(updated)
                    scope.launch {
                        snackbarHostState.showSnackbar("Đổi mật khẩu thành công.")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ĐỔI MẬT KHẨU", color = Color.White)
            }
        }
    }
}

