package org.example;

public class AlphaVantageProvider implements OilProvider{
    @Override
    public OilPrice provide() {
        String apiKey_Crude = "EIURB4K339TBW8NQ";

        String url1 = "https://www.alphavantage.co/query?function=WTI&interval=daily&apikey=" + apiKey_Crude;
        OilExtractor WTI = new OilExtractor();
        WTI.Extractor(url1, OilType.WTI);

        String url2 = "https://www.alphavantage.co/query?function=BRENT&interval=daily&apikey=" + apiKey_Crude;
        OilExtractor BRENT = new OilExtractor();
        BRENT.Extractor(url2, OilType.Brent);

        return null;
    }
}