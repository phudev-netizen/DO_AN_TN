import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lapstore.models.SanPham
import com.example.lapstore.views.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavHostController,
    makhachhang: Int,
    tentaikhoan: String?,
    sanPhamViewModel: SanPhamViewModel = viewModel(),
    yeuThichViewModel: YeuThichViewModel = viewModel()
) {
    val context = LocalContext.current
    val danhSachYeuThich by yeuThichViewModel.danhSachYeuThich.observeAsState(emptyList())
    val danhSachSanPham = sanPhamViewModel.danhSachAllSanPham

    LaunchedEffect(Unit) {
        if (makhachhang > 0) {
            sanPhamViewModel.getAllSanPham()
            yeuThichViewModel.loadDanhSach(makhachhang)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("❤️ Yêu Thích", style = MaterialTheme.typography.titleLarge)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            when {
                makhachhang <= 0 -> {
                    EmptyState(
                        emoji = "🔒",
                        title = "Bạn chưa đăng nhập",
                        message = "Vui lòng đăng nhập để xem danh sách yêu thích."
                    )
                }

                danhSachYeuThich.isEmpty() -> {
                    EmptyState(
                        emoji = "🧐",
                        title = "Danh sách trống",
                        message = "Bạn chưa thêm sản phẩm nào vào yêu thích."
                    )
                }

                else -> {
                    val sanPhamYeuThich = remember(danhSachSanPham, danhSachYeuThich) {
                        danhSachSanPham.filter { sp ->
                            danhSachYeuThich.any { it.maSanPham == sp.MaSanPham }
                        }
                    }

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        items(sanPhamYeuThich, key = { it.MaSanPham }) { sanpham ->
                            Card(
                                shape = MaterialTheme.shapes.extraLarge,
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                ProductCard(
                                    sanpham = sanpham,
                                    isFavorite = true,
                                    onFavoriteClick = {
                                        yeuThichViewModel.xoaYeuThich(
                                            makhachhang,
                                            sanpham.MaSanPham
                                        ) {
                                            Toast.makeText(
                                                context,
                                                "Đã xoá khỏi danh sách yêu thích",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            yeuThichViewModel.loadDanhSach(makhachhang)
                                        }
                                    },
                                    navController = navController,
                                    makhachhang = makhachhang.toString(),
                                    tentaikhoan = tentaikhoan
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun EmptyState(
    emoji: String,
    title: String,
    message: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(emoji, fontSize = MaterialTheme.typography.headlineLarge.fontSize)
        Spacer(modifier = Modifier.height(12.dp))
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(message, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
