<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Chapter" %>
<%@ page import="model.Comment" %>
<%@ page import="model.Image" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<html>
<%
    Chapter chapter = (Chapter) request.getAttribute("chapter");
    List<Image> images = new ArrayList<>();
    List<Comment> comments = new ArrayList<>();
    List<Chapter> chapters = (List<Chapter>) request.getAttribute("allChapters");
    if (chapter == null) {
        response.sendRedirect("home");
    }
    if(chapter.getImages() != null && chapter.getImages().size() > 0) {
        images = chapter.getImages();
    }
    if(chapter.getComments() != null && chapter.getComments().size() > 0) {
        comments = chapter.getComments();
    }
%>
<head>
    <title>Chapter <%= chapter.getNumber() %></title>
    <link rel="stylesheet" href="css/BookDetail.css">
    <link rel="stylesheet" href="css/Home.css">
    <link rel="stylesheet" href="css/AccountList.css">
    <style>
        .chapter-content {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .chapter-content img {
            max-width: 1000px;
            height: auto;
            margin-bottom: 0px;
        }
        .chapter-content {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
        }
        .chapter-content img {
            max-width: 1000px;
            height: auto;
            margin-bottom: 20px;
            padding: 10px;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
        }
        .chapter-navigation {
            display: flex;
            justify-content: center;
            padding: 10px;
            background-color: #f0f0f0;
            border-radius: 5px;
            width: 100%;
            transition: all 0.3s ease;
            font-family: "Open Sans", sans-serif;
            font-weight: 600;

        }
        .chapter-navigation button {
            margin-left: 5px;
            margin-right: 5px;
            padding: 10px 20px;
            background-color: #4CAF50;
            border-radius: 5px;
            border: none;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
            font-family: "Open Sans", sans-serif;
            font-weight: 600;
        }
        .chapter-navigation button:hover {
            background-color: #45a049;
        }
        .chapter-navigation select {
            max-height: 100px;
            overflow-y: auto;
            font-family: "Open Sans", sans-serif;
            font-weight: 600;
            border-radius: 5px;
        }
        #top-button {
            display: none;
            position: fixed;
            bottom: 20px;
            right: 30px;
            z-index: 99;
            border: none;
            outline: none;
            background-color: #555;
            color: white;
            cursor: pointer;
            padding: 15px;
            border-radius: 4px;

        }
        #top-button:hover {
            background-color: #444;
        }
        h1 {
            font-size: 2em;
            color: #333;
        }
        #top-button {
            display: none;
            position: fixed;
            bottom: 20px;
            right: 30px;
            z-index: 99;
            border: none;
            outline: none;
            background-color: #555;
            color: white;
            cursor: pointer;
            padding: 15px;
            border-radius: 4px;
        }
        #top-button:hover {
            background-color: #444;
        }
    </style>
    <script>
        window.onscroll = function() {scrollFunction()};
        function scrollFunction() {
            var chapterNavigation = document.getElementsByClassName("chapter-navigation")[0];
            var topButton = document.getElementById("top-button");
            if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
                topButton.style.display = "block";
                topButton.style.bottom = "50px";
                chapterNavigation.style.position = "fixed";
                chapterNavigation.style.bottom = "0";
                chapterNavigation.style.width = "100%";
            } else {
                topButton.style.display = "none";
                chapterNavigation.style.position = "static";
            }
        }
        function topFunction() {
            document.body.scrollTop = 0;
            document.documentElement.scrollTop = 0;
        }
    </script>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="chapter-navigation">
    <% if(chapter.getNumber() > 1){%>
    <button onclick="window.location.href='comic?id=' + '<%=chapter.getComicId()%>' + '&chapter=<%= chapter.getNumber() - 1 %>'" style="font-size: 20px"> < </button>
    <% } %>
    <select onchange="window.location.href='comic?id=' + '<%=chapter.getComicId()%>' +'&chapter=' + this.value">
        <% for(Chapter ch : chapters) { %>
        <option value="<%= ch.getNumber() %>" <%= ch.getNumber() == chapter.getNumber() ? "selected" : "" %> >Chapter <%= ch.getNumber() %></option>
        <% } %>
    </select>

    <% if(chapter.getNumber() < chapters.size()) { %>
    <button onclick="window.location.href='comic?id=' + '<%=chapter.getComicId()%>' + '&chapter=<%= chapter.getNumber() + 1 %>'" style="font-size: 20px"> > </button>
    <% } %>
</div>

<div class="chapter-content">
    <% if(images.size() > 0) { %>
    <% for(Image image : images) { %>
    <img src="<%= image.getUrl() %>" alt="<%=chapter.getNumber()%>">
    <% } %>
    <% } else { %>
    <img src="img/nothing.png" alt="No images" height="100px">
    <% } %>
</div>

<button onclick="topFunction()" id="top-button" class="fa fa-angle-up" title="Go to top" style="background: #fa921f"></button>

</body>
</html>