package model;

import java.sql.Timestamp;
import java.util.List;

public class Comment {
    private int id;
    private int accountId;
    private int chapterId;
    private String content;
    private Integer parentId;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private boolean deleted;

    private String username;

    private String img;


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

    public Comment(int id, int accountId, int chapterId, String content, Integer parentId, Timestamp createdDate, Timestamp updatedDate, boolean deleted, String username, String img, List<Comment> children) {
        this.id = id;
        this.accountId = accountId;
        this.chapterId = chapterId;
        this.content = content;
        this.parentId = parentId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deleted = deleted;
        this.username = username;
        this.img = img;
        this.children = children;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
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
