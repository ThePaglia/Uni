<?php

$conn = new mysqli("localhost", "root", "", "esami");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

function createTable($data)
{
    echo "<table>";
    foreach ($data as $row) {
        echo "<tr>";
        foreach ($row as $key => $value) {
            if ($row == $data[0]) {
                echo "<th id='$key'>$key</th>";
            }
        }
        echo "</tr>";
        echo "<tr>";
        foreach ($row as $key => $value) {
            echo "<td headers='$key'>$value</td>";
        }
        echo "</tr>";
    }
    echo "</table>";
}


function createList($data)
{
    echo "<ul>";
    foreach ($data as $row) {
        echo "<li>";
        echo "<ul>";
        foreach ($row as $key => $value) {
            echo "<li>$key: $value</li>";
        }
        echo "</ul>";
        echo "</li><br>";
    }
    echo "</ul>";
}

if (isset($_GET["format"])) {
    $format = $_GET["format"];
    if (isset($_GET["id"])) {
        $id = $_GET["id"];
        $sql = "SELECT * FROM movies WHERE id = (?)";
        if ($stmt = $conn->prepare($sql)) {
            $stmt->bind_param("i", $id);
            if ($stmt->execute()) {
                $res = $stmt->get_result();
                $data = $res->fetch_all(MYSQLI_ASSOC);
                // Creo la base di una pagina html accessibile in html5
                echo "<!DOCTYPE html>
                <html lang='en'>
                <head>
                    <meta charset='UTF-8'>
                    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
                    <title>Movies</title>
                    <style>
                        table, th, td {
                            border: 1px solid black;
                        }
                    </style>
                </head>
                <body>";
                switch ($format) {
                    case "html/table":
                        createTable($data);
                        break;
                    case "html/list":
                        createList($data);
                        break;
                    case "json":
                        echo json_encode($data);
                        break;
                    default:
                        echo "Error";
                }
                echo "</body>
                </html>";
            } else {
                echo "Error";
            }
        } else {
            echo "Error";
        }
    } else {
        $sql = "SELECT * FROM movies";
        if ($stmt = $conn->prepare($sql)) {
            if ($stmt->execute()) {
                $res = $stmt->get_result();
                $data = $res->fetch_all(MYSQLI_ASSOC);
                echo "<!DOCTYPE html>
                <html lang='en'>
                <head>
                    <meta charset='UTF-8'>
                    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
                    <title>Movies</title>
                    <style>
                        table, th, td {
                            border: 1px solid black;
                        }
                    </style>                    
                </head>
                <body>";
                switch ($format) {
                    case "html/table":
                        createTable($data);
                        break;
                    case "html/list":
                        createList($data);
                        break;
                    case "json":
                        echo json_encode($data);
                        break;
                    default:
                        echo "Error";
                }
                echo "</body>
                </html>";
            } else {
                echo "Error";
            }
        } else {
            echo "Error";
        }
    }
}

$conn->close();

?>
