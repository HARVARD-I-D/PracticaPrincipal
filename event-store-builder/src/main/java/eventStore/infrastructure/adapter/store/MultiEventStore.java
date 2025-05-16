package eventStore.infrastructure.adapter.store;

import eventStore.application.domain.model.NewsEvent;
import eventStore.application.domain.model.OilEvent;
import eventStore.infrastructure.port.EventStore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class MultiEventStore implements EventStore {
    private static final String BASE_DIR = "eventstore";
    private static final String TOPIC_OIL = "OIL_PRICE";
    private static final String SS_DIR_OIL = "AlphaVantage";

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public void storeOil(OilEvent oilEvent) {
        String dateStr = DATE_FORMAT.format(oilEvent.getTs().atZone(ZoneOffset.UTC));
        String fileName = dateStr + ".events";

        File dir = Paths.get(BASE_DIR, TOPIC_OIL, SS_DIR_OIL).toFile();
        if(!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))){
            String line = String.format("%s,%f,%s,%s",
                    oilEvent.getTsAsString(),
                    oilEvent.getValue(),
                    oilEvent.getType(),
                    oilEvent.getSs());

            writer.write(line);
            writer.newLine();
        } catch (IOException e){
            System.err.println("Error al escribir evento en archivo: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }
    @Override
    public void storeNews(NewsEvent newsEvent) {

    }
}
