package org.apache.logging.log4j.core.lookup;

import javax.naming.NamingException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.net.JndiManager;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(
	name = "jndi",
	category = "Lookup"
)
public class JndiLookup extends AbstractLookup {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static final Marker LOOKUP = MarkerManager.getMarker("LOOKUP");
	static final String CONTAINER_JNDI_RESOURCE_PATH_PREFIX = "java:comp/env/";

	@Override
	public String lookup(LogEvent event, String key) {
		if (key == null) {
			return null;
		} else {
			String jndiName = this.convertJndiName(key);

			try (JndiManager jndiManager = JndiManager.getDefaultManager()) {
				Object value = jndiManager.lookup(jndiName);
				return value == null ? null : String.valueOf(value);
			} catch (NamingException var19) {
				LOGGER.warn(LOOKUP, "Error looking up JNDI resource [{}].", jndiName, var19);
				return null;
			}
		}
	}

	private String convertJndiName(String jndiName) {
		return !jndiName.startsWith("java:comp/env/") && jndiName.indexOf(58) == -1 ? "java:comp/env/" + jndiName : jndiName;
	}
}
