package businessUnit.infrastructure.adapter.broker;

import businessUnit.application.domain.model.NewsEvent;
import businessUnit.application.domain.model.OilEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.time.Instant;
import java.util.LinkedList;

public class BrokerEventService {
    private static final String url = "tcp://localhost:61616";
    private static final String OIL_PRICE = "OIL_PRICE";
    private static final String NEWS_FEED = "NEWS_FEED";

    private static final int MAX_EVENTS = 10;
    private final LinkedList<OilEvent> recentOilEvents = new LinkedList<>();
    private final LinkedList<NewsEvent> recentNewsEvents = new LinkedList<>();

    public void start() {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("business-unit-subscriber");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic oilTopic = session.createTopic(OIL_PRICE);
            TopicSubscriber oilSubscriber = session.createDurableSubscriber(oilTopic, "BusinessUnitOilSubscriber");
            oilSubscriber.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    OilEvent event = parseOilEvent((TextMessage) message);
                    if (event != null) {
                        synchronized (recentOilEvents) {
                            if (recentOilEvents.size() >= MAX_EVENTS) {
                                recentOilEvents.removeFirst();
                            }
                            recentOilEvents.add(event);
                        }
                        System.out.println("Nuevo OilEvent recibido: " + event);
                    }
                }
            });

            Topic newsTopic = session.createTopic(NEWS_FEED);
            TopicSubscriber newsSubscriber = session.createDurableSubscriber(newsTopic, "BusinessUnitNewsSubscriber");
            newsSubscriber.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    NewsEvent news = parseNewsEvent((TextMessage) message);
                    if (news != null) {
                        synchronized (recentNewsEvents) {
                            if (recentNewsEvents.size() >= MAX_EVENTS) {
                                recentNewsEvents.removeFirst();
                            }
                            recentNewsEvents.add(news);
                        }
                        System.out.println("Nuevo NewsEvent recibido: " + news.getTitle());
                    }
                }
            });

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private OilEvent parseOilEvent(TextMessage message) {
        try {
            String text = message.getText();
            String[] parts = text.split(",");
            Instant ts = Instant.parse(parts[0].split(":")[1]);
            double value = Double.parseDouble(parts[1].split(":")[1]);
            String type = parts[2].split(":")[1];
            String ss = parts[3].split(":")[1];
            return new OilEvent(ts, value, ss, type);
        } catch (Exception e) {
            System.err.println("Error al parsear OilEvent: " + e.getMessage());
            return null;
        }
    }

    private NewsEvent parseNewsEvent(TextMessage message) {
        try {
            String json = message.getText();
            Gson gson = new Gson();
            return gson.fromJson(json, NewsEvent.class);
        } catch (Exception e) {
            System.err.println("Error al parsear NewsEvent: " + e.getMessage());
            return null;
        }
    }

    public LinkedList<OilEvent> getRecentOilEvents() {
        return recentOilEvents;
    }

    public LinkedList<NewsEvent> getRecentNewsEvents() {
        return recentNewsEvents;
    }
}
