package br.com.caelum.jms;
 
import java.util.Properties;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteConsumidorProperties {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {

		Properties properties = new Properties();
		
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.setProperty("java.naming.provider.url", "tcp://192.168.0.103:61616");
		properties.setProperty("queue.finaceiro", "fila.finaceiro");				
		
		InitialContext context = new InitialContext(properties);

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();

		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination fila = (Destination) context.lookup("finaceiro");

		MessageConsumer consumer = session.createConsumer(fila);

//		consumer.receive();
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {

				TextMessage txtMessage = (TextMessage) message;

				try {
					
					System.out.println(txtMessage.getText());
					
				} catch (JMSException e) {

					e.printStackTrace();
				}

			}
		});

		// Message message = consumer.receive();

		// TextMessage txt = (TextMessage) consumer.receive();

		// System.out.println("Recebendo a mensagem: "+message);

		// System.out.println("Recebendo a mensagem: "+txt.getText());

		new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();

	}

}