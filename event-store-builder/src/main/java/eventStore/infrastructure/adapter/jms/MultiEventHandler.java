package eventStore.infrastructure.adapter.jms;

import com.google.gson.Gson;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import eventStore.application.domain.model.NewsEvent;
import eventStore.application.domain.model.OilEvent;
import eventStore.infrastructure.port.EventHandler;

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
    private static final String NEWS_SUBSCRIPTION_NAME = "NewsFeedSubscription";

    @Override
    public void start(){
        try{
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.setClientID("event-subscriber");
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            OilEventProcessor processor = new OilEventProcessor();

            Topic oilTopic = session.createTopic(OIL_PRICE);
            TopicSubscriber oilConsumer = session.createDurableSubscriber(oilTopic, OIL_SUBSCRIPTION_NAME);
            oilConsumer.setMessageListener(message -> {
                try{
                    if (message instanceof TextMessage) {
                        String oilMessage = ((TextMessage) message).getText();
                        System.out.println("Mensaje de petróleo recibido: " + oilMessage);
                        processor.OilProccessor(oilMessage);
                        synchronized (oilEvents) {
                            oilEvents.addAll(processor.getParsedOilEvents());
                        }
                    }
                } catch (JMSException e){
                    e.printStackTrace();
                }
            });

            Topic newsTopic = session.createTopic(NEWS_FEED);
            MessageConsumer newsConsumer = session.createDurableSubscriber(newsTopic, NEWS_SUBSCRIPTION_NAME);
            newsConsumer.setMessageListener(message -> {
                try {
                    if (message instanceof TextMessage) {
                        String json = ((TextMessage) message).getText();
                        Gson gson = new Gson();
                        NewsEvent newsEvent = gson.fromJson(json, NewsEvent.class);

                        synchronized (newsEvents) {
                            newsEvents.add(newsEvent);
                        }

                        System.out.println("Mensaje de noticia recibido: " + newsEvent.getPublishedAt() + " - " + newsEvent.getTitle());
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });

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
            ArrayList<NewsEvent> result = new ArrayList<>(newsEvents);
            newsEvents.clear();
            return result;
        }
    }
}
