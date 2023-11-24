<?php
require_once("bootstrap.php");

$templateparams["titolo"] = "Blog TW - Contatti";
$templateparams["nome"] = "template/contatti.php";
$templateparams["articolicasuali"] = $dbh->getRandomPosts(2);
$templateparams["categorie"] = $dbh->getCategories();

$templateparams["autori"] = $dbh->getAuthors(2);

require("template/base.php");