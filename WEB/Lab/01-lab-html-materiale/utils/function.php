<?php
function getIDFromName($name)
{
    return preg_replace("/[^a-z0-9]/", "", strtolower($name));
}

function isUserLoggedIn()
{
    return !empty($_SESSION["idautore"]);
}
