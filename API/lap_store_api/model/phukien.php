<?php
class PhuKien
{
    private $conn;

    // Thuộc tính
    public $MaSanPham;
    public $TenSanPham;
    public $MaLoaiSanPham;
    public $MaMauSac;
    public $KieuKetNoi;
    public $SoLuongPhim;
    public $DenNen;
    public $DPI;
    public $Gia;
    public $SoLuong;
    public $MoTa;
    public $TrangThai;
    public $HinhAnh;        // Đường dẫn hình ảnh, nếu có

    public function __construct($database)
    {
        $this->conn = $database;
    }

    // Lấy tất cả phụ kiện
    public function GetAllPhuKien()
    {
        $query = "SELECT pk.*, ha.DuongDan as HinhAnh 
                  FROM PhuKien pk
                  LEFT JOIN HinhAnh ha ON pk.MaSanPham = ha.MaSanPham AND ha.MacDinh = 1";
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt;
    }

    // Lấy phụ kiện theo ID
    public function GetPhuKienById()
    {
        $query = "SELECT pk.*, ha.DuongDan as HinhAnh 
                  FROM PhuKien pk
                  LEFT JOIN HinhAnh ha ON pk.MaSanPham = ha.MaSanPham AND ha.MacDinh = 1
                  WHERE pk.MaSanPham = ? LIMIT 1";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(1, $this->MaSanPham);
        $stmt->execute();
        $row = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($row) {
            // Gán giá trị từ kết quả vào các thuộc tính của đối tượng
            $this->TenSanPham    = $row['TenSanPham'] ?? null;
            $this->MaLoaiSanPham = $row['MaLoaiSanPham'] ?? null;
            $this->MaMauSac      = $row['MaMauSac'] ?? null;
            $this->KieuKetNoi    = $row['KieuKetNoi'] ?? null;
            $this->SoLuongPhim   = $row['SoLuongPhim'] ?? null;
            $this->DenNen        = $row['DenNen'] ?? null;
            $this->DPI           = $row['DPI'] ?? null;
            $this->Gia           = $row['Gia'] ?? null;
            $this->SoLuong       = $row['SoLuong'] ?? null;
            $this->MoTa          = $row['MoTa'] ?? null;
            $this->TrangThai     = $row['TrangThai'] ?? null;
            $this->HinhAnh       = $row['HinhAnh'] ?? null;
            return true;
        } else {
            echo "Phụ kiện không tồn tại.";
            return false;
        }
    }

    // Tìm kiếm phụ kiện
    public function GetPhuKienBySearch($searchTerm)
    {
        $query = "SELECT pk.*, ha.DuongDan as HinhAnh
                  FROM PhuKien pk
                  LEFT JOIN HinhAnh ha ON pk.MaSanPham = ha.MaSanPham AND ha.MacDinh = 1
                  WHERE pk.TenSanPham LIKE ? OR pk.MoTa LIKE ?";
        $stmt = $this->conn->prepare($query);
        $searchTerm = "%" . $searchTerm . "%";
        $stmt->bindParam(1, $searchTerm);
        $stmt->bindParam(2, $searchTerm);
        $stmt->execute();
        return $stmt;
    }

    // Lấy phụ kiện theo loại
    public function GetPhuKienByLoai()
    {
        $query = "SELECT pk.*, ha.DuongDan as HinhAnh
                  FROM PhuKien pk
                  LEFT JOIN HinhAnh ha ON pk.MaSanPham = ha.MaSanPham AND ha.MacDinh = 1
                  WHERE pk.MaLoaiSanPham = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(1, $this->MaLoaiSanPham);
        $stmt->execute();
        return $stmt;
    }

