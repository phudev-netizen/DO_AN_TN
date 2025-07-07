<?php
// Xóa phụ kiện
   header('Access-Control-Allow-Origin:*');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: POST');
    header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With');
    
    include_once('../../config/database.php');
    include_once('../../model/phukien.php');
    
$database = new database();
$conn = $database->Connect();
$phukien = new PhuKien($conn);

$data = json_decode(file_get_contents("php://input"));
$phukien->MaSanPham = $_GET['id'] ?? ($data->MaSanPham ?? null);

if ($phukien->DeletePhuKien()) {
    echo json_encode([
        "success" => true,
        "message" => "Xóa phụ kiện thành công"
    ]);
} else {
    echo json_encode([
        "success" => false,
        "message" => "Xóa phụ kiện thất bại"
    ]);
}
?>