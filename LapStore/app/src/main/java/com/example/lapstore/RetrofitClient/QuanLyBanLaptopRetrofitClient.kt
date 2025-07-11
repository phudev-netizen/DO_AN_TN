//// QuanLyBanLaptopRetrofitClient.kt
//package com.example.lapstore.api
//
//import ApiService
//import BinhLuanApiService
//import ChiTietHoaDonBanAPIService
//import DiaChiAPIService
//import GioHangAPIService
//import HinhAnhAPIService
//import HoaDonBanAPIService
//import KhachHangAPIService
//import SanPhamAPIService
//import TaiKhoanAPIService
//import YeuThichApi
//import com.example.lapstore.data.api.ThanhToanAPIService
//import com.google.gson.GsonBuilder
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//object Constants {
//    //const val BASE_URL = "http://10.0.2.2/lap_store_api/api/"
//    const val BASE_URL = "http://192.168.3.49/lap_store_api/api/"
//}
//
//object QuanLyBanLaptopRetrofitClient {
//    val sanphamAPIService: SanPhamAPIService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(SanPhamAPIService::class.java)
//    }
//
//    val hinhAnhAPIService: HinhAnhAPIService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(HinhAnhAPIService::class.java)
//    }
//
//    val taiKhoanAPIService: TaiKhoanAPIService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(TaiKhoanAPIService::class.java)
//    }
//
//    val khachHangAPIService: KhachHangAPIService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(KhachHangAPIService::class.java)
//    }
//
//    val giohangAPIService: GioHangAPIService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(GioHangAPIService::class.java)
//    }
//
//    val hoaDonBanAPIService: HoaDonBanAPIService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(HoaDonBanAPIService::class.java)
//    }
//
//    val diaChiAPIService: DiaChiAPIService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(DiaChiAPIService::class.java)
//    }
//
//    val chiTietHoaDonBanAPIService: ChiTietHoaDonBanAPIService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(ChiTietHoaDonBanAPIService::class.java)
//    }
//
//    val binhLuanAPIService: BinhLuanApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(BinhLuanApiService::class.java)
//    }
//    val apiService: ApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(ApiService::class.java)
//    }
//
//    val api: YeuThichApi by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(YeuThichApi::class.java)
//    }
//    val khuyenMaiAPIService: KhuyenMaiAPIService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(KhuyenMaiAPIService::class.java)
//    }
//    val Thanhtoanapi: ThanhToanAPIService by lazy {
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ThanhToanAPIService::class.java)
//    }
//
//}
//
package com.example.lapstore.api

import ApiService
import BinhLuanApiService
import ChiTietHoaDonBanAPIService
import DiaChiAPIService
import GioHangAPIService
import HinhAnhAPIService
import HoaDonBanAPIService
import KhachHangAPIService
import SanPhamAPIService
import TaiKhoanAPIService
import YeuThichApi
import com.example.lapstore.data.api.ThanhToanAPIService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Constants {
    private const val BASE_URL_EMULATOR = "http://10.0.2.2/lap_store_api/api/"
    private const val BASE_URL_DEVICE = "http://192.168.3.49/lap_store_api/api/"
    // 👉 Chỉ cần đổi dòng này nếu chuyển môi trường
    val BASE_URL: String = BASE_URL_DEVICE
    //val BASE_URL: String = BASE_URL_EMULATOR
}

object QuanLyBanLaptopRetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    val sanphamAPIService: SanPhamAPIService by lazy {
        retrofit.create(SanPhamAPIService::class.java)
    }

    val hinhAnhAPIService: HinhAnhAPIService by lazy {
        retrofit.create(HinhAnhAPIService::class.java)
    }

    val taiKhoanAPIService: TaiKhoanAPIService by lazy {
        retrofit.create(TaiKhoanAPIService::class.java)
    }

    val khachHangAPIService: KhachHangAPIService by lazy {
        retrofit.create(KhachHangAPIService::class.java)
    }

    val giohangAPIService: GioHangAPIService by lazy {
        retrofit.create(GioHangAPIService::class.java)
    }

    val hoaDonBanAPIService: HoaDonBanAPIService by lazy {
        retrofit.create(HoaDonBanAPIService::class.java)
    }

    val diaChiAPIService: DiaChiAPIService by lazy {
        retrofit.create(DiaChiAPIService::class.java)
    }

    val chiTietHoaDonBanAPIService: ChiTietHoaDonBanAPIService by lazy {
        retrofit.create(ChiTietHoaDonBanAPIService::class.java)
    }

    val binhLuanAPIService: BinhLuanApiService by lazy {
        retrofit.create(BinhLuanApiService::class.java)
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val api: YeuThichApi by lazy {
        retrofit.create(YeuThichApi::class.java)
    }

    val khuyenMaiAPIService: KhuyenMaiAPIService by lazy {
        retrofit.create(KhuyenMaiAPIService::class.java)
    }

    val Thanhtoanapi: ThanhToanAPIService by lazy {
        retrofit.create(ThanhToanAPIService::class.java)
    }
}
