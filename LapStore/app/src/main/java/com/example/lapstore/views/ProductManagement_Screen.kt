import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
                    onSave = { sp, imageUris ->
                        if (imageUris.isNotEmpty()) {
                            viewModel.uploadImage(context, imageUris[0]) { imageUrl ->
                                if (imageUrl != null) {
                                    val updated = sp.copy(HinhAnh = imageUrl)
                                    if (updated.MaSanPham == 0) {
                                        viewModel.createSanPham(updated) { _, _ -> showForm = false }
                                    } else {
                                        viewModel.updateSanPham(updated) { _, _ -> showForm = false }
                                    }
                                }
                            }
                        } else {
                            if (sp.MaSanPham == 0) viewModel.createSanPham(sp) { _, _ -> showForm = false }
                            else viewModel.updateSanPham(sp) { _, _ -> showForm = false }
                        }
                    },
                    sanPhamViewModel = viewModel
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
    onSave: (SanPham, List<Uri>) -> Unit, // ✅ Sửa kiểu List<Uri>
    sanPhamViewModel: SanPhamViewModel
) {
    val context = LocalContext.current

    // 🔁 Chọn nhiều ảnh
    val imageUris = remember { mutableStateListOf<Uri>() }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        imageUris.clear()
        imageUris.addAll(uris)
    }

    // Các state khác như title, price, dropdown...
    var tenSanPham by remember { mutableStateOf(sanPham?.TenSanPham ?: "") }
    var gia by remember { mutableStateOf(sanPham?.Gia?.toString() ?: "") }
    var soLuong by remember { mutableStateOf(sanPham?.SoLuong?.toString() ?: "") }
    var moTa by remember { mutableStateOf(sanPham?.MoTa ?: "") }
    var hinhAnh by remember { mutableStateOf(sanPham?.HinhAnh ?: "") }

    // Dropdown lấy từ ViewModel
    val cpuOptions = sanPhamViewModel.getDistinctCpuList()
    val ramOptions = sanPhamViewModel.getDistinctRamList()
    val cardOptions = sanPhamViewModel.getDistinctCardList()
    val ssdOptions = sanPhamViewModel.getDistinctSsdList()
    val manHinhOptions = sanPhamViewModel.getDistinctManHinhList()
    val maloaiOptions = sanPhamViewModel.getLoaiSanPhamList()
    val mauSacOptions = listOf(1 to "Đen", 2 to "Trắng", 3 to "Xám")
    val trangThaiOptions = listOf("Kinh doanh" to 1, "Ngừng bán" to 0)

    // Các state dropdown
    var selectedLoai by remember { mutableStateOf(maloaiOptions.find { it.first == sanPham?.MaLoaiSanPham } ?: maloaiOptions.first()) }
    var selectedCPU by remember { mutableStateOf(cpuOptions.firstOrNull().orEmpty()) }
    var selectedRAM by remember { mutableStateOf(ramOptions.firstOrNull().orEmpty()) }
    var selectedCard by remember { mutableStateOf(cardOptions.firstOrNull().orEmpty()) }
    var selectedSSD by remember { mutableStateOf(ssdOptions.firstOrNull().orEmpty()) }
    var selectedManHinh by remember { mutableStateOf(manHinhOptions.firstOrNull().orEmpty()) }
    var selectedMauSac by remember { mutableStateOf(sanPham?.MaMauSac ?: mauSacOptions.first().first) }
    var selectedTrangThai by remember { mutableStateOf(trangThaiOptions.first()) }

    var errorText by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                // ✅ Validate dữ liệu
                if (tenSanPham.isBlank()) { errorText = "Tên sản phẩm không được để trống"; return@Button }
                val giaInt = gia.toIntOrNull(); if (giaInt == null || giaInt <= 0) { errorText = "Giá phải là số > 0"; return@Button }
                val soLuongInt = soLuong.toIntOrNull(); if (soLuongInt == null || soLuongInt < 0) { errorText = "Số lượng không hợp lệ"; return@Button }
                if (moTa.isBlank()) { errorText = "Mô tả không được để trống"; return@Button }
                if (imageUris.isEmpty() && sanPham?.HinhAnh.isNullOrBlank()) {
                    errorText = "Vui lòng chọn ít nhất 1 ảnh"; return@Button
                }

                errorText = null
                // ✅ Gọi onSave truyền SanPham mới và danh sách ảnh
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
                        HinhAnh = "", // sẽ được cập nhật từ ViewModel khi upload
                        TrangThai = selectedTrangThai.second,
                        loaiPhuKien = sanPham?.loaiPhuKien,
                        DaXoa = sanPham?.DaXoa ?: 0,
                        NgayTao = sanPham?.NgayTao ?: "",
                        NgayCapNhat = sanPham?.NgayCapNhat ?: ""
                    ),
                    imageUris.toList() // ✅ truyền list ảnh
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
                    .heightIn(max = 600.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                errorText?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                // Các TextField
                OutlinedTextField(tenSanPham, onValueChange = { tenSanPham = it }, label = { Text("Tên sản phẩm *") })
                OutlinedTextField(gia, onValueChange = { gia = it }, label = { Text("Giá (VND) *") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                OutlinedTextField(soLuong, onValueChange = { soLuong = it }, label = { Text("Số lượng *") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                OutlinedTextField(moTa, onValueChange = { moTa = it }, label = { Text("Mô tả *") })

                // Nút chọn nhiều ảnh
                Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Text("Chọn nhiều ảnh")
                }

                // Hiển thị ảnh đã chọn
                if (imageUris.isNotEmpty()) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(imageUris) { uri ->
                            AsyncImage(
                                model = uri,
                                contentDescription = "Ảnh đã chọn",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                } else {
                    sanPham?.HinhAnh?.takeIf { it.isNotBlank() }?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = "Ảnh cũ",
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Gợi ý dropdown - bạn dùng CustomDropdown của bạn
                CustomDropdown("Loại sản phẩm", selectedLoai.second, maloaiOptions.map { it.second }) {
                    selectedLoai = maloaiOptions[it]
                }
                CustomDropdown("CPU", selectedCPU, cpuOptions) { selectedCPU = cpuOptions[it] }
                CustomDropdown("RAM", selectedRAM, ramOptions) { selectedRAM = ramOptions[it] }
                CustomDropdown("Card", selectedCard, cardOptions) { selectedCard = cardOptions[it] }
                CustomDropdown("SSD", selectedSSD, ssdOptions) { selectedSSD = ssdOptions[it] }
                CustomDropdown("Màn hình", selectedManHinh, manHinhOptions) { selectedManHinh = manHinhOptions[it] }
                CustomDropdown("Màu sắc", mauSacOptions.firstOrNull { it.first == selectedMauSac }?.second ?: "", mauSacOptions.map { it.second }) {
                    selectedMauSac = mauSacOptions[it].first
                }
                CustomDropdown("Trạng thái", selectedTrangThai.first, trangThaiOptions.map { it.first }) {
                    selectedTrangThai = trangThaiOptions[it]
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown(label: String, value: String, options: List<String>, onSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    onSelected(index)
                    expanded = false
                })
            }
        }
    }
}
