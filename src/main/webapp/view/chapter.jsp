<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Chapter" %>
<%@ page import="model.Comment" %>
<%@ page import="model.Image" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Account" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
%>
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


<div class="container bootstrap snippets bootdey">
    <div class="row">
        <div class="col-md-12">
            <div class="blog-comment">
                <h3 class="text-success">Comments</h3>
                <hr/>
                <!-- Form to post a new comment -->
                <% if (isLoggedIn) { %>
                <form action="comment" method="post">
                    <input type="hidden" name="chapterId" value="<%= chapter.getId() %>"/>
                    <input type="hidden" name="action" value="add"/>
                    <input type="hidden" name="accountId" value="<%= user_id %>"/>
                    <input type="hidden" name="comicId" value="<%= chapter.getComicId() %>"/>
                    <input type="hidden" name="number" value="<%=chapter.getNumber()%>">
                    <div style="display: flex;">
                                    <textarea
                                            name="content"
                                            placeholder="Add your comment here"
                                            style="width: 92%; min-height: 80px"
                                    ></textarea>
                        <button style="
                    max-height: 30px;
                    width: 6%;
                    border-color: #f18121;
                    color: #f18121;
                    background-color: white;
                    border-radius: 4px;
                  "
                        >
                            Send
                        </button>
                    </div>

                </form>
                <% } %>
                <ul class="comments">
                    <%
                        for (Comment comment : comments) {
                    %>
                    <li class="clearfix">
                        <img src="<%=comment.getImg() != null ? comment.getImg() : "img/user.png"%>"
                             alt="<%=comment.getUsername()%>" class="avatar">
                        <div class="post-comments">
                            <p class="meta"><%= sdf.format(comment.getCreatedDate()) %> <a
                                    href=""><%=comment.getUsername()%>
                            </a>
                                <i class="pull-right">
                                            <span class="reply-comment" style="cursor: pointer; color: #0a86db"
                                                  onclick="toggleReplyForm(<%= comment.getId() %>)"
                                            ><i class="fa fa-comment"></i> Reply</span>
                                    <% if(isAdmin || comment.getAccountId() == user_id){ %>
                                    <a href="comment?id=<%=comment.getId()%>&comicId=<%=chapter.getComicId()%>&number=<%=chapter.getNumber()%>"
                                       style="color: red"><small>Delete</small></a>
                                    <% } %>
                                </i></p>
                            <% if(comment.isDeleted()){ %>
                            <p style="color: red">This comment has been deleted</p>
                            <% } else { %>
                            <p>
                                <%= comment.getContent()%>
                            </p>

                            <% } %>
                            <div id="reply-form-<%= comment.getId() %>" style="display:none;">
                                <form action="comment" method="post" style="margin-top: 1%">
                                    <input type="hidden" name="action" value="add"/>
                                    <input type="hidden" name="accountId" value="<%= user_id %>"/>
                                    <input type="hidden" name="parentId" value="<%= comment.getId() %>"/>
                                    <input type="hidden" name="chapterId" value="<%= chapter.getId() %>"/>
                                    <input type="hidden" name="comicId" value="<%= chapter.getComicId() %>"/>
                                    <input type="hidden" name="number" value="<%=chapter.getNumber()%>">

                                    <div style="display: flex; justify-content: flex-start">
                                        <textarea name="content" placeholder="Reply to this comment..."
                                                  style="width: 50%"></textarea>
                                        <button type="submit" style="max-height: 20px;margin-left: 10px; border-color: #f18121;
    color: #f18121;
    background-color: white;
    border-radius: 4px;">Send
                                        </button>
                                    </div>

                                </form>
                            </div>
                        </div>
                        <%
                            if (comment.getChildren() != null && comment.getChildren().size() > 0) {
                                for (Comment child : comment.getChildren()) {
                        %>
                        <ul class="comments">
                            <li class="clearfix">
                                <img src="<%=child.getImg() != null ? child.getImg() : "img/user.png"%>"
                                     alt="<%=child.getUsername()%>" class="avatar">
                                <div class="post-comments">
                                    <p class="meta"><%= sdf.format(comment.getCreatedDate()) %> <a
                                            href=""><%=child.getUsername()%>
                                    </a>
                                        <i class="pull-right">
                                            <span class="reply-comment" style="cursor: pointer; color: #0a86db"
                                                  onclick="toggleReplyForm(<%= comment.getId() %>)"
                                            ><i class="fa fa-comment"></i> Reply</span>
                                            <% if(isAdmin || child.getAccountId() == user_id){ %>
                                            <a href="comment?id=<%=child.getId()%>&comicId=<%=chapter.getComicId()%>&number=<%=chapter.getNumber()%>"
                                               style="color: red"><small>Delete</small></a>
                                            <% } %>
                                        </i></p>
                                    <% if(child.isDeleted()){ %>
                                    <p style="color: red">This comment has been deleted</p>
                                    <% } else { %>
                                    <p>
                                    <%= child.getContent()%>
                                    </p>

                                    <% } %>
                                    <div id="reply-form-<%= child.getId() %>" style="display:none;">
                                        <form action="comment" method="post" style="margin-top: 1%">
                                            <input type="hidden" name="action" value="add"/>
                                            <input type="hidden" name="accountId" value="<%= user_id %>"/>
                                            <input type="hidden" name="parentId" value="<%= comment.getId() %>"/>
                                            <input type="hidden" name="chapterId" value="<%= chapter.getId() %>"/>
                                            <input type="hidden" name="comicId" value="<%= chapter.getComicId() %>"/>
                                            <input type="hidden" name="number" value="<%=chapter.getNumber()%>">

                                            <div style="display: flex; justify-content: flex-start">
                                                <textarea name="content" placeholder="Reply to this comment..."
                                                          style="width: 50%"></textarea>
                                                <button type="submit" style="max-height: 20px;margin-left: 10px; border-color: #f18121;
    color: #f18121;
    background-color: white;
    border-radius: 4px;">Send
                                                </button>
                                            </div>

                                        </form>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <%
                                }
                            }
                        %>
                    </li>
                    <%
                        }
                    %>
                </ul>
            </div>
        </div>

    </div>
</div>

</body>
</html>