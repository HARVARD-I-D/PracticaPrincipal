package newsFeeder.infrastructure.adapters.jms;
import com.google.gson.Gson;
import jakarta.jms.*;
import newsFeeder.application.domain.New;
import newsFeeder.infrastructure.port.NewStore;
import org.apache.activemq.ActiveMQConnectionFactory;


public class NewEventFeeder implements NewStore {
    private static final String url = "tcp://localhost:61616";
    private static String subject = "NEWS_FEED";

    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public NewEventFeeder(){
        try{
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(subject);
            messageProducer = session.createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        } catch(JMSException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(New news) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(news);
            TextMessage textMessage = session.createTextMessage(json);
            messageProducer.send(textMessage);
            System.out.println("Enviado Mensaje: " + news.getPublishedAt() + " " + news.getTitle());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public void close(){
        try {
            messageProducer.close();
            session.close();
            connection.close();
        }
        catch (JMSException e){
            e.printStackTrace();
        }
    }
}