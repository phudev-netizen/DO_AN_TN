<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../../config/database.php';
include_once '../../model/yeuthich.php';

 $database = new database();
    $conn = $database->Connect(); 

$yeuthich = new SanPhamYeuThich($db);

$MaKhachHang = isset($_GET['MaKhachHang']) ? $_GET['MaKhachHang'] : die(json_encode(["message" => "Thiếu MaKhachHang"]));

$favorites_arr = $yeuthich->readByKhachHang($MaKhachHang);
echo json_encode($favorites_arr);
?>