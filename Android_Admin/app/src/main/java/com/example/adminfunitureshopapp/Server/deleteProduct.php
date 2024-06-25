<?php
include "connect.php";

$id = $_POST["productId"];

$query = "DELETE FROM `product` WHERE `id`=$id";

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





