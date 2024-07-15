<?php
include 'conn.php';

header('Content-Type: application/json');

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT room_id, check_in_date, check_out_date FROM booking";
$result = $conn->query($sql);

$bookings = array();

if ($result->num_rows > 0) {
    // Output data of each row
    while($row = $result->fetch_assoc()) {
        $bookings[] = $row;
    }
}

echo json_encode($bookings);

$conn->close();
?>
