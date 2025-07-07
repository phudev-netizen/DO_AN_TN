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

$database = new database();
$conn = $database->Connect();
$thongke = new ThongKeModel($conn);

try {
    $response = [
        "success" => true,
        "topSelling" => $thongke->getTopSellingProducts(5),
        "slowSelling" => $thongke->getSlowSellingProducts(5),
        "totalQtySold" => $thongke->getTotalQuantitySold()
    ];

    echo json_encode($response);
} catch (Exception $e) {
    echo json_encode([
        "success" => false,
        "message" => "Lỗi: " . $e->getMessage()
    ]);
}
?>
