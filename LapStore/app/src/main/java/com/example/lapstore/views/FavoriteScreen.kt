package com.example.lapstore.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lapstore.models.SanPham
import com.example.lapstore.viewmodels.YeuThichViewModel
import com.example.lapstore.viewmodels.TaiKhoanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteProductsScreen(
    navController: NavHostController,
    allProducts: List<SanPham>,
    yeuThichViewModel: YeuThichViewModel,
    taiKhoanViewModel: TaiKhoanViewModel
) {
    val favoriteIds by yeuThichViewModel.favoriteIds.collectAsState()
    val taikhoan = taiKhoanViewModel.taikhoan
    val favoriteProducts = remember(favoriteIds, allProducts) {
        allProducts.filter { it.MaSanPham in favoriteIds }
    }
    val errorMessage by yeuThichViewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sản phẩm yêu thích") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (taikhoan == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Bạn cần đăng nhập để xem Sản phẩm yêu thích.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                if (errorMessage != null) {
                    Text(
                        text = "Lỗi: $errorMessage",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                if (favoriteProducts.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Bạn chưa có sản phẩm yêu thích nào.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        items(favoriteProducts) { sanpham ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.medium,
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                ProductCard(
                                    sanpham = sanpham,
                                    isFavorite = true,
                                    onFavoriteClick = {
                                        taikhoan.MaKhachHang?.let {
                                            yeuThichViewModel.toggleFavorite(sanpham.MaSanPham, it)
                                        }
                                    },
                                    navController = navController,
                                    makhachhang = taikhoan.MaKhachHang.toString(),
                                    tentaikhoan = taikhoan.TenTaiKhoan
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
