package dao;

import model.Genre;
import util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    Connection connection = new DBConnect().connection;

    public List<Genre> getAll() {
        try{
            String sql = "SELECT * FROM Genre";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Genre> genres = new ArrayList<>();
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getInt("id"));
                genre.setName(resultSet.getString("name"));
                genres.add(genre);
            }
            return genres;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Genre findByName(String newGenreName) {
        try{
            String sql = "SELECT * FROM Genre WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newGenreName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getInt("id"));
                genre.setName(resultSet.getString("name"));
                return genre;
            }
        } catch (Exception e) {
            e.printStackTrace();
    }
        return null;
    }

    public Genre create(String newGenreName) {
        try{
            String sql = "INSERT INTO Genre (name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newGenreName);
            preparedStatement.executeUpdate();
            return findByName(newGenreName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
