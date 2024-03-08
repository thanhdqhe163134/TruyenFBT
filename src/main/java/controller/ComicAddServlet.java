package controller;

import dao.ComicDAO;
import dao.GenreDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Genre;
import util.ConvertIMG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
@WebServlet(name = "ComicAddServlet", value = "/add-comic")
public class ComicAddServlet extends HttpServlet {

    GenreDAO genreDAO = new GenreDAO();

    ComicDAO comicDAO = new ComicDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("allGenres", genreDAO.getAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("view/comic-add.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String[] selectedGenres = request.getParameterValues("genres");
        String[] newGenresNames = request.getParameterValues("newGenres");
        Part filePart = request.getPart("image");
        String relativePath = "";
        if(filePart != null && filePart.getSize() > 0){
            relativePath = ConvertIMG.saveImage(filePart, request, "img");
        }

        List<String> allGenresIds = selectedGenres != null ? new ArrayList<>(Arrays.asList(selectedGenres)) : new ArrayList<>();
        if (newGenresNames != null) {
            for (String newGenreName : newGenresNames) {
                if (newGenreName != null && !newGenreName.trim().isEmpty()) {
                    Genre existingGenre = genreDAO.findByName(newGenreName);
                    if (existingGenre == null) {
                        Genre newGenre = genreDAO.create(newGenreName);
                        allGenresIds.add(String.valueOf(newGenre.getId()));
                    } else {
                        allGenresIds.add(String.valueOf(existingGenre.getId()));
                    }
                }
            }
        }

        String newTitle = comicDAO.create(title, description, relativePath, allGenresIds.toArray(new String[0]));
        if(newTitle != null){
            response.sendRedirect("comic?title=" + newTitle);
        }else{
            request.setAttribute("message", "Comic creation failed");
            doGet(request, response);
        }







        }
}
