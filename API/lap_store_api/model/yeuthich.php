<?php
class YeuThich {
    private $db;

    private $table = 'yeuthich';
    private $id = 'ID';
    private $maKhachHang = 'MaKhachHang';
    private $maSanPham = 'MaSanPham';   
    private $ngayYeuThich = 'NgayYeuThich';

    public function __construct($db) {
        $this->db = $db;
    }

    // Thêm yêu thích
    public function them($maKhachHang, $maSanPham) {
        $stmt = $this->db->prepare("SELECT ID FROM yeuthich WHERE MaKhachHang = ? AND MaSanPham = ?");
        $stmt->execute([$maKhachHang, $maSanPham]);
        if ($stmt->rowCount() > 0) {
            return ['success' => false, 'message' => 'Sản phẩm đã được yêu thích'];
        }
        $stmt = $this->db->prepare("INSERT INTO yeuthich (MaKhachHang, MaSanPham, NgayYeuThich) VALUES (?, ?, NOW())");
        if ($stmt->execute([$maKhachHang, $maSanPham])) {
            return ['success' => true, 'message' => 'Đã thêm vào yêu thích'];
        }
        return ['success' => false, 'message' => 'Lỗi khi thêm'];
    }

    // Xoá yêu thích
    public function xoa($maKhachHang, $maSanPham) {
        $stmt = $this->db->prepare("DELETE FROM yeuthich WHERE MaKhachHang = ? AND MaSanPham = ?");
        if ($stmt->execute([$maKhachHang, $maSanPham])) {
            return ['success' => true, 'message' => 'Đã xoá khỏi yêu thích'];
        }
        return ['success' => false, 'message' => 'Lỗi khi xoá'];
    }

    // ✅ Lấy danh sách yêu thích theo khách hàng (trả thêm ID để Android parse được)
    public function layDanhSach($maKhachHang) {
        $stmt = $this->db->prepare("SELECT ID, MaKhachHang, MaSanPham, NgayYeuThich FROM yeuthich WHERE MaKhachHang = ? ORDER BY NgayYeuThich DESC");
        $stmt->execute([$maKhachHang]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}
?>