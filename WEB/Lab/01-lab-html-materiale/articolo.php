<?php
require_once("bootstrap.php");

$templateParams["titolo"] = "Blog TW - Articolo";
$templateParams["nome"] = "singolo-articoli.php";
$templateParams["articolicasuali"] = $dbh->getRandomPosts(2);
$templateParams["categorie"] = $dbh->getCategories();

$idarticolo = -1;
if (isset($_GET["ID"])) {
    $idarticolo = $_GET["ID"];
}
$templateParams["articolo"] = $dbh->getPostByID($idarticolo);

require("template/base.php");
