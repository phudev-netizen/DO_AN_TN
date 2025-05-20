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
    var snackbarHostState = remember {
        SnackbarHostState()
    }
    var scope = rememberCoroutineScope()

    var matkhaucu by remember { mutableStateOf("") }
    var matkhaumoi by remember { mutableStateOf("") }
    var kiemtramkmoi by remember { mutableStateOf("") }

    var taiKhoanViewModel: TaiKhoanViewModel = viewModel()

    var taikhoan = taiKhoanViewModel.taikhoan

    taiKhoanViewModel.getTaiKhoanByTentaikhoan(tentaikhoan)

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Đổi mật khẩu", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = matkhaucu,
                label = { Text("Mật khẩu cũ") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Red,
                    focusedLabelColor = Color.Red
                ),
                shape = RoundedCornerShape(17.dp),
                onValueChange = { matkhaucu = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = matkhaumoi,
                label = { Text("Mật khẩu mới") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Red,
                    focusedLabelColor = Color.Red
                ),
                shape = RoundedCornerShape(17.dp),
                onValueChange = { matkhaumoi = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = kiemtramkmoi,
                label = { Text("Nhập lại mật khẩu") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Red,
                    focusedLabelColor = Color.Red
                ),
                shape = RoundedCornerShape(17.dp),
                onValueChange = { kiemtramkmoi = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            SnackbarHost(
                modifier = Modifier.padding(4.dp),
                hostState = snackbarHostState
            )
            Button(
                onClick = {
                    if (taikhoan != null) {
                        if (tentaikhoan.isEmpty() || matkhaucu.isEmpty() || kiemtramkmoi.isEmpty()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Vui lòng nhập đày đủ thông tin."
                                )
                            }
                        }else if(matkhaumoi!=kiemtramkmoi){
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Xác nhận mật khẩu không khớp."
                                )
                            }
                        } else if (tentaikhoan.contains(" ")) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Username không được chứa khoảng trắng."
                                )
                            }
                        } else if (matkhaumoi.contains(tentaikhoan)) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Password không được chứa Username."
                                )
                            }
                        } else if (matkhaumoi.length < 8) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Password phải từ 8 ký tự trở lên."
                                )
                            }
                        }else{
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Đổi mật khẩu thành công."
                                )
                            }
                            val updatedTaiKhoan = TaiKhoan(tentaikhoan, taikhoan.MaKhachHang, matkhaumoi, 0, 1)
                            taiKhoanViewModel.updateTaiKhoan(updatedTaiKhoan)
                        }
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

