document.addEventListener("DOMContentLoaded", () => {

    if (getToken()) {
        location.replace("/dashboard");
        return;
    }

    const form = document.getElementById("loginForm");
    const msg = document.getElementById("message");

    const params = new URLSearchParams(location.search);

    if (params.has("expired")) {
        setMessage(
            msg,
            "Your session expired or you logged out. Please sign in again."
        );
    }

    if (params.has("loggedOut")) {
        setMessage(
            msg,
            "You have been logged out successfully.",
            true
        );
    }

    form.addEventListener("submit", async (e) => {

        e.preventDefault();

        setMessage(msg, "Signing in…");

        try {

            const data = await apiFetch(
                "/api/auth/login",
                {
                    method: "POST",
                    body: JSON.stringify({
                        username:
                            document.getElementById("username").value,

                        password:
                            document.getElementById("password").value
                    })
                }
            );

            saveAuth(data);

            location.href = "/dashboard";

        } catch (err) {

            setMessage(
                msg,
                err.message === "Login Failed"
                    ? "Login Failed"
                    : err.message
            );
        }
    });
});