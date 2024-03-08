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
            if (isExist(accountId, comicId, chapterId)) {
                String sql = "UPDATE ReadingProgress SET chapterId = ?, updatedDate = ?, updateCount = updateCount + 1 WHERE accountId = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, chapterId);
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(3, accountId);
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

    public boolean isExist(int accountId, int comicId, int chapteId) {
        if (connection != null) {
            try {
                String sql = "SELECT * FROM ReadingProgress WHERE accountId = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, accountId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int existingChapterId = resultSet.getInt("chapterId");
                    if (existingChapterId == chapteId) {
                        return true;
                    }
                    sql = "SELECT rp.* FROM ReadingProgress rp " +
                            "JOIN Chapter c1 ON rp.chapterId = c1.id " +
                            "JOIN Chapter c2 ON c1.comicId = c2.comicId " +
                            "WHERE rp.accountId = ? AND c2.id = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, accountId);
                    preparedStatement.setInt(2, chapteId);
                    resultSet = preparedStatement.executeQuery();
                    return resultSet.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public Chapter getReadingProgress(int accountId) {
        try {
            String sql = "SELECT * FROM Chapter WHERE id = (SELECT chapterId FROM ReadingProgress WHERE accountId = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
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
