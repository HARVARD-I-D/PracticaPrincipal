package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.util.ArrayList;

public class NewsAPIProvider implements NewProvider {
    private final String url;



    public NewsAPIProvider(String apiKey) {
        String apikey = "c252391085974338b92b1dcfb4d895bc";
        url = "https://newsapi.org/v2/everything?q=oil&sortBy=popularity&apiKey=" + apikey;
    }

    @Override
    public ArrayList<New> provide() {
        ArrayList<New> news = new ArrayList<>();

        try {
            Connection.Response response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();

            String jsonResponse = response.body();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            if(jsonObject.has("data")){
                JsonArray data = jsonObject.get("data").getAsJsonArray();
                NewProcessor newProcessor = new NewProcessor();
                newProcessor.Processor(data);
                news.addAll(newProcessor.getNews());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return news;
    }
}
