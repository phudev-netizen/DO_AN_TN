package com.example.lapstore.api

import com.example.lapstore.models.YeuThich
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class ApiResponse(val message: String)

interface SanPhamYeuThichAPIService {
    @GET("yeuthich/list.php")
    suspend fun getFavoritesByKhachHang(@Query("MaKhachHang") maKhachHang: Int): List<YeuThich>

    @POST("yeuthich/add.php")
    suspend fun addFavorite(@Body yeuThich: YeuThich): ApiResponse

    @POST("yeuthich/remove.php")
    suspend fun removeFavorite(@Body yeuThich: YeuThich): ApiResponse
}