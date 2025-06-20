-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 19, 2025 lúc 02:54 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlybanlaptop`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `binhluandanhgia`
--

CREATE TABLE `binhluandanhgia` (
  `MaBinhLuan` int(11) NOT NULL,
  `MaKhachHang` varchar(50) NOT NULL,
  `MaSanPham` int(11) NOT NULL,
  `MaHoaDonBan` int(11) NOT NULL,
  `SoSao` int(11) NOT NULL,
  `NoiDung` text DEFAULT NULL,
  `NgayDanhGia` datetime NOT NULL,
  `TrangThai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `binhluandanhgia`
--

INSERT INTO `binhluandanhgia` (`MaBinhLuan`, `MaKhachHang`, `MaSanPham`, `MaHoaDonBan`, `SoSao`, `NoiDung`, `NgayDanhGia`, `TrangThai`) VALUES
(1, '12', 3, 3, 5, 'hài lòng', '2025-06-11 11:33:05', 1),
(4, '12', 1, 0, 3, 'tạm ổn', '2025-06-11 00:00:00', 0),
(5, '12', 5, 0, 5, 'ổn nhe 10đ', '2025-06-11 00:00:00', 1),
(8, '12', 6, 0, 5, 'ok', '2025-06-11 00:00:00', 1),
(9, '12', 1, 0, 3, 'kê', '2025-06-11 00:00:00', 1),
(10, '12', 1, 0, 3, 'oki', '2025-06-11 11:58:05', 1),
(12, '', 0, 0, 0, '', '0000-00-00 00:00:00', 0),
(13, '12', 2, 0, 5, 'rất tốt ', '2025-06-17 11:04:29', 0),
(14, '12', 3, 0, 4, 'ổn nhe ', '2025-06-17 11:04:44', 1),
(15, '12', 1, 0, 5, 'ok nhe \n', '2025-06-17 11:56:18', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitiethoadonban`
--

CREATE TABLE `chitiethoadonban` (
  `MaChiTietHoaDonBan` int(11) NOT NULL,
  `MaHoaDonBan` int(11) DEFAULT NULL,
  `MaSanPham` int(11) DEFAULT NULL,
  `SoLuong` int(11) NOT NULL,
  `DonGia` int(20) DEFAULT NULL,
  `ThanhTien` int(20) GENERATED ALWAYS AS (`SoLuong` * `DonGia`) STORED,
  `GiamGia` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chitiethoadonban`
--

INSERT INTO `chitiethoadonban` (`MaChiTietHoaDonBan`, `MaHoaDonBan`, `MaSanPham`, `SoLuong`, `DonGia`, `GiamGia`) VALUES
(58, 40, 1, 1, 29990000, 0),
(59, 40, 2, 1, 8990000, 0),
(60, 41, 2, 1, 8990000, 0),
(61, 41, 1, 1, 29990000, 0),
(62, 42, 1, 1, 29990000, 0),
(63, 42, 4, 1, 17390000, 0),
(64, 43, 1, 1, 29990000, 0),
(65, 44, 2, 1, 8990000, 0),
(66, 45, 1, 1, 29990000, 0),
(67, 46, 6, 1, 73990000, 0),
(68, 47, 1, 4, 29990000, 0),
(69, 48, 1, 4, 29990000, 0),
(70, 49, 1, 1, 29990000, 0),
(71, 49, 2, 2, 8990000, 0),
(72, 50, 1, 1, 29990000, 0),
(73, 51, 1, 1, 29990000, 0),
(74, 52, 2, 2, 8990000, 0),
(75, 53, 1, 1, 29990000, 0),
(76, 54, 1, 1, 29990000, 0),
(77, 55, 1, 1, 29990000, 0),
(78, 56, 1, 10, 29990000, 0),
(79, 57, 1, 3, 29990000, 0),
(80, 58, 1, 4, 29990000, 0),
(81, 59, 7, 1, 73990000, 0),
(82, 60, 7, 1, 850000, 0),
(83, 61, 5, 1, 13399000, 0),
(84, 62, 1, 2, 29990000, 0),
(85, 63, 1, 1, 29990000, 0),
(86, 64, 6, 1, 73990000, 0),
(87, 64, 6, 1, 73990000, 0),
(88, 65, 5, 1, 13399000, 0),
(89, 67, 4, 1, 17390000, 0),
(90, 67, 2, 1, 8990000, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `diachi`
--

CREATE TABLE `diachi` (
  `MaDiaChi` int(11) NOT NULL,
  `ThongTinDiaChi` varchar(200) NOT NULL,
  `TenNguoiNhan` varchar(100) NOT NULL,
  `SoDienThoai` char(10) NOT NULL,
  `MaKhachHang` int(11) NOT NULL,
  `MacDinh` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `diachi`
--

INSERT INTO `diachi` (`MaDiaChi`, `ThongTinDiaChi`, `TenNguoiNhan`, `SoDienThoai`, `MaKhachHang`, `MacDinh`) VALUES
(1, 'Số 102 ấp Đông B', 'Anh B', '0901234545', 1, 0),
(8, 'Dong hoa chau thanh tien giang', 'Nguyen Thi Te Le', '0984581041', 6, 1),
(16, 'gfdgfd', 'gfdg', 'gfdgdf', 3, 0),
(17, 'gfgdf', 'gfdgf', 'gfdgf', 3, 0),
(18, 'hgfhfg', 'hghg', 'hghf', 3, 0),
(19, 'gfdgfd', 'gfdggfdgfd', 'gfggfd', 3, 1),
(26, '123 P10 Q10 Tp.HCM', 'Nguy thi Be Tu', '0987654387', 10, 1),
(33, 'dhhdhhd', 'khay', '0383184941', 11, 1),
(34, '35/39 bế văn cấm tân kiểng quận 7 Tp HCM', 'khầy bùa ', '0374897753', 12, 1),
(35, '35 bế văn cấm', 'phú', '0789579981', 12, 0),
(36, '31/15 ấp bình dương thị trấn cửu nghĩa tỉnh bình dương', 'gô', '0966040025', 12, 0),
(37, '35/39 phan văn trị ', 'phu', '0789579989', 13, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `giohang`
--

CREATE TABLE `giohang` (
  `MaGioHang` int(11) NOT NULL,
  `MaKhachHang` int(11) DEFAULT NULL,
  `MaSanPham` int(11) DEFAULT NULL,
  `SoLuong` int(11) NOT NULL,
  `TrangThai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `giohang`
--

INSERT INTO `giohang` (`MaGioHang`, `MaKhachHang`, `MaSanPham`, `SoLuong`, `TrangThai`) VALUES
(238, 12, 6, 1, 1),
(241, 12, 5, 1, 1),
(244, 12, 1, 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hinhanh`
--

CREATE TABLE `hinhanh` (
  `MaHinhAnh` int(11) NOT NULL,
  `DuongDan` varchar(500) DEFAULT NULL,
  `MacDinh` int(1) NOT NULL,
  `MaSanPham` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `hinhanh`
--

INSERT INTO `hinhanh` (`MaHinhAnh`, `DuongDan`, `MacDinh`, `MaSanPham`) VALUES
(1, 'https://i.postimg.cc/50S1K8vJ/asus-gaming-vivobook-k3605zf-rp634w-d59d42a7451f48d48cce32645f03f90c-1024x1024.webp', 0, 3),
(2, 'https://i.postimg.cc/cCQM92V9/03-fx507-1-8a446be6628445bc8586d1bec682e63a-1024x1024.webp', 1, 1),
(3, 'https://i.postimg.cc/Bb83SkkS/km095w-9e26278b10f642f18dd9855f30828c43-1024x1024.webp', 1, 4),
(4, 'https://i.postimg.cc/1zQSrgFQ/011vn-88f465ddba134eb2854752de1a3910f7-1024x1024.webp', 1, 2),
(10, 'https://i.postimg.cc/sfY5hB88/lp040w-124541e5ca0947f78a7483bc988e44b4-grande.gif', 0, 1),
(11, 'https://i.postimg.cc/J0pnQWjy/03-fx507-1-8a446be6628445bc8586d1bec682e63a-1024x1024.webp', 0, 1),
(12, 'https://i.postimg.cc/PrFJpTt9/w800-3-7b88f352fe074af08e16a4c76346e998-1024x1024.png', 0, 1),
(13, 'https://i.postimg.cc/sgQX5qxX/w800-2-60d5e4b1f652414596396e545c9f4f83-1024x1024.png', 0, 1),
(14, 'https://i.postimg.cc/zGsf77Hk/w800-1-0296ad913a7b44798b11e6e6ff965348-1024x1024.png', 0, 1),
(15, 'https://i.postimg.cc/brVyt9jk/rvn-laptop-msi-modern-14-c11m-011vn-2-cbbe9ef2b217457fa884468dd735dd22-8d6ccca29ff844fca4ebc312713a1.png', 0, 2),
(16, 'https://i.postimg.cc/Fs1sQ8jZ/rvn-laptop-msi-modern-14-c11m-011vn-3-61f7caf6e5db4867941fca086d789f06-bbf4fdc78bba4cfe98aa979b3cbb3.png', 0, 2),
(17, 'https://i.postimg.cc/cJ7dmSSs/asus-gaming-vivobook-k3605z-k3605v-product-photo-2k-bf5a83b87cf249fd8f86309daa5b170f-1024x1024.png', 1, 3),
(18, 'https://i.postimg.cc/FR7NQJSK/asus-gaming-vivobook-k3605z-k3605v-product-photo-2k-indie-black-08-1-099bd8beb0514f36b6e6f8511129a4.png', 0, 3),
(19, 'https://i.postimg.cc/BZP4YHsY/asus-gaming-vivobook-k3605z-k3605v-product-photo-2k-indie-black-12-ab9df3ac16b543d99b1ceeae874a1ade.png', 0, 3),
(20, 'https://i.postimg.cc/fy1SbcWb/vobook-14-oled-x1405v-m1405y-cool-silver-black-keyboard-07-fingerprint-776fe1c0bfb7488e90ef8f1737c06.png', 0, 4),
(21, 'https://i.postimg.cc/3rqv9TjH/vobook-14-oled-x1405v-m1405y-cool-silver-black-keyboard-08-fingerprint-23951ef8f70f40c2a1b505f2d862b.png', 0, 4),
(22, 'https://i.postimg.cc/RFyw35X9/vobook-14-oled-x1405v-m1405y-cool-silver-black-keyboard-13-fingerprint-d48fac641f1f4f0bbecd3ef4ef61e.png', 0, 4),
(24, 'https://i.postimg.cc/9F4gTML5/acer-swift-14-ai-sf14-51-4-488a9d6dcff043b28b1f1e653f2ba41c-ed494337af344334a071373c4e47cd97-1024x10.jpg', 1, 5),
(25, 'https://i.postimg.cc/JzZT6Fwp/acer-swift-14-ai-sf14-51-5-970de47c52834cc09d13c5ed5f6354c1-0c2123dac39e41ffb0d0ef07ab54044e-1024x10.jpg', 0, 5),
(26, 'https://i.postimg.cc/prmCDGYY/acer-swift-14-ai-sf14-51-9-fd6ee1f465484a5ca6a9386d8acca436-f50bfb57987c4716a843345d2ff14c6f-1024x10.jpg', 0, 5),
(27, 'https://i.postimg.cc/D0Rg5rxV/acer-swift-14-ai-sf14-51-7-9f47991c031741659222fd88d8ce897c-0778465fc0ad4e4db6e49afd1551f634-1024x10.jpg', 0, 5),
(28, 'https://i.postimg.cc/sDNX6nPG/ava-9f543d3a40a040739ce1929c13bf6813-1024x1024.png', 1, 6),
(29, 'https://i.postimg.cc/VvNf4MdF/1024-1-984a2f3a7c2f4b798dc0cd3bf6d7e098-1024x1024.png', 0, 6),
(30, 'https://i.postimg.cc/jdmxQDKz/1024-2-8a469d3acdca4e22ad5011d985339930-1024x1024.png', 0, 6),
(31, 'https://i.postimg.cc/9fjcKsD0/1024-4-70a6077595534ce19eede38ef2e15818-1024x1024.png', 0, 6),
(32, 'https://i.postimg.cc/k5F7mwSc/msi-stealth16-ai-ultra9-laptop-blue-5b0240e0268945fb8e14a429f4033571-1024x1024.png', 0, 6),
(33, 'https://i.postimg.cc/Y0TrnDHj/120-73258-ram-laptop-kingston-kvr48s40bd8-32-32gb-1x32gb-ddr5-4800mhz-1.jpg', 1, 7),
(34, 'https://i.postimg.cc/0NF81ddD/55947-ram-laptop-kingston-kvr32s22s8-16.png', 0, 7),
(35, 'https://i.postimg.cc/wMt12xHz/R.png', 0, 7),
(36, 'https://i.postimg.cc/0NF81ddD/55947-ram-laptop-kingston-kvr32s22s8-16.png', 1, 8),
(37, 'https://i.postimg.cc/vZrYhvSH/55947-ram-laptop-kingston-kvr32s22s8-16.png', 0, 8),
(38, 'https://i.postimg.cc/j59m5tMF/ssd-nvme-1tb-tot-nhat-2.jpg', 1, 9),
(39, 'https://i.postimg.cc/mZvKWZzc/ny3swjwftnucimrgapdqud-27515f4843bc46e5a921d9b0f5dcc693.webp', 0, 9),
(40, 'https://i.postimg.cc/yxnts74t/sd128.jpg', 1, 10),
(41, 'https://i.postimg.cc/vBzDF2XT/sd1282.jpg', 0, 10),
(42, 'https://i.postimg.cc/9MJXNRFq/SD5-SG2-128-G-1052-E-lg.jpg', 0, 10),
(46, 'https://i.postimg.cc/gj1Mx8vf/Lexar.png', 1, 11),
(47, 'https://i.postimg.cc/s2hWTjSB/ban-phim-co-khong-day-e-dra-ek368-L.jpg', 1, 12),
(48, 'https://i.postimg.cc/prgnt3KG/ziyoulang.png', 0, 12);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadonban`
--

CREATE TABLE `hoadonban` (
  `MaHoaDonBan` int(11) NOT NULL,
  `MaKhachHang` int(11) DEFAULT NULL,
  `NgayDatHang` date NOT NULL,
  `MaDiaChi` int(11) DEFAULT NULL,
  `TongTien` int(11) DEFAULT NULL,
  `PhuongThucThanhToan` varchar(50) NOT NULL,
  `TrangThai` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `hoadonban`
--

INSERT INTO `hoadonban` (`MaHoaDonBan`, `MaKhachHang`, `NgayDatHang`, `MaDiaChi`, `TongTien`, `PhuongThucThanhToan`, `TrangThai`) VALUES
(40, 11, '2025-01-17', 33, 39010000, 'Thanh toán khi nhận hàng', '4'),
(41, 11, '2025-01-17', 33, 39010000, 'Thanh toán khi nhận hàng', '6'),
(42, 11, '2025-01-17', 33, 47410000, 'Thanh toán khi nhận hàng', '6'),
(43, 11, '2025-01-17', 33, 30020000, 'Thanh toán khi nhận hàng', '4'),
(44, 11, '2025-01-17', 33, 9020000, 'Thanh toán khi nhận hàng', '4'),
(45, 1, '2025-05-09', 1, 30020000, 'Thanh toán khi nhận hàng', '6'),
(46, 12, '2025-05-09', 34, 74020000, 'Thanh toán khi nhận hàng', '6'),
(47, 12, '2025-05-10', 34, 119990000, 'Chuyển khoản ngân hàng', '6'),
(48, 12, '2025-05-10', 34, 119990000, 'Chuyển khoản ngân hàng', '4'),
(49, 12, '2025-05-10', 34, 48000000, 'Chuyển khoản ngân hàng', '6'),
(50, 12, '2025-05-10', 34, 30020000, 'Thanh toán khi nhận hàng', '6'),
(51, 12, '2025-05-12', 34, 30020000, 'Thanh toán khi nhận hàng', '6'),
(52, 12, '2025-05-12', 34, 18010000, 'Chuyển khoản ngân hàng', '6'),
(53, 12, '2025-05-12', 34, 30020000, 'Chuyển khoản ngân hàng', '6'),
(54, 12, '2025-05-12', 34, 30020000, 'Thanh toán khi nhận hàng', '6'),
(55, 12, '2025-05-12', 34, 30020000, 'Chuyển khoản ngân hàng', '6'),
(56, 12, '2025-05-12', 34, 299930000, 'Chuyển khoản ngân hàng', '6'),
(57, 12, '2025-05-14', 34, 90000000, 'Thanh toán khi nhận hàng', '6'),
(58, 12, '2025-05-14', 34, 119990000, 'Chuyển khoản ngân hàng', '6'),
(59, 12, '2025-05-25', 34, 74020000, 'Thanh toán khi nhận hàng', '6'),
(60, 12, '2025-06-09', 34, 880000, 'Chuyển khoản ngân hàng', '6'),
(61, 12, '2025-06-10', 34, 13429000, 'Thanh toán khi nhận hàng', '3'),
(62, 12, '2025-06-10', 34, 60010000, 'Thanh toán khi nhận hàng', '6'),
(63, 12, '2025-06-11', 34, 30020000, 'Chuyển khoản ngân hàng', '6'),
(64, 12, '2025-06-12', 35, 74020000, 'Thanh toán khi nhận hàng', '6'),
(65, 12, '2025-06-12', 34, 13429000, 'Thanh toán khi nhận hàng', '6'),
(66, 12, '2025-06-17', 34, 13429000, 'Thanh toán khi nhận hàng', '4'),
(67, 13, '2025-06-18', 37, 26410000, 'Thanh toán khi nhận hàng', '4');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `MaKhachHang` int(11) NOT NULL,
  `HoTen` varchar(100) NOT NULL,
  `GioiTinh` char(3) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `Email` varchar(100) NOT NULL,
  `SoDienThoai` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`MaKhachHang`, `HoTen`, `GioiTinh`, `NgaySinh`, `Email`, `SoDienThoai`) VALUES
(1, 'Nguyễn Văn P', 'Nam', '1990-05-11', 'admin@gmail.com', '0374897753'),
(3, 'Nguyễn Thị B', 'Nữ', '1992-01-01', 'nguyenthib@gmail.com', '1234567890'),
(5, 'lamvanc', 'Nam', '1997-03-05', 'lamvanc@gmail.com', '0939049151'),
(6, 'nguyenthitele', '', '0000-00-00', 'tele@gmail.com', ''),
(7, 'nguyenvana', '', '0000-00-00', 'vn@gmail.com', ''),
(9, 'nguyenvanaa', '', '0000-00-00', 'gfgfdg', ''),
(10, 'nguyenthibetu', 'Nữ', '2004-06-05', 'betubungbu@gmail.com', '0904853621'),
(11, 'khaylo', 'Nam', '2004-09-23', 'khaylohuynh23092004@gmail.com', '0383184941'),
(12, 'khầy bùa ', 'Nam', '2000-12-19', 'khaybua@gmail.com', '0374897743'),
(13, 'phu', '', '0000-00-00', 'phu77@gmail.com', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaisanpham`
--

CREATE TABLE `loaisanpham` (
  `MaLoai` int(11) NOT NULL,
  `TenLoai` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `loaisanpham`
--

INSERT INTO `loaisanpham` (`MaLoai`, `TenLoai`) VALUES
(1, 'Laptop văn phòng'),
(2, 'Laptop Gaming'),
(3, 'Phụ kiện RAM'),
(4, 'Bàn phím');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `mausac`
--

CREATE TABLE `mausac` (
  `MaMauSac` int(11) NOT NULL,
  `TenMauSac` varchar(20) DEFAULT NULL,
  `MaSanPham` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `mausac`
--

INSERT INTO `mausac` (`MaMauSac`, `TenMauSac`, `MaSanPham`) VALUES
(1, 'Đen', 1),
(2, 'Trắng', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `MaSanPham` int(11) NOT NULL,
  `TenSanPham` varchar(100) NOT NULL,
  `MaLoaiSanPham` int(11) DEFAULT NULL,
  `CPU` char(50) DEFAULT NULL,
  `RAM` varchar(10) DEFAULT NULL,
  `CardManHinh` varchar(20) DEFAULT NULL,
  `SSD` varchar(20) DEFAULT NULL,
  `ManHinh` char(50) DEFAULT NULL,
  `MaMauSac` int(11) DEFAULT NULL,
  `Gia` int(11) DEFAULT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `MoTa` text DEFAULT NULL,
  `TrangThai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`MaSanPham`, `TenSanPham`, `MaLoaiSanPham`, `CPU`, `RAM`, `CardManHinh`, `SSD`, `ManHinh`, `MaMauSac`, `Gia`, `SoLuong`, `MoTa`, `TrangThai`) VALUES
(1, 'Laptop gaming ASUS TUF Gaming F15 FX507VV LP304W', 2, 'INTEL CORE I9 12900K', 'RAM 16 GB', 'RTX 4060', 'SSD 512 GB', '1920x1080', 2, 29990000, 13, 'Laptop gaming RTX 40 Series mạnh nhất cho học sinh, sinh viên\n\nHoạt động bền bỉ, làm chủ trận chiến trên chiếc laptop ASUS TUF Gaming F15 (2023), sử dụng bộ vi xử lý Intel Core™ i7-13620H với sự kết hợp giữa lõi hiệu năng (P-core) và lõi tiết kiệm điện (E-core) cân bằng sức mạnh chơi game và khả năng đa nhiệm tuyệt vời. ASUS TUF Gaming F15 còn được trang bị đồ họa NVIDIA GeForce RTX™ 40 series mới nhất hỗ trợ G-SYNC® và có TGP tối đa là 140 W với Dynamic Boost. Sự kết hợp giữa CPU mạnh mẽ và GPU hiệu suất cao mang lại khả năng chơi game đáng kinh ngạc trên cả các tựa game mới nhất. Màn hình FHD 144Hz cho trải nghiệm gaming mượt mà, tận hưởng không gian giải trí đắm chìm. ', 1),
(2, 'Laptop MSI Modern 14 C11M 011VN', 1, 'Intel Core i3-1115G4 (up to 4.1Ghz, 6MB)', 'RAM 8 GB', 'Intel UHD Graphics', 'SSD 512 GB', '1920x1080', 1, 8990000, 19, 'MSI Modern 14 C11M-011VN nằm trong phân khúc laptop 9 triệu được trang bị màn hình rộng 14 inch có độ phân giải Full HD cùng tần số quét ở mức cơ bản 60Hz. Màn hình IPS này sẽ đủ sắc nét để người dùng có thể thoải mái sử dụng trong học tập, nghiên cứu và làm việc.', 1),
(3, 'Laptop gaming ASUS Vivobook 16X K3605ZF RP634W', 2, 'AMD R77700M', 'RAM 16 GB', 'RTX 3050', 'SSD 1TB', '1920x1080', 1, 17290000, 0, 'Bên trong ASUS Gaming Vivobook K3605ZF-RP634W là bộ vi xử lý Intel Core i5-12500H với 4 nhân hiệu năng cao và 8 nhân tiết kiệm điện, cùng với bộ nhớ đệm 18MB, đạt tốc độ tối đa lên đến 4.5 GHz. Cấu hình này không chỉ đáp ứng tốt các tựa game phổ biến mà còn tối ưu cho các tác vụ đa nhiệm và xử lý đồ họa nặng.', 1),
(4, 'Laptop ASUS Vivobook 14 OLED A1405VA KM095W', 1, 'INTEL CORE I7 12500H', 'RAM 8 GB', 'INTEL ARISXE', 'SSD 1TB', '1920x1080', 1, 17390000, 3, 'Tỏa sáng với cả thế giới cùng ASUS Vivobook 14 OLED mạnh mẽ, chiếc laptop tích hợp nhiều tính năng với màn hình OLED rực rỡ, gam màu DCI-P3 đẳng cấp điện ảnh. Mọi thứ trở nên dễ dàng hơn nhờ những tiện ích thân thiện với người dùng bao gồm bản lề duỗi thẳng 180°, nắp che webcam vật lý và các phím chức năng chuyên dụng. Bảo vệ sức khỏe an toàn với ASUS kháng khuẩn Guard Plus trên các bề mặt thường xuyên chạm vào. Bắt đầu ngày mới đầy hứng khởi với ASUS Vivobook 14 OLED!', 1),
(5, 'Laptop Acer Swift 14 AI SF14 51 53P9', 1, 'Intel® Core™ Ultra 5 Processor 226V', 'RAM 16GB', 'Intel® Arc™ Graphic', 'SSD 1TB', '14&amp;quot; 3K (2880 x 1800) OLED', 1, 13399000, 7, 'Acer Swift 14 AI SF14 51 53P9 tiếp tục thừa hưởng ngôn ngữ thiết kế tối giản, thanh lịch của dòng Swift. Vỏ máy được chế tác từ hợp kim nhôm cao cấp, mang đến vẻ ngoài sang trọng và độ bền bỉ cao. Với độ mỏng ấn tượng chỉ 15.95mm và trọng lượng siêu nhẹ 1.26kg, chiếc laptop ai này là người bạn đồng hành lý tưởng cho những người thường xuyên di chuyển. ', 1),
(6, 'Laptop gaming MSI Stealth 16 AI Studio A1VHG 241VN', 2, 'Intel® Core™ Ultra 9 processor 185H with Intel® AI', 'RAM: 64GB', 'NVIDIA® GeForce RTX™', 'SSD: 1TB', '16\" QHD+(2560x1600), 240Hz', 1, 73990000, 10, 'Chiếc laptop gaming được trang bị con chip Intel Core Ultra 9 - 185H với 16 lõi, 22 luồng kết hợp với VGA NVIDIA GeForece RTX 4070 giúp trải nghiệm chơi các game AAA và render hình ảnh, video một cách mượt mà không xảy ra hiện tượng giật lag. \r\n\r\n', 1),
(7, 'RAM Laptop DDR4 8GB Bus 3200MHz', 3, 'ADM 5570', 'RAM: 16GB', 'NVIDIA® GeForce RTX™', 'BUS:3200MHz', 'Kích thước 13.8cm', 1, 850000, 15, 'RAM Laptop DDR4 8GB Bus 3200MHz – nâng cấp hiệu năng, chạy đa nhiệm mượt mà, tiết kiệm điện, tương thích nhiều dòng máy.\"', 1),
(8, 'RAM laptop Kingston CL42  DDR5  (KVR52S42BS8-16)', 3, 'AMD Ryzen 3', 'RAM:16GB', 'NVIDIA® GeForce RTX™', 'BUS :5200MHz', 'Kích thước 13.8cm', 2, 1249000, 15, 'Nâng cấp hiệu suất cho chiếc laptop của bạn với RAM Laptop DDR4 8GB Bus 3200MHz – giải pháp tối ưu giúp tăng tốc độ xử lý, cải thiện đa nhiệm và mang đến trải nghiệm mượt mà hơn trong công việc lẫn giải trí.', 1),
(9, 'SSD KingSton ', 3, 'AMD Ryzen7', 'RAM:2TB', 'RTX 3060', 'BUS:3200MHz', 'Kích thước 8cm', 1, 1099000, 30, 'SSD laptop lưu trữ nhiều dữ liệu còn nhanh nữa chứ ', 1),
(10, 'SSD SanDisk ', 3, 'AMD Ryzen 5', 'RAM:128GB', 'RTX 7090', 'BUS: 2800MHz', 'Kích thước 8cm', 1, 900000, 50, 'SSD Laptop 128GB – tăng tốc khởi động máy, truy xuất dữ liệu nhanh, nhỏ gọn, tiết kiệm điện, phù hợp nâng cấp cho laptop cũ.', 1),
(11, 'RAM laptop Lexar LD4AS016G-B3200GSST  DDR4  (LD4AS016G-B3200GSST (L))', 3, 'AMD Ryzen 3', 'Ram:16GB', 'RTX 3060', 'BUS:3200MHz', 'Kích thước 8cm', 2, 1090000, 25, 'Ram Laptop Lexar DDR4 16G (1x 16Gb) 3200Mhz hiệu năng cao đã được chọn lọc và hoàn toàn đáng tin cậy. Ram Lexar 32GB dành cho laptop với độ trễ thấp, mang lại một tốc độ đáp ứng gần như tức thì cho mọi ứng dụng, tối ưu trải nghiệm của bạn với hiệu năng cải thiện đáng kể.\nLắp đặt dễ dàng, ít tiêu tốn điện năng \nRam Laptop Lexar DDR4 16G (1x 16Gb) 3200Mhz cho phép bạn lắp đặt ngay mà không thông qua trình cài đặt phức tạp, dễ dàng nâng cấp cấu hình của chiếc máy tính ngay lập tức để tận hưởng hiệu năng mong muốn. Quá trình kiểm tra nghiêm ngặt đảm bảo độ tin cậy cho Ram Lexar DDR4.', 1),
(12, 'Bàn phím', 4, 'AMD', '8GB', 'GTX 1080', '512GB', '25cm', 2, 1800000, 32, 'thích hợp mọi loại máy', 1),
(14, '', 0, '', '', '', '', '', 0, 0, 0, '', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `TenTaiKhoan` varchar(50) NOT NULL,
  `MaKhachHang` int(11) NOT NULL,
  `MatKhau` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `LoaiTaiKhoan` int(1) NOT NULL,
  `TrangThai` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`TenTaiKhoan`, `MaKhachHang`, `MatKhau`, `LoaiTaiKhoan`, `TrangThai`) VALUES
('admin', 1, '1', 1, 1),
('khay', 12, '123@4567', 0, 1),
('khaylodepzai', 11, 'khay22444', 0, 1),
('lamvanc', 5, '123', 0, 1),
('nguyenthibetu', 10, '12345678', 0, 1),
('nguyenthitele', 6, '12345678', 0, 1),
('nguyenvanaa', 9, '23092004', 0, 1),
('phu', 13, '123@4567', 0, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `yeuthich`
--

CREATE TABLE `yeuthich` (
  `ID` int(11) NOT NULL,
  `MaKhachHang` int(11) NOT NULL,
  `MaSanPham` int(11) NOT NULL,
  `NgayYeuThich` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `yeuthich`
--

INSERT INTO `yeuthich` (`ID`, `MaKhachHang`, `MaSanPham`, `NgayYeuThich`) VALUES
(1, 10, 3, '2025-06-19 11:00:21'),
(2, 12, 8, '2025-06-19 11:00:27');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `binhluandanhgia`
--
ALTER TABLE `binhluandanhgia`
  ADD PRIMARY KEY (`MaBinhLuan`);

--
-- Chỉ mục cho bảng `chitiethoadonban`
--
ALTER TABLE `chitiethoadonban`
  ADD PRIMARY KEY (`MaChiTietHoaDonBan`),
  ADD KEY `FK_ChiTietHoaDonBan_HoaDonBan` (`MaHoaDonBan`),
  ADD KEY `FK_ChiTietHoaDonBan_SanPham` (`MaSanPham`);

--
-- Chỉ mục cho bảng `diachi`
--
ALTER TABLE `diachi`
  ADD PRIMARY KEY (`MaDiaChi`),
  ADD KEY `fk_diachi_khachhang` (`MaKhachHang`);

--
-- Chỉ mục cho bảng `giohang`
--
ALTER TABLE `giohang`
  ADD PRIMARY KEY (`MaGioHang`),
  ADD KEY `FK_GioHang_KhachHang` (`MaKhachHang`),
  ADD KEY `FK_GioHang_SanPham` (`MaSanPham`);

--
-- Chỉ mục cho bảng `hinhanh`
--
ALTER TABLE `hinhanh`
  ADD PRIMARY KEY (`MaHinhAnh`),
  ADD KEY `fk_ma_sanpham` (`MaSanPham`);

--
-- Chỉ mục cho bảng `hoadonban`
--
ALTER TABLE `hoadonban`
  ADD PRIMARY KEY (`MaHoaDonBan`),
  ADD KEY `FK_HoaDonBan_KhachHang` (`MaKhachHang`),
  ADD KEY `FK_HoaDonBan_DiaChi` (`MaDiaChi`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MaKhachHang`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Chỉ mục cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD PRIMARY KEY (`MaLoai`);

--
-- Chỉ mục cho bảng `mausac`
--
ALTER TABLE `mausac`
  ADD PRIMARY KEY (`MaMauSac`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`MaSanPham`),
  ADD KEY `FK_SanPham_LoaiSanPham` (`MaLoaiSanPham`),
  ADD KEY `FK_SanPham_MauSac` (`MaMauSac`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`TenTaiKhoan`),
  ADD KEY `FK_TaiKhoan_KhachHang` (`MaKhachHang`);

--
-- Chỉ mục cho bảng `yeuthich`
--
ALTER TABLE `yeuthich`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `MaKhachHang` (`MaKhachHang`,`MaSanPham`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `binhluandanhgia`
--
ALTER TABLE `binhluandanhgia`
  MODIFY `MaBinhLuan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `chitiethoadonban`
--
ALTER TABLE `chitiethoadonban`
  MODIFY `MaChiTietHoaDonBan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;

--
-- AUTO_INCREMENT cho bảng `diachi`
--
ALTER TABLE `diachi`
  MODIFY `MaDiaChi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT cho bảng `giohang`
--
ALTER TABLE `giohang`
  MODIFY `MaGioHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=245;

--
-- AUTO_INCREMENT cho bảng `hinhanh`
--
ALTER TABLE `hinhanh`
  MODIFY `MaHinhAnh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT cho bảng `hoadonban`
--
ALTER TABLE `hoadonban`
  MODIFY `MaHoaDonBan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MaKhachHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  MODIFY `MaLoai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `mausac`
--
ALTER TABLE `mausac`
  MODIFY `MaMauSac` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `MaSanPham` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `yeuthich`
--
ALTER TABLE `yeuthich`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `diachi`
--
ALTER TABLE `diachi`
  ADD CONSTRAINT `fk_diachi_khachhang` FOREIGN KEY (`MaKhachHang`) REFERENCES `khachhang` (`MaKhachHang`);

--
-- Các ràng buộc cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `FK_TaiKhoan_KhachHang` FOREIGN KEY (`MaKhachHang`) REFERENCES `khachhang` (`MaKhachHang`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
