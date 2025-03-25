package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;


public class Main {
    public static void main(String[] args) {
        String apiKey = "EIURB4K339TBW8NQ";
        String url = "https://www.alphavantage.co/query?function=WTI&interval=daily&apikey=" + apiKey;

        WTIExtractor WTI = new WTIExtractor();
        WTI.Extractor(url);
    }
}