package bussinessUnit.infrastructure.adapter.eventStore;

import bussinessUnit.application.domain.model.NewsEvent;
import bussinessUnit.application.domain.model.OilEvent;
import bussinessUnit.infrastructure.adapter.proccessor.OilFormattingProccessor;
import bussinessUnit.infrastructure.port.HistoricsHandler;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HistoricsAccessor implements HistoricsHandler {
    private final String BASE_DIR = Paths.get(System.getProperty("user.dir"))
            .resolve("eventstore")
            .toString();
    private final String OIL_PRICE = "OIL_PRICE";
    private final String NEWS_FEED = "NEWS_FEED";
    private final String SS_DIR_OIL = "AlphaVantage";
    private final String SS_DIR_NEWS = "NewsAPI";

    @Override
    public List<OilEvent> loadHistoricOil(){
        List<OilEvent> events = new ArrayList<>();
        File dir = Paths.get(BASE_DIR, OIL_PRICE, SS_DIR_OIL).toFile();

        if(!dir.exists() || !dir.isDirectory()) {
            System.err.println("Directorio de eventos no encontrado: " + dir.getAbsolutePath());
            return events;
        }

        File[] files = dir.listFiles((_, name) -> name.endsWith(".events"));
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

    @Override
    public List<NewsEvent> loadHistoricNews(){
        List<NewsEvent> events = new ArrayList<>();
        File dir = Paths.get(BASE_DIR, NEWS_FEED, SS_DIR_NEWS).toFile();

        if(!dir.exists() || !dir.isDirectory()) {
            System.err.println("Directorio de noticias no encontrado: " + dir.getAbsolutePath());
            return events;
        }

        File[] files = dir.listFiles((_, name) -> name.endsWith(".events"));
        if(files == null) return events;

        for (File file : files){
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    NewsEvent event = parseLineToNewsEvent(line);
                    if (event != null) {
                        events.add(event);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error leyendo archivo: " + file.getName());
                e.printStackTrace();
            }
        }
        return events;
    }

    private OilEvent parseLineToOilEvent(String line) {
        OilFormattingProccessor processor = new OilFormattingProccessor();
        try {
            processor.proccessStoredEvent(line);
            return processor.getParsedOilEvents();
        } catch (Exception e) {
            System.err.println("HISTORICS ACCESSOR ERROR: Fallo de filtrado de línea OIL " + line);
            return null;
        }
    }

    private NewsEvent parseLineToNewsEvent(String line) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(line, NewsEvent.class);
        } catch (Exception e) {
            System.err.println("HISTORICS ACCESSOR ERROR: Fallo de filtrado de línea NEWS: " + line);
            return null;
        }
    }
}
