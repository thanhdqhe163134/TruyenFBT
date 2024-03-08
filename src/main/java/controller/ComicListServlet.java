package controller;

import dao.ComicDAO;
import dao.GenreDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Comic;
import model.Genre;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ComicListServlet", value = "/home")
public class ComicListServlet extends HttpServlet {
    GenreDAO genreDAO = new GenreDAO();

    ComicDAO comicDAO = new ComicDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");
        String genre = request.getParameter("genre");
        List<Comic> comicList;

        if (sortBy == null || sortBy.equals("all")) {
            comicList = comicDAO.getAll();
        } else if (sortBy.equals("newest")) {
            comicList = comicDAO.getAllSortedByUpdatedDate();
        } else if (sortBy.equals("views")) {
            comicList = comicDAO.getAllSortedByViews();
        } else {
            comicList = comicDAO.getAll();
        }

        if (genre != null && !genre.isEmpty()) {
            comicList = comicList.stream()
                    .filter(comic -> comic.getGenres().stream()
                            .anyMatch(g -> g.getName().equalsIgnoreCase(genre)))
                    .collect(Collectors.toList());
        }

        if(sortBy != null){
            request.setAttribute("sortBy", sortBy);
        }
        if(genre != null){
            request.setAttribute("genre", genre);
        }

        request.setAttribute("comicList", comicList);
        request.setAttribute("genreList", genreDAO.getAll());
        request.getRequestDispatcher("view/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
