package com.example.lapstore

import HinhAnhViewModel
import NavRoute
import NavRoute.ACCESSORY.NavgationGraph
import SanPhamViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.lapstore.ui.theme.LapStoreTheme
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lapstore.models.SanPham
import com.example.lapstore.viewmodels.TaiKhoanViewModel
import com.example.lapstore.views.LoginScreen
import com.example.lapstore.models.TaiKhoan
import com.example.lapstore.viewmodels.KhachHangViewModel
import android.widget.Toast

data class CategoryData(
    val title: String,
    val items: List<String>,
    val icon: ImageVector,
)

@Composable
fun CategoryMenuMain(
    onItemClick: (String) -> Unit // callback khi chọn item
) {
    val categories = listOf(
        // Có thể thêm nhiều CategoryData hơn nếu muốn
        CategoryData("PHỤ KIỆN", listOf("RAM", "Bàn Phím"), Icons.Filled.Headset)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(categories.size) { index ->
            val category = categories[index]
            CategoryMenu(
                title = category.title,
                items = category.items,
                icon = category.icon,
                onItemClick = onItemClick
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CategoryMenu(
    title: String,
    items: List<String>,
    icon: ImageVector,
    onItemClick: (String) -> Unit // callback khi chọn item
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Tiêu đề danh mục (Category) với khả năng mở rộng/thu gọn
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(modifier = Modifier.padding(start = 16.dp)) {
                items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClick(item) }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

// Hàm màn hình chính demo xử lý khi chọn mục con
@Composable
fun MainScreen() {
    val context = LocalContext.current
    CategoryMenuMain { selectedItem ->
        Toast.makeText(context, "Bạn chọn: $selectedItem", Toast.LENGTH_SHORT).show()
        // Thay logic ở đây nếu muốn điều hướng hoặc xử lý khác
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LapStoreTheme {
                // Nếu muốn sử dụng Navigation thì thay MainScreen() bằng NavgationGraph(...)
                MainScreen()
                // Hoặc:
                 val navController = rememberNavController()
                 val viewModel = SanPhamViewModel()
                 val hinhAnhViewModel = HinhAnhViewModel()
                 val KhachHangViewModel = KhachHangViewModel()
                 val taiKhoanViewModel = TaiKhoanViewModel()
                 NavgationGraph(
                     navController,
                     viewModel,
                     hinhAnhViewModel,
                     KhachHangViewModel,
                     taiKhoanViewModel
                 )
            }
        }
    }
}