package org.koreait;

public class ArticleList {

    int id;
    String title;
    String body;


    String regDate;
    String updateDate;

    ArticleList(int id, String title, String body, String regDate, String updateDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.regDate = regDate;
        this.updateDate = updateDate;
    }

    private int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    private String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private String getBody() {
        return body;
    }

    private void setBody(String body) {
        this.body = body;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
