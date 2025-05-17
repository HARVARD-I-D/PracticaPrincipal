package businessUnit.infrastructure.port;

import businessUnit.application.domain.model.OilEvent;
import businessUnit.application.domain.model.NewsEvent;

import java.util.List;

public interface DatamartRepository {
    void saveOilEvent(OilEvent event);
    void saveNewsEvent(NewsEvent event);

    List<OilEvent> getLastOilEvents(int count);
    List<NewsEvent> getLastNewsEvents(int count);
}
