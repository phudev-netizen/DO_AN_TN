
package com.example.lapstore.views

import NavRoute
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lapstore.viewmodels.TaiKhoanViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    taiKhoanViewModel: TaiKhoanViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()

    var tendangnhap by remember { mutableStateOf("") }
    var matkhau by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    var isDarkTheme by remember { mutableStateOf(true) }

    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else Color(0xFFF5F5F5)
    val neonColor = if (isDarkTheme) Color(0xFF00B8D4) else Color(0xFF1976D2)
    val textColor = if (isDarkTheme) Color.White else Color.Black

    val loginResult = taiKhoanViewModel.loginResult.value

    SideEffect {
        systemUiController.setStatusBarColor(color = backgroundColor, darkIcons = !isDarkTheme)
    }

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = backgroundColor),
                title = {},
                actions = {
                    IconButton(onClick = { isDarkTheme = !isDarkTheme }) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.Brightness7 else Icons.Default.Brightness4,
                            contentDescription = "Chuyển giao diện",
                            tint = neonColor
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "ĐĂNG NHẬP",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = textColor,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = tendangnhap,
                label = { Text("Tên đăng nhập", color = textColor) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = neonColor,
                    unfocusedBorderColor = neonColor,
                    focusedLabelColor = neonColor,
                    cursorColor = neonColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                ),
                shape = RoundedCornerShape(17.dp),
                onValueChange = { tendangnhap = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = matkhau,
                label = { Text("Mật khẩu", color = textColor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "Ẩn mật khẩu" else "Hiển thị mật khẩu",
                            tint = textColor
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = neonColor,
                    unfocusedBorderColor = neonColor,
                    focusedLabelColor = neonColor,
                    cursorColor = neonColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                ),
                shape = RoundedCornerShape(17.dp),
                onValueChange = { matkhau = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .shadow(10.dp),
                shape = RoundedCornerShape(17.dp),
                colors = ButtonDefaults.buttonColors(containerColor = neonColor),
                onClick = {
                    if (tendangnhap.isBlank() || matkhau.isBlank()) {
                        openDialog = true
                    } else {
                        taiKhoanViewModel.kiemTraDangNhap(tendangnhap, matkhau)
                        if (loginResult != null) {
                            if (loginResult.result == true) {
                                navController.navigate("${NavRoute.HOME.route}?tentaikhoan=$tendangnhap") {
                                    popUpTo(0) { inclusive = true }
                                }
                                scope.launch {
                                    snackbarHostState.showSnackbar("Đăng nhập thành công")
                                }
                            } else {
                                openDialog = true
                            }
                        }
                    }
                },
            ) {
                Text(
                    text = "ĐĂNG NHẬP",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkTheme) Color.Black else Color.White
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Bạn chưa có tài khoản?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                TextButton(
                    onClick = { navController.navigate(NavRoute.REGISTERSCREEN.route) }
                ) {
                    Text(
                        "Đăng ký ngay!",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2196F3)
                    )
                }
            }

            SnackbarHost(
                modifier = Modifier.padding(30.dp),
                hostState = snackbarHostState
            )

            if (openDialog) {
                AlertDialog(
                    containerColor = if (isDarkTheme) Color(0xFF1A1A1A) else Color.White,
                    title = {
                        Text("Thông báo", color = textColor)
                    },
                    onDismissRequest = { openDialog = false },
                    text = {
                        Text(
                            if (tendangnhap.isBlank() || matkhau.isBlank()) "Vui lòng nhập đầy đủ thông tin!" else "Tài khoản hoặc mật khẩu không chính xác",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { openDialog = false }) {
                            Text("OK", fontSize = 17.sp, color = neonColor)
                        }
                    },
                )
            }
        }
    }
}

