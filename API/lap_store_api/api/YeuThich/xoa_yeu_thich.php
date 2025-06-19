<?php
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST, OPTIONS');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once '../../config/database.php';
include_once '../../model/YeuThich.php';

// KHỞI TẠO KẾT NỐI DB
$dbClass = new database();
$db = $dbClass->Connect();

$maKhachHang = $_POST['MaKhachHang'] ?? null;
$maSanPham = $_POST['MaSanPham'] ?? null;

if (!$maKhachHang || !$maSanPham) {
    echo json_encode(['success' => false, 'message' => 'Thiếu tham số']);
    exit;
}

$model = new YeuThich($db);
$result = $model->xoa($maKhachHang, $maSanPham);
echo json_encode($result);
exit;
?>