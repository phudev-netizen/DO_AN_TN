<?php
   header('Access-Control-Allow-Origin:*');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: POST');
    header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With');
    
    include_once('../../config/database.php');
    include_once('../../model/phukien.php');
$database = new database();
$conn = $database->Connect();
$phukien = new PhuKien($conn);

$phukien->MaSanPham = isset($_GET['id']) ? $_GET['id'] : die();
if ($phukien->GetPhuKienById()) {
    $phukien_item = array(
        "MaSanPham" => $phukien->MaSanPham,
        "TenSanPham" => $phukien->TenSanPham,
        "MaLoaiSanPham" => $phukien->MaLoaiSanPham,
        "MaMauSac" => $phukien->MaMauSac,
        "KieuKetNoi" => $phukien->KieuKetNoi,
        "SoLuongPhim" => $phukien->SoLuongPhim,
        "DenNen" => $phukien->DenNen,
        "DPI" => $phukien->DPI,
        "Gia" => $phukien->Gia,
        "SoLuong" => $phukien->SoLuong,
        "MoTa" => $phukien->MoTa,
        "TrangThai" => $phukien->TrangThai,
        "HinhAnh" => $phukien->HinhAnh,
    );
    echo json_encode($phukien_item);
} else {
    echo json_encode(["message" => "Không tìm thấy phụ kiện"]);
}
?>