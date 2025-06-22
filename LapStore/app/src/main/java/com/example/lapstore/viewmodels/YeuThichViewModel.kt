import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YeuThichViewModel : ViewModel() {
    val danhSachYeuThich = MutableLiveData<List<YeuThich>>()
    val message = MutableLiveData<String?>()
fun xoaYeuThich(maKhachHang: Int, maSanPham: Int, onSuccess: () -> Unit) {
    if (maKhachHang <= 0) {
        return
    }
    QuanLyBanLaptopRetrofitClient.api.xoaYeuThich(maKhachHang, maSanPham)
        .enqueue(object : Callback<ApiResponse3> {
            override fun onResponse(call: Call<ApiResponse3>, response: Response<ApiResponse3>) {
                val body = response.body()
                if (response.isSuccessful && body?.success == true) {
                    message.value = body.message
                    onSuccess()
                    loadDanhSach(maKhachHang)
                } else {
                    message.value = body?.message ?: "Xoá thất bại"
                }
            }
            override fun onFailure(call: Call<ApiResponse3>, t: Throwable) {
                message.value = "Lỗi khi xoá yêu thích: ${t.message}"
            }
        })
}

    fun themYeuThich(maKhachHang: Int, maSanPham: Int, onDone: () -> Unit) {
    QuanLyBanLaptopRetrofitClient.api.themYeuThich(maKhachHang, maSanPham.toString())
        .enqueue(object : Callback<ApiResponse3> {
            override fun onResponse(call: Call<ApiResponse3>, response: Response<ApiResponse3>) {
                val body = response.body()
                if (response.isSuccessful && body?.success == true) {
                    loadDanhSach(maKhachHang)  // cập nhật lại danh sách
                    onDone()
                } else {
                    message.value = body?.message ?: "Thêm thất bại"
                }
            }
            override fun onFailure(call: Call<ApiResponse3>, t: Throwable) {
                message.value = "Lỗi khi thêm: ${t.message}"
            }
        })
}

fun loadDanhSach(maKhachHang: Int) {
    QuanLyBanLaptopRetrofitClient.api.getDanhSachYeuThich(maKhachHang)
        .enqueue(object : Callback<DanhSachYeuThichResponse> {
            override fun onResponse(
                call: Call<DanhSachYeuThichResponse>,
                response: Response<DanhSachYeuThichResponse>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null && body.success) {
                    danhSachYeuThich.value = body.data ?: emptyList()
                } else {
                    val message = body?.message ?: "Tải danh sách yêu thích thất bại"
                    this@YeuThichViewModel.message.value = message
                }
            }
            override fun onFailure(call: Call<DanhSachYeuThichResponse>, t: Throwable) {
                val error = "Lỗi khi tải danh sách: ${t.message}"
                message.value = error
            }
        })
}

}