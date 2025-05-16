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
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].trim().toLowerCase();
                String value = keyValue[1].trim();
                fields.put(key, value);
            }
        }

        System.out.println("DEBUG - Mensaje recibido: " + oilRawMessage);

        try {
            String dateStr = fields.get("date");
            String valueStr = fields.get("value");
            String type = fields.getOrDefault("type", "Unknown");
            String source = fields.getOrDefault("source", "Unknown");

            if (dateStr == null || valueStr == null) {
                System.err.println("ERROR: Falta campo obligatorio (date o value). Mensaje: " + oilRawMessage);
                return;
            }

            Instant date = Instant.parse(dateStr);
            double value = Double.parseDouble(valueStr);

            OilEvent event = new OilEvent(date, value, type, source);
            oilEvents.add(event);
        } catch (Exception e) {
            System.out.println("Error al parsear el mensaje: " + oilRawMessage);
            e.printStackTrace();
        }
    }


    public ArrayList<OilEvent> getParsedOilEvents() {return oilEvents;}
}
