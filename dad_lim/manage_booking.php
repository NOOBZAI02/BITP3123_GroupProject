<?php

include 'conn.php'; // Include the connection file

header('Content-Type: application/json');

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die(json_encode(["error" => "Connection failed: " . $conn->connect_error]));
}

// Handle POST request for booking cancellation
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['cancel_booking_id'])) {
    $bookingId = $_POST['cancel_booking_id'];

    // SQL to delete a booking
    $sql = "DELETE FROM booking WHERE id = $bookingId";

    if ($conn->query($sql) === TRUE) {
        echo json_encode(["message" => "Booking cancelled successfully"]);
    } else {
        echo json_encode(["error" => "Error cancelling booking: " . $conn->error]);
    }
    $conn->close();
    exit();
}

// SQL to select all bookings
$sql = "SELECT id, room_id, room_type, total_price, username FROM booking";
$result = $conn->query($sql);

$bookings = [];
if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $bookings[] = $row;
    }
    echo json_encode($bookings);
} else {
    echo json_encode([]);
}

$conn->close();
?>
