package org.example;

public class AlphadvantageProvider implements OilProvider{
    @Override
    public OilPrice provide() {
        String apiKey_Crude = "EIURB4K339TBW8NQ";
        String url1 = "https://www.alphavantage.co/query?function=WTI&interval=daily&apikey=" + apiKey_Crude;

        WTIExtractor WTI = new WTIExtractor();
        WTI.Extractor(url1);

        String url2 = "https://www.alphavantage.co/query?function=BRENT&interval=daily&apikey=" + apiKey_Crude;
        BrentExtractor BRENT = new BrentExtractor();
        BRENT.Extractor(url2);

        return null;
    }
}
