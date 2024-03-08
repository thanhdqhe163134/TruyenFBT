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
@WebServlet(name = "ComicEditServlet", value = "/edit-comic")
public class ComicEditServlet extends HttpServlet {
    GenreDAO genreDAO = new GenreDAO();
    ComicDAO comicDAO = new ComicDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String[] selectedGenres = request.getParameterValues("genres");
        String[] newGenresNames = request.getParameterValues("newGenres");
        Part filePart = request.getPart("image");
        String relativePath = "";
        String id = request.getParameter("id");

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
        if(id != null && !id.isEmpty()){
            String newTitle = comicDAO.update(Integer.parseInt(id) ,title, description, relativePath, allGenresIds.toArray(new String[0]));
            if(newTitle != null){
                response.sendRedirect("comic?title=" + newTitle);
            }else{
                request.setAttribute("message", "Comic creation failed");
                response.sendRedirect("comic?title=" + title);
            }
        }



    }
}
