<?php
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once('../../config/database.php');
include_once('../../model/taikhoan.php');

$raw = file_get_contents("php://input");
$data = json_decode($raw);

if (!isset($data->usernameOrEmail)) {
    echo json_encode([
        "success" => false,
        "message" => "Thiếu usernameOrEmail"
    ]);
    exit;
}

$usernameOrEmail = $data->usernameOrEmail;

$database = new database();
$db = $database->Connect();
$taikhoan = new TaiKhoan($db);

$newPass = "";

if ($taikhoan->ForgotPassword($usernameOrEmail, $newPass)) {
    echo json_encode([
        "success" => true,
        "message" => "Mật khẩu mới của tài khoản là: $newPass"
    ]);
} else {
    echo json_encode([
        "success" => false,
        "message" => "Không tìm thấy tài khoản"
    ]);
}
?>
