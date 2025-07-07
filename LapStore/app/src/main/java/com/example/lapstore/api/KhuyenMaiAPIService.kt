package com.example.lapstore.api

import com.example.lapstore.models.KhuyenMai
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface KhuyenMaiAPIService {
    @GET("KhuyenMai/lay_khuyen_mai.php")
    suspend fun getKhuyenMaiByProductId(
        @Query("MaSanPham") maSanPham: Int
    ): List<KhuyenMai>

    @GET("KhuyenMai/lay_khuyen_mai.php")
    suspend fun getTatCaKhuyenMai(): List<KhuyenMai>

    @DELETE("KhuyenMai/xoa_khuyen_mai.php")
    suspend fun deleteKhuyenMai(@Query("MaKhuyenMai") id: Int): Response<Unit>

    @PUT("KhuyenMai/sua_khuyen_mai.php")
    suspend fun updateKhuyenMai(@Body khuyenMai: KhuyenMai): Response<Unit>

    @POST("KhuyenMai/them_khuyen_mai.php")
    suspend fun addKhuyenMai(@Body khuyenMai: KhuyenMai): Response<Unit>


}

