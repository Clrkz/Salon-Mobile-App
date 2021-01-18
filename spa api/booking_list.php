<?php 
	//Creating a connection
	include 'db.php'; 
	$con = mysqli_connect(HOST, USER, PASSWORD, DB); 
    if (mysqli_connect_errno())
    {
       echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }  
	$sql= "SELECT `id`,`dateadded`,
(SELECT concat(g_firstname,' ',g_lastname) from guest_information where guest_id_PK=b.`guest_id`) as name,
`member` as status,
(select service_name from salon_service where salon_service_id=b.`service_id`) as service,
(select service_price from salon_service where salon_service_id=b.`service_id`) as price,
(select service_duration from salon_service where salon_service_id=b.`service_id`) as duration,
`time`,`date`,
(SELECT `code` FROM `booking_payment` WHERE `booking_id`=b.`id`) as code,
(SELECT `image` FROM `booking_payment` WHERE `booking_id`=b.`id`) as image 
FROM `booking` b WHERE status=0  ORDER BY id DESC";
	
	$result = mysqli_query($con ,$sql);
	
	while ($row = mysqli_fetch_assoc($result)) {
		
		$array[] = $row;
		
	}
	header('Content-Type:Application/json');
	
	echo json_encode($array);
 
    mysqli_free_result($result);
 
    mysqli_close($con); 
 ?>