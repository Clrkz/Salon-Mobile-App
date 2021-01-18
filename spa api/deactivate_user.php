 
<?php 
include 'db.php';  

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
 
            $id = $_GET['id']; 
            $status = $_GET['status']; 
			
	  $query = "UPDATE `user_information`SET `u_status`='$status' WHERE u_information_id_PK = '$id'";

  if(mysqli_query($con, $query)){
			echo 'success';
		}else{
			echo 'error';
		}   
 
	mysqli_close($con);

?>