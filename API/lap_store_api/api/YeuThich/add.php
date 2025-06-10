<?php
// header("Access-Control-Allow-Origin: *");
// header("Content-Type: application/json; charset=UTF-8");
// header("Access-Control-Allow-Methods: POST");
// header("Access-Control-Max-Age: 3600");
// header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    header('Access-Control-Allow-Origin:*');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: POST');
    header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With');
    
include_once '../../config/database.php';
include_once '../../model/yeuthich.php';

 $database = new database();
    $conn = $database->Connect(); 

$yeuthich = new SanPhamYeuThich($db);
$data = json_decode(file_get_contents("php://input"));

if (!empty($data->MaKhachHang) && !empty($data->MaSanPham)) {
    $yeuthich->MaKhachHang = $data->MaKhachHang;
    $yeuthich->MaSanPham = $data->MaSanPham;

    $result = $yeuthich->create();
    if ($result["success"]) {
        http_response_code(201);
    } else {
        http_response_code(200);
    }
    echo json_encode(["message" => $result["message"]]);
} else {
    http_response_code(400);
    echo json_encode(["message" => "Không thể thêm sản phẩm yêu thích. Thiếu dữ liệu đầu vào."]);
}
?>