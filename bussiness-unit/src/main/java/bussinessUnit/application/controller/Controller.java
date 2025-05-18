package bussinessUnit.application.controller;

import bussinessUnit.application.domain.model.NewsEvent;
import bussinessUnit.application.domain.model.OilEvent;
import bussinessUnit.application.service.BreakingNewsAccessor;
import bussinessUnit.application.service.FinancialCalculator;
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
    private final FinancialCalculator calculator;
    private final BreakingNewsAccessor breakingNewsAccessor;

    public Controller(DatamartRepository datamartRepository, HistoricsHandler historicsHandler, LiveBroker liveBroker) {
        this.datamartRepository = datamartRepository;
        this.historicsHandler = historicsHandler;
        this.liveBroker = liveBroker;
        this.graphMaker = new GraphMaker();
        this.calculator = new FinancialCalculator();
        this.breakingNewsAccessor = new BreakingNewsAccessor();
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
                Thread.sleep(1000);
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
            System.out.println("\n--- BIENVENIDO A LA TERMINAL McPATO ---");
            System.out.println("1. Ver históricos en gráfica de Brent");
            System.out.println("2. Ver históricos en gráfica de WTI");
            System.out.println("3. Elegir desde qué fecha quiere graficar (actual: " + fromDate.format(formatter) + ")");
            System.out.println("4. Ver análisis financiero de los últimos precios");
            System.out.println("5. Ultimas noticias relevantes");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    graphMaker.showChart("Brent", fromDate, datamartRepository);
                    break;
                case "2":
                    graphMaker.showChart("WTI", fromDate, datamartRepository);
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
                case "4":
                    calculator.calculateFinancialSummary(datamartRepository);
                    break;
                case "5":
                    breakingNewsAccessor.printLatestNews(datamartRepository);
                    break;
                case "0":
                    System.out.println("Saliendo del CLI...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
