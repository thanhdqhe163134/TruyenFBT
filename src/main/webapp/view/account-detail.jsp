<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MANAGE ACCOUNTS</title>
    <link rel="stylesheet" href="css/Home.css">
    <link rel="stylesheet" href="css/AccountCreate.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="header.jsp"/>
    <div class="top-title">
    </div>
    <div class="title-box">
        <h2 class="title">Account Details</h2>
        <a href="list-account">
                <span>
                    <i class="fa-solid fa-angle-left"></i>
                    Back to list
                </span>
        </a>
    </div>

    <div class="form">
        <br>
        <c:if test="${sessionScope.loggedInUser.getRole() == 'admin'}">
            <div class="form-content">
                <h3> ID</h3>
                <input type="text" name="student_id" class="no-change" value="${a.getId()}" readonly>
            </div>
        <div class="form-content">
            <h3>Username</h3>
            <input type="text" name="username" class="no-change" value="${a.getUsername()}" readonly>
        </div>
        <div class="form-content role">
            <h3>Role</h3>
                <input type="radio" name="role" class="no-change" value="user" disabled  ${a.getRole().equals("user")?"checked":""}>User
                <input type="radio" name="role" class="no-change" value="admin" disabled  ${a.getRole().equals("admin")?"checked":""}>Admin
        </div>
        <div class="form-content">
            <h3>Create date</h3>
            <input type="date" name="create_date" class="no-change" value="${a.getCreatedDate()}" readonly>
        </div>
        <div class="form-content">
            <h3>Update date</h3>
            <input type="date" name="update_date" class="no-change" value="${a.getUpdatedDate()}" readonly>
        </div>
        <a href="profile?editMode=true&id=${a.getId()}" class="form-content submit">
            <input type="submit" name="submit" value="EDIT">
        </a>
        </c:if>

    </div>
</div>
</body>
<script>
    var today = new Date();
    document.getElementById("today").value = today.toLocaleDateString("en-CA");
</script>
</html>
