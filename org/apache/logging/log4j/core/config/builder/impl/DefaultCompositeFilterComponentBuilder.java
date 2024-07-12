package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.CompositeFilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;

class DefaultCompositeFilterComponentBuilder
	extends DefaultComponentAndConfigurationBuilder<CompositeFilterComponentBuilder>
	implements CompositeFilterComponentBuilder {
	public DefaultCompositeFilterComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String onMatch, String onMisMatch) {
		super(builder, "Filters");
		this.addAttribute("onMatch", onMatch);
		this.addAttribute("onMisMatch", onMisMatch);
	}

	public CompositeFilterComponentBuilder add(FilterComponentBuilder builder) {
		return this.addComponent(builder);
	}
}
