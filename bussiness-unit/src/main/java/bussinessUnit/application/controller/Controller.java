package bussinessUnit.application.controller;

import bussinessUnit.application.domain.model.NewsEvent;
import bussinessUnit.application.domain.model.OilEvent;
import bussinessUnit.application.service.GraphMaker;
import bussinessUnit.infrastructure.port.DatamartRepository;
import bussinessUnit.infrastructure.port.HistoricsHandler;
import bussinessUnit.infrastructure.port.LiveBroker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private final DatamartRepository datamartRepository;
    private final HistoricsHandler historicsHandler;
    private final LiveBroker liveBroker;
    private final GraphMaker graphMaker;

    public Controller(DatamartRepository datamartRepository, HistoricsHandler historicsHandler, LiveBroker liveBroker) {
        this.datamartRepository = datamartRepository;
        this.historicsHandler = historicsHandler;
        this.liveBroker = liveBroker;
        this.graphMaker = new GraphMaker();
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

    public void runCLI() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.now().minusDays(30);

        while (true) {
            System.out.println("\n--- MENÚ CLI ---");
            System.out.println("1. Ver históricos en gráfica de Brent");
            System.out.println("2. Ver históricos en gráfica de WTI");
            System.out.println("3. Elegir desde qué fecha quiere graficar (actual: " + fromDate.format(formatter) + ")");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    graphMaker.mostrarGrafica("Brent", fromDate, datamartRepository);
                    break;
                case "2":
                    graphMaker.mostrarGrafica("WTI", fromDate, datamartRepository);
                    break;
                case "3":
                    System.out.print("Ingrese fecha en formato yyyy-MM-dd: ");
                    String input = scanner.nextLine();
                    try {
                        fromDate = LocalDate.parse(input, formatter);
                    } catch (Exception e) {
                        System.out.println("Fecha inválida. Intente de nuevo.");
                    }
                    break;
                case "0":
                    System.out.println("Saliendo del CLI...");
                    return;
                default:
                    System.out.println("Opción inválida.");

                //TODO Añadir funcionalidades:
                //  - Calculadora de diferencia de precios
                //  - Impresora de noticias relevantes
                //  - Ultimar detalles
            }
        }
    }
}
