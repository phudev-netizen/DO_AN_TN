<?php
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Authorization, X-Requested-With');

include_once('../../config/database.php');
include_once('../../model/hoadonban.php');

$database = new Database();
$conn = $database->Connect();
$hoadon = new HoaDonBan($conn);

$raw = file_get_contents("php://input");
$data = json_decode($raw);

// debug
file_put_contents("debug_log.txt", "Raw: $raw\nParsed: " . print_r($data, true));

if (
    isset($data->MaHoaDonBan) &&
    isset($data->LyDoTraHang)
) {
    $hoadon->MaHoaDonBan = $data->MaHoaDonBan;
    $hoadon->LyDoTraHang = $data->LyDoTraHang;
    $hoadon->TrangThai   = 7; // cứng cho “Trả hàng”

    // Gọi hàm mới chỉ cập nhật lý do + trạng thái
    if ($hoadon->updateTrangThaiVaLyDoTraHang()) {
        echo json_encode(["success" => true, "message" => "Cập nhật lý do trả hàng thành công."]);
    } else {
        echo json_encode(["success" => false, "message" => "Cập nhật thất bại."]);
    }
} else {
    echo json_encode([
        "success" => false,
        "message" => "Thiếu dữ liệu.",
        "debug" => [
            "MaHoaDonBan"   => $data->MaHoaDonBan ?? null,
            "LyDoTraHang"   => $data->LyDoTraHang ?? null
        ]
    ]);
}
