import com.example.lapstore.models.KhachHang
import com.example.lapstore.models.SanPham
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

data class SanPhamResponse(
    val sanpham: List<SanPham>
)

interface SanPhamAPIService{
    @GET("SanPham/read.php")
    suspend fun getAllSanPham(): SanPhamResponse

    @GET("SanPham/readByLoaiSanPham.php")
    suspend fun getSanPhamTheoLoai(
        @Query("MaLoaiSanPham") MaLoaiSanPham: Int
    ): SanPhamResponse

    @GET("SanPham/getproductCardCuaKhachHang.php")
    suspend fun getSanPhamByGioHang(
        @Query("MaKhachHang") MaKhachHang: Int
    ): SanPhamResponse

    @GET("SanPham/show.php")
    suspend fun getSanPhamById(
        @Query("MaSanPham") MaSanPham: String
    ): SanPham

    @GET("SanPham/searchSanPham.php")
    suspend fun searchSanPham(@Query("search") search: String): SanPhamResponse

    @GET("SanPham/laysanphamtheohoadon.php")
    suspend fun getSanPhamTheoHoaDon(
        @Query("MaHoaDonBan") MaHoaDonBan: Int
    ): SanPhamResponse

    @PUT("SanPham/update.php")
    suspend fun updateSanPham(
        @Body sanpham: SanPham
    ): KhachHangUpdateResponse
}