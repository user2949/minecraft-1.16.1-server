package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;

class DefaultLoggerComponentBuilder extends DefaultComponentAndConfigurationBuilder<LoggerComponentBuilder> implements LoggerComponentBuilder {
	public DefaultLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String name, String level) {
		super(builder, name, "Logger");
		this.addAttribute("level", level);
	}

	public DefaultLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String name, String level, boolean includeLocation) {
		super(builder, name, "Logger");
		this.addAttribute("level", level);
		this.addAttribute("includeLocation", includeLocation);
	}

	public DefaultLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String name, String level, String type) {
		super(builder, name, type);
		this.addAttribute("level", level);
	}

	public DefaultLoggerComponentBuilder(
		DefaultConfigurationBuilder<? extends Configuration> builder, String name, String level, String type, boolean includeLocation
	) {
		super(builder, name, type);
		this.addAttribute("level", level);
		this.addAttribute("includeLocation", includeLocation);
	}

	public LoggerComponentBuilder add(AppenderRefComponentBuilder builder) {
		return this.addComponent(builder);
	}

	public LoggerComponentBuilder add(FilterComponentBuilder builder) {
		return this.addComponent(builder);
	}
}
