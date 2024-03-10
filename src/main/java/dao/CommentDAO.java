package dao;

import util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class CommentDAO {

    Connection connection = new DBConnect().connection;

    public void addComment(String chapterId, String accountId, String content, String parentId) {
        try {
            String sql = "INSERT INTO Comment(chapterId, accountId, content, parentId, createdDate, updatedDate) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(chapterId));
            preparedStatement.setInt(2, Integer.parseInt(accountId));
            preparedStatement.setString(3, content);
            if (parentId != null) {
                preparedStatement.setInt(4, Integer.parseInt(parentId));
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void deleteComment(String id) {
        try {
            String sql = "UPDATE Comment SET deleted = 1 WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
