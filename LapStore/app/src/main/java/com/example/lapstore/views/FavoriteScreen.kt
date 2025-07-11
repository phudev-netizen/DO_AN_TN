import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

    LaunchedEffect(makhachhang) {
        if (makhachhang > 0) {
            sanPhamViewModel.getAllSanPham()
            yeuThichViewModel.loadDanhSach(makhachhang)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("❤️ Yêu Thích", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = {
                        val popped = navController.popBackStack()
                        if (!popped) {
                            navController.navigate("home_screen") { popUpTo("home_screen") { inclusive = true } }
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
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
                        danhSachSanPham.filter { sp -> danhSachYeuThich.any { it.maSanPham == sp.MaSanPham } }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(sanPhamYeuThich, key = { it.MaSanPham }) { sanpham ->
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                                    .clip(RoundedCornerShape(16.dp)),
                                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
                                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                ProductCard(
                                    sanpham = sanpham,
                                    isFavorite = true,
                                    onFavoriteClick = {
                                        yeuThichViewModel.xoaYeuThich(makhachhang, sanpham.MaSanPham) {
                                            Toast.makeText(context, "Đã xoá khỏi yêu thích", Toast.LENGTH_SHORT).show()
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
        Surface(
            shape = RoundedCornerShape(100.dp),
            tonalElevation = 4.dp,
            modifier = Modifier.size(100.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(emoji, style = MaterialTheme.typography.headlineLarge)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
