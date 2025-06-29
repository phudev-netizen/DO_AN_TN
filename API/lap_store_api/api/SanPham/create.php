<?php
    header('Access-Control-Allow-Origin:*');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: POST');
    header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With');
    
    include_once('../../config/database.php');
    include_once('../../model/sanpham.php');

    // Tạo đối tượng database và kết nối
    $database = new database();
    $conn = $database->Connect(); // Lấy kết nối PDO

    // Khởi tạo lớp SanPham với kết nối PDO
    $sanpham = new SanPham($conn);

    $data = json_decode(file_get_contents("php://input"));
    $sanpham->MaSanPham = $data->MaSanPham ?? null;
    $sanpham->TenSanPham = $data->TenSanPham ?? '';
    $sanpham->MaLoaiSanPham = $data->MaLoaiSanPham ?? null;
    $sanpham->CPU = $data->CPU ?? '';
    $sanpham->RAM = $data->RAM ?? '';
    $sanpham->CardManHinh = $data->CardManHinh ?? '';
    $sanpham->SSD = $data->SSD ?? '';
    $sanpham->ManHinh = $data->ManHinh ?? '';
    $sanpham->MaMauSac = $data->MaMauSac ?? null;
    $sanpham->Gia = $data->Gia ?? 0;
    $sanpham->SoLuong = $data->SoLuong ?? 0;
    $sanpham->MoTa = $data->MoTa ?? '';
    $sanpham->HinhAnh = $data->HinhAnh ?? '';
    $sanpham->TrangThai = $data->TrangThai ?? 1;

    if($sanpham->AddSanPham()){
        echo json_encode(array('success' => true, 'message' => 'San Pham Created'));
    }
    else{
        echo json_encode(array('success' => false, 'message' => 'San Pham Not Created'));
    }
?>