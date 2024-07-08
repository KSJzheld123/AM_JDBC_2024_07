package org.koreait;

public class ArticleList {

    int id;
    String title;
    String body;

    ArticleList(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
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
}
