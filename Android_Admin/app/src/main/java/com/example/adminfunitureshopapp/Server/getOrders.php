<?php
include "connect.php";
$query = "SELECT `order`.*, `user`.`fullname`, `orderdetail`.`idProduct`, `orderdetail`.`quantity`
          FROM `order`
          JOIN `user` ON `order`.`idUser` = `user`.`id`
          JOIN `orderdetail` ON `order`.`id` = `orderdetail`.`idOrder`
          ORDER BY `order`.`id` ASC"; // Sắp xếp theo id đơn hàng tăng dần

$data = mysqli_query($conn, $query);
$result = array();

$currentOrderId = null;
$currentOrder = null;
while ($row = mysqli_fetch_assoc($data)) {
    $id = $row['id'];
    $idProduct = $row['idProduct'];
    $quantity = $row['quantity'];

    // Nếu đơn hàng hiện tại khác với đơn hàng trước đó thì đó là một đơn hàng mới
    if ($id != $currentOrderId) {
        // Nếu đơn hàng trước đó đã được lưu lại thì đưa vào mảng result
        if ($currentOrder) {
            $result[] = $currentOrder;
        }
        // Tạo đơn hàng mới
        $currentOrderId = $id;
        $currentOrder = [
            'id' => $id,
            'idUser' => $row['idUser'],
            'phone' => $row['phone'],
            'address' => $row['address'],
            'email' => $row['email'],
            'totalPrice' => $row['totalPrice'],
            'quantity' => $row['quantity'],
            'status' =>$row['status'],
            'fullname' => $row['fullname'],
            'item' => [],
        ];
    }

    // Lấy thông tin sản phẩm từ bảng product
    $queryProduct = "SELECT * FROM `product` WHERE `id` = $idProduct";
    $dataProduct = mysqli_query($conn, $queryProduct);
    $product = mysqli_fetch_assoc($dataProduct);

    // Thêm thông tin sản phẩm vào mảng items
    $item = [
        'id' => $product['id'],
        'name' => $product['name'],
        'quantity' => $quantity,
        'imageUrl' => $product['imageUrl'],
        'price' => $product['price'],
    ];
    $currentOrder['item'][] = $item;
}

// Đưa đơn hàng cuối cùng vào mảng result
if ($currentOrder) {
    $result[] = $currentOrder;
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
