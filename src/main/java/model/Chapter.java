package model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Chapter {
    private int id;
    private int comicId;
    private int number;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private boolean deleted;

    private List<Image> images;

    private List<Comment> comments;

    public Chapter() {
    }

    public Chapter(int id, int comicId, int number, Timestamp createdDate, Timestamp updatedDate, boolean deleted) {
        this.id = id;
        this.comicId = comicId;
        this.number = number;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
    }

    public Chapter(int id, int comicId, int number, Timestamp createdDate, Timestamp updatedDate, boolean deleted, List<Image> images) {
        this.id = id;
        this.comicId = comicId;
        this.number = number;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
        this.images = images;
    }

    public Chapter(int id, int comicId, int number, Timestamp createdDate, Timestamp updatedDate, boolean deleted, List<Image> images, List<Comment> comments) {
        this.id = id;
        this.comicId = comicId;
        this.number = number;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
        this.images = images;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
