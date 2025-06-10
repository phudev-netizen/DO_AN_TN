<?php
// objects/SanPhamYeuThich.php
class SanPhamYeuThich {
    private $conn;
    private $table_name = "Sanphamyeuthich";

    public $id;
    public $MaSanPham;
    public $MaKhachHang;

    public function __construct($db) {
        $this->conn = $db;
    }

    // Kiểm tra sản phẩm đã được yêu thích chưa
    public function check($MaSanPham, $MaKhachHang) {
        $query = "SELECT id FROM " . $this->table_name . " WHERE MaSanPham = ? AND MaKhachHang = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(1, $MaSanPham, PDO::PARAM_INT);
        $stmt->bindParam(2, $MaKhachHang, PDO::PARAM_INT);
        $stmt->execute();
        return $stmt->rowCount() > 0;
    }

    // Thêm sản phẩm yêu thích (có kiểm tra trùng lặp)
    public function create() {
        // Kiểm tra trùng lặp
        if ($this->check($this->MaSanPham, $this->MaKhachHang)) {
            return array("success" => false, "message" => "Đã tồn tại trong danh sách yêu thích");
        }

        $query = "INSERT INTO " . $this->table_name . " (MaSanPham, MaKhachHang) VALUES (:MaSanPham, :MaKhachHang)";
        $stmt = $this->conn->prepare($query);

        $this->MaSanPham = htmlspecialchars(strip_tags($this->MaSanPham));
        $this->MaKhachHang = htmlspecialchars(strip_tags($this->MaKhachHang));

        $stmt->bindParam(":MaSanPham", $this->MaSanPham, PDO::PARAM_INT);
        $stmt->bindParam(":MaKhachHang", $this->MaKhachHang, PDO::PARAM_INT);

        if ($stmt->execute()) {
            return array("success" => true, "message" => "Thêm vào yêu thích thành công");
        }
        return array("success" => false, "message" => "Lỗi khi thêm vào yêu thích");
    }

    // Xóa sản phẩm yêu thích
    public function delete() {
        $query = "DELETE FROM " . $this->table_name . " WHERE MaSanPham = :MaSanPham AND MaKhachHang = :MaKhachHang";
        $stmt = $this->conn->prepare($query);

        $this->MaSanPham = htmlspecialchars(strip_tags($this->MaSanPham));
        $this->MaKhachHang = htmlspecialchars(strip_tags($this->MaKhachHang));

        $stmt->bindParam(":MaSanPham", $this->MaSanPham, PDO::PARAM_INT);
        $stmt->bindParam(":MaKhachHang", $this->MaKhachHang, PDO::PARAM_INT);

        if ($stmt->execute()) {
            return array("success" => true, "message" => "Đã xóa khỏi yêu thích");
        }
        return array("success" => false, "message" => "Lỗi khi xóa khỏi yêu thích");
    }

    // Lấy danh sách sản phẩm yêu thích của khách hàng
    public function readByKhachHang($MaKhachHang) {
        $query = "SELECT * FROM " . $this->table_name . " WHERE MaKhachHang = ? ORDER BY id DESC";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(1, $MaKhachHang, PDO::PARAM_INT);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC); // Trả về mảng kết quả
    }
}
?>