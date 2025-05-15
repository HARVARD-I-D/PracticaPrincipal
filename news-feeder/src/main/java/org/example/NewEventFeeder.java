/*
package org.example;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.jetty.server.ConnectionFactory;
import org.w3c.dom.Text;
import spark.Session;
import java.sql.Connection;

public class NewEventFeeder implements NewStore{
    private static final String url = "tcp://localhost:61616";
    private static String subject = "NEW_QUEUE";

    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public NewEventFeeder(){
        try{
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        } catch(){

        }
    }

    @Override
    public void save(New New) {

    }
}
*/