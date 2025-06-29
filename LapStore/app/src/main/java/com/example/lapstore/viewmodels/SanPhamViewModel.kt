import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import com.example.lapstore.models.ChiTietHoaDonBan
import com.example.lapstore.models.HoaDonBan
import com.example.lapstore.models.KhachHang
import com.example.lapstore.models.SanPham
import com.example.lapstore.models.TaiKhoan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch/**/
import kotlinx.coroutines.withContext

class SanPhamViewModel : ViewModel() {


    var danhSachSanPhamTrongHoaDon by mutableStateOf<List<SanPham>>(emptyList())

    private val _danhSachSanPhamGaming = MutableStateFlow<List<SanPham>>(emptyList())
    val danhSachSanPhamGaming: StateFlow<List<SanPham>> = _danhSachSanPhamGaming

    private val _danhSachSanPhamVanPhong = MutableStateFlow<List<SanPham>>(emptyList())
    val danhSachSanPhamVanPhong: StateFlow<List<SanPham>> = _danhSachSanPhamVanPhong

    var danhSachSanPhamCuaKhachHang by mutableStateOf<List<SanPham>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    var sanPhamUpdateResult by mutableStateOf("")
        private set

    var sanPham by mutableStateOf<SanPham?>(null)
        private set

    var danhSach by mutableStateOf<List<SanPham>>(emptyList())

    // SanPhamViewModel.kt
    val taikhoan = MutableStateFlow<TaiKhoan?>(null)

    private val _danhsachSanPham = MutableStateFlow<List<SanPham>>(emptyList())
    val danhsachSanPham: StateFlow<List<SanPham>> get() = _danhsachSanPham

    // Lấy danh sách phụ kiện từ API (giả sử id loại phụ kiện là 3)

    private val _danhSachSanPhamPhuKien = MutableStateFlow<List<SanPham>>(emptyList())
    val danhSachSanPhamPhuKien: StateFlow<List<SanPham>> = _danhSachSanPhamPhuKien

    // Lấy danh sách phụ kiện từ API (giả sử id loại phụ kiện là 4)
    private val _danhSachSanPhamPhuKienphim = MutableStateFlow<List<SanPham>>(emptyList())
    val danhSachSanPhamPhuKienphim: StateFlow<List<SanPham>> = _danhSachSanPhamPhuKienphim

     var danhSachAllSanPham by mutableStateOf<List<SanPham>>(emptyList())
    // ViewModel
//    private val _danhSachAllSanPham = MutableStateFlow<List<SanPham>>(emptyList())
//    val danhSachAllSanPham: StateFlow<List<SanPham>> = _danhSachAllSanPham

//

    fun getAllSanPham() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            errorMessage = null
            try {
                val response = QuanLyBanLaptopRetrofitClient.sanphamAPIService.getAllSanPham()
                danhSachAllSanPham = response.sanpham
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }


    //    fun getSanPhamTheoLoaiPhuKien() {
//        viewModelScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.getSanPhamTheoLoai(3)
//                }
//                Log.d("SanPhamViewModel", "PhuKien API trả về: ${response.sanpham}")
//                _danhSachSanPhamPhuKien.value = response.sanpham ?: emptyList()
//            } catch (e: Exception) {
//                Log.e("SanPham Error", "Lỗi khi lấy sanpham: ${e.message}")
//                _danhSachSanPhamPhuKien.value = emptyList()
//            }
//        }
//    }
    fun getSanPhamTheoLoaiRAM() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.getSanPhamTheoLoai(3)
                }
                Log.d("SanPhamViewModel", "PhuKien API trả về: ${response.sanpham}")
                _danhSachSanPhamPhuKien.value = response.sanpham ?: emptyList()
            } catch (e: Exception) {
                Log.e("SanPham Error", "Lỗi khi lấy sanpham: ${e.message}")
                _danhSachSanPhamPhuKien.value = emptyList()
            }
        }
    }

    fun getSanPhamTheoLoaiPHIM() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.getSanPhamTheoLoai(4)
                }
                Log.d("SanPhamViewModel", "PhuKien API trả về: ${response.sanpham}")
                _danhSachSanPhamPhuKienphim.value = response.sanpham ?: emptyList()
            } catch (e: Exception) {
                Log.e("SanPham Error", "Lỗi khi lấy sanpham: ${e.message}")
                _danhSachSanPhamPhuKienphim.value = emptyList()
            }
        }
    }

    fun getSanPhamTheoLoaiGaming() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.getSanPhamTheoLoai(2)
                }
                _danhSachSanPhamGaming.value = response.sanpham ?: emptyList()
            } catch (e: Exception) {
                Log.e("SanPham Error", "Lỗi khi lấy sanpham: ${e.message}")
                _danhSachSanPhamGaming.value = emptyList()
            }
        }
    }

    fun getSanPhamTheoLoaiVanPhong() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.getSanPhamTheoLoai(1)
                }
                _danhSachSanPhamVanPhong.value = response.sanpham ?: emptyList()
            } catch (e: Exception) {
                Log.e("SanPham Error", "Lỗi khi lấy sanpham: ${e.message}")
                _danhSachSanPhamVanPhong.value = emptyList()
            }
        }
    }

    fun getSanPhamTheoGioHang(MaKhachHang: Int) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.getSanPhamByGioHang(MaKhachHang)
                }
                danhSachSanPhamCuaKhachHang = response.sanpham
            } catch (e: Exception) {
                Log.e("SanPham Error", "Lỗi khi lấy sản phẩm: ${e.message}")
            }
        }
    }

    fun getSanPhamById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                sanPham = QuanLyBanLaptopRetrofitClient.sanphamAPIService.getSanPhamById(id)
            } catch (e: Exception) {
                Log.e("SanPhamViewModel", "Error getting SanPham", e)
            }
        }
    }

    fun getSanPhamById2(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val sanPham = QuanLyBanLaptopRetrofitClient.sanphamAPIService.getSanPhamById(id)
                _danhsachSanPham.update { currentList -> currentList + sanPham }
            } catch (e: Exception) {
                Log.e("SanPhamViewModel", "Error getting SanPham", e)
            }
        }
    }


    fun getSanPhamSearch(search: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.searchSanPham(search)
                }
                danhSach = response.sanpham
            } catch (e: Exception) {
                Log.e("SanPham Error", "Lỗi khi lấy sản phẩm: ${e.message}")
            }
        }
    }

    fun getSanPhamTrongHoaDon(MaHoaDonBan: Int) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.getSanPhamTheoHoaDon(MaHoaDonBan)
                }
                danhSachSanPhamTrongHoaDon = response.sanpham
            } catch (e: Exception) {
                Log.e("Sản Phẩm Error", "Lỗi khi lấy Sản Phẩm")
            }
        }
    }

    fun clearSanPhamSearch() {
        danhSach = emptyList()
    }

    fun updateSanPham(sanpham: SanPham) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.updateSanPham(sanpham)
                }
                sanPhamUpdateResult = if (response.success) {
                    "Cập nhật thành công: ${response.message}"
                } else {
                    "Cập nhật thất bại: ${response.message}"
                }
            } catch (e: Exception) {
                sanPhamUpdateResult = "Lỗi khi cập nhật khách hàng: ${e.message}"
                Log.e("SanPham Error", "Lỗi khi cập nhật khách hàng: ${e.message}")
            }
        }
    }

