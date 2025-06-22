<?php
// Cho phép truy cập từ mọi nguồn (CORS)
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json; charset=UTF-8');
header('Access-Control-Allow-Methods: POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

// Trả về sớm nếu là preflight request (OPTIONS)
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit();
}

include_once '../../config/database.php';
include_once '../../model/YeuThich.php';

// Kết nối CSDL
$dbClass = new database();
$db = $dbClass->Connect();

// Lấy dữ liệu từ POST
$maKhachHang = $_POST['MaKhachHang'] ?? null;
$maSanPham = $_POST['MaSanPham'] ?? null;

// Log tạm input (debug)
// file_put_contents("debug_input.txt", json_encode($_POST));

// Kiểm tra dữ liệu đầu vào
if (!$maKhachHang || !$maSanPham || !is_numeric($maKhachHang) || !is_numeric($maSanPham)) {
    http_response_code(400);
    echo json_encode([
        'success' => false,
        'message' => 'Thiếu hoặc sai tham số'
    ]);
    exit;
}

// Gọi model để thêm
$model = new YeuThich($db);
$result = $model->them($maKhachHang, $maSanPham);

// Trả phản hồi đúng theo kết quả
if (isset($result['success'])) {
    http_response_code($result['success'] ? 200 : 400);
    echo json_encode($result);
} else {
    // Trường hợp model trả về dữ liệu không đúng cấu trúc
    http_response_code(500);
    echo json_encode([
        'success' => false,
        'message' => 'Lỗi không xác định từ server'
    ]);
}
exit;
?>