package com.example.lapstore.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import com.example.lapstore.models.ChiTietHoaDonBan
import com.example.lapstore.models.HinhAnh
import com.example.lapstore.models.HoaDonBan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChiTietHoaDonBanViewmodel:ViewModel() {
    var chitiethoadonAddResult by mutableStateOf("")

    var danhsachchitethoadon by mutableStateOf<List<ChiTietHoaDonBan>>(emptyList())

    fun addHoaDon(chitiethoadonban: ChiTietHoaDonBan) {
        viewModelScope.launch {
            try {
                // Gọi API để thêm sản phẩm vào giỏ hàng trên server
                val response = QuanLyBanLaptopRetrofitClient.chiTietHoaDonBanAPIService.addChiTietHoaDonBan(chitiethoadonban)
                chitiethoadonAddResult = if (response.success) {
                    "Cập nhật thành công: ${response.message}"
                } else {
                    "Cập nhật thất bại: ${response.message}"
                }
            } catch (e: Exception) {
                Log.e("AddToCart", "Lỗi kết nối: ${e.message}")
            }
        }
    }

    fun getChiTietHoaDonTheoMaHoaDon(mahoadon: Int) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.chiTietHoaDonBanAPIService.getChiTietHoaDoByMaHoaDon(mahoadon)
                }
                danhsachchitethoadon = response.chitiethoadonban
            } catch (e: Exception) {
                // Xử lý lỗi nếu có
                Log.e("HinhAnhError", "Lỗi khi lấy hình ảnh: ${e.message}")
            }
        }
    }
}