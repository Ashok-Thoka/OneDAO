let currentPage = 0;
let totalPages = 0;
const pageSize = 10;

document.addEventListener("DOMContentLoaded", () => {

    if (!requireLogin()) {
        return;
    }

    document.getElementById("newProductBtn").onclick = () => {
        openEditor();
    };

    document.getElementById("cancelEdit").onclick = closeEditor;

    document.getElementById("searchBtn").onclick = () => {
        currentPage = 0;
        loadProducts();
    };

    document.getElementById("previousPage").onclick = () => {

        if (currentPage > 0) {
            currentPage--;
            loadProducts();
        }
    };

    document.getElementById("nextPage").onclick = () => {

        if (currentPage + 1 < totalPages) {
            currentPage++;
            loadProducts();
        }
    };

    document.getElementById("productForm").onsubmit = saveProduct;

    loadProducts();
});


function openEditor(p) {

    document
        .getElementById("productEditor")
        .classList.remove("hidden");

    document.getElementById("editorTitle").textContent =
        p ? "Edit product" : "Add product";

    document.getElementById("productId").value =
        p?.id || "";

    document.getElementById("productName").value =
        p?.name || "";

    document.getElementById("productCategory").value =
        p?.category || "";

    document.getElementById("productPrice").value =
        p?.price || "";

    document.getElementById("productDescription").value =
        p?.description || "";

    setMessage(
        document.getElementById("formMessage"),
        ""
    );

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
}


function closeEditor() {

    document
        .getElementById("productEditor")
        .classList.add("hidden");

    document
        .getElementById("productForm")
        .reset();
}


async function loadProducts() {

    const rows = document.getElementById("productRows");
    const msg = document.getElementById("listMessage");

    rows.innerHTML =
        '<tr><td colspan="6">Loading…</td></tr>';

    const q = new URLSearchParams({
        page: currentPage,
        size: pageSize,
        sortBy: document.getElementById("sortBy").value,
        direction: document.getElementById("direction").value
    });

    const name =
        document.getElementById("searchName").value.trim();

    const category =
        document.getElementById("searchCategory").value.trim();

    if (name) {
        q.set("name", name);
    }

    if (category) {
        q.set("category", category);
    }

    try {

        const data =
            await apiFetch("/api/products?" + q.toString());

        totalPages = data.totalPages;

        document.getElementById("pageInfo").textContent =
            `Page ${data.number + 1} of ${Math.max(data.totalPages, 1)} ` +
            `(${data.totalElements} products)`;

        document.getElementById("previousPage").disabled =
            currentPage <= 0;

        document.getElementById("nextPage").disabled =
            currentPage + 1 >= totalPages;

        rows.innerHTML = "";

        if (!data.content.length) {

            rows.innerHTML =
                '<tr><td colspan="6">No products found.</td></tr>';

            return;
        }

        for (const p of data.content) {

            const tr = document.createElement("tr");

            const cells = [
                p.id,
                p.name,
                p.category,
                Number(p.price).toFixed(2),
                p.description
            ];

            for (const value of cells) {

                const td = document.createElement("td");

                td.textContent = value ?? "";

                tr.appendChild(td);
            }

            const actions =
                document.createElement("td");

            const edit =
                document.createElement("button");

            edit.className = "btn btn-outline";
            edit.textContent = "Edit";

            edit.onclick = () => {
                openEditor(p);
            };

            actions.appendChild(edit);

            const settings = document.getElementById("settingsLink");

             if (getUser().userType !== "ADMINISTRATOR") {
                settings.style.display = "none";
             }

            if (getUser().userType === "ADMINISTRATOR") {

                const del =
                    document.createElement("button");

                del.className = "btn btn-outline";
                del.textContent = "Delete";

                del.onclick = () => {
                    deleteProduct(p.id);
                };

                actions.appendChild(del);
            }

            tr.appendChild(actions);
            rows.appendChild(tr);
        }

    } catch (e) {

        rows.innerHTML =
            '<tr><td colspan="6">Unable to load products.</td></tr>';

        setMessage(msg, e.message);
    }
}


async function saveProduct(e) {

    e.preventDefault();

    const id =
        document.getElementById("productId").value;

    const payload = {
        name:
            document.getElementById("productName").value.trim(),

        category:
            document.getElementById("productCategory").value.trim(),

        price:
            Number(
                document.getElementById("productPrice").value
            ),

        description:
            document
                .getElementById("productDescription")
                .value
                .trim()
    };

    try {

        await apiFetch(
            id
                ? "/api/products/" + id
                : "/api/products",
            {
                method: id ? "PUT" : "POST",
                body: JSON.stringify(payload)
            }
        );

        closeEditor();

        setMessage(
            document.getElementById("listMessage"),
            "Product saved successfully.",
            true
        );

        loadProducts();

    } catch (err) {

        setMessage(
            document.getElementById("formMessage"),
            err.message
        );
    }
}


async function deleteProduct(id) {

    if (!confirm("Delete this product?")) {
        return;
    }

    try {

        await apiFetch(
            "/api/products/" + id,
            {
                method: "DELETE"
            }
        );

        loadProducts();

    } catch (e) {

        setMessage(
            document.getElementById("listMessage"),
            e.message
        );
    }
}