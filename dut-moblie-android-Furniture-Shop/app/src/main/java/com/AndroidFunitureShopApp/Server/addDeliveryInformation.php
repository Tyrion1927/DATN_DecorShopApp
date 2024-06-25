<?php
include "connect.php";
$user_id = $_POST['user_id'];
$name = $_POST['name'];
$phone = $_POST['phone'];
$city = $_POST['city'];
$district = $_POST['district'];
$ward = $_POST['ward'];
$street = $_POST['street'];
$notes = $_POST['notes'];

$query = "INSERT INTO `delivery_information` (`user_id`, `name`, `phone`, `city`, `district`, `ward`, `street`, `notes`) VALUES ('$user_id', '$name', '$phone', '$city', '$district', '$ward', '$street', '$notes')";

$data = mysqli_query($conn, $query);
$result = array();

if ($data) {
    $select_query = "SELECT * FROM `delivery_information` WHERE `user_id`=$user_id";
    $row = mysqli_fetch_assoc(mysqli_query($conn, $select_query));
    $result[] =  ($row);
}

if($data) {
    $arr =[
        'success' => true,
        'message' => 'Success',
        'result' => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message' => 'Unsuccess',
        'result' => $result
    ];
}
print_r(json_encode($arr));
