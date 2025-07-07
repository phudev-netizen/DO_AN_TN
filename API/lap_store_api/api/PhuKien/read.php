<?php
// Lấy tất cả phụ kiện
    header('Access-Control-Allow-Origin:*');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: POST');
    header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With');
    
    include_once('../../config/database.php');
    include_once('../../model/phukien.php');

$database = new database();
$conn = $database->Connect();
$phukien = new PhuKien($conn);

$stmt = $phukien->GetAllPhuKien();
$num = $stmt->rowCount();

if ($num > 0) {
    $phukien_arr = array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        extract($row);
        $phukien_item = array(
            "MaSanPham" => $MaSanPham,
            "TenSanPham" => $TenSanPham,
            "MaLoaiSanPham" => $MaLoaiSanPham,
            "MaMauSac" => $MaMauSac,
            "KieuKetNoi" => $KieuKetNoi,
            "SoLuongPhim" => $SoLuongPhim,
            "DenNen" => $DenNen,
            "DPI" => $DPI,
            "Gia" => $Gia,
            "SoLuong" => $SoLuong,
            "MoTa" => $MoTa,
            "TrangThai" => $TrangThai,
            "HinhAnh" => $HinhAnh
        );
        array_push($phukien_arr, $phukien_item);
    }
    echo json_encode(["phukien" => $phukien_arr]);
} else {
    echo json_encode(["phukien" => []]);
}
?>