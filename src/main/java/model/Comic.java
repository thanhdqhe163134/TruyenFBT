package model;

import java.sql.Timestamp;
import java.util.List;

public class Comic {
    private int id;
    private String title;
    private String description;

    private String img;

    private int views;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private boolean deleted;

    private List<Genre> genres;

    private List<Chapter> chapters;


    public Comic() {
    }

    public Comic(int id, String title, String description, Timestamp createdDate, Timestamp updatedDate, boolean deleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
    }

    public Comic(int id, String title, String description, Timestamp createdDate, Timestamp updatedDate, boolean deleted, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
        this.genres = genres;
    }

    public Comic(int id, String title, String description, Timestamp createdDate, Timestamp updatedDate, boolean deleted, List<Genre> genres, List<Chapter> chapters) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
        this.genres = genres;
        this.chapters = chapters;
    }

    public Comic(int id, String title, String description, String img, Timestamp createdDate, Timestamp updatedDate, boolean deleted, List<Genre> genres, List<Chapter> chapters) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.img = img;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
        this.genres = genres;
        this.chapters = chapters;
    }

    public Comic(int id, String title, String description, String img, int views, Timestamp createdDate, Timestamp updatedDate, boolean deleted, List<Genre> genres, List<Chapter> chapters) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.img = img;
        this.views = views;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
        this.genres = genres;
        this.chapters = chapters;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}