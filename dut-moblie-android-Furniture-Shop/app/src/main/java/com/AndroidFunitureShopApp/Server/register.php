<?php
include "connect.php";
$username = $_POST['username'];
$password = $_POST['password'];
$role = $_POST['role'];
$fullname = $_POST['fullname'];
$imageAva = $_POST['imageAva'];
$defaultAdress = $_POST['defaultAdress'];
$email = $_POST['email'];
$phone = $_POST['phone'];

//check exits account
$query = "SELECT * FROM `user` WHERE `username` = '$username'";
$data = mysqli_query($conn, $query);
$numrows = mysqli_num_rows($data);

if ($numrows > 0) {
    $arr = [
        'success' => false,
        'message' => 'Account name has been exited'
    ];
}
else{
    $query = 'Insert into `user` (username, password, role, fullname, imageAva, defaultAdress, email, phone) values ("'.$username.'","'.$password.'","'.$role.'","'.$fullname.'","'.$imageAva.'","'.$defaultAdress.'","'.$email.'","'.$phone.'")';
    $data = mysqli_query($conn, $query);

    if ($data == true){
        $arr =[
            'success' => true,
            'message' => 'Success'
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => 'Unsuccess'
        ];
    }
}

print_r(json_encode($arr));