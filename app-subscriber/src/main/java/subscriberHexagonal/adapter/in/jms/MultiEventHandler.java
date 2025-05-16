package subscriberHexagonal.adapter.in.jms;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import subscriberHexagonal.adapter.out.EventProcessor;
import subscriberHexagonal.domain.port.in.EventHandler;

public class MultiEventHandler implements EventHandler {
    private static final String url = "tcp://localhost:61616";
    private static String OIL_QUEUE = "OIL_QUEUE";
    private static String NEWS_QUEUE = "NEWS_QUEUE";

    private Connection connection;
    private Session session;

    @Override
    public void start(){
        try{
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            EventProcessor processor = new EventProcessor();

            //OIL_QUEUE
            Destination oilQueue = session.createQueue(OIL_QUEUE);
            MessageConsumer oilConsumer = session.createConsumer(oilQueue);
            oilConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try{
                        if (message instanceof TextMessage) {
                            String oilMessage = ((TextMessage) message).getText();
                            processor.OilProccessor(oilMessage);
                        }
                    } catch (JMSException e){
                        e.printStackTrace();
                    }
                }
            });

            //TODO NEWS_QUEUE SEGÚN ESTRUCTURA DEL EVENTO


        } catch (JMSException e){
            e.printStackTrace();
        }
    }


}
