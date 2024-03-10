<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Register</title>
  <link rel="stylesheet" href="css/account-login.css">
</head>

<body>
<div class="wrapper">
  <div class="login">
    <img class="logo" src="https://cdn.haitrieu.com/wp-content/uploads/2021/10/Logo-Dai-hoc-FPT.png">
    <br>
    <br>
    <form action="register" method="post" class="form-login">
      <div class="form-input">
        <input type="text" class="enter" placeholder="Username" name="username">
        <i class="fa-solid fa-user"></i>
      </div>
      <div class="form-input">
        <input type="password" class="enter" placeholder="Password" name="password">
        <i class="fa-solid fa-lock"></i>
      </div>
      <div class="form-input">
        <input type="password" class="enter" placeholder="Password" name="ReEnterPassword">
        <i class="fa-solid fa-lock"></i>
      </div>

      <p class="error-mess"><b>${error}</b></p>
      <input type="submit" value="Submit" class="form-submit">

      <br>

      <a href="login" class="back">
        Back to Login Page
      </a>
    </form>
  </div>
</div>

</body>
</html>