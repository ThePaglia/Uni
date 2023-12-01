<?php
require_once("bootstrap.php");

$templateparams["titolo"] = "Blog TW - Archivio";
$templateparams["nome"] = "lista-articoli.php";
$templateparams["articolicasuali"] = $dbh->getRandomPosts(2);
$templateparams["categorie"] = $dbh->getCategories();

$templateparams["autori"] = $dbh->getPosts();

require("template/base.php");
