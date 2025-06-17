<?php
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST, OPTIONS');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');


require_once '../model/YeuThichModel.php';

$maKhachHang = $_POST['maKhachHang'] ?? 0;
$maSanPham = $_POST['maSanPham'] ?? 0;

$model = new YeuThichModel();
$result = $model->toggleYeuThich($maKhachHang, $maSanPham);

echo json_encode($result);
?>
