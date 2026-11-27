
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>OneDAO | Login</title>

    <link rel="stylesheet" href="/css/app.css">

</head>

<body class="auth-bg">

    <main class="auth-card">

        <!-- Branding -->

        <div class="brand large">
            OneDAO
        </div>


        <!-- Login Heading -->

        <h1>Welcome back</h1>

        <p class="muted">
            Sign in to continue to your workspace.
        </p>


        <!-- Login Form -->

        <form id="loginForm">

            <label>
                Username

                <input
                    id="username"
                    name="username"
                    minlength="8"
                    required
                    autocomplete="username">
            </label>


            <label>
                Password

                <input
                    id="password"
                    name="password"
                    type="password"
                    required
                    autocomplete="current-password">
            </label>


            <button
                class="btn btn-primary full"
                type="submit">
                Sign in
            </button>


            <!-- Error / Success Message -->

            <p id="message" class="message"></p>

        </form>


        <!-- Signup Link -->

        <p class="switch">
            New to OneDAO?

            <a href="/signup">
                Create an account
            </a>
        </p>

    </main>


    <!-- JavaScript Files -->

    <script src="/js/common.js"></script>

    <script src="/js/login.js"></script>

</body>

</html>
