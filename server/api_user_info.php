<?php
$servername = "localhost"; // Địa chỉ máy chủ cơ sở dữ liệu
$username = "root"; // Tên người dùng cơ sở dữ liệu
$password = "ndthrk3072002"; // Mật khẩu cơ sở dữ liệu
$database = "my_database"; // Tên cơ sở dữ liệu


// Tạo kết nối đến cơ sở dữ liệu
$conn = new mysqli($servername, $username, $password, $database);

// Kiểm tra kết nối
if ($conn->connect_error) {
    die("Kết nối thất bại: " . $conn->connect_error);
}

// Thực hiện truy vấn SQL và lấy dữ liệu từ cơ sở dữ liệu
$sql = "SELECT * FROM user_info";
$result = $conn->query($sql);

while ($row[]=$result->fetch_assoc()) //line.Readline
{
    $json=json_encode($row);
}
echo ($json);
file_put_contents("users_infomation.json", $json);
// Đóng kết nối
$conn->close();
?>
