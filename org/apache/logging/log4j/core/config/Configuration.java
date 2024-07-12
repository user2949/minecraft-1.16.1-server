package org.apache.logging.log4j.core.config;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDelegate;
import org.apache.logging.log4j.core.filter.Filterable;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.script.ScriptManager;
import org.apache.logging.log4j.core.util.NanoClock;
import org.apache.logging.log4j.core.util.WatchManager;

public interface Configuration extends Filterable {
	String CONTEXT_PROPERTIES = "ContextProperties";

	String getName();

	LoggerConfig getLoggerConfig(String string);

	<T extends Appender> T getAppender(String string);

	Map<String, Appender> getAppenders();

	void addAppender(Appender appender);

	Map<String, LoggerConfig> getLoggers();

	void addLoggerAppender(Logger logger, Appender appender);

	void addLoggerFilter(Logger logger, Filter filter);

	void setLoggerAdditive(Logger logger, boolean boolean2);

	void addLogger(String string, LoggerConfig loggerConfig);

	void removeLogger(String string);

	List<String> getPluginPackages();

	Map<String, String> getProperties();

	LoggerConfig getRootLogger();

	void addListener(ConfigurationListener configurationListener);

	void removeListener(ConfigurationListener configurationListener);

	StrSubstitutor getStrSubstitutor();

	void createConfiguration(Node node, LogEvent logEvent);

	<T> T getComponent(String string);

	void addComponent(String string, Object object);

	void setAdvertiser(Advertiser advertiser);

	Advertiser getAdvertiser();

	boolean isShutdownHookEnabled();

	long getShutdownTimeoutMillis();

	ConfigurationScheduler getScheduler();

	ConfigurationSource getConfigurationSource();

	List<CustomLevelConfig> getCustomLevels();

	ScriptManager getScriptManager();

	AsyncLoggerConfigDelegate getAsyncLoggerConfigDelegate();

	WatchManager getWatchManager();

	ReliabilityStrategy getReliabilityStrategy(LoggerConfig loggerConfig);

	NanoClock getNanoClock();

	void setNanoClock(NanoClock nanoClock);

	LoggerContext getLoggerContext();
}
