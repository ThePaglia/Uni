<?php

$conn = new mysqli("localhost", "root", "", "esami");

if ($conn->connect_error) {
    die("Connection Error: " . $conn->connect_error);
}



