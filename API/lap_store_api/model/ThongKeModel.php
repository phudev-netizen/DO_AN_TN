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

}
?>
