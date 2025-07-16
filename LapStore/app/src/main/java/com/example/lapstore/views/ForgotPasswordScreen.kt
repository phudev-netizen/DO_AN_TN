package com.example.lapstore.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lapstore.viewmodels.TaiKhoanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
    taiKhoanViewModel: TaiKhoanViewModel = viewModel()
) {
    var input by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf<String?>(null) }
    val result by taiKhoanViewModel.forgotPassResult


    LaunchedEffect(result) {
        result?.let {
            dialogMessage = it.message
            taiKhoanViewModel.resetForgotPassResult()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quên mật khẩu") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Nhập tên đăng nhập hoặc email để lấy lại mật khẩu")
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Tên đăng nhập hoặc Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { taiKhoanViewModel.forgotPassword(input.trim()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Gửi yêu cầu")
            }
        }
    }

    // Dialog thông báo kết quả
    if (dialogMessage != null) {
        AlertDialog(
            onDismissRequest = { /* không đóng ngoài nút */ },
            title = { Text("Thông báo") },
            text = { Text(dialogMessage!!) },
            confirmButton = {
                TextButton(onClick = {
                    if (result?.success == true) {
                        navController.popBackStack()
                    }
                    dialogMessage = null
                    taiKhoanViewModel.resetForgotPassResult()
                }) {
                    Text("OK")
                }
            }
        )
    }
}
