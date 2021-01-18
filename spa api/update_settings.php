
<?php
include 'db.php';
$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");

		      $fhr = $_GET['fhr'];
              $thr = $_GET['thr'];
              $cdate =$_GET['cdate']; 
			   
			$query = "DELETE FROM `settings` WHERE 1";
					 if(mysqli_query($con, $query)){
						$query = "INSERT INTO `settings`( `hfrom`, `hto`, `date_close`) VALUES ('$fhr','$thr','$cdate' )";
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
			