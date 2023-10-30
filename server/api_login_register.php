<?php

header('Access-Control-Allow-Origin: *');

$servername = "localhost"; // Địa chỉ máy chủ cơ sở dữ liệu
$username = "root"; // Tên người dùng cơ sở dữ liệu
$password = "ndthrk3072002"; // Mật khẩu cơ sở dữ liệu
$database = "my_database"; // Tên cơ sở dữ liệu
// Tạo kết nối đến cơ sở dữ liệu
$conn = new mysqli($servername, $username, $password, $database);

// Xử lý yêu cầu đăng nhập
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['login'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];
    
    // Kiểm tra thông tin đăng nhập
    $query = "SELECT * FROM user_info WHERE username = '$username' AND password = '$password'";
    $result = mysqli_query($conn, $query);
    
    if (mysqli_num_rows($result) === 1) {
        $response = array('status' => 'success', 'message' => 'Đăng nhập thành công');
    } else {
        $response = array('status' => 'error', 'message' => 'Tài khoản hoặc mật khẩu không chính xác');
    }
    
    echo json_encode($response);
}

// Xử lý yêu cầu đăng ký
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['register'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];
    
    // Kiểm tra xem tên người dùng đã tồn tại trong cơ sở dữ liệu chưa
    $check_query = "SELECT * FROM user_info WHERE username = '$username'";
    $check_result = mysqli_query($conn, $check_query);
    
    if (mysqli_num_rows($check_result) === 0) {
        // Chưa có tên người dùng, thêm vào cơ sở dữ liệu
        $insert_query = "INSERT INTO user_info (username, password) VALUES ('$username', '$password')";
        $insert_result = mysqli_query($conn, $insert_query);
        
        if ($insert_result) {
            $response = array('status' => 'success', 'message' => 'Đăng ký thành công');
        } else {
            $response = array('status' => 'error', 'message' => 'Đăng ký thất bại');
        }
    } else {
        $response = array('status' => 'error', 'message' => 'Tên người dùng đã tồn tại');
    }
    echo json_encode($response);
}
?>