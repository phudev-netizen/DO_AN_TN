import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import kotlinx.coroutines.launch

class ThongKeViewModel : ViewModel() {
    private val _thongKe = mutableStateOf<ThongKeData?>(null)
    val thongKe: State<ThongKeData?> = _thongKe

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val api = QuanLyBanLaptopRetrofitClient.apiService


    fun fetchThongKe() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getThongKeBaoCao(role = "admin")

                Log.d("ThongKe", "Response body: ${response.body()}")
                if (response.isSuccessful && response.body()?.success == true) {
                    _thongKe.value = response.body()!!.data
                }
            } catch (e: Exception) {
                Log.e("ThongKe", "Lỗi: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
