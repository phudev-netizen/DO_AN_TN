<?php
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json; charset=UTF-8');
header('Access-Control-Allow-Methods: GET');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

// Kết nối CSDL
include_once('../../config/database.php');
include_once('../../model/ThongKeModel.php');

// Tạo đối tượng Database
$database = new Database();
$conn = $database->Connect();

// Tạo đối tượng ThongKeModel với $conn
$thongke = new ThongKeModel($conn);

try {
    $orders = $thongke->getOrders(); // Hàm này bạn phải có trong ThongKeModel
    echo json_encode([
        "success" => true,
        "data" => $orders
    ]);
} catch (Exception $e) {
    echo json_encode([
        "success" => false,
        "message" => "Lỗi: " . $e->getMessage()
    ]);
}
?>
