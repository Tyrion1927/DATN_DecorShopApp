<?php
include "connect.php";

$phone = $_POST['phone'];
$email = $_POST['email'];
$iduser = $_POST['idUser'];
$address = $_POST['address'];
$totalPrice = $_POST['totalPrice'];
$quantity = $_POST['quantity'];
$orderDetail = $_POST['orderDetail'];

$query = "INSERT INTO `order`(`idUser`, `phone`, `address`, `email`, `totalPrice`, `quantity`) 
VALUES ('.$iduser.', '.$phone.', '.$address.', '.$email.', '.$totalPrice.', '.$quantity.')";

$data = mysqli_query($conn, $query);

if ($data == true) {

    $query = 'SELECT id AS idOrder from `order` WHERE `idUser` = ' . $iduser . ' ORDER BY id DESC LIMIT 1';
    $data = mysqli_query($conn, $query);
    while ($row = mysqli_fetch_assoc($data)) {
        $order = ($row);
    }

    if (!empty($order)) {
        $orderDetail = json_decode($orderDetail, true);
        foreach ($orderDetail as $key => $value) {
            $truyVan = 'INSERT INTO `orderdetail`(`idOrder`, `idProduct`, `quantity`, `price`) VALUES (' . $order['idOrder'] . ', ' . $value['id'] . ', ' . $value['quantity'] . ', ' . $value['price'] . ')';
            // echo $truyVan;
            $data = mysqli_query($conn, $truyVan);
        }
        if ($data == true) {
            $arr = [
                'success' => true,
                'message' => 'success',

            ];
        } else {
            $arr = [
                'success' => false,
                'message' => 'not success',
            ];
        }
        // print_r(json_encode($arr));
    }
} else {
    $arr = [
        'success' => false,
        'message' => 'not success',
        'result' => $result
    ];
}

print_r(json_encode($arr));
