<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// Kết nối CSDL và model hóa đơn
include_once("../../config/database.php");
include_once("../../model/hoadonban.php");

// Lấy dữ liệu từ client gửi qua (POST)
$maKhachHang = $_POST['MaKhachHang'] ?? '';
$maDiaChi = $_POST['MaDiaChi'] ?? '';
$tongTien = $_POST['TongTien'] ?? '';
$ngayDat = date("Y-m-d");
$phuongThuc = "Thanh toán qua Momo";
$trangThai = 0; // Chưa xác nhận

// Kiểm tra dữ liệu đầu vào
if (empty($maKhachHang) || empty($maDiaChi) || empty($tongTien)) {
    echo json_encode(["success" => false, "message" => "Thiếu thông tin bắt buộc"]);
    exit;
}

// Tạo kết nối CSDL
 $database = new database();
 $conn = $database->Connect();

// Tạo hóa đơn mới
$hoaDon = new HoaDonBan($db);

// Gán dữ liệu cho đối tượng
$hoaDon->MaHoaDonBan = null;
$hoaDon->MaKhachHang = $maKhachHang;
$hoaDon->NgayDatHang = $ngayDat;
$hoaDon->MaDiaChi = $maDiaChi;
$hoaDon->TongTien = $tongTien;
$hoaDon->PhuongThucThanhToan = $phuongThuc;
$hoaDon->TrangThai = $trangThai;

// Thêm hóa đơn vào DB
if ($hoaDon->addHoaDonBan()) {
    // Lấy mã hóa đơn mới nhất để đưa vào URL thanh toán (tùy chọn)
    $maHoaDonMoi = $hoaDon->getMaxMaHoaDonBan();

    // Giả lập tạo liên kết thanh toán qua Momo
    $payUrl = "https://momo.vn/pay?amount={$tongTien}&orderId={$maHoaDonMoi}&makh={$maKhachHang}";

    echo json_encode([
        "success" => true,
        "payUrl" => $payUrl,
        "MaHoaDonBan" => $maHoaDonMoi
    ]);
} else {
    echo json_encode([
        "success" => false,
        "message" => "Tạo hóa đơn thất bại"
    ]);
}
?>