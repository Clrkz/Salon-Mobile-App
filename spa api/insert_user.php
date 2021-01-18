 
<?php 
include 'db.php';  

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
 
            $firstname = $_GET['firstname'];
            $lastname = $_GET['lastname'];
            $address =$_GET['address'];
            $ImageName =$_GET['ImageName'];  
            $gender =$_GET['gender'];
            $facebook = $_GET['facebook'];
            $email =$_GET['email'];
            $password = $_GET['password'];
            $contact =$_GET['contact'];
            $role =$_GET['role'];
			$hashpass = md5($password);
   $query = "SELECT * from user_information where u_gmail='$email' ";
	$recordExists = mysqli_fetch_array(mysqli_query($con, $query)); 
 if($recordExists){
	 echo 'exist';
 }else{
 $query = "SELECT * from guest_information where g_gmail='$email' ";
	$recordExists = mysqli_fetch_array(mysqli_query($con, $query)); 
 if($recordExists){
	 echo 'exist';
 }else{ 
	 $query = "INSERT INTO `user_information`( `u_firstname`, `u_lastname`, `u_address`, `u_picture`, `u_gender`, `u_fb_name`, `u_gmail`, `u_password`, `u_phone_number`,u_availability, u_status,`u_dateadded`,`u_role`) 
											VALUES ('$firstname','$lastname','$address','$ImageName','$gender','$facebook','$email','$hashpass','$contact',1,1, now(),'$role')";
		 if(mysqli_query($con, $query)){
			echo 'success';
		}else{
			echo 'error';
		}    
 }
	  
 }
	mysqli_close($con);

?>