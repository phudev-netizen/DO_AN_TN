import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lapstore.models.SanPham
import com.example.lapstore.views.formatGiaTien
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.text.NumberFormat
import java.util.Locale


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchScreen(
//    navController: NavHostController,
//    makhachhang:String?,
//    tentaikhoan:String?
//) {
//    val systemUiController = rememberSystemUiController()
//    SideEffect {
//        systemUiController.setStatusBarColor(color = Color.Red, darkIcons = false)
//    }
//    var sanPhamViewModel:SanPhamViewModel = viewModel()
//    val danhSachSanPham = sanPhamViewModel.danhSach
//
//    var timkiem by remember { mutableStateOf("") }
//
//    val focusRequester = remember { FocusRequester() }
//
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
//
//    Scaffold(
//        containerColor = Color.White,
//        topBar = {
//            CenterAlignedTopAppBar(
//                navigationIcon = {
//                    IconButton(
//                        onClick = {
//                            navController.popBackStack()
//                        }) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBackIosNew,
//                            contentDescription = "",
//                            tint = Color.White
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = Color.Red
//                ),
//                actions = {
//                    IconButton(
//                        onClick = {
//                            if(tentaikhoan==null && makhachhang == null){
//                                navController.navigate(NavRoute.LOGINSCREEN.route)
//                            }
//                            else{
//                                navController.navigate("${NavRoute.CART.route}?makhachhang=${makhachhang}&tentaikhoan=${tentaikhoan}")
//                            }
//                        }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.ShoppingCart,
//                            contentDescription = "",
//                            tint = Color.White
//                        )
//                    }
//                },
//                title = {
//                    // Search Bar
//                    Row(
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        OutlinedTextField(
//                            value = timkiem,
//                            onValueChange = {
//                                timkiem = it
//                                if (timkiem.isBlank()) {
//                                    sanPhamViewModel.clearSanPhamSearch() // Hàm này cần được thêm vào ViewModel để làm rỗng danh sách
//                                } else {
//                                    sanPhamViewModel.getSanPhamSearch(timkiem)
//                                }
//                            },
//                            modifier = Modifier
//                                .height(50.dp)
//                                .fillMaxWidth()
//                                .focusRequester(focusRequester),
//                            textStyle = TextStyle(
//                                color = Color.Black,
//                                fontSize = 16.sp
//                            ),
//                            colors = OutlinedTextFieldDefaults.colors(
//                                focusedContainerColor = Color.White,
//                                unfocusedContainerColor = Color.White,
//                                focusedBorderColor = Color.White,
//                                unfocusedBorderColor = Color.White
//                            ),
//                            placeholder = {
//                                Text(
//                                    text = "Bạn cần tìm gì",
//                                    style = TextStyle(
//                                        color = Color.Black,
//                                        fontSize = 13.sp
//                                    ),
//                                )
//                            },
//                            trailingIcon = {
//                                Icon(
//                                    imageVector = Icons.Filled.Search,
//                                    contentDescription = "",
//                                    tint = Color.Black
//                                )
//                            },
//                            shape = RoundedCornerShape(50)
//                        )
//                    }
//                }
//
//            )
//
//        },
//    ) {
//        LazyColumn(
//            modifier = Modifier.padding(it)
//        ) {
//            items(danhSachSanPham){sanpham->
//                SanPhamSearchCard(sanpham,makhachhang,tentaikhoan,navController)
//            }
//        }
//
//    }
//}
//
@Composable
fun SanPhamSearchCard(
    sanpham: SanPham,
    makhachhang: String?,
    tentaikhoan: String?,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        onClick = {
            if(tentaikhoan != null)
                navController.navigate(NavRoute.PRODUCTDETAILSCREEN.route + "?id=${sanpham.MaSanPham}&makhachhang=${makhachhang}&tentaikhoan=${tentaikhoan}")
            else
                navController.navigate(NavRoute.PRODUCTDETAILSCREEN.route + "?id=${sanpham.MaSanPham}&makhachhang=${makhachhang}")
        },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = sanpham.HinhAnh,
                contentDescription = sanpham.TenSanPham,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = sanpham.TenSanPham, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = formatGiaTien(sanpham.Gia), color = Color.Red, fontSize = 16.sp)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    makhachhang: String?,
    tentaikhoan: String?
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Red, darkIcons = false)
    }

    val viewModel: SanPhamViewModel = viewModel()

    val danhSachSanPham = viewModel.danhSach
    val isLoading       = viewModel.isLoading
    val errorMessage    = viewModel.errorMessage

    // State cho các bộ lọc
    var ten by remember { mutableStateOf("") }
    var cpu by remember { mutableStateOf<String?>(null) }
    var ram by remember { mutableStateOf<String?>(null) }
    var card by remember { mutableStateOf<String?>(null) }
    var giaTuStr by remember { mutableStateOf("") }
    var giaDenStr by remember { mutableStateOf("") }

    // Options mẫu; bạn có thể load động từ server
    val cpuOptions = listOf("i3", "i5", "i7","i9", "Ryzen 3", "Ryzen 5", "Ryzen 7")
    val ramOptions = listOf("4GB", "8GB", "16GB", "32GB")
    val cardOptions = listOf("Intel", "Integrated", "RTX 4060", "RTX 3060","RTX 3050","NVIDIA GTX")

    // Khi bất cứ tiêu chí nào thay đổi thì gọi API
    LaunchedEffect(ten, cpu, ram, card, giaTuStr, giaDenStr) {
        val giaTu = giaTuStr.toIntOrNull()
        val giaDen = giaDenStr.toIntOrNull()
        viewModel.getSanPhamSearch(
            ten    = ten.takeIf { it.isNotBlank() },
            cpu    = cpu,
            ram    = ram,
            card   = card,
            giaTu  = giaTu,
            giaDen = giaDen
        )
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = null, tint = Color.White)
                    }
                },
                title = { Text("Tìm sản phẩm", color = Color.White) },
                actions = {
                    IconButton(onClick = {
                        if (tentaikhoan == null)
                            navController.navigate(NavRoute.LOGINSCREEN.route)
                        else
                            navController.navigate("${NavRoute.CART.route}?makhachhang=$makhachhang&tentaikhoan=$tentaikhoan")
                    }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Red)
            )
        },
        content = { padding ->
            Column(modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(8.dp)
            ) {
                // Tên
                OutlinedTextField(
                    value = ten,
                    onValueChange = { ten = it },
                    label = { Text("Tên sản phẩm") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                // CPU + RAM + Card trong 1 Row
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DropdownFilter(label = "CPU", options = cpuOptions, selected = cpu, onSelect = { cpu = it })
                }
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DropdownFilter(label = "RAM", options = ramOptions, selected = ram, onSelect = { ram = it })
                }
                Spacer(Modifier.height(8.dp))
                DropdownFilter(label = "GPU", options = cardOptions, selected = card, onSelect = { card = it })
                Spacer(Modifier.height(8.dp))

                // Giá từ – đến
                var giaTu by remember { mutableStateOf("") }
                var giaDen by remember { mutableStateOf("") }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = giaTu,
                        onValueChange = { input ->
                            // Lọc chỉ giữ số
                            val digits = input.filter { it.isDigit() }
                            giaTu = if (digits.isNotEmpty()) {
                                // Format ngay
                                NumberFormat.getNumberInstance(Locale("vi", "VN"))
                                    .format(digits.toLong())
                            } else ""
                        },
                        label = { Text("Giá từ") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = giaDen,
                        onValueChange = { input ->
                            val digits = input.filter { it.isDigit() }
                            giaDen = if (digits.isNotEmpty()) {
                                NumberFormat.getNumberInstance(Locale("vi", "VN"))
                                    .format(digits.toLong())
                            } else ""
                        },
                        label = { Text("Giá đến") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Hiển thị loading / error
                if (isLoading) {
                    Text("Đang tải...", modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                errorMessage?.let {
                    Text("Lỗi: $it", color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                // Danh sách kết quả
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(danhSachSanPham) { sp ->
                        SanPhamSearchCard(sp, makhachhang, tentaikhoan, navController)
                    }
                }
            }
        }
    )
}

@Composable
fun DropdownFilter(
    label: String,
    options: List<String>,
    selected: String?,
    onSelect: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = selected ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    Icons.Outlined.Menu,
                    contentDescription = null,
                    Modifier.clickable { expanded = true }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Tất cả") },
                onClick = { onSelect(null); expanded = false }
            )
            options.forEach { opt ->
                DropdownMenuItem(
                    text = { Text(opt) },
                    onClick = { onSelect(opt); expanded = false }
                )
            }
        }
    }
}

