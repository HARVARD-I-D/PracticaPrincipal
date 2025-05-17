package eventStore.infrastructure.adapter.jms;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import eventStore.application.domain.model.NewsEvent;
import eventStore.application.domain.model.OilEvent;
import eventStore.infrastructure.port.EventHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MultiEventHandler implements EventHandler {
    private static final String url = "tcp://localhost:61616";
    private static String OIL_PRICE = "OIL_PRICE";
    private static String NEWS_FEED = "NEWS_FEED";

    private Connection connection;
    private Session session;

    private final ArrayList<OilEvent> oilEvents = new ArrayList<>();
    private final ArrayList<NewsEvent> newsEvents = new ArrayList<>();

    private static final String OIL_SUBSCRIPTION_NAME = "OilPriceSubscription";

    @Override
    public void start(){
        try{
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.setClientID("oil-event-subscriber");
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            EventProcessor processor = new EventProcessor();

            Topic oilTopic = session.createTopic(OIL_PRICE);
            TopicSubscriber oilConsumer = session.createDurableSubscriber(oilTopic, OIL_SUBSCRIPTION_NAME);
            oilConsumer.setMessageListener(message -> {
                try{
                    if (message instanceof TextMessage) {
                        String oilMessage = ((TextMessage) message).getText();
                        System.out.println("Mensaje recibido de JMS: " + oilMessage);
                        processor.OilProccessor(oilMessage);
                        synchronized (oilEvents) {
                            oilEvents.addAll(processor.getParsedOilEvents());
                        }
                    }
                } catch (JMSException e){
                    e.printStackTrace();
                }
            });

            //TODO NEWS_QUEUE SEGÚN ESTRUCTURA DEL EVENTO


        } catch (JMSException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<OilEvent> handleOil(){
        synchronized (oilEvents) {
            ArrayList<OilEvent> result = new ArrayList<>(oilEvents);
            oilEvents.clear();
            return result;
        }
    }

    @Override
    public ArrayList<NewsEvent> handleNews(){
        synchronized (newsEvents) {
            return new ArrayList<>(newsEvents);
        }
    }
}
