package bussinessUnit.infrastructure.adapter.broker;

import bussinessUnit.application.domain.model.NewsEvent;
import bussinessUnit.application.domain.model.OilEvent;
import bussinessUnit.infrastructure.adapter.proccessor.OilFormattingProccessor;
import bussinessUnit.infrastructure.port.LiveBroker;
import com.google.gson.Gson;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.LinkedList;

public class BrokerEventService implements LiveBroker {
    private final String url = "tcp://localhost:61616";
    private final String OIL_PRICE = "OIL_PRICE";
    private final String NEWS_FEED = "NEWS_FEED";

    public final int MAX_EVENTS = 10;
    private final LinkedList<OilEvent> recentOilEvents = new LinkedList<>();
    private final LinkedList<NewsEvent> recentNewsEvents = new LinkedList<>();

    @Override
    public void start() {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("business-unit-subscriber");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            OilFormattingProccessor processor = new OilFormattingProccessor();

            Topic oilTopic = session.createTopic(OIL_PRICE);
            TopicSubscriber oilSubscriber = session.createDurableSubscriber(oilTopic, "BusinessUnitOilSubscriber");
            oilSubscriber.setMessageListener(message -> {
                try {
                    if (message instanceof TextMessage) {
                        String oilMessage = ((TextMessage) message).getText();
                        System.out.println("Mensaje de petróleo recibido: " + oilMessage);
                        processor.proccessRawEvent(oilMessage);
                        OilEvent event = processor.getParsedOilEvents();
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
                } catch (JMSException e){
                    e.printStackTrace();
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

    @Override
    public LinkedList<OilEvent> getRecentOilEvents() {
        synchronized (recentOilEvents) {
            LinkedList<OilEvent> result = new LinkedList<>(recentOilEvents);
            recentOilEvents.clear();
            return result;
        }
    }

    @Override
    public LinkedList<NewsEvent> getRecentNewsEvents() {
        synchronized (recentNewsEvents) {
            LinkedList<NewsEvent> result = new LinkedList<>(recentNewsEvents);
            recentNewsEvents.clear();
            return result;
        }
    }

}
