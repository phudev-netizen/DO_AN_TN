<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../../config/database.php';
include_once '../../objects/SanPhamYeuThich.php';

$database = new Database();
$db = $database->getConnection();

$yeuthich = new SanPhamYeuThich($db);

$MaKhachHang = isset($_GET['MaKhachHang']) ? $_GET['MaKhachHang'] : die(json_encode(["message" => "Thiếu MaKhachHang"]));

$stmt = $yeuthich->readByKhachHang($MaKhachHang);
$num = $stmt->rowCount();

if ($num > 0) {
    $favorites_arr = [];

    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        extract($row);
        $favorites_arr[] = [
            "id" => $id,
            "MaSanPham" => $MaSanPham,
            "MaKhachHang" => $MaKhachHang
        ];
    }

    echo json_encode($favorites_arr);
} else {
    echo json_encode([]);
}
?>
