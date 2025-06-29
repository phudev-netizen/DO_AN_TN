//package com.example.lapstore.viewmodels
//
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.widget.Toast
//import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
//import com.example.lapstore.models.MomoRequest
//import com.example.lapstore.models.MomoResponse
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
////fun thanhToanQuaMomo(context: Context, maKhachHang: String, maDiaChi: String, tongTien: String) {
////    val request = MomoRequest(
////        MaKhachHang = maKhachHang,
////        MaDiaChi = maDiaChi,
////        TongTien = tongTien
////    )
////
////    QuanLyBanLaptopRetrofitClient.instance.createMomoPayment(request)
////        .enqueue(object : Callback<MomoResponse> {
////            override fun onResponse(call: Call<MomoResponse>, response: Response<MomoResponse>) {
////                if (response.isSuccessful && response.body() != null) {
////                    val payUrl = response.body()!!.payUrl
////                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(payUrl))
////                    context.startActivity(intent)
////                } else {
////                    Toast.makeText(context, "Không lấy được URL thanh toán", Toast.LENGTH_SHORT).show()
////                }
////            }
////
////            override fun onFailure(call: Call<MomoResponse>, t: Throwable) {
////                Toast.makeText(context, "Lỗi mạng: ${t.message}", Toast.LENGTH_SHORT).show()
////            }
////        })
////}
//fun thanhToanQuaMomo(context: Context, maKhachHang: String, maDiaChi: String, tongTien: String) {
//    val api = QuanLyBanLaptopRetrofitClient.instance
//
//    api.createMomoPayment(maKhachHang, maDiaChi, tongTien)
//        .enqueue(object : Callback<MomoResponse> {
//            override fun onResponse(call: Call<MomoResponse>, response: Response<MomoResponse>) {
//                if (response.isSuccessful && response.body() != null) {
//                    val payUrl = response.body()!!.payUrl
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(payUrl))
//                    context.startActivity(intent)
//                } else {
//                    Toast.makeText(context, "Không lấy được URL thanh toán", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<MomoResponse>, t: Throwable) {
//                Toast.makeText(context, "Lỗi mạng: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//}
//
//
//
