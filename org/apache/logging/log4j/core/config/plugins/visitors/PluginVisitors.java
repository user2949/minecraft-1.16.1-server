package org.apache.logging.log4j.core.config.plugins.visitors;

import java.lang.annotation.Annotation;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.PluginVisitorStrategy;
import org.apache.logging.log4j.status.StatusLogger;

public final class PluginVisitors {
	private static final Logger LOGGER = StatusLogger.getLogger();

	private PluginVisitors() {
	}

	public static PluginVisitor<? extends Annotation> findVisitor(Class<? extends Annotation> annotation) {
		PluginVisitorStrategy strategy = (PluginVisitorStrategy)annotation.getAnnotation(PluginVisitorStrategy.class);
		if (strategy == null) {
			return null;
		} else {
			try {
				return (PluginVisitor<? extends Annotation>)strategy.value().newInstance();
			} catch (Exception var3) {
				LOGGER.error("Error loading PluginVisitor [{}] for annotation [{}].", strategy.value(), annotation, var3);
				return null;
			}
		}
	}
}
