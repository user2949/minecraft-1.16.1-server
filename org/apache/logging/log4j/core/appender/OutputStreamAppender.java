package org.apache.logging.log4j.core.appender;

import java.io.OutputStream;
import java.io.Serializable;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.CloseShieldOutputStream;

@Plugin(
	name = "OutputStream",
	category = "Core",
	elementType = "appender",
	printObject = true
)
public final class OutputStreamAppender extends AbstractOutputStreamAppender<OutputStreamManager> {
	private static OutputStreamAppender.OutputStreamManagerFactory factory = new OutputStreamAppender.OutputStreamManagerFactory();

	@PluginFactory
	public static OutputStreamAppender createAppender(
		Layout<? extends Serializable> layout, Filter filter, OutputStream target, String name, boolean follow, boolean ignore
	) {
		if (name == null) {
			LOGGER.error("No name provided for OutputStreamAppender");
			return null;
		} else {
			if (layout == null) {
				layout = PatternLayout.createDefaultLayout();
			}

			return new OutputStreamAppender(name, layout, filter, getManager(target, follow, layout), ignore);
		}
	}

	private static OutputStreamManager getManager(OutputStream target, boolean follow, Layout<? extends Serializable> layout) {
		OutputStream os = new CloseShieldOutputStream(target);
		String managerName = target.getClass().getName() + "@" + Integer.toHexString(target.hashCode()) + '.' + follow;
		return OutputStreamManager.getManager(managerName, new OutputStreamAppender.FactoryData(os, managerName, layout), factory);
	}

	@PluginBuilderFactory
	public static OutputStreamAppender.Builder newBuilder() {
		return new OutputStreamAppender.Builder();
	}

	private OutputStreamAppender(String name, Layout<? extends Serializable> layout, Filter filter, OutputStreamManager manager, boolean ignoreExceptions) {
		super(name, layout, filter, ignoreExceptions, true, manager);
	}

	public static class Builder implements org.apache.logging.log4j.core.util.Builder<OutputStreamAppender> {
		private Filter filter;
		private boolean follow = false;
		private boolean ignoreExceptions = true;
		private Layout<? extends Serializable> layout = PatternLayout.createDefaultLayout();
		private String name;
		private OutputStream target;

		public OutputStreamAppender build() {
			return new OutputStreamAppender(
				this.name, this.layout, this.filter, OutputStreamAppender.getManager(this.target, this.follow, this.layout), this.ignoreExceptions
			);
		}

		public OutputStreamAppender.Builder setFilter(Filter aFilter) {
			this.filter = aFilter;
			return this;
		}

		public OutputStreamAppender.Builder setFollow(boolean shouldFollow) {
			this.follow = shouldFollow;
			return this;
		}

		public OutputStreamAppender.Builder setIgnoreExceptions(boolean shouldIgnoreExceptions) {
			this.ignoreExceptions = shouldIgnoreExceptions;
			return this;
		}

		public OutputStreamAppender.Builder setLayout(Layout<? extends Serializable> aLayout) {
			this.layout = aLayout;
			return this;
		}

		public OutputStreamAppender.Builder setName(String aName) {
			this.name = aName;
			return this;
		}

		public OutputStreamAppender.Builder setTarget(OutputStream aTarget) {
			this.target = aTarget;
			return this;
		}
	}

	private static class FactoryData {
		private final Layout<? extends Serializable> layout;
		private final String name;
		private final OutputStream os;

		public FactoryData(OutputStream os, String type, Layout<? extends Serializable> layout) {
			this.os = os;
			this.name = type;
			this.layout = layout;
		}
	}

	private static class OutputStreamManagerFactory implements ManagerFactory<OutputStreamManager, OutputStreamAppender.FactoryData> {
		private OutputStreamManagerFactory() {
		}

		public OutputStreamManager createManager(String name, OutputStreamAppender.FactoryData data) {
			return new OutputStreamManager(data.os, data.name, data.layout, true);
		}
	}
}
