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
    public void showChart(String type, LocalDate fromDate, DatamartRepository datamartRepository) {
        Instant fromInstant = fromDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        List<OilEvent> events = datamartRepository.getHistoricOilsForGraphs(type, fromInstant);

        List<OilEvent> filteredEvents = events.stream()
                .filter(event -> event.getValue() != null && event.getValue() != 0.0)
                .collect(Collectors.toList());

        if (filteredEvents.isEmpty()) {
            System.out.println("No data available for " + type + " from " + fromDate + " (after filtering invalid values)");
            return;
        }

        List<Date> dates = filteredEvents.stream()
                .map(event -> Date.from(event.getTs()))
                .collect(Collectors.toList());

        List<Double> prices = filteredEvents.stream()
                .map(OilEvent::getValue)
                .collect(Collectors.toList());

        XYChart chart = new XYChartBuilder()
                .width(800).height(600)
                .title("Historical " + type)
                .xAxisTitle("Date")
                .yAxisTitle("Price")
                .build();

        chart.addSeries(type, dates, prices);
        new SwingWrapper<>(chart).displayChart();
    }


}
