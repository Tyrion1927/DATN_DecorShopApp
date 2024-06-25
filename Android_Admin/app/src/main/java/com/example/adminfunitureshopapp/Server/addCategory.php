<?php
    include "connect.php";
    $name = $_POST["name"];
    $imageUrl = $_POST["imageUrl"];

    $query = "INSERT INTO `categories`(`name`, `imageUrl`) VALUES ('$name', '$imageUrl')";

    if(mysqli_query($conn, $query)) {
        $categoryId = mysqli_insert_id($conn);
        $arr = [$categories];
    } else {
        $arr = [
            'success' => false,
            'message' => 'Error inserting categories',
            'result' => null
        ];
    }
    print_r(json_encode($arr));
?>