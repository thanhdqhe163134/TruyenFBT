package controller;

import dao.CommentDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Comment;

import java.io.IOException;

@WebServlet(name = "CommentServlet", value = "/comment")
public class CommentServlet extends HttpServlet {
    CommentDAO commentDAO = new CommentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String comicId = request.getParameter("comicId");
        String number = request.getParameter("number");
        commentDAO.deleteComment(id);
        response.sendRedirect("comic?id=" + comicId + "&chapter=" + number);



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("add")) {
            String comicId = request.getParameter("comicId");
            String chapterId = request.getParameter("chapterId");
            String accountId = request.getParameter("accountId");
            String content = request.getParameter("content");
            String parentId = request.getParameter("parentId");
            String number = request.getParameter("number");
            commentDAO.addComment(chapterId, accountId, content, parentId);

            response.sendRedirect("comic?id=" + comicId + "&chapter=" + number);
        }

    }
}
