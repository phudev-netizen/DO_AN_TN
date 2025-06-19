import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Header

interface ApiService {
//@GET("Thongke/thongkebaocao.php")
//suspend fun getThongKeBaoCao(
//    @Header("Role") role: String = "admin"
//): Response<ThongKeResponse>
@GET("Thongke/thongkebaocao.php")
suspend fun getThongKeBaoCao(
    @Header("Role") role: String = "admin" // <== Cái này ĐÃ có rồi
): Response<ThongKeResponse>

}
