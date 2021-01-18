 
<?php 
include 'db.php';  

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
 
            $id = $_GET['id'];
			$firstname = $_GET['firstname'];
            $lastname = $_GET['lastname'];
            $contact =$_GET['contact'];
            $facebook = $_GET['facebook'];
            $gender =$_GET['gender'];
            $email =$_GET['email'];
            $password = $_GET['password'];
            $ImageName =$_GET['ImageName']; 
            $oemail =$_GET['oemail']; 
			$hashpass = md5($password);
  
if($oemail==$email ){
	
	  $query = ""; 
 if($password==""){
	  $query = "UPDATE `guest_information`SET `g_firstname`='$firstname', `g_lastname`='$lastname', `g_contact_number`='$contact', `g_fb_name`='$facebook', `g_gender`='$gender', `g_gmail`='$email', `g_picture`='$ImageName' WHERE guest_id_PK = '$id'";
 }else{
	  $query = "UPDATE `guest_information`SET `g_firstname`='$firstname', `g_lastname`='$lastname', `g_contact_number`='$contact', `g_fb_name`='$facebook', `g_gender`='$gender', `g_gmail`='$email', `g_password`='$hashpass', `g_picture`='$ImageName' WHERE guest_id_PK = '$id'";
 }
 
		 if(mysqli_query($con, $query)){
			echo 'success';
		}else{
			echo 'error';
		}   
		
		
		
}else{
	
	
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
  $query = ""; 
 if($password==""){
	  $query = "UPDATE `guest_information`SET `g_firstname`='$firstname', `g_lastname`='$lastname', `g_contact_number`='$contact', `g_fb_name`='$facebook', `g_gender`='$gender', `g_gmail`='$email', `g_picture`='$ImageName' WHERE guest_id_PK = '$id'";
 }else{
	  $query = "UPDATE `guest_information`SET `g_firstname`='$firstname', `g_lastname`='$lastname', `g_contact_number`='$contact', `g_fb_name`='$facebook', `g_gender`='$gender', `g_gmail`='$email', `g_password`='$hashpass', `g_picture`='$ImageName' WHERE guest_id_PK = '$id'";
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