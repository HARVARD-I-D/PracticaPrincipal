package oilFeeder.infrastructure.adapters.api;

import com.google.gson.*;
import oilFeeder.application.domain.model.OilType;
import oilFeeder.application.domain.model.OilPrice;

import java.time.Instant;
import java.util.ArrayList;

public class OilProcessor {
    ArrayList<OilPrice> oilPrices;

    public void Processor(JsonArray data, OilType type){
        this.oilPrices = new ArrayList<>();

        for(int i = 0; i< data.size(); i++){
            JsonObject entry = data.get(i).getAsJsonObject();
            String dateStr = entry.has("date") ? entry.get("date").getAsString() : null;
            Instant date = dateStr != null ? Instant.parse(dateStr + "T00:00:00Z") : null;
            Double value = Double.NaN;
            if (entry.has("value") && !entry.get("value").getAsString().equals("."))
                value = entry.get("value").getAsDouble();

            if(type == OilType.Brent){
                oilPrices.add(new OilPrice(date,"AlphaVantage", value, "Brent"));
            }
            else if (type == OilType.WTI) {
                oilPrices.add(new OilPrice(date, "AlphaVantage", value, "WTI"));
            }
        }
    }

    public ArrayList<OilPrice> getOilPrices() {
        return oilPrices;
    }

}
