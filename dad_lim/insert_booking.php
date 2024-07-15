<?php

include 'conn.php';

header('Content-Type: application/json');

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$data = json_decode(file_get_contents('php://input'), true);

if (isset($data['room_id']) && isset($data['room_type']) && isset($data['total_price']) && isset($data['check_in_date']) && isset($data['check_out_date']) && isset($data['username'])) {
    $roomId = $data['room_id'];
    $roomType = $data['room_type'];
    $totalPrice = $data['total_price'];
    $checkInDate = $data['check_in_date'];
    $checkOutDate = $data['check_out_date'];
    $userName = $data['username'];

    // SQL to insert a new booking
    $sql = "INSERT INTO booking (room_id, room_type, total_price, check_in_date, check_out_date, username) VALUES ('$roomId', '$roomType', '$totalPrice', '$checkInDate', '$checkOutDate', '$userName')";

    if ($conn->query($sql) === TRUE) {
        echo json_encode(["message" => "Booking inserted successfully"]);
    } else {
        echo json_encode(["error" => "Error inserting booking: " . $conn->error]);
    }
} else {
    echo json_encode(["error" => "Invalid request"]);
}

$conn->close();
?>
