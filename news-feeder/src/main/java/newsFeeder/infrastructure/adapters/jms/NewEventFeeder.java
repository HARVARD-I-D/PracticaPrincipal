package newsFeeder.infrastructure.adapters.jms;
import jakarta.jms.*;
import newsFeeder.application.domain.New;
import newsFeeder.infrastructure.port.NewStore;
import org.apache.activemq.ActiveMQConnectionFactory;


public class NewEventFeeder implements NewStore {
    private static final String url = "tcp://localhost:61616";
    private static String subject = "NEW_QUEUE";

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
    public void save(New New) {
        try{
            String text = "Date" + New.getPublishedAt() + "Author" + New.getAuthor() + "Title" + New.getTitle() +
                    "Description" + New.getDescription() + "Url" + New.getUrl() + "Url To Image" + New.getUrlToImage() +
                    "Content:" + New.getContent() + "Source: " + New.getSourceAsString() + "Id: " + New.getId() +
                    "Name: " + New.getName();
            TextMessage textMessage = session.createTextMessage(text);
            messageProducer.send(textMessage);
            System.out.println("Enviado Mensaje: " + New.getPublishedAt() + " " + New.getTitle());
        }
        catch (JMSException e){
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