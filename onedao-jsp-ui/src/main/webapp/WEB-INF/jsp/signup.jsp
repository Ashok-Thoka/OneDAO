
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>OneDAO | Sign up</title>

    <link rel="stylesheet" href="/css/app.css">

</head>

<body class="auth-bg">

    <main class="auth-card wide">

        <!-- Branding -->

        <div class="brand large">
            OneDAO
        </div>


        <!-- Signup Heading -->

        <h1>Create your account</h1>

        <p class="muted">
            Register to access the OneDAO workspace.
        </p>


        <!-- Signup Form -->

        <form id="signupForm">

            <label>
                Username

                <input
                    id="username"
                    required
                    minlength="8"
                    maxlength="50"
                    autocomplete="username">
            </label>


            <label>
                Email

                <input
                    id="email"
                    type="email"
                    required
                    maxlength="150"
                    placeholder="name_first@example.com">
            </label>


            <label>
                Password

                <input
                    id="password"
                    type="password"
                    required
                    minlength="8"
                    maxlength="12"
                    autocomplete="new-password">

                <small>
                    8–12 chars, uppercase, lowercase, number and special character.
                </small>

            </label>


            <label>
                User type

                <select id="userType" required>

                    <option value="STANDARD_USER">
                        Standard User
                    </option>

                    <option value="ADMINISTRATOR">
                        Administrator
                    </option>

                </select>

                <small>
                    For this machine test, both roles can be selected during registration.
                </small>

            </label>


            <!-- Submit Button -->

            <button
                class="btn btn-primary full"
                type="submit">
                Create account
            </button>


            <!-- Response Message -->

            <p id="message" class="message"></p>

        </form>


        <!-- Login Link -->

        <p class="switch">

            Already registered?

            <a href="/login">
                Sign in
            </a>

        </p>

    </main>


    <!-- JavaScript Files -->

    <script src="/js/common.js"></script>

    <script src="/js/signup.js"></script>

</body>

</html>
