package org.example;

public class AlphaVantageProvider implements OilProvider{
    @Override
    public OilPrice provide(String arg) {

        String url1 = "https://www.alphavantage.co/query?function=WTI&interval=daily&apikey=" + arg;
        OilExtractor WTI = new OilExtractor();
        WTI.Extractor(url1, OilType.WTI);

        String url2 = "https://www.alphavantage.co/query?function=BRENT&interval=daily&apikey=" + arg;
        OilExtractor BRENT = new OilExtractor();
        BRENT.Extractor(url2, OilType.Brent);

        return null;
    }
}