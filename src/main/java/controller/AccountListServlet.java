package controller;

import dao.AccountDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "AccountListServlet", value = "/list-account")
public class AccountListServlet extends HttpServlet {
    AccountDAO accountDAO = new AccountDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");
        List<Account> acc = new ArrayList<>();

        if (search != null) {
            acc = accountDAO.search(search);
        } else {
            acc = accountDAO.getAll();
        }
        if (sort != null) {
            if (sort.equals("newest")) {
                acc.sort(Comparator.comparing(Account::getCreatedDate).reversed());
            } else if (sort.equals("oldest")) {
                acc.sort(Comparator.comparing(Account::getCreatedDate));
            }
        }
        request.setAttribute("acc", acc);
        request.getRequestDispatcher("view/account-list.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
