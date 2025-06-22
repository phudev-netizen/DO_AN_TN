
import retrofit2.http.*
import retrofit2.Call

interface YeuThichApi {


@GET("YeuThich/danh_sach_yeu_thich.php")
fun getDanhSachYeuThich(@Query("MaKhachHang") maKhachHang: Int): Call<DanhSachYeuThichResponse>

    @FormUrlEncoded
    @POST("YeuThich/them_yeu_thich.php")
    fun themYeuThich(
        @Field("MaKhachHang") maKhachHang: Int,
        @Field("MaSanPham") maSanPham: String
    ): Call<ApiResponse3>

    @FormUrlEncoded
    @POST("yeuthich/xoa_yeu_thich.php")
    fun xoaYeuThich(
        @Field("MaKhachHang") maKhachHang: Int,
        @Field("MaSanPham") maSanPham: Int
    ): Call<ApiResponse3>

}

