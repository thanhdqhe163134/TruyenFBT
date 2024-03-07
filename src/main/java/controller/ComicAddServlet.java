package controller;

import dao.GenreDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ComicAddServlet", value = "/addComic")
public class ComicAddServlet extends HttpServlet {

    GenreDAO genreDAO = new GenreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("allGenres", genreDAO.getAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/comic-add.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
