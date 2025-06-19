package com.example.lapstore.views

import SanPhamViewModel
import YeuThichViewModel
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavHostController,
    makhachhang: Int, // SỬA thành Int
    tentaikhoan: String?,
    sanPhamViewModel: SanPhamViewModel = viewModel(),
    yeuThichViewModel: YeuThichViewModel = viewModel()
) {
    val context = LocalContext.current
    val danhSachYeuThich by yeuThichViewModel.danhSachYeuThich.observeAsState(emptyList())
    val danhSachSanPham = sanPhamViewModel.danhSachAllSanPham
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(makhachhang) {
        yeuThichViewModel.loadDanhSach(makhachhang)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sản phẩm yêu thích") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (danhSachYeuThich.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Bạn chưa có sản phẩm nào trong danh sách yêu thích.")
                }
            } else {
                val sanPhamYeuThich = remember(danhSachYeuThich, danhSachSanPham) {
                    danhSachYeuThich.mapNotNull { yeuThich ->
                        danhSachSanPham.find { it.MaSanPham == yeuThich.maSanPham }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(sanPhamYeuThich, key = { it.MaSanPham }) { sanpham ->
                        ProductCard(
                            sanpham = sanpham,
                            isFavorite = true,
                            onFavoriteClick = {
                                yeuThichViewModel.xoaYeuThich(
                                    makhachhang, // Đã là Int
                                    sanpham.MaSanPham.toString()
                                )
                                Toast.makeText(context, "Đã xoá khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show()
                                yeuThichViewModel.loadDanhSach(makhachhang)
                            },
                            navController = navController,
                            makhachhang = makhachhang.toString(), // Nếu ProductCard yêu cầu String
                            tentaikhoan = tentaikhoan
                        )
                    }
                }
            }
        }
    }
}