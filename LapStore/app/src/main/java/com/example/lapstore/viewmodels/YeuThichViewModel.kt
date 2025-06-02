package com.example.lapstore.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class YeuThichViewModel : ViewModel() {
    val danhSachYeuThich = mutableStateListOf<Int>() // Lưu mã sản phẩm yêu thích

    fun isYeuThich(maSanPham: Int): Boolean {
        return danhSachYeuThich.contains(maSanPham)
    }

    fun themYeuThich(maSanPham: Int) {
        if (!isYeuThich(maSanPham)) {
            danhSachYeuThich.add(maSanPham)
        }
    }

    fun xoaYeuThich(maSanPham: Int) {
        danhSachYeuThich.remove(maSanPham)
    }
}