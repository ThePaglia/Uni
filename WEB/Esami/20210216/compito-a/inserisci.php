<?php

$conn = new mysqli("localhost", "root", "", "febbraio");

if ($conn->connect_error) {
    exit("dioc aneee" . $conn->connect_error);
}

if (isset($_GET["chiave"]) && isset($_GET["valore"]) && isset($_GET["metodo"])) {
    $metodo = $_GET["metodo"];
    $chiave = $_GET["chiave"];
    $valore = $_GET["valore"];

    $esiste = false;

    if ($metodo == "cookie") {
        setcookie($chiave, $valore);
    } else if ($metodo == "db") {
        $sel = "SELECT * FROM dati WHERE chiave = ?";
        if ($stmt = $conn->prepare($sel)) {
            $stmt->bind_param("s", $chiave);
            if ($stmt->execute()) {
                $result = $stmt->get_result();
                if ($result->num_rows > 0) {
                    $esiste = true;
                } else {
                    $esiste = false;
                }
            } else {
                echo "Error " . $stmt->error;
            }
        } else {
            echo "Error " . $stmt->error;
        }
        if ($esiste == false) {
            $sql = "INSERT INTO dati(chiave, valore) VALUES (?,?)";
            if ($stmt = $conn->prepare($sql)) {
                $stmt->bind_param("ss", $chiave, $valore);
                if ($stmt->execute()) {
                    echo "chiave valore inserito correttamente";
                } else {
                    echo "Error " . $stmt->error;
                }
            } else {
                echo "Error " . $stmt->error;
            }
        } else {
            $sql = "UPDATE dati SET valore = ? WHERE chiave = ?";
            if ($stmt = $conn->prepare($sql)) {
                $stmt->bind_param("ss", $valore, $chiave);
                if ($stmt->execute()) {
                    echo "chiave valore aggiornato correttamente";
                } else {
                    echo "Error " . $stmt->error;
                }
            } else {
                echo "Error " . $stmt->error;
            }
        }
    } else {
        echo "sei stronzo, non hai messo il valore giusto";
    }
} else {
    echo "valori non settati";
}

$conn->close();
