package com.example.lapstore.views

import DiaChiCard
import NavRoute
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lapstore.models.DiaChi
import com.example.lapstore.viewmodels.DiaChiViewmodel
import com.example.lapstore.viewmodels.KhachHangViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressManagementScreen(
    navController: NavHostController,
    makhachhang: Int?
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.White, darkIcons = false)
    }

    val diaChiViewModel: DiaChiViewmodel = viewModel()
    var listDiaChi = diaChiViewModel.listDiacHi

    LaunchedEffect(makhachhang) {
        diaChiViewModel.getDiaChiKhachHang(makhachhang)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text("Quản lý địa chỉ")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Quay lại",
                            tint = Color.Red
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            items(listDiaChi) { diachi ->
                DiaChiCard(diachi, navController)
            }
            item {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        navController.navigate("${NavRoute.ADDDIACHI.route}?makhachhang=${makhachhang}")
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddCircleOutline,
                            contentDescription = "",
                            tint = Color.White
                        )
                        Text(
                            "Thêm địa chỉ mới",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDiaChiScreen(
    navController: NavHostController,
    makhachhang: Int?
) {
    val maxLength = 10
    var snackbarHostState = remember {
        SnackbarHostState()
    }

    var scope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)
    }
    var hoten by remember { mutableStateOf("") }
    var sodienthoai by remember { mutableStateOf("") }
    var thongtindiachi by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }


    var diaChiViewmodel: DiaChiViewmodel = viewModel()

    var listDiaChi = diaChiViewmodel.listDiacHi
    diaChiViewmodel.getDiaChiKhachHang(makhachhang)

    Log.d("",listDiaChi.toString())

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text(
                        "Địa chỉ mới"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "",
                            tint = Color.Red
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(10.dp)
                .fillMaxSize(),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {
                Column(
                    modifier = Modifier.padding(7.dp)
                ) {
                    Text(
                        "Liên hệ", fontWeight = FontWeight.Bold
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    value = hoten,
                    placeholder = { Text("Họ tên") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = {
                        hoten = it
                    }
                )
                HorizontalDivider(
                    color = Color.Black, // Màu của thanh ngang
                    thickness = 1.dp, // Độ dày của thanh ngang
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    value = sodienthoai,
                    placeholder = { Text("Số điện thoại") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = {
                        if(it.length <=maxLength){
                            sodienthoai = it
                        }
                    }
                )
                HorizontalDivider(
                    color = Color.Black, // Màu của thanh ngang
                    thickness = 1.dp, // Độ dày của thanh ngang
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp))
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {
                Column(
                    modifier = Modifier.padding(7.dp)
                ) {
                    Text(
                        "Địa chỉ", fontWeight = FontWeight.Bold
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    value = thongtindiachi,
                    placeholder = { Text("Tỉnh, huyện, xã, số nhà") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    ),
                    maxLines = 3,
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = {
                        thongtindiachi = it
                    }
                )
                HorizontalDivider(color = Color.Black, // Màu của thanh ngang
                    thickness = 1.dp, // Độ dày của thanh ngang
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp))

            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {
                Column(
                    modifier = Modifier.padding(7.dp)
                ) {
                    Text(
                        "Cài đặt", fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Đặt làm mặc đinh",
                            modifier = Modifier.padding(start = 7.dp)
                        )
                        Switch(
                            checked = isChecked,
                            colors = SwitchDefaults.colors(
                                uncheckedThumbColor = Color.White,
                                checkedTrackColor = Color.Red,
                            ),
                            onCheckedChange = {
                                isChecked = it
                            }
                        )
                    }

                }
            }
            SnackbarHost(
                modifier = Modifier.padding(4.dp),
                hostState = snackbarHostState
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                onClick = {
                    if(hoten == "" || sodienthoai == "" && thongtindiachi == ""){
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Vui lòng nhập đây đủ thông tin"
                            )
                        }
                    }
                    else{
                        if (isChecked) {
                            for (diachi in listDiaChi) {
                                if (diachi.MacDinh == 1) {
                                    var diachi = DiaChi(
                                        diachi.MaDiaChi,
                                        diachi.ThongTinDiaChi,
                                        diachi.MaKhachHang,
                                        diachi.TenNguoiNhan,
                                        diachi.SoDienThoai,
                                        0
                                    )
                                    diaChiViewmodel.updateDiaChi(diachi)
                                }
                            }
                        }
                        if (makhachhang != null) {
                            var diachi = DiaChi(
                                0,
                                thongtindiachi,
                                makhachhang,
                                hoten,
                                sodienthoai,
                                if (isChecked) 1 else 0
                            )
                            var diachinew = DiaChi(
                                0,
                                thongtindiachi,
                                makhachhang,
                                hoten,
                                sodienthoai,
                                1
                            )
                            if(listDiaChi.isEmpty())
                                diaChiViewmodel.addDiaChi(diachinew)
                            else
                                diaChiViewmodel.addDiaChi(diachi)
                        }
                        navController.popBackStack()
                    }
                }
            ) {
                Text(
                    "Thêm địa chỉ mới",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDiaChiScreen(
    navController: NavHostController,
    makhachhang: Int?,
    madiachi: Int
) {
    var snackbarHostState = remember {
        SnackbarHostState()
    }

    val maxLength = 10

    var scope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(color = Color.White, darkIcons = false)
    }

    var hoten by remember { mutableStateOf("") }
    var sodienthoai by remember { mutableStateOf("") }
    var thongtindiachi by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    var trangthaiswitch by remember { mutableStateOf(true) }

    var diaChiViewmodel: DiaChiViewmodel = viewModel()

    var diachiHienTai = diaChiViewmodel.diachi
    var listDiaChi = diaChiViewmodel.listDiacHi

    diaChiViewmodel.getDiaChiKhachHang(makhachhang)
    diaChiViewmodel.getDiaChiByMaDiaChi(madiachi)

    if (diachiHienTai != null) {
        hoten = diachiHienTai.TenNguoiNhan
        sodienthoai = diachiHienTai.SoDienThoai
        thongtindiachi = diachiHienTai.ThongTinDiaChi
        isChecked = if (diachiHienTai.MacDinh == 1) true else false
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text(
                        "Địa chỉ mới"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "",
                            tint = Color.Red
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(10.dp)
                .fillMaxSize(),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {
                Column(
                    modifier = Modifier.padding(7.dp)
                ) {
                    Text(
                        "Liên hệ", fontWeight = FontWeight.Bold
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    value = hoten,
                    placeholder = { Text("Họ tên") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = {
                        hoten = it
                    }
                )
                HorizontalDivider(
                    color = Color.Black, // Màu của thanh ngang
                    thickness = 1.dp, // Độ dày của thanh ngang
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    value = sodienthoai,
                    placeholder = { Text("Số điện thoại") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = {
                        if(it.length <=maxLength){
                            sodienthoai = it
                        }
                    }
                )
                HorizontalDivider(
                    color = Color.Black, // Màu của thanh ngang
                    thickness = 1.dp, // Độ dày của thanh ngang
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp))
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {
                Column(
                    modifier = Modifier.padding(7.dp)
                ) {
                    Text(
                        "Địa chỉ", fontWeight = FontWeight.Bold
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    value = thongtindiachi,
                    placeholder = { Text("Tỉnh, huyện, xã, số nhà") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    ),
                    maxLines = 3,
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = {
                        thongtindiachi = it
                    }
                )
                HorizontalDivider(color = Color.Black, // Màu của thanh ngang
                    thickness = 1.dp, // Độ dày của thanh ngang
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp))

            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {
                Column(
                    modifier = Modifier.padding(7.dp)
                ) {
                    Text(
                        "Cài đặt", fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Đặt làm mặc đinh",
                            modifier = Modifier.padding(start = 7.dp)
                        )

                        if (diachiHienTai != null) {
                            if (diachiHienTai.MacDinh == 1) {
                                trangthaiswitch = false
                            } else {
                                trangthaiswitch = true
                            }
                        }

                        Switch(
                            checked = isChecked,
                            colors = SwitchDefaults.colors(
                                uncheckedThumbColor = Color.White,
                                checkedTrackColor = Color.Red,
                            ),
                            enabled = trangthaiswitch,
                            onCheckedChange = {
                                isChecked = it
                            }
                        )

                    }

                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .border(1.dp, Color.Red, shape = RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if (diachiHienTai != null) {
                        if (diachiHienTai.MacDinh == 1) {
                            openDialog = true
                        } else {
                            openDialog = true
                        }
                    }
                }
            ) {
                Text(
                    "Xóa địa chỉ",
                    fontSize = 17.sp,
                    color = Color.Red
                )
            }
            SnackbarHost(
                modifier = Modifier.padding(4.dp),
                hostState = snackbarHostState
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                onClick = {
                    if(hoten == "" || sodienthoai == "" && thongtindiachi == ""){
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Vui lòng nhập đây đủ thông tin"
                            )
                        }
                    }
                    else{
                        if (isChecked) {
                            for (diachi in listDiaChi) {
                                if (diachi.MacDinh == 1) {
                                    var diachi = DiaChi(
                                        diachi.MaDiaChi,
                                        diachi.ThongTinDiaChi,
                                        diachi.MaKhachHang,
                                        diachi.TenNguoiNhan,
                                        diachi.SoDienThoai,
                                        0
                                    )
                                    diaChiViewmodel.updateDiaChi(diachi)
                                }
                            }
                        }
                        if (makhachhang != null) {
                            var diachi2 = DiaChi(
                                madiachi,
                                thongtindiachi,
                                makhachhang,
                                hoten,
                                sodienthoai,
                                if (isChecked) 1 else 0
                            )
                            diaChiViewmodel.updateDiaChi(diachi2)
                        }
                        navController.popBackStack()
                    }
                }
            ) {
                Text(
                    "Lưu địa chỉ",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }

            if (openDialog) {
                AlertDialog(
                    containerColor = Color.White,
                    modifier = Modifier.padding(10.dp),
                    onDismissRequest = { openDialog = false },
                    text = {
                        if (diachiHienTai != null) {
                            if (diachiHienTai.MacDinh == 1) {
                                Text(
                                    "Bạn không thể xóa địa chỉ mặc định?",
                                    fontSize = 17.sp
                                )
                            }
                            else{
                                Text(
                                    "Xóa địa chỉ?",
                                    fontSize = 17.sp,
                                )
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (diachiHienTai != null) {
                                    if (diachiHienTai.MacDinh == 1) {
                                        openDialog = false
                                    }
                                    else{
                                        openDialog = false
                                        diaChiViewmodel.deleteDiaChi(madiachi)
                                        navController.popBackStack()
                                    }
                                }
                        }) {
                            Text(
                                "Xác nhận",
                                color = Color.Red
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { openDialog = false }) {
                            Text(
                                "Thoát",
                                fontSize = 17.sp,
                                color = Color.Black
                            )
                        }
                    }
                )
            }
        }
    }
}




