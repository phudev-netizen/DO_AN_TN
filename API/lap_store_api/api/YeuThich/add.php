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

// Nhận dữ liệu từ JSON hoặc POST (hỗ trợ cả fetch/axios và form)
$data = json_decode(file_get_contents("php://input"), true);

// Ưu tiên lấy từ JSON
$MaKhachHang = isset($data['MaKhachHang']) ? intval($data['MaKhachHang']) : (isset($_POST['MaKhachHang']) ? intval($_POST['MaKhachHang']) : null);
$MaSanPham = isset($data['MaSanPham']) ? intval($data['MaSanPham']) : (isset($_POST['MaSanPham']) ? intval($_POST['MaSanPham']) : null);

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