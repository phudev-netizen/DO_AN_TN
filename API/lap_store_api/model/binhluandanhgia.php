<?php
class binhluandanhgia{
    private $conn;

    //Thuoc tinh
    public $MaBinhLuan;
    public $MaKhachHang;
    public $MaSanPham;
    public $MaDonHang;
    public $SoSao;
    public $NoiDung;
    public $NgayDanhGia;
    public $TrangThai;
    //connect db
    
    public function __construct($database){
        $this->conn = $database;
    }

    //Doc dữ liệu

    public function GetAllBinhLuanDanhGia() {
        $query = "SELECT * FROM binhluandanhgia"; 
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt; // Trả về PDOStatement
    }
    public function GetBinhLuanDanhGiaById() {
        $query = "SELECT * FROM binhluandanhgia WHERE MaBinhLuan = ? LIMIT 1"; 
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(1,$this->MaBinhLuan);
        $stmt->execute();
        $row = $stmt->fetch(PDO::FETCH_ASSOC);

        $this->MaBinhLuan = $row['MaBinhLuan'];
        $this->MaKhachHang = $row['MaKhachHang'];
        $this->MaSanPham = $row['MaSanPham'];
        $this->MaDonHang = $row['MaDonHang'];
        $this->SoSao = $row['SoSao'];
        $this->NoiDung = $row['NoiDung'];
        $this->NgayDanhGia = $row['NgayDanhGia'];
        $this->TrangThai = $row['TrangThai'];
    } 

    public function AddBinhLuanDanhGia(){
        $query = "INSERT INTO binhluandanhgia SET MaBinhLuan=:MaBinhLuan, MaKhachHang=:MaKhachHang, MaSanPham=:MaSanPham, MaDonHang=:MaDonHang, SoSao=:SoSao, NoiDung=:NoiDung, NgayDanhGia=:NgayDanhGia, TrangThai=:TrangThai";

        $stmt = $this->conn->prepare($query);

        $this->MaBinhLuan = htmlspecialchars(strip_tags($this->MaBinhLuan));
        $this->MaKhachHang = htmlspecialchars(strip_tags($this->MaKhachHang));
        $this->MaSanPham = htmlspecialchars(strip_tags($this->MaSanPham));
        $this->MaDonHang = htmlspecialchars(strip_tags($this->MaDonHang));
        $this->SoSao = htmlspecialchars(strip_tags($this->SoSao));
        $this->NoiDung = htmlspecialchars(string: strip_tags($this->NoiDung));
        $this->NgayDanhGia = htmlspecialchars(string: strip_tags($this->NgayDanhGia));
        $this->TrangThai = htmlspecialchars(string: strip_tags($this->TrangThai));


        $stmt->bindParam(':MaBinhLuan',$this->MaBinhLuan);
        $stmt->bindParam(':MaKhachHang',$this->MaKhachHang);
        $stmt->bindParam(':MaSanPham',$this->MaSanPham);
        $stmt->bindParam(':MaDonHang',$this->MaDonHang);
        $stmt->bindParam(':SoSao',$this->SoSao);
        $stmt->bindParam(':NoiDung',$this->NoiDung);
        $stmt->bindParam(':NgayDanhGia',$this->NgayDanhGia);
        $stmt->bindParam(':TrangThai',$this->TrangThai);

        
        if($stmt->execute()){
            return true;
        }
        printf("Error %s.\n",$stmt->error);
        return false;
    }

    public function UpdateBinhLuanDanhGia() {
        $query = "UPDATE binhluandanhgia SET MaKhachHang=:MaKhachHang, MaSanPham=:MaSanPham, MaDonHang=:MaDonHang, SoSao=:SoSao, NoiDung=:NoiDung, NgayDanhGia=:NgayDanhGia, TrangThai=:TrangThai WHERE MaBinhLuan=:MaBinhLuan";

        $stmt = $this->conn->prepare($query);

        // Xử lý dữ liệu
        $this->MaBinhLuan = htmlspecialchars(strip_tags($this->MaBinhLuan));
        $this->MaKhachHang = htmlspecialchars(strip_tags($this->MaKhachHang));
        $this->MaSanPham = htmlspecialchars(strip_tags($this->MaSanPham));
        $this->MaDonHang = htmlspecialchars(strip_tags($this->MaDonHang));
        $this->SoSao = htmlspecialchars(strip_tags($this->SoSao));
        $this->NoiDung = htmlspecialchars(strip_tags($this->NoiDung));
        $this->NgayDanhGia = htmlspecialchars(strip_tags($this->NgayDanhGia));
        $this->TrangThai = htmlspecialchars(strip_tags($this->TrangThai));

        // Gán giá trị tham số
        $stmt->bindParam(':MaBinhLuan', $this->MaBinhLuan);
        $stmt->bindParam(':MaKhachHang', $this->MaKhachHang);
        $stmt->bindParam(':MaSanPham', $this->MaSanPham);
        $stmt->bindParam(':MaDonHang', $this->MaDonHang);
        $stmt->bindParam(':SoSao', $this->SoSao);
        $stmt->bindParam(':NoiDung', $this->NoiDung);
        $stmt->bindParam(':NgayDanhGia', $this->NgayDanhGia);
        $stmt->bindParam(':TrangThai', $this->TrangThai);

        if ($stmt->execute()) {
            return true;
        }
        printf("Error: %s.\n", $stmt->error);
        return false;
    }

    public function DeleteBinhLuanDanhGia() {
        $query = "DELETE FROM binhluandanhgia WHERE MaBinhLuan=:MaBinhLuan";

        $stmt = $this->conn->prepare($query);

        $this->MaBinhLuan = htmlspecialchars(strip_tags($this->MaBinhLuan));

        $stmt->bindParam(':MaBinhLuan', $this->MaBinhLuan);

        if ($stmt->execute()) {
            return true;
        }
        printf("Error: %s.\n", $stmt->error);
        return false;
    }
}
?>