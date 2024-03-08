package controller;

import dao.GenreDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "GenreDeleteServlet", value = "/delete-genre")
public class GenreDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String genre = request.getParameter("genre");
        GenreDAO genreDAO = new GenreDAO();
        genreDAO.delete(genre);
        response.sendRedirect("home");

    }
}
