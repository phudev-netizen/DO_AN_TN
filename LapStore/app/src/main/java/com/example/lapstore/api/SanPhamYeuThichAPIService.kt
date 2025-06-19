
import retrofit2.http.*
import retrofit2.Call

interface YeuThichApi {


@GET("api/YeuThich/danh_sach_yeu_thich.php")
fun getDanhSachYeuThich(@Query("MaKhachHang") maKhachHang: Int): Call<DanhSachYeuThichResponse>

//    @FormUrlEncoded
//    @POST("api/YeuThich/them_yeu_thich.php")
//    fun themYeuThich(
//        @Field("MaKhachHang") maKhachHang: String,
//        @Field("MaSanPham") maSanPham: String
//    ): Call<ApiResponse>
@FormUrlEncoded
@POST("api/YeuThich/them_yeu_thich.php")
fun themYeuThich(
    @Field("MaKhachHang") maKhachHang: String,
    @Field("MaSanPham") maSanPham: String
): Call<ApiResponse3>


    @FormUrlEncoded
    @POST("xoa_yeu_thich.php")
    fun xoaYeuThich(
        @Field("MaKhachHang") maKhachHang: String,
        @Field("MaSanPham") maSanPham: String
    ): Call<String>
}
data class ApiResponse3(
    val success: Boolean,
    val message: String
)
