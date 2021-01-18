
<?php
include 'db.php';
$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");

		      $bid = $_GET['bid']; 
			 
						$query = "UPDATE `accept_book` SET `status`=1 WHERE `id`='$bid'";
					 if(mysqli_query($con, $query)){
						echo 'success';
					}else{
						echo 'error';
					}   
	mysqli_close($con);
?>   
			