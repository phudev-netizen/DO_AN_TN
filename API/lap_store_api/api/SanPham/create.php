<?php
header('Access-Control-Allow-Origin:*');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers, Content-Type,Access-Control-Allow-Methods,Authorization,X-Requested-With');

include_once('../../config/database.php');
include_once('../../model/sanpham.php');

$database = new database();
$conn = $database->Connect();
$sanpham = new SanPham($conn);

// Nhận dữ liệu JSON
$data = json_decode(file_get_contents("php://input"));
$sanpham->TenSanPham = $data->TenSanPham ?? '';
$sanpham->MaLoaiSanPham = $data->MaLoaiSanPham ?? null;
$sanpham->CPU = $data->CPU ?? '';
$sanpham->RAM = $data->RAM ?? '';
$sanpham->CardManHinh = $data->CardManHinh ?? '';
$sanpham->SSD = $data->SSD ?? '';
$sanpham->ManHinh = $data->ManHinh ?? '';
$sanpham->MaMauSac = $data->MaMauSac ?? null;
$sanpham->Gia = $data->Gia ?? 0;
$sanpham->SoLuong = $data->SoLuong ?? 0;
$sanpham->MoTa = $data->MoTa ?? '';
$sanpham->HinhAnh = $data->HinhAnh ?? ''; // URL ảnh
$sanpham->TrangThai = $data->TrangThai ?? 1;

// Thêm sản phẩm
if ($sanpham->AddSanPham()) {
    // Lấy MaSanPham vừa thêm
    $lastId = $conn->lastInsertId();

    // Nếu có ảnh thì thêm vào bảng hinhanh
    if (!empty($sanpham->HinhAnh)) {
        $query = "INSERT INTO hinhanh (MaSanPham, DuongDan, MacDinh) 
                  VALUES (:MaSanPham, :DuongDan, 1)";
        $stmt = $conn->prepare($query);
        $stmt->bindParam(':MaSanPham', $lastId);
        $stmt->bindParam(':DuongDan', $sanpham->HinhAnh);

        if ($stmt->execute()) {
            echo json_encode(['success' => true, 'message' => 'Thêm sản phẩm và ảnh thành công']);
        } else {
            echo json_encode(['success' => true, 'message' => 'Sản phẩm OK, ảnh lỗi']);
        }
    } else {
        echo json_encode(['success' => true, 'message' => 'Sản phẩm thêm thành công (không ảnh)']);
    }
} else {
    echo json_encode(['success' => false, 'message' => 'Thêm sản phẩm thất bại']);
}
?>
