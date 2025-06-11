<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../../config/database.php';
include_once '../../model/yeuthich.php';

$database = new database();
$conn = $database->Connect();

$yeuthich = new SanPhamYeuThich($conn);

// Nhận dữ liệu từ JSON hoặc POST truyền thống
$data = json_decode(file_get_contents("php://input"), true);
$MaKhachHang = isset($data['MaKhachHang']) ? intval($data['MaKhachHang']) : (isset($_POST['MaKhachHang']) ? intval($_POST['MaKhachHang']) : null);

if ($MaKhachHang) {
    // Lấy danh sách sản phẩm yêu thích theo khách hàng từ model, trả về đầy đủ thông tin từ bảng sanpham
    $sql = "SELECT sp.*
            FROM sanphamyeuthich syt
            JOIN sanpham sp ON syt.MaSanPham = sp.MaSanPham
            WHERE syt.MaKhachHang = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $MaKhachHang);
    $stmt->execute();
    $result = $stmt->get_result();

    $list = array();
    while ($row = $result->fetch_assoc()) {
        $list[] = $row;
    }

    echo json_encode($list);
    $stmt->close();
} else {
    echo json_encode(["success" => false, "message" => "Thiếu MaKhachHang."]);
}
?>