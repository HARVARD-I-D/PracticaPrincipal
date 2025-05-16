package subscriberHexagonal.domain.port.out;

public interface EventStore {
    void storeOil(String oilPriceRaw);
    void storeNews(String newsRaw);
}
