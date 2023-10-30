<?php
header('Access-Control-Allow-Origin: *');
//Thông tin server MySQL
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
// Câu lệnh select toàn bộ dữ liệu
$queue="select * from phone_data";
// Thực thi kết nối
$result=$conn->query($queue);

$counter = 0;
$data = array();
while ($row = $result->fetch_assoc()) //line.Readline
{
    $data[] = $row;
    $counter++;
}

$json = json_encode($data);

// Lưu dữ liệu vào tệp JSON
// file_put_contents('data.json', $json);

$conn->close();
echo "Số bản ghi: " . $counter . "<br>";
?>