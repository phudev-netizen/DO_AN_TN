package com.example.lapstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {
    private val _favoriteIds = MutableStateFlow<List<Int>>(emptyList())
    val favoriteIds: StateFlow<List<Int>> = _favoriteIds

    fun loadFavoriteIds(maKhachHang: Int) {
        viewModelScope.launch {
            val response = QuanLyBanLaptopRetrofitClient.SanPhamYeuThichAPIService.getFavoriteList(maKhachHang)
            if (response.isSuccessful) {
                val arr = response.body()?.getAsJsonArray("data")
                val ids = mutableListOf<Int>()
                arr?.forEach { element -> ids.add(element.asInt) }
                _favoriteIds.value = ids
            }
        }
    }
}