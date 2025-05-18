package bussinessUnit.application.service;

import bussinessUnit.application.domain.model.OilEvent;
import bussinessUnit.infrastructure.port.DatamartRepository;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GraphMaker {
    public void mostrarGrafica(String tipo, LocalDate desdeFecha, DatamartRepository datamartRepository) {
        Instant fromInstant = desdeFecha.atStartOfDay().toInstant(ZoneOffset.UTC);
        List<OilEvent> eventos = datamartRepository.getHistoricOilsForGraphs(tipo, fromInstant);

        if (eventos.isEmpty()) {
            System.out.println("No hay datos disponibles para " + tipo + " desde " + desdeFecha);
            return;
        }

        List<Date> fechas = eventos.stream()
                .map(event -> Date.from(event.getTs()))
                .collect(Collectors.toList());

        List<Double> precios = eventos.stream()
                .map(OilEvent::getValue)
                .collect(Collectors.toList());

        XYChart chart = new XYChartBuilder()
                .width(800).height(600)
                .title("Histórico " + tipo)
                .xAxisTitle("Fecha")
                .yAxisTitle("Precio")
                .build();

        chart.addSeries(tipo, fechas, precios);
        new SwingWrapper<>(chart).displayChart();
    }
}
