<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Chapter" %>
<%@ page import="model.Comment" %>
<%@ page import="model.Image" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Account" %>
<html>
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
    <script src="js/chapter.js" defer></script>
    <link rel="stylesheet" href="css/chapter.css">

</head>
<body>
<jsp:include page="header.jsp"/>

<div class="chapter-navigation">
    <% if(isAdmin){ %>
    <button id="editButton" style="background: #0a86db">Manage Images</button>
    <% } %>

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

<% if(isAdmin){ %>
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <table>
            <tr>
                <th>No</th>
                <th>URL</th>
                <th>Image</th>
                <th></th>
            </tr>
            <% for(Image image : chapter.getImages()) { %>
            <tr>
                <td><%= image.getOrder() %></td>
                <td><%= image.getUrl() %></td>
                <td>
                    <img src="<%=image.getUrl()%>" style="height: 100px; width: auto">
                    <input type="file" id="edit-image-file-<%=image.getId()%>">
                </td>
                <td>
                    <button class="edit-image" id="edit-image-<%=image.getId()%>" imgId="<%=image.getId()%>">Update</button>
                    <button class="delete-image" imgId="<%=image.getId()%>">Delete</button>
                </td>
            </tr>
            <% } %>
        </table>
        <input type="file" id="new-image-file">
        <button class="add-image" id="add-image" data-chapter-id="<%= chapter.getId() %>">Add</button>
    </div>
</div>
<% } %>

<div class="chapter-content">
    <% if (images.size() > 0) { %>
    <% for (Image image : images) { %>
    <img src="<%= image.getUrl() %>" alt="<%=image.getOrder()%>">
    <% } %>
    <% } else { %>
    <img src="img/nothing.png" alt="No images" height="100px">
    <% } %>
</div>

<button onclick="topFunction()" id="top-button" class="fa fa-angle-up" title="Go to top"
        style="background: #fa921f"></button>


</body>
</html>