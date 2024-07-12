package org.apache.logging.log4j.core.config.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationException;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;

@Plugin(
	name = "PropertiesConfigurationFactory",
	category = "ConfigurationFactory"
)
@Order(8)
public class PropertiesConfigurationFactory extends ConfigurationFactory {
	@Override
	protected String[] getSupportedTypes() {
		return new String[]{".properties"};
	}

	public PropertiesConfiguration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
		Properties properties = new Properties();

		try {
			InputStream configStream = source.getInputStream();
			Throwable var5 = null;

			try {
				properties.load(configStream);
			} catch (Throwable var15) {
				var5 = var15;
				throw var15;
			} finally {
				if (configStream != null) {
					if (var5 != null) {
						try {
							configStream.close();
						} catch (Throwable var14) {
							var5.addSuppressed(var14);
						}
					} else {
						configStream.close();
					}
				}
			}
		} catch (IOException var17) {
			throw new ConfigurationException("Unable to load " + source.toString(), var17);
		}

		return new PropertiesConfigurationBuilder().setConfigurationSource(source).setRootProperties(properties).setLoggerContext(loggerContext).build();
	}
}
