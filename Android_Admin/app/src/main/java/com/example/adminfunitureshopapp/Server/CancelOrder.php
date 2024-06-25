<?php
include "connect.php";

$id = $_POST["id"];

$query = "DELETE FROM `orderdetail` WHERE `idOrder`=$id";

if (mysqli_query($conn, $query)) {
    $arr = [
        1
    ];
} else {
    $arr = [
        'success' => false,
        'message' => 'Unsuccess',
        'result' => null
    ];
}

$query = "DELETE FROM `order` WHERE `id`=$id";

if (mysqli_query($conn, $query)) {
    $arr = [
        1
    ];
} else {
    $arr = [
        'success' => false,
        'message' => 'Unsuccess',
        'result' => null
    ];
}

print_r(json_encode($arr));?>