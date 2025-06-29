<?php
class KhuyenMai {
    private $conn;
    private $table = "khuyenmai";

    public function __construct($db) {
        $this->conn = $db;
    }

    // Lấy tất cả khuyến mãi còn hiệu lực + sản phẩm
    public function getAll() {
        $query = "SELECT km.*, sp.TenSanPham, sp.Gia
                  FROM $this->table km
                  JOIN sanpham sp ON km.MaSanPham = sp.MaSanPham
                  WHERE CURDATE() BETWEEN km.NgayBatDau AND km.NgayKetThuc";
        return $this->conn->query($query);
    }

    // Lấy khuyến mãi theo sản phẩm
    public function getByProductId($maSanPham) {
        $query = "SELECT km.*, sp.TenSanPham, sp.Gia
                  FROM $this->table km
                  JOIN sanpham sp ON km.MaSanPham = sp.MaSanPham
                  WHERE km.MaSanPham = ? AND CURDATE() BETWEEN km.NgayBatDau AND km.NgayKetThuc";
        $stmt = $this->conn->prepare($query);
        $stmt->execute([$maSanPham]);
        return $stmt;
    }

    // Thêm mới khuyến mãi
    public function insert($data) {
        $query = "INSERT INTO $this->table (MaSanPham, TenKhuyenMai, PhanTramGiam, NgayBatDau, NgayKetThuc)
                  VALUES (?, ?, ?, ?, ?)";
        $stmt = $this->conn->prepare($query);
        return $stmt->execute([
            $data['MaSanPham'],
            $data['TenKhuyenMai'],
            $data['PhanTramGiam'],
            $data['NgayBatDau'],
            $data['NgayKetThuc']
        ]);
    }
    public function delete($maKhuyenMai) {
    $query = "DELETE FROM $this->table WHERE MaKhuyenMai = ?";
    $stmt = $this->conn->prepare($query);
    return $stmt->execute([$maKhuyenMai]);
}
    
    public function update($data) {
    $query = "UPDATE $this->table 
              SET TenKhuyenMai = ?, PhanTramGiam = ?, NgayBatDau = ?, NgayKetThuc = ? 
              WHERE MaKhuyenMai = ?";
    $stmt = $this->conn->prepare($query);
    return $stmt->execute([
        $data['TenKhuyenMai'],
        $data['PhanTramGiam'],
        $data['NgayBatDau'],
        $data['NgayKetThuc'],
        $data['MaKhuyenMai']
    ]);
}

}
?>
