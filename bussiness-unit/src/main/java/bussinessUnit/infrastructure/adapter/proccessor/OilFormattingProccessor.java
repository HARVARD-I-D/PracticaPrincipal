package bussinessUnit.infrastructure.adapter.proccessor;

import bussinessUnit.application.domain.model.OilEvent;
import jakarta.jms.JMSException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


public class OilFormattingProccessor {
    private OilEvent oilEvent;

    public void proccessRawEvent(String oilRawMessage) throws JMSException {
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
        try {
            String dateStr = fields.get("date");
            String valueStr = fields.get("value");
            String type = fields.getOrDefault("type", "Unknown");
            String source = fields.getOrDefault("source", "Unknown");

            if (dateStr == null || valueStr == null) {
                System.err.println("ERROR FORMATTING PROCCESSOR: Falta campo obligatorio (date o value). Mensaje: " + oilRawMessage);
                return;
            }

            Instant date = Instant.parse(dateStr);
            double value = Double.parseDouble(valueStr);

            oilEvent = new OilEvent(date, value, type, source);
        } catch (Exception e) {
            System.out.println("ERROR FORMATTING PROCCESSOR: Este mensaje no tiene un formato válido (tal vez un CSV)" + oilRawMessage);
            e.printStackTrace();
        }
    }

    public void proccessStoredEvent(String oilStoredEvent){
        try{
            String[] parts = oilStoredEvent.split(",");
            if(parts.length < 4){
                System.err.println("ERROR FORMATTING PROCCESSOR: Este CSV tiene un formato inválido: " + oilStoredEvent);
            }

            Instant date = Instant.parse(parts[0]);
            double value = Double.parseDouble(parts[1]);
            String type = parts[2];
            String source = parts[3];

            oilEvent = new OilEvent(date, value , type, source);
            System.out.println(oilEvent.getTsAsString() + " " + oilEvent.getValue());
        } catch (Exception e){
            System.out.println("ERROR FORMATTING PROCCESSOR: Error de filtración: " + oilStoredEvent);
        }
    }

    public OilEvent getParsedOilEvents() {return oilEvent;}
}
