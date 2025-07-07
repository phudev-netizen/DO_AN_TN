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
              LEFT JOIN sanpham sp ON km.MaSanPham = sp.MaSanPham
              WHERE CURDATE() BETWEEN km.NgayBatDau AND km.NgayKetThuc";
    return $this->conn->query($query);
}

    // Lấy khuyến mãi theo sản phẩm
    public function getByProductId($maSanPham) {
    $query = "SELECT km.*, sp.TenSanPham, sp.Gia
              FROM $this->table km
              LEFT JOIN sanpham sp ON km.MaSanPham = sp.MaSanPham
              WHERE (km.MaSanPham = ? OR km.MaSanPham = 0)
              AND CURDATE() BETWEEN km.NgayBatDau AND km.NgayKetThuc";
    $stmt = $this->conn->prepare($query);
    $stmt->execute([$maSanPham]);
    return $stmt;
}
    // Thêm mới khuyến mãi
    public function insert($data) {
    if ($data['MaSanPham'] == 0) {
        // Áp dụng cho tất cả sản phẩm
        $querySP = "SELECT MaSanPham FROM sanpham";
        $stmtSP = $this->conn->prepare($querySP);
        $stmtSP->execute();
        $sanPhams = $stmtSP->fetchAll(PDO::FETCH_ASSOC);

        $queryInsert = "INSERT INTO $this->table (MaSanPham, TenKhuyenMai, PhanTramGiam, NgayBatDau, NgayKetThuc)
                        VALUES (?, ?, ?, ?, ?)";
        $stmtInsert = $this->conn->prepare($queryInsert);

        foreach ($sanPhams as $sp) {
            $stmtInsert->execute([
                $sp['MaSanPham'],
                $data['TenKhuyenMai'],
                $data['PhanTramGiam'],
                $data['NgayBatDau'],
                $data['NgayKetThuc']
            ]);
        }
        return true;
    } else {
        // Áp dụng cho một sản phẩm
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
