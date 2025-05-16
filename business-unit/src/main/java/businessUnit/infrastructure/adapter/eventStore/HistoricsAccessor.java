package businessUnit.infrastructure.adapter.eventStore;

import businessUnit.application.domain.model.OilEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class HistoricsAccessor {
    private static final String BASE_DIR = Paths.get(System.getProperty("user.dir"))
            .getParent()
            .resolve("eventstore")
            .toString();
    private static final String OIL_PRICE = "OIL_PRICE";
    private static final String SS_DIR_OIL = "AlphaVantage";

    public List<OilEvent> loadHistoricOil(){
        List<OilEvent> events = new ArrayList<>();
        File dir = Paths.get(BASE_DIR, OIL_PRICE, SS_DIR_OIL).toFile();

        if(!dir.exists() || !dir.isDirectory()) {
            System.err.println("Directorio de eventos no encontrado: " + dir.getAbsolutePath());
            return events;
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".events"));
        if(files == null) return events;

        for(File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    OilEvent event = parseLineToOilEvent(line);
                    if (event != null){
                        events.add(event);
                    }
                }

            } catch (Exception e){
                System.err.println("Error leyendo archivo: " + file.getName());
                e.printStackTrace();
            }
        }
        return events;
    }

    private OilEvent parseLineToOilEvent(String line) {
        try{
            String[] parts = line.split(",");
            Instant ts = Instant.parse(parts[0]);
            double value = Double.parseDouble(parts[1]);
            String type = parts[2];
            String ss = parts[3];
            return new OilEvent(ts, value, ss ,type);
        } catch (Exception e){
            System.err.println("Error al parsear línea: " + line);
            return null;
        }
    }
}
