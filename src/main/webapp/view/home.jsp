<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="model.Account" %>
<%@ page import="model.Genre" %>
<%@ page import="model.Comic" %>
<%@ page import="model.Chapter" %>
<%@ page import="java.text.SimpleDateFormat" %>

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
                    <form action="delete-genre" method="post" style="display: inline">
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
                    <option value="views" ${sortBy.equals("views")?"selected":""}>Top Views</option>

                </select>
            </form>

            <% if (isAdmin) { %>
            <a href="add-comic">
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
                <a href="<%= "comic?title=" + comic.getTitle() %>">
                    <img
                            src="<%= comic.getImg() %>"
                            class="book-img"
                            alt="<%= comic.getTitle() %>"
                    />
                </a>
                <div class="book-content">
                    <h4 class="book-title"><%= comic.getTitle() %></h4>
                    <p class="name col-xs-3">
                        <i class="fa fa-eye"></i> <%= comic.getViews() %>
                    </p>
                    <div class="book-genres">
                        <%
                            List<Chapter> chapters = comic.getChapters();
                            List<Genre> genres = comic.getGenres();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                            genres = genres.stream().sorted(Comparator.comparing(Genre::getName)).collect(Collectors.toList());
                            for (Genre genre : genres) {
                        %>
                        <a href="<%= "home?genre=" + genre.getName() %>">
                            <span class="book-genres"><%= genre.getName() %></span>
                        </a> <% } %>
                    </div>
                    <% for (Chapter chapter : chapters) { %>
                    <a href="<%= "comic?id=" + comic.getId() + "&chapter=" + chapter.getNumber() %>">
                        Chapter <%= chapter.getNumber() %>
                    </a>
                    <span style="padding-left: 20px;  color: #777; font-size: 0.8em;"><%=sdf.format(chapter.getUpdatedDate()) %></span>
                    <% } %>

                </div>
                <div class="button-action">
                    <% if (isAdmin) { %>
                    <a href="<%= "comic?title=" + comic.getTitle() + "&editMode=true" %>">
                        <button class="detail-button" style="background: #0a86db; padding: 2% 5%;">Edit</button>
                    </a>
                    <form action="delete-comic" method="post">
                        <input
                                type="hidden"
                                name="id"
                                value="<%= comic.getId() %>"
                        />
                        <button class="detail-button" style="background: red">Delete</button>
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
