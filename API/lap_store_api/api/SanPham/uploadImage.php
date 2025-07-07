<?php
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['file'])) {
    $fileTmpPath = $_FILES['file']['tmp_name'];
    $fileName = $_FILES['file']['name'];
    $fileExtension = strtolower(pathinfo($fileName, PATHINFO_EXTENSION));

    $allowedExtensions = ['jpg', 'jpeg', 'png', 'gif'];
    if (!in_array($fileExtension, $allowedExtensions)) {
        echo json_encode(['success' => false, 'message' => 'File không hợp lệ']);
        exit;
    }

    $newFileName = 'sanpham_' . time() . '.' . $fileExtension;
    $uploadPath = '../../uploads/sanpham/' . $newFileName;

    if (move_uploaded_file($fileTmpPath, $uploadPath)) {
        $imageUrl = 'http://192.168.3.49/lap_store_api/uploads/sanpham/' . $newFileName;
        echo json_encode(['success' => true, 'imageUrl' => $imageUrl]);
    } else {
        echo json_encode(['success' => false, 'message' => 'Upload thất bại']);
    }
} else {
    echo json_encode(['success' => false, 'message' => 'Không có file']);
}
