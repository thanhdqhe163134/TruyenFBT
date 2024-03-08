package util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ConvertIMG {

    public static String saveImage(Part filePart, HttpServletRequest request, String saveDir) throws IOException {
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + saveDir;
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        String filePath = savePath + File.separator + fileName;
        filePart.write(filePath);
        return saveDir + File.separator + fileName;
    }
}