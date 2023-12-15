<?php
$conn = new mysqli("localhost", "root", "", "giugno", 3306);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

function isValid($var)
{
    return $var >= 0;
}

function getArray($conn, $sql, $var)
{
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $var);
    $stmt->execute();
    $result = $stmt->get_result();

    while ($row = $result->fetch_assoc()) {
        $resultingArray[] = $row["valore"];
    }
    return $resultingArray;
}

if (!isset($_GET["A"]) || !isset($_GET["B"]) || !isset($_GET["O"])) {
    echo "Inserire i parametri A e B";
    exit;
}

$A = $_GET["A"];
$B = $_GET["B"];
$O = $_GET["O"];

if (!isValid($A) || !isValid($B)) {
    echo "Inserire un valore valido";
    exit;
}

$sql = "SELECT valore FROM insiemi WHERE insieme = ?";

$A = getArray($conn, $sql, 1);
$B = getArray($conn, $sql, 2);

switch ($O) {
    case "u":
        $operation = array_unique(array_merge($A, $B));
        break;

    case "i":
        $operation = array_intersect($A, $B);
        break;

    default:
        echo "Inserire i o u";
        exit;
        break;
}

$maxIdSql = "SELECT MAX(insieme) AS maxInsieme FROM insiemi";
$dbMaxInsieme = $conn->query($maxIdSql)->fetch_assoc();
$dbMaxInsieme = $dbMaxInsieme['maxInsieme'] ?? 0;
$dbMaxInsieme++;

$insertSql = "INSERT INTO insiemi (valore, insieme) VALUES (?, ?)";

foreach ($operation as $var) {
    $stmt = $conn->prepare($insertSql);
    $stmt->bind_param("ii", $var, $dbMaxInsieme);
    $stmt->execute();
}
$dbMaxInsieme++;

$conn->close();
