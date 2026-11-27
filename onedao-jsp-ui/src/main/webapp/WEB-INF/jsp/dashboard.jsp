
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1">
    <title>OneDAO | Dashboard</title>
    <link rel="stylesheet" href="/css/app.css">
</head>

<body>

    <%@ include file="fragments/nav.jsp" %>

    <main class="container">

        <!-- Page Heading -->

        <section class="page-heading">

            <div>

                <p class="eyebrow">OVERVIEW</p>

                <h1>Dashboard</h1>

                <p class="muted">
                    Your OneDAO workspace at a glance.
                </p>

            </div>

        </section>


        <!-- Welcome Section -->

        <section class="panel welcome-panel">

            <div>

                <p class="eyebrow">SIGNED IN</p>

                <h2 id="welcomeText">
                    Loading your dashboard…
                </h2>

                <p class="muted">
                    You are authenticated using a JWT access token.
                </p>

            </div>

            <div class="avatar" id="userInitial">
                U
            </div>

        </section>


        <!-- Dashboard Statistics -->

        <section class="stats-grid">

            <article class="stat-card">

                <span>Account type</span>

                <strong id="userRole">—</strong>

            </article>


            <article class="stat-card">

                <span>Product workspace</span>

                <strong>Available</strong>

            </article>


            <article class="stat-card">

                <span>Authentication</span>

                <strong>JWT secured</strong>

            </article>

        </section>


        <!-- Message Section -->

        <div id="message" class="message"></div>

    </main>


    <!-- JavaScript Files -->

    <script src="/js/common.js"></script>

    <script src="/js/dashboard.js"></script>

</body>

</html>
