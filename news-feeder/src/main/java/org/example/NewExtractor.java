package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class NewExtractor {
    public void Extractor(String url){
        try {
            Connection.Response response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();

            String jsonResponse = response.body();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

            if (jsonObject.has("data")) {
                System.out.println("Datos de las noticias: " + jsonObject.get("data").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