//    fun createSanPham(sanpham: SanPham, onResult: (Boolean, String) -> Unit = {_,_->}) {
//        viewModelScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.createSanPham(sanpham)
//                }
//                Log.d("SanPham", "Create response: $response")
//                if (response.success) {
//                    getAllSanPham()
//                    onResult(true, "Thêm sản phẩm thành công")
//                } else {
//                    onResult(false, "Thêm sản phẩm thất bại: ${response.message}")
//                }
//            } catch (e: Exception) {
//                onResult(false, "Lỗi khi thêm sản phẩm: ${e.message}")
//                Log.e("SanPham Error", "Lỗi khi thêm sản phẩm: ${e.message}")
//            }
//        }
//    }
fun createSanPham(sanpham: SanPham, onResult: (Boolean, String) -> Unit = {_,_->}) {
    viewModelScope.launch {
        try {
            val response = withContext(Dispatchers.IO) {
                QuanLyBanLaptopRetrofitClient.sanphamAPIService.createSanPham(sanpham)
            }
            Log.d("SanPham", "Create response: $response")
            if (response.success) {
                getAllSanPham()
                onResult(true, "Thêm sản phẩm thành công")
            } else {
                onResult(false, "Thêm sản phẩm thất bại: ${response.message}")
            }
        } catch (e: Exception) {
            onResult(false, "Lỗi khi thêm sản phẩm: ${e.message}")
            Log.e("SanPham Error", "Lỗi khi thêm sản phẩm: ${e.message}")
        }
    }
}

    fun updateSanPham(sanpham: SanPham, onResult: (Boolean, String) -> Unit = {_,_->}) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.updateSanPham(sanpham)
                }
                if (response.success) {
                    getAllSanPham()
                    onResult(true, "Cập nhật thành công")
                } else {
                    onResult(false, "Cập nhật thất bại: ${response.message}")
                }
            } catch (e: Exception) {
                onResult(false, "Lỗi khi cập nhật sản phẩm: ${e.message}")
                Log.e("SanPham Error", "Lỗi khi cập nhật sản phẩm: ${e.message}")
            }
        }
    }

    fun deleteSanPham(maSanPham: Int, onResult: (Boolean, String) -> Unit = {_,_->}) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.deleteSanPham(maSanPham)
                }
                if (response.success) {
                    getAllSanPham()
                    onResult(true, "Xóa sản phẩm thành công")
                } else {
                    onResult(false, "Xóa sản phẩm thất bại: ${response.message}")
                }
            } catch (e: Exception) {
                onResult(false, "Lỗi khi xóa sản phẩm: ${e.message}")
                Log.e("SanPham Error", "Lỗi khi xóa sản phẩm: ${e.message}")
            }
        }
    }

//    fun getSanPhamByMaSanPham(maSanPham: Int): SanPham? {
//        return danhSachAllSanPham.find { it.MaSanPham == maSanPham }
//    }

}