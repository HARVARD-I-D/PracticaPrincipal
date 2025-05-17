package newsFeeder.infrastructure.adapters.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import newsFeeder.application.domain.New;

import java.util.ArrayList;

public class NewProcessor {
    ArrayList<New> news;

    public void Processor(JsonArray articles) {
        this.news = new ArrayList<>();


        for (int i = 0; i < articles.size() - 1; i++) {
            JsonObject entry = articles.get(i).getAsJsonObject();
            String date = "unknown";
            if (!entry.get("publishedAt").isJsonNull()){
                if(entry.get("publishedAt") != null){
                    date = entry.get("publishedAt").getAsString();
                }
            }
            String author = "unknown";
            if (!entry.get("author").isJsonNull()){
                if(entry.get("author") != null){
                    author = entry.get("author").getAsString();
                }
            }
            String title = "none";
            if (!entry.get("title").isJsonNull()){
                if(entry.get("title") != null){
                    title = entry.get("title").getAsString();
                }
            }

            String description = "none";
            if (entry.has("description")){
                if(!entry.get("description").isJsonNull()){
                    description = entry.get("description").getAsString();
                }
            }
            String url = "unknown";
            if (!entry.get("url").isJsonNull()){
                if(entry.get("url") != null){
                    url = entry.get("url").getAsString();
                }
            }
            String urlToImage = "none";
            if (entry.has("urlToImage")){
                if(!entry.get("urlToImage").isJsonNull()){
                    urlToImage = entry.get("urlToImage").getAsString();
                }
            }
            String content = "none";
            if (!entry.get("content").isJsonNull()){
                if(entry.get("content") != null){
                    content = entry.get("content").getAsString();
                }
            }
            JsonObject source = entry.has("source") ? entry.get("source").getAsJsonObject() : null;
            assert source != null;
            String idsource = "unknown";
            if (source.has("id")){
                if(!source.get("id").isJsonNull()) {
                    idsource = source.get("id").getAsString();
                }
            }
            String name = "unknown";
            if (source.has("name")){
                if(!source.get("name").isJsonNull()) {
                    idsource = source.get("name").getAsString();
                }
            }
            news.add(new New(title, source, idsource, name, author, description, url, urlToImage, date, content));
        }
    }

    public ArrayList<New> getNews() { return news; }
}
