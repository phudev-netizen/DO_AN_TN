<?php
// Bật debug PHP
ini_set('display_errors', 1);
error_reporting(E_ALL);

// Kết nối CSDL (PDO)
require_once __DIR__ . '/../../config/database.php';
$db = new database();
$conn = $db->Connect();

header('Content-Type: application/json; charset=UTF-8');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Content-Type, Authorization');

try {
    // Đọc input JSON
    $data = json_decode(file_get_contents('php://input'), true);
    $email = trim($data['email'] ?? '');
    $otp   = trim($data['otp']   ?? '');

    if (!$email || !$otp) {
        throw new Exception("Thiếu email hoặc mã OTP");
    }

    // Truy vấn OTP mới nhất cho email
    $stmt = $conn->prepare(
        "SELECT otp_code, expires_at FROM email_otp WHERE email = :email ORDER BY id DESC LIMIT 1"
    );
    $stmt->execute([':email' => $email]);
    $row = $stmt->fetch(PDO::FETCH_ASSOC);

    if (!$row) {
        echo json_encode(["success"=>false, "message"=>"Chưa gửi OTP hoặc email không tồn tại"]);
        exit;
    }

    if ($row['otp_code'] !== $otp) {
        echo json_encode(["success"=>false, "message"=>"Sai mã OTP"]);
        exit;
    }

    if (strtotime($row['expires_at']) < time()) {
        echo json_encode(["success"=>false, "message"=>"Mã OTP đã hết hạn"]);
        exit;
    }

    // Xoá bản ghi sau khi xác thực (tuỳ chọn)
    $del = $conn->prepare("DELETE FROM email_otp WHERE email = :email");
    $del->execute([':email'=>$email]);

    echo json_encode(["success"=>true, "message"=>"Xác thực thành công"]);
} catch (Exception $e) {
    http_response_code(400);
    echo json_encode(["success"=>false, "message"=>$e->getMessage()]);
}
?>