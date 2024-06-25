<?php
include "connect.php";
$idUser = $_POST['idUser'];

$query = "SELECT * FROM `order` WHERE `idUser` = '$idUser'";
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
    $truyVan = 'SELECT * FROM `orderdetail` INNER JOIN `product` ON orderdetail.idProduct = product.id WHERE orderdetail.idOrder = ' . $row['id'] . '';
    $data1 = mysqli_query($conn, $truyVan);
    $item = array();
    while ($row1 = mysqli_fetch_assoc($data1)) {
        $item[] = $row1;
    }

    $row['item'] = $item;
    $result[] = ($row);
}

if (!empty($result)) {
    $arr = $result;
} else {
    $arr = [
        'success' => false,
        'message' => 'Unsuccess',
        'result' => $result
    ];
}
print_r(json_encode($arr));
