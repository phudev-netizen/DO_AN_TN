<?php

// Set headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// Include database and model
include_once '../../config/database.php';
include_once '../../objects/SanPhamYeuThich.php';

// Instantiate database and product object
$database = new Database();
$db = $database->getConnection();

// Initialize object
$yeuthich = new SanPhamYeuThich($db);

// Get posted data
$data = json_decode(file_get_contents("php://input"));

// Validate input
if (
    !empty($data->MaKhachHang) &&
    !empty($data->MaSanPham)
) {
    // Set properties
    $yeuthich->MaKhachHang = $data->MaKhachHang;
    $yeuthich->MaSanPham = $data->MaSanPham;

    // Check if record already exists
    if ($yeuthich->check($data->MaSanPham, $data->MaKhachHang)) {
        http_response_code(200);
        echo json_encode(array("message" => "Sản phẩm đã có trong danh sách yêu thích."));
    } else {
        // Attempt to create
        if ($yeuthich->create()) {
            http_response_code(201);
            echo json_encode(array("message" => "Sản phẩm đã được thêm vào danh sách yêu thích."));
        } else {
            http_response_code(503);
            echo json_encode(array("message" => "Không thể thêm sản phẩm yêu thích."));
        }
    }
} else {
    // Missing data
    http_response_code(400);
    echo json_encode(array("message" => "Không thể thêm sản phẩm yêu thích. Thiếu dữ liệu đầu vào."));
}
?>
