package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.Instant;
import java.util.ArrayList;

public class NewProcessor {
    ArrayList<New> news;

    public void Processor(JsonArray data){
        this.news = new ArrayList<>();



        for (int i = 0; i < data.size(); i++){
            JsonObject entry = data.get(i).getAsJsonObject();
            String date =  entry.has("publishedAt") ? entry.get("publishedAt").getAsString() : null;
            String author =  entry.has("author") ? entry.get("author").getAsString() : null;
            String title =  entry.has("title") ? entry.get("title").getAsString() : null;
            String description =  entry.has("description") ? entry.get("description").getAsString() : null;
            String url =  entry.has("url") ? entry.get("url").getAsString() : null;
            String urlToImage =  entry.has("urlToImage") ? entry.get("urlToImage").getAsString() : null;
            String content =  entry.has("content") ? entry.get("content").getAsString() : null;
            JsonArray source = entry.has("source") ? entry.get("source").getAsJsonArray() : null;
            String id = source[0].getAsString();
            String name = source[1].getAsString();
        }
    }

    public ArrayList<New> getNews(){ return news; }
}
