<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="model.Account" %>
<%@ page import="model.Comic" %>
<%@ page import="model.Genre" %>
<%@ page import="model.Chapter" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
                                    <p class="col-xs-9"><%= comic.getViews() %></p>
                                </li>
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
                <a href="<%= "comic?id=" + comic.getId() + "&chapter=1" %>">
                    <button class="action-button" type="button" style="font-size: 20px">Read now</button>
                </a>
                <a href="<%= "comic?id=" + comic.getId() + "&action=continueReading" %>">
                    <button class="action-button" type="button" style="font-size: 20px; background: #009900">Continue Reading</button>
                </a>
                <% if (isAdmin) { %>
                <a href="<%= "comic?title=" + comic.getTitle() + "&editMode=true" %>">
                    <button class="action-button" type="button" style="font-size: 20px; background: #0a86db">Edit Comic</button>
                </a>
                <% } %>



            </div>

            <div class="chapter-list-container">
                <ul class="chapter-list" style="margin-left: 20px">
                    <%
                        List<Chapter> chapters = ((Comic)request.getAttribute("comic")).getChapters();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        for(int i = 0; i < chapters.size(); i++) {
                            Chapter chapter = chapters.get(i);
                    %>
                    <li class="<%= i >= 5 ? "hidden-chapter" : "" %>">
                        <a href="<%= "comic?id=" + comic.getId() + "&chapter=" + chapter.getNumber() %>">
                            Chapter <%= chapter.getNumber() %>
                        </a>
                        <span style="padding-left: 20px;"><%=sdf.format(chapter.getUpdatedDate()) %></span>
                    </li>
                    <%
                        }
                        if(chapters.size() > 5) {
                    %>
                    <li>
                        <button id="show-more" style="margin-left: 600px;">Show more</button>
                    </li>
                    <%
                        }
                    %>
                </ul>
            </div>
            <script>
                document.getElementById("show-more").addEventListener("click", function() {
                    var hiddenChapters = document.querySelectorAll(".hidden-chapter");
                    for(var i = 0; i < hiddenChapters.length; i++) {
                        hiddenChapters[i].style.display = "list-item";
                    }
                    this.style.display = "none";
                });
            </script>




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
<style>
    .hidden-chapter {
        display: none;
    }
    .chapter-list-container {
        border: 1px solid #ddd;
        padding: 20px;
        margin-top: 20px;
        background-color: #f9f9f9;
        border-radius: 5px;
    }

    .chapter-list {
        list-style-type: none;
        padding: 0;
    }

    .chapter-list li {
        padding: 10px 0;
        border-bottom: 1px solid #ddd;
    }

    .chapter-list li:last-child {
        border-bottom: none;
    }

    .chapter-list li a {
        color: #333;
        text-decoration: none;
    }

    .chapter-list li span {
        color: #777;
        font-size: 0.8em;
    }
    #show-more {
        background-color: #ff8800;
        color: white;
        border-radius: 5px;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    #show-more:hover {
        background-color: #ff8800;
    }
</style>


