<!DOCTYPE html>
<html lang="it">

<head>
    <title>
        <?php
        echo $templateparams["titolo"];
        ?>
    </title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>

<body>
    <header>
        <h1>Blog di Tecnologie Web</h1>
    </header>
    <nav>
        <ul>
            <li><a href="index.php">Home</a></li>
            <li><a href="archivio.php">Archivio</a></li>
            <li><a href="contatti.php">Contatti</a></li>
            <li><a href="login.php"></a></li>
        </ul>
    </nav>
    <main>
        <?php
        require($templateparams["nome"]);
        ?>
    </main>
    <aside>
        <section>
            <h2>Post Casuali</h2>
            <ul>
                <?php
                foreach ($templateparams["articolicasuali"] as $articolocasuale) :
                ?>
                    <li>
                        <img src="<?php echo UPLOAD_DIR . $articolocasuale["imgarticolo"]; ?>" alt="">
                        <a href="#"><?php echo $articolocasuale["titoloarticolo"]; ?></a>
                    </li>
                <?php
                endforeach;
                ?>
            </ul>
        </section>
        <section>
            <h2>Categorie</h2>
            <ul>
                <?php
                foreach ($templateparams["categorie"] as $categoria) :
                ?>
                    <li><a href="#"><?php echo $categoria["nomecategoria"]; ?></a></li>
                <?php
                endforeach;
                ?>
            </ul>
        </section>
    </aside>
    <footer>
        <p>Tecnologie Web - A.A. 2023/2024</p>
    </footer>
</body>

</html>