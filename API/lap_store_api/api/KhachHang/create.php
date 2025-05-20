<?php
    header('Access-Control-Allow-Origin:*');
    header('Content-Type: application/json');
    header('Access-Control-Allow-Methods: POST');
    header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With');
    
    include_once('../../config/database.php');
    include_once('../../model/khachhang.php');

    // Tạo đối tượng database và kết nối
    $database = new database();
    $conn = $database->Connect(); // Lấy kết nối PDO

    // Khởi tạo lớp Khachhang với kết nối PDO
    $khachhang = new Khachhang($conn);

    $data = json_decode(file_get_contents("php://input"));
    $khachhang->HoTen = $data->HoTen;
    $khachhang->Email = $data->Email;
    $khachhang->SoDienThoai = $data->SoDienThoai;
    $khachhang->MaDiaChi = $data->MaDiaChi;


    if($khachhang->AddKhachHang()){
        echo json_encode(array('message','Khach Hang Created'));
    }
    else{
        echo json_encode(array('message','Khach Hang Not Created'));
    }

?>