<?php
if (isset($templateparams["titolo_pagina"])) : ?>
    <h2><?php echo $templateparams["titolo_pagina"]; ?></h2>
<?php endif; ?>

<?php
foreach ($templateparams["articoli"] as $articolo) : ?>
    <article>
        <header>
            <img src="<?php echo UPLOAD_DIR . $articolo["imgarticolo"]; ?>" alt="">
            <h2><?php echo $articolo["titoloarticolo"] ?></h2>
            <p><?php echo $articolo["dataarticolo"]; ?> - <?php echo $articolo["nome"]; ?></p>
        </header>
        <section lang="zxx">
            <p><?php echo $articolo["anteprimaarticolo"]; ?></p>
        </section>
        <footer>
            <p><a href="articolo.php?id=<?php echo $articolo["idarticolo"]; ?>">Leggi tutto</a></p>
        </footer>
    </article>
<?php endforeach; ?>