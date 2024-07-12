package org.apache.logging.log4j.core.net.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.logging.log4j.LoggingException;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LogEventListener;
import org.apache.logging.log4j.core.LifeCycle.State;
import org.apache.logging.log4j.core.appender.mom.JmsManager;
import org.apache.logging.log4j.core.net.JndiManager;

public class JmsServer extends LogEventListener implements MessageListener, LifeCycle2 {
	private final AtomicReference<State> state = new AtomicReference(State.INITIALIZED);
	private final JmsManager jmsManager;
	private MessageConsumer messageConsumer;

	public JmsServer(String connectionFactoryBindingName, String destinationBindingName, String username, String password) {
		String managerName = JmsServer.class.getName() + '@' + JmsServer.class.hashCode();
		JndiManager jndiManager = JndiManager.getDefaultManager(managerName);
		this.jmsManager = JmsManager.getJmsManager(managerName, jndiManager, connectionFactoryBindingName, destinationBindingName, username, password);
	}

	@Override
	public State getState() {
		return (State)this.state.get();
	}

	public void onMessage(Message message) {
		try {
			if (message instanceof ObjectMessage) {
				Object body = ((ObjectMessage)message).getObject();
				if (body instanceof LogEvent) {
					this.log((LogEvent)body);
				} else {
					LOGGER.warn("Expected ObjectMessage to contain LogEvent. Got type {} instead.", body.getClass());
				}
			} else {
				LOGGER.warn("Received message of type {} and JMSType {} which cannot be handled.", message.getClass(), message.getJMSType());
			}
		} catch (JMSException var3) {
			LOGGER.catching(var3);
		}
	}

	@Override
	public void initialize() {
	}

	@Override
	public void start() {
		if (this.state.compareAndSet(State.INITIALIZED, State.STARTING)) {
			try {
				this.messageConsumer = this.jmsManager.createMessageConsumer();
				this.messageConsumer.setMessageListener(this);
			} catch (JMSException var2) {
				throw new LoggingException(var2);
			}
		}
	}

	@Override
	public void stop() {
		this.stop(0L, AbstractLifeCycle.DEFAULT_STOP_TIMEUNIT);
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		boolean stopped = true;

		try {
			this.messageConsumer.close();
		} catch (JMSException var6) {
			LOGGER.debug("Exception closing {}", this.messageConsumer, var6);
			stopped = false;
		}

		return stopped && this.jmsManager.stop(timeout, timeUnit);
	}

	@Override
	public boolean isStarted() {
		return this.state.get() == State.STARTED;
	}

	@Override
	public boolean isStopped() {
		return this.state.get() == State.STOPPED;
	}

	public void run() throws IOException {
		this.start();
		System.out.println("Type \"exit\" to quit.");
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));

		String line;
		do {
			line = stdin.readLine();
		} while (line != null && !line.equalsIgnoreCase("exit"));

		System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
		this.stop();
	}
}
