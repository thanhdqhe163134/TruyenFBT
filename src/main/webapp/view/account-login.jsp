<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LOGIN</title>
    <link rel="stylesheet" href="css/account-login.css">
</head>

<body>
<div class="wrapper">
    <div class="login">
        <img class="logo" src="https://cdn.haitrieu.com/wp-content/uploads/2021/10/Logo-Dai-hoc-FPT.png">
        <br>
        <br>
        <form action="login" method="post" class="form-login">
            <div class="form-input">
                <%
                    String username = request.getAttribute("username").toString();
                    String password = request.getAttribute("password").toString();
                %>
                <input type="text" class="enter" placeholder="Username" name="username" value="<%=username != null ? username : ""%>">
                <i class="fa-solid fa-user"></i>

            </div>
            <div class="form-input">
                <input type="password" class="enter" placeholder="Password" name="password" value="<%=password != null ? password : ""%>">
                <i class="fa-solid fa-lock"></i>
            </div>
            <div >
                <input type="checkbox" id="rememberMe" name="rememberMe">
                <label for="rememberMe">Remember me</label>
            </div>
            <p class="error-mess"><b>${error}</b></p>
            <input type="submit" value="Login" class="form-submit">

            <br>

            <a href="home" class="back">
                Back Homepage
            </a>
        </form>
    </div>
</div>

</body>
</html>