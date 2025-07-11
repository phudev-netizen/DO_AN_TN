import android.content.Context
import android.net.Uri
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

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

     var danhSachAllSanPham by mutableStateOf<List<SanPham>>(emptyList())






    fun getAllSanPham() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            errorMessage = null
            try {
                val response = QuanLyBanLaptopRetrofitClient.sanphamAPIService.getAllSanPham()
                danhSachAllSanPham = response.sanpham!!
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
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
                danhSachSanPhamCuaKhachHang = response.sanpham!!
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


fun getSanPhamSearch(
    ten: String? = null,
    cpu: String? = null,
    ram: String? = null,
    card: String? = null,
    giaTu: Int? = null,
    giaDen: Int? = null
) {
    viewModelScope.launch {
        isLoading = true
        errorMessage = null
        try {
            val response = withContext(Dispatchers.IO) {
                QuanLyBanLaptopRetrofitClient
                    .sanphamAPIService
                    .searchSanPham(ten, cpu, ram, card, giaTu, giaDen)
            }
            // gán an toàn: nếu null thì thành emptyList()
            danhSach = response.sanpham ?: emptyList()
        } catch (e: Exception) {
            Log.e("SanPhamSearch", "Lỗi tìm kiếm: ${e.message}")
            errorMessage = e.message
            danhSach = emptyList()
        } finally {
            isLoading = false
        }
    }
}


    fun getSanPhamTrongHoaDon(MaHoaDonBan: Int) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    QuanLyBanLaptopRetrofitClient.sanphamAPIService.getSanPhamTheoHoaDon(MaHoaDonBan)
                }
                danhSachSanPhamTrongHoaDon = response.sanpham!!
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

    fun uploadImage(context: Context, uri: Uri, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val contentResolver = context.contentResolver
                val inputStream = contentResolver.openInputStream(uri)
                val bytes = inputStream?.readBytes()

                if (bytes != null) {
                    val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData(
                        "file", "sanpham_${System.currentTimeMillis()}.jpg", requestBody
                    )

                    val response = withContext(Dispatchers.IO) {
                        QuanLyBanLaptopRetrofitClient.sanphamAPIService.uploadImage(body)
                    }

                    if (response.isSuccessful && response.body() != null) {
                        onResult(response.body()?.imageUrl)
                    } else {
                        Log.e("UploadImage", "Upload failed: ${response.errorBody()?.string()}")
                        onResult(null)
                    }
                } else {
                    onResult(null)
                }
            } catch (e: Exception) {
                Log.e("UploadImage", "Lỗi khi upload ảnh: ${e.message}")
                onResult(null)
            }
        }
    }

    fun khoiPhucTonKhoKhiTraHang(danhsachchitiet: List<ChiTietHoaDonBan>) {
        viewModelScope.launch {
            danhsachchitiet.forEachIndexed { index, ct ->
                try {
                    val soLuongTra = ct.SoLuong ?: error("soLuong null tại index $index, MaSP=${ct.MaSanPham}")
                    val maSanPham = ct.MaSanPham ?: error("maSanPham null tại index $index")

                    val sp = danhSachSanPhamTrongHoaDon.find { it.MaSanPham == maSanPham }
                        ?: error("Không tìm thấy sản phẩm với MaSP=$maSanPham tại index $index")

                    val spCapNhat = sp.copy(SoLuong = sp.SoLuong + soLuongTra)

                    Log.d("KHTH", "Phục hồi [#$index] MaSP=$maSanPham, SL=${sp.SoLuong} + $soLuongTra")

                    updateSanPham(spCapNhat)
                } catch (e: Exception) {
                    Log.e("KHTH_ERROR", "Lỗi tại index $index: ${e.message}", e)
                }
            }
        }
    }
    fun getDistinctCpuList(): List<String> =
        danhSachAllSanPham.mapNotNull { it.CPU }.distinct()

    fun getDistinctRamList(): List<String> =
        danhSachAllSanPham.mapNotNull { it.RAM }.distinct()

    fun getDistinctCardList(): List<String> =
        danhSachAllSanPham.mapNotNull { it.CardManHinh }.distinct()

    fun getDistinctSsdList(): List<String> =
        danhSachAllSanPham.mapNotNull { it.SSD }.distinct()

    fun getDistinctManHinhList(): List<String> =
        danhSachAllSanPham.mapNotNull { it.ManHinh }.distinct()

    fun getLoaiSanPhamList(): List<Pair<Int, String>> =
        danhSachAllSanPham.map { it.MaLoaiSanPham to mapLoai(it.MaLoaiSanPham) }.distinctBy { it.first }

    private fun mapLoai(ma: Int): String {
        return when (ma) {
            1 -> "Laptop Gaming"
            2 -> "Văn phòng"
            else -> "Khác"
        }
    }



}