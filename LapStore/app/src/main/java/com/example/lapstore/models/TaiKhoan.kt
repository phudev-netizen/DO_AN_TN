package com.example.lapstore.models

import com.google.gson.annotations.SerializedName
import android.content.Context
import android.content.SharedPreferences

data class TaiKhoan(
    @SerializedName("TenTaiKhoan") var TenTaiKhoan: String,
    @SerializedName("MaKhachHang") var MaKhachHang: Int?,
    @SerializedName("MatKhau") var MatKhau: String,
    @SerializedName("LoaiTaiKhoan") var LoaiTaiKhoan: Int,
    @SerializedName("TrangThai") var TrangThai: Int,
)



