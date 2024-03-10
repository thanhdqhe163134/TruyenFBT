package controller;

import dao.AccountDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import util.PasswordUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "AccountRegisterServlet", value = "/register")
public class AccountRegisterServlet extends HttpServlet {

    AccountDAO accountDAO = new AccountDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("view/account-register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String ReEnterPassword = request.getParameter("ReEnterPassword");
        String hassPass = "";

        if (password.equals(ReEnterPassword)) {
            if(accountDAO.existsByUsername(username)){
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher("view/account-register.jsp").forward(request, response);
                return;
            }
            try {
                hassPass = PasswordUtil.generateStrongPasswordHash(password);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
            accountDAO.createAccount(username, hassPass, "user");
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Password does not match");
            request.getRequestDispatcher("view/account-register.jsp").forward(request, response);

        }

    }
}
