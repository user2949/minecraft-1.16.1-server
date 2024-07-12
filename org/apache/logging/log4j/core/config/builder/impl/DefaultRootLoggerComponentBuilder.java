package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;

class DefaultRootLoggerComponentBuilder extends DefaultComponentAndConfigurationBuilder<RootLoggerComponentBuilder> implements RootLoggerComponentBuilder {
	public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String level) {
		super(builder, "", "Root");
		this.addAttribute("level", level);
	}

	public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String level, boolean includeLocation) {
		super(builder, "", "Root");
		this.addAttribute("level", level);
		this.addAttribute("includeLocation", includeLocation);
	}

	public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String level, String type) {
		super(builder, "", type);
		this.addAttribute("level", level);
	}

	public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String level, String type, boolean includeLocation) {
		super(builder, "", type);
		this.addAttribute("level", level);
		this.addAttribute("includeLocation", includeLocation);
	}

	public RootLoggerComponentBuilder add(AppenderRefComponentBuilder builder) {
		return this.addComponent(builder);
	}

	public RootLoggerComponentBuilder add(FilterComponentBuilder builder) {
		return this.addComponent(builder);
	}
}
