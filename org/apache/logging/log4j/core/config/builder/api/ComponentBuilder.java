package org.apache.logging.log4j.core.config.builder.api;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.util.Builder;

public interface ComponentBuilder<T extends ComponentBuilder<T>> extends Builder<Component> {
	T addAttribute(String string1, String string2);

	T addAttribute(String string, Level level);

	T addAttribute(String string, Enum<?> enum2);

	T addAttribute(String string, int integer);

	T addAttribute(String string, boolean boolean2);

	T addAttribute(String string, Object object);

	T addComponent(ComponentBuilder<?> componentBuilder);

	String getName();

	ConfigurationBuilder<? extends Configuration> getBuilder();
}
