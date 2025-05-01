package org.example;

public class NewsAPIProvider implements NewProvider {
    @Override
    public New provide(String arg) {
        arg = "c252391085974338b92b1dcfb4d895bc";
        String url1 = "https://newsapi.org/v2/everything?q=oil&from=2025-03-29&to=2025-03-29&sortBy=popularity&apiKey=" + arg;
        NewExtractor New = new NewExtractor();
        New.Extractor(url1);

        return null;
    }
}
