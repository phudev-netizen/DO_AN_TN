package com.example.lapstore.models

data class KhuyenMai(
    val MaKhuyenMai: Int,
    val MaSanPham: Int = 0,
    val TenKhuyenMai: String,
    val PhanTramGiam: Int,
    val NgayBatDau: String,
    val NgayKetThuc: String
)

