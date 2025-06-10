<?php
// header("Access-Control-Allow-Origin: *");
// header("Content-Type: application/json; charset=UTF-8");
// header("Access-Control-Allow-Methods: POST, OPTIONS");
// header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
  header('Access-Control-Allow-Origin:*');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: POST');
    header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With');
    
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit();
}

include_once '../../config/database.php';
include_once '../../model/yeuthich.php';

 $database = new database();
    $conn = $database->Connect(); 

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