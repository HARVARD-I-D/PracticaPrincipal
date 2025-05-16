package subscriberHexagonal.domain.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class OilEventParser {
    public static OilEvent parse(String rawMessage){
        Map<String, String> fields = new HashMap<>();

        String[] pairs = rawMessage.split(",");
        for(String pair : pairs){
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2){
                fields.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        Instant date = Instant.parse(fields.get("Date") + "T00:00:00Z");
        double value = Double.parseDouble(fields.get("Value"));
        String type = fields.get("Type");
        String source = fields.get("Source");

        return new OilEvent(date, value, type, source);
    }
}
