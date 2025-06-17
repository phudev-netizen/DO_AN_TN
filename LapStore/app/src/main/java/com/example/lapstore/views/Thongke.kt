//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun ThongKeScreen(viewModel: ThongKeViewModel = viewModel(), tentaikhoan: String?) {
//    val thongKe = viewModel.thongKe.value
//    val isLoading = viewModel.isLoading.value
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchThongKe()
//    }
//
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        if (isLoading) {
//            CircularProgressIndicator()
//        } else {
//            thongKe?.let {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    Text("🖥 Tổng sản phẩm: ${it.total_products}")
//                    Text("📦 Tổng đơn hàng: ${it.total_orders}")
//                    Text("💰 Tổng doanh thu: ${it.total_revenue} VND")
//                    Text("👤 Tổng người dùng: ${it.total_users}")
//                    Spacer(modifier = Modifier.height(12.dp))
//                    Text("📊 Doanh thu theo tháng:")
//                    it.monthly_revenue.forEach { month ->
//                        Text("   - Tháng ${month.month}: ${month.revenue} VND")
//                    }
//                }
//            } ?: Text("Không có dữ liệu thống kê.")
//        }
//    }
//}
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ThongKeScreen(viewModel: ThongKeViewModel = viewModel(), tentaikhoan: String?) {
    val thongKe = viewModel.thongKe.value
    val isLoading = viewModel.isLoading.value

    LaunchedEffect(Unit) {
        viewModel.fetchThongKe()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            thongKe?.let {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🖥 Tổng sản phẩm: ${it.total_products}")
                    Text("📦 Tổng đơn hàng: ${it.total_orders}")
                    Text("💰 Tổng doanh thu: ${it.total_revenue} VND")
                    Text("👤 Tổng người dùng: ${it.total_users}")
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("📊 Doanh thu theo tháng:")

                    it.monthly_revenue.forEach { month ->
                        val maxBarWidth = 200.dp // Chiều dài tối đa
                        val scaledWidth = ((month.revenue / 1_000_000).coerceAtMost(200).toFloat()).dp

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Th ${month.month}",
                                modifier = Modifier.width(40.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .height(20.dp)
                                    .width(scaledWidth)
                                    .background(color = Color(0xFF4CAF50))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("${month.revenue} VND")
                        }
                    }
                }
            } ?: Text("Không có dữ liệu thống kê.")
        }
    }
}
