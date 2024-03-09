<%@ page import="model.Account" %>
<%
    session = request.getSession(false);
    String role = null;
    String username = null;
    long user_id = 0L;
    String img = null;

    if (session != null) {
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            role = loggedInUser.getRole(); // Lấy role từ Account
            username = loggedInUser.getUsername(); // Lấy username từ Account
            user_id = loggedInUser.getId();
            img = loggedInUser.getImg();
        }
    }
    boolean isloggedInUser = role != null; // Kiểm tra xem role có phải là user không
    boolean isAdmin = "admin".equals(role); // Kiểm tra xem role có phải là admin không

%>

<%
    String callingPageURI = (String) request.getAttribute("jakarta.servlet.forward.request_uri");
    boolean isHome = false;
    boolean isBooks = false;
%>


<link rel="stylesheet" href="css/Home.css">
<link rel="stylesheet" href="css/Search.css">
<script src="js/home.js" defer></script>


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
      integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
      crossorigin="anonymous" referrerpolicy="no-referrer"/>


<div class="header">
    <div class="logo">
        <a href="home">
            <img
                    src="https://cdn.haitrieu.com/wp-content/uploads/2021/10/Logo-Dai-hoc-FPT.png"
            />
        </a>

    </div>
    <form class="search-block2">
        <input type="text" id="search-bar" placeholder="Search by Title"
               oninput="searchBooks1()"/>
        <div class="search-results2"></div>
    </form>

    <div class="navbar">

        <ul class="menu">

            <li>
                <a href="home">
                    <span>Home</span>
                </a>
            </li>
            <li>
                <a href="home">
                    <span>Feedback</span>
                </a>
            </li>
            <li>
                <a href="home">
                    <span>Facebook</span>
                </a>
            </li>

        </ul>
        <% if (isloggedInUser) { %>
        <div class="dropdown-items">
                <!-- <p style="font-weight: 600; font-size: 20px;">Admin</p> -->
                <div style="width: 12px"></div>
                <% if (img == null || img.equals("")) { %> <%
                    img = "img/admin-pic.png";
                } %>
                <img
                        src="<%=img%>"
                        class="avatar-user"
                />
                <ul class="menu-items">
                    <label>
                    <li><a href="myProfile?id=<%=user_id%>">Profile</a></li>
                    </label>
                    <label>
                        <li><a href="history">History</a></li>
                    </label>
                    <label>
                        <li><a href="logout">Logout</a></li>

                    </label>
                    <% } else { %>
                    <label>
                        <a href="login" style="text-decoration: none">
                            <span style="padding: 16px 36px; background: orange; color: white; border-radius: 8px">Login</span>
                        </a>
                    </label>
                    <% } %>
                </ul>
            </div>

    </div>
</div>