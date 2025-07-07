package com.example.lapstore.models

import ThongKeSanPhamResponse
import ThongKeViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text


@Composable
fun ThongKeScreen(
    viewModel: ThongKeViewModel = viewModel(),
    tentaikhoan: String?,
    onBack: (() -> Unit)? = null
) {
    val thongKe by viewModel.thongKe
    val isLoading by viewModel.isLoading
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val currentYear = remember { Calendar.getInstance().get(Calendar.YEAR) }
    var selectedYear by remember { mutableStateOf(currentYear) }



    // Load khi chọn năm
    LaunchedEffect(selectedYear) {
        viewModel.fetchThongKeTheoNam(selectedYear)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFe3f2fd), Color(0xFFffffff))))
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ){
            Spacer(modifier = Modifier.height(30.dp))

            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xEFF60217))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onBack?.invoke() ?: backDispatcher?.onBackPressed() },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Quay lại",
                        tint = Color(0xFF1976D2)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Thống Kê",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Dropdown chọn năm
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Chọn năm: ", fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(8.dp))

                val years = (2018..currentYear).toList().reversed()
                var expanded by remember { mutableStateOf(false) }

                Box {
                    TextButton(onClick = { expanded = true }) {
                        Text("$selectedYear", fontWeight = FontWeight.Medium)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        years.forEach { year ->
                            DropdownMenuItem(
                                text = { Text(text = year.toString()) },
                                onClick = {
                                    selectedYear = year
                                    expanded = false
                                }
                            )
                        }
                    }
                }

            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color(0xFF1976D2))
                } else {
                    thongKe?.let { data ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, RoundedCornerShape(18.dp))
                                .background(Color.White, RoundedCornerShape(18.dp))
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {


                            // Doanh thu theo tháng
                            Text(
                                "📊 Doanh thu theo tháng năm $selectedYear",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color(0xFF1976D2),
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            // Tổng quan
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                StatCard("🖥", "Sản phẩm", "${data.total_products}")
                                StatCard("📦", "Đơn hàng", "${data.total_orders}")
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                StatCard("💰", "Doanh thu", "${formatCurrency(data.total_revenue)} VND")
                                StatCard("👤", "Người dùng", "${data.total_users}")
                            }
                            ThongKeSanPhamSection(
                                data = viewModel.data.value,
                                loading = viewModel.loading.value,
                                onRefresh = { viewModel.fetchThongKeSanPham() }
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFf3f7fa), RoundedCornerShape(10.dp))
                                    .padding(10.dp)
                            ) {
                                val maxBarWidth = 200.dp
                                val maxRevenue = data.monthly_revenue.maxOfOrNull { it.revenue }?.takeIf { it > 0 } ?: 1

                                data.monthly_revenue.forEach { month ->
                                    val scaledWidth = (month.revenue.toFloat() / maxRevenue * maxBarWidth.value).coerceAtLeast(8f).dp

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Th${month.month}", modifier = Modifier.width(38.dp), color = Color(0xFF1976D2))
                                        Box(
                                            modifier = Modifier
                                                .height(20.dp)
                                                .width(scaledWidth)
                                                .background(
                                                    Brush.horizontalGradient(
                                                        listOf(Color(0xFF4CAF50), Color(0xFF81C784))
                                                    ),
                                                    shape = RoundedCornerShape(6.dp)
                                                )
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("${formatCurrency(month.revenue)} VND", fontSize = 13.sp, color = Color(0xFF388E3C))
                                    }
                                }
                            }
                        }
                    } ?: Text("Không có dữ liệu thống kê.", color = Color.Red, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun StatCard(icon: String, label: String, value: String) {
    Column(
        modifier = Modifier
            .width(155.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFe3f2fd), Color(0xFFbbdefb))
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 12.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = icon, fontSize = 28.sp)
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1976D2)
        )
        Text(
            text = label,
            color = Color(0xFF616161),
            fontSize = 14.sp
        )
    }
}

// Helper function to format currency
fun formatCurrency(amount: Long): String {
    return NumberFormat.getNumberInstance(Locale.US).format(amount)
}


@Composable
fun ThongKeSanPhamSection(
    data: ThongKeSanPhamResponse?,
    loading: Boolean,
    onRefresh: () -> Unit
) {
    LaunchedEffect(Unit) {
        onRefresh()
    }

    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF1976D2))
        }
    } else {
        data?.let {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "🏆 Top 5 sản phẩm bán chạy",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF388E3C)
                )
                Spacer(modifier = Modifier.height(8.dp))
                it.topSelling.forEach { item ->
                    ProductStatCard(
                        name = item.TenSanPham,
                        count = item.totalQty,
                        isTop = true
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "🐢 Top 5 sản phẩm bán chậm",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFFF57C00)
                )
                Spacer(modifier = Modifier.height(8.dp))
                it.slowSelling.forEach { item ->
                    ProductStatCard(
                        name = item.TenSanPham,
                        count = item.totalQty,
                        isTop = false
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "📦 Tổng số sản phẩm đã bán: ${it.totalQtySold}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ProductStatCard(
    name: String,
    count: Int,
    isTop: Boolean
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = if (isTop)
                            listOf(Color(0xFF81C784), Color(0xFF4CAF50))
                        else
                            listOf(Color(0xFFFFB74D), Color(0xFFF57C00))
                    )
                )
                .padding(12.dp)
        ) {
            Icon(
                imageVector = if (isTop) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = "Số lượt: $count",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

