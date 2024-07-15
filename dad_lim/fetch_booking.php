<?php
header('Content-Type: application/json');

include "conn.php";

$userName = $_GET['username'];

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT id, room_id, room_type, total_price FROM booking WHERE username = '$userName'";
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
