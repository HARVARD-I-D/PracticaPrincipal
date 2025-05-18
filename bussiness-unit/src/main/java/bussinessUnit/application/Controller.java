package bussinessUnit.application;

import bussinessUnit.application.domain.model.NewsEvent;
import bussinessUnit.application.domain.model.OilEvent;
import bussinessUnit.infrastructure.port.DatamartRepository;
import bussinessUnit.infrastructure.port.HistoricsHandler;
import bussinessUnit.infrastructure.port.LiveBroker;

import java.util.List;

public class Controller {
    private final DatamartRepository datamartRepository;
    private final HistoricsHandler historicsHandler;
    private final LiveBroker liveBroker;

    public Controller(DatamartRepository datamartRepository, HistoricsHandler historicsHandler, LiveBroker liveBroker) {
        this.datamartRepository = datamartRepository;
        this.historicsHandler = historicsHandler;
        this.liveBroker = liveBroker;
    }

    public void loadAndStoreHistoricData() {
        List<OilEvent> oilEvents = historicsHandler.loadHistoricOil();
        for (OilEvent event : oilEvents) {
            datamartRepository.saveOilEvent(event);
        }

        List<NewsEvent> newsEvents = historicsHandler.loadHistoricNews();
        for (NewsEvent event : newsEvents) {
            datamartRepository.saveNewsEvent(event);
        }

        System.out.println("Eventos históricos almacenados en el datamart.");
    }


    public void runLiveBroker() {
        liveBroker.start();
        System.out.println("Esperando eventos en vivo...");

        while (true) {
            List<OilEvent> oilEvents = liveBroker.getRecentOilEvents();
            if (!oilEvents.isEmpty()) {
                for (OilEvent event : oilEvents) {
                    datamartRepository.saveRecentOilEvent(event);
                }
            }

            List<NewsEvent> newsEvents = liveBroker.getRecentNewsEvents();
            if (!newsEvents.isEmpty()) {
                for (NewsEvent event : newsEvents) {
                    System.out.println("Almacenando evento NEWS: " + event.getPublishedAt() + " - " + event.getTitle());
                    datamartRepository.saveRecentNewsEvent(event);
                }
            }

            try {
                Thread.sleep(1000); // Espera 1 segundo antes de revisar de nuevo
            } catch (InterruptedException e) {
                System.out.println("Interrumpido, finalizando...");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
