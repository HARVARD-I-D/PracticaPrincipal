package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class NewProcessor {
    ArrayList<New> news;

    public void Processor(JsonArray articles){
        this.news = new ArrayList<>();


        try{
            for (int i = 0; i < articles.size()-1; i++){
                JsonObject entry = articles.get(i).getAsJsonObject();
                String date =  entry.has("publishedAt") ? entry.get("publishedAt").getAsString() : null;
                String author =  entry.has("author") ? entry.get("author").getAsString() : null;
                String title =  entry.has("title") ? entry.get("title").getAsString() : null;
                String description =  entry.has("description") ? entry.get("description").getAsString() : null;
                String url =  entry.has("url") ? entry.get("url").getAsString() : null;
                String urlToImage =  entry.has("urlToImage") ? entry.get("urlToImage").getAsString() : null;
                String content =  entry.has("content") ? entry.get("content").getAsString() : null;
                JsonObject source = entry.has("source") ? entry.get("source").getAsJsonObject() : null;
                assert source != null;
                String id = source.has("id") ? source.get("id").getAsString() : null;
                if (id == null){
                    id = "unknown";
                }
                String name = source.has("name") ? source.get("name").getAsString() : null;
                news.add(new New(title, source, id, name, author, description, url, urlToImage, date,content));
            }
        }catch(Exception ignored) {}
    }

    public ArrayList<New> getNews(){ return news; }
}
