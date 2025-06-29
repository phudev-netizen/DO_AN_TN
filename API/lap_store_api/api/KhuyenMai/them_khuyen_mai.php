<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With");

include_once '../../config/database.php';
include_once '../../model/KhuyenMai.php';

$db = (new Database())->connect();
$km = new KhuyenMai($db);

$data = json_decode(file_get_contents("php://input"), true);

// Kiểm tra dữ liệu đầu vào
if (
    isset($data['MaSanPham']) &&
    isset($data['TenKhuyenMai']) &&
    isset($data['PhanTramGiam']) &&
    isset($data['NgayBatDau']) &&
    isset($data['NgayKetThuc'])
) {
    if ($km->insert($data)) {
        echo json_encode(["success" => true]);
    } else {
        http_response_code(500);
        echo json_encode(["error" => "Insert failed"]);
    }
} else {
    http_response_code(400);
    echo json_encode(["error" => "Thiếu dữ liệu"]);
}
?>