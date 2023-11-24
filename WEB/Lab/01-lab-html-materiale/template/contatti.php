<section>
    <h2>
        Autori del Blog
    </h2>
    <table>
        <thead>
            <tr>
                <th id="autore">Autore</th>
                <th id="email">Email</th>
                <th id="argomenti">Argomenti</th>
            </tr>
        </thead>
        <tbody>
            <?php
            foreach ($templateparams["autori"] as $autore) :
            ?>
                <tr>
                    <th id="<?php echo getIDFromName($autore["nome"]); ?>"><?php echo $autore["nome"]; ?></th>
                    <td headers="email <?php echo getIDFromName($autore["nome"]); ?>"><?php echo $autore["username"]; ?></td>
                    <td headers="argomenti <?php echo getIDFromName($autore["nome"]); ?>"><?php echo $autore["argomentoo"] ?></td>
                </tr>
            <?php
            endforeach;
            ?>
        </tbody>
    </table>
</section>