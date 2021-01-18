<?php 
	//Creating a connection
	include 'db.php'; 
	$con = mysqli_connect(HOST, USER, PASSWORD, DB); 
    if (mysqli_connect_errno())
    {
       echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }  
	$sql= "SELECT * FROM `guest_information` WHERE 1 ORDER BY `g_lastname` ASC";
	
	$result = mysqli_query($con ,$sql);
	
	while ($row = mysqli_fetch_assoc($result)) {
		
		$array[] = $row;
		
	}
	header('Content-Type:Application/json');
	
	echo json_encode($array);
 
    mysqli_free_result($result);
 
    mysqli_close($con); 
 ?>