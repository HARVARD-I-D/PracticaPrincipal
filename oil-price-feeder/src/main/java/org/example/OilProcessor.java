package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;

public class OilProcessor {
    ArrayList<OilPrice> oilPrices_Brent;
    ArrayList<OilPrice> oilPrices_WTI;

    public void OilProcessor(JsonArray data, OilType type){
        for(int i = 0; i< data.size(); i++){
            JsonObject entry = data.get(i).getAsJsonObject();
            String date = entry.has("date") ? entry.get("date").getAsString() : "Unknown";
            Double value = entry.has("value") ? entry.get("value").getAsDouble() : null;
            if(type == OilType.Brent){
                oilPrices_Brent.add(new OilPrice(date, value));
            }
            else if (type == OilType.WTI) {
                oilPrices_WTI.add(new OilPrice(date, value));
            }
        }
    }
}
