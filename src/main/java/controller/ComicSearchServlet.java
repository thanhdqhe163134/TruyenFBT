package controller;

import dao.ComicDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Comic;
import model.Genre;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "ComicSearchServlet", value = "/search")
public class ComicSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        ComicDAO comicDAO = new ComicDAO();
        if (query.equals("")) {
            return;
        }
        List<Comic> comicList = comicDAO.searchComic(query);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        for (Comic comic : comicList) {
            out.println("<div class='book-card2' onclick=\"window.location.href='comic?title=" + comic.getTitle() + "'\">");
            out.println("<img src='" + comic.getImg() + "' alt='" + comic.getTitle() + "' class='book-img2'>");
            out.println("<h3 class='book-title2'>" + comic.getTitle() + "</h3>");
            out.println("<div class='book-genres2'>");
            List<Genre> genres = comic.getGenres();
            genres = genres.stream().sorted(Comparator.comparing(Genre::getName)).collect(Collectors.toList());
            for (Genre genre : genres) {
                out.println("<span class='genre2'>" + genre.getName() + "</span>");
            }
            out.println("</div>");
            out.println("</div>");


        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
