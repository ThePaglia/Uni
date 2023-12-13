<?php
require_once("bootstrap.php");

if (isset($_POST["username"]) && isset($_POST["password"])) {
    $login_result = $dbh->checkLogin($_POST["username"], $_POST["password"]);

    if (count($login_result) == 0) {
        $templateParams["errore"] = "Username o password errati";
    } else {
        registerLoggedUser($login_result[0]);
    }
}
if (isUserLoggedIn()) {
    $templateParams["titolo"] = "Blog TW - Admin";
    $templateParams["nome"] = "login-home.php";
    $templateParams["articoli"] = $dbh->getPostByAuthorID($_SESSION["idautore"]);

    if (isset($_GET["formmsg"])) {
        $templateParams["formmsg"] = $_GET["formmsg"];
    }
    $templateParams["categorie"] = $dbh->getCategories();
    $templateParams["articolicasuali"] = $dbh->getRandomPosts(2);
} else {
    $templateParams["titolo"] = "Blog TW - Login";
    $templateParams["nome"] = "login-form.php";
}

require("template/base.php");
