package model;


import java.sql.Timestamp;

public class Image {
    private int id;
    private String url;
    private int chapterId;
    private int order;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private boolean deleted;

    public Image() {
    }

    public Image(int id, String url, int chapterId, int order, Timestamp createdDate, Timestamp updatedDate, boolean deleted) {
        this.id = id;
        this.url = url;
        this.chapterId = chapterId;
        this.order = order;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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