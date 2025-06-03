package com.example.lapstore.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SanPhamYeuThichAPIService {
    @POST("SanPhamYeuThich/add.php")
    suspend fun addToFavorite(@Body body: Map<String, Any>): Response<JsonObject>

    @POST("SanPhamYeuThich/remove.php")
    suspend fun removeFromFavorite(@Body body: Map<String, Any>): Response<JsonObject>

    @GET("SanPhamYeuThich/list.php")
    suspend fun getFavoriteList(@Query("MaKhachHang") maKhachHang: Int): Response<JsonObject>
}
suspend fun addToFavorite(maKhachHang: Int, maSanPham: Int) {
    QuanLyBanLaptopRetrofitClient.SanPhamYeuThichAPIService.addToFavorite(
        mapOf("MaKhachHang" to maKhachHang, "MaSanPham" to maSanPham)
    )
}

suspend fun removeFromFavorite(maKhachHang: Int, maSanPham: Int) {
    QuanLyBanLaptopRetrofitClient.SanPhamYeuThichAPIService.removeFromFavorite(
        mapOf("MaKhachHang" to maKhachHang, "MaSanPham" to maSanPham)
    )
}