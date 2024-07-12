package org.apache.logging.log4j.core.appender.mom.kafka;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.SerializedLayout;
import org.apache.logging.log4j.core.util.StringEncoder;

@Plugin(
	name = "Kafka",
	category = "Core",
	elementType = "appender",
	printObject = true
)
public final class KafkaAppender extends AbstractAppender {
	private final KafkaManager manager;

	@Deprecated
	public static KafkaAppender createAppender(
		@PluginElement("Layout") Layout<? extends Serializable> layout,
		@PluginElement("Filter") Filter filter,
		@Required(message = "No name provided for KafkaAppender") @PluginAttribute("name") String name,
		@PluginAttribute(value = "ignoreExceptions",defaultBoolean = true) boolean ignoreExceptions,
		@Required(message = "No topic provided for KafkaAppender") @PluginAttribute("topic") String topic,
		@PluginElement("Properties") Property[] properties,
		@PluginConfiguration Configuration configuration
	) {
		KafkaManager kafkaManager = new KafkaManager(configuration.getLoggerContext(), name, topic, true, properties);
		return new KafkaAppender(name, layout, filter, ignoreExceptions, kafkaManager);
	}

	@PluginBuilderFactory
	public static <B extends KafkaAppender.Builder<B>> B newBuilder() {
		return new KafkaAppender.Builder<B>().asBuilder();
	}

	private KafkaAppender(String name, Layout<? extends Serializable> layout, Filter filter, boolean ignoreExceptions, KafkaManager manager) {
		super(name, filter, layout, ignoreExceptions);
		this.manager = (KafkaManager)Objects.requireNonNull(manager, "manager");
	}

	@Override
	public void append(LogEvent event) {
		if (event.getLoggerName().startsWith("org.apache.kafka")) {
			LOGGER.warn("Recursive logging from [{}] for appender [{}].", event.getLoggerName(), this.getName());
		} else {
			try {
				Layout<? extends Serializable> layout = this.getLayout();
				byte[] data;
				if (layout != null) {
					if (layout instanceof SerializedLayout) {
						byte[] header = layout.getHeader();
						byte[] body = layout.toByteArray(event);
						data = new byte[header.length + body.length];
						System.arraycopy(header, 0, data, 0, header.length);
						System.arraycopy(body, 0, data, header.length, body.length);
					} else {
						data = layout.toByteArray(event);
					}
				} else {
					data = StringEncoder.toBytes(event.getMessage().getFormattedMessage(), StandardCharsets.UTF_8);
				}

				this.manager.send(data);
			} catch (Exception var6) {
				LOGGER.error("Unable to write to Kafka [{}] for appender [{}].", this.manager.getName(), this.getName(), var6);
				throw new AppenderLoggingException("Unable to write to Kafka in appender: " + var6.getMessage(), var6);
			}
		}
	}

	@Override
	public void start() {
		super.start();
		this.manager.startup();
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		this.setStopping();
		boolean stopped = super.stop(timeout, timeUnit, false);
		stopped &= this.manager.stop(timeout, timeUnit);
		this.setStopped();
		return stopped;
	}

	@Override
	public String toString() {
		return "KafkaAppender{name=" + this.getName() + ", state=" + this.getState() + ", topic=" + this.manager.getTopic() + '}';
	}

	public static class Builder<B extends KafkaAppender.Builder<B>>
		extends AbstractAppender.Builder<B>
		implements org.apache.logging.log4j.core.util.Builder<KafkaAppender> {
		@PluginAttribute("topic")
		private String topic;
		@PluginAttribute(
			value = "syncSend",
			defaultBoolean = true
		)
		private boolean syncSend;
		@PluginElement("Properties")
		private Property[] properties;

		public KafkaAppender build() {
			KafkaManager kafkaManager = new KafkaManager(this.getConfiguration().getLoggerContext(), this.getName(), this.topic, this.syncSend, this.properties);
			return new KafkaAppender(this.getName(), this.getLayout(), this.getFilter(), this.isIgnoreExceptions(), kafkaManager);
		}

		public String getTopic() {
			return this.topic;
		}

		public Property[] getProperties() {
			return this.properties;
		}

		public B setTopic(String topic) {
			this.topic = topic;
			return this.asBuilder();
		}

		public B setSyncSend(boolean syncSend) {
			this.syncSend = syncSend;
			return this.asBuilder();
		}

		public B setProperties(Property[] properties) {
			this.properties = properties;
			return this.asBuilder();
		}
	}
}
