<?php
    include "connect.php";

    $id = $_POST["id"];
    $name = $_POST["name"];
    $quantity = $_POST["quantity"];
    $imageUrl = $_POST["imageUrl"];
    $originalPrice = $_POST["originalPrice"];
    $discount = $_POST["discount"];
    $detail = $_POST["detail"];
    $type = $_POST["type"];

    $query = "UPDATE `product` SET `name`='$name',`quantity`=$quantity,`imageUrl`='$imageUrl',`originalPrice`=$originalPrice,`discount`=$discount,`price`= $originalPrice * (1 - $discount/100),`detail`='$detail',`type`='$type' WHERE `id`=$id";

    if (mysqli_query($conn, $query)) {
        $arr = [
            'success' => true,
            'message' => 'Success',
            'result' => null
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => 'Unsuccess',
            'result' => null
        ];
    }
    echo json_encode($arr);
?>