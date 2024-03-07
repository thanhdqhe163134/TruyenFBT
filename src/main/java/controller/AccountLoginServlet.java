package controller;

import dao.AccountDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Account;

import java.io.IOException;

@WebServlet(name = "AccountLoginServlet", value = "/login")
public class AccountLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie arr[] = request.getCookies();
        String user = "";
        String pass = "";
        if (arr != null) {
            for (Cookie o : arr) {
                if (o.getName().equals("rememberedUsername")) {
                    user = o.getValue();
                }
                if (o.getName().equals("rememberedPassword")) {
                    request.setAttribute("password", o.getValue());
                    pass = o.getValue();
                }
            }

        }
        request.setAttribute("username", user);
        request.setAttribute("password", pass);
        request.getRequestDispatcher("view/account-login.jsp").forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean rememberMe = request.getParameter("rememberMe") != null;
        String id = request.getParameter("id");

        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.login(username, password);

        if (account != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", account);

            if (rememberMe) {
                // Nếu checkbox "Remember Me" được chọn, tạo và lưu cookies
                Cookie usernameCookie = new Cookie("rememberedUsername", username);
                Cookie passwordCookie = new Cookie("rememberedPassword", password);

                usernameCookie.setMaxAge(7 * 24 * 60 * 60); // Thời gian sống của cookie (7 ngày)
                passwordCookie.setMaxAge(7 * 24 * 60 * 60);

                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            } else {
                // Nếu checkbox không được chọn, xóa cookies nếu có
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("rememberedUsername") || cookie.getName().equals("rememberedPassword")) {
                            cookie.setMaxAge(0); // Đặt thời gian sống của cookie về 0 để xóa
                            response.addCookie(cookie);
                        }
                    }
                }
            }
            if(id != null){
                response.sendRedirect("chapter?id=" + id);
            }
            else {
                response.sendRedirect("home?loginSuccess=true");
            }
        } else {
            request.setAttribute("loginFailed", true);
            request.getRequestDispatcher("/view/account-login.jsp").forward(request, response);

        }
    }
}
