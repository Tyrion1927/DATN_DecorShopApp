<?php
    include "connect.php";

    $id = $_POST["id"];
    $name = $_POST["name"];
    $imageUrl = $_POST["imageUrl"];

    $query = "UPDATE `categories` SET `name`='$name',`imageUrl`='$imageUrl' WHERE `id`=$id";

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