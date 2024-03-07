package controller;

import dao.ComicDAO;
import dao.GenreDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Comic;
import model.Genre;

import java.io.IOException;

@WebServlet(name = "ConmicServlet", value = "/comic")
public class ConmicServlet extends HttpServlet {
    ComicDAO comicDAO = new ComicDAO();

    GenreDAO genreDAO = new GenreDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        Comic comic = comicDAO.getComicByTitle(title);




        request.setAttribute("allGenres", genreDAO.getAll());
        request.setAttribute("comic", comic);
        request.getRequestDispatcher("view/comic.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
