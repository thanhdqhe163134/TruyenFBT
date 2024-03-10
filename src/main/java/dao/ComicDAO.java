package dao;

import model.Chapter;
import model.Comic;
import model.Genre;
import util.DBConnect;

import java.sql.*;
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
                    comic.setViews(resultSet.getInt("view"));
                    comic.setImg(resultSet.getString("img"));
                    comic.setGenres(new ArrayList<>());
                    comics.put(comicId, comic);
                }
                Genre genre = new Genre();
                genre.setName(resultSet.getString("name"));
                comic.getGenres().add(genre);
                List<Chapter> chapters = new ArrayList<>();
                Chapter chapter = new Chapter();
                String sql2 = "SELECT * FROM Chapter WHERE comicId = ? AND number = (SELECT MAX(number) FROM Chapter WHERE comicId = ? AND deleted = 0)";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, comicId);
                preparedStatement2.setInt(2, comicId);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while (resultSet2.next()) {
                    chapter = new Chapter();
                    chapter.setId(resultSet2.getInt("id"));
                    chapter.setComicId(resultSet2.getInt("comicId"));
                    chapter.setNumber(resultSet2.getInt("number"));
                    chapter.setCreatedDate(resultSet2.getTimestamp("createdDate"));
                    chapter.setUpdatedDate(resultSet2.getTimestamp("updatedDate"));
                    chapter.setDeleted(resultSet2.getBoolean("deleted"));
                    chapters.add(chapter);
                }
                comic.setChapters(chapters);

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
                    comic.setViews(resultSet.getInt("view"));
                    comic.setGenres(new ArrayList<>());
                    comics.put(comicId, comic);
                }
                Genre genre = new Genre();
                genre.setName(resultSet.getString("name"));
                comic.getGenres().add(genre);
                List<Chapter> chapters = new ArrayList<>();
                Chapter chapter = new Chapter();
                String sql2 = "SELECT * FROM Chapter WHERE comicId = ? AND number = (SELECT MAX(number) FROM Chapter WHERE comicId = ? AND deleted = 0)";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, comicId);
                preparedStatement2.setInt(2, comicId);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while (resultSet2.next()) {
                    chapter = new Chapter();
                    chapter.setId(resultSet2.getInt("id"));
                    chapter.setComicId(resultSet2.getInt("comicId"));
                    chapter.setNumber(resultSet2.getInt("number"));
                    chapter.setCreatedDate(resultSet2.getTimestamp("createdDate"));
                    chapter.setUpdatedDate(resultSet2.getTimestamp("updatedDate"));
                    chapter.setDeleted(resultSet2.getBoolean("deleted"));
                    chapters.add(chapter);
                }
                comic.setChapters(chapters);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(comics.values());
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
                    comic.setViews(resultSet.getInt("view"));
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
                    comic.setViews(resultSet.getInt("view"));
                    comic.setCreatedDate(resultSet.getTimestamp("createdDate"));
                    comic.setUpdatedDate(resultSet.getTimestamp("updatedDate"));
                    comic.setGenres(new ArrayList<>());
                }
                Genre genre = new Genre();
                String name = resultSet.getString("name");
                if(name != null) {
                    genre.setName(name);
                    comic.getGenres().add(genre);
                }
                Chapter chapter = new Chapter();
                String sql2 = "SELECT * FROM Chapter WHERE comicId = ? AND deleted = 0 ORDER BY number DESC";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, comic.getId());
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                List<Chapter> chapters = new ArrayList<>();
                while (resultSet2.next()) {
                    chapter = new Chapter();
                    chapter.setId(resultSet2.getInt("id"));
                    chapter.setComicId(resultSet2.getInt("comicId"));
                    chapter.setNumber(resultSet2.getInt("number"));
                    chapter.setCreatedDate(resultSet2.getTimestamp("createdDate"));
                    chapter.setUpdatedDate(resultSet2.getTimestamp("updatedDate"));
                    chapter.setDeleted(resultSet2.getBoolean("deleted"));
                    chapters.add(chapter);
                }
                comic.setChapters(chapters);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comic;
    }

    public String getComicTitleByTile(String title) {
        if(connection != null) {
            try {
                String sql = "SELECT title FROM Comic WHERE title = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, title);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    return resultSet.getString("title");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public String create(String title, String description, String relativePath, String[] toArray) {
        try {
            String originalTitle = title;
            int count = 0;
            while (getComicTitleByTile(title) != null) {
                count++;
                title = originalTitle + " (" + count + ")";
            }
            String sql = "INSERT INTO Comic (title, description, img, createdDate, updatedDate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, relativePath);
            preparedStatement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int comicId = generatedKeys.getInt(1);
                for (String genreId : toArray) {
                    String sql2 = "INSERT INTO ComicGenre (comicId, genreId) VALUES (?, ?)";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setInt(1, comicId);
                    preparedStatement2.setInt(2, Integer.parseInt(genreId));
                    preparedStatement2.executeUpdate();
                }
                String sql3 = "Insert into Chapter (comicId, number, createdDate, updatedDate) VALUES (?, 1, ?, ?)";
                PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                preparedStatement3.setInt(1, comicId);
                preparedStatement3.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement3.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement3.executeUpdate();
                return title;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String update(int id, String title, String description, String relativePath, String[] toArray) {
        try {
            String originalTitle = title;
            int count = 0;
            while (getComicTitleByTile(title) != null && !getComicTitleByTile(title).equals(getComicByTitle(title).getTitle())) {
                count++;
                title = originalTitle + " (" + count + ")";
            }
            if(!relativePath.equals("")) {
                String sql = "UPDATE Comic SET title = ?, description = ?, img = ?, updatedDate = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, relativePath);
                preparedStatement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(5, id);
                preparedStatement.executeUpdate();
                if (preparedStatement.getUpdateCount() > 0) {
                    int comicId = getComicByTitle(title).getId();
                    String sql2 = "DELETE FROM ComicGenre WHERE comicId = ?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setInt(1, comicId);
                    preparedStatement2.executeUpdate();
                    for (String genreId : toArray) {
                        String sql3 = "INSERT INTO ComicGenre (comicId, genreId) VALUES (?, ?)";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                        preparedStatement3.setInt(1, comicId);
                        preparedStatement3.setInt(2, Integer.parseInt(genreId));
                        preparedStatement3.executeUpdate();
                    }
                    return title;
                }
            } else {
                String sql = "UPDATE Comic SET title = ?, description = ?, updatedDate = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();
                if (preparedStatement.getUpdateCount() > 0) {
                    int comicId = getComicByTitle(title).getId();
                    String sql2 = "DELETE FROM ComicGenre WHERE comicId = ?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setInt(1, comicId);
                    preparedStatement2.executeUpdate();
                    for (String genreId : toArray) {
                        String sql3 = "INSERT INTO ComicGenre (comicId, genreId) VALUES (?, ?)";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                        preparedStatement3.setInt(1, comicId);
                        preparedStatement3.setInt(2, Integer.parseInt(genreId));
                        preparedStatement3.executeUpdate();
                    }
                    return title;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {
        try {
            String sql = "UPDATE Comic SET deleted = 1 WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Comic> getAllSortedByViews() {
        Map<Integer, Comic> comics = new LinkedHashMap<>();
        try {
            String sql = "SELECT c.*, g.* FROM Comic c " +
                    "JOIN ComicGenre cg ON c.id = cg.comicId " +
                    "JOIN Genre g ON cg.genreId = g.id where c.deleted = 0 and g.deleted = 0 " +
                    "ORDER BY c.[view] DESC ";
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
                    comic.setViews(resultSet.getInt("view"));
                    comic.setGenres(new ArrayList<>());
                    comics.put(comicId, comic);
                }
                Genre genre = new Genre();
                genre.setName(resultSet.getString("name"));
                comic.getGenres().add(genre);
                List<Chapter> chapters = new ArrayList<>();
                Chapter chapter = new Chapter();
                String sql2 = "SELECT * FROM Chapter WHERE comicId = ? AND number = (SELECT MAX(number) FROM Chapter WHERE comicId = ? AND deleted = 0)";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, comicId);
                preparedStatement2.setInt(2, comicId);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while (resultSet2.next()) {
                    chapter = new Chapter();
                    chapter.setId(resultSet2.getInt("id"));
                    chapter.setComicId(resultSet2.getInt("comicId"));
                    chapter.setNumber(resultSet2.getInt("number"));
                    chapter.setCreatedDate(resultSet2.getTimestamp("createdDate"));
                    chapter.setUpdatedDate(resultSet2.getTimestamp("updatedDate"));
                    chapter.setDeleted(resultSet2.getBoolean("deleted"));
                    chapters.add(chapter);
                }
                comic.setChapters(chapters);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(comics.values());
    }

    public void updateViewCount(int id) {
        try {
            String sql = "UPDATE Comic SET [view] = [view] + 1 where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Comic> getComicHistory(int id) {
        Map<Integer, Comic> comics = new LinkedHashMap<>();
        try {
            String sql = "SELECT c.*, g.*, ch.number, ch.updatedDate as updatedDateC FROM Comic c\n" +
                    "JOIN ComicGenre cg ON c.id = cg.comicId\n" +
                    "JOIN Genre g ON cg.genreId = g.id\n" +
                    "JOIN Chapter ch ON ch.comicId = c.id\n" +
                    "JOIN ReadingProgress rp ON rp.chapterId = (SELECT TOP 1 ch.id from Chapter ch WHERE ch.comicId = c.id AND ch.id = rp.chapterId)\n" +
                    "WHERE rp.accountId = ? and c.deleted = 0 and g.deleted = 0 and ch.id = rp.chapterId ORDER BY rp.updatedDate DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Comic comic = null;
            while (resultSet.next()) {
                int comicId = resultSet.getInt("id");
                comic = comics.get(comicId);
                if (comic == null) {
                    comic = new Comic();
                    comic.setId(comicId);
                    comic.setTitle(resultSet.getString("title"));
                    comic.setDescription(resultSet.getString("description"));
                    comic.setImg(resultSet.getString("img"));
                    comic.setViews(resultSet.getInt("view"));
                    comic.setUpdatedDate(resultSet.getTimestamp("updatedDate"));
                    comic.setGenres(new ArrayList<>());
                    comics.put(comicId, comic);
                }
                Genre genre = new Genre();
                genre.setName(resultSet.getString("name"));
                comic.getGenres().add(genre);
                List<Chapter> chapters = new ArrayList<>();
                Chapter chapter = new Chapter();
                chapter.setNumber(resultSet.getInt("number"));
                chapter.setUpdatedDate(resultSet.getTimestamp("updatedDateC"));

                chapters.add(chapter);
                comic.setChapters(chapters);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(comics.values());
    }

}
