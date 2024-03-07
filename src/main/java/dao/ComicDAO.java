package dao;

import model.Comic;
import model.Genre;
import util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ComicDAO {

    Connection connection = new DBConnect().connection;

    public List<Comic> getAll() {
        Map<Integer, Comic> comics = new HashMap<>();
        try {
            String sql = "SELECT c.*, g.* FROM Comic c " +
                    "JOIN ComicGenre cg ON c.id = cg.comicId " +
                    "JOIN Genre g ON cg.genreId = g.id where c.deleted = 0 and g.deleted = 0";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int comicId = resultSet.getInt("id");
                Comic comic = comics.get(comicId);
                if (comic == null) {
                    comic = new Comic();
                    comic.setId(comicId);
                    comic.setTitle(resultSet.getString("title"));
                    comic.setDescription(resultSet.getString("description"));
                    comic.setImg(resultSet.getString("img"));
                    comic.setGenres(new ArrayList<>());
                    comics.put(comicId, comic);
                }
                Genre genre = new Genre();
                genre.setName(resultSet.getString("name"));
                comic.getGenres().add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(comics.values());
    }

    public List<Comic> getAllSortedByUpdatedDate() {
        Map<Integer, Comic> comics = new LinkedHashMap<>();
        try {
            String sql = "SELECT c.*, g.* FROM Comic c " +
                    "JOIN ComicGenre cg ON c.id = cg.comicId " +
                    "JOIN Genre g ON cg.genreId = g.id where c.deleted = 0 and g.deleted = 0 " +
                    "ORDER BY c.updatedDate DESC ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int comicId = resultSet.getInt("id");
                Comic comic = comics.get(comicId);
                if (comic == null) {
                    comic = new Comic();
                    comic.setId(comicId);
                    comic.setTitle(resultSet.getString("title"));
                    comic.setDescription(resultSet.getString("description"));
                    comic.setImg(resultSet.getString("img"));
                    comic.setGenres(new ArrayList<>());
                    comics.put(comicId, comic);
                }
                Genre genre = new Genre();
                genre.setName(resultSet.getString("name"));
                comic.getGenres().add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(comics.values());
    }

    public List<Comic> getAllSortedByReadingProgress(int day) {
        return null;

    }

    public List<Comic> searchComic(String query) {
        Map<Integer, Comic> comics = new HashMap<>();
        try {
            String sql = "SELECT c.*, g.* FROM Comic c " +
                    "JOIN ComicGenre cg ON c.id = cg.comicId " +
                    "JOIN Genre g ON cg.genreId = g.id where c.deleted = 0 and g.deleted = 0 and c.title like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + query + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int comicId = resultSet.getInt("id");
                Comic comic = comics.get(comicId);
                if (comic == null) {
                    comic = new Comic();
                    comic.setId(comicId);
                    comic.setTitle(resultSet.getString("title"));
                    comic.setDescription(resultSet.getString("description"));
                    comic.setImg(resultSet.getString("img"));
                    comic.setGenres(new ArrayList<>());
                    comics.put(comicId, comic);
                }
                Genre genre = new Genre();
                genre.setName(resultSet.getString("name"));
                comic.getGenres().add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(comics.values());
    }

    public Comic getComicByTitle(String title) {
        Comic comic = null;
        try {
            String sql = "SELECT c.*, g.* FROM Comic c " +
                    "JOIN ComicGenre cg ON c.id = cg.comicId " +
                    "JOIN Genre g ON cg.genreId = g.id where c.deleted = 0 and g.deleted = 0 and c.title = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (comic == null) {
                    comic = new Comic();
                    comic.setId(resultSet.getInt("id"));
                    comic.setTitle(resultSet.getString("title"));
                    comic.setDescription(resultSet.getString("description"));
                    comic.setImg(resultSet.getString("img"));
                    comic.setCreatedDate(resultSet.getTimestamp("createdDate"));
                    comic.setUpdatedDate(resultSet.getTimestamp("updatedDate"));
                    comic.setGenres(new ArrayList<>());
                }
                Genre genre = new Genre();
                genre.setName(resultSet.getString("name"));
                comic.getGenres().add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comic;
    }
}
