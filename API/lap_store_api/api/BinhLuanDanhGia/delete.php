<?php
// Cấu hình header
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Methods: DELETE');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type, Access-Control-Allow-Methods, Authorization, X-Requested-With');

// Kết nối đến database và model
include_once('../../config/database.php');
include_once('../../model/binhluandanhgia.php');

// Tạo đối tượng database và kết nối
$database = new Database();
$conn = $database->Connect(); // Lấy kết nối PDO

// Khởi tạo lớp binhluandanhgia với kết nối PDO (viết đúng tên class theo file model)
$binhluan = new binhluandanhgia($conn);

// Lấy dữ liệu JSON từ yêu cầu
$data = json_decode(file_get_contents("php://input"));

// Gán giá trị cho thuộc tính MaBinhLuan
$binhluan->MaBinhLuan = $data->MaBinhLuan ?? null;

// Gọi hàm DeleteBinhLuanDanhGia() để xóa bình luận
if ($binhluan->DeleteBinhLuanDanhGia()) {
    echo json_encode(array('message' => 'Xóa bình luận thành công'));
} else {
    echo json_encode(array('message' => 'Xóa bình luận thất bại'));
}
?>