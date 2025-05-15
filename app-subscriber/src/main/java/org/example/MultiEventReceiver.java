package org.example;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MultiEventReceiver implements EventReceiver{
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

            //OIL_QUEUE
            Destination oilQueue = session.createQueue(OIL_QUEUE);
            MessageConsumer oilConsumer = session.createConsumer(oilQueue);
            oilConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try{
                        if (message instanceof TextMessage) {
                            String oilMessage = ((TextMessage) message).getText();
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

    @Override
    public void stop(){
        try{
            if(session != null) session.close();
            if(connection != null) connection.close();
        } catch (JMSException e){
            e.printStackTrace();
        }
    }

}
