<!DOCTYPE html>
<html lang="it">

<head>
    <title>Mostra</title>
</head>

<body>
    <?php
    $conn = new mysqli("localhost", "root", "", "febbraio");

    if ($conn->connect_error) {
        exit("dioc aneee" . $conn->connect_error);
    }

    echo "<h1>db</h1>";
    $sql = "SELECT chiave, valore FROM dati";
    if ($stmt = $conn->prepare($sql)) {
        if ($stmt->execute()) {
            $result = $stmt->get_result();
            $data = $result->fetch_all(MYSQLI_ASSOC);
            echo "<ul>";
            for ($i = 0; $i < count($data); $i++) {
                echo "<li>{$data[$i]['chiave']}: {$data[$i]['valore']}</li>";
            }
            echo "</ul>";
            if ($result->num_rows == 0) {
                echo "non ci sono valori sul db";
            }
        } else {
            echo "Error " . $stmt->error;
        }
    } else {
        echo "Error " . $stmt->error;
    }

    echo "<h1>cookie</h1>";
    if (count($_COOKIE) > 0) {
        echo "<ul>";
        foreach ($_COOKIE as $key => $value) {
            echo "<li>{$key}: {$value}</li>";
        }
        echo "</ul>";
    } else {
        echo "non ci sono cookie settati";
    }

    $conn->close();
    ?>
</body>

</html>