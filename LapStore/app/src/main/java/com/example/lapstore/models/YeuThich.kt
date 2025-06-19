import com.google.gson.annotations.SerializedName


data class YeuThich(
    @SerializedName("ID") val id: String,
    @SerializedName("MaKhachHang") val maKhachHang: Int,
    @SerializedName("MaSanPham") val maSanPham: Int,
    @SerializedName("NgayYeuThich") val ngayYeuThich: String
)
data class DanhSachYeuThichResponse(
    val success: Boolean,
    val data: List<YeuThich>?,
    val message: String?
)