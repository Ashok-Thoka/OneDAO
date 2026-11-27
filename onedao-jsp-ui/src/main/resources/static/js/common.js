const API_BASE = "";

function getToken() {
    return sessionStorage.getItem("onedao_token");
}

function getUser() {
    try {
        return JSON.parse(
            sessionStorage.getItem("onedao_user") || "{}"
        );
    } catch (e) {
        return {};
    }
}

function saveAuth(data) {
    sessionStorage.setItem("onedao_token", data.token);

    sessionStorage.setItem(
        "onedao_user",
        JSON.stringify({
            username: data.username,
            userType: data.userType
        })
    );
}

function clearAuth() {
    sessionStorage.removeItem("onedao_token");
    sessionStorage.removeItem("onedao_user");
}

async function apiFetch(url, options = {}) {

    const headers = new Headers(options.headers || {});

    if (!headers.has("Content-Type") && options.body) {
        headers.set("Content-Type", "application/json");
    }

    if (getToken()) {
        headers.set(
            "Authorization",
            "Bearer " + getToken()
        );
    }

    const response = await fetch(
        API_BASE + url,
        {
            ...options,
            headers
        }
    );

    const text = await response.text();

    let body = {};

    try {
        body = text ? JSON.parse(text) : {};
    } catch (e) {
        body = {
            message: text
        };
    }

    if (response.status === 401) {

        if (
            url !== "/api/auth/login" &&
            url !== "/api/auth/signup"
        ) {
            clearAuth();

            if (
                !location.pathname.startsWith("/login") &&
                !location.pathname.startsWith("/signup")
            ) {
                location.href = "/login?expired=1";
            }
        }
    }

    if (!response.ok) {

        const error = new Error(
            body.message ||
            body.error ||
            ("HTTP " + response.status)
        );

        error.status = response.status;
        error.body = body;

        throw error;
    }

    return body;
}

function requireLogin() {

    if (!getToken()) {
        location.replace("/login");
        return false;
    }

    return true;
}

function setMessage(el, message, success = false) {

    if (!el) {
        return;
    }

    el.textContent = message || "";

    el.className =
        "message" +
        (success ? " success" : "");
}

document.addEventListener(
    "DOMContentLoaded",
    () => {

        const btn =
            document.getElementById("logoutBtn");

        if (btn) {

            btn.addEventListener(
                "click",
                async () => {

                    try {

                        await apiFetch(
                            "/api/auth/logout",
                            {
                                method: "POST"
                            }
                        );

                    } catch (e) {

                        // Ignore logout API errors
                    } finally {

                        clearAuth();

                        location.href =
                            "/login?loggedOut=1";
                    }
                }
            );
        }
    }
);