package dao;

import model.Chapter;
import model.Comment;
import model.Image;
import util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapterDAO {
    Connection connection = new DBConnect().connection;

    public Chapter getChapterByNumber(int chapterNumber, int comicId) {
        Chapter chapter = null;
        try {
            String sql = "SELECT * FROM Chapter WHERE number = ? AND comicId = ? AND deleted = 0";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, chapterNumber);
            preparedStatement.setInt(2, comicId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                chapter = new Chapter();
                chapter.setId(resultSet.getInt("id"));
                chapter.setComicId(resultSet.getInt("comicId"));
                chapter.setNumber(resultSet.getInt("number"));
                chapter.setCreatedDate(resultSet.getTimestamp("createdDate"));
                chapter.setUpdatedDate(resultSet.getTimestamp("updatedDate"));
                chapter.setDeleted(resultSet.getBoolean("deleted"));

                List<Image> images = new ArrayList<>();
                String sql2 = "SELECT * FROM Image WHERE chapterId = ? AND deleted = 0 ORDER BY number ASC";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, chapter.getId());
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while (resultSet2.next()) {
                    Image image = new Image();
                    image.setId(resultSet2.getInt("id"));
                    image.setUrl(resultSet2.getString("url"));
                    image.setChapterId(resultSet2.getInt("chapterId"));
                    image.setOrder(resultSet2.getInt("number"));
                    images.add(image);
                }
                chapter.setImages(images);

                List<Comment> comments = new ArrayList<>();
                String sql3 = "SELECT Comment.*, Account.username, Account.img FROM Comment JOIN Account ON Comment.accountId = Account.id WHERE chapterId = ?";
                PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                preparedStatement3.setInt(1, chapter.getId());
                ResultSet resultSet3 = preparedStatement3.executeQuery();
                while (resultSet3.next()) {
                    Comment comment = new Comment();
                    comment.setId(resultSet3.getInt("id"));
                    comment.setChapterId(resultSet3.getInt("chapterId"));
                    comment.setAccountId(resultSet3.getInt("accountId"));
                    comment.setContent(resultSet3.getString("content"));
                    comment.setCreatedDate(resultSet3.getTimestamp("createdDate"));
                    comment.setUpdatedDate(resultSet3.getTimestamp("updatedDate"));
                    comment.setDeleted(resultSet3.getBoolean("deleted"));
                    comment.setUsername(resultSet3.getString("username"));
                    comment.setImg(resultSet3.getString("img"));
                    comment.setParentId(resultSet3.getObject("parentId", Integer.class));
                    comments.add(comment);
                }

                // Create a list to hold root comments
                List<Comment> rootComments = new ArrayList<>();
                // Iterate over the comments again
                for (Comment comment : comments) {
                    // If the comment has a parentId, find the parent comment and add this comment to its children
                    if (comment.getParentId() != null) {
                        for (Comment potentialParent : comments) {
                            if (potentialParent.getId() == comment.getParentId()) {
                                if (potentialParent.getChildren() == null) {
                                    potentialParent.setChildren(new ArrayList<>());
                                }
                                potentialParent.getChildren().add(comment);
                                break;
                            }
                        }
                    } else {
                        // If the comment doesn't have a parentId, it's a root comment
                        rootComments.add(comment);
                    }
                }
                // Set the comments of the chapter to the root comments
                chapter.setComments(rootComments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chapter;
    }

    public List<Chapter> getAllChaptersByComicId(int parseInt) {
        List<Chapter> chapters = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Chapter WHERE comicId = ? AND deleted = 0 ORDER BY number desc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, parseInt);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Chapter chapter = new Chapter();
                chapter.setId(resultSet.getInt("id"));
                chapter.setComicId(resultSet.getInt("comicId"));
                chapter.setNumber(resultSet.getInt("number"));
                chapter.setCreatedDate(resultSet.getTimestamp("createdDate"));
                chapter.setUpdatedDate(resultSet.getTimestamp("updatedDate"));
                chapter.setDeleted(resultSet.getBoolean("deleted"));
                chapters.add(chapter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chapters;
    }

    public Chapter addChapter(int parseInt, int parseInt1) {
        Chapter chapter = null;
        try {
            String sql = "INSERT INTO Chapter (comicId, number, createdDate, updatedDate) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, parseInt1);
            preparedStatement.setInt(2, parseInt);
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                chapter = getChapterByNumber(parseInt, parseInt1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chapter;
    }
}
