
data class ThongKeResponse(
    val success: Boolean,
    val data: ThongKeData?
)

data class ThongKeData(
    val total_products: Int,
    val total_orders: Int,
    val total_revenue: Long,
    val total_users: Int,
    val monthly_revenue: List<MonthlyRevenue>
)

data class MonthlyRevenue(
    val month: Int,
    val revenue: Long
)
data class ThongKeError(
    val success: Boolean,
    val message: String
)