<?php
include "connect.php";

$id = $_POST["id"];

$query = "DELETE FROM `user` WHERE `id`=$id";

if (mysqli_query($conn, $query)) {
    $arr = [
        'success' => true,
        'message' => 'Success',
    ];
} else {
    $arr = [
        'success' => false,
        'message' => 'Unsuccess',
        'result' => null
    ];
}

print_r(json_encode($arr));
?>