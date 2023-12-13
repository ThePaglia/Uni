<?php
require_once("bootstrap.php");

$templateParams["titolo"] = "Blog TW - Home";
$templateParams["nome"] = "template/lista-articoli.php";
$templateParams["articolicasuali"] = $dbh->getRandomPosts(2);
$templateParams["categorie"] = $dbh->getCategories();

$templateParams["articoli"] = $dbh->getPosts(2);

require("template/base.php");
