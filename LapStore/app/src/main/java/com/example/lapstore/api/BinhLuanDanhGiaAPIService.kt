import retrofit2.Response
import retrofit2.http.*

interface BinhLuanApiService {

    @GET("binhluandanhgia/read.php")
    suspend fun getAll(): Response<BinhLuanResponse>

    @GET("binhluandanhgia/getbyid.php")
    suspend fun getById(@Query("id") id: String): Response<BinhLuanDanhGia>

    @POST("binhluandanhgia/create.php")
    suspend fun add(@Body binhLuan: BinhLuanDanhGia): Response<ApiResponse1>

    @PUT("binhluandanhgia/update.php")
    suspend fun update(@Body binhLuan: BinhLuanDanhGia): Response<ApiResponse1>

    @HTTP(method = "DELETE", path = "binhluandanhgia/delete.php", hasBody = true)
    suspend fun delete(@Body map: Map<String, String>): Response<ApiResponse1>
}