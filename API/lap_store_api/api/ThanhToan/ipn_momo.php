<?php
$data = json_decode(file_get_contents("php://input"));
if ($data->resultCode == 0) {
    // Thanh toán thành công
    $orderId = $data->orderId;
    // Bạn xử lý cập nhật DB ở đây nếu muốn
}
http_response_code(200);
