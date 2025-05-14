package org.example;

import com.google.gson.JsonObject;

import java.sql.Array;

public class New {
    String id;
    String name;
    String author;
    String title;
    String description;
    String url;
    String urlToImage;
    String publishedAt;
    String content;

    public New(String title, JsonObject source, String id, String name, String author, String description, String url, String urlToImage, String publishedAt, String content) {
        this.title = title;
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public String getContent() {return content;}

    public String getAuthor() {return author;}

    public String getTitle() {return title;}

    public String getDescription() {return description;}

    public String getUrl() {return url;}

    public String getPublishedAt() {return publishedAt;}

    public String getUrlToImage() {return urlToImage;}

    public String getId() {return id;}

    public String getName() {return name;}
}
