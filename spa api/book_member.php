
<?php
include 'db.php';
$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");

		      $cid = $_GET['cid'];
              $mem = $_GET['mem'];
              $sid =$_GET['sid'];
              $date =$_GET['date'];  
              $time =$_GET['time'];  
			   
			$query = "INSERT INTO `booking`( `guest_id`, `service_id`, `member`, `date`, `time`, `status`, `dateadded`) VALUES ('$cid','$sid','$mem','$date','$time',0,now())";
					 if(mysqli_query($con, $query)){
						echo 'success';
					}else{
						echo 'error';
					}    
	mysqli_close($con);
?>