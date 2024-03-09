package dao;

import util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImgDAO {
    Connection connection = new DBConnect().connection;

    public boolean create(String relativePath, String chapterId) {
        try {
            String sql = "SELECT MAX(number) FROM Image WHERE chapterId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(chapterId));
            ResultSet resultSet = preparedStatement.executeQuery();
            int maxNumber = 0;
            if (resultSet.next()) {
                maxNumber = resultSet.getInt(1);
            }
            String sql2 = "INSERT INTO Image (url, number, chapterId) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setString(1, relativePath);
            preparedStatement.setInt(2, maxNumber + 1);
            preparedStatement.setInt(3, Integer.parseInt(chapterId));
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    public boolean update(String id, String relativePath) {
        try {
            String sql = "UPDATE Image SET url = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, relativePath);
            preparedStatement.setInt(2, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean delete(String id) {
        try {
            String sql = "UPDATE Image SET url = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "");
            preparedStatement.setInt(2, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