    // Thêm phụ kiện
    public function AddPhuKien()
    {
        $query = "INSERT INTO PhuKien 
        (TenSanPham, MaLoaiSanPham, MaMauSac, KieuKetNoi, SoLuongPhim, DenNen, DPI, Gia, SoLuong, MoTa, TrangThai) 
        VALUES 
        (:TenSanPham, :MaLoaiSanPham, :MaMauSac, :KieuKetNoi, :SoLuongPhim, :DenNen, :DPI, :Gia, :SoLuong, :MoTa, :TrangThai)";

        $stmt = $this->conn->prepare($query);

        $this->TenSanPham    = htmlspecialchars(strip_tags($this->TenSanPham));
        $this->MaLoaiSanPham = htmlspecialchars(strip_tags($this->MaLoaiSanPham));
        $this->MaMauSac      = htmlspecialchars(strip_tags($this->MaMauSac));
        $this->KieuKetNoi    = htmlspecialchars(strip_tags($this->KieuKetNoi));
        $this->SoLuongPhim   = htmlspecialchars(strip_tags($this->SoLuongPhim));
        $this->DenNen        = htmlspecialchars(strip_tags($this->DenNen));
        $this->DPI           = htmlspecialchars(strip_tags($this->DPI));
        $this->Gia           = htmlspecialchars(strip_tags($this->Gia));
        $this->SoLuong       = htmlspecialchars(strip_tags($this->SoLuong));
        $this->MoTa          = htmlspecialchars(strip_tags($this->MoTa));
        $this->TrangThai     = htmlspecialchars(strip_tags($this->TrangThai));

        $stmt->bindParam(':TenSanPham', $this->TenSanPham);
        $stmt->bindParam(':MaLoaiSanPham', $this->MaLoaiSanPham);
        $stmt->bindParam(':MaMauSac', $this->MaMauSac);
        $stmt->bindParam(':KieuKetNoi', $this->KieuKetNoi);
        $stmt->bindParam(':SoLuongPhim', $this->SoLuongPhim);
        $stmt->bindParam(':DenNen', $this->DenNen);
        $stmt->bindParam(':DPI', $this->DPI);
        $stmt->bindParam(':Gia', $this->Gia);
        $stmt->bindParam(':SoLuong', $this->SoLuong);
        $stmt->bindParam(':MoTa', $this->MoTa);
        $stmt->bindParam(':TrangThai', $this->TrangThai);

        if ($stmt->execute()) {
            return true;
        }
        printf("Error %s.\n", $stmt->error);
        return false;
    }

    // Cập nhật phụ kiện
    public function UpdatePhuKien()
    {
        $query = "UPDATE PhuKien 
        SET TenSanPham = :TenSanPham, 
            MaLoaiSanPham = :MaLoaiSanPham, 
            MaMauSac = :MaMauSac, 
            KieuKetNoi = :KieuKetNoi, 
            SoLuongPhim = :SoLuongPhim, 
            DenNen = :DenNen, 
            DPI = :DPI, 
            Gia = :Gia, 
            SoLuong = :SoLuong, 
            MoTa = :MoTa, 
            TrangThai = :TrangThai 
        WHERE MaSanPham = :MaSanPham";

        $stmt = $this->conn->prepare($query);

        $this->TenSanPham    = htmlspecialchars(strip_tags($this->TenSanPham));
        $this->MaLoaiSanPham = htmlspecialchars(strip_tags($this->MaLoaiSanPham));
        $this->MaMauSac      = htmlspecialchars(strip_tags($this->MaMauSac));
        $this->KieuKetNoi    = htmlspecialchars(strip_tags($this->KieuKetNoi));
        $this->SoLuongPhim   = htmlspecialchars(strip_tags($this->SoLuongPhim));
        $this->DenNen        = htmlspecialchars(strip_tags($this->DenNen));
        $this->DPI           = htmlspecialchars(strip_tags($this->DPI));
        $this->Gia           = htmlspecialchars(strip_tags($this->Gia));
        $this->SoLuong       = htmlspecialchars(strip_tags($this->SoLuong));
        $this->MoTa          = htmlspecialchars(strip_tags($this->MoTa));
        $this->TrangThai     = htmlspecialchars(strip_tags($this->TrangThai));
        $this->MaSanPham     = htmlspecialchars(strip_tags($this->MaSanPham));

        $stmt->bindParam(':TenSanPham', $this->TenSanPham);
        $stmt->bindParam(':MaLoaiSanPham', $this->MaLoaiSanPham);
        $stmt->bindParam(':MaMauSac', $this->MaMauSac);
        $stmt->bindParam(':KieuKetNoi', $this->KieuKetNoi);
        $stmt->bindParam(':SoLuongPhim', $this->SoLuongPhim);
        $stmt->bindParam(':DenNen', $this->DenNen);
        $stmt->bindParam(':DPI', $this->DPI);
        $stmt->bindParam(':Gia', $this->Gia);
        $stmt->bindParam(':SoLuong', $this->SoLuong);
        $stmt->bindParam(':MoTa', $this->MoTa);
        $stmt->bindParam(':TrangThai', $this->TrangThai);
        $stmt->bindParam(':MaSanPham', $this->MaSanPham);

        if ($stmt->execute()) {
            return true;
        }
        printf("Error %s.\n", $stmt->error);
        return false;
    }

    // Xóa phụ kiện
    public function DeletePhuKien()
    {
        $query = "DELETE FROM PhuKien WHERE MaSanPham=:MaSanPham";
        $stmt = $this->conn->prepare($query);

        $this->MaSanPham = htmlspecialchars(strip_tags($this->MaSanPham));
        $stmt->bindParam(':MaSanPham', $this->MaSanPham);

        if ($stmt->execute()) {
            return true;
        }
        printf("Error %s.\n", $stmt->error);
        return false;
    }
}
?>