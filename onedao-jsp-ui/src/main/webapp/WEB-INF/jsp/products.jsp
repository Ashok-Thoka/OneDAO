
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>OneDAO | Products</title>

    <link rel="stylesheet" href="/css/app.css">

</head>

<body>

    <%@ include file="fragments/nav.jsp" %>

    <main class="container">

        <!-- Page Heading -->

        <section class="page-heading">

            <div>

                <p class="eyebrow">CATALOGUE</p>

                <h1>Products</h1>

                <p class="muted">
                    Create, search, update and manage products.
                </p>

            </div>

            <button
                id="newProductBtn"
                class="btn btn-primary">
                + Add product
            </button>

        </section>


        <!-- Product Add / Edit Section -->

        <section id="productEditor" class="panel hidden">

            <h2 id="editorTitle">Add product</h2>

            <form id="productForm" class="form-grid">

                <input
                    id="productId"
                    type="hidden">


                <label>
                    Product name

                    <input
                        id="productName"
                        maxlength="120"
                        required>
                </label>


                <label>
                    Category

                    <input
                        id="productCategory"
                        maxlength="80"
                        required>
                </label>


                <label>
                    Price

                    <input
                        id="productPrice"
                        type="number"
                        min="0.01"
                        step="0.01"
                        required>
                </label>


                <label class="span-2">
                    Description

                    <textarea
                        id="productDescription"
                        maxlength="500"
                        required>
                    </textarea>
                </label>


                <!-- Form Actions -->

                <div class="form-actions span-2">

                    <button
                        class="btn btn-primary"
                        type="submit">
                        Save product
                    </button>

                    <button
                        id="cancelEdit"
                        class="btn btn-outline"
                        type="button">
                        Cancel
                    </button>

                </div>


                <p
                    id="formMessage"
                    class="message span-2">
                </p>

            </form>

        </section>


        <!-- Product List Section -->

        <section class="panel">

            <!-- Search and Filter Toolbar -->

            <div class="toolbar">

                <input
                    id="searchName"
                    placeholder="Search product name">

                <input
                    id="searchCategory"
                    placeholder="Filter category">


                <!-- Sorting -->

                <select id="sortBy">

                    <option value="id">Sort: ID</option>

                    <option value="name">Name</option>

                    <option value="price">Price</option>

                    <option value="category">Category</option>

                    <option value="createdAt">Created</option>

                </select>


                <select id="direction">

                    <option value="asc">Ascending</option>

                    <option value="desc">Descending</option>

                </select>


                <button
                    id="searchBtn"
                    class="btn btn-outline">
                    Search
                </button>

            </div>


            <!-- Products Table -->

            <div class="table-wrap">

                <table>

                    <thead>

                        <tr>

                            <th>ID</th>

                            <th>Name</th>

                            <th>Category</th>

                            <th>Price</th>

                            <th>Description</th>

                            <th>Actions</th>

                        </tr>

                    </thead>


                    <tbody id="productRows">

                        <tr>
                            <td colspan="6">
                                Loading…
                            </td>
                        </tr>

                    </tbody>

                </table>

            </div>


            <!-- Pagination -->

            <div class="pagination">

                <button
                    id="previousPage"
                    class="btn btn-outline">
                    Previous
                </button>

                <span id="pageInfo">
                    Page 1
                </span>

                <button
                    id="nextPage"
                    class="btn btn-outline">
                    Next
                </button>

            </div>


            <!-- List Message -->

            <p
                id="listMessage"
                class="message">
            </p>

        </section>

    </main>


    <!-- JavaScript Files -->

    <script src="/js/common.js"></script>

    <script src="/js/products.js"></script>

</body>

</html>
