 
<?php 
include 'db.php';  

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
 
            $id = $_GET['id']; 
            $status = $_GET['status']; 
			
	  $query = "UPDATE `guest_information`SET `g_status`='$status' WHERE guest_id_PK = '$id'";

  if(mysqli_query($con, $query)){
			echo 'success';
		}else{
			echo 'error';
		}   
 
	mysqli_close($con);

?>