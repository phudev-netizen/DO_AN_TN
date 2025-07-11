package com.example.lapstore.views

import ChangePasswordSection
import NavRoute
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lapstore.datastore.UserPreferences
import com.example.lapstore.models.KhachHang
import com.example.lapstore.models.TaiKhoan
import com.example.lapstore.viewmodels.KhachHangViewModel
import com.example.lapstore.viewmodels.TaiKhoanViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcccountScreen(
    navController: NavHostController,
    tentaikhoan: String
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Red, darkIcons = false)
    }

    // Lấy ViewModel
    val taiKhoanViewModel: TaiKhoanViewModel = viewModel()

    // Lấy thông tin tài khoản từ ViewModel
    val taikhoan = taiKhoanViewModel.taikhoan

    // Gọi API nếu taikhoan chưa được lấy
    LaunchedEffect(tentaikhoan) {
        taiKhoanViewModel.getTaiKhoanByTentaikhoan(tentaikhoan)
    }

    // Kiểm tra nếu tài khoản chưa có dữ liệu
    if (taikhoan == null) {
        Text(text = "Đang tải thông tin tài khoản...")
        return
    }

    // Kiểm tra trạng thái Tab
    var currentTab by remember { mutableStateOf("accountInfo") }

    Scaffold(
        containerColor = Color.Red,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Red
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(
                        "TÀI KHOẢN",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

            )

        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                ) {

                    when (currentTab) {
                        "duyetdonhang" -> navController.navigate(NavRoute.ADMINSCREEN.route)
                        "duyetkhuyenmai" -> navController.navigate("${NavRoute.ADMIN_KHUYENMAI.route}?tentaikhoan=${taikhoan.TenTaiKhoan}") // Thêm route này
                        "quanLySanPham" -> navController.navigate(NavRoute.PRODUCT_MANAGEMENT.route)
                        "thongke" -> navController.navigate(NavRoute.THONGKE.route)
                        "accountInfo" -> AccountInfoSection(tentaikhoan)
                        "cartManagement" -> navController.navigate("${NavRoute.QUANLYDONHANG.route}?makhachhang=${taikhoan.MaKhachHang}")
                        "changePassword" -> ChangePasswordSection(tentaikhoan)
                        "addresses" -> navController.navigate("${NavRoute.DIACHISCREEN.route}?makhachhang=${taikhoan.MaKhachHang}")

                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Phần menu danh sách bên dưới
            item {
                AccountOptionsSection(
                    onOptionSelected = { selectedTab ->
                        currentTab = selectedTab
                    }, currentTab = currentTab,
                    navController,
                    taikhoan
                )
            }
        }
    }
}
@Composable
fun AccountInfoSection(
    tentaikhoan: String
) {
    val maxLength = 10

    val taikhoanviewModel: TaiKhoanViewModel = viewModel()
    val khachhangviewModel: KhachHangViewModel = viewModel()

    val taikhoan = taikhoanviewModel.taikhoan
    val khachhang = khachhangviewModel.khachhang

    var isFocused by remember { mutableStateOf(false) }
    var isButtonEnabled by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(tentaikhoan) {
        if (tentaikhoan.isNotEmpty()) {
            taikhoanviewModel.getTaiKhoanByTentaikhoan(tentaikhoan)
        }
    }

    if (taikhoan != null) {
        khachhangviewModel.getKhachHangById(taikhoan.MaKhachHang.toString())
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Thông tin tài khoản", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Spacer(modifier = Modifier.height(8.dp))
            if (khachhang != null && taikhoan != null) {
                val isEditable = taikhoan.LoaiTaiKhoan == 0 // khách hàng mới được chỉnh sửa
                val hoTen = remember { mutableStateOf(khachhang.HoTen) }
                val soDienThoai = remember { mutableStateOf(khachhang.SoDienThoai) }
                val email = remember { mutableStateOf(khachhang.Email) }
                val gioiTinh = remember { mutableStateOf(khachhang.GioiTinh) }
                val selectedDay = remember { mutableStateOf(khachhang.NgaySinh.split("-")[2]) }
                val selectedMonth = remember { mutableStateOf(khachhang.NgaySinh.split("-")[1]) }
                val selectedYear = remember { mutableStateOf(khachhang.NgaySinh.split("-")[0]) }

                val initialHoTen = remember { mutableStateOf(khachhang.HoTen) }
                val initialSoDienThoai = remember { mutableStateOf(khachhang.SoDienThoai) }
                val initialEmail = remember { mutableStateOf(khachhang.Email) }
                val initialGioiTinh = remember { mutableStateOf(khachhang.GioiTinh) }
                val initialNgaySinh = remember { mutableStateOf(khachhang.NgaySinh) }

                fun checkIfChanged(): Boolean {
                    return hoTen.value != initialHoTen.value ||
                            soDienThoai.value != initialSoDienThoai.value ||
                            email.value != initialEmail.value ||
                            gioiTinh.value != initialGioiTinh.value ||
                            selectedDay.value != initialNgaySinh.value.split("-")[2] ||
                            selectedMonth.value != initialNgaySinh.value.split("-")[1] ||
                            selectedYear.value != initialNgaySinh.value.split("-")[0]
                }

                LaunchedEffect(hoTen.value, soDienThoai.value, email.value, gioiTinh.value, selectedDay.value, selectedMonth.value, selectedYear.value) {
                    isButtonEnabled = checkIfChanged()
                }
                // Họ tên
                Text("Họ Tên: ", fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = hoTen.value,
                    onValueChange = {
                        hoTen.value = it
                        isButtonEnabled = checkIfChanged()
                                    },
                    modifier = Modifier.fillMaxWidth().onFocusChanged {
                        if (it.isFocused) isFocused = true
                    },
                    enabled = isEditable,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Red,
                        unfocusedBorderColor = Color.Red,
                        focusedLabelColor = Color.Red
                    ),
                    shape = RoundedCornerShape(17.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Giới tính: ", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        listOf("Nam", "Nữ").forEach { gender ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = gioiTinh.value == gender,
                                    onClick = { gioiTinh.value = gender },
                                    enabled = isEditable,
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color.Red
                                    )

                                )
                                Text(text = gender)
                            }
                        }
                    }
                }

                // Số điện thoại
                Text("Số điện thoại: ", fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = soDienThoai.value,
                    onValueChange = {
                        if(it.length <=maxLength){
                            soDienThoai.value = it
                            isButtonEnabled = checkIfChanged()
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth().onFocusChanged {
                        if (it.isFocused) isFocused = true
                    },
                    enabled = isEditable,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Red,
                        unfocusedBorderColor = Color.Red,
                        focusedLabelColor = Color.Red
                    ),
                    shape = RoundedCornerShape(17.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Email
                Text("Email: ", fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                        isButtonEnabled = checkIfChanged()},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth().onFocusChanged {
                        if (it.isFocused) isFocused = true
                    },
                    enabled = isEditable,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Red,
                        unfocusedBorderColor = Color.Red,
                        focusedLabelColor = Color.Red
                    ),
                    shape = RoundedCornerShape(17.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Ngày sinh
                Text("Ngày sinh: ", fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DropdownMenuField(
                        label = "Ngày",
                        items = (1..31).map { it.toString() },
                        selectedValue = selectedDay.value,
                        onValueChange = { selectedDay.value = it },
                        enabled = isEditable,
                        modifier = Modifier
                            .weight(1.15f)
                            .padding(end = 0.5.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    DropdownMenuField(
                        label = "Tháng",
                        items = (1..12).map { it.toString() },
                        selectedValue = selectedMonth.value,
                        onValueChange = { selectedMonth.value = it },
                        enabled = isEditable,
                        modifier = Modifier
                            .weight(1.2f)
                            .padding(horizontal = 0.5.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    DropdownMenuField(
                        label = "Năm",
                        items = (1900..2025).map { it.toString() }.reversed(),
                        selectedValue = selectedYear.value,
                        onValueChange = { selectedYear.value = it },
                        enabled = isEditable,
                        modifier = Modifier
                            .weight(1.4f)
                            .padding(start = 0.5.dp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                SnackbarHost(
                    modifier = Modifier.padding(4.dp),
                    hostState = snackbarHostState,
                )
                        Button(
                            onClick = {
                                // Xử lý lưu dữ liệu
                                val regexName = "^[a-zA-Z\\p{L} ]+$"
                                val regexPhone = "^\\d{10}$".toRegex()

                                if (hoTen.value.isBlank() || !hoTen.value.matches(Regex(regexName))) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Họ và tên không hợp lệ",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                } else if (!regexPhone.matches(soDienThoai.value)) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Số điện thoại phải có 10 số."
                                        )
                                    }
                                } else if (email.value.isBlank()) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Email không được để trống.",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                } else if (!email.value.contains("@")) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Email phải chứa ký tự '@'.",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                } else {
                                    val khachHang = KhachHang(
                                        MaKhachHang = khachhang.MaKhachHang,
                                        HoTen = hoTen.value,
                                        GioiTinh = gioiTinh.value,
                                        NgaySinh = "${selectedYear.value}-${selectedMonth.value}-${selectedDay.value}",
                                        Email = email.value,
                                        SoDienThoai = soDienThoai.value
                                    )
                                    khachhangviewModel.updateKhachHang(khachHang)
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Cập nhật thành công",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red) ,
                            enabled = isEditable && isButtonEnabled,

                            ) {
                            Text("LƯU THAY ĐỔI", color = Color.White)
                        }
            }
        }
    }
}


@Composable
fun DropdownMenuField(
    label: String,
    items: List<String>,
    selectedValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
) {
    var isExpanded by remember { mutableStateOf(false) } // Trạng thái menu

    Column(modifier = modifier) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = { onValueChange(it) },
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                Text(
                    text = "▼",
                    modifier = Modifier
                        .clickable { isExpanded = !isExpanded }
                        .padding(8.dp)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Red,
                focusedLabelColor = Color.Red
            ),
            shape = RoundedCornerShape(17.dp),
        )

        DropdownMenu(
            containerColor = Color.White,
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .heightIn(max = 250.dp)
                .widthIn(300.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item, fontWeight = FontWeight.Bold, fontSize = 15.sp) },
                    onClick = {
                        onValueChange(item)
                        isExpanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun AccountOptionsSection(
    onOptionSelected: (String) -> Unit,
    currentTab: String,
    navController: NavHostController,
    taikhoan: TaiKhoan
) {
    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current


    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {


        Column(modifier = Modifier.padding(8.dp)) {

            if (taikhoan.LoaiTaiKhoan == 1) {
                AccountOptionItem(
                    iconRes = Icons.Filled.AdminPanelSettings,
                    label = "Duyệt đơn hàng",
                    isSelected = currentTab == "duyetdonhang",
                    onClick = { onOptionSelected("duyetdonhang") }
                )
                AccountOptionItem(
                    iconRes = Icons.Filled.AdminPanelSettings,
                    label = "Duyệt khuyến mãi",
                    isSelected = currentTab == "duyetkhuyenmai",
                    onClick = { onOptionSelected("duyetkhuyenmai") }
                )
                AccountOptionItem(
                    iconRes = Icons.Filled.ManageAccounts,
                    label = "QUẢN LÝ SẢN PHẨM",
                    isSelected = currentTab == "quanLySanPham",
                    onClick = { onOptionSelected("quanLySanPham") }
                )
                AccountOptionItem(
                    iconRes = Icons.Filled.ManageAccounts,
                    label = "Thống Kê",
                    isSelected = currentTab == "thongke",
                    onClick = { onOptionSelected("thongke") }
                )
                AccountOptionItem(
                    iconRes = Icons.Filled.ExitToApp,
                    label = "Đăng xuất",
                    isSelected = false, // Không cần trạng thái cho mục đăng xuất
                    onClick = {
                        openDialog.value = true
                    }
                )
            } else {
                AccountOptionItem(
                    iconRes = Icons.Filled.Person,
                    label = "Thông tin tài khoản",
                    isSelected = currentTab == "accountInfo",
                    onClick = { onOptionSelected("accountInfo") }
                )
                AccountOptionItem(
                    iconRes = Icons.Filled.LocationOn,
                    label = "Số địa chỉ",
                    isSelected = currentTab == "addresses",
                    onClick = { onOptionSelected("addresses") }
                )
                AccountOptionItem(
                    iconRes = Icons.Filled.ShoppingCart,
                    label = "Quản lý đơn hàng",
                    isSelected = currentTab == "cartManagement",
                    onClick = { onOptionSelected("cartManagement") }
                )
                AccountOptionItem(
                    iconRes = Icons.Filled.Lock,
                    label = "Đổi mật khẩu",
                    isSelected = currentTab == "changePassword",
                    onClick = { onOptionSelected("changePassword") }
                )
                AccountOptionItem(
                    iconRes = Icons.Filled.ExitToApp,
                    label = "Đăng xuất",
                    isSelected = false, // Không cần trạng thái cho mục đăng xuất
                    onClick = {
                        openDialog.value = true
                    }
                )
            }
        }
    }

    if (openDialog.value == true) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { openDialog.value = false },
            title = { Text("Đăng xuất") },
            text = { Text("Đăng xuất tài khoản của bạn?", fontSize = 17.sp) },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false

                        // 👉 Xóa thông tin login đã lưu
                        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit().clear().apply()

                        // 👉 Đánh dấu là đã đăng xuất
                        CoroutineScope(Dispatchers.IO).launch {
                            val userPrefs = UserPreferences(context)
                            userPrefs.setLoggedOut(true)
                        }

                        // 👉 Quay về màn hình đăng nhập
                        navController.navigate(NavRoute.LOGINSCREEN.route) {
                            popUpTo(0) // Xóa toàn bộ back stack
                        }
                    }


                ) {
                    Text("OK", color = Color.Red, fontSize = 14.sp)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Hủy", color = Color.Black, fontSize = 14.sp)
                }
            }
        )
    }
}

@Composable
fun AccountOptionItem(
    iconRes: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = iconRes,
            contentDescription = label,
            tint = if (isSelected) Color.Red else Color.Gray
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = if (isSelected) Color.Red else Color.Black,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}



