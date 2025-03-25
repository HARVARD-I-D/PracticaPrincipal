package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class WTIExtractor {
    public void Extractor(String url){
        try {
            Connection.Response response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();

            String jsonResponse = response.body();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

            System.out.println(gson.toJson(jsonObject));

            if (jsonObject.has("data")) {
                System.out.println("Datos del WTI: " + jsonObject.get("data").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
