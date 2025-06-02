//package com.example.lapstore.viewmodels
//
//import androidx.compose.runtime.mutableStateListOf
//import androidx.lifecycle.ViewModel
//
//class YeuThichViewModel : ViewModel() {
//    // Lưu danh sách mã sản phẩm yêu thích (chỉ 1 người dùng, muốn đa người dùng thì map thêm mã khách hàng)
//    val danhSachYeuThich = mutableStateListOf<Int>()
//
//    fun isYeuThich(maSanPham: Int): Boolean {
//        return danhSachYeuThich.contains(maSanPham)
//    }
//
//    fun themYeuThich(maSanPham: Int) {
//        if (!isYeuThich(maSanPham)) {
//            danhSachYeuThich.add(maSanPham)
//        }
//    }
//
//    fun xoaYeuThich(maSanPham: Int) {
//        danhSachYeuThich.remove(maSanPham)
//    }
//}