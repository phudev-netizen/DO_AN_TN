package com.example.lapstore.data.api

import com.example.lapstore.data.model.MomoResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ThanhToanAPIService {
    @POST("Thanhtoan/momo_payment.php")
    suspend fun taoThanhToanMomo(
        @Body body: Map<String, String>
    ): MomoResponse
}

