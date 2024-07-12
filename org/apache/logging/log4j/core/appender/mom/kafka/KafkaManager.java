package org.apache.logging.log4j.core.appender.mom.kafka;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.util.Log4jThread;

public class KafkaManager extends AbstractManager {
	public static final String DEFAULT_TIMEOUT_MILLIS = "30000";
	static KafkaProducerFactory producerFactory = new DefaultKafkaProducerFactory();
	private final Properties config = new Properties();
	private Producer<byte[], byte[]> producer;
	private final int timeoutMillis;
	private final String topic;
	private final boolean syncSend;

	public KafkaManager(LoggerContext loggerContext, String name, String topic, boolean syncSend, Property[] properties) {
		super(loggerContext, name);
		this.topic = (String)Objects.requireNonNull(topic, "topic");
		this.syncSend = syncSend;
		this.config.setProperty("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		this.config.setProperty("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		this.config.setProperty("batch.size", "0");

		for (Property property : properties) {
			this.config.setProperty(property.getName(), property.getValue());
		}

		this.timeoutMillis = Integer.parseInt(this.config.getProperty("timeout.ms", "30000"));
	}

	@Override
	public boolean releaseSub(long timeout, TimeUnit timeUnit) {
		if (timeout > 0L) {
			this.closeProducer(timeout, timeUnit);
		} else {
			this.closeProducer((long)this.timeoutMillis, TimeUnit.MILLISECONDS);
		}

		return true;
	}

	private void closeProducer(long timeout, TimeUnit timeUnit) {
		if (this.producer != null) {
			Thread closeThread = new Log4jThread(new Runnable() {
				public void run() {
					if (KafkaManager.this.producer != null) {
						KafkaManager.this.producer.close();
					}
				}
			}, "KafkaManager-CloseThread");
			closeThread.setDaemon(true);
			closeThread.start();

			try {
				closeThread.join(timeUnit.toMillis(timeout));
			} catch (InterruptedException var6) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public void send(byte[] msg) throws ExecutionException, InterruptedException, TimeoutException {
		if (this.producer != null) {
			ProducerRecord<byte[], byte[]> newRecord = new ProducerRecord(this.topic, msg);
			if (this.syncSend) {
				Future<RecordMetadata> response = this.producer.send(newRecord);
				response.get((long)this.timeoutMillis, TimeUnit.MILLISECONDS);
			} else {
				this.producer.send(newRecord, new Callback() {
					public void onCompletion(RecordMetadata metadata, Exception e) {
						if (e != null) {
							KafkaManager.LOGGER.error("Unable to write to Kafka [" + KafkaManager.this.getName() + "].", (Throwable)e);
						}
					}
				});
			}
		}
	}

	public void startup() {
		this.producer = producerFactory.newKafkaProducer(this.config);
	}

	public String getTopic() {
		return this.topic;
	}
}
