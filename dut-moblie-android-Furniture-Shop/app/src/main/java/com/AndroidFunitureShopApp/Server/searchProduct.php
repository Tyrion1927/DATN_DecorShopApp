<?php
    include "connect.php";
    $search = $_POST['search'];
    if (empty($search)){
        $arr = [
            'success' => false,
            'message' => 'Unsuccess',
        ];
    }
    else{
        $query = "SELECT * FROM `product` WHERE `name` LIKE '%".$search."%'";
        $data = mysqli_query($conn, $query);
        $result = array();
    
        while ($row = mysqli_fetch_assoc($data)) {
            $result[] = ($row);
        }
    
        if(!empty($result)) {
            $arr =
                $result
            ;
        } else {
            $arr = [
                'success' => false,
                'message' => 'Unsuccess',
                'result' => $result
            ];
        }
        print_r(json_encode($arr));
    }
   
?>