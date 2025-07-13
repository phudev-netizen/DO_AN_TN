package com.example.lapstore

import HinhAnhViewModel
import NavRoute.ACCOUNT.NavgationGraph
import SanPhamViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.lapstore.ui.theme.LapStoreTheme
import com.example.lapstore.viewmodels.KhachHangViewModel
import com.example.lapstore.viewmodels.TaiKhoanViewModel

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//
//            LapStoreTheme {
//                val navController = rememberNavController()
//                val sanPhamViewModel = SanPhamViewModel()
//                val hinhAnhViewModel = HinhAnhViewModel()
//                val khachHangViewModel = KhachHangViewModel()
//                val taiKhoanViewModel = TaiKhoanViewModel()
//
//                NavgationGraph(
//                    navController = navController,
//                    sanPhamViewModel = sanPhamViewModel,
//                    hinhAnhViewModel = hinhAnhViewModel,
//                    khachHangViewModel = khachHangViewModel,
//                    taiKhoanViewModel = taiKhoanViewModel,
//                    viewmodel = viewModel()
//                )
//            }
//        }
//    }
//}
//
class MainActivity : ComponentActivity() {

    private var currentIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentIntent = intent

        enableEdgeToEdge()
        setContent {
            LapStoreTheme {
                val navController = rememberNavController()
                val sanPhamViewModel = SanPhamViewModel()
                val hinhAnhViewModel = HinhAnhViewModel()
                val khachHangViewModel = KhachHangViewModel()
                val taiKhoanViewModel = TaiKhoanViewModel()

                // Xử lý deep link
                LaunchedEffect(currentIntent) {
                    currentIntent?.data?.let { uri ->
                        if (uri.scheme == "mylapstore" && uri.host == "paysuccess") {
                            val tentaikhoan = uri.getQueryParameter("tentaikhoan") ?: ""
                            navController.navigate("paysuccess_screen?tentaikhoan=$tentaikhoan")
                        }
                    }
                }

                NavgationGraph(
                    navController = navController,
                    sanPhamViewModel = sanPhamViewModel,
                    hinhAnhViewModel = hinhAnhViewModel,
                    khachHangViewModel = khachHangViewModel,
                    taiKhoanViewModel = taiKhoanViewModel,
                    viewmodel = viewModel()
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        currentIntent = intent
    }
}
