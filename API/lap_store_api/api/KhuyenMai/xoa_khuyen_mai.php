<?php
// Cho phép gọi từ frontend (React/Android v.v.)
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
header("Access-Control-Allow-Methods: DELETE");
header("Access-Control-Allow-Headers: Content-Type, Authorization");

// Import kết nối và model
include_once '../../config/database.php';
include_once '../../model/KhuyenMai.php';

// Tạo kết nối
$db = (new Database())->connect();
$khuyenMai = new KhuyenMai($db);

// Lấy MaKhuyenMai từ URL (?MaKhuyenMai=...) hoặc từ body JSON
$maKhuyenMai = $_GET['MaKhuyenMai'] ?? null;

// Nếu không có trên URL, thử lấy từ body DELETE
if (!$maKhuyenMai) {
    $body = json_decode(file_get_contents("php://input"), true);
    $maKhuyenMai = $body['MaKhuyenMai'] ?? null;
}

// Xử lý xóa nếu có mã khuyến mãi
if ($maKhuyenMai) {
    $success = $khuyenMai->delete($maKhuyenMai);

    echo json_encode([
        "success" => $success,
        "message" => $success ? "Xóa khuyến mãi thành công" : "Xóa khuyến mãi thất bại"
    ]);
} else {
    echo json_encode([
        "success" => false,
        "message" => "Thiếu mã khuyến mãi (MaKhuyenMai)"
    ]);
}
?>
