package org.apache.logging.log4j.core.jmx;

import java.util.Objects;
import javax.management.ObjectName;
import org.apache.logging.log4j.core.selector.ContextSelector;

public class ContextSelectorAdmin implements ContextSelectorAdminMBean {
	private final ObjectName objectName;
	private final ContextSelector selector;

	public ContextSelectorAdmin(String contextName, ContextSelector selector) {
		this.selector = (ContextSelector)Objects.requireNonNull(selector, "ContextSelector");

		try {
			String mbeanName = String.format("org.apache.logging.log4j2:type=%s,component=ContextSelector", Server.escape(contextName));
			this.objectName = new ObjectName(mbeanName);
		} catch (Exception var4) {
			throw new IllegalStateException(var4);
		}
	}

	public ObjectName getObjectName() {
		return this.objectName;
	}

	@Override
	public String getImplementationClassName() {
		return this.selector.getClass().getName();
	}
}
