<form action="#" method="POST">
    <h2>Login</h2>
    <?php if (isset($templateParams["erroreLogin"])) : ?>
        <p><?php echo $templateParams["erroreLogin"]; ?></p>
    <?php endif; ?>

    <ul>
        <li>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username">
        </li>
        <li>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password">
        </li>
        <li>
            <input type="submit" value="Login">
        </li>
    </ul>
</form>