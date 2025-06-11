<?php
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST, OPTIONS');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit();
}

include_once '../../config/database.php';
include_once '../../model/yeuthich.php';

$database = new database();
$conn = $database->Connect();

$yeuthich = new SanPhamYeuThich($conn);

// Nhận dữ liệu từ JSON hoặc POST (hỗ trợ fetch/axios và form)
$data = json_decode(file_get_contents("php://input"), true);

$MaKhachHang = isset($data['MaKhachHang']) ? intval($data['MaKhachHang']) : (isset($_POST['MaKhachHang']) ? intval($_POST['MaKhachHang']) : null);
$MaSanPham = isset($data['MaSanPham']) ? intval($data['MaSanPham']) : (isset($_POST['MaSanPham']) ? intval($_POST['MaSanPham']) : null);

if ($MaKhachHang && $MaSanPham) {
    $exists = $yeuthich->check($MaSanPham, $MaKhachHang);
    echo json_encode(["isFavorite" => $exists]);
} else {
    echo json_encode([
        "isFavorite" => false,
        "message" => "Thiếu dữ liệu MaKhachHang hoặc MaSanPham."
    ]);
}
?>