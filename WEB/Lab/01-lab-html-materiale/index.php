<?php
require_once("bootstrap.php");

$templateparams["titolo"] = "Blog TW - Home";
$templateparams["nome"] = "lista-articoli.php";
$templateparams["articolicasuali"] = $dbh->getRandomPosts(2);
$templateparams["categorie"] = $dbh->getCategories();

$templateparams["articoli"] = $dbh->getPosts(2);

require("template/base.php");

?>