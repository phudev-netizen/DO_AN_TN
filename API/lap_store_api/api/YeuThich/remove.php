<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: DELETE");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../../config/database.php';
include_once '../../objects/SanPhamYeuThich.php';

$database = new Database();
$db = $database->getConnection();

$yeuthich = new SanPhamYeuThich($db);
$data = json_decode(file_get_contents("php://input"));

if (!empty($data->MaKhachHang) && !empty($data->MaSanPham)) {
    $yeuthich->MaKhachHang = $data->MaKhachHang;
    $yeuthich->MaSanPham = $data->MaSanPham;

    if ($yeuthich->delete()) {
        http_response_code(200);
        echo json_encode(["message" => "Đã xoá sản phẩm khỏi danh sách yêu thích."]);
    } else {
        http_response_code(503);
        echo json_encode(["message" => "Không thể xoá sản phẩm yêu thích."]);
    }
} else {
    http_response_code(400);
    echo json_encode(["message" => "Thiếu thông tin cần thiết."]);
}
?>
