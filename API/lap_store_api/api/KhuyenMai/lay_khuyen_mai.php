<?php
// Cho phép gọi từ app
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

// Dùng GET chứ không phải POST cho API lấy dữ liệu
header('Access-Control-Allow-Methods: GET');
header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once '../../config/database.php';
include_once '../../model/KhuyenMai.php';

$db = (new Database())->connect();
$khuyenMai = new KhuyenMai($db);

$maSanPham = $_GET['MaSanPham'] ?? null;

try {
    if ($maSanPham) {
        // Lấy khuyến mãi theo sản phẩm
        $result = $khuyenMai->getByProductId($maSanPham);
    } else {
        // Lấy tất cả khuyến mãi còn hiệu lực
        $result = $khuyenMai->getAll();
    }

    $data = $result->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($data);
} catch (Exception $e) {
    echo json_encode([
        "error" => true,
        "message" => $e->getMessage()
    ]);
}
?>
