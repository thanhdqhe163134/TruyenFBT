package dao;

import model.Chapter;
import model.ReadingProgress;
import util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReadingProgressDAO {
    Connection connection = new DBConnect().connection;

    public boolean saveReadingProgress(int accountId, int comicId, int chapterId) {
        try {
            int existingChapterId = isExist(accountId, comicId, chapterId);
            if (existingChapterId != 0) {
                String sql = "UPDATE ReadingProgress SET chapterId = ?, updatedDate = ?, updateCount = updateCount + 1 WHERE accountId = ? AND chapterId = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, chapterId);
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(3, accountId);
                preparedStatement.setInt(4, existingChapterId);
                preparedStatement.executeUpdate();
            } else {
                String sql = "INSERT INTO ReadingProgress (accountId, chapterId, createdDate, updatedDate, updateCount) VALUES (?, ?, ?, ?, 1)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, accountId);
                preparedStatement.setInt(2, chapterId);
                preparedStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int isExist(int accountId, int comicId, int chapteId) {
        if (connection != null) {
            try {
                String sql = "SELECT * FROM ReadingProgress WHERE accountId = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, accountId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int existingChapterId = resultSet.getInt("chapterId");
                    if (existingChapterId == chapteId) {
                     return existingChapterId;
                    }
                    String sql2 = "SELECT rp.* FROM ReadingProgress rp " +
                            "JOIN Chapter c1 ON rp.chapterId = c1.id " +
                            "JOIN Chapter c2 ON c1.comicId = c2.comicId " +
                            "WHERE rp.accountId = ? AND c2.id = ? AND c1.comicId = ?";
                    preparedStatement = connection.prepareStatement(sql2);
                    preparedStatement.setInt(1, accountId);
                    preparedStatement.setInt(2, chapteId);
                    preparedStatement.setInt(3, comicId);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getInt("chapterId");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public Chapter getReadingProgress(int accountId, int id) {
        try {
            String sql = "SELECT c.* FROM Chapter c " +
                    "JOIN ReadingProgress rp ON c.id = rp.chapterId " +
                    "WHERE rp.accountId = ? AND c.comicId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Chapter chapter = new Chapter();
                chapter.setId(resultSet.getInt("id"));
                chapter.setComicId(resultSet.getInt("comicId"));
                chapter.setNumber(resultSet.getInt("number"));
                chapter.setCreatedDate(resultSet.getTimestamp("createdDate"));
                chapter.setUpdatedDate(resultSet.getTimestamp("updatedDate"));
                chapter.setDeleted(resultSet.getBoolean("deleted"));
                return chapter;
            }
        } catch (Exception e) {
            e.printStackTrace();
    }
        return null;
    }
}
