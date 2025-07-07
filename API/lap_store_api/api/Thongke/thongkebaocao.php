<?php
header('Access-Control-Allow-Origin:*');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

// ✅ Gọi kết nối PDO
include_once('../../config/database.php');
include_once('../../model/ThongKeModel.php');


// Giả sử bạn lấy role từ header gửi lên
$headers = getallheaders();
$role = $headers["Role"] ?? "";

if ($role !== "admin") {
    echo json_encode([
        "success" => false,
        "message" => "Bạn không có quyền truy cập thống kê."
    ]);
    exit;
}

$database = new database();
$conn = $database->Connect();
$thongke = new ThongKeModel($conn);

try {
    $data = [
        "total_products" => $thongke->getTotalProducts(),
        "total_orders" => $thongke->getTotalOrders(),
        "total_revenue" => $thongke->getTotalRevenue(),
        "total_users" => $thongke->getTotalUsers(),
        "monthly_revenue" => $thongke->getMonthlyRevenue()
    ];

    echo json_encode([
        "success" => true,
        "data" => $data
    ]);
} catch (Exception $e) {
    echo json_encode([
        "success" => false,
        "message" => "Lỗi: " . $e->getMessage()
    ]);
}
?>

