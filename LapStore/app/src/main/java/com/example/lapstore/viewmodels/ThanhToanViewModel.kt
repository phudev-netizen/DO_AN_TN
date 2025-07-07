package com.example.lapstore.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient.Thanhtoanapi
import com.example.lapstore.data.api.ThanhToanAPIService
import com.example.lapstore.data.model.MomoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentViewModel : ViewModel() {

    private val _momoResponse = MutableStateFlow<MomoResponse?>(null)
    val momoResponse: StateFlow<MomoResponse?> = _momoResponse

    fun taoThanhToan(amount: String) {
        viewModelScope.launch {
            try {
                val response = Thanhtoanapi.taoThanhToanMomo(mapOf("amount" to amount))
                _momoResponse.value = response
            } catch (e: Exception) {
                Log.e("PaymentViewModel", "Lỗi tạo thanh toán", e)
                _momoResponse.value = MomoResponse(false, null, e.localizedMessage)
            }
        }
    }
}


