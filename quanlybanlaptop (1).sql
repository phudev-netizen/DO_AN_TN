-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th7 13, 2025 lúc 01:55 PM
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
(13, '12', 2, 0, 5, 'rất tốt ', '2025-06-17 11:04:29', 1),
(14, '12', 3, 0, 4, 'ổn nhe ', '2025-06-17 11:04:44', 1),
(15, '12', 1, 0, 5, 'ok nhe \n', '2025-06-17 11:56:18', 0),
(16, '14', 2, 0, 5, 'ổn nhe ', '2025-06-30 09:54:43', 1),
(19, '14', 4, 92, 4, 'cũng tạm ổn nhe ', '2025-06-30 10:06:28', 1),
(20, '12', 3, 223, 2, 'thấy không ổn lắm nhe ', '2025-07-12 00:31:23', 1);

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
(90, 67, 2, 1, 8990000, 0),
(91, 68, 6, 1, 73990000, 0),
(92, 69, 5, 1, 13399000, 0),
(93, 70, 2, 1, 8990000, 0),
(94, 71, 2, 1, 8990000, 0),
(95, 72, 2, 1, 8990000, 0),
(96, 73, 2, 1, 8990000, 0),
(97, 74, 2, 1, 8990000, 0),
(98, 75, 2, 1, 8990000, 0),
(99, 77, 2, 1, 8990000, 0),
(100, 79, 1, 6, 29990000, 0),
(101, 80, 5, 1, 13399000, 0),
(102, 82, 2, 1, 8990000, 0),
(103, 84, 5, 1, 13399000, 0),
(104, 85, 4, 1, 17390000, 0),
(105, 86, 4, 1, 17390000, 0),
(106, 87, 6, 1, 73990000, 0),
(107, 88, 2, 1, 8990000, 0),
(108, 89, 2, 2, 8990000, 0),
(109, 90, 2, 1, 8990000, 0),
(110, 91, 2, 1, 8099000, 0),
(111, 92, 4, 1, 17000000, 0),
(112, 93, 5, 1, 13399000, 3349750),
(113, 95, 2, 1, 8099000, 1214850),
(114, 96, 2, 1, 8099000, 1214850),
(115, 98, 1, 1, 29790000, 0),
(116, 100, 2, 1, 8099000, 1214850),
(117, 102, 2, 1, 8099000, 809900),
(118, 164, 1, 1, 29790000, 48907672),
(119, 215, 2, 1, 8099000, 0),
(120, 216, 1, 2, 29790000, 17874000),
(121, 216, 4, 1, 17000000, 0),
(122, 217, 2, 1, 8099000, 0),
(123, 218, 1, 1, 29790000, 8937000),
(124, 219, 1, 1, 29790000, 8937000),
(125, 220, 1, 1, 29790000, 8937000),
(126, 221, 1, 1, 29790000, 8937000),
(127, 222, 2, 1, 8099000, 0),
(128, 223, 3, 1, 17290000, 3458000),
(129, 224, 5, 1, 13399000, 0),
(130, 225, 1, 1, 29790000, 8937000),
(131, 225, 2, 2, 8099000, 0),
(132, 226, 7, 1, 10690000, 0),
(133, 227, 6, 1, 73990000, 57747673),
(134, 228, 2, 1, 8099000, 0),
(135, 229, 9, 1, 17690000, 3538000),
(136, 230, 7, 2, 10690000, 0),
(137, 230, 5, 1, 13399000, 0),
(138, 231, 2, 1, 8099000, 0),
(139, 232, 2, 1, 8099000, 0),
(140, 233, 1, 1, 29790000, 8937000),
(141, 234, 2, 3, 8099000, 0),
(142, 235, 2, 1, 8099000, 0),
(143, 236, 2, 1, 8099000, 0),
(144, 237, 1, 1, 29790000, 8937000),
(145, 237, 2, 1, 8099000, 0),
(146, 238, 2, 1, 8099000, 0),
(147, 239, 2, 1, 8099000, 0),
(148, 240, 1, 1, 29790000, 8937000),
(149, 241, 7, 1, 10690000, 0),
(150, 242, 8, 1, 24205000, 0),
(151, 243, 7, 1, 10690000, 0),
(152, 244, 17, 1, 10990000, 0),
(153, 245, 2, 1, 8099000, 0),
(154, 246, 10, 1, 22490000, 4947800),
(155, 247, 5, 1, 13399000, 0),
(156, 248, 1, 1, 29790000, 8937000),
(157, 249, 2, 1, 8099000, 0),
(158, 250, 15, 1, 1099000, 0),
(159, 251, 8, 1, 24205000, 0),
(160, 252, 1, 1, 29790000, 8937000),
(161, 252, 5, 1, 13399000, 0),
(162, 252, 3, 1, 17290000, 3458000),
(163, 252, 8, 1, 24205000, 0),
(164, 252, 1, 1, 29790000, 8937000),
(165, 253, 6, 1, 73990000, 57747673),
(166, 254, 2, 5, 8099000, 0),
(167, 255, 18, 1, 17900000, 0),
(168, 256, 1, 2, 29790000, 17874000),
(169, 257, 10, 1, 22490000, 4947800),
(170, 258, 13, 2, 18500000, 33300000),
(171, 259, 15, 1, 20099000, 0),
(172, 260, 7, 1, 10690000, 0),
(173, 261, 17, 1, 10990000, 0),
(174, 262, 1, 1, 29790000, 8937000),
(175, 263, 2, 2, 18099000, 0),
(176, 264, 2, 2, 18099000, 0),
(177, 264, 5, 1, 13399000, 0),
(178, 265, 2, 1, 18099000, 0),
(179, 266, 2, 1, 18099000, 0),
(180, 267, 2, 1, 18099000, 0),
(181, 268, 2, 1, 18099000, 0),
(182, 269, 18, 1, 17900000, 0),
(183, 269, 18, 1, 17900000, 0);

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
(37, '35/39 phan văn trị ', 'phu', '0789579989', 13, 0),
(38, 'phường nhị quý tỉnh đồng tháp', 'ko có tên', '0739945182', 14, 1),
(39, '123 phường nhị quý tỉnh đồng tháp', 'nguyênzlex', '0789579981', 16, 1),
(40, 'phường nhị quý ', 'snn', '0377897753', 16, 0),
(41, 'tỉnh lộ 10 phường bình trị đông', 'văn c', '0345775389', 5, 1),
(42, 'phường tân hưng tphcm', 'phú thanh', '0932821598', 13, 0),
(43, '35 phường tân hưng tphcm', 'phú', '0933754633', 13, 0),
(44, 'phường tân kiểng quận 7', 'gô', '0374894447', 13, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `email_otp`
--

CREATE TABLE `email_otp` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `otp_code` varchar(10) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `expires_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(276, 16, 2, 1, 1),
(279, 5, 1, 1, 1),
(280, 5, 2, 1, 1),
(281, 5, 5, 1, 1),
(282, 5, 7, 2, 1),
(285, 11, 2, 1, 1),
(286, 11, 6, 1, 1),
(287, 11, 7, 1, 1),
(288, 11, 5, 1, 1),
(289, 11, 17, 1, 1),
(297, 12, 1, 1, 1),
(298, 12, 17, 1, 1),
(299, 12, 11, 3, 1),
(301, 13, 8, 6, 1),
(309, 13, 1, 1, 1);

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
(33, 'https://i.postimg.cc/MTb0mVcJ/acer-aspire-go-ag15-1.jpg', 1, 7),
(34, 'https://i.postimg.cc/3NtgPhCL/acer-aspire-go-ag15-2.jpg', 0, 7),
(35, 'https://i.postimg.cc/Z5BrD19c/acer-aspire-go-ag15-3.jpg', 0, 7),
(36, 'https://i.postimg.cc/fyRd7FnY/acer-aspire-go-ag15-4.jpg', 0, 7),
(37, 'https://i.postimg.cc/rwrr9xbZ/acer-nitro-v-15-1.jpg', 1, 8),
(38, 'https://i.postimg.cc/D00W4XLg/acer-nitro-v-15-2.jpg', 0, 8),
(39, 'https://i.postimg.cc/3JPyWWRX/acer-nitro-v-15-3.jpg', 0, 8),
(40, 'https://i.postimg.cc/LsVhyTVD/acer-nitro-v-15-4.jpg', 0, 8),
(41, 'https://i.postimg.cc/KcWjs4t6/hp-victus-15-1.jpg', 1, 9),
(42, 'https://i.postimg.cc/mgYrDwK4/hp-victus-15-2.jpg', 0, 9),
(44, 'https://i.postimg.cc/Y9srVyL6/hp-victus-15-3.jpg', 0, 9),
(45, 'https://i.postimg.cc/VkWbTmfX/hp-victus-15-4.jpg', 0, 9),
(46, 'https://i.postimg.cc/BZ7pY5YJ/dell-inspiron-15-1.jpg', 1, 10),
(47, 'https://i.postimg.cc/jSPhSpsj/dell-inspiron-15-2.jpg', 0, 10),
(48, 'https://i.postimg.cc/85KRYDbZ/dell-inspiron-15-3.jpg', 0, 10),
(49, 'https://i.postimg.cc/dVP8p58c/dell-inspiron-15-4.jpg', 0, 10),
(50, 'https://i.postimg.cc/7LX0CDjq/msi-thin-15-1.jpg', 1, 11),
(51, 'https://i.postimg.cc/2SD0hk9W/msi-thin-15-2.jpg', 0, 11),
(52, 'https://i.postimg.cc/B6Xp4Xbr/msi-thin-15-3.jpg', 0, 11),
(53, 'https://i.postimg.cc/ZnNLv2rj/msi-thin-15-4.jpg', 0, 11),
(57, 'https://i.postimg.cc/Y0TrnDHj/120-73258-ram-laptop-kingston-kvr48s40bd8-32-32gb-1x32gb-ddr5-4800mhz-1.jpg', 1, 12),
(58, 'https://i.postimg.cc/0NF81ddD/55947-ram-laptop-kingston-kvr32s22s8-16.png', 0, 12),
(59, 'https://i.postimg.cc/wMt12xHz/R.png', 0, 12),
(60, 'https://i.postimg.cc/Sx7B2bKB/Lenovo-LOQ-Gaming-15-IAX9-i5-12450-H-1.jpg', 1, 13),
(61, 'https://i.postimg.cc/h4k62tQF/Lenovo-LOQ-Gaming-15-IAX9-i5-12450-H-2.jpg', 0, 13),
(62, 'https://i.postimg.cc/yY05tC0D/Lenovo-LOQ-Gaming-15-IAX9-i5-12450-H-3.jpg', 0, 13),
(63, 'https://i.postimg.cc/g02TxwQh/Lenovo-LOQ-Gaming-15-IAX9-i5-12450-H-4.jpg', 0, 13),
(64, 'https://i.postimg.cc/NGHP2vJd/Lenovo-LOQ-Gaming-15-IAX9-i5-12450-H-5.jpg', 0, 13),
(65, 'https://i.postimg.cc/8cRHKDxD/HP-Victus-16-r0130-TX-i5-13500-H-1.jpg', 1, 14),
(66, 'https://i.postimg.cc/FsvGfdJK/HP-Victus-16-r0130-TX-i5-13500-H-2.jpg', 0, 14),
(67, 'https://i.postimg.cc/htPrrksW/HP-Victus-16-r0130-TX-i5-13500-H-3.jpg', 0, 14),
(68, 'https://i.postimg.cc/7L5NvSjK/HP-Victus-16-r0130-TX-i5-13500-H-4.jpg', 0, 14),
(69, 'https://i.postimg.cc/G3Fx1LSn/HP-OMEN-Transcend-14-fb0135-TX-Ultra-7-1.jpg', 1, 15),
(70, 'https://i.postimg.cc/dtJmsbzk/HP-OMEN-Transcend-14-fb0135-TX-Ultra-7-2.jpg', 0, 15),
(71, 'https://i.postimg.cc/520Bvw9f/HP-OMEN-Transcend-14-fb0135-TX-Ultra-7-3.jpg', 0, 15),
(72, 'https://i.postimg.cc/Y0RFLSJ4/HP-OMEN-Transcend-14-fb0135-TX-Ultra-7-4.jpg', 0, 15),
(73, 'https://i.postimg.cc/pXVzbDFK/HP-OMEN-16-xf0071-AX-R7-7840-HS-1.jpg', 1, 16),
(74, 'https://i.postimg.cc/Qx65XHQw/HP-OMEN-16-xf0071-AX-R7-7840-HS-2.jpg', 0, 16),
(75, 'https://i.postimg.cc/Dw8Gmn5Z/HP-OMEN-16-xf0071-AX-R7-7840-HS-3.jpg', 0, 16),
(76, 'https://i.postimg.cc/3RH0Lph4/HP-OMEN-16-xf0071-AX-R7-7840-HS-4.jpg', 0, 16),
(77, 'https://i.postimg.cc/yxLJYbZc/Lenovo-Ideapad-Slim-3-15-ABR8-R7-7730-U-1.jpg', 1, 17),
(78, 'https://i.postimg.cc/05nKfbBj/Lenovo-Ideapad-Slim-3-15-ABR8-R7-7730-U-2.jpg', 0, 17),
(79, 'https://i.postimg.cc/QxM9nh2b/Lenovo-Ideapad-Slim-3-15-ABR8-R7-7730-U-3.jpg', 0, 17),
(80, 'https://i.postimg.cc/jSwWtvdn/Lenovo-Idea-Pad-5-15-IAL7-i5-1235-U-1.jpg', 1, 18),
(81, 'https://i.postimg.cc/HLbV7xMp/Lenovo-Idea-Pad-5-15-IAL7-i5-1235-U-2.jpg', 0, 18),
(82, 'https://i.postimg.cc/BbR0g5WR/Lenovo-Idea-Pad-5-15-IAL7-i5-1235-U-4.jpg', 0, 18),
(83, 'https://i.postimg.cc/5NF1trLJ/Lenovo-Idea-Pad-5-15-IAL7-i5-1235-U-5.jpg', 0, 18),
(84, 'https://i.postimg.cc/1tt117sL/Dell-XPS-13-9340-Ultra-5-125-H-1.jpg', 1, 19),
(85, 'https://i.postimg.cc/NjcMvtVS/Dell-XPS-13-9340-Ultra-5-125-H-2.jpg', 0, 19),
(86, 'https://i.postimg.cc/mDJLXWCd/Dell-XPS-13-9340-Ultra-5-125-H-3.jpg', 0, 19),
(87, 'https://i.postimg.cc/c1t4K6GP/Dell-XPS-13-9340-Ultra-5-125-H-4.jpg', 0, 19),
(88, 'https://i.postimg.cc/Jzx1wkjH/Acer-Nitro-V-ANV15-51-i5-12500-H-1.webp', 1, 20),
(89, 'https://i.postimg.cc/BvvJJnCY/Acer-Nitro-V-ANV15-51-i5-12500-H-2.webp', 0, 20),
(90, 'https://i.postimg.cc/0NHPj1W8/Acer-Nitro-V-ANV15-51-i5-12500-H-3.webp', 0, 20),
(91, 'https://i.postimg.cc/wxNgqPhp/Acer-Nitro-V-ANV15-51-i5-12500-H-4.webp', 0, 20),
(94, 'http://192.168.3.49/lap_store_api/uploads/sanpham/sanpham_1752162287.jpg', 1, 58),
(95, 'http://192.168.3.49/lap_store_api/uploads/sanpham/sanpham_1752165159.jpg', 1, 59);

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
  `TrangThai` varchar(20) NOT NULL,
  `LyDoTraHang` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `hoadonban`
--

INSERT INTO `hoadonban` (`MaHoaDonBan`, `MaKhachHang`, `NgayDatHang`, `MaDiaChi`, `TongTien`, `PhuongThucThanhToan`, `TrangThai`, `LyDoTraHang`) VALUES
(1, 11, '2023-01-05', 33, 10000000, 'Thanh toán khi nhận hàng', '4', NULL),
(2, 12, '2023-01-12', 34, 8500000, 'Thanh toán khi nhận hàng', '4', NULL),
(3, 13, '2023-02-08', 35, 9200000, 'Thanh toán khi nhận hàng', '4', NULL),
(4, 14, '2023-02-20', 36, 11000000, 'Thanh toán khi nhận hàng', '4', NULL),
(5, 11, '2023-03-01', 33, 15000000, 'Thanh toán khi nhận hàng', '4', NULL),
(6, 12, '2023-03-18', 34, 7300000, 'Thanh toán khi nhận hàng', '4', NULL),
(7, 13, '2023-04-02', 35, 9800000, 'Thanh toán khi nhận hàng', '4', NULL),
(8, 14, '2023-04-17', 36, 11500000, 'Thanh toán khi nhận hàng', '4', NULL),
(9, 11, '2023-05-05', 33, 9900000, 'Thanh toán khi nhận hàng', '4', NULL),
(10, 12, '2023-05-19', 34, 10500000, 'Thanh toán khi nhận hàng', '4', NULL),
(11, 13, '2023-06-03', 35, 8800000, 'Thanh toán khi nhận hàng', '4', NULL),
(12, 14, '2023-06-22', 36, 10100000, 'Thanh toán khi nhận hàng', '4', NULL),
(13, 11, '2023-07-04', 33, 9400000, 'Thanh toán khi nhận hàng', '4', NULL),
(14, 12, '2023-07-15', 34, 9700000, 'Thanh toán khi nhận hàng', '4', NULL),
(15, 13, '2023-08-01', 35, 8900000, 'Thanh toán khi nhận hàng', '4', NULL),
(16, 14, '2023-08-18', 36, 10700000, 'Thanh toán khi nhận hàng', '4', NULL),
(17, 11, '2023-09-06', 33, 11300000, 'Thanh toán khi nhận hàng', '4', NULL),
(18, 12, '2023-09-22', 34, 9500000, 'Thanh toán khi nhận hàng', '4', NULL),
(19, 13, '2023-10-03', 35, 9800000, 'Thanh toán khi nhận hàng', '4', NULL),
(20, 14, '2023-10-21', 36, 12200000, 'Thanh toán khi nhận hàng', '4', NULL),
(21, 11, '2023-11-02', 33, 8700000, 'Thanh toán khi nhận hàng', '4', NULL),
(22, 12, '2023-11-18', 34, 10000000, 'Thanh toán khi nhận hàng', '4', NULL),
(23, 13, '2023-11-27', 35, 8900000, 'Thanh toán khi nhận hàng', '4', NULL),
(24, 14, '2023-12-04', 36, 9600000, 'Thanh toán khi nhận hàng', '4', NULL),
(25, 11, '2023-12-12', 33, 10900000, 'Thanh toán khi nhận hàng', '4', NULL),
(26, 12, '2023-12-17', 34, 9400000, 'Thanh toán khi nhận hàng', '4', NULL),
(27, 13, '2023-12-21', 35, 8800000, 'Thanh toán khi nhận hàng', '4', NULL),
(28, 14, '2023-12-25', 36, 9300000, 'Thanh toán khi nhận hàng', '4', NULL),
(29, 11, '2023-12-28', 33, 10200000, 'Thanh toán khi nhận hàng', '4', NULL),
(30, 12, '2023-12-30', 34, 11100000, 'Thanh toán khi nhận hàng', '4', NULL),
(45, 12, '2024-05-09', 33, 30020000, 'Thanh toán khi nhận hàng', '6', NULL),
(46, 12, '2024-05-09', 34, 74020000, 'Thanh toán khi nhận hàng', '6', NULL),
(47, 12, '2024-05-10', 34, 119990000, 'Chuyển khoản ngân hàng', '6', NULL),
(48, 12, '2024-05-10', 34, 119990000, 'Chuyển khoản ngân hàng', '8', NULL),
(49, 12, '2024-05-10', 34, 48000000, 'Chuyển khoản ngân hàng', '6', NULL),
(50, 12, '2024-05-10', 34, 30020000, 'Thanh toán khi nhận hàng', '6', NULL),
(51, 12, '2025-05-12', 34, 30020000, 'Thanh toán khi nhận hàng', '6', NULL),
(52, 12, '2025-05-12', 34, 18010000, 'Chuyển khoản ngân hàng', '6', NULL),
(53, 12, '2025-05-12', 34, 30020000, 'Chuyển khoản ngân hàng', '6', NULL),
(54, 12, '2025-05-12', 34, 30020000, 'Thanh toán khi nhận hàng', '6', NULL),
(165, 11, '2024-01-15', 34, 1500000, 'Thanh toán khi nhận hàng', '6', NULL),
(166, 12, '2024-02-08', 35, 3200000, 'Thanh toán khi nhận hàng', '6', NULL),
(167, 10, '2024-03-12', 36, 2150000, 'Thanh toán khi nhận hàng', '6', NULL),
(168, 14, '2024-03-28', 37, 4700000, 'Thanh toán khi nhận hàng', '6', NULL),
(169, 11, '2024-04-05', 38, 5200000, 'Thanh toán khi nhận hàng', '6', NULL),
(170, 12, '2024-04-15', 34, 1890000, 'Thanh toán khi nhận hàng', '6', NULL),
(171, 10, '2024-04-27', 35, 7900000, 'Thanh toán khi nhận hàng', '8', NULL),
(172, 14, '2024-05-03', 36, 2950000, 'Thanh toán khi nhận hàng', '8', NULL),
(173, 11, '2024-05-14', 37, 4600000, 'Thanh toán khi nhận hàng', '8', NULL),
(174, 12, '2024-05-29', 38, 2750000, 'Thanh toán khi nhận hàng', '6', NULL),
(175, 10, '2024-06-01', 34, 2500000, 'Thanh toán khi nhận hàng', '6', NULL),
(176, 14, '2024-06-07', 35, 3800000, 'Thanh toán khi nhận hàng', '6', NULL),
(177, 11, '2024-06-13', 36, 1990000, 'Thanh toán khi nhận hàng', '6', NULL),
(178, 12, '2024-06-18', 37, 5100000, 'Thanh toán khi nhận hàng', '6', NULL),
(179, 10, '2024-06-24', 38, 3400000, 'Thanh toán khi nhận hàng', '6', NULL),
(180, 14, '2024-06-29', 34, 2650000, 'Thanh toán khi nhận hàng', '6', NULL),
(181, 11, '2024-07-03', 35, 6150000, 'Thanh toán khi nhận hàng', '6', NULL),
(182, 12, '2024-07-08', 36, 4800000, 'Thanh toán khi nhận hàng', '6', NULL),
(183, 10, '2024-07-15', 37, 3050000, 'Thanh toán khi nhận hàng', '6', NULL),
(184, 14, '2024-07-25', 38, 6750000, 'Thanh toán khi nhận hàng', '6', NULL),
(185, 11, '2024-08-04', 34, 3900000, 'Thanh toán khi nhận hàng', '6', NULL),
(186, 12, '2024-08-12', 35, 1800000, 'Thanh toán khi nhận hàng', '6', NULL),
(187, 10, '2024-08-18', 36, 3250000, 'Thanh toán khi nhận hàng', '6', NULL),
(188, 14, '2024-08-23', 37, 2950000, 'Thanh toán khi nhận hàng', '6', NULL),
(189, 11, '2024-08-31', 38, 4300000, 'Thanh toán khi nhận hàng', '6', NULL),
(190, 12, '2024-09-06', 34, 2700000, 'Thanh toán khi nhận hàng', '6', NULL),
(191, 10, '2024-09-11', 35, 6600000, 'Thanh toán khi nhận hàng', '6', NULL),
(192, 14, '2024-09-16', 36, 2200000, 'Thanh toán khi nhận hàng', '6', NULL),
(193, 11, '2024-09-22', 37, 3450000, 'Thanh toán khi nhận hàng', '6', NULL),
(194, 12, '2024-09-28', 38, 5200000, 'Thanh toán khi nhận hàng', '6', NULL),
(195, 10, '2024-10-01', 34, 3800000, 'Thanh toán khi nhận hàng', '6', NULL),
(196, 14, '2024-10-05', 35, 2100000, 'Thanh toán khi nhận hàng', '6', NULL),
(197, 11, '2024-10-11', 36, 4450000, 'Thanh toán khi nhận hàng', '6', NULL),
(198, 12, '2024-10-15', 37, 3150000, 'Thanh toán khi nhận hàng', '6', NULL),
(199, 10, '2024-10-20', 38, 7300000, 'Thanh toán khi nhận hàng', '6', NULL),
(200, 14, '2024-10-27', 34, 5600000, 'Thanh toán khi nhận hàng', '6', NULL),
(201, 11, '2024-11-01', 35, 1800000, 'Thanh toán khi nhận hàng', '6', NULL),
(202, 12, '2024-11-07', 36, 4150000, 'Thanh toán khi nhận hàng', '6', NULL),
(203, 10, '2024-11-14', 37, 2950000, 'Thanh toán khi nhận hàng', '6', NULL),
(204, 14, '2024-11-20', 38, 2500000, 'Thanh toán khi nhận hàng', '6', NULL),
(205, 11, '2024-12-01', 34, 3850000, 'Thanh toán khi nhận hàng', '6', NULL),
(206, 12, '2024-12-04', 35, 4100000, 'Thanh toán khi nhận hàng', '6', NULL),
(207, 10, '2024-12-10', 36, 2050000, 'Thanh toán khi nhận hàng', '6', NULL),
(208, 14, '2024-12-14', 37, 2800000, 'Thanh toán khi nhận hàng', '6', NULL),
(209, 11, '2024-12-19', 38, 4650000, 'Thanh toán khi nhận hàng', '6', NULL),
(210, 12, '2024-12-22', 34, 5250000, 'Thanh toán khi nhận hàng', '8', NULL),
(211, 10, '2024-12-25', 35, 3150000, 'Thanh toán khi nhận hàng', '6', NULL),
(212, 14, '2024-12-27', 36, 4550000, 'Thanh toán khi nhận hàng', '6', NULL),
(213, 11, '2024-12-29', 37, 2900000, 'Thanh toán khi nhận hàng', '6', NULL),
(214, 12, '2024-12-31', 38, 4950000, 'Thanh toán khi nhận hàng', '6', NULL),
(215, 13, '2025-07-04', 37, 8129000, 'Thanh toán khi nhận hàng', '8', NULL),
(216, 13, '2025-07-04', 37, 58736000, 'Thanh toán khi nhận hàng', '8', NULL),
(217, 13, '2025-07-04', 37, 8129000, 'Thanh toán qua Momo', '6', NULL),
(218, 16, '2025-07-04', 40, 20883000, 'Thanh toán khi nhận hàng', '6', NULL),
(219, 12, '2025-07-04', 34, 20883000, 'Thanh toán khi nhận hàng', '6', NULL),
(220, 12, '2025-07-06', 34, 20883000, 'Thanh toán khi nhận hàng', '8', NULL),
(221, 12, '2025-07-06', 34, 20883000, 'Thanh toán khi nhận hàng', '6', NULL),
(222, 12, '2025-07-06', 34, 8129000, 'Thanh toán khi nhận hàng', '6', NULL),
(223, 12, '2025-07-06', 34, 13862000, 'Thanh toán khi nhận hàng', '6', NULL),
(224, 12, '2025-07-06', 34, 13429000, 'Thanh toán khi nhận hàng', '6', NULL),
(225, 12, '2025-07-06', 34, 37081000, 'Thanh toán khi nhận hàng', '6', NULL),
(226, 12, '2025-07-06', 34, 10720000, 'Thanh toán khi nhận hàng', '6', NULL),
(227, 12, '2025-07-06', 34, 16272327, 'Thanh toán khi nhận hàng', '8', NULL),
(228, 12, '2025-07-06', 34, 8129000, 'Thanh toán khi nhận hàng', '6', NULL),
(229, 12, '2025-07-06', 34, 14182000, 'Thanh toán khi nhận hàng', '6', NULL),
(230, 5, '2025-07-07', 41, 34809000, 'Thanh toán qua Momo', '4', NULL),
(231, 5, '2025-07-07', 41, 8129000, 'Thanh toán qua Momo', '4', NULL),
(232, 12, '2025-07-07', 34, 8129000, 'Thanh toán qua Momo', '8', NULL),
(233, 12, '2025-07-07', 34, 20883000, 'Thanh toán qua Momo', '8', NULL),
(234, 14, '2025-07-07', 38, 24327000, 'Thanh toán khi nhận hàng', '4', NULL),
(235, 12, '2025-07-10', 34, 8129000, 'Thanh toán khi nhận hàng', '8', NULL),
(236, 12, '2025-07-10', 34, 8129000, 'Thanh toán khi nhận hàng', '6', NULL),
(237, 12, '2025-07-10', 34, 28982000, 'Thanh toán khi nhận hàng', '6', NULL),
(238, 12, '2025-07-10', 34, 8129000, 'Thanh toán khi nhận hàng', '6', NULL),
(239, 12, '2025-07-10', 34, 8129000, 'Thanh toán khi nhận hàng', '6', NULL),
(240, 12, '2025-07-10', 34, 20883000, 'Thanh toán khi nhận hàng', '6', NULL),
(241, 12, '2025-07-10', 34, 10720000, 'Thanh toán khi nhận hàng', '6', NULL),
(242, 12, '2025-07-10', 34, 24235000, 'Thanh toán khi nhận hàng', '6', NULL),
(243, 12, '2025-07-10', 34, 10720000, 'Thanh toán khi nhận hàng', '6', NULL),
(244, 11, '2025-07-10', 33, 11020000, 'Thanh toán khi nhận hàng', '8', NULL),
(245, 11, '2025-07-10', 33, 8129000, 'Thanh toán khi nhận hàng', '6', NULL),
(246, 11, '2025-07-10', 33, 17572200, 'Thanh toán khi nhận hàng', '6', NULL),
(247, 11, '2025-07-10', 33, 13429000, 'Thanh toán khi nhận hàng', '6', NULL),
(248, 12, '2025-07-11', 34, 20883000, 'Chuyển khoản ngân hàng', '8', 'Không đúng sản phẩm'),
(249, 12, '2025-07-12', 34, 30000000, 'Thanh toán khi nhận hàng', '8', NULL),
(250, 12, '2025-07-12', 34, 30000000, 'Thanh toán khi nhận hàng', '6', NULL),
(251, 12, '2025-07-12', 34, 20883000, 'Thanh toán khi nhận hàng', '8', NULL),
(252, 12, '2025-07-12', 34, 20883000, 'Thanh toán khi nhận hàng', '8', 'sai lầm '),
(253, 12, '2025-07-12', 34, 16272327, 'Thanh toán khi nhận hàng', '8', NULL),
(254, 12, '2025-07-12', 34, 40525000, 'Thanh toán khi nhận hàng', '4', NULL),
(255, 12, '2025-07-12', 34, 30000, 'Thanh toán khi nhận hàng', '4', NULL),
(256, 13, '2025-07-12', 37, 41736000, 'Thanh toán khi nhận hàng', '8', NULL),
(257, 13, '2025-07-12', 37, 17572200, 'Thanh toán khi nhận hàng', '8', NULL),
(258, 13, '2025-07-12', 37, 3730000, 'Thanh toán khi nhận hàng', '8', NULL),
(259, 13, '2025-07-12', 37, 20129000, 'Thanh toán khi nhận hàng', '8', NULL),
(260, 13, '2025-07-12', 37, 10720000, 'Thanh toán khi nhận hàng', '8', 'lỗi'),
(261, 13, '2025-07-12', 37, 11020000, 'Thanh toán khi nhận hàng', '8', 'lỗi sp'),
(262, 13, '2025-07-12', 37, 20883000, 'Thanh toán khi nhận hàng', '1', NULL),
(263, 13, '2025-07-12', 37, 36228000, 'Thanh toán qua Momo', '1', NULL),
(264, 13, '2025-07-13', 37, 49627000, 'Thanh toán khi nhận hàng', '6', NULL),
(265, 13, '2025-07-13', 37, 18129000, 'Thanh toán khi nhận hàng', '4', NULL),
(266, 13, '2025-07-13', 37, 18129000, 'Thanh toán qua Momo', '4', NULL),
(267, 13, '2025-07-13', 37, 18129000, 'Thanh toán qua Momo', '4', NULL),
(268, 13, '2025-07-13', 37, 18129000, 'Thanh toán qua Momo', '4', NULL),
(269, 13, '2025-07-13', 42, 17930000, 'Thanh toán khi nhận hàng', '1', NULL);

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
(1, 'Nguyễn Văn Phú', 'Nam', '1990-05-11', 'admin@gmail.com        ', '0374897753'),
(3, 'Nguyễn Thị B', 'Nữ', '1992-01-01', 'nguyenthib@gmail.com', '1234567890'),
(5, 'lamvanc', 'Nam', '1997-03-05', 'lamvanc@gmail.com', '0939049151'),
(6, 'nguyenthitele', '', '1987-04-10', 'tele@gmail.com', '0939457788'),
(7, 'nguyenvana', '', '1995-05-16', 'vn@gmail.com', ''),
(9, 'nguyenvanaa', '', '0000-00-00', 'gfgfdg', ''),
(10, 'nguyenthibetu', 'Nữ', '2004-06-05', 'betubungbu@gmail.com', '0904853621'),
(11, 'nguyễn ngọc thương', 'Nam', '2004-09-23', 'khaylohuynh23092004@gmail.com', '0383184941'),
(12, 'phú nguyễn thanh', 'Nam', '1999-12-19', 'khaybua@gmail.com', '0374897743'),
(13, 'phú thanh ', 'Nam', '1997-07-03', 'phu77@gmail.com', '0789579981'),
(14, 'anh minh', 'Nam', '1982-11-04', 'khang77@gmail.com', '0377897753'),
(15, 'nhật lễ', '', '2000-07-16', 'pthao1219w@gmail.com', ''),
(16, 'thanh phú', 'Nam', '1987-05-04', 'phanp0723@gmail.com', '0334753399'),
(18, 'nguyễn phú', '', '1999-07-02', 'phanmai11126@gmail.com', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khuyenmai`
--

CREATE TABLE `khuyenmai` (
  `MaKhuyenMai` int(11) NOT NULL,
  `MaSanPham` int(11) DEFAULT NULL,
  `TenKhuyenMai` varchar(255) DEFAULT NULL,
  `PhanTramGiam` int(11) DEFAULT NULL,
  `NgayBatDau` date DEFAULT NULL,
  `NgayKetThuc` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khuyenmai`
--

INSERT INTO `khuyenmai` (`MaKhuyenMai`, `MaSanPham`, `TenKhuyenMai`, `PhanTramGiam`, `NgayBatDau`, `NgayKetThuc`) VALUES
(1, 2, 'all', 2, '2025-07-23', '2025-09-30'),
(3, 5, 'all', 23, '0032-12-15', '0033-03-17'),
(5, 10, 'all', 22, '2025-06-27', '2025-09-27'),
(6, 1, 'all', 30, '2025-06-27', '2025-09-27'),
(7, 3, 'all', 20, '2025-06-27', '2025-09-27'),
(8, 6, 'all', 20, '2025-06-27', '2025-09-27'),
(9, 8, 'all', 12, '2025-07-25', '2025-09-25'),
(10, 9, 'all', 20, '2025-06-27', '2025-09-27'),
(11, 11, 'all', 20, '2025-06-27', '2025-09-27'),
(12, 13, 'all', 90, '2025-06-27', '2025-09-27'),
(13, 14, 'all', 20, '2025-06-27', '2025-09-27');

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
(2, 'Laptop Gaming');

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
(1, 'Laptop gaming ASUS TUF Gaming F15 FX507VV LP304W', 2, 'INTEL CORE I9 12900K', ' 16 GB', 'NVIDIA RTX 4060', '512 GB', '1920x1080', 2, 29790000, 96, 'Laptop gaming RTX 40 Series mạnh nhất cho học sinh, sinh viên\n\nHoạt động bền bỉ, làm chủ trận chiến trên chiếc laptop ASUS TUF Gaming F15 (2023), sử dụng bộ vi xử lý Intel Core™ i7-13620H với sự kết hợp giữa lõi hiệu năng (P-core) và lõi tiết kiệm điện (E-core) cân bằng sức mạnh chơi game và khả năng đa nhiệm tuyệt vời. ASUS TUF Gaming F15 còn được trang bị đồ họa NVIDIA GeForce RTX™ 40 series mới nhất hỗ trợ G-SYNC® và có TGP tối đa là 140 W với Dynamic Boost. Sự kết hợp giữa CPU mạnh mẽ và GPU hiệu suất cao mang lại khả năng chơi game đáng kinh ngạc trên cả các tựa game mới nhất. Màn hình FHD 144Hz cho trải nghiệm gaming mượt mà, tận hưởng không gian giải trí đắm chìm. ', 1),
(2, 'Laptop MSI Modern 14 C11M 011VN', 1, 'Intel i5', ' 16 GB', 'NVIDIA RTX 4060', '512 GB', '1920x1080', 1, 18099000, 99, 'MSI Modern 14 C11M-011VN nằm trong phân khúc laptop 9 triệu được trang bị màn hình rộng 14 inch có độ phân giải Full HD cùng tần số quét ở mức cơ bản 60Hz. Màn hình IPS này sẽ đủ sắc nét để người dùng có thể thoải mái sử dụng trong học tập, nghiên cứu và làm việc.', 1),
(3, 'Laptop gaming ASUS Vivobook 16X K3605ZF RP634W', 2, 'Intel i5', '4GB', 'Integrated', '128GB', '13.3&amp;quot; FHD', 1, 17290000, 100, 'Bên trong ASUS Gaming Vivobook K3605ZF-RP634W là bộ vi xử lý Intel Core i5-12500H với 4 nhân hiệu năng cao và 8 nhân tiết kiệm điện, cùng với bộ nhớ đệm 18MB, đạt tốc độ tối đa lên đến 4.5 GHz. Cấu hình này không chỉ đáp ứng tốt các tựa game phổ biến mà còn tối ưu cho các tác vụ đa nhiệm và xử lý đồ họa nặng.', 1),
(4, 'Laptop ASUS Vivobook 14 OLED A1405VA KM095W', 1, 'INTEL CORE I7 12500H', ' 8 GB', 'INTEL ARISXE', '1TB', '1920x1080', 1, 17000000, 0, 'Tỏa sáng với cả thế giới cùng ASUS Vivobook 14 OLED mạnh mẽ, chiếc laptop tích hợp nhiều tính năng với màn hình OLED rực rỡ, gam màu DCI-P3 đẳng cấp điện ảnh. Mọi thứ trở nên dễ dàng hơn nhờ những tiện ích thân thiện với người dùng bao gồm bản lề duỗi thẳng 180°, nắp che webcam vật lý và các phím chức năng chuyên dụng. Bảo vệ sức khỏe an toàn với ASUS kháng khuẩn Guard Plus trên các bề mặt thường xuyên chạm vào. Bắt đầu ngày mới đầy hứng khởi với ASUS Vivobook 14 OLED!', 1),
(5, 'Laptop Acer Swift 14 AI SF14 51 53P9', 1, 'Intel® Core™ Ultra 5 Processor 226V', ' 16GB', 'NVIDIA RTX 4060', '1TB', '14&amp; 3K (2880 x 1800) OLED', 1, 13399000, 140, 'Acer Swift 14 AI SF14 51 53P9 tiếp tục thừa hưởng ngôn ngữ thiết kế tối giản, thanh lịch của dòng Swift. Vỏ máy được chế tác từ hợp kim nhôm cao cấp, mang đến vẻ ngoài sang trọng và độ bền bỉ cao. Với độ mỏng ấn tượng chỉ 15.95mm và trọng lượng siêu nhẹ 1.26kg, chiếc laptop ai này là người bạn đồng hành lý tưởng cho những người thường xuyên di chuyển. ', 1),
(6, 'Laptop gaming MSI Stealth 16 AI Studio A1VHG 241VN', 2, 'Intel® Core™ Ultra 9 processor 185H with Intel® AI', '32GB', 'NVIDIA® GeForce RTX™', ' 1TB', '16&amp;amp; QHD+(2560x1600), 240Hz', 1, 73990000, 130, 'Chiếc laptop gaming được trang bị con chip Intel Core Ultra 9 - 185H với 16 lõi, 22 luồng kết hợp với VGA NVIDIA GeForece RTX 4070 giúp trải nghiệm chơi các game AAA và render hình ảnh, video một cách mượt mà không xảy ra hiện tượng giật lag. \r\n\r\n', 1),
(7, 'Laptop Acer Aspire Go AG15', 1, 'Intel Core i3 Alder Lake', '16GB', ' Intel UHD Graphics', '128GB', '1920 x 1080', 1, 10690000, 189, 'Acer Aspire Go 15 AG15‑31P‑30M4 (i3‑N305/8 GB/256 GB/Win11) là lựa chọn hợp lý cho người tìm laptop văn phòng nhẹ, ổn định, màn hình lớn, pin tốt với mức giá phải chăng. Tuy nhiên, nếu bạn cần RAM cao hơn hoặc xử lý đồ họa/phim ảnh/đồ họa nặng, bạn nên cân nhắc nâng cấp hoặc chọn cấu hình cao hơn.', 1),
(8, 'Laptop Acer Gaming Nitro V 15', 2, 'AMD Ryzen 5', '8GB', 'NVIDIA RTX 3050', '128GB', '1920 x 1080', 2, 24205000, 69, 'Acer Nitro V 15 là mẫu laptop gaming phổ thông có hiệu năng mạnh mẽ, phù hợp cho game thủ tầm trung và sinh viên cần một chiếc máy chơi game tốt mà vẫn gọn nhẹ. Máy được trang bị vi xử lý Intel Core i5-13420H thế hệ 13 kết hợp với card đồ họa rời NVIDIA GeForce RTX 3050 hoặc RTX 4050, giúp chơi mượt các tựa game eSports và AAA ở độ phân giải Full HD', 1),
(9, 'Laptop HP Gaming VICTUS 15 fa1139TX', 2, 'Intel Core i5 Alder Lake', '16GB', 'NVIDIA RTX 3060', '128GB', '1920x1080', 2, 17690000, 70, 'HP Gaming Victus 15 fa1139TX là mẫu laptop gaming tầm trung nổi bật với cấu hình ổn định, thiết kế hiện đại và mức giá dễ tiếp cận. Máy sử dụng vi xử lý Intel Core i5-12450H (thế hệ 12) kết hợp với card đồ họa rời NVIDIA GeForce RTX 2050, mang đến hiệu năng khá tốt cho các tựa game eSports, game online phổ biến và cả một số game offline tầm trung ở mức thiết lập vừa phải. Bộ nhớ RAM 16GB DDR4 (2 thanh 8GB, hỗ trợ nâng cấp) cùng ổ SSD 512GB NVMe giúp máy hoạt động mượt mà, khởi động nhanh và xử lý đa nhiệm hiệu quả', 1),
(10, 'Laptop Dell Inspiron 15', 1, 'Intel Core i7 Raptor Lake', '16GB', 'Intel Iris ', '128GB', '1920x1080', 1, 22490000, 59, 'Dell Inspiron 15 là dòng phổ thông, phù hợp cho học tập và công việc nhẹ. Có nhiều cấu hình khác nhau: phổ biến là Core i5 + 8 GB RAM + SSD 512 GB hoặc HDD 1 TB. Màn hình 15.6″ FHD IPS (120–144Hz) hiển thị mượt, tuy màu sắc không quá nổi bật. Thiết kế bền, trọng lượng từ 1.65–2.2 kg tùy bản, pin dùng khoảng 6–9 giờ. Máy hỗ trợ nâng cấp RAM/SSD, cổng kết nối đa dạng, hiệu năng đủ cho đa nhiệm, làm văn phòng, học trực tuyến .', 1),
(11, 'Laptop MSI Gaming Thin 15', 2, 'Intel Core i7 Raptor Lake', '8GB', 'NVIDIA RTX 3060', '512GB', '1920x1080', 2, 22790000, 70, 'MSI Thin 15 là chiếc laptop gaming mỏng nhẹ nhưng vẫn mạnh mẽ, thường có cấu hình Core i5‑13420H hoặc i7‑13620H kết hợp card đồ họa RTX 3050, RTX 4050, thậm chí RTX 4060, phù hợp chơi game Full HD ở thiết lập cao với mức giá rất cạnh tranh, chỉ từ khoảng 16–17 triệu cho bản RTX 3050', 1),
(13, 'Lenovo LOQ Gaming 15IAX9 i5-12450H', 2, 'Intel Core i5-12450H', '16GB', 'NVIDIA RTX 3050', '512GB', '15.6&quot; FHD 120Hz', 1, 18500000, 68, 'Lenovo LOQ Gaming 15IAX9 i5 12450HX (83GS000JVN) là chiếc laptop gaming mang thiết kế mạnh mẽ, hiện đại, phù hợp với học sinh – sinh viên cần hiệu năng cao cho cả học tập lẫn giải trí. Máy sở hữu vi xử lý Intel Core i5-12450HX, card đồ họa RTX 3050 6GB và chip AI LA1 tối ưu hiệu năng tự động giữa CPU và GPU.\n\nRAM 16GB nâng cấp tối đa 32GB cùng SSD 512GB (có thể mở rộng đến 1TB) đáp ứng tốt các nhu cầu game và đồ họa. Màn hình 15.6 inch FHD IPS, 144Hz, hỗ trợ G-Sync, phủ màu 100% sRGB, kết hợp công nghệ chống chói và lọc ánh sáng xanh, cho trải nghiệm hình ảnh mượt mà, bảo vệ mắt.\n\nÂm thanh sống động với công nghệ Nahimic Audio, webcam có khóa vật lý, bàn phím có đèn nền, khung máy hầm hố nhưng vẫn linh hoạt với trọng lượng 2.38kg. Cổng kết nối đa dạng, phù hợp cho nhu cầu học, làm và chơi game. Đây là lựa chọn lý tưởng trong tầm giá 20–25 triệu.', 1),
(14, 'HP Victus 16 r0130TX i5-13500H', 2, 'Intel Core i5-13500H', '16GB', 'NVIDIA RTX 3060', '512GB', '16.1\" FHD 144Hz', 2, 1249000, 90, 'HP Gaming VICTUS 16 r0130TX i5 13500H (8C5N5PA) là phiên bản nâng cấp từ dòng VICTUS 15, mang đến hiệu năng mạnh mẽ và thiết kế tinh tế. Máy sử dụng vi xử lý Intel Core i5-13500H kết hợp card đồ họa RTX 3050 6GB, giúp xử lý tốt cả công việc văn phòng, thiết kế 2D, 3D nhẹ và chơi các tựa game AAA phổ biến với FPS ổn định.\n\nRAM 16GB DDR5 (hỗ trợ nâng cấp đến 32GB) cho khả năng đa nhiệm mượt mà. SSD 512GB PCIe giúp khởi động và truy xuất dữ liệu nhanh. Màn hình 16.1 inch FHD, tấm nền IPS, tần số quét 144Hz, độ phủ màu tốt cho trải nghiệm hình ảnh rộng rãi và sắc nét.\n\nÂm thanh chất lượng cao nhờ công nghệ Realtek HD, B&O và HP Audio Boost. Thiết kế tối giản nhưng mạnh mẽ, khung máy bền, logo V nổi bật, bàn phím fullsize có RGB 1 vùng dễ tùy chỉnh, touchpad mượt.\n\nNgoài ra, máy hỗ trợ đầy đủ các cổng kết nối phổ biến như USB-C, HDMI, LAN... Với trọng lượng 2.34 kg, đây là một lựa chọn đáng giá cho sinh viên hoặc người dùng cần một laptop gaming mạnh, giá hợp lý, phục vụ cả học tập lẫn giải trí hiệu quả.', 1),
(15, 'HP OMEN Transcend 14 fb0135TX Ultra 7', 1, 'Intel Core i7-13620H', '16GB', 'NVDIA RTX 3060', '512GB', '14&quot; FHD 144Hz', 1, 20099000, 70, 'HP OMEN Transcend 14 fb0135TX Ultra 7 155H (AY8V1PA) là sự kết hợp hoàn hảo giữa ultrabook mỏng nhẹ và laptop gaming hiệu năng cao. Máy trang bị CPU Intel Core Ultra 7 155H thế hệ mới, tích hợp nhân AI, đi kèm GPU RTX 4060 mạnh mẽ – sẵn sàng chiến các tựa game AAA, render đồ hoạ phức tạp hay xử lý AI chuyên sâu.\n\nRAM 16GB LPDDR5X tốc độ cao, SSD 1TB NVMe giúp đa nhiệm mượt và lưu trữ thoải mái. Màn hình OLED 14 inch 2.8K, tần số quét 120Hz, 100% DCI-P3 mang đến hình ảnh sắc nét, màu sắc chân thực – phù hợp cho game thủ lẫn dân thiết kế.\n\nÂm thanh DTS:X Ultra sống động, webcam Full HD, bảo mật khuôn mặt với camera IR. Bàn phím RGB 4 vùng, thân máy kim loại cao cấp chỉ nặng 1.63kg – cực kỳ linh hoạt. Kết nối đầy đủ (Thunderbolt 4, HDMI, USB-C...) và pin 71Wh dùng tốt cả ngày.\n\nĐây là lựa chọn lý tưởng cho người dùng yêu cầu cấu hình mạnh, thiết kế tinh gọn, phục vụ tốt cho cả chơi game, học tập và sáng tạo nội dung.', 1),
(16, 'HP OMEN 16 xf0071AX R7-7840HS', 1, 'AMD Ryzen 7 7840HS', '16GB', 'NVIDIA RTX 4060', '256GB', '13.3&quot; FHD', 2, 2330000, 50, 'HP OMEN 16 xf0071AX R7 7840HS là mẫu laptop gaming cao cấp với hiệu năng vượt trội, trang bị vi xử lý AMD Ryzen 7 7840HS và card đồ họa RTX 4060 8GB, phù hợp cho cả chơi game AAA, livestream và thiết kế 3D chuyên sâu. RAM 32GB DDR5 (hỗ trợ nâng đến 64GB) và SSD 1TB NVMe mang lại khả năng đa nhiệm mượt mà, lưu trữ lớn và tốc độ truy xuất nhanh chóng.\n\nMàn hình 16.1 inch QHD (2K), tấm nền IPS, tần số quét 240Hz, độ phủ màu 100% sRGB cho trải nghiệm hình ảnh cực sắc nét và mượt, phù hợp với game thủ và nhà sáng tạo nội dung. Âm thanh sống động nhờ công nghệ DTS:X Ultra, HP Audio Boost và B&O, tạo cảm giác đắm chìm trong game và giải trí.\n\nThiết kế mạnh mẽ nhưng vẫn tinh tế với trọng lượng 2.37kg, bàn phím RGB 4 vùng và đầy đủ cổng kết nối như USB-C, HDMI, LAN… Đây là lựa chọn lý tưởng cho người dùng cần một chiếc laptop gaming - đồ họa cao cấp, vừa mạnh vừa sang trọng, trong tầm giá hợp lý.', 1),
(17, 'Lenovo Ideapad Slim 3 15ABR8 R7-7730U', 1, 'AMD Ryzen 7 7730U', '16GB', 'NVIDIA RTX 3060', '512GB', '14&amp;quot; FHD OLED', 2, 10990000, 48, 'Lenovo Ideapad Slim 5 OLED 14AKP10 R5 AI 340 (83HX001KVN) là lựa chọn hoàn hảo cho học sinh, sinh viên và dân văn phòng cần một chiếc laptop mỏng nhẹ, bền bỉ, hiệu năng mạnh và màn hình xuất sắc. Máy dùng CPU AMD Ryzen AI 5 340, 6 nhân 12 luồng, tích hợp NPU AMD XDNA xử lý AI tới 50 TOPS, kết hợp RAM 24GB DDR5 và SSD 512GB, đảm bảo xử lý mượt mọi tác vụ văn phòng, học tập, giải trí và đa nhiệm.\n\nMàn hình 14 inch OLED WUXGA, phủ màu 100% DCI-P3, đạt chuẩn DisplayHDR True Black 500 mang lại trải nghiệm hình ảnh sắc nét, sống động và chân thực. Tích hợp loa Dolby Audio cho âm thanh sống động, hạn chế mỏi mắt nhờ công nghệ Low Blue Light.\n\nThiết kế kim loại cao cấp, chỉ 1.39kg, mỏng 16.9mm, đạt chuẩn độ bền MIL-STD-810H, kèm đèn nền phím trắng và camera IR hỗ trợ mở khóa khuôn mặt. Máy có đầy đủ cổng USB-C, HDMI, SD card, Wi-Fi 7, Bluetooth 5.4, pin 60Wh dùng dài lâu.\n\nTóm lại, đây là mẫu laptop AI mỏng nhẹ, pin trâu, cấu hình cao, rất phù hợp cho người cần thiết bị đa năng, linh hoạt cho học tập – làm việc – giải trí.', 1),
(18, 'Lenovo IdeaPad 5 15IAL7 i5-1235U', 1, 'Intel Core i5-1235U', '8GB', 'Intel Iris Xe', '512GB', '15.6&quot; FHD', 1, 17900000, 69, 'Lenovo Ideapad 5 15IAL7 i5 1235U (82SF005HVN) là mẫu laptop văn phòng lý tưởng cho học sinh, sinh viên và người đi làm nhờ thiết kế thanh lịch, hiệu năng ổn định và độ bền chuẩn quân đội MIL-STD-810H. Máy được trang bị CPU Intel Core i5-1235U Gen 12, kết hợp Intel Iris Xe Graphics, RAM 8GB DDR4, ổ SSD 512GB NVMe tốc độ cao, đủ sức đáp ứng tốt các tác vụ học tập, làm việc, thiết kế cơ bản và giải trí.\n\nMàn hình 15.6 inch Full HD IPS, viền mỏng, có chống chói và Low Blue Light, phù hợp dùng lâu dài không mỏi mắt. Âm thanh Dolby Audio mang lại trải nghiệm giải trí sống động. Bàn phím full size có đèn nền, hỗ trợ tốt khi làm việc ban đêm.\n\nMáy nặng 1.85kg, vỏ kim loại chắc chắn, pin 57Wh dùng được khoảng 6 tiếng. Webcam Full HD, khóa camera, bảo mật vân tay giúp tăng tính riêng tư. Đầy đủ cổng HDMI, USB-C, khe thẻ SD... và hỗ trợ nâng cấp SSD/HDD tiện lợi. Đây là mẫu laptop cân đối giữa giá – hiệu năng – tính di động trong phân khúc phổ thông đáng mua.', 1),
(19, 'Dell XPS 13 9340 Ultra 5-125H', 1, 'Intel Core Ultra 5 125H', '8GB', 'Intel Integrated', '512GB', '13.4\" FHD', 1, 17800000, 40, 'Dell XPS 13 9340 Ultra 5 125H (XPSU5934W1) là mẫu laptop cao cấp nổi bật với thiết kế siêu mỏng nhẹ chỉ 1.22 kg, bàn phím tràn viền độc đáo và touchpad ẩn, mang lại vẻ hiện đại tối giản nhưng cực kỳ tinh tế. Máy sử dụng Intel Core Ultra 5 125H Gen 14, RAM 16GB LPDDR5X, SSD 2TB và đồ họa Intel Arc, xử lý mượt các tác vụ nặng như thiết kế, chỉnh sửa video hay đa nhiệm văn phòng.\n\nMàn hình 13.4 inch QHD+ (2560 x 1600) công nghệ WVA, tần số quét 120Hz, độ phủ màu 100% sRGB, hỗ trợ cảm ứng, hiển thị sắc nét, màu chuẩn, kết hợp Eyesafe & Anti Glare giúp bảo vệ mắt khi dùng lâu.\n\nÂm thanh sống động nhờ Realtek Audio, webcam sắc nét, bàn phím có đèn nền, mở khóa bằng vân tay tiện lợi. Máy có 2 cổng Thunderbolt 4 cho tốc độ truyền tải cao, dễ dàng mở rộng kết nối qua hub rời. Đây là chiếc laptop lý tưởng cho người dùng sáng tạo, doanh nhân hay làm việc di động cần một thiết bị vừa đẹp – nhẹ – mạnh mẽ.\n', 1),
(20, 'Acer Nitro V ANV15-51 i5-12500H', 2, 'Intel Core i5-12500H', '8GB', 'NVIDIA RTX 3050', '512GB', '15.6\" FHD', 2, 25790000, 90, 'Acer Nitro V ANV15-51-58AN là mẫu laptop gaming quốc dân nổi bật với thiết kế cá tính, hiệu năng mạnh mẽ từ Intel Core i5-13420H Gen 13 và card rời NVIDIA GeForce RTX 2050. Máy trang bị RAM 16GB DDR5 và SSD 512GB (hỗ trợ nâng cấp lên 2TB), dễ dàng đáp ứng các tựa game AAA, E-Sports và các phần mềm đồ họa.\n\nMàn hình 15.6 inch FHD IPS, tần số quét 144Hz, công nghệ ComfyView, tái hiện hình ảnh sắc nét, mượt mà. Hệ thống tản nhiệt kép Dual-Fan giúp duy trì hiệu suất cao trong thời gian dài. Thiết kế đậm chất gaming với tông đen - tím hiện đại, khối lượng vừa phải dễ mang theo.\n\nMáy hỗ trợ đầy đủ cổng kết nối gồm USB-C, 3 cổng USB-A, HDMI 2.1, LAN RJ45, Wi-Fi 6, tiện lợi cho mọi nhu cầu học tập, chơi game hay làm việc. Đây là lựa chọn lý tưởng cho game thủ phổ thông hoặc sinh viên ngành đồ họa cần cấu hình mạnh, giá tốt.', 1),
(58, 'lapgaming', 2, 'Intel i5', ' 16 GB', 'Integrated', '512 GB', '15.6&quot; FHD 120Hz', 1, 12000000, 12, 'máy rất ổn trongvtaamf giá ', 1),
(59, 'lap gaming', 2, 'Intel i5', ' 16 GB', 'INTEL ARISXE', '512 GB', '1920x1080', 1, 120000, 12, 'cũng tạm tạm', 1);

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
('khaylodepzai', 11, 'khay22444@', 0, 1),
('lamvanc', 5, '123@456789', 0, 1),
('lenhat', 14, '123@4567', 0, 1),
('nguyenp', 18, '123@456789', 0, 1),
('nguyenthibetu', 10, '12345678@', 0, 1),
('nguyenthitele', 6, '12345678!@#', 0, 1),
('nguyenvanaa', 9, '23092004@@@', 0, 1),
('phanvanc', 7, '123@5678', 0, 1),
('phu', 13, '123@4567', 0, 1),
('phunguyen', 12, '123@4567', 0, 1),
('phuth', 16, '123@4567', 0, 1),
('thanhphu', 15, '123@4567', 0, 1);

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
(7, 12, 6, '2025-06-22 21:29:59'),
(8, 12, 2, '2025-06-22 22:05:34'),
(10, 12, 1, '2025-06-22 22:20:59'),
(15, 16, 1, '2025-07-04 20:09:46'),
(16, 16, 2, '2025-07-04 20:09:48'),
(17, 16, 4, '2025-07-04 20:09:49'),
(18, 16, 5, '2025-07-04 20:09:50'),
(19, 16, 11, '2025-07-04 20:09:55'),
(21, 14, 1, '2025-07-07 20:44:06'),
(22, 18, 1, '2025-07-07 23:18:25'),
(23, 14, 2, '2025-07-07 23:27:27'),
(24, 14, 4, '2025-07-07 23:27:28'),
(26, 14, 10, '2025-07-07 23:35:42'),
(27, 12, 19, '2025-07-10 12:26:35'),
(28, 11, 2, '2025-07-10 20:41:13'),
(29, 11, 1, '2025-07-10 20:41:14'),
(31, 15, 2, '2025-07-11 10:18:38'),
(33, 13, 7, '2025-07-13 09:42:03'),
(34, 13, 10, '2025-07-13 09:42:05'),
(36, 13, 1, '2025-07-13 10:19:25'),
(37, 13, 2, '2025-07-13 10:19:31');

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
-- Chỉ mục cho bảng `email_otp`
--
ALTER TABLE `email_otp`
  ADD PRIMARY KEY (`id`);

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
-- Chỉ mục cho bảng `khuyenmai`
--
ALTER TABLE `khuyenmai`
  ADD PRIMARY KEY (`MaKhuyenMai`);

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
  MODIFY `MaBinhLuan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT cho bảng `chitiethoadonban`
--
ALTER TABLE `chitiethoadonban`
  MODIFY `MaChiTietHoaDonBan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=184;

--
-- AUTO_INCREMENT cho bảng `diachi`
--
ALTER TABLE `diachi`
  MODIFY `MaDiaChi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT cho bảng `email_otp`
--
ALTER TABLE `email_otp`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT cho bảng `giohang`
--
ALTER TABLE `giohang`
  MODIFY `MaGioHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=310;

--
-- AUTO_INCREMENT cho bảng `hinhanh`
--
ALTER TABLE `hinhanh`
  MODIFY `MaHinhAnh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;

--
-- AUTO_INCREMENT cho bảng `hoadonban`
--
ALTER TABLE `hoadonban`
  MODIFY `MaHoaDonBan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=270;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MaKhachHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT cho bảng `khuyenmai`
--
ALTER TABLE `khuyenmai`
  MODIFY `MaKhuyenMai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

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
  MODIFY `MaSanPham` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT cho bảng `yeuthich`
--
ALTER TABLE `yeuthich`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

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
