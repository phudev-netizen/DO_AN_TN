<?php
class SanPham
{
    private $conn;

    // Thuộc tính
    public $MaSanPham;
    public $TenSanPham;
    public $MaLoaiSanPham;
    public $CPU;
    public $RAM;
    public $CardManHinh;
    public $SSD;
    public $ManHinh;
    public $MaMauSac;
    public $Gia;
    public $SoLuong;
    public $MoTa;
    public $HinhAnh;
    public $TrangThai;

    public $MaKhachHang;
    public $SoLuongTrongGioHang;

    // connect db
    public function __construct($database)
    {
        $this->conn = $database;
    }

    // Doc du lieu
    public function GetAllSanPham()
    {
        $query = "SELECT sp.*, ha.DuongDan FROM SanPham sp 
              JOIN hinhanh ha ON sp.MaSanPham = ha.MaSanPham WHERE ha.MacDinh = 1";
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt;
    }

    public function GetSanPhamById()
    {
        $query = "SELECT sp.*, ha.DuongDan FROM SanPham sp 
              JOIN hinhanh ha ON sp.MaSanPham = ha.MaSanPham
              WHERE ha.MacDinh = 1 AND sp.MaSanPham = ? LIMIT 1";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(1, $this->MaSanPham);
        $stmt->execute();

        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        if ($row) {
            $this->TenSanPham      = $row['TenSanPham'];
            $this->MaLoaiSanPham   = $row['MaLoaiSanPham'];
            $this->CPU             = $row['CPU'];
            $this->RAM             = $row['RAM'];
            $this->CardManHinh     = $row['CardManHinh'];
            $this->SSD             = $row['SSD'];
            $this->ManHinh         = $row['ManHinh'];
            $this->MaMauSac        = $row['MaMauSac'];
            $this->Gia             = $row['Gia'];
            $this->SoLuong         = $row['SoLuong'];
            $this->MoTa            = $row['MoTa'];
            $this->HinhAnh         = $row['DuongDan'];
            $this->TrangThai       = $row['TrangThai'];
        } else {
            echo "Sản phẩm không tồn tại.";
            return false;
        }
        unset($row);
    }

    public function GetSanPhamBySearch($searchTerm)
    {
        $query = "SELECT sp.*, ha.DuongDan
          FROM SanPham sp
          JOIN HinhAnh ha ON sp.MaSanPham = ha.MaSanPham
          WHERE ha.MacDinh = 1 AND (
            sp.TenSanPham LIKE ? 
            OR sp.MoTa LIKE ? 
            OR sp.CPU LIKE ? 
            OR sp.RAM LIKE ? 
            OR sp.CardManHinh LIKE ? 
            OR sp.SSD LIKE ?
          )";
        $stmt = $this->conn->prepare($query);
        $term = "%" . $searchTerm . "%";
        for ($i = 1; $i <= 6; $i++) {
            $stmt->bindValue($i, $term);
        }
        $stmt->execute();
        return $stmt;
    }

    /**
     * Tìm kiếm sản phẩm theo nhiều tiêu chí: tên, CPU, RAM, card, khoảng giá
     */
    public function SearchMultiCriteria($ten, $cpu, $ram, $card, $giaTu, $giaDen)
    {
        $sql = "SELECT sp.*, ha.DuongDan FROM SanPham sp
                JOIN HinhAnh ha ON sp.MaSanPham = ha.MaSanPham
                WHERE ha.MacDinh = 1";
        $params = [];

        if (!empty($ten)) {
            $sql .= " AND sp.TenSanPham LIKE :ten";
            $params[':ten'] = "%{$ten}%";
        }
        if (!empty($cpu)) {
            $sql .= " AND sp.CPU LIKE :cpu";
            $params[':cpu'] = "%{$cpu}%";
        }
        if (!empty($ram)) {
            $sql .= " AND sp.RAM LIKE :ram";
            $params[':ram'] = "%{$ram}%";
        }
        if (!empty($card)) {
            $sql .= " AND sp.CardManHinh LIKE :card";
            $params[':card'] = "%{$card}%";
        }
        // Khoảng giá
        $sql .= " AND sp.Gia BETWEEN :giaTu AND :giaDen";
        $params[':giaTu'] = $giaTu;
        $params[':giaDen'] = $giaDen;

        $stmt = $this->conn->prepare($sql);
        foreach ($params as $key => &$val) {
            $stmt->bindParam($key, $val);
        }
        $stmt->execute();
        return $stmt;
    }

