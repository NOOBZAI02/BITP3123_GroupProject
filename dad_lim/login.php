<?php
include 'conn.php';   

$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Get the posted data
$data = json_decode(file_get_contents("php://input"));

if (isset($data->username) && isset($data->password)) {
    $username = $data->username;
    $password = $data->password;

    // Prepare and execute the query
    $sql = "SELECT * FROM user WHERE username='$username'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        // Verify the hashed password
        if (password_verify($password, $row['password'])) {
            echo json_encode(array("message" => "Login successful", "username" => $username));
        } else {
            echo json_encode(array("message" => "Invalid password"));
        }
    } else {
        echo json_encode(array("message" => "No user found"));
    }

    $conn->close();
} else {
    echo json_encode(array("message" => "Invalid input"));
}
?>
