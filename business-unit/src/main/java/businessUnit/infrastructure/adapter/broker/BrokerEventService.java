package businessUnit.infrastructure.adapter.broker;


import businessUnit.application.domain.model.NewsEvent;
import businessUnit.application.domain.model.OilEvent;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.time.Instant;
import java.util.LinkedList;

public class BrokerEventService {
    private static final String url = "tcp://localhost:61616";
    private static String OIL_PRICE = "OIL_PRICE";
    private static String NEWS_FEED = "NEWS_FEED";

    private static final int MAX_EVENTS = 10;
    private final LinkedList<OilEvent> recentOilEvents = new LinkedList<>();
    private final LinkedList<NewsEvent> recentNewsEvents = new LinkedList<>();

    public void start(){
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
                    if(event != null){
                        synchronized (recentOilEvents) {
                            if (recentOilEvents.size() >= MAX_EVENTS){
                                recentOilEvents.removeFirst();
                            }
                            recentOilEvents.add(event);
                        }
                        System.out.println("Nuevo OilEvent recibido: " + event);
                    }
                }
            });

            //TODO Crear broker service de recientes y parser para News

        } catch (JMSException e){
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

    public LinkedList<OilEvent> getRecentOilEvents() {
        return recentOilEvents;
    }
}
