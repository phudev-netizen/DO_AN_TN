// File: Register_Screen.kt
package com.example.lapstore.views

import NavRoute
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lapstore.models.KhachHang
import com.example.lapstore.models.TaiKhoan
import com.example.lapstore.viewmodels.KhachHangViewModel
import com.example.lapstore.viewmodels.TaiKhoanViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    taiKhoanViewModel: TaiKhoanViewModel,
    khachHangViewModel: KhachHangViewModel
) {
    val systemUiController = rememberSystemUiController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var tentaikhoan by remember { mutableStateOf("") }
    var matkhau by remember { mutableStateOf("") }
    var confirmMatkhau by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var issuccess by remember { mutableStateOf(false) }

    var isDarkTheme by remember { mutableStateOf(true) }

    val openDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }

    // Màu động theo giao diện
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else Color(0xFFF5F5F5)
    val primaryColor = if (isDarkTheme) Color(0xFF00B8D4) else Color(0xFF1976D2)
    val textColor = if (isDarkTheme) Color.White else Color.Black

    SideEffect {
        systemUiController.setStatusBarColor(color = backgroundColor, darkIcons = !isDarkTheme)
    }

    Scaffold(
        containerColor = backgroundColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = backgroundColor),
                title = {},
                actions = {
                    IconButton(onClick = { isDarkTheme = !isDarkTheme }) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.Brightness7 else Icons.Default.Brightness4,
                            contentDescription = "Chuyển giao diện",
                            tint = primaryColor
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "TẠO TÀI KHOẢN",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            @Composable
            fun styledField(value: String, label: String, onChange: (String) -> Unit) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = value,
                    onValueChange = onChange,
                    label = { Text(label, color = textColor) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = primaryColor,
                        focusedLabelColor = primaryColor,
                        cursorColor = primaryColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
            }

            styledField(tentaikhoan, "Tên đăng nhập", { tentaikhoan = it })
            styledField(email, "Email", { email = it })
            styledField(matkhau, "Mật khẩu", { matkhau = it })
            styledField(confirmMatkhau, "Xác nhận mật khẩu", { confirmMatkhau = it })

            Button(
                onClick = {
                    when {
                        tentaikhoan.isBlank() || matkhau.isBlank() || confirmMatkhau.isBlank() || email.isBlank() -> {
                            dialogMessage.value = "Vui lòng nhập đầy đủ thông tin."
                            openDialog.value = true
                        }
                        !email.contains("@") -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Email không hợp lệ.")
                            }
                        }
                        tentaikhoan.contains(" ") -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Tên đăng nhập không được chứa khoảng trắng.")
                            }
                        }
                        matkhau.contains(tentaikhoan) -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Mật khẩu không được chứa tên đăng nhập.")
                            }
                        }
                        matkhau.length < 8 -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Mật khẩu phải từ 8 ký tự trở lên.")
                            }
                        }
                        matkhau != confirmMatkhau -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Mật khẩu xác nhận không khớp.")
                            }
                        }
                        else -> {
                            taiKhoanViewModel.kiemTraTrungUsername(tentaikhoan)
                            val result = taiKhoanViewModel.checkUsernameResult.value
                            if (result?.result == true) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Tên đăng nhập đã tồn tại.")
                                }
                            } else {
                                val khachhang = KhachHang(
                                    MaKhachHang = 0,
                                    HoTen = tentaikhoan,
                                    GioiTinh = "Nam",
                                    NgaySinh = "",
                                    Email = email,
                                    SoDienThoai = ""
                                )
                                khachHangViewModel.ThemKhachHang(khachhang)

                                val taikhoan = TaiKhoan(
                                    TenTaiKhoan = tentaikhoan,
                                    MaKhachHang = 0,
                                    MatKhau = confirmMatkhau,
                                    LoaiTaiKhoan = 0,
                                    TrangThai = 1
                                )
                                taiKhoanViewModel.TaoTaiKhoan(taikhoan)

                                dialogMessage.value = "Đăng ký thành công."
                                openDialog.value = true
                                issuccess = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text("ĐĂNG KÝ", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Row(
                modifier = Modifier.padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Bạn đã có tài khoản?", fontSize = 16.sp, color = textColor)
                TextButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(NavRoute.LOGINSCREEN.route)
                }) {
                    Text("Đăng nhập", color = Color(0xFF2196F3), fontWeight = FontWeight.Bold)
                }
            }
        }

        if (openDialog.value) {
            AlertDialog(
                containerColor = if (isDarkTheme) Color(0xFF1A1A1A) else Color.White,
                onDismissRequest = { openDialog.value = false },
                title = {
                    Text("Thông báo", fontWeight = FontWeight.Bold, color = primaryColor)
                },
                text = {
                    Text(dialogMessage.value, fontSize = 16.sp, color = textColor)
                },
                confirmButton = {
                    TextButton(onClick = {
                        openDialog.value = false
                        if (issuccess) navController.navigate(NavRoute.LOGINSCREEN.route)
                    }) {
                        Text("OK", color = primaryColor, fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    }
}

