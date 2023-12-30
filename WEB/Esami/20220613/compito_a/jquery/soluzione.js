window.onload = function () {
    document.getElementsByTagName("form")[0].style.display = "none";
    document.getElementsByTagName("button")[1].style.display = "none";
    for (let i = 0; i < document.getElementsByTagName("span").length; i++) {
        document.getElementsByTagName("span")[i].style.display = "none";
    }
}
