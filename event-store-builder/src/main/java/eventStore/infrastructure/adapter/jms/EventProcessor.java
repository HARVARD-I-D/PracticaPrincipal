package eventStore.infrastructure.adapter.jms;
import eventStore.application.domain.model.OilEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EventProcessor {
    ArrayList<OilEvent> oilEvents;

    public void OilProccessor(String oilRawMessage) {
        this.oilEvents = new ArrayList<>();
        Map<String, String> fields = new HashMap<>();

        String[] pairs = oilRawMessage.split(",");
        for(String pair : pairs){
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2){
                fields.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        try {
            Instant date = Instant.parse(fields.get("Date") + "T00:00:00Z");
            double value = Double.parseDouble(fields.get("Value"));
            String type = fields.get("Type");
            String source = fields.get("Source");

            OilEvent event = new OilEvent(date, value, type, source);
            oilEvents.add(event);
        } catch (Exception e){
            System.out.println("Error al parsear el mensaje: " + oilRawMessage);
            e.printStackTrace();
        }
    }

    public ArrayList<OilEvent> getParsedOilEvents() {return oilEvents;}
}
