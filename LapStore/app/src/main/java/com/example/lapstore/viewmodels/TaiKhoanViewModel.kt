package com.example.lapstore.viewmodels

import BaseResponse
import ForgotPassRequest
import KiemTraTaiKhoanResponse
import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import com.example.lapstore.datastore.UserPreferences
import com.example.lapstore.models.TaiKhoan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaiKhoanViewModel : ViewModel() {
    var taikhoan: TaiKhoan? by mutableStateOf(null)
        private set

    private val _loginResult = mutableStateOf<KiemTraTaiKhoanResponse?>(null)
    val loginResult: State<KiemTraTaiKhoanResponse?> = _loginResult

    private val _checkUsernameResult = mutableStateOf<KiemTraTaiKhoanResponse?>(null)
    val checkUsernameResult: State<KiemTraTaiKhoanResponse?> = _checkUsernameResult

    var taikhoanUpdateResult by mutableStateOf("")
    var tentaikhoan: String? = null
    var TaoTaiKhoanResult by mutableStateOf("")

    fun kiemTraDangNhap(tenTaiKhoan: String, matKhau: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.taiKhoanAPIService.kiemTraDangNhap(tenTaiKhoan, matKhau)
                }
                _loginResult.value = response
            } catch (e: Exception) {
                Log.e("TaiKhoanViewModel", "Đã xảy ra lỗi: ${e.message}")
                _loginResult.value = KiemTraTaiKhoanResponse(
                    result = false, message = e.message ?: "Lỗi không xác định",
                    role = ""
                )
            }
        }
    }

    fun kiemTraTrungUsername(tenTaiKhoan: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.taiKhoanAPIService.kiemTraTrunUsername(tenTaiKhoan)
                }
                _checkUsernameResult.value = response
            } catch (e: Exception) {
                Log.e("TaiKhoanViewModel", "Đã xảy ra lỗi: ${e.message}")
                _checkUsernameResult.value = KiemTraTaiKhoanResponse(
                    result = false, message = e.message ?: "",
                    role = null
                )
            }
        }
    }

    fun getTaiKhoanByTentaikhoan(tentaikhoan: String) {
        this.tentaikhoan = tentaikhoan
        viewModelScope.launch(Dispatchers.IO) {
            try {
                taikhoan = QuanLyBanLaptopRetrofitClient.taiKhoanAPIService.getTaiKhoanByTentaikhoan(tentaikhoan)
            } catch (e: Exception) {
                Log.e("TaiKhoanViewModel", "Lỗi khi lấy thông tin tài khoản", e)
            }
        }
    }

    fun updateTaiKhoan(taiKhoan: TaiKhoan) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.taiKhoanAPIService.updateTaiKhoan(taiKhoan)
                }
                taikhoanUpdateResult = if (response.success) {
                    "Cập nhật thành công: ${response.message}"
                } else {
                    "Cập nhật thất bại: ${response.message}"
                }
            } catch (e: Exception) {
                taikhoanUpdateResult = "Lỗi khi cập nhật tài khoản: ${e.message}"
                Log.e("TaiKhoanViewModel", "Lỗi khi cập nhật tài khoản: ${e.message}")
            }
        }
    }

    fun TaoTaiKhoan(taikhoan: TaiKhoan) {
        viewModelScope.launch {
            try {
                val response = QuanLyBanLaptopRetrofitClient.taiKhoanAPIService.TaoTaiKhoan(taikhoan)
                TaoTaiKhoanResult = if (response.success) {
                    "Đăng ký thành công: ${response.message}"
                } else {
                    "Đăng ký thất bại: ${response.message}"
                }
            } catch (e: Exception) {
                Log.e("TaiKhoanViewModel", "Lỗi khi tạo tài khoản: ${e.message}")
                TaoTaiKhoanResult = "Lỗi khi tạo tài khoản: ${e.message}"
            }
        }
    }

    fun getRole(s: String): String? {
        return when (s.lowercase()) {
            "admin" -> "admin"
            "user" -> "user"
            "nhanvien" -> "nhanvien"
            else -> null
        }
    }

    fun saveLogin(context: Context, tentaikhoan: String, role: String) {
        val userPreferences = UserPreferences(context)
        viewModelScope.launch {
            userPreferences.saveLogin(tentaikhoan, role)
        }
    }

    fun logout(context: Context) {
        val userPreferences = UserPreferences(context)
        viewModelScope.launch {
            userPreferences.clearLogin()
        }
    }
    private val _forgotPassResult = mutableStateOf<BaseResponse?>(null)
    val forgotPassResult: State<BaseResponse?> = _forgotPassResult

    fun forgotPassword(usernameOrEmail: String) {
        viewModelScope.launch {
            try {
                // Gọi API trên IO
                val resp = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient
                        .taiKhoanAPIService
                        .forgotPassword(ForgotPassRequest(usernameOrEmail))
                }
                _forgotPassResult.value = resp
            } catch (e: Exception) {
                // Bắt hết mọi exception để khỏi crash
                Log.e("TaiKhoanVM", "forgotPassword failed", e)
                _forgotPassResult.value = BaseResponse(
                    success = false,
                    message = "Lỗi: ${e.localizedMessage ?: "Không xác định"}"
                )
            }
        }
    }

    fun resetForgotPassResult() {
        _forgotPassResult.value = null
    }


}
