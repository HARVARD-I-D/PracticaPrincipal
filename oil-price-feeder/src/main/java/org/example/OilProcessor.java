package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.time.Instant;

public class OilProcessor {
    ArrayList<OilPrice> oilPrices;

    public void Processor(JsonArray data, OilType type){
        this.oilPrices = new ArrayList<>();

        for(int i = 0; i< data.size(); i++){
            JsonObject entry = data.get(i).getAsJsonObject();
            String dateStr = entry.has("date") ? entry.get("date").getAsString() : null;
            Instant date = dateStr != null ? Instant.parse(dateStr + "T00:00:00Z") : null;
            Double value = entry.has("value") ? entry.get("value").getAsDouble() : null;

            if(type == OilType.Brent){
                oilPrices.add(new OilPrice(date, value, "Brent"));
            }
            else if (type == OilType.WTI) {
                oilPrices.add(new OilPrice(date, value, "WTI"));
            }
        }
    }

    public ArrayList<OilPrice> getOilPrices() {
        return oilPrices;
    }

}
