package com.example.lapstore.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapstore.api.KhuyenMaiAPIService
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import com.example.lapstore.models.KhuyenMai

import kotlinx.coroutines.launch

class KhuyenMaiViewModel : ViewModel() {

    private val _khuyenMai = mutableStateOf<KhuyenMai?>(null)
    val khuyenMai: State<KhuyenMai?> = _khuyenMai

    private val _khuyenMaiList = mutableStateListOf<KhuyenMai>()
    val khuyenMaiList: List<KhuyenMai> get() = _khuyenMaiList


fun fetchKhuyenMai(maSanPham: Int) {
    viewModelScope.launch {
        try {
            val response = QuanLyBanLaptopRetrofitClient.khuyenMaiAPIService.getKhuyenMaiByProductId(maSanPham)
            Log.d("KhuyenMaiVM", "KQ KM cho SP $maSanPham: $response")

            // Ưu tiên khuyến mãi riêng cho SP trước, nếu không có thì lấy khuyến mãi toàn bộ
            _khuyenMai.value = response.firstOrNull { it.MaSanPham == maSanPham }
                ?: response.firstOrNull { it.MaSanPham == 0 }

            Log.d("KhuyenMaiVM", "Khuyến mãi áp dụng: ${_khuyenMai.value}")
        } catch (e: Exception) {
            Log.e("KhuyenMaiVM", "Lỗi khi fetch KM: ${e.message}")
        }
    }
}

    fun fetchTatCaKhuyenMai() {
        viewModelScope.launch {
            try {
                val response = QuanLyBanLaptopRetrofitClient.khuyenMaiAPIService.getTatCaKhuyenMai()
                _khuyenMaiList.clear()
                _khuyenMaiList.addAll(response)
            } catch (e: Exception) {
                Log.e("KM", "Lỗi fetch tất cả KM: ${e.message}")
            }
        }
    }

    fun addKhuyenMai(khuyenMai: KhuyenMai, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("DEBUG_ADD_KM", "Gửi lên: $khuyenMai")
                QuanLyBanLaptopRetrofitClient.khuyenMaiAPIService.addKhuyenMai(khuyenMai)
                _khuyenMaiList.add(khuyenMai)
                onSuccess()
            } catch (e: Exception) {
                Log.e("ADD_KM", "Lỗi thêm khuyến mãi: ${e.message}")

            }
        }
    }
    fun updateKhuyenMai(khuyenMai: KhuyenMai, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                QuanLyBanLaptopRetrofitClient.khuyenMaiAPIService.updateKhuyenMai(khuyenMai)
                val index = khuyenMaiList.indexOfFirst { it.MaKhuyenMai == khuyenMai.MaKhuyenMai }
                if (index != -1) {
                    _khuyenMaiList[index] = khuyenMai
                }
                onSuccess()
            } catch (e: Exception) { }
        }
    }
    fun deleteKhuyenMai(maKhuyenMai: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                QuanLyBanLaptopRetrofitClient.khuyenMaiAPIService.deleteKhuyenMai(maKhuyenMai)
                _khuyenMaiList.removeAll { it.MaKhuyenMai == maKhuyenMai }
                onSuccess()
            } catch (e: Exception) {
                Log.e("KhuyenMaiViewModel", "Error deleting khuyen mai: ${e.message}")
            }
        }
    }
}
