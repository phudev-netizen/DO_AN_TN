import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lapstore.models.KhuyenMai
import com.example.lapstore.models.SanPham
import com.example.lapstore.viewmodels.KhuyenMaiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminKhuyenMaiScreen(
    viewModel: KhuyenMaiViewModel = viewModel(),
    danhSachSanPham: List<SanPham>
) {
    val danhSachKhuyenMai = viewModel.khuyenMaiList
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedKM by remember { mutableStateOf<KhuyenMai?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchTatCaKhuyenMai()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quản lý khuyến mãi") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Thêm khuyến mãi")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(danhSachKhuyenMai) { km ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🎁 ${km.TenKhuyenMai}", style = MaterialTheme.typography.titleMedium)
                        Text("🛒 Sản phẩm: ${danhSachSanPham.firstOrNull { it.MaSanPham == km.MaSanPham }?.TenSanPham ?: "Không rõ"}")
                        Text("🔻 Giảm giá: ${km.PhanTramGiam}%")
                        Text("🕒 Thời gian: ${km.NgayBatDau} → ${km.NgayKetThuc}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            OutlinedButton(
                                onClick = { selectedKM = km },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null)
                                Spacer(Modifier.width(4.dp))
                                Text("Sửa")
                            }
                            Spacer(Modifier.width(8.dp))
                            OutlinedButton(
                                onClick = { viewModel.deleteKhuyenMai(km.MaKhuyenMai) {} },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                                Spacer(Modifier.width(4.dp))
                                Text("Xóa")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog || selectedKM != null) {
        KhuyenMaiFormDialog(
            initial = selectedKM,
            danhSachSanPham = danhSachSanPham,
            onDismiss = {
                showAddDialog = false
                selectedKM = null
            },
            onSubmit = { km ->
                if (selectedKM == null) {
                    viewModel.addKhuyenMai(km) { showAddDialog = false }
                } else {
                    viewModel.updateKhuyenMai(km) { selectedKM = null }
                }
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KhuyenMaiFormDialog(
    initial: KhuyenMai?,
    danhSachSanPham: List<SanPham>,
    onDismiss: () -> Unit,
    onSubmit: (KhuyenMai) -> Unit
) {
    var tenKhuyenMai by remember { mutableStateOf(initial?.TenKhuyenMai ?: "") }
    var phanTramGiam by remember { mutableStateOf(initial?.PhanTramGiam?.toString() ?: "") }
    var ngayBatDau by remember { mutableStateOf(initial?.NgayBatDau ?: "") }
    var ngayKetThuc by remember { mutableStateOf(initial?.NgayKetThuc ?: "") }
    var expanded by remember { mutableStateOf(false) }
    var selectedSanPham by remember {
        mutableStateOf(
            danhSachSanPham.firstOrNull { it.MaSanPham == initial?.MaSanPham } ?: danhSachSanPham.firstOrNull()
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initial == null) "➕ Thêm khuyến mãi" else "✏️ Sửa khuyến mãi",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedSanPham?.TenSanPham ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Sản phẩm") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        danhSachSanPham.forEach { sp ->
                            DropdownMenuItem(
                                text = { Text(sp.TenSanPham) },
                                onClick = {
                                    selectedSanPham = sp
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = tenKhuyenMai,
                    onValueChange = { tenKhuyenMai = it },
                    label = { Text("Tên khuyến mãi") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = phanTramGiam,
                    onValueChange = { phanTramGiam = it },
                    label = { Text("Phần trăm giảm") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = ngayBatDau,
                    onValueChange = { ngayBatDau = it },
                    label = { Text("Ngày bắt đầu") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = ngayKetThuc,
                    onValueChange = { ngayKetThuc = it },
                    label = { Text("Ngày kết thúc") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            ElevatedButton(onClick = {
                val km = KhuyenMai(
                    MaKhuyenMai = initial?.MaKhuyenMai ?: 0,
                    MaSanPham = selectedSanPham?.MaSanPham ?: 0,
                    TenKhuyenMai = tenKhuyenMai,
                    PhanTramGiam = phanTramGiam.toIntOrNull() ?: 0,
                    NgayBatDau = ngayBatDau,
                    NgayKetThuc = ngayKetThuc
                )
                onSubmit(km)
            }) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}
