<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
header("Access-Control-Allow-Methods: DELETE");
header("Access-Control-Allow-Headers: Content-Type, Authorization");

include_once '../../config/database.php';
include_once '../../model/KhuyenMai.php';

$db = (new Database())->connect();
$khuyenMai = new KhuyenMai($db);

// Lấy ID từ query
$maKhuyenMai = $_GET['MaKhuyenMai'] ?? null;

if ($maKhuyenMai) {
    $query = "DELETE FROM khuyenmai WHERE MaKhuyenMai = ?";
    $stmt = $db->prepare($query);
    $success = $stmt->execute([$maKhuyenMai]);

    echo json_encode([
        "success" => $success,
        "message" => $success ? "Xoá thành công" : "Xoá thất bại"
    ]);
} else {
    echo json_encode(["error" => "Thiếu MaKhuyenMai"]);
}
?>
