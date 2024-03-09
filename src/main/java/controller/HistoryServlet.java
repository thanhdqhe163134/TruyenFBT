package controller;

import dao.ComicDAO;
import dao.GenreDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Account;
import model.Comic;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "HistoryServlet", value = "/history")
public class HistoryServlet extends HttpServlet {
    ComicDAO comicDAO = new ComicDAO();
    GenreDAO genreDAO = new GenreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            List<Comic> comicList = comicDAO.getComicHistory(loggedInUser.getId());
            String sortBy = request.getParameter("sortBy");
            String genre = request.getParameter("genre");
            if (sortBy == null || sortBy.equals("all")) {
                // Không làm gì cả, giữ nguyên danh sách
            } else if (sortBy.equals("newest")) {
                // Sắp xếp danh sách theo ngày cập nhật mới nhất
                comicList.sort(Comparator.comparing(Comic::getUpdatedDate).reversed());
            } else if (sortBy.equals("views")) {
                // Sắp xếp danh sách theo lượt xem
                comicList.sort(Comparator.comparing(Comic::getViews).reversed());
            } else {
            }
            if(genre != null && !genre.isEmpty()){
                comicList = comicList.stream().filter(comic -> comic.getGenres().stream().anyMatch(g -> g.getName().equalsIgnoreCase(genre))).toList();
            }

            request.setAttribute("comicList", comicList);
            request.setAttribute("genreList", genreDAO.getAll());

            request.getRequestDispatcher("view/comic-history.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
