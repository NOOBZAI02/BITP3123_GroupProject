<?php

include 'conn.php';

header('Content-Type: application/json');

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die(json_encode(["error" => "Connection failed: " . $conn->connect_error]));
}

$data = json_decode(file_get_contents('php://input'), true);

if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($data['cancel_booking_id'])) {
    $bookingId = $data['cancel_booking_id'];

    // SQL to delete a booking
    $sql = "DELETE FROM booking WHERE id = $bookingId";

    if ($conn->query($sql) === TRUE) {
        echo json_encode(["message" => "Booking cancelled successfully"]);
    } else {
        echo json_encode(["error" => "Error cancelling booking: " . $conn->error]);
    }
} else {
    echo json_encode(["error" => "Invalid request"]);
}

$conn->close();
?>
