//package com.example.lapstore.api
//
//import com.example.lapstore.models.MomoRequest
//import com.example.lapstore.models.MomoResponse
//import retrofit2.Call
//import retrofit2.http.Body
//import retrofit2.http.Field
//import retrofit2.http.FormUrlEncoded
//import retrofit2.http.POST
//
////interface ThanhToanAPIService {
////    @POST("momo_payment.php")
////    fun createMomoPayment(@Body request: MomoRequest): Call<MomoResponse>
////}
//interface ThanhToanAPIService {
//    @FormUrlEncoded
//    @POST("ThanhToan/momo_payment.php")
//    fun createMomoPayment(
//        @Field("MaKhachHang") maKhachHang: String,
//        @Field("MaDiaChi") maDiaChi: String,
//        @Field("TongTien") tongTien: String
//    ): Call<MomoResponse>
//}
//
