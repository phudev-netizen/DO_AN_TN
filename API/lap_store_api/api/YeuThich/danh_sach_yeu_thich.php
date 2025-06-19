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

$dbClass = new database();
$db = $dbClass->Connect();

if (!$db) {
    echo json_encode(['success' => false, 'message' => 'Không thể kết nối database']);
    exit;
}

$maKhachHang = isset($_GET['MaKhachHang']) ? intval($_GET['MaKhachHang']) : null;

if (!$maKhachHang) {
    echo json_encode([
        'success' => false,
        'message' => 'Thiếu MaKhachHang'
    ]);
    exit;
}

$model = new YeuThich($db);
$data = $model->layDanhSach($maKhachHang);

echo json_encode([
    'success' => true,
    'data' => $data
]);
exit;
?>