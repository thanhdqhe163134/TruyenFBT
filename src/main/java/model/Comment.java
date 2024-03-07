package model;

import java.sql.Timestamp;
import java.util.List;

public class Comment {
    private int id;
    private int accountId;
    private int chapterId;
    private String content;
    private int parentId;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private boolean deleted;

    public Comment() {
    }

    private List<Comment> children;

    public Comment(int id, int accountId, int chapterId, String content, int parentId, Timestamp createdDate, Timestamp updatedDate, boolean deleted, List<Comment> children) {
        this.id = id;
        this.accountId = accountId;
        this.chapterId = chapterId;
        this.content = content;
        this.parentId = parentId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
        this.children = children;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }
}
