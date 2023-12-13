<?php
require_once("bootstrap.php");

$templateParams["titolo"] = "Blog TW - Contatti";
$templateParams["nome"] = "template/contatti.php";
$templateParams["articolicasuali"] = $dbh->getRandomPosts(2);
$templateParams["categorie"] = $dbh->getCategories();

$templateParams["autori"] = $dbh->getAuthors(2);

require("template/base.php");
