package controller;

import dao.ImgDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import util.ConvertIMG;

import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
@WebServlet(name = "ChapterIMGServlet", value = "/chapter-img")
public class ChapterIMGServlet extends HttpServlet {

    ImgDAO imgDAO = new ImgDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String chapterId = request.getParameter("chapterId");
        String id = request.getParameter("id");
        Part filePart = request.getPart("image");
        String relativePath = "";
        if(filePart != null && filePart.getSize() > 0){
            relativePath = ConvertIMG.saveImage(filePart, request, "img");
        }
        try {
            if(action.equals("add")){
                if(imgDAO.create(relativePath, chapterId)){
                    response.getWriter().write("Image added successfully");
                } else {
                    response.getWriter().write("Failed to add image");
                }
            } else if(action.equals("edit")){
                if(filePart != null && imgDAO.update(id, relativePath)){
                    response.getWriter().write("Image updated successfully");
                } else {
                    response.getWriter().write("Failed to update image");
                }
            } else if(action.equals("delete")){
                if(imgDAO.delete(id)){
                    response.getWriter().write("Image deleted successfully");
                } else {
                    response.getWriter().write("Failed to delete image");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred");
        }
    }
}