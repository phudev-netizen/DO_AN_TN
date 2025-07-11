<?php
class ThongKeModel {
    private $conn;

    public function __construct($pdo)
    {
        $this->conn = $pdo;
    }

    public function getTotalProducts() {
        $stmt = $this->conn->prepare("SELECT COUNT(*) AS total FROM SanPham");
        $stmt->execute();
        return $stmt->fetch(PDO::FETCH_ASSOC)['total'];
    }

    public function getTotalOrders() {
        $stmt = $this->conn->prepare("SELECT COUNT(*) AS total FROM hoadonban");
        $stmt->execute();
        return $stmt->fetch(PDO::FETCH_ASSOC)['total'];
    }

    public function getTotalRevenue() {
        $stmt = $this->conn->prepare("SELECT SUM(tongtien) AS total FROM hoadonban "); // WHERE trangthai = 'Đã giao'
        $stmt->execute();
        //return $stmt->fetch(PDO::FETCH_ASSOC)['total'];
        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        return $result['total'] ?? 0;

    }


    public function getTotalUsers() {
        $stmt = $this->conn->prepare("SELECT COUNT(*) AS total FROM khachhang");
        $stmt->execute();
        return $stmt->fetch(PDO::FETCH_ASSOC)['total'];
    }

    public function getMonthlyRevenue() {
    $sql = "
        SELECT MONTH(NgayDatHang) AS month, SUM(TongTien) AS revenue
        FROM hoadonban
        GROUP BY MONTH(NgayDatHang)
        ORDER BY MONTH(NgayDatHang)
    "; // WHERE TrangThai = 'Đã giao'

    $stmt = $this->conn->prepare($sql);
    $stmt->execute();
    //return $stmt->fetchAll(PDO::FETCH_ASSOC);
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
    return $result ?: [];

    }
    //theonam
    public function getTotalRevenueTheoNam($nam) {
    $sql = "SELECT SUM(TongTien) AS total FROM hoadonban WHERE YEAR(NgayDatHang) = :nam";
    $stmt = $this->conn->prepare($sql);
    $stmt->bindParam(':nam', $nam, PDO::PARAM_INT);
    $stmt->execute();
    $result = $stmt->fetch(PDO::FETCH_ASSOC);
    return $result['total'] ?? 0;
}

public function getMonthlyRevenueTheoNam($nam) {
    $sql = "
        SELECT MONTH(NgayDatHang) AS month, SUM(TongTien) AS revenue
        FROM hoadonban
        WHERE YEAR(NgayDatHang) = :nam
        GROUP BY MONTH(NgayDatHang)
        ORDER BY MONTH(NgayDatHang)
    ";
    $stmt = $this->conn->prepare($sql);
    $stmt->bindParam(':nam', $nam, PDO::PARAM_INT);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
    return $result ?: [];
}
//theo sp bán chạy
public function getTopSellingProducts($limit = 5) {
    $sql = "
      SELECT sp.MaSanPham, sp.TenSanPham, SUM(ct.SoLuong) AS totalQty
      FROM chitiethoadonban ct
      JOIN sanpham sp ON sp.MaSanPham = ct.MaSanPham
      GROUP BY ct.MaSanPham
      ORDER BY totalQty DESC
      LIMIT :limit";
    $stmt = $this->conn->prepare($sql);
    $stmt->bindParam(':limit', $limit, PDO::PARAM_INT);
    $stmt->execute();
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}
//sp bán chậm
public function getSlowSellingProducts($limit = 5) {
    $sql = "
      SELECT sp.MaSanPham, sp.TenSanPham, SUM(ct.SoLuong) AS totalQty
      FROM chitiethoadonban ct
      JOIN sanpham sp ON sp.MaSanPham = ct.MaSanPham
      GROUP BY ct.MaSanPham
      ORDER BY totalQty ASC
      LIMIT :limit";
    $stmt = $this->conn->prepare($sql);
    $stmt->bindParam(':limit', $limit, PDO::PARAM_INT);
    $stmt->execute();
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}
//tổng sl sp đã bán
public function getTotalQuantitySold() {
    $sql = "SELECT SUM(SoLuong) AS totalQty FROM chitiethoadonban";
    $stmt = $this->conn->prepare($sql);
    $stmt->execute();
    $result = $stmt->fetch(PDO::FETCH_ASSOC);
    return $result['totalQty'] ?? 0;
}

public function getOrders() {
    $sql = "SELECT MaHoaDonBan, NgayDatHang, TrangThai, TongTien FROM hoadonban ORDER BY NgayDatHang DESC";
    $stmt = $this->conn->prepare($sql);
    $stmt->execute();
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}

}
?>
