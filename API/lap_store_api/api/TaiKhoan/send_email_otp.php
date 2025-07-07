<?php
ini_set('display_errors', 1);
error_reporting(E_ALL);

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require '../../vendor/autoload.php';
require_once('../../config/database.php');

// ✅ Sửa chỗ này
$db = new database();
$conn = $db->Connect();  // <-- Quan trọng

header('Content-Type: application/json; charset=UTF-8');

header('Content-Type: application/json; charset=UTF-8');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Content-Type, Authorization');

try {
    // Đọc input từ Android app
    $raw = file_get_contents('php://input');
    $data = json_decode($raw, true);

    if (!isset($data['email'])) {
        throw new Exception("Thiếu trường email");
    }

    $email = trim($data['email']);
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        throw new Exception("Email không hợp lệ");
    }

    // Tạo mã OTP
    $otp = rand(100000, 999999);
    $expires_at = date("Y-m-d H:i:s", strtotime('+5 minutes'));

    // Lưu OTP vào MySQL
    $stmt = $conn->prepare("INSERT INTO email_otp (email, otp_code, expires_at) VALUES (?, ?, ?)");
    $stmt->execute([$email, $otp, $expires_at]);

    if (!$stmt->execute()) {
        throw new Exception("Không thể lưu OTP vào cơ sở dữ liệu");
    }

    // Gửi email bằng PHPMailer
    $mail = new PHPMailer(true);
    $mail->isSMTP();
    $mail->Host       = 'smtp.gmail.com';
    $mail->SMTPAuth   = true;
    $mail->Username   = 'lapstore.sender@gmail.com';
    $mail->Password   = 'onca nqov baok yuej'; // App password
    $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
    $mail->Port       = 587;

    $mail->setFrom('lapstore.sender@gmail.com', 'LapStore');
    $mail->addAddress($email);

    $mail->isHTML(true);
    $mail->CharSet = 'UTF-8';
    $mail->Subject = 'Xác thực tài khoản LapStore';
    $mail->Body    = "Mã xác thực của bạn là: <b>{$otp}</b>. Mã có hiệu lực trong 5 phút.";

    $mail->send();

    echo json_encode(["success" => true, "message" => "OTP đã gửi đến email"]);
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(["success" => false, "message" => $e->getMessage()]);
}
?>