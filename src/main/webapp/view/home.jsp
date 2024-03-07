<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="model.Account" %>
<%@ page import="model.Genre" %>
<%@ page import="model.Comic" %>

<%
    session = request.getSession(false);
    String role = null;
    String username = null;

    if (session != null) {
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            role = loggedInUser.getRole(); // Lấy role từ Account
            username = loggedInUser.getUsername(); // Lấy username từ Account
        }
    }

    boolean isAdmin = "admin".equals(role); // Kiểm tra xem role có phải là admin không



%>

<head>
    <link rel="stylesheet" href="css/BookList.css">

    <title>TruyenFBT</title>
</head>
<body>

<jsp:include page="header.jsp" />


<br>
<br>

<div class="wrapper">
    <div class="genre-list">
        <div class="logo">
            <a href="home">
                <img
                        src="https://cdn.haitrieu.com/wp-content/uploads/2021/10/Logo-Dai-hoc-FPT.png"
                        width="120"
                />
            </a>
        </div>
        <%
            String selectedGenre = request.getParameter("genre");
            if(selectedGenre == null || selectedGenre.isEmpty()) {
                selectedGenre = "";
            }

        %>
        <ul>
            <li class="<%= selectedGenre.equals("") ? "selected-genre" : "" %>">
                <a href="home">
                    <span class="genre">All </span>
                </a>
            </li>

            <%
                List<Genre> allGenres = (List<Genre>) request.getAttribute("genreList");
                if (allGenres != null) {
                    allGenres = allGenres.stream().sorted(Comparator.comparing(Genre::getName)).collect(Collectors.toList());
                    for (Genre genre : allGenres) {
            %>
            <li class="<%= genre.getName().equals(selectedGenre) ? "selected-genre" : "" %>">
                <div class="sidebar-items">
                    <a href="<%= "home?genre=" + genre.getName() %>">
                        <span class="genre"><%= genre.getName() %></span>
                    </a>
                        </label>
                        <% if (isAdmin) { %>
                    <form action="deleteGenre" method="post" style="display: inline">
                        <input type="hidden" name="genre" value="<%= genre.getName() %>"/>
                        <button type="submit" class="delete-genre-button">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </form>
                    <% } %>
                </div>
            </li>
            <% } %>
            <% } %>
        </ul>
    </div>

    <div class="wrapper column">
        <%
            String sortBy = request.getParameter("sortBy");
        %>

        <div class="search-add-container" style="padding-top: 41px;padding-left: 0px;margin-bottom: 0px;padding-bottom: 0px;">
            <form class="sort" action="home" method="get" id="sort" style="margin-bottom: 0px;">
                <% if (request.getParameter("genre") != null) { %>
                <input type="hidden" name="genre" value="<%= request.getParameter("genre") %>"/>
                <% } %>
                <span>Sort by</span>
                <select name="sortBy" onchange="document.getElementById('sort').submit();">
                    <option value="all" ${sortBy == null?"selected":""}>All</option>
                    <option value="newest" ${sortBy.equals("newest")?"selected":""}>Recent update</option>
                    <option value="topWeek" ${sortBy.equals("topWeek")?"selected":""}>Top Week</option>
                    <option value="topMouth" ${sortBy.equals("topMouth")?"selected":""}>Top Mouth</option>
                    <option value="topYear" ${sortBy.equals("topYear")?"selected":""}>Top Year</option>
                    <option value="topAll" ${sortBy.equals("topAll")?"selected":""}>Top All</option>
                </select>
            </form>

            <% if (isAdmin) { %>
            <a href="addComic">
                <button class="add-button" style="z-index: 999">Add Comic</button>
            </a>
            <% } %>
        </div>



        <div class="book-container" style="padding-top: 0px; padding-left: 0px">
            <%
                List<Comic> comicList = (List<Comic>) request.getAttribute("comicList");
                if(comicList != null){
                for (Comic comic : comicList) {
            %>
            <div class="book-card" style="margin-left: 5px;">
                <img
                        src="<%= comic.getImg() %>"
                        class="book-img"
                        alt="<%= comic.getTitle() %>"
                />
                <div class="book-content">
                    <h4 class="book-title"><%= comic.getTitle() %>
                    </h4>
                    <div class="book-genres">

                        <%
                            List<Genre> genres = comic.getGenres();
                            genres = genres.stream().sorted(Comparator.comparing(Genre::getName)).collect(Collectors.toList());                            for (Genre genre : genres) {
                        %>
                        <a href="<%= "home?genre=" + genre.getName() %>">
                            <span class="book-genres"><%= genre.getName() %></span>
                        </a> <% } %>

                    </div>

                </div>
                <div class="button-action">
                    <a href="<%= "comic?title=" + comic.getTitle() %>">
                        <button class="detail-button">Detail</button>
                    </a>
                    <% if (isAdmin) { %>
                    <form action="delete-comic" method="post">
                        <input
                                type="hidden"
                                name="id"
                                value="<%= comic.getId() %>"
                        />
                        <button class="delete-button">Delete</button>
                    </form>
                    <% } %>
                </div>
            </div>

            <% } }%>
        </div>


        </div>


    </div>
</div>

</body>

</html>
<%--<jsp:include page="footer.jsp" />--%>
