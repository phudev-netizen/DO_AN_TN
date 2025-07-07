<?php
header('Access-Control-Allow-Origin:*');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: GET');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once('../../config/database.php');
include_once('../../model/ThongKeModel.php');

$headers = getallheaders();
$role = $headers["Role"] ?? "";

if ($role !== "admin") {
    echo json_encode([
        "success" => false,
        "message" => "Bạn không có quyền truy cập thống kê."
    ]);
    exit;
}

$nam = isset($_GET['nam']) ? intval($_GET['nam']) : date('Y');

$database = new database();
$conn = $database->Connect();
$thongke = new ThongKeModel($conn);

try {
    $data = [
        "total_products" => $thongke->getTotalProducts(),
        "total_orders" => $thongke->getTotalOrders(),
        "total_revenue" => $thongke->getTotalRevenueTheoNam($nam),
        "total_users" => $thongke->getTotalUsers(),
        "monthly_revenue" => $thongke->getMonthlyRevenueTheoNam($nam)
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
