package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;

class DefaultFilterComponentBuilder extends DefaultComponentAndConfigurationBuilder<FilterComponentBuilder> implements FilterComponentBuilder {
	public DefaultFilterComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String type, String onMatch, String onMisMatch) {
		super(builder, type);
		this.addAttribute("onMatch", onMatch);
		this.addAttribute("onMisMatch", onMisMatch);
	}
}
