import com.google.gson.annotations.SerializedName


data class YeuThich(
    @SerializedName("ID") val id: Int,
    @SerializedName("MaKhachHang") val maKhachHang: Int,
    @SerializedName("MaSanPham") val maSanPham: Int,
    @SerializedName("NgayYeuThich") val ngayYeuThich: String?
)

data class DanhSachYeuThichResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: List<YeuThich>,
    @SerializedName("message") val message: String
)


data class ApiResponse3(
    val success: Boolean,
    val message: String?
)