<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
header("Access-Control-Allow-Methods: PUT");
header("Access-Control-Allow-Headers: Content-Type, Authorization");

include_once '../../config/database.php';
include_once '../../model/KhuyenMai.php';

$db = (new Database())->connect();
$khuyenMai = new KhuyenMai($db);

$data = json_decode(file_get_contents("php://input"), true);

if (
    isset($data['MaKhuyenMai']) &&
    isset($data['TenKhuyenMai']) &&
    isset($data['PhanTramGiam']) &&
    isset($data['NgayBatDau']) &&
    isset($data['NgayKetThuc'])
) {
    $query = "UPDATE khuyenmai SET TenKhuyenMai = ?, PhanTramGiam = ?, NgayBatDau = ?, NgayKetThuc = ? WHERE MaKhuyenMai = ?";
    $stmt = $db->prepare($query);
    $success = $stmt->execute([
        $data['TenKhuyenMai'],
        $data['PhanTramGiam'],
        $data['NgayBatDau'],
        $data['NgayKetThuc'],
        $data['MaKhuyenMai']
    ]);

    echo json_encode([
        "success" => $success,
        "message" => $success ? "Cập nhật thành công" : "Cập nhật thất bại"
    ]);
} else {
    echo json_encode(["error" => "Thiếu dữ liệu"]);
}
?>
