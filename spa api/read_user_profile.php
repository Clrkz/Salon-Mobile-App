<?php 
	//Creating a connection
	include 'db.php';
	
	$con = mysqli_connect(HOST, USER, PASSWORD, DB);
	 
    if (mysqli_connect_errno())
    {
       echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
	/*Get the id of the last visible item in the RecyclerView from the request and store it in a variable. For            the first request id will be zero.*/	
	$id = $_GET["id"]; 
	$sql= "SELECT * FROM clinic_services where c_id = '$id' and status=1 order by cs_title ASC";
	
	$result = mysqli_query($con ,$sql);
	
	while ($row = mysqli_fetch_assoc($result)) {
		
		$array[] = $row;
		
	}
	header('Content-Type:Application/json');
	
	echo json_encode($array);
 
    mysqli_free_result($result);
 
    mysqli_close($con); 
 ?>