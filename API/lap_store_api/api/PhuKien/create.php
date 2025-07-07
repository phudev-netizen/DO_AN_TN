<?php
// Thêm phụ kiện
   header('Access-Control-Allow-Origin:*');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: POST');
    header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With');
    
    include_once('../../config/database.php');
    include_once('../../model/phukien.php');
    
$database = new database();
$conn = $database->Connect();
$phukien = new PhuKien($conn);

$data = json_decode(file_get_contents("php://input"));

$phukien->TenSanPham = $data->TenSanPham ?? '';
$phukien->MaLoaiSanPham = $data->MaLoaiSanPham ?? '';
$phukien->MaMauSac = $data->MaMauSac ?? '';
$phukien->KieuKetNoi = $data->KieuKetNoi ?? '';
$phukien->SoLuongPhim = $data->SoLuongPhim ?? '';
$phukien->DenNen = $data->DenNen ?? '';
$phukien->DPI = $data->DPI ?? '';
$phukien->Gia = $data->Gia ?? '';
$phukien->SoLuong = $data->SoLuong ?? '';
$phukien->MoTa = $data->MoTa ?? '';
$phukien->TrangThai = $data->TrangThai ?? '';

if ($phukien->AddPhuKien()) {
    echo json_encode([
        "success" => true,
        "message" => "Thêm phụ kiện thành công"
    ]);
} else {
    echo json_encode([
        "success" => false,
        "message" => "Thêm phụ kiện thất bại"
    ]);
}
?>