
<?php
include 'db.php';
$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");

		      $id = $_GET['id'];
		      $service_type = $_GET['service_type'];
              $service_name = $_GET['service_name'];
              $service_price =$_GET['service_price'];
              $service_duration =$_GET['service_duration']; 
			  
			   
			$query = "UPDATE `salon_service` SET `service_type`='$service_type', `service_name`='$service_name', `service_price`='$service_price', `service_duration`='$service_duration'  WHERE salon_service_id='$id' ";
					 if(mysqli_query($con, $query)){
						echo 'success';
					}else{
						echo 'error';
					}    
	mysqli_close($con);
?>