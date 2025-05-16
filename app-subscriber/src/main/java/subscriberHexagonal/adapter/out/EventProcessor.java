package subscriberHexagonal.adapter.out;

import subscriberHexagonal.domain.model.OilEvent;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class EventProcessor {


    public void OilProccessor(String oilRawMessage) {
        Map<String, String> fields = new HashMap<>();

        String[] pairs = oilRawMessage.split(",");
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
    }
}
