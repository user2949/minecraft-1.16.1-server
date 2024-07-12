package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(
	name = "CountingNoOp",
	category = "Core",
	elementType = "appender",
	printObject = true
)
public class CountingNoOpAppender extends AbstractAppender {
	private final AtomicLong total = new AtomicLong();

	public CountingNoOpAppender(String name, Layout<?> layout) {
		super(name, null, (Layout<? extends Serializable>)layout);
	}

	public long getCount() {
		return this.total.get();
	}

	@Override
	public void append(LogEvent event) {
		this.total.incrementAndGet();
	}

	@PluginFactory
	public static CountingNoOpAppender createAppender(@PluginAttribute("name") String name) {
		return new CountingNoOpAppender((String)Objects.requireNonNull(name), null);
	}
}
