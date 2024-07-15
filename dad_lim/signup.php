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

if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($data['username']) && isset($data['password']) && isset($data['email']) && isset($data['phone_number'])) {
    $username = $data['username'];
    $password = password_hash($data['password'], PASSWORD_BCRYPT); // Use bcrypt to hash the password
    $email = $data['email'];
    $phone_number = $data['phone_number'];

    // SQL to insert a new user
    $sql = "INSERT INTO user (username, password, email, phone_number) VALUES ('$username', '$password', '$email', '$phone_number')";

    if ($conn->query($sql) === TRUE) {
        echo json_encode(["message" => "User signed up successfully"]);
    } else {
        echo json_encode(["error" => "Error signing up: " . $conn->error]);
    }
} else {
    echo json_encode(["error" => "Invalid request"]);
}

$conn->close();
?>
