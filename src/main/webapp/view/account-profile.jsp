<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>MANAGE ACCOUNTS</title>
  <link rel="stylesheet" href="css/Home.css">
  <link rel="stylesheet" href="css/MyProfile.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
        integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
        crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body>
<div class="wrapper">
  <jsp:include page="header.jsp"/>
  <div class="top-title">
    <h3>MY PROFILE</h3>
  </div>

  <div class="mainpage">
    <div class="side-bar">
      <div class="avatar">
        <% if(request.getParameter("changeAvatar") != null && request.getParameter("changeAvatar").equals("true")) { %>
        <form action="profile" method="post" enctype="multipart/form-data">
          <img src="${empty a.getImg() ? 'img/user.png' : a.getImg()}">
          <input type="file" name="image">
          <input type="hidden" name="id" value="${sessionScope.loggedInUser.getId()}">
          <div class="submit">
          <input type="submit" value="Save">
            </div>
        </form>
        <% } else { %>
        <img src="${empty a.getImg() ? 'img/user.png' : a.getImg()}">
        <% } %>
        <span>Role: ${a.getRole()}</span>
      </div>
      <div class="menu">
        <a href="profile?changePassword=true">
          <i class="fa-solid fa-lock"></i>
          <p>Change password</p>
        </a>
        <br>
        <a href="profile?changeAvatar=true">
          <i class="fa-solid fa-user"></i>
          <p>Change avatar</p>
        </a>

      </div>
    </div>
    <form action="profile" method="post" enctype="multipart/form-data">
      <div class="profile-content">
        <%
          if (request.getAttribute("changePassword") != null) {
        %>
        <h2>Change my password</h2>
        <div class="pass-content">
          <h3>Current password</h3>
          <input type="password" name="current" value="" required>
          <p>${wrongPass}</p>
        </div>
        <div class="pass-content">
          <h3>New password</h3>
          <input type="password" name="new" value="" required>
        </div>
        <div class="pass-content">
          <h3>Password confirmation</h3>
          <input type="password" name="confirm" value="" required>
          <p>${noMatch}</p>
        </div>
        <input type="hidden" name="id" value="${sessionScope.loggedInUser.getId()}">
        <input type="hidden" name="changePassword" value="changePassword">
        <div class="submit">
          <input type="submit" name="submit" value="Save password">
        </div>
        <% } %>
        <div class="profile-list">
          <h3>Username</h3>
          <p>${a.getUsername()}</p>
        </div>
        <div class="profile-list">
          <h3>Created date</h3>
          <p>${a.getCreatedDate()}</p>
        </div>
        <div class="profile-list">
          <h3>Updated date</h3>
          <p>${a.getUpdatedDate()}</p>
        </div>
      </div>
    </form>
  </div>

</div>
</body>
</html>
