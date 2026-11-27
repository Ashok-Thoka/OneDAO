document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("signupForm");
    const msg = document.getElementById("message");

    form.addEventListener("submit", async (e) => {

        e.preventDefault();

        setMessage(msg, "Creating account…");

        const payload = {
            username:
                document.getElementById("username").value.trim(),

            email:
                document.getElementById("email").value.trim(),

            password:
                document.getElementById("password").value,

            userType:
                document.getElementById("userType").value
        };

        try {

            const data = await apiFetch(
                "/api/auth/signup",
                {
                    method: "POST",
                    body: JSON.stringify(payload)
                }
            );

            setMessage(
                msg,
                data.message + ". Please sign in.",
                true
            );

            form.reset();

        } catch (err) {

            const details =
                err.body && err.body.details
                    ? Object.values(err.body.details).join("; ")
                    : "";

            setMessage(
                msg,
                details || err.message
            );
        }
    });
});