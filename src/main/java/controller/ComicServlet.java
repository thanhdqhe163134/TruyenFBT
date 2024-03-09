package controller;

import dao.ChapterDAO;
import dao.ComicDAO;
import dao.GenreDAO;
import dao.ReadingProgressDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Account;
import model.Chapter;
import model.Comic;
import model.Comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ComicServlet", value = "/comic")
public class ComicServlet extends HttpServlet {
    ComicDAO comicDAO = new ComicDAO();
    GenreDAO genreDAO = new GenreDAO();

    ChapterDAO chapterDAO = new ChapterDAO();
    ReadingProgressDAO readingProgressDAO = new ReadingProgressDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String chapter = request.getParameter("chapter");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if(id == null || id.isEmpty()){
            id = "0";
        }
        HttpSession session = request.getSession();
        int accountId = 0;
        if (session != null) {
            Account loggedInUser = (Account) session.getAttribute("loggedInUser");
            if (loggedInUser != null) {
                accountId = loggedInUser.getId();
            }
        }
        if(action != null && action.equals("continueReading")) {
            Chapter currentChapter = readingProgressDAO.getReadingProgress(accountId,Integer.parseInt(id));
            if (currentChapter != null) {
                response.sendRedirect("comic?id=" + id + "&chapter=" + currentChapter.getNumber());

            } else {
                response.sendRedirect("comic?id=1&chapter=1");
            }
        } else {

            if (chapter != null && !chapter.isEmpty() && !chapter.equals("0") && !id.equals("0")) {
                Chapter currentChapter = new Chapter();
                if(action != null && action.equals("add")){
                    currentChapter = chapterDAO.addChapter(Integer.parseInt(chapter), Integer.parseInt(id));
                } else {
                    currentChapter = chapterDAO.getChapterByNumber(Integer.parseInt(chapter), Integer.parseInt(id));
                }


                request.setAttribute("chapter", currentChapter);
                request.setAttribute("allChapters", chapterDAO.getAllChaptersByComicId(Integer.parseInt(id)));
                request.getRequestDispatcher("view/chapter.jsp").forward(request, response);
                comicDAO.updateViewCount();


            } else {
                Comic comic = comicDAO.getComicByTitle(title);
                request.setAttribute("allGenres", genreDAO.getAll());
                request.setAttribute("comic", comic);
                request.getRequestDispatcher("view/comic.jsp").forward(request, response);
            }


            if (chapter != null && !chapter.isEmpty() && accountId != 0 && id != null && !id.isEmpty() && !id.equals("0")) {
                int chapteId = chapterDAO.getChapterByNumber(Integer.parseInt(chapter), Integer.parseInt(id)).getId();
                readingProgressDAO.saveReadingProgress(accountId, Integer.parseInt(id), chapteId);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}