<?php
include "connect.php";
$id = $_POST['id'];
//$username = $_POST['username'];
$password = $_POST['password'];
//$role = $_POST['role'];
$fullname = $_POST['fullname'];
$imageAva = $_POST['imageAva'];
$defaultAdress = $_POST['defaultAdress'];
$email = $_POST['email'];
$phone = $_POST['phone'];

//
$query = "UPDATE `user` SET `password`='$password',`fullname`='$fullname',`imageAva`='$imageAva',`defaultAdress`='$defaultAdress',`email`='$email',`phone`='$phone' WHERE id='$id'";

$data = mysqli_query($conn, $query);
$result = array();

if ($data) {
    $select_query = "SELECT * FROM `user` WHERE id = '$id'";
    $row = mysqli_fetch_assoc(mysqli_query($conn, $select_query));
    $result[] =  ($row);
}

if($data) {
    $arr =[
        'success' => true,
        'message' => 'Success',
        'result' => $result
    ]
    ;
} else {
    $arr = [
        'success' => false,
        'message' => 'Unsuccess',
        'result' => $result
    ];
}
print_r(json_encode($arr));