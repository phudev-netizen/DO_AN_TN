<?php
header('Access-Control-Allow-Origin:*');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

include_once('../../config/database.php');
include_once('../../model/MomoPayment.php');

// Nhận dữ liệu từ app
$data = json_decode(file_get_contents("php://input"));

if (!isset($data->amount)) {
    echo json_encode(['success' => false, 'message' => 'Thiếu số tiền']);
    exit;
}

$momo = new MomoPayment();
$result = $momo->createPayment($data->amount);

// Gửi link thanh toán về cho app
if (isset($result['payUrl'])) {
    echo json_encode([
        'success' => true,
        'payUrl' => $result['payUrl']
    ]);
} else {
    echo json_encode([
        'success' => false,
        'message' => 'Tạo thanh toán thất bại',
        'error' => $result
    ]);
}
