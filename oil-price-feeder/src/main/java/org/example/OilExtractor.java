package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class OilExtractor {
    public void Extractor(String url, OilType type){
        try {
            Connection.Response response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();

            String jsonResponse = response.body();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

            if (jsonObject.has("data")) {
                JsonArray data = jsonObject.get("data").getAsJsonArray();
                OilProcessor processor = new OilProcessor();
                processor.Processor(data, type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
