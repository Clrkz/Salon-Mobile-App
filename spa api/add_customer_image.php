<?php
/*
 $file_path = "images/";
  $file_path = $file_path . basename( $_FILES['uploaded_file']['name']);

  if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
  echo "success";
  } else{ echo "fail";}
  */
   if (is_uploaded_file($_FILES['bill']['tmp_name'])) {
    $uploads_dir = 'images/';
                            $tmp_name = $_FILES['bill']['tmp_name'];
                            $pic_name = $_FILES['bill']['name'];
                            move_uploaded_file($tmp_name, $uploads_dir.$pic_name);
                            }
               else{
                   echo "File not uploaded successfully.";
           }


?>