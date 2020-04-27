package br.com.caelum.jms;

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

public class TesteConsumidorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

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