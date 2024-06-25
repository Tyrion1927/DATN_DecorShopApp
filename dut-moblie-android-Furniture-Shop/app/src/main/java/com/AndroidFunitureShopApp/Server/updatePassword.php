<?php
include "connect.php";
$id = $_POST['id'];
$password = $_POST['password'];
//
$query = "UPDATE `user` SET `password`='$password' WHERE id='$id'";

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