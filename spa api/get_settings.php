<?php

include 'db.php';

if($_SERVER['REQUEST_METHOD'] == 'POST'){
 
  
 
	$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
	
	$query = "SELECT * FROM `settings` WHERE 1 ";
	$result = mysqli_query($con, $query);
	$data = mysqli_fetch_array($result);
 
	if(isset($data)){
  $data["success"] = 1;
  $data["hfrom"];
  $data["hto"];
  $data["date_close"]; 
	} 

  echo json_encode($data);
	mysqli_close($con);
}



?>