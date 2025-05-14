package org.example;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class OilEventFeeder implements OilStore{
    private static final String url = "tcp://localhost:61616";
    private static String subject = "OIL_QUEUE";

    private final Connection connection;
    private final Session session;
    private final MessageProducer producer;

    public OilEventFeeder() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);
        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    @Override
    public void save(OilPrice oilPrice) {
        try{
            ObjectMessage message = session.createObjectMessage(oilPrice);
            producer.send(message);
            System.out.println("Enviado Mensaje: " + message);
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
