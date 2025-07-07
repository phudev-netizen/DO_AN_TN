<?php
header('Content-Type: application/json');

$data = json_decode(file_get_contents("php://input"), true);

if ($data && isset($data['extraData'])) {
    $extraDataJson = base64_decode($data['extraData']);
    $extra = json_decode($extraDataJson, true);

    $makhachhang = $extra['makhachhang'];
    $madiachi = $extra['madiachi'];
    $ghichu = $extra['ghichu'];

    $resultCode = $data['resultCode']; // 0 là thành công

    if ($resultCode == 0) {
        // ✅ Ghi hóa đơn vào database tại đây
        // Bạn có thể dùng file model/hoadonban.php và gọi addHoaDon()
        // hoặc lưu log ra file để test

        file_put_contents('momo_log.txt', json_encode([
            'success' => true,
            'makhachhang' => $makhachhang,
            'madiachi' => $madiachi,
            'ghichu' => $ghichu,
            'orderId' => $data['orderId']
        ]) . "\n", FILE_APPEND);
    } else {
        file_put_contents('momo_log.txt', json_encode([
            'fail' => true,
            'resultCode' => $resultCode
        ]) . "\n", FILE_APPEND);
    }
}
