package model;


import java.sql.Timestamp;

public class ReadingProgress {
    private int id;
    private int accountId;
    private int chapterId;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private boolean deleted;

    public ReadingProgress() {
    }

    public ReadingProgress(int id, int accountId, int chapterId, Timestamp createdDate, Timestamp updatedDate, boolean deleted) {
        this.id = id;
        this.accountId = accountId;
        this.chapterId = chapterId;
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
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