package org.example;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class OilEventFeeder implements OilStore{
    private static final String url = "tcp://localhost:61616";
    private static String subject = "OIL_QUEUE";

    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public OilEventFeeder(){
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        }
        catch (JMSException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(OilPrice oilPrice) {
        try{
            String text = "Date:" + oilPrice.getTs() + "," + "Value:" + oilPrice.getValue() + "," +
                    "Type:" + oilPrice.getType() + "," + "Source:" + oilPrice.getSs();
            TextMessage message = session.createTextMessage(text);
            producer.send(message);
            System.out.println("Enviado Mensaje: " + oilPrice.getTs() + " " + oilPrice.getValue());
        }
        catch (JMSException e){
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
