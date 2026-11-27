
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>OneDAO | Settings</title>

    <link rel="stylesheet" href="/css/app.css">

</head>

<body>

    <%@ include file="fragments/nav.jsp" %>

    <main class="container">

        <!-- Page Heading -->

        <section class="page-heading">

            <div>

                <p class="eyebrow">ADMINISTRATION</p>

                <h1>Settings</h1>

                <p class="muted">
                    Administrator-only area.
                </p>

            </div>

        </section>


        <!-- Settings Panel -->

        <section class="panel">

            <h2>Role management</h2>

            <p id="settingsMessage" class="message">
                Checking administrator permission…
            </p>

            <p class="muted">
                The server enforces this restriction on
                <code>GET /api/settings</code>;
                hiding this page link is not the security boundary.
            </p>

        </section>

    </main>


    <!-- JavaScript Files -->

    <script src="/js/common.js"></script>

    <script src="/js/settings.js"></script>

</body>

</html>
