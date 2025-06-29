//package com.example.lapstore.viewmodels
//
//import android.util.Log
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.liveData
//import androidx.lifecycle.viewModelScope
//import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
//import com.example.lapstore.models.ChiTietHoaDonBan
//import com.example.lapstore.models.DiaChi
//import com.example.lapstore.models.HoaDonBan
//import com.example.lapstore.models.KhachHang
//import com.example.lapstore.models.SanPham
//import com.example.lapstore.models.TaiKhoan
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class KhachHangViewModel: ViewModel() {
//
//    var hoaDonBanList by mutableStateOf<List<HoaDonBan>?>(null)
//        private set
//
//    var chiTietHoaDonBanList by mutableStateOf<List<ChiTietHoaDonBan>?>(null)
//        private set
//
//    var khachhang by mutableStateOf<KhachHang?>(null)
//        private set
//
//    var khachhangUpdateResult by mutableStateOf("")
//        private set
//
//    var ThemKhachHangResult by mutableStateOf("")
//
//    val allKhachHang = liveData(Dispatchers.IO) {
//        try {
//            // Gọi API lấy danh sách khách hàng
//            val response = QuanLyBanLaptopRetrofitClient.khachHangAPIService.getAllKhachHang().execute()
//            if (response.isSuccessful) {
//                emit(response.body()?.khachhang ?: emptyList())  // Trả dữ liệu vào LiveData
//            } else {
//                emit(emptyList())  // Trả danh sách trống nếu phản hồi không thành công
//            }
//        } catch (e: Exception) {
//            emit(emptyList())  // Trả danh sách trống nếu có lỗi
//        }
//    }
//
//    fun getKhachHangById(id: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                khachhang = QuanLyBanLaptopRetrofitClient.khachHangAPIService.getKhachHangById(id)
//            } catch (e: Exception) {
//                Log.e("KhachHangViewModel", "Error getting khachhang", e)
//            }
//        }
//    }
//
//    fun updateKhachHang(khachhang: KhachHang) {
//        viewModelScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    QuanLyBanLaptopRetrofitClient.khachHangAPIService.updateKhachHang(khachhang)
//                }
//                khachhangUpdateResult = if (response.success) {
//                    "Cập nhật thành công: ${response.message}"
//                } else {
//                    "Cập nhật thất bại: ${response.message}"
//                }
//            } catch (e: Exception) {
//                khachhangUpdateResult = "Lỗi khi cập nhật khách hàng: ${e.message}"
//                Log.e("GioHang Error", "Lỗi khi cập nhật khách hàng: ${e.message}")
//            }
//        }
//    }
//
//
//    fun ThemKhachHang(khachhang: KhachHang) {
//        viewModelScope.launch {
//            try {
//                val response = QuanLyBanLaptopRetrofitClient.khachHangAPIService.ThemKhachHang(khachhang)
//                ThemKhachHangResult = if (response.success) {
//                    "Đăng ký thành công: ${response.message}"
//                } else {
//                    "Đăng ký thất bại: ${response.message}"
//                }
//            } catch (e: Exception) {
//                Log.e("Thêm tài khoản", "Lỗi kết nối: ${e.message}")
//            }
//        }
//    }
//fun getAllHoaDonBan() {
//    viewModelScope.launch(Dispatchers.IO) {
//        try {
//            val response = QuanLyBanLaptopRetrofitClient.hoaDonBanAPIService.getAllHoaDonBan()
//            hoaDonBanList = response.hoadonban ?: emptyList()
//        } catch (e: Exception) {
//            hoaDonBanList = emptyList()
//        }
//    }
//}
//    fun getAllChiTietHoaDonBan() {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = QuanLyBanLaptopRetrofitClient.chiTietHoaDonBanAPIService.getAllChiTietHoaDonBan()
//                chiTietHoaDonBanList = response.chitiethoadonban ?: emptyList()
//            } catch (e: Exception) {
//                chiTietHoaDonBanList = emptyList()
//            }
//        }
//    }
//}
package com.example.lapstore.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import com.example.lapstore.models.ChiTietHoaDonBan
import com.example.lapstore.models.HoaDonBan
import com.example.lapstore.models.KhachHang
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KhachHangViewModel: ViewModel() {

    var hoaDonBanList by mutableStateOf<List<HoaDonBan>>(emptyList())
        private set

    var chiTietHoaDonBanList by mutableStateOf<List<ChiTietHoaDonBan>>(emptyList())
        private set

    var khachhang by mutableStateOf<KhachHang?>(null)
        private set

    var khachhangUpdateResult by mutableStateOf("")
        private set

    var ThemKhachHangResult by mutableStateOf("")

    val allKhachHang = liveData(Dispatchers.IO) {
        try {
            val response = QuanLyBanLaptopRetrofitClient.khachHangAPIService.getAllKhachHang().execute()
            if (response.isSuccessful) {
                emit(response.body()?.khachhang ?: emptyList())
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun getKhachHangById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                khachhang = QuanLyBanLaptopRetrofitClient.khachHangAPIService.getKhachHangById(id)
            } catch (e: Exception) {
                Log.e("KhachHangViewModel", "Error getting khachhang", e)
            }
        }
    }

    fun updateKhachHang(khachhang: KhachHang) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.khachHangAPIService.updateKhachHang(khachhang)
                }
                khachhangUpdateResult = if (response.success) {
                    "Cập nhật thành công: ${response.message}"
                } else {
                    "Cập nhật thất bại: ${response.message}"
                }
            } catch (e: Exception) {
                khachhangUpdateResult = "Lỗi khi cập nhật khách hàng: ${e.message}"
                Log.e("GioHang Error", "Lỗi khi cập nhật khách hàng: ${e.message}")
            }
        }
    }

    fun ThemKhachHang(khachhang: KhachHang) {
        viewModelScope.launch {
            try {
                val response = QuanLyBanLaptopRetrofitClient.khachHangAPIService.ThemKhachHang(khachhang)
                ThemKhachHangResult = if (response.success) {
                    "Đăng ký thành công: ${response.message}"
                } else {
                    "Đăng ký thất bại: ${response.message}"
                }
            } catch (e: Exception) {
                Log.e("Thêm tài khoản", "Lỗi kết nối: ${e.message}")
            }
        }
    }
fun getAllHoaDonBan() {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = QuanLyBanLaptopRetrofitClient.hoaDonBanAPIService.getAllHoaDonBan()
            hoaDonBanList = response.hoadonban ?: emptyList()
        } catch (e: Exception) {
            hoaDonBanList = emptyList()
        }
    }
}
    fun getAllChiTietHoaDonBan() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = QuanLyBanLaptopRetrofitClient.chiTietHoaDonBanAPIService.getAllChiTietHoaDonBan()
                chiTietHoaDonBanList = response.chitiethoadonban ?: emptyList()
            } catch (e: Exception) {
                chiTietHoaDonBanList = emptyList()
            }
        }
    }
}