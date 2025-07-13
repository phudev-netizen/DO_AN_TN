<?php
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json; charset=UTF-8');

include_once('../../config/database.php');
include_once('../../model/hoadonban.php');

// Tạo đối tượng database và kết nối
$database = new Database();
$conn = $database->Connect(); // Lấy kết nối PDO

// Khởi tạo lớp HoaDonBan với kết nối PDO
$hoadon = new HoaDonBan($conn);

// Lấy tất cả dữ liệu hóa đơn bán
$getAllHoaDon = $hoadon->getAllHoaDonBan();
$num = $getAllHoaDon->rowCount();

// Khởi tạo mảng trả về
$hoadon_array = [];
$hoadon_array['hoadonban'] = [];

if ($num > 0) {
    while ($row = $getAllHoaDon->fetch(PDO::FETCH_ASSOC)) {
        extract($row);

        $hoadon_item = array(
            'MaHoaDonBan' => $MaHoaDonBan,
            'MaKhachHang' => $MaKhachHang,
            'NgayDatHang' => $NgayDatHang,
            'MaDiaChi' => $MaDiaChi,
            'TongTien' => $TongTien,
            'PhuongThucThanhToan' => $PhuongThucThanhToan,
            'TrangThai' => $TrangThai
        );

        array_push($hoadon_array['hoadonban'], $hoadon_item);
    }
}

// Luôn trả về object có trường hoadonban là mảng (dù rỗng)
echo json_encode($hoadon_array, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

?>
