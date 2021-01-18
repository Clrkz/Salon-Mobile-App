
<?php
include 'db.php';
$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");

		      $uid = $_GET['uid'];
              $bid = $_GET['bid']; 
              $status = $_GET['status']; 
			  
			   
			$query = "INSERT INTO `accept_book`(`user_id`, `book_id`, `date_accepted`, `status`) VALUES ('$uid','$bid',now(),'$status')";
					 if(mysqli_query($con, $query)){
						   
			$query = "UPDATE `booking` SET `status`= 1  WHERE `id`='$bid'";
					 if(mysqli_query($con, $query)){
						echo 'success';
					}else{
						echo 'error';
					}    
					
					}else{
						echo 'error';
					}    
	mysqli_close($con);
?>