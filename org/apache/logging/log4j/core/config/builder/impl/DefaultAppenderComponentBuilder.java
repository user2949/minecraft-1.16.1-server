package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;

class DefaultAppenderComponentBuilder extends DefaultComponentAndConfigurationBuilder<AppenderComponentBuilder> implements AppenderComponentBuilder {
	public DefaultAppenderComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String name, String type) {
		super(builder, name, type);
	}

	@Override
	public AppenderComponentBuilder add(LayoutComponentBuilder builder) {
		return this.addComponent(builder);
	}

	public AppenderComponentBuilder add(FilterComponentBuilder builder) {
		return this.addComponent(builder);
	}
}
