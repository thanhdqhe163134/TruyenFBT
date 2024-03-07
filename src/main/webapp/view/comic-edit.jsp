





<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="model.Account" %>
<%@ page import="model.Comic" %>
<%@ page import="model.Genre" %>


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

<div class="container" style="width: 90%; margin: 2% auto;">
    <form action="${pageContext.request.contextPath}/book" method="post" enctype="multipart/form-data">
        <input type="hidden" name="book_id" value="<%= comic.getId() %>" />
        <div class="main_content">
            <div class="book_detail">
                <div class="book_info">
                    <div class="book_avatar" itemtype="https://schema.org/ImageObject">
                        <label>
                            <img itemprop="image" src="<%= comic.getImg() %>" alt="<%= comic.getTitle() %>" />
                            <input type="file" name="image" />
                        </label>
                    </div>
                    <div class="book_other" itemscope="" itemtype="http://schema.org/Book">
                        <ul class="list-info">
                            <li class="row">
                                <p class="name col-xs-3">
                                    <i class="fa fa-book"></i> Title
                                </p>
                                <div class="col-xs-9">
                                    <input type="text" name="title" id="title" value="<%= comic.getTitle() %>" />
                                </div>
                            </li>
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
                            <br>
                            <li class="row">
                                <p class="name col-xs-3">
                                    <i class="fa fa-info-circle"></i> Description
                                </p>
                                <div class="col-xs-9">
                                    <textarea name="description"><%= comic.getDescription() %></textarea>
                                </div>
                            </li>
                        </ul>
                        <div class="genres">
                            <h3>Genres</h3>
                            <ul class="list01">
                                <% for(Genre genre : allGenres) {
                                    String checkedStr = "";
                                    for(Genre comicGenre : comicGenres) {
                                        if(comicGenre.getName().equals(genre.getName())) {
                                            checkedStr = "checked";
                                            break;
                                        }
                                    } %>
                                <li class="li03">
                                    <a>
                                        <input type="checkbox" id="genre<%= genre.getName() %>" name="genres" value="<%= genre.getName() %>" <%= checkedStr %> readonly/>
                                        <label for="genre<%= genre.getName() %>"><%= genre.getName() %></label>
                                    </a>
                                </li>
                                <% } %>
                            </ul>
                        </div>
                        <br>
                        <strong> <label for="newGenre">Add a new genre:</label> </strong>
                        <input type="text" id="newGenre" name="newGenre">
                        <button class="edit-button" type="button" onclick="addGenre()">
                            <i class="fa fa-add"></i> Add
                        </button>
                        <br>
                        <ul class="list01">
                            <li class="li03">
                                <div id="newGenreList" name="newGenreList">
                                </div>
                            </li>
                        </ul>
                        <br>

                        <!-- Newly added genres will appear here -->
                        <input type="hidden" name="updated_user" value="<%= username %>" />
                        <button type="submit" class="save-button">
                            <i class="fa fa-save"></i> Save All
                        </button>
                    </div>

                    <div class="clear"></div>
                </div>
                <a href="<%= "comic?title=" + comic.getTitle() %>"><button type="button" class="cancel-button">
                    <i class="fa fa-close" style="size: 20px;"> </i>
                </button></a>

            </div>


        </div>
    </form>
</div>



    <script>
        function addGenre() {
            var newGenreList = document.getElementById('newGenreList');
            var newGenre = document.getElementById('newGenre');
            if (newGenre.value.trim() === '') return;

            // Create a new anchor element
            var genreElement = document.createElement('a');
            genreElement.textContent = newGenre.value;

            // Create a new hidden input element
            var hiddenInput = document.createElement('input');
            hiddenInput.type = 'hidden';
            hiddenInput.name = 'newGenres';  // This name should match the parameter you want to retrieve on the server side
            hiddenInput.value = newGenre.value;

            // Append elements
            newGenreList.appendChild(genreElement);
            newGenreList.appendChild(hiddenInput);

            // Clear the input field
            newGenre.value = '';
        }

    </script>