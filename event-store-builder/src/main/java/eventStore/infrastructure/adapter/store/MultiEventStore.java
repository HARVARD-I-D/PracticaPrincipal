package eventStore.infrastructure.adapter.store;

import com.google.gson.Gson;
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
    private final String BASE_DIR = Paths.get(System.getProperty("user.dir"))
            .resolve("eventstore")
            .toString();
    private final String TOPIC_OIL = "OIL_PRICE";
    private final String SS_DIR_OIL = "AlphaVantage";

    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public void storeOil(OilEvent oilEvent) {
        String dateStr = DATE_FORMAT.format(oilEvent.getTs().atZone(ZoneOffset.UTC));
        String fileName = dateStr + ".events";

        File dir = Paths.get(BASE_DIR, TOPIC_OIL, SS_DIR_OIL).toFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String line = String.format("%s,%s,%s,%s",
                    oilEvent.getTsAsString(),
                    oilEvent.getValue(),
                    oilEvent.getType(),
                    oilEvent.getSs());

            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir evento en archivo: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    @Override
    public void storeNews(NewsEvent newsEvent) {
        String dateStr = newsEvent.getPublishedAt().substring(0, 10).replace("-", ""); // yyyyMMdd
        String fileName = dateStr + ".events";

        String topic = "NEWS_FEED";
        String ssDir = "NewsAPI";

        File dir = Paths.get(BASE_DIR, topic, ssDir).toFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String json = new Gson().toJson(newsEvent);

            writer.write(json);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir evento de noticia en archivo: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

}
