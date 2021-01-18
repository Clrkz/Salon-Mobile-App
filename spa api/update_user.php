 
<?php 
include 'db.php';  

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
 
 
 
 
            $id = $_GET['id'];
			$firstname = $_GET['firstname'];
            $lastname = $_GET['lastname'];
            $contact =$_GET['contact'];
            $facebook = $_GET['facebook'];
            $gender =$_GET['gender'];
			$role =$_GET['role'];
            $email =$_GET['email'];
            $address =$_GET['address'];
            $password = $_GET['password'];
            $ImageName =$_GET['ImageName']; 
            $oemail =$_GET['oemail'];  
			$hashpass = md5($password);
			
 
    
if($oemail==$email ){
	$query = "";
 if($password===""){
	  $query = "UPDATE `user_information`SET `u_firstname`='$firstname', `u_lastname`='$lastname', `u_address`='$address', `u_phone_number`='$contact', `u_fb_name`='$facebook', `u_gender`='$gender', `u_role`='$role',`u_gmail`='$email', `u_picture`='$ImageName' WHERE u_information_id_PK = '$id'";
 }else{
	   $query = "UPDATE `user_information`SET `u_firstname`='$firstname', `u_lastname`='$lastname', `u_address`='$address', `u_phone_number`='$contact', `u_fb_name`='$facebook', `u_gender`='$gender',`u_role`='$role', `u_gmail`='$email', `u_picture`='$ImageName' , `u_password`= '$hashpass' WHERE u_information_id_PK = '$id'";
  }
  if(mysqli_query($con, $query)){
			echo 'success';
		}else{
			echo 'error';
		}   
}else{
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
		$query = "";
 if($password===""){
	  $query = "UPDATE `user_information`SET `u_firstname`='$firstname', `u_lastname`='$lastname', `u_address`='$address', `u_phone_number`='$contact', `u_fb_name`='$facebook', `u_gender`='$gender', `u_role`='$role',`u_gmail`='$email', `u_picture`='$ImageName' WHERE u_information_id_PK = '$id'";
 }else{
	   $query = "UPDATE `user_information`SET `u_firstname`='$firstname', `u_lastname`='$lastname', `u_address`='$address', `u_phone_number`='$contact', `u_fb_name`='$facebook', `u_gender`='$gender',`u_role`='$role', `u_gmail`='$email', `u_picture`='$ImageName' , `u_password`= '$hashpass' WHERE u_information_id_PK = '$id'";
  }
  if(mysqli_query($con, $query)){
			echo 'success';
		}else{
			echo 'error';
		}   
 }
	  
 }
  
}
	mysqli_close($con);

?>