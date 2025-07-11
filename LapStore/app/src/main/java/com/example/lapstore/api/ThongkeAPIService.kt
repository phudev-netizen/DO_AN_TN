import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Header

interface ApiService {
@GET("Thongke/thongkebaocao.php")
suspend fun getThongKeBaoCao(
    @Header("Role") role: String = "admin"
): Response<ThongKeResponse>

@GET("ThongKe/thongkebaocaotheonam.php")
suspend fun getThongKeBaoCaoTheoNam(
    @retrofit2.http.Query("nam") nam: Int,
    @Header("Role") role: String = "admin"
): Response<ThongKeResponse>

@GET("ThongKe/thongke_sanpham.php")
suspend fun getThongKeSanPham(
    @Header("Role") role: String = "admin"
): Response<ThongKeSanPhamResponse>

@GET("ThongKe/thongke_donhang.php")
suspend fun getOrders(
    @Header("Role") role: String = "admin"
): Response<OrderResponse>

}
