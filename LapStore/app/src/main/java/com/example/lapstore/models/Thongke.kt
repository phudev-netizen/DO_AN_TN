data class ThongKeResponse(
    val success: Boolean,
    val data: ThongKeData?
)

data class ThongKeData(
    val total_products: Int,
    val total_orders: Int,
    val total_revenue: Long,
    val total_users: Int,
    val monthly_revenue: List<MonthlyRevenue>,
    val orders: List<Order>
)

data class MonthlyRevenue(
    val month: Int,
    val revenue: Long
)
data class ThongKeError(
    val success: Boolean,
    val message: String
)
data class ThongKeSanPhamResponse(
    val success: Boolean,
    val topSelling: List<SanPhamThongKe>,
    val slowSelling: List<SanPhamThongKe>,
    val totalQtySold: Int
)

data class SanPhamThongKe(
    val MaSanPham: Int,
    val TenSanPham: String,
    val totalQty: Int
)
data class Order(
    val MaHoaDonBan: Int,
    val NgayDatHang: String,
    val TrangThai: String,
    val TongTien: Long
)


data class OrderResponse(
    val success: Boolean,
    val data: List<Order>
)

