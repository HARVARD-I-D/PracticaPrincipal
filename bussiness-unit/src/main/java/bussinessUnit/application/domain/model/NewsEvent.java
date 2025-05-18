package bussinessUnit.application.domain.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class NewsEvent {
    private final JsonObject source;
    private final String id;
    private final String name;
    private final String author;
    private final String title;
    private final String description;
    private final String url;
    private final String urlToImage;
    private final String publishedAt;
    private final String content;

    public NewsEvent(String title, JsonObject source, String id, String name, String author, String description, String url, String urlToImage, String publishedAt, String content) {
        this.title = title;
        this.source = source;
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public JsonObject getSource() {return source;}

    public String getSourceAsString() {
        String sourceStr = "unknown";
        if (!getSource().isJsonNull()) {
            sourceStr = getSource().toString();
        }
        return sourceStr;
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
