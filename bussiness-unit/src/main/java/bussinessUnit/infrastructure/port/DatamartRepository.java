package bussinessUnit.infrastructure.port;

import bussinessUnit.application.domain.model.OilEvent;
import bussinessUnit.application.domain.model.NewsEvent;

import java.util.List;

public interface DatamartRepository {
    void saveOilEvent(OilEvent event);
    void saveNewsEvent(NewsEvent event);
    void saveRecentOilEvent(OilEvent event);
    void saveRecentNewsEvent(NewsEvent event);

    List<OilEvent> getLastOilEvents(int count);
    List<NewsEvent> getLastNewsEvents(int count);
}
