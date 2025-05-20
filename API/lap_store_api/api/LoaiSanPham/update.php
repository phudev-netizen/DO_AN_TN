<?php
    header('Access-Control-Allow-Origin:*');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: PUT');
    header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With');
    
    include_once('../../config/database.php');
    include_once('../../model/loaisanpham.php');

    // Tạo đối tượng database và kết nối
    $database = new database();
    $conn = $database->Connect(); // Lấy kết nối PDO

    // Khởi tạo lớp Khachhang với kết nối PDO
    $loaisanpham = new loaisanpham($conn);

    $data = json_decode(file_get_contents("php://input"));

    $loaisanpham->MaLoai = $data->MaLoai;
    $loaisanpham->TenLoai = $data->TenLoai;

    if($loaisanpham->UpdateLoaiSanPham()){
        echo json_encode(array('message','Tinh Updated'));
    }
    else{
        echo json_encode(array('message','Tinh Not Updated'));
    }

?>