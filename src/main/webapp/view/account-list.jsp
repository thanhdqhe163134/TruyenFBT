<%@ page import="model.Account" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: tranthanhhuyen
  Date: 06/10/2023
  Time: 00:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MANAGE ACCOUNTS</title>
    <link rel="stylesheet" href="css/AccountList.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <script src="js/AccountList.js" defer></script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="header.jsp"/>
    <div class="top-title">
        <h3>MANAGE ACCOUNTS</h3>
    </div>
    <div class="title-box">
        <h2 class="title">User Accounts Listing</h2>
    </div>
    <div class="user-list">
        <div class="search-sort">
            <div class="search">
                <i class="fa-solid fa-magnifying-glass"></i>
                <form action="list-account" method="get" id="search-form">
                <input type="text" placeholder="Search by Username" id="search" name="search"/>
                <button type="submit" form="search-form" style="display: none"></button>
                </form>
            </div>
            <form class="sort" action="list-account" method="get" id="sort">
                <span>Sort by date created:</span>
                <select name="sort" onchange="document.getElementById('sort').submit();">
                    <option ${sortBy == null?"selected":""}>None</option>
                    <option value="newest" ${sortBy.equals("newest")?"selected":""}>Newest</option>
                    <option value="oldest" ${sortBy.equals("oldest")?"selected":""}>Oldest</option>
                </select>
            </form>
        </div>
        <table class="listing" border="1">
            <thead>
            <tr>
                <th>USER ID</th>
                <th>USERNAME</th>
                <th>ROLE</th>
                <th>CREATE DATE</th>
                <th>ACTION</th>
            </tr>
            </thead>
            <tbody class="list">
            <%
                List<Account> acc = (List<Account>) request.getAttribute("acc");
                for (Account a : acc) {
            %>
                <tr>
                    <td><%=a.getId()%></td>
                    <td><%=a.getUsername()%></td>
                    <td><%=a.getRole()%></td>
                    <td><%=a.getCreatedDate()%></td>
                    <td class="action">
                        <a href="profile?id=<%=a.getId()%>" class="view">
                                    <span>
                                        <i class="fa-solid fa-eye"></i>
                                        View
                                    </span>
                        </a>
                        <a href="delete-account?id=<%=a.getId()%>" class="delete">
                                    <span>
                                        <i class="fa-solid fa-trash-can"></i>
                                        Delete
                                    </span>
                        </a>
                    </td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>

    </div>
</div>
</body>

</html>