    public function GetSanPhamByLoai()
    {
        $query = "SELECT sp.*, ha.DuongDan
                  FROM SanPham sp
                  JOIN hinhanh ha ON sp.MaSanPham = ha.MaSanPham
                  WHERE ha.MacDinh = 1 AND sp.MaLoaiSanPham = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(1, $this->MaLoaiSanPham);
        $stmt->execute();
        return $stmt;
    }

    public function GetSanPhamTrongHoaDon($mahoadon)
    {
        $query = "SELECT sp.*, ha.DuongDan
                  FROM SanPham sp
                  JOIN hinhanh ha ON sp.MaSanPham = ha.MaSanPham
                  JOIN ChiTietHoaDonBan cthd ON sp.MaSanPham = cthd.MaSanPham
                  WHERE ha.MacDinh = 1 AND cthd.MaHoaDonBan = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(1, $mahoadon);
        $stmt->execute();
        return $stmt;
    }

    public function GetSanPhamByGioHang()
    {
        $query = "SELECT sp.*, ha.DuongDan
                FROM sanpham sp
                JOIN giohang gh ON sp.MaSanPham = gh.MaSanPham
                JOIN hinhanh ha ON sp.MaSanPham = ha.MaSanPham
                WHERE ha.MacDinh = 1 AND gh.MaKhachHang = ?";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(1, $this->MaKhachHang);
        $stmt->execute();
        return $stmt;
    }

    public function AddSanPham()
    {
        $query = "INSERT INTO SanPham 
        (MaSanPham, TenSanPham, MaLoaiSanPham, CPU, RAM, CardManHinh, SSD, ManHinh, MaMauSac, Gia, SoLuong, MoTa, TrangThai) 
        VALUES 
        (:MaSanPham, :TenSanPham, :MaLoaiSanPham, :CPU, :RAM, :CardManHinh, :SSD, :ManHinh, :MaMauSac, :Gia, :SoLuong, :MoTa, :TrangThai)";

        $stmt = $this->conn->prepare($query);
        // Xử lý dữ liệu đầu vào
        foreach (['MaSanPham','TenSanPham','MaLoaiSanPham','CPU','RAM','CardManHinh','SSD','ManHinh','MaMauSac','Gia','SoLuong','MoTa','TrangThai'] as $field) {
            $this->$field = htmlspecialchars(strip_tags($this->$field));
        }
        
        $stmt->bindParam(':MaSanPham', $this->MaSanPham);
        $stmt->bindParam(':TenSanPham', $this->TenSanPham);
        $stmt->bindParam(':MaLoaiSanPham', $this->MaLoaiSanPham);
        $stmt->bindParam(':CPU', $this->CPU);
        $stmt->bindParam(':RAM', $this->RAM);
        $stmt->bindParam(':CardManHinh', $this->CardManHinh);
        $stmt->bindParam(':SSD', $this->SSD);
        $stmt->bindParam(':ManHinh', $this->ManHinh);
        $stmt->bindParam(':MaMauSac', $this->MaMauSac);
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

    public function UpdateSanPham()
    {
        $query = "UPDATE SanPham 
        SET TenSanPham = :TenSanPham,
            MaLoaiSanPham = :MaLoaiSanPham,
            CPU = :CPU,
            RAM = :RAM,
            CardManHinh = :CardManHinh,
            SSD = :SSD,
            ManHinh = :ManHinh,
            MaMauSac = :MaMauSac,
            Gia = :Gia,
            SoLuong = :SoLuong,
            MoTa = :MoTa,
            TrangThai = :TrangThai
        WHERE MaSanPham = :MaSanPham";

        $stmt = $this->conn->prepare($query);
        // Xử lý dữ liệu
        foreach (['TenSanPham','MaLoaiSanPham','CPU','RAM','CardManHinh','SSD','ManHinh','MaMauSac','Gia','SoLuong','MoTa','TrangThai','MaSanPham'] as $field) {
            $this->$field = htmlspecialchars(strip_tags($this->$field));
        }

        $stmt->bindParam(':TenSanPham', $this->TenSanPham);
        $stmt->bindParam(':MaLoaiSanPham', $this->MaLoaiSanPham);
        $stmt->bindParam(':CPU', $this->CPU);
        $stmt->bindParam(':RAM', $this->RAM);
        $stmt->bindParam(':CardManHinh', $this->CardManHinh);
        $stmt->bindParam(':SSD', $this->SSD);
        $stmt->bindParam(':ManHinh', $this->ManHinh);
        $stmt->bindParam(':MaMauSac', $this->MaMauSac);
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

    public function DeleteSanPham()
    {
        $query = "DELETE FROM SanPham WHERE MaSanPham = :MaSanPham";
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
