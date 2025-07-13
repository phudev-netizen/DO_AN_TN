
import com.example.lapstore.models.DiaChi
import com.example.lapstore.models.HoaDonBan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

data class addHoaDonBanResponse(
    val success: Boolean,
    val message: String
)

data class HoaDonBanResponse(
    val hoadonban: List<HoaDonBan>?
)

data class MaHoaDonBanResponse(
    val MaHoaDonBan: Int
)

data class HoaDonDeleteRequest(
    val MaHoaDonBan: Int
)
data class AddHoaDonBanAndGetIdResponse(
    val success: Boolean,
    val message: String,
    val MaHoaDonBan: Int? // chú ý tên trùng với key JSON backend trả về
)
data class LyDoTraHangRequest(
    val MaHoaDonBan: Int,
    val LyDoTraHang: String
)
interface HoaDonBanAPIService{
    @POST("HoaDonBan/create.php")
    suspend fun addHoaDonBan(
        @Body hoadon: HoaDonBan
    ): addHoaDonBanResponse

    @GET("HoaDonBan/getHoaDonBanByKhachHang.php")
    suspend fun getHoaDoByKhachHang(
        @Query("MaKhachHang") MaKhachHang: Int,
        @Query("TrangThai") TrangThai: Int
    ): HoaDonBanResponse

    @POST("HoaDonBan/delete.php")
    suspend fun deleteHoaDon(
        @Body deleteRequest: HoaDonDeleteRequest
    ): Response<ApiResponse>

    @PUT("HoaDonBan/update.php")
    suspend fun updateHoaDon(
        @Body hoadon: HoaDonBan
    ): addHoaDonBanResponse

    @GET("HoaDonBan/show.php")
    suspend fun getHoaDonByMaHoaDon(
        @Query("MaHoaDonBan") MaHoaDonBan: Int
    ): HoaDonBan

    @GET("HoaDonBan/layhoadontheotrangthai.php")
    suspend fun getHoaDonTheoTrangThai(
        @Query("TrangThai") TrangThai: Int
    ): HoaDonBanResponse

    // Thêm mới:
    @POST("HoaDonBan/create.php")
    suspend fun addHoaDonBanAndGetId(
        @Body hoadon: HoaDonBan
    ): AddHoaDonBanAndGetIdResponse

    @GET("HoaDonBan/read.php")
    suspend fun getAllHoaDonBan(): HoaDonBanResponse

    // ✅ Cập nhật lý do trả hàng
    @POST("HoaDonBan/update_trahang.php")
    suspend fun capNhatLyDoTraHang(
        @Body request: LyDoTraHangRequest
    ): ApiResponse

}