<?php

$conn = new mysqli("localhost", "root", "", "db_esami");

if ($conn->connect_error) {
    die("Connection Error:" . $conn->connect_error);
}

if (isset($_POST["nome"]) && isset($_POST["cognome"]) && isset($_POST["codicefiscale"]) && isset($_POST["datanascita"]) && isset($_POST["sesso"])) {
    $nome = $_POST["nome"];
    $cognome = $_POST["cognome"];
    $codicefiscale = $_POST["codicefiscale"];
    $datanascita = $_POST["datanascita"];
    $sesso = $_POST["sesso"];

    if (is_string($nome) && is_string($cognome) && strlen($codicefiscale) == 16 && preg_match('/^\d{4}-\d{2}-\d{2}$/', $datanascita) && in_array($sesso, array("M", "F", "A"))) {
        $sql = "INSERT INTO cittadino(nome, cognome, codicefiscale, datanascita, sesso) VALUES (?, ?, ?, ?, ?)";
        if ($stmt = $conn->prepare($sql)) {
            $stmt->bind_param("sssss", $nome, $cognome, $codicefiscale, $datanascita, $sesso);
            if ($stmt->execute()) {
                echo "Worka";
            } else {
                echo "Non worka";
            }
        } else {
            echo $stmt->error;
        }
    } else {
        echo "Wrong values";
    }
} else {
    echo "Values not set";
}

if (isset($_POST["idcittadino"])) {
    $idcittadino = $_POST["idcittadino"];
    $sql = "SELECT * FROM cittadino WHERE idcittadino = ?";
    if ($stmt = $conn->prepare($sql)) {
        $stmt->bind_param("i", $idcittadino);
        if ($stmt->execute()) {
            $result = $stmt->get_result();
            $data = $result->fetch_all(MYSQLI_ASSOC);
        } else {
            echo "Diocane";
        }
    } else {
        echo $stmt->error;
    }
} else {
    $sql = "SELECT * FROM cittadino";
    if ($stmt = $conn->prepare($sql)) {
        if ($stmt->execute()) {
            $result = $stmt->get_result();
            $data = $result->fetch_all(MYSQLI_ASSOC);
        } else {
            echo "Diocane";
        }
    } else {
        echo $stmt->error;
    }
}

echo "<table>
        <th>Nome</th>
        <th>Cognome</th>
        <th>Codice Fiscale</th>
        <th>Data di nascita</th>
        <th>Sesso</th>";

for ($i = 0; $i < count($data); $i++) {
    echo "<tr>
            <td>{$data[$i]['nome']}</td>
            <td>{$data[$i]['cognome']}</td>
            <td>{$data[$i]['codicefiscale']}</td>
            <td>{$data[$i]['datanascita']}</td>
            <td>{$data[$i]['sesso']}</td>
          </tr>";
}

echo "</table>";

$conn->close();
