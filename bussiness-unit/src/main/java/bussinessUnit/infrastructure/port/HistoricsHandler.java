package bussinessUnit.infrastructure.port;

import bussinessUnit.application.domain.model.NewsEvent;
import bussinessUnit.application.domain.model.OilEvent;
import java.util.List;

public interface HistoricsHandler {
    List<OilEvent> loadHistoricOil();
    List<NewsEvent> loadHistoricNews();

}
