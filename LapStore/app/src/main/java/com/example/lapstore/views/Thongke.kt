package com.example.lapstore.models

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
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ThongKeScreen(
    viewModel: ThongKeViewModel = viewModel(),
    tentaikhoan: String?,
    onBack: (() -> Unit)? = null // Optional callback for back navigation
) {
    val thongKe = viewModel.thongKe.value
    val isLoading = viewModel.isLoading.value
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(Unit) {
        viewModel.fetchThongKe()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFe3f2fd), Color(0xFFffffff))
                )
            )
    ) {
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            // Top App Bar with Back Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xEFF60217))
                    .padding(vertical = 12.dp, horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (onBack != null) {
                            onBack()
                        } else {
                            backDispatcher?.onBackPressed()
                        }
                    },
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
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Thống Kê",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
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
                    thongKe?.let {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, RoundedCornerShape(18.dp))
                                .background(Color.White, RoundedCornerShape(18.dp))
                                .padding(20.dp)
                                .align(Alignment.Center),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Summary Stats
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                StatCard("🖥", "Sản phẩm", "${it.total_products}")
                                StatCard("📦", "Đơn hàng", "${it.total_orders}")
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                StatCard("💰", "Doanh thu", "${formatCurrency(it.total_revenue)} VND")
                                StatCard("👤", "Người dùng", "${it.total_users}")
                            }

                            // Monthly Revenue Section
                            Text(
                                "📊 Doanh thu theo tháng",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color(0xFF1976D2),
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 14.dp, bottom = 2.dp),
                                textAlign = TextAlign.Center
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFf3f7fa), RoundedCornerShape(10.dp))
                                    .padding(10.dp)
                            ) {
                                it.monthly_revenue.forEach { month ->
                                    val maxBarWidth = 200.dp
                                    val maxRevenue = it.monthly_revenue.maxOfOrNull { m -> m.revenue }?.takeIf { it > 0 } ?: 1
                                    val scaledWidth = (month.revenue.toFloat() / maxRevenue * maxBarWidth.value).coerceAtLeast(8f).dp

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            "Th${month.month}",
                                            modifier = Modifier.width(38.dp),
                                            color = Color(0xFF1976D2),
                                            fontWeight = FontWeight.Bold
                                        )
                                        Box(
                                            modifier = Modifier
                                                .height(20.dp)
                                                .width(scaledWidth)
                                                .background(
                                                    brush = Brush.horizontalGradient(
                                                        colors = listOf(Color(0xFF4CAF50), Color(0xFF81C784))
                                                    ),
                                                    shape = RoundedCornerShape(6.dp)
                                                )
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "${formatCurrency(month.revenue)} VND",
                                            fontSize = 13.sp,
                                            color = Color(0xFF388E3C)
                                        )
                                    }
                                }
                            }
                        }
                    } ?: Text(
                        "Không có dữ liệu thống kê.",
                        color = Color.Red,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

// Helper composable for stat cards
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