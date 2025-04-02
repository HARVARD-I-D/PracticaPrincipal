package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;

public class OilProcessor {
    ArrayList<OilPrice> oilPrices_Brent;
    ArrayList<OilPrice> oilPrices_WTI;

    public void Processor(JsonArray data, OilType type){
        this.oilPrices_Brent = new ArrayList<>();
        this.oilPrices_WTI = new ArrayList<>();

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
        this.getOilPrices_Brent();
        this.getOilPrices_WTI();
    }

    public ArrayList<OilPrice> getOilPrices_Brent() {
        System.out.println("BRENT OIL PRICES: ");
        for(OilPrice oilPrice : oilPrices_Brent){
            System.out.println("Date: " + oilPrice.getDate() + ", Value: " + oilPrice.getValue());
        }
        return oilPrices_Brent;
    }

    public ArrayList<OilPrice> getOilPrices_WTI() {
        System.out.println("WTI OIL PRICES: ");
        for(OilPrice oilPrice : oilPrices_WTI){
            System.out.println("Date: " + oilPrice.getDate() + ", Value: " + oilPrice.getValue());
        }
        return oilPrices_WTI;
    }
}
