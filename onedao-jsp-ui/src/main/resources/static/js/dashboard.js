document.addEventListener("DOMContentLoaded", async () => {

    if (!requireLogin()) {
        return;
    }

    const msg = document.getElementById("message");

    try {

        const data = await apiFetch("/api/dashboard");

        const user = getUser();

        document.getElementById("welcomeText").textContent =
            data.message;

        document.getElementById("userRole").textContent =
            (user.userType || "UNKNOWN").replaceAll("_", " ");

        document.getElementById("userInitial").textContent =
            (user.username || "U").charAt(0).toUpperCase();

        const settings =
            document.getElementById("settingsLink");

        if (user.userType !== "ADMINISTRATOR") {
            settings.style.display = "none";
        }

    } catch (e) {

        setMessage(msg, e.message);

    }
});