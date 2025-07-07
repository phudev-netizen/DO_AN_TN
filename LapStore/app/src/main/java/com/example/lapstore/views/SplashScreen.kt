import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.lapstore.datastore.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext



@Composable
fun SplashScreen(navController: NavHostController, userPreferences: UserPreferences) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val isLoggedOut = withContext(Dispatchers.IO) {
            userPreferences.isLoggedOut()
        }
        val username = withContext(Dispatchers.IO) {
            userPreferences.getLoginInfo().first
        }

        delay(1000) // thời gian chờ splash

        if (!username.isNullOrBlank() && !isLoggedOut) {
            // Đã đăng nhập thì chuyển vào Home kèm tên tài khoản
            navController.navigate("${NavRoute.HOME.route}?tentaikhoan=$username") {
                popUpTo(0) { inclusive = true }
            }
        } else {
            // Nếu đã đăng xuất → quay về trang Home không có tài khoản (giao diện khách)
            navController.navigate(NavRoute.HOME.route) {
                popUpTo(0) { inclusive = true }
            }
        }

    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
