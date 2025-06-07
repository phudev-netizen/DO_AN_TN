package com.example.lapstore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import com.example.lapstore.models.YeuThich
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class YeuThichViewModel : ViewModel() {
    private val _favoriteIds = MutableStateFlow<List<Int>>(emptyList())
    val favoriteIds: StateFlow<List<Int>> = _favoriteIds

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getFavoritesByKhachHang(maKhachHang: Int) {
        viewModelScope.launch {
            try {
                val result = QuanLyBanLaptopRetrofitClient.SanPhamYeuThichAPIService.getFavoritesByKhachHang(maKhachHang)
                _favoriteIds.value = result.map { it.MaSanPham }
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi lấy danh sách yêu thích: ${e.message}"
            }
        }
    }

    fun toggleFavorite(maSanPham: Int, maKhachHang: Int) {
        viewModelScope.launch {
            try {
                val isFavorite = _favoriteIds.value.contains(maSanPham)
                val model = YeuThich(MaSanPham = maSanPham, MaKhachHang = maKhachHang)
                if (isFavorite) {
                    QuanLyBanLaptopRetrofitClient.SanPhamYeuThichAPIService.removeFavorite(model)
                } else {
                    QuanLyBanLaptopRetrofitClient.SanPhamYeuThichAPIService.addFavorite(model)
                }
                getFavoritesByKhachHang(maKhachHang)
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi khi thay đổi yêu thích: ${e.message}"
            }
        }
    }
}