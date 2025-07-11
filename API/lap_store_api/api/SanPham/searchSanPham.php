<?php
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json; charset=UTF-8');

include_once('../../config/database.php');
include_once('../../model/SanPham.php');

// Khởi tạo kết nối và model
$database = new Database();
$conn     = $database->Connect();
$sanpham  = new SanPham($conn);

// Lấy các tham số tìm kiếm (nếu không truyền thì trả về chuỗi rỗng hoặc giá mặc định)
$ten    = isset($_GET['ten'])    ? trim($_GET['ten'])    : '';
$cpu    = isset($_GET['cpu'])    ? trim($_GET['cpu'])    : '';
$ram    = isset($_GET['ram'])    ? trim($_GET['ram'])    : '';
$card   = isset($_GET['card'])   ? trim($_GET['card'])   : '';
$giaTu  = isset($_GET['giatu'])  ? (int)$_GET['giatu']   : 0;
$giaDen = isset($_GET['giaden']) ? (int)$_GET['giaden']  : PHP_INT_MAX;

// Gọi hàm tìm kiếm nhiều tiêu chí
$stmt = $sanpham->SearchMultiCriteria($ten, $cpu, $ram, $card, $giaTu, $giaDen);

// Xây dựng mảng kết quả
if ($stmt->rowCount() > 0) {
    $items = [];
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        $items[] = [
            'MaSanPham'   => $row['MaSanPham'],
            'TenSanPham'  => $row['TenSanPham'],
            'CPU'         => $row['CPU'],
            'RAM'         => $row['RAM'],
            'CardManHinh' => $row['CardManHinh'],
            'SSD'         => $row['SSD'],
            'ManHinh'     => $row['ManHinh'],
            'Gia'         => $row['Gia'],
            'HinhAnh'     => $row['DuongDan'],
            'TrangThai'   => $row['TrangThai'],
        ];
    }
    echo json_encode(['sanpham' => $items], JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);
} else {
    echo json_encode(['message' => 'Không tìm thấy sản phẩm nào.'], JSON_UNESCAPED_UNICODE);
}
?>