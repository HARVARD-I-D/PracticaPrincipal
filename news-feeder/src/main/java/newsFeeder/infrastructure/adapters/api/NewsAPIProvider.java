package newsFeeder.infrastructure.adapters.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import newsFeeder.application.domain.New;
import newsFeeder.infrastructure.port.NewProvider;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.util.ArrayList;

public class NewsAPIProvider implements NewProvider {
    private final String url;


    public NewsAPIProvider(String apiKey) {
        url = "https://newsapi.org/v2/everything?q=oil&sortBy=popularity&apiKey=" + apiKey;
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
            if(jsonObject.has("articles")){
                JsonArray articles = jsonObject.get("articles").getAsJsonArray();
                NewProcessor newProcessor = new NewProcessor();
                newProcessor.Processor(articles);
                news.addAll(newProcessor.getNews());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return news;
    }
}
