<?php
// Cho phép gọi từ app
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: GET');
header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once '../../config/database.php';
include_once '../../model/KhuyenMai.php';

$db = (new Database())->connect();
$khuyenMai = new KhuyenMai($db);

// Lấy MaSanPham từ query string (có thể là 0)
$maSanPham = isset($_GET['MaSanPham']) ? intval($_GET['MaSanPham']) : null;

try {
    if ($maSanPham !== null) {
        // Ưu tiên lấy khuyến mãi riêng cho sản phẩm
        $stmt = $khuyenMai->getByProductId($maSanPham);
        $data = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Nếu không có, fallback về khuyến mãi toàn bộ (MaSanPham = 0)
        if (empty($data) && $maSanPham !== 0) {
            $stmt = $khuyenMai->getByProductId(0);
            $data = $stmt->fetchAll(PDO::FETCH_ASSOC);
        }
    } else {
        // Nếu không truyền gì, lấy tất cả khuyến mãi còn hiệu lực
        $stmt = $khuyenMai->getAll();
        $data = $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    echo json_encode($data);
} catch (Exception $e) {
    echo json_encode([
        "error" => true,
        "message" => $e->getMessage()
    ]);
}
?>
