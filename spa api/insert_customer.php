 
<?php 
include 'db.php';  

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
 
            $firstname = $_GET['firstname'];
            $lastname = $_GET['lastname'];
            $contact =$_GET['contact'];
            $facebook = $_GET['facebook'];
            $gender =$_GET['gender'];
            $email =$_GET['email'];
            $password = $_GET['password'];
            $ImageName =$_GET['ImageName']; 
			$hashpass = md5($password);
  
 $query = "SELECT * from guest_information where g_gmail='$email' ";
	$recordExists = mysqli_fetch_array(mysqli_query($con, $query)); 
 if($recordExists){
	 echo 'exist';
 }else{
	  $query = "SELECT * from user_information where u_gmail='$email' ";
	$recordExists = mysqli_fetch_array(mysqli_query($con, $query)); 
 if($recordExists){
	 echo 'exist';
 }else{ 
		 $query = "INSERT INTO `guest_information`( `g_firstname`, `g_lastname`, `g_contact_number`, `g_fb_name`, `g_gender`, `g_gmail`, `g_password`, `g_picture`, `g_date_time_added`, `g_status`) 
		 VALUES ('$firstname','$lastname','$contact','$facebook','$gender','$email','$hashpass','$ImageName', now(),0)";
		 if(mysqli_query($con, $query)){
			echo 'success';
		}else{
			echo 'error';
		}   
 }
 }
	mysqli_close($con);

?>