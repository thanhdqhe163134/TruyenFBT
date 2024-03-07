<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="model.Account" %>
<%@ page import="model.Comic" %>
<%@ page import="model.Genre" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>



<%
    session = request.getSession(false);
    String role = "";
    String username = "";
    boolean isLoggedIn = false;
    int user_id = 0;

    if (session != null) {
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            role = loggedInUser.getRole(); // Lấy role từ Account
            username = loggedInUser.getUsername(); // Lấy username từ Account
            isLoggedIn = true;
            user_id = loggedInUser.getId();
        }
    }
    boolean isAdmin = "admin".equals(role); // Kiểm tra xem role có phải là admin không
%>


<%
    Comic comic = (Comic) request.getAttribute("comic");
    List<Genre> allGenres = (List<Genre>) request.getAttribute("allGenres");
    allGenres = allGenres.stream().sorted(Comparator.comparing(Genre::getName)).collect(Collectors.toList());
    List<Genre> comicGenres = comic != null ? comic.getGenres() : new ArrayList<>();
    boolean editMode = "true".equals(request.getParameter("editMode"));
%>
<head>
    <title><%= comic.getTitle() %></title>
</head>
<script src="js/BookAdd.js" defer></script>
<script src="js/BookDetail.js" defer></script>
<link rel="stylesheet" href="css/BookDetail.css">
<link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
/>

<jsp:include page="header.jsp"/>

<div class="book-detail1">
        <% if (editMode) { %>
    <!-- Edit Mode -->
    <jsp:include page="comic-edit.jsp"/>

    <!-- View Mode -->
    <% } else { %>
        <div class="container" style="
    width: 90%;
    margin: 2% auto;
">
        <div class="main_content">
            <div class="book_detail">
                <div class="book_info">
                    <div class="book_avatar" itemtype="https://schema.org/ImageObject">
                        <img itemprop="image" src="<%= comic.getImg() %>" alt="<%= comic.getTitle() %>"       />
                    </div>
                    <div
                            class="book_other"
                            itemscope=""
                            itemtype="http://schema.org/Book"
                    >
                        <h1 itemprop="name"><%= comic.getTitle() %></h1>
                        <div class="txt">
                            <ul class="list-info">
                                <li class="row">
                                    <p class="name col-xs-3">
                                        <i class="fa fa-eye"></i> Total views
                                    </p>
                                    <p class="col-xs-9"><% %></p>
                                </li>
                                <% if (isAdmin) { %>
                                <br>
                                <br>
                                <li class="row">
                                    <p class="name col-xs-3">
                                        <i class="fa fa-calendar"></i> Created date
                                    </p>
                                    <p class="col-xs-9"><%= comic.getCreatedDate() %></p>
                                </li>
                                <br>
                                <li class="row">
                                    <p class="name col-xs-3">
                                        <i class="fa fa-calendar"></i> Updated date
                                    </p>
                                    <p class="col-xs-9"><%= comic.getUpdatedDate() %></p>
                                </li>
                                <% } %>
                            </ul>
                        </div>

                        <ul class="list01">
                            <%
                                List<Genre> genres = comic.getGenres();
                                genres = genres.stream().sorted(Comparator.comparing(Genre::getName)).collect(Collectors.toList());
                                for(Genre genre : genres) {
                            %>
                            <li class="li03">
                                <a href="<%= "home?genre=" + genre.getName() %>"><%= genre.getName() %></a>
                            </li>
                            <% } %>
                        </ul>
                        <div class="clear"></div>
                    </div>
                    <div class="clear"></div>
                </div>
                <h3><i class="fa fa-info-circle"></i> Description</h3>
                <div
                        class="story-detail-info detail-content readmore-js-section readmore-js-collapsed"
                        style="/* height: 60px; */"
                >
                    <p>
                        <%= comic.getDescription() %>
                    </p>
                </div>
                <br>
                <a href="<%= "comic?title=" + comic.getTitle() + "&chapter?=1" %>">
                    <button class="action-button" type="button" style="font-size: 20px">Read now</button>
                </a>
                <a href="<%= "comic?title=" + comic.getTitle() + "&chapter?=1" %>">
                    <button class="action-button" type="button" style="font-size: 20px; background: #009900">Continue Reading</button>
                </a>
                <% if (isAdmin) { %>
                <a href="<%= "comic?title=" + comic.getTitle() + "&editMode=true" %>">
                    <button class="action-button" type="button" style="font-size: 20px; background: #0a86db">Edit Comic</button>
                </a>
                <% } %>

            </div>





            <script type="text/javascript">
                var urlComment = "https://truyenqqvn.com/frontend/comment/index";
                var urlCommentLoad = "https://truyenqqvn.com/frontend/comment/list";
                var urlCommentRemove =
                    "https://truyenqqvn.com/frontend/comment/remove";
                var urlLikeComment = "https://truyenqqvn.com/frontend/comment/like";
            </script>
        </div>
    </div>
    <% } %>
</div>


