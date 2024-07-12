package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Objects;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.ErrorHandler;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.Integers;

public abstract class AbstractAppender extends AbstractFilterable implements Appender {
	private final String name;
	private final boolean ignoreExceptions;
	private final Layout<? extends Serializable> layout;
	private ErrorHandler handler = new DefaultErrorHandler(this);

	protected AbstractAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
		this(name, filter, layout, true);
	}

	protected AbstractAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
		super(filter);
		this.name = (String)Objects.requireNonNull(name, "name");
		this.layout = layout;
		this.ignoreExceptions = ignoreExceptions;
	}

	public static int parseInt(String s, int defaultValue) {
		try {
			return Integers.parseInt(s, defaultValue);
		} catch (NumberFormatException var3) {
			LOGGER.error("Could not parse \"{}\" as an integer,  using default value {}: {}", s, defaultValue, var3);
			return defaultValue;
		}
	}

	public void error(String msg) {
		this.handler.error(msg);
	}

	public void error(String msg, LogEvent event, Throwable t) {
		this.handler.error(msg, event, t);
	}

	public void error(String msg, Throwable t) {
		this.handler.error(msg, t);
	}

	@Override
	public ErrorHandler getHandler() {
		return this.handler;
	}

	@Override
	public Layout<? extends Serializable> getLayout() {
		return this.layout;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean ignoreExceptions() {
		return this.ignoreExceptions;
	}

	@Override
	public void setHandler(ErrorHandler handler) {
		if (handler == null) {
			LOGGER.error("The handler cannot be set to null");
		}

		if (this.isStarted()) {
			LOGGER.error("The handler cannot be changed once the appender is started");
		} else {
			this.handler = handler;
		}
	}

	public String toString() {
		return this.name;
	}

	public abstract static class Builder<B extends AbstractAppender.Builder<B>> extends org.apache.logging.log4j.core.filter.AbstractFilterable.Builder<B> {
		@PluginBuilderAttribute
		private boolean ignoreExceptions = true;
		@PluginElement("Layout")
		private Layout<? extends Serializable> layout;
		@PluginBuilderAttribute
		@Required(
			message = "No appender name provided"
		)
		private String name;
		@PluginConfiguration
		private Configuration configuration;

		public String getName() {
			return this.name;
		}

		public boolean isIgnoreExceptions() {
			return this.ignoreExceptions;
		}

		public Layout<? extends Serializable> getLayout() {
			return this.layout;
		}

		public B withName(String name) {
			this.name = name;
			return this.asBuilder();
		}

		public B withIgnoreExceptions(boolean ignoreExceptions) {
			this.ignoreExceptions = ignoreExceptions;
			return this.asBuilder();
		}

		public B withLayout(Layout<? extends Serializable> layout) {
			this.layout = layout;
			return this.asBuilder();
		}

		public Layout<? extends Serializable> getOrCreateLayout() {
			return (Layout<? extends Serializable>)(this.layout == null ? PatternLayout.createDefaultLayout() : this.layout);
		}

		public Layout<? extends Serializable> getOrCreateLayout(Charset charset) {
			return (Layout<? extends Serializable>)(this.layout == null ? PatternLayout.newBuilder().withCharset(charset).build() : this.layout);
		}

		@Deprecated
		public B withConfiguration(Configuration configuration) {
			this.configuration = configuration;
			return this.asBuilder();
		}

		public B setConfiguration(Configuration configuration) {
			this.configuration = configuration;
			return this.asBuilder();
		}

		public Configuration getConfiguration() {
			return this.configuration;
		}
	}
}
