document.addEventListener("DOMContentLoaded", async () => {

    if (!requireLogin()) {
        return;
    }

    const msg =
        document.getElementById("settingsMessage");

    try {

        const data =
            await apiFetch("/api/settings");

        setMessage(
            msg,
            data.message,
            true
        );

    } catch (e) {

        setMessage(
            msg,
            e.status === 403
                ? "Access denied. Only Administrator users can open Settings."
                : e.message
        );

        if (e.status === 403) {

            setTimeout(() => {
                location.href = "/dashboard";
            }, 1800);
        }
    }
});