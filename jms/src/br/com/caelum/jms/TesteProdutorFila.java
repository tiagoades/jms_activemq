package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProdutorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();

		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination fila = (Destination) context.lookup("finaceiro");

		MessageProducer producer = session.createProducer(fila);

		String texto;// = "<pedido><id>123</pedido></id>";
		
		
		//Message message = session.createTextMessage(texto);
		
		//producer.send(message);
		
		for (int i = 0; i < 10; i++) {
			
			 texto = "<pedido><id>"+i+"</pedido></id>";
			
			 Message message = session.createTextMessage(texto);
			
			producer.send(message);
			
		}

		//new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();

	}

}

