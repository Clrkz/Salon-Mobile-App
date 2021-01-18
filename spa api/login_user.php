<?php

include 'db.php';

if($_SERVER['REQUEST_METHOD'] == 'POST'){

	$userID = $_POST['g_gmail'];
	$userPassword = $_POST['g_password'];
	$hashPass = md5($userPassword); 
 
	if($userID == '' || $userPassword == ''){
		echo "fail";
		exit;
	}

 
	$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
	
	$query = "SELECT * FROM guest_information WHERE g_gmail = '$userID' AND g_password = '$hashPass' ";
	$result = mysqli_query($con, $query);
	$data = mysqli_fetch_array($result);
 
	if(isset($data)){
  $data["success"] = 1;
  $data["guest_id_PK"];
  $data["g_firstname"];
  $data["g_lastname"];
  $data["g_picture"];
  $data["g_date_time_added"];
  $data["g_gender"];
  $data["g_contact_number"];
  $data["g_fb_name"];
  $data["g_gmail"];
  $data["g_status"];  
	}else{
		$query = "SELECT * FROM user_information WHERE u_gmail = '$userID' AND u_password = '$hashPass' AND u_status=1";
	$result = mysqli_query($con, $query);
	$data = mysqli_fetch_array($result); 
	if(isset($data)){
  $data["success"] = 2;
  $data["u_information_id_PK"];
  $data["u_firstname"];
  $data["u_lastname"];
  $data["u_address"];
  $data["u_picture"];
  $data["u_dateadded"];
  $data["u_gender"];
  $data["u_fb_name"];
  $data["u_gmail"]; 
  $data["u_phone_number"];  
  $data["u_availability"];  
  $data["u_status"];   
	}else{
		 $data["success"] = 0;
	} 
	}

  echo json_encode($data);
	mysqli_close($con);
}



?>