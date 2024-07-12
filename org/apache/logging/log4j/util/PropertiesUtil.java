package org.apache.logging.log4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public final class PropertiesUtil {
	private static final PropertiesUtil LOG4J_PROPERTIES = new PropertiesUtil("log4j2.component.properties");
	private final Properties props;

	public PropertiesUtil(Properties props) {
		this.props = props;
	}

	public PropertiesUtil(String propertiesFileName) {
		Properties properties = new Properties();

		for (URL url : LoaderUtil.findResources(propertiesFileName)) {
			try {
				InputStream in = url.openStream();
				Throwable var6 = null;

				try {
					properties.load(in);
				} catch (Throwable var16) {
					var6 = var16;
					throw var16;
				} finally {
					if (in != null) {
						if (var6 != null) {
							try {
								in.close();
							} catch (Throwable var15) {
								var6.addSuppressed(var15);
							}
						} else {
							in.close();
						}
					}
				}
			} catch (IOException var18) {
				LowLevelLogUtil.logException("Unable to read " + url.toString(), var18);
			}
		}

		this.props = properties;
	}

	static Properties loadClose(InputStream in, Object source) {
		Properties props = new Properties();
		if (null != in) {
			try {
				props.load(in);
			} catch (IOException var12) {
				LowLevelLogUtil.logException("Unable to read " + source, var12);
			} finally {
				try {
					in.close();
				} catch (IOException var11) {
					LowLevelLogUtil.logException("Unable to close " + source, var11);
				}
			}
		}

		return props;
	}

	public static PropertiesUtil getProperties() {
		return LOG4J_PROPERTIES;
	}

	public boolean getBooleanProperty(String name) {
		return this.getBooleanProperty(name, false);
	}

	public boolean getBooleanProperty(String name, boolean defaultValue) {
		String prop = this.getStringProperty(name);
		return prop == null ? defaultValue : "true".equalsIgnoreCase(prop);
	}

	public Charset getCharsetProperty(String name) {
		return this.getCharsetProperty(name, Charset.defaultCharset());
	}

	public Charset getCharsetProperty(String name, Charset defaultValue) {
		String prop = this.getStringProperty(name);
		return prop == null ? defaultValue : Charset.forName(prop);
	}

	public double getDoubleProperty(String name, double defaultValue) {
		String prop = this.getStringProperty(name);
		if (prop != null) {
			try {
				return Double.parseDouble(prop);
			} catch (Exception var6) {
				return defaultValue;
			}
		} else {
			return defaultValue;
		}
	}

	public int getIntegerProperty(String name, int defaultValue) {
		String prop = this.getStringProperty(name);
		if (prop != null) {
			try {
				return Integer.parseInt(prop);
			} catch (Exception var5) {
				return defaultValue;
			}
		} else {
			return defaultValue;
		}
	}

	public long getLongProperty(String name, long defaultValue) {
		String prop = this.getStringProperty(name);
		if (prop != null) {
			try {
				return Long.parseLong(prop);
			} catch (Exception var6) {
				return defaultValue;
			}
		} else {
			return defaultValue;
		}
	}

	public String getStringProperty(String name) {
		String prop = null;

		try {
			prop = System.getProperty(name);
		} catch (SecurityException var4) {
		}

		return prop == null ? this.props.getProperty(name) : prop;
	}

	public String getStringProperty(String name, String defaultValue) {
		String prop = this.getStringProperty(name);
		return prop == null ? defaultValue : prop;
	}

	public static Properties getSystemProperties() {
		try {
			return new Properties(System.getProperties());
		} catch (SecurityException var1) {
			LowLevelLogUtil.logException("Unable to access system properties.", var1);
			return new Properties();
		}
	}

	public static Properties extractSubset(Properties properties, String prefix) {
		Properties subset = new Properties();
		if (prefix != null && prefix.length() != 0) {
			String prefixToMatch = prefix.charAt(prefix.length() - 1) != '.' ? prefix + '.' : prefix;
			List<String> keys = new ArrayList();

			for (String key : properties.stringPropertyNames()) {
				if (key.startsWith(prefixToMatch)) {
					subset.setProperty(key.substring(prefixToMatch.length()), properties.getProperty(key));
					keys.add(key);
				}
			}

			for (String keyx : keys) {
				properties.remove(keyx);
			}

			return subset;
		} else {
			return subset;
		}
	}

	public static Map<String, Properties> partitionOnCommonPrefixes(Properties properties) {
		Map<String, Properties> parts = new ConcurrentHashMap();

		for (String key : properties.stringPropertyNames()) {
			String prefix = key.substring(0, key.indexOf(46));
			if (!parts.containsKey(prefix)) {
				parts.put(prefix, new Properties());
			}

			((Properties)parts.get(prefix)).setProperty(key.substring(key.indexOf(46) + 1), properties.getProperty(key));
		}

		return parts;
	}

	public boolean isOsWindows() {
		return this.getStringProperty("os.name").startsWith("Windows");
	}
}
