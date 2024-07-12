package org.apache.logging.log4j.core.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.ConfigurationAware;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.apache.logging.log4j.status.StatusLogger;

public class Interpolator extends AbstractConfigurationAwareLookup {
	private static final String LOOKUP_KEY_WEB = "web";
	private static final String LOOKUP_KEY_JNDI = "jndi";
	private static final String LOOKUP_KEY_JVMRUNARGS = "jvmrunargs";
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static final char PREFIX_SEPARATOR = ':';
	private final Map<String, StrLookup> lookups = new HashMap();
	private final StrLookup defaultLookup;

	public Interpolator(StrLookup defaultLookup) {
		this(defaultLookup, null);
	}

	public Interpolator(StrLookup defaultLookup, List<String> pluginPackages) {
		this.defaultLookup = (StrLookup)(defaultLookup == null ? new MapLookup(new HashMap()) : defaultLookup);
		PluginManager manager = new PluginManager("Lookup");
		manager.collectPlugins(pluginPackages);
		Map<String, PluginType<?>> plugins = manager.getPlugins();

		for (Entry<String, PluginType<?>> entry : plugins.entrySet()) {
			try {
				Class<? extends StrLookup> clazz = ((PluginType)entry.getValue()).getPluginClass().asSubclass(StrLookup.class);
				this.lookups.put(entry.getKey(), ReflectionUtil.instantiate(clazz));
			} catch (Throwable var8) {
				this.handleError((String)entry.getKey(), var8);
			}
		}
	}

	public Interpolator() {
		this((Map<String, String>)null);
	}

	public Interpolator(Map<String, String> properties) {
		this.defaultLookup = new MapLookup((Map<String, String>)(properties == null ? new HashMap() : properties));
		this.lookups.put("log4j", new Log4jLookup());
		this.lookups.put("sys", new SystemPropertiesLookup());
		this.lookups.put("env", new EnvironmentLookup());
		this.lookups.put("main", MainMapLookup.MAIN_SINGLETON);
		this.lookups.put("marker", new MarkerLookup());
		this.lookups.put("java", new JavaLookup());

		try {
			this.lookups.put("jndi", Loader.newCheckedInstanceOf("org.apache.logging.log4j.core.lookup.JndiLookup", StrLookup.class));
		} catch (Exception | LinkageError var5) {
			this.handleError("jndi", var5);
		}

		try {
			this.lookups.put("jvmrunargs", Loader.newCheckedInstanceOf("org.apache.logging.log4j.core.lookup.JmxRuntimeInputArgumentsLookup", StrLookup.class));
		} catch (Exception | LinkageError var4) {
			this.handleError("jvmrunargs", var4);
		}

		this.lookups.put("date", new DateLookup());
		this.lookups.put("ctx", new ContextMapLookup());
		if (Loader.isClassAvailable("javax.servlet.ServletContext")) {
			try {
				this.lookups.put("web", Loader.newCheckedInstanceOf("org.apache.logging.log4j.web.WebLookup", StrLookup.class));
			} catch (Exception var3) {
				this.handleError("web", var3);
			}
		} else {
			LOGGER.debug("Not in a ServletContext environment, thus not loading WebLookup plugin.");
		}
	}

	private void handleError(String lookupKey, Throwable t) {
		switch (lookupKey) {
			case "jndi":
				LOGGER.warn(
					"JNDI lookup class is not available because this JRE does not support JNDI. JNDI string lookups will not be available, continuing configuration. Ignoring "
						+ t
				);
				break;
			case "jvmrunargs":
				LOGGER.warn(
					"JMX runtime input lookup class is not available because this JRE does not support JMX. JMX lookups will not be available, continuing configuration. Ignoring "
						+ t
				);
				break;
			case "web":
				LOGGER.info(
					"Log4j appears to be running in a Servlet environment, but there's no log4j-web module available. If you want better web container support, please add the log4j-web JAR to your web archive or server lib directory."
				);
				break;
			default:
				LOGGER.error("Unable to create Lookup for {}", lookupKey, t);
		}
	}

	@Override
	public String lookup(LogEvent event, String var) {
		if (var == null) {
			return null;
		} else {
			int prefixPos = var.indexOf(58);
			if (prefixPos >= 0) {
				String prefix = var.substring(0, prefixPos).toLowerCase(Locale.US);
				String name = var.substring(prefixPos + 1);
				StrLookup lookup = (StrLookup)this.lookups.get(prefix);
				if (lookup instanceof ConfigurationAware) {
					((ConfigurationAware)lookup).setConfiguration(this.configuration);
				}

				String value = null;
				if (lookup != null) {
					value = event == null ? lookup.lookup(name) : lookup.lookup(event, name);
				}

				if (value != null) {
					return value;
				}

				var = var.substring(prefixPos + 1);
			}

			if (this.defaultLookup != null) {
				return event == null ? this.defaultLookup.lookup(var) : this.defaultLookup.lookup(event, var);
			} else {
				return null;
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (String name : this.lookups.keySet()) {
			if (sb.length() == 0) {
				sb.append('{');
			} else {
				sb.append(", ");
			}

			sb.append(name);
		}

		if (sb.length() > 0) {
			sb.append('}');
		}

		return sb.toString();
	}
}
