data class BinhLuanDanhGia(
    val MaBinhLuan: Int?,
    val MaKhachHang: String,
    val MaSanPham: String,
    val MaHoaDonBan: Int?,
    val SoSao: Int,
    val NoiDung: String,
    val NgayDanhGia: String,
    val TrangThai: String
)
data class BinhLuanResponse(val binhluandanhgia: List<BinhLuanDanhGia>?)
data class ApiResponse1(val message: String)