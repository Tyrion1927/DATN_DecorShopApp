<?php
include "connect.php";
$id = $_POST['id'];

//
$query = "UPDATE `user` SET `password`='123456' WHERE id='$id'";

$data = mysqli_query($conn, $query);

if($data) {
    $arr =[
        'success' => true,
        'message' => 'Success',
    ]
    ;
} else {
    $arr = [
        'success' => false,
        'message' => 'Unsuccess',
    ];
}
print_r(json_encode($arr));