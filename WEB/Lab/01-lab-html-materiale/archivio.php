<?php
require_once("bootstrap.php");

$templateParams["titolo"] = "Blog TW - Archivio";
$templateParams["nome"] = "lista-articoli.php";
$templateParams["articolicasuali"] = $dbh->getRandomPosts(2);
$templateParams["categorie"] = $dbh->getCategories();

$templateParams["autori"] = $dbh->getPosts();

require("template/base.php");
