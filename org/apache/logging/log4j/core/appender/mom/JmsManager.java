package org.apache.logging.log4j.core.appender.mom;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.NamingException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.net.JndiManager;
import org.apache.logging.log4j.status.StatusLogger;

public class JmsManager extends AbstractManager {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static final JmsManager.JmsManagerFactory FACTORY = new JmsManager.JmsManagerFactory();
	private final JndiManager jndiManager;
	private final Connection connection;
	private final Session session;
	private final Destination destination;

	private JmsManager(String name, JndiManager jndiManager, String connectionFactoryName, String destinationName, String username, String password) throws NamingException, JMSException {
		super(null, name);
		this.jndiManager = jndiManager;
		ConnectionFactory connectionFactory = this.jndiManager.lookup(connectionFactoryName);
		if (username != null && password != null) {
			this.connection = connectionFactory.createConnection(username, password);
		} else {
			this.connection = connectionFactory.createConnection();
		}

		this.session = this.connection.createSession(false, 1);
		this.destination = this.jndiManager.lookup(destinationName);
		this.connection.start();
	}

	public static JmsManager getJmsManager(
		String name, JndiManager jndiManager, String connectionFactoryName, String destinationName, String username, String password
	) {
		JmsManager.JmsConfiguration configuration = new JmsManager.JmsConfiguration(jndiManager, connectionFactoryName, destinationName, username, password);
		return getManager(name, FACTORY, configuration);
	}

	public MessageConsumer createMessageConsumer() throws JMSException {
		return this.session.createConsumer(this.destination);
	}

	public MessageProducer createMessageProducer() throws JMSException {
		return this.session.createProducer(this.destination);
	}

	public Message createMessage(Serializable object) throws JMSException {
		return (Message)(object instanceof String ? this.session.createTextMessage((String)object) : this.session.createObjectMessage(object));
	}

	@Override
	protected boolean releaseSub(long timeout, TimeUnit timeUnit) {
		boolean closed = true;

		try {
			this.session.close();
		} catch (JMSException var7) {
			closed = false;
		}

		try {
			this.connection.close();
		} catch (JMSException var6) {
			closed = false;
		}

		return closed && this.jndiManager.stop(timeout, timeUnit);
	}

	private static class JmsConfiguration {
		private final JndiManager jndiManager;
		private final String connectionFactoryName;
		private final String destinationName;
		private final String username;
		private final String password;

		private JmsConfiguration(JndiManager jndiManager, String connectionFactoryName, String destinationName, String username, String password) {
			this.jndiManager = jndiManager;
			this.connectionFactoryName = connectionFactoryName;
			this.destinationName = destinationName;
			this.username = username;
			this.password = password;
		}
	}

	private static class JmsManagerFactory implements ManagerFactory<JmsManager, JmsManager.JmsConfiguration> {
		private JmsManagerFactory() {
		}

		public JmsManager createManager(String name, JmsManager.JmsConfiguration data) {
			try {
				return new JmsManager(name, data.jndiManager, data.connectionFactoryName, data.destinationName, data.username, data.password);
			} catch (Exception var4) {
				JmsManager.LOGGER
					.error("Error creating JmsManager using ConnectionFactory [{}] and Destination [{}].", data.connectionFactoryName, data.destinationName, var4);
				return null;
			}
		}
	}
}
