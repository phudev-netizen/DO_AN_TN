<?php
// Cấu hình header
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

// Kết nối đến database và model
include_once('../../config/database.php');
include_once('../../model/binhluandanhgia.php');

// Tạo đối tượng database và kết nối
$database = new Database();
$conn = $database->Connect(); // Lấy kết nối PDO

// Khởi tạo lớp binhluandanhgia với kết nối PDO (viết đúng tên class theo file model)
$binhluan = new binhluandanhgia($conn);

// Lấy dữ liệu JSON được gửi từ client
$data = json_decode(file_get_contents("php://input"));

// Gán dữ liệu cho các thuộc tính của đối tượng (đã đổi MaDonHang thành MaHoaDonBan)
$binhluan->MaBinhLuan = $data->MaBinhLuan ?? null;
$binhluan->MaKhachHang = $data->MaKhachHang ?? null;
$binhluan->MaSanPham = $data->MaSanPham ?? null;
$binhluan->MaHoaDonBan = $data->MaHoaDonBan ?? null;
$binhluan->SoSao = $data->SoSao ?? null;
$binhluan->NoiDung = $data->NoiDung ?? null;
$binhluan->NgayDanhGia = $data->NgayDanhGia ?? null;
$binhluan->TrangThai = $data->TrangThai ?? null;

// Gọi hàm thêm mới bình luận đánh giá
if ($binhluan->AddBinhLuanDanhGia()) {
    echo json_encode(array('message' => 'Thêm bình luận thành công'));
} else {
    echo json_encode(array('message' => 'Thêm bình luận thất bại'));
}
?>

