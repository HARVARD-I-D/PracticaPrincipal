package bussinessUnit.application.service;

import bussinessUnit.application.domain.model.OilEvent;
import bussinessUnit.infrastructure.port.DatamartRepository;

import java.util.Comparator;
import java.util.List;


public class FinancialCalculator {
    public static void calculateFinancialSummary(DatamartRepository repository){
        List<OilEvent> recent = repository.getLastOilEvents(100);
        for (OilEvent event : recent){
            System.out.println("Precios recientes:");
            System.out.println(event.getType());
        }
        System.out.println("Eventos recientes totales obtenidos: " + recent.size());
        recent.forEach(e -> System.out.println("Tipo: " + e.getType() + ", Fecha: " + e.getTsAsString() + ", Valor: " + e.getValue()));

        List<OilEvent> brentEvents = recent.stream()
                .filter(e -> e.getType().equalsIgnoreCase("Brent"))
                .sorted(Comparator.comparing(OilEvent::getTs).reversed())
                .limit(50)
                .toList();

        System.out.println("Brent events found: " + brentEvents.size());

        List<OilEvent> wtiEvents = recent.stream()
                .filter(e -> e.getType().equalsIgnoreCase("WTI"))
                .sorted(Comparator.comparing(OilEvent::getTs).reversed())
                .limit(50)
                .toList();

        System.out.println("WTI events found: " + wtiEvents.size());

        System.out.println("\n--- RESUMEN FINANCIERO ---");
        analizar("Brent", brentEvents);
        analizar("WTI", wtiEvents);
    }

    private static void analizar(String tipo, List<OilEvent> eventosRecientes){
        if(eventosRecientes.size() < 2) {
            System.out.println("No hay suficientes datos recientes para " + tipo);
            return;
        }

        double media = eventosRecientes.stream().mapToDouble(OilEvent::getValue).average().orElse(0);
        double primero = eventosRecientes.get(eventosRecientes.size() - 1).getValue();
        double ultimo = eventosRecientes.get(0).getValue();
        double cambio = ((ultimo - primero) / primero) * 100;
        double max = eventosRecientes.stream().mapToDouble(OilEvent::getValue).max().orElse(0);
        double min = eventosRecientes.stream().mapToDouble(OilEvent::getValue).min().orElse(0);
        double rango = max - min;

        System.out.println("\nTipo: " + tipo);
        System.out.printf("Media de precios (últimos %d): %.2f\n", eventosRecientes.size(), media);
        System.out.printf("Cambio porcentual: %.2f%% (%s)\n", cambio, cambio >= 0 ? "subida" : "bajada");
        System.out.printf("Máximo: %.2f, Mínimo: %.2f, Rango: %.2f\n", max, min, rango);
    }

}
