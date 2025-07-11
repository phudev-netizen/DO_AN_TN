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

    private val _data = mutableStateOf<ThongKeSanPhamResponse?>(null)
    val data: State<ThongKeSanPhamResponse?> = _data

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _orders = mutableStateOf<List<Order>>(emptyList())
    val orders: State<List<Order>> = _orders

    private val _ordersLoading = mutableStateOf(false)
    val ordersLoading: State<Boolean> = _ordersLoading



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
    fun fetchThongKeTheoNam(nam: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getThongKeBaoCaoTheoNam(nam = nam, role = "admin")

                Log.d("ThongKe", "Response body (năm $nam): ${response.body()}")
                if (response.isSuccessful && response.body()?.success == true) {
                    _thongKe.value = response.body()!!.data
                }
            } catch (e: Exception) {
                Log.e("ThongKe", "Lỗi khi tải thống kê theo năm: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchThongKeSanPham() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = QuanLyBanLaptopRetrofitClient.apiService.getThongKeSanPham()
                if (response.isSuccessful && response.body()?.success == true) {
                    _data.value = response.body()
                }
            } catch (e: Exception) {
                Log.e("ThongKeSanPham", "Lỗi: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }
    fun fetchOrders() {
        viewModelScope.launch {
            _ordersLoading.value = true
            try {
                val response = api.getOrders()
                if (response.isSuccessful && response.body()?.success == true) {
                    _orders.value = response.body()!!.data
                }
            } catch (e: Exception) {
                Log.e("ThongKeViewModel", "Lỗi: ${e.message}")
            } finally {
                _ordersLoading.value = false
            }
        }
    }

}
