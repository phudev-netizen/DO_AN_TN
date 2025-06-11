package com.example.lapstore.models

data class YeuThich(
    val id: Int? = null,
    val MaSanPham: Int,
    val MaKhachHang: Int,
    val NgayYeuThich: String? = null // add this if you want to display favorited date
)
