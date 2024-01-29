<?php

$conn = new mysqli("localhost", "root", "", "lotto");

if ($conn->connect_error) {
    die("Connection Error:" . $conn->connect_error);
}

if (isset($_GET["action"])) {
    $action = $_GET["action"];

    if (in_array($action, array("extract", "new", "check"))) {
        switch ($action) {
            case "extract":
                $sqlInsert = "INSERT INTO estrazione(numero) VALUES (?)";
                $sqlSelectAll = "SELECT numero FROM estrazione";
                $sqlCount = "SELECT COUNT(*) AS n FROM estrazione";
                $rand = rand(1, 90);
                if ($stmt = $conn->prepare($sqlCount)) {
                    if ($stmt->execute()) {
                        $result = $stmt->get_result();
                        $countNumber = $result->fetch_all(MYSQLI_ASSOC);
                    }
                }
                if ($stmt = $conn->prepare($sqlSelectAll)) {
                    if ($stmt->execute()) {
                        $result = $stmt->get_result();
                        $numbers = $result->fetch_all(MYSQLI_ASSOC);
                    }
                }
                if (!in_array($rand, $numbers) && $countNumber[0]["n"] < 5) {
                    if ($stmt = $conn->prepare($sqlInsert)) {
                        $stmt->bind_param("i", $rand);
                        if ($stmt->execute()) {
                            echo "Inserito correttamente";
                        } else {
                            echo "Non inserito";
                        }
                    } else {
                        $stmt->error;
                    }
                } else {
                    echo "Già nel db o ce ne sono già 5";
                }
                break;
            case "new":
                $sqlDeleteAll = "DELETE FROM estrazione";
                if ($stmt = $conn->prepare($sqlDeleteAll)) {
                    if ($stmt->execute()) {
                        echo json_encode(["message" => "Db deleted"]);
                    }
                }
                break;
            case "check":
                // Il check non avevo voglia di farlo
                if (isset($_POST['sequence'])) {
                    $sequence = $_POST['sequence'];
                    $numbers = explode('-', $sequence);

                    // Controllo che tutti e 5 i numeri siano presenti nel database
                    $checkAllNumbers = true;
                    foreach ($numbers as $number) {
                        $checkNumberQuery = "SELECT COUNT(*) as count FROM estrazione WHERE numero = $number";
                        $checkNumberResult = $conn->query($checkNumberQuery);
                        $numberRow = $checkNumberResult->fetch_assoc();

                        if ($numberRow['count'] == 0) {
                            $checkAllNumbers = false;
                            break;
                        }
                    }

                    if ($checkAllNumbers) {
                        echo json_encode(["message" => "Hai vinto! Tutti i numeri sono presenti nel database."]);
                    } else {
                        echo json_encode(["message" => "Mi dispiace, non hai vinto. Alcuni numeri non sono presenti nel database."]);
                    }
                } else {
                    echo json_encode(["message" => "La variabile 'sequence' non è stata fornita."]);
                }
                break;
            default:
                exit("Scegli una opzione");
        }
    } else {
        exit("Not a function");
    }
} else {
    exit("Not set");
}

?>