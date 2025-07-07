import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.lapstore.models.SanPham
import com.example.lapstore.views.formatGiaTien


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductManagementScreen(
    navController: NavHostController,
    viewModel: SanPhamViewModel
) {
    var showForm by remember { mutableStateOf(false) }
    var editingSanPham by remember { mutableStateOf<SanPham?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getAllSanPham()
    }

    val danhSachSanPham by remember { viewModel::danhSachAllSanPham }
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quản lý sản phẩm", style = MaterialTheme.typography.headlineSmall) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingSanPham = null
                    showForm = true
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Thêm sản phẩm")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                errorMessage != null -> {
                    Text(
                        text = "Lỗi: $errorMessage",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(danhSachSanPham) { sanPham ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
//                                    // Hình ảnh placeholder

                                        Box(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .background(Color.LightGray, RoundedCornerShape(12.dp))

                                        ) {
                                            AsyncImage(
                                                model = sanPham.HinhAnh,
                                                contentDescription = "Hình ảnh sản phẩm",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(100.dp)
                                                    .clip(RoundedCornerShape(12.dp)),
                                                contentScale = ContentScale.Crop
                                            )
                                        }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = sanPham.TenSanPham,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = "Giá: ${formatGiaTien(sanPham.Gia)}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "Mô tả: ${sanPham.MoTa}",
                                            style = MaterialTheme.typography.bodySmall,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    Column {
                                        IconButton(onClick = {
                                            editingSanPham = sanPham
                                            showForm = true
                                        }) {
                                            Icon(Icons.Default.Edit, contentDescription = "Sửa")
                                        }
                                        IconButton(onClick = {
                                            viewModel.deleteSanPham(sanPham.MaSanPham) { success, msg ->
                                                // Show Snackbar nếu cần
                                            }
                                        }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Xóa")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            val context = LocalContext.current

            if (showForm) {
                ProductFormDialog(
                    sanPham = editingSanPham,
                    onDismiss = { showForm = false },
//                    onSave = { sanPham ->
//                        if (sanPham.MaSanPham == 0) {
//                            viewModel.createSanPham(sanPham) { _, _ -> showForm = false }
//                        } else {
//                            viewModel.updateSanPham(sanPham) { _, _ -> showForm = false }
//                        }
//                    }
                    onSave = { sanPham, imageUri ->
                        if (imageUri != null) {
                            viewModel.uploadImage(context, imageUri) { imageUrl ->
                                if (imageUrl != null) {
                                    // Tạo bản mới để chắc chắn có imageUrl
                                    val updatedSanPham = sanPham.copy(HinhAnh = imageUrl)

                                    if (sanPham.MaSanPham == 0) {
                                        viewModel.createSanPham(updatedSanPham) { _, _ -> showForm = false }
                                    } else {
                                        viewModel.updateSanPham(updatedSanPham) { _, _ -> showForm = false }
                                    }
                                } else {
                                    // Upload ảnh thất bại
                                }
                            }
                        } else {
                            // Không chọn ảnh mới → dùng ảnh cũ đã có trong sanPham
                            if (sanPham.MaSanPham == 0) {
                                viewModel.createSanPham(sanPham) { _, _ -> showForm = false }
                            } else {
                                viewModel.updateSanPham(sanPham) { _, _ -> showForm = false }
                            }
                        }
                    }

                )
            }
        }
    }
}
fun formatGiaTien(gia: Double): String {
    return "%,.0f₫".format(gia)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormDialog(
    sanPham: SanPham?,
    onDismiss: () -> Unit,
//    onSave: (SanPham) -> Unit
     onSave: (SanPham, Uri?) -> Unit
) {
    val cpuOptions = listOf("Intel i5", "Intel i7", "AMD Ryzen 5", "AMD Ryzen 7", "Apple M1", "Apple M2")
    val ramOptions = listOf("4GB", "8GB", "16GB", "32GB", "64GB")
    val cardOptions = listOf("Integrated", "GTX 1650", "RTX 3050", "RTX 3060", "RTX 4060", "RTX 4080")
    val mauSacOptions = listOf(1 to " Đen", 2  to "Trắng")
    val maloaiOptions = listOf(
        1 to "Laptop Gaming",
        2 to "Laptop Văn phòng"
    )
    val ssdOptions = listOf("128GB", "256GB", "512GB", "1TB", "2TB")
    val manHinhOptions = listOf("13.3\" FHD", "14\" FHD", "15.6\" FHD", "16\" 2K", "17\" 4K", "18\" QHD")
    val trangThaiOptions = listOf("Kinh doanh" to 1, "Ngừng bán" to 0)

    // Image picker launcher
    val imageUriState = remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUriState.value = uri
    }

    var tenSanPham by remember { mutableStateOf(sanPham?.TenSanPham ?: "") }
    var gia by remember { mutableStateOf(sanPham?.Gia?.toString() ?: "") }
    var moTa by remember { mutableStateOf(sanPham?.MoTa ?: "") }
    var soLuong by remember { mutableStateOf(sanPham?.SoLuong?.toString() ?: "") }
    var hinhAnh by remember { mutableStateOf(sanPham?.HinhAnh ?: "") }

    var selectedLoai by remember {
        mutableStateOf(maloaiOptions.find { it.first == sanPham?.MaLoaiSanPham } ?: maloaiOptions.first())
    }
    var loaiExpanded by remember { mutableStateOf(false) }

    var selectedCPU by remember {
        mutableStateOf(cpuOptions.find { it == sanPham?.CPU } ?: cpuOptions.first())
    }
    var cpuExpanded by remember { mutableStateOf(false) }

    var selectedRAM by remember {
        mutableStateOf(ramOptions.find { it == sanPham?.RAM } ?: ramOptions.first())
    }
    var ramExpanded by remember { mutableStateOf(false) }

    var selectedCard by remember {
        mutableStateOf(cardOptions.find { it == sanPham?.CardManHinh } ?: cardOptions.first())
    }
    var cardExpanded by remember { mutableStateOf(false) }

    var selectedSSD by remember {
        mutableStateOf(ssdOptions.find { it == sanPham?.SSD } ?: ssdOptions.first())
    }
    var ssdExpanded by remember { mutableStateOf(false) }

    var selectedManHinh by remember {
        mutableStateOf(manHinhOptions.find { it == sanPham?.ManHinh } ?: manHinhOptions.first())
    }
    var manHinhExpanded by remember { mutableStateOf(false) }

    var selectedMauSac by remember {
        mutableStateOf(
            if ((sanPham?.MaMauSac ?: 0) in mauSacOptions.indices) sanPham?.MaMauSac ?: 0 else 0
        )
    }
    var mauSacExpanded by remember { mutableStateOf(false) }

    var selectedTrangThai by remember {
        mutableStateOf(trangThaiOptions.find { it.second == sanPham?.TrangThai } ?: trangThaiOptions.first())
    }
    var trangThaiExpanded by remember { mutableStateOf(false) }

    var errorText by remember { mutableStateOf<String?>(null) }

    // Khi đổi loại sản phẩm, reset các field về mặc định
    LaunchedEffect(selectedLoai) {
        selectedCPU = cpuOptions.first()
        selectedRAM = ramOptions.first()
        selectedCard = cardOptions.first()
        selectedSSD = ssdOptions.first()
        selectedManHinh = manHinhOptions.first()
        selectedMauSac = 0
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (tenSanPham.isBlank()) { errorText = "Tên sản phẩm không được để trống"; return@Button }
                val giaInt = gia.toIntOrNull(); if (giaInt == null || giaInt <= 0) { errorText = "Giá phải là số > 0"; return@Button }
                val soLuongInt = soLuong.toIntOrNull(); if (soLuongInt == null || soLuongInt < 0) { errorText = "Số lượng không hợp lệ"; return@Button }
                if (moTa.isBlank()) { errorText = "Mô tả không được để trống"; return@Button }
                if (imageUriState.value == null && sanPham?.HinhAnh.isNullOrBlank()) {
                    errorText = "Vui lòng chọn ảnh"; return@Button
                }
                errorText = null
                onSave(
                    SanPham(
                            MaSanPham = sanPham?.MaSanPham ?: 0,
                            TenSanPham = tenSanPham,
                            MaLoaiSanPham = selectedLoai.first,
                            CPU = selectedCPU,
                            RAM = selectedRAM,
                            CardManHinh = selectedCard,
                            SSD = selectedSSD,
                            ManHinh = selectedManHinh,
                            MaMauSac = selectedMauSac,
                            Gia = giaInt,
                            SoLuong = soLuongInt,
                            MoTa = moTa,
                            HinhAnh = hinhAnh,
                            TrangThai = selectedTrangThai.second,
                            loaiPhuKien = sanPham?.loaiPhuKien,
                            DaXoa = sanPham?.DaXoa ?: 0,
                            NgayTao = sanPham?.NgayTao ?: "",
                            NgayCapNhat = sanPham?.NgayCapNhat ?: ""
                        ),
                    imageUriState.value
                )
            }) {
                Text(if (sanPham == null) "Thêm mới" else "Cập nhật")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Hủy") }
        },
        title = { Text(if (sanPham == null) "Thêm sản phẩm" else "Sửa sản phẩm") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (errorText != null) Text(errorText!!, color = MaterialTheme.colorScheme.error)
                OutlinedTextField(
                    value = tenSanPham, onValueChange = { tenSanPham = it },
                    label = { Text("Tên sản phẩm *") }, singleLine = true
                )
                OutlinedTextField(
                    value = gia, onValueChange = { gia = it },
                    label = { Text("Giá (VND) *") }, singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = soLuong, onValueChange = { soLuong = it },
                    label = { Text("Số lượng *") }, singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = moTa, onValueChange = { moTa = it },
                    label = { Text("Mô tả *") }
                )
                // Chọn ảnh
                Button(onClick = {
                    imagePickerLauncher.launch("image/*")
                }) {
                    Text("Chọn ảnh từ thiết bị")
                }

                imageUriState.value?.let { uri ->
                    Spacer(Modifier.height(8.dp))
                    AsyncImage(
                        model = uri,
                        contentDescription = "Ảnh sản phẩm",
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                    )
                } ?: sanPham?.HinhAnh?.takeIf { it.isNotBlank() }?.let {
                    Spacer(Modifier.height(8.dp))
                    AsyncImage(
                        model = it,
                        contentDescription = "Ảnh cũ",
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                    )
                }
                // Loại sản phẩm
                ExposedDropdownMenuBox(
                    expanded = loaiExpanded,
                    onExpandedChange = { loaiExpanded = !loaiExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedLoai.second,
                        onValueChange = {},
                        modifier = Modifier.menuAnchor(),
                        label = { Text("Loại sản phẩm *") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(loaiExpanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = loaiExpanded,
                        onDismissRequest = { loaiExpanded = false }
                    ) {
                        maloaiOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option.second) },
                                onClick = {
                                    selectedLoai = option
                                    loaiExpanded = false
                                }
                            )
                        }
                    }
                }
                // CPU
                ExposedDropdownMenuBox(
                    expanded = cpuExpanded,
                    onExpandedChange = { cpuExpanded = !cpuExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCPU,
                        onValueChange = {},
                        modifier = Modifier.menuAnchor(),
                        label = { Text("CPU *") }, readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(cpuExpanded) }

                    )
                    ExposedDropdownMenu(
                        expanded = cpuExpanded,
                        onDismissRequest = { cpuExpanded = false }
                    ) {
                        cpuOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedCPU = option
                                    cpuExpanded = false
                                }
                            )
                        }
                    }
                }
                // RAM
                ExposedDropdownMenuBox(
                    expanded = ramExpanded,
                    onExpandedChange = { ramExpanded = !ramExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedRAM,
                        onValueChange = {},
                        modifier = Modifier.menuAnchor(),
                        label = { Text("RAM *") }, readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(ramExpanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = ramExpanded,
                        onDismissRequest = { ramExpanded = false }
                    ) {
                        ramOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedRAM = option
                                    ramExpanded = false
                                }
                            )
                        }
                    }
                }
                // Card màn hình
                ExposedDropdownMenuBox(
                    expanded = cardExpanded,
                    onExpandedChange = { cardExpanded = !cardExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCard,
                        onValueChange = {},
                        modifier = Modifier.menuAnchor(),
                        label = { Text("Card màn hình *") }, readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(cardExpanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = cardExpanded,
                        onDismissRequest = { cardExpanded = false }
                    ) {
                        cardOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedCard = option
                                    cardExpanded = false
                                }
                            )
                        }
                    }
                }
                // SSD
                ExposedDropdownMenuBox(
                    expanded = ssdExpanded,
                    onExpandedChange = { ssdExpanded = !ssdExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedSSD,
                        onValueChange = {},
                        modifier = Modifier.menuAnchor(),
                        label = { Text("SSD *") }, readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(ssdExpanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = ssdExpanded,
                        onDismissRequest = { ssdExpanded = false }
                    ) {
                        ssdOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedSSD = option
                                    ssdExpanded = false
                                }
                            )
                        }
                    }
                }
                // Màn hình
                ExposedDropdownMenuBox(
                    expanded = manHinhExpanded,
                    onExpandedChange = { manHinhExpanded = !manHinhExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedManHinh,
                        onValueChange = {},
                        modifier = Modifier.menuAnchor(),
                        label = { Text("Màn hình *") }, readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(manHinhExpanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = manHinhExpanded,
                        onDismissRequest = { manHinhExpanded = false }
                    ) {
                        manHinhOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedManHinh = option
                                    manHinhExpanded = false
                                }
                            )
                        }
                    }
                }
                // Màu sắc
                ExposedDropdownMenuBox(
                    expanded = mauSacExpanded,
                    onExpandedChange = { mauSacExpanded = !mauSacExpanded }
                ) {
                    OutlinedTextField(
                        value = mauSacOptions.find { it.first == selectedMauSac }?.second ?: "Chưa chọn",
                        onValueChange = {},
                        modifier = Modifier.menuAnchor(),
                        label = { Text("Màu sắc *") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = mauSacExpanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = mauSacExpanded,
                        onDismissRequest = { mauSacExpanded = false }
                    ) {
                        mauSacOptions.forEach { (maMau, tenMau) ->
                            DropdownMenuItem(
                                text = { Text(tenMau) },
                                onClick = {
                                    selectedMauSac = maMau
                                    mauSacExpanded = false
                                }
                            )
                        }
                    }
                }

                // Trạng thái
                ExposedDropdownMenuBox(
                    expanded = trangThaiExpanded,
                    onExpandedChange = { trangThaiExpanded = !trangThaiExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedTrangThai.first,
                        onValueChange = {},
                        modifier = Modifier.menuAnchor(),
                        label = { Text("Trạng thái *") }, readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(trangThaiExpanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = trangThaiExpanded,
                        onDismissRequest = { trangThaiExpanded = false }
                    ) {
                        trangThaiOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option.first) },
                                onClick = {
                                    selectedTrangThai = option
                                    trangThaiExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}