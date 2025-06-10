import android.util.Log
import androidx.lifecycle.*
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
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
            }
        }
    }

    fun add(binhLuan: BinhLuanDanhGia) {
        viewModelScope.launch {
            try {
                val res = QuanLyBanLaptopRetrofitClient.binhLuanAPIService.add(binhLuan)
                // In ra body raw để kiểm tra
                val rawString = res.errorBody()?.string() ?: res.body()?.message ?: "null"
                Log.d("BinhLuanAPI", "Raw response: $rawString")
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
}