package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.util.ArrayList;

public class AlphaVantageProvider implements OilProvider {
    private final String wtiURl;
    private final String brentUrl;

    public AlphaVantageProvider(String apiKey) {
        wtiURl = "https://www.alphavantage.co/query?function=WTI&interval=daily&apikey=" + apiKey;
        brentUrl = "https://www.alphavantage.co/query?function=BRENT&interval=daily&apikey=" + apiKey;
    }

    @Override
    public ArrayList<OilPrice> provide() {
        ArrayList<OilPrice> prices = new ArrayList<>();
        try {
            Connection.Response response = Jsoup.connect(wtiURl)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();

            String jsonResponse = response.body();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            if (jsonObject.has("data")) {
                JsonArray data = jsonObject.get("data").getAsJsonArray();
                OilProcessor processor = new OilProcessor();
                processor.Processor(data, OilType.WTI);
                prices.addAll(processor.getOilPrices());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection.Response response = Jsoup.connect(brentUrl)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();

            String jsonResponse = response.body();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            if (jsonObject.has("data")) {
                JsonArray data = jsonObject.get("data").getAsJsonArray();
                OilProcessor processor = new OilProcessor();
                processor.Processor(data, OilType.Brent);
                prices.addAll(processor.getOilPrices());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prices;
    }
}
