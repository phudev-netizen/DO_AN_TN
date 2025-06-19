//
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class YeuThichViewModel : ViewModel() {
//
//    val danhSachYeuThich = MutableLiveData<List<YeuThich>>()
//
//    val message = MutableLiveData<String>()
//
//    fun xoaYeuThich(maKhachHang: Int, maSanPham: String) {
//        QuanLyBanLaptopRetrofitClient.api.xoaYeuThich(maKhachHang.toString(), maSanPham)
//            .enqueue(object : Callback<String> {
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    message.value = response.body() ?: "Xóa thất bại"
//                    loadDanhSach(maKhachHang)
//                }
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    message.value = "Lỗi: ${t.message}"
//                }
//            })
//    }
//fun themYeuThich(maKhachHang: Int, maSanPham: String) {
//    QuanLyBanLaptopRetrofitClient.api.themYeuThich(maKhachHang.toString(), maSanPham)
//        .enqueue(object : Callback<ApiResponse> {
//            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                Log.d("YEU_THICH", "API add favorite response: ${response.body()} | Success: ${response.isSuccessful}")
//                val body = response.body()
//                if (body != null && body.success == true) {
//                    message.value = "Thêm thành công"
//                    loadDanhSach(maKhachHang)
//                } else {
//                    message.value = body?.message ?: "Thêm thất bại"
//                }
//            }
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                message.value = "Lỗi: ${t.message}"
//            }
//        })
//}
//
//fun loadDanhSach(maKhachHang: Int) {
//    QuanLyBanLaptopRetrofitClient.api.getDanhSachYeuThich(maKhachHang)
//        .enqueue(object : Callback<DanhSachYeuThichResponse> {
//            override fun onResponse(call: Call<DanhSachYeuThichResponse>, response: Response<DanhSachYeuThichResponse>) {
//                Log.d("YEU_THICH", "API get favorites: ${response.body()} | Success: ${response.isSuccessful}")
//                val body = response.body()
//                if (response.isSuccessful && body != null && body.success) {
//                    danhSachYeuThich.value = body.data ?: emptyList()
//                } else {
//                    message.value = "Tải danh sách yêu thích thất bại"
//                }
//            }
//            override fun onFailure(call: Call<DanhSachYeuThichResponse>, t: Throwable) {
//                message.value = "Lỗi: ${t.message}"
//            }
//        })
//}
//
//}
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lapstore.api.QuanLyBanLaptopRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YeuThichViewModel : ViewModel() {

    val danhSachYeuThich = MutableLiveData<List<YeuThich>>()
    val message = MutableLiveData<String>()

    fun xoaYeuThich(maKhachHang: Int, maSanPham: String) {
        QuanLyBanLaptopRetrofitClient.api.xoaYeuThich(maKhachHang.toString(), maSanPham)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val msg = response.body() ?: "Xóa thất bại"
                    message.value = msg
                    Log.d("YEU_THICH", "Xóa yêu thích: $msg")
                    loadDanhSach(maKhachHang)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    val error = "Lỗi khi xoá yêu thích: ${t.message}"
                    message.value = error
                    Log.e("YEU_THICH", error)
                }
            })
    }

    fun themYeuThich(maKhachHang: Int, maSanPham: String) {
        QuanLyBanLaptopRetrofitClient.api.themYeuThich(maKhachHang.toString(), maSanPham)
            .enqueue(object : Callback<ApiResponse3> {
                override fun onResponse(call: Call<ApiResponse3>, response: Response<ApiResponse3>) {
                    Log.d("YEU_THICH", "API add favorite response: ${response.body()} | Success: ${response.isSuccessful}")
                    val body = response.body()
                    if (response.isSuccessful && body != null && body.success) {
                        message.value = body.message
                        loadDanhSach(maKhachHang)
                    } else {
                        message.value = body?.message ?: "Thêm thất bại"
                    }
                }

                override fun onFailure(call: Call<ApiResponse3>, t: Throwable) {
                    val error = "Lỗi khi thêm yêu thích: ${t.message}"
                    message.value = error
                    Log.e("YEU_THICH", error)
                }
            })
    }

    fun loadDanhSach(maKhachHang: Int) {
        QuanLyBanLaptopRetrofitClient.api.getDanhSachYeuThich(maKhachHang)
            .enqueue(object : Callback<DanhSachYeuThichResponse> {
                override fun onResponse(call: Call<DanhSachYeuThichResponse>, response: Response<DanhSachYeuThichResponse>) {
                    Log.d("YEU_THICH", "API get favorites: ${response.body()} | Success: ${response.isSuccessful}")
                    val body = response.body()
                    if (response.isSuccessful && body != null && body.success) {
                        danhSachYeuThich.value = body.data ?: emptyList()
                    } else {
                        message.value = body?.message ?: "Tải danh sách yêu thích thất bại"
                    }
                }

                override fun onFailure(call: Call<DanhSachYeuThichResponse>, t: Throwable) {
                    val error = "Lỗi khi tải danh sách: ${t.message}"
                    message.value = error
                    Log.e("YEU_THICH", error)
                }
            })
    }
}
