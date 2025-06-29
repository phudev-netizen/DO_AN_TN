package com.example.lapstore.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import com.example.lapstore.models.KhuyenMai

import kotlinx.coroutines.launch

class KhuyenMaiViewModel : ViewModel() {
//    val khuyenMaiList: MutableList<KhuyenMai> = mutableListOf()

    private val _khuyenMai = mutableStateOf<KhuyenMai?>(null)
    val khuyenMai: State<KhuyenMai?> = _khuyenMai

    private val _khuyenMaiList = mutableStateListOf<KhuyenMai>()
    val khuyenMaiList: List<KhuyenMai> get() = _khuyenMaiList

    fun fetchKhuyenMai(maSanPham: Int) {
        viewModelScope.launch {
            try {
                val response = QuanLyBanLaptopRetrofitClient.khuyenMaiAPIService.getKhuyenMaiByProductId(maSanPham)
                Log.d("KhuyenMaiVM", "Fetch khuyến mãi cho MaSanPham = $maSanPham, kết quả: $response")
                _khuyenMai.value = response.firstOrNull()
            } catch (e: Exception) {
            }
        }
    }

    fun fetchTatCaKhuyenMai() {
        viewModelScope.launch {
            try {
                val response = QuanLyBanLaptopRetrofitClient.khuyenMaiAPIService.getKhuyenMaiByProductId(0) // 0 để lấy tất cả
                _khuyenMaiList.clear()
                _khuyenMaiList.addAll(response)
            } catch (e: Exception) {
            }
        }
    }

//    fun fetchTatCaKhuyenMai1() {
//            viewModelScope.launch {
//                try {
//                    val response = QuanLyBanLaptopRetrofitClient.khuyenMaiAPIService.getKhuyenMaiByProductId(0)
//                    _khuyenMaiList.clear()
//                    _khuyenMaiList.addAll(response)
//                } catch (e: Exception) {
//                    Log.e("KhuyenMaiViewModel", "Lỗi fetchTatCaKhuyenMai: ${e.message}")
//                }
//            }
//        }

    fun addKhuyenMai(khuyenMai: KhuyenMai, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                QuanLyBanLaptopRetrofitClient.khuyenMaiAPIService.addKhuyenMai(khuyenMai)
                _khuyenMaiList.add(khuyenMai)
                onSuccess()
            } catch (e: Exception) {

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
