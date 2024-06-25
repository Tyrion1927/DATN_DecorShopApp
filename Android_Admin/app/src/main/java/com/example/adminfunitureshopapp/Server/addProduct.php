<?php
    include "connect.php";

    $name = $_POST["name"];
    $quantity = $_POST["quantity"];
    $imageUrl = $_POST["imageUrl"];
    $originalPrice = $_POST["originalPrice"];
    $discount = $_POST["discount"];
    $detail = $_POST["detail"];
    $type = $_POST["type"];
    $categoryId = $_POST["categoryId"];

    $query = "INSERT INTO `product`(`name`, `quantity`, `imageUrl`, `originalPrice`, `discount`, `price`, `detail`, `type`, `categoryId`) 
                VALUES ('$name', $quantity, '$imageUrl', $originalPrice, $discount, $originalPrice * (1 - $discount/100), '$detail', '$type', '$categoryId')";


    if (mysqli_query($conn, $query)) {
        $productId = mysqli_insert_id($conn);
        $arr = [
            $products
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