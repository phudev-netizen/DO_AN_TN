//package com.example.lapstore.api
//
//import com.example.lapstore.models.YeuThich
//import retrofit2.http.Body
//import retrofit2.http.GET
//import retrofit2.http.POST
//import retrofit2.http.Query
//
//data class IsFavoriteResponse(
//    val isFavorite: Boolean
//)
//data class ApiResponse(
//    val success: Boolean,
//    val message: String
//)
//interface SanPhamYeuThichAPIService {
//    @GET("yeuthich/list.php")
//    suspend fun getFavoritesByKhachHang(@Query("MaKhachHang") maKhachHang: Int): List<YeuThich>
//
//    @POST("yeuthich/add.php")
//    suspend fun addFavorite(@Body yeuThich: YeuThich): ApiResponse
//
//    @POST("yeuthich/remove.php")
//    suspend fun removeFavorite(@Body yeuThich: YeuThich): ApiResponse
//
//    @POST("yeuthich/kiemtra.php")
//    suspend fun isFavorite(@Body yeuThich: YeuThich): IsFavoriteResponse
//}
//
package com.example.lapstore.api

import com.example.lapstore.models.YeuThich
import retrofit2.http.Body
import retrofit2.http.POST

data class ApiResponse(
    val success: Boolean,
    val message: String
)

data class IsFavoriteResponse(
    val isFavorite: Boolean,
    val message: String? = null
)

data class KhachHangRequest(
    val MaKhachHang: Int
)

interface SanPhamYeuThichAPIService {
    @POST("yeuthich/list.php")
    suspend fun getFavoritesByKhachHang(@Body request: KhachHangRequest): List<YeuThich>

    @POST("yeuthich/add.php")
    suspend fun addFavorite(@Body yeuThich: YeuThich): ApiResponse

    @POST("yeuthich/remove.php")
    suspend fun removeFavorite(@Body yeuThich: YeuThich): ApiResponse

    @POST("yeuthich/check.php")
    suspend fun isFavorite(@Body yeuThich: YeuThich): IsFavoriteResponse
}