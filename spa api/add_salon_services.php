
<?php
include 'db.php';
$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");

		      $service_type = $_GET['service_type'];
              $service_name = $_GET['service_name'];
              $service_price =$_GET['service_price'];
              $service_duration =$_GET['service_duration']; 
			  
			   
			$query = "INSERT INTO `salon_service`( `service_type`, `service_name`, `service_price`, `service_duration`) 
										VALUES ('$service_type','$service_name','$service_price','$service_duration')";
					 if(mysqli_query($con, $query)){
						echo 'success';
					}else{
						echo 'error';
					}    
	mysqli_close($con);
?>