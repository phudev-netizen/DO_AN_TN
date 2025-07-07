import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import com.example.lapstore.models.ChiTietHoaDonBan
import com.example.lapstore.models.HoaDonBan
import kotlinx.coroutines.launch

class BinhLuanDanhGiaViewModel : ViewModel() {


    private val _list = MutableLiveData<List<BinhLuanDanhGia>>()
    val list: LiveData<List<BinhLuanDanhGia>> = _list

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun fetchAll() {
        viewModelScope.launch {
            try {
                val res = QuanLyBanLaptopRetrofitClient.binhLuanAPIService.getAll()
                if (res.isSuccessful) {
                    _list.value = res.body()?.binhluandanhgia ?: emptyList()
                } else {
                    _message.value = "Lỗi tải dữ liệu"
                }
            } catch (e: Exception) {
                _message.value = "Lỗi mạng hoặc server ${e.message}"
                Log.e("BinhLuanViewModel", "fetchAll lỗi: ${e.message}")
            }
        }
    }

fun add(binhLuan: BinhLuanDanhGia) {
    if (binhLuan.MaHoaDonBan == 0) {
        _message.value = "Bạn phải mua sản phẩm này mới được bình luận!"
        return
    }
    viewModelScope.launch {
        try {
            val res = QuanLyBanLaptopRetrofitClient.binhLuanAPIService.add(binhLuan)
            _message.value = res.body()?.message ?: "Lỗi thêm"
            fetchAll()
        } catch (e: Exception) {
            _message.value = "Lỗi mạng khi thêm: ${e.message}"
        }
    }
}
    fun update(binhLuan: BinhLuanDanhGia) {
        viewModelScope.launch {
            try {
                val res = QuanLyBanLaptopRetrofitClient.binhLuanAPIService.update(binhLuan)
                _message.value = res.body()?.message ?: "Lỗi cập nhật"
                fetchAll()
            } catch (e: Exception) {
                _message.value = "Lỗi mạng khi cập nhật"
            }
        }
    }

    fun delete(maBinhLuan: String) {
        viewModelScope.launch {
            try {
                val res = QuanLyBanLaptopRetrofitClient.binhLuanAPIService.delete(mapOf("MaBinhLuan" to maBinhLuan))
                _message.value = res.body()?.message ?: "Lỗi xóa"
                fetchAll()
            } catch (e: Exception) {
                _message.value = "Lỗi mạng khi xóa"
            }
        }
    }
fun hideOrShow(maBinhLuan: Int, newTrangThai: String) {
    viewModelScope.launch {
        try {
            val binhLuan = _list.value?.find { it.MaBinhLuan == maBinhLuan }
            if (binhLuan != null) {
                val updated = binhLuan.copy(TrangThai = newTrangThai)
                val res = QuanLyBanLaptopRetrofitClient.binhLuanAPIService.update(updated)
                if (res.isSuccessful) {
                    _message.value = res.body()?.message ?: "Cập nhật trạng thái thành công"
                } else {
                    _message.value = "Lỗi server: ${res.code()} ${res.message()}"
                }
                fetchAll()
            } else {
                _message.value = "Không tìm thấy bình luận"
            }
        } catch (e: Exception) {
            _message.value = "Lỗi mạng khi cập nhật trạng thái: ${e.message}"
        }
    }
 }
}