<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../../config/database.php';
include_once '../../objects/SanPhamYeuThich.php';

$database = new Database();
$db = $database->getConnection();

$yeuthich = new SanPhamYeuThich($db);
$data = json_decode(file_get_contents("php://input"));

if (!empty($data->MaKhachHang) && !empty($data->MaSanPham)) {
    $exists = $yeuthich->check($data->MaSanPham, $data->MaKhachHang);

    echo json_encode(["isFavorite" => $exists]);
} else {
    http_response_code(400);
    echo json_encode(["message" => "Thiếu dữ liệu đầu vào."]);
}
?>
