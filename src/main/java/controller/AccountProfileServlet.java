package controller;

import dao.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Account;
import util.ConvertIMG;
import util.PasswordUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
@WebServlet(name = "AccountProfileServlet", value = "/profile")
public class AccountProfileServlet extends HttpServlet {

    AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");
        HttpSession session = request.getSession();
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        if (loggedInUser != null && userId == null || userId.isEmpty()) {
            request.setAttribute("a", accountDAO.get(loggedInUser.getId()));
            String changePassword = null;
            String changeAvatar = null;
            changePassword = request.getParameter("changePassword");
            changeAvatar = request.getParameter("changeAvatar");
            request.setAttribute("changeAvatar", changeAvatar);
            request.setAttribute("changePassword", changePassword);
            request.getRequestDispatcher("view/account-profile.jsp").forward(request, response);
        } else if (userId != null && !userId.isEmpty() && loggedInUser.getRole().equals("admin")) {
            request.setAttribute("a", accountDAO.get(Integer.parseInt(userId)));
            request.getRequestDispatcher("view/account-detail.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String changePassword = request.getParameter("changePassword");
        String current = request.getParameter("current");
        String newPass = request.getParameter("new");
        String confirm = request.getParameter("confirm");

        Account a = accountDAO.get(Integer.parseInt(id));
        if(changePassword != null) {
            try {
                if (PasswordUtil.validatePassword(current, a.getPassword()) == false) {
                    String wrongPass = "Invalid current password";
                    request.setAttribute("wrongPass", wrongPass);
                    request.setAttribute("a", a);
                    request.setAttribute("changePassword", changePassword);
                    request.getRequestDispatcher("view/account-profile.jsp").forward(request, response);
                } else {
                    if (newPass.equals(confirm) == false) {
                        String noMatch = "Password confirmation doesn't match New Password";
                        request.setAttribute("noMatch", noMatch);
                        request.setAttribute("a", a);
                        request.setAttribute("changePassword", changePassword);
                        request.getRequestDispatcher("view/account-profile.jsp").forward(request, response);
                    } else {
                        String hashPassword = PasswordUtil.generateStrongPasswordHash(newPass);
                        accountDAO.changePassword(Integer.parseInt(id), hashPassword);
                        response.sendRedirect("profile");
                    }
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        } else {
            Part filePart = request.getPart("image");
            String relativePath = "";
            if(filePart != null && filePart.getSize() > 0){
                relativePath = ConvertIMG.saveImage(filePart, request, "img");
            }
            if(accountDAO.updateAvatar(Integer.parseInt(id), relativePath)){
                response.sendRedirect("profile");
            } else {
                response.getWriter().write("Failed to update avatar");
            }
        }

    }
}
