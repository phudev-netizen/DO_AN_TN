<?php
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once '../../config/database.php';
include_once '../../model/yeuthich.php';

$database = new database();
$conn = $database->Connect();

$yeuthich = new SanPhamYeuThich($conn);

// Lấy dữ liệu JSON gửi lên
$data = json_decode(file_get_contents("php://input"), true);

// Ghi log để debug (xem app gửi gì lên)
//file_put_contents("debug.log", print_r($data, true));

$MaKhachHang = isset($data['MaKhachHang']) ? intval($data['MaKhachHang']) : null;
$MaSanPham = isset($data['MaSanPham']) ? intval($data['MaSanPham']) : null;

if ($MaKhachHang && $MaSanPham) {
    $yeuthich->MaKhachHang = $MaKhachHang;
    $yeuthich->MaSanPham = $MaSanPham;
    $result = $yeuthich->create();
    echo json_encode($result);
} else {
    echo json_encode([
        "success" => false,
        "message" => "Thiếu dữ liệu MaKhachHang hoặc MaSanPham."
    ]);
}
?>