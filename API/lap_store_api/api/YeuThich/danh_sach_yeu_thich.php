<?php
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json; charset=UTF-8');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit();
}

include_once '../../config/database.php';
include_once '../../model/YeuThich.php';

// Kết nối database
$dbClass = new database();
$db = $dbClass->Connect();

if (!$db) {
    echo json_encode([
        'success' => false,
        'message' => 'Không thể kết nối database'
    ]);
    exit;
}

// Lấy mã khách hàng từ query
$maKhachHang = isset($_GET['MaKhachHang']) ? intval($_GET['MaKhachHang']) : null;

if (!$maKhachHang || $maKhachHang <= 0) {
    echo json_encode([
        'success' => false,
        'message' => 'Thiếu hoặc sai mã khách hàng'
    ]);
    exit;
}

$model = new YeuThich($db);
$data = $model->layDanhSach($maKhachHang);

if ($data && is_array($data)) {
    echo json_encode([
        'success' => true,
        'data' => $data,
        'message' => 'Lấy danh sách yêu thích thành công'
    ]);
} else {
    echo json_encode([
        'success' => true,
        'data' => [],
        'message' => 'Không có sản phẩm yêu thích nào'
    ]);
}
exit;
?>