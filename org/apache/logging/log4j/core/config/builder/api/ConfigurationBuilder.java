package org.apache.logging.log4j.core.config.builder.api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.util.Builder;

public interface ConfigurationBuilder<T extends Configuration> extends Builder<T> {
	ConfigurationBuilder<T> add(ScriptComponentBuilder scriptComponentBuilder);

	ConfigurationBuilder<T> add(ScriptFileComponentBuilder scriptFileComponentBuilder);

	ConfigurationBuilder<T> add(AppenderComponentBuilder appenderComponentBuilder);

	ConfigurationBuilder<T> add(CustomLevelComponentBuilder customLevelComponentBuilder);

	ConfigurationBuilder<T> add(FilterComponentBuilder filterComponentBuilder);

	ConfigurationBuilder<T> add(LoggerComponentBuilder loggerComponentBuilder);

	ConfigurationBuilder<T> add(RootLoggerComponentBuilder rootLoggerComponentBuilder);

	ConfigurationBuilder<T> addProperty(String string1, String string2);

	ScriptComponentBuilder newScript(String string1, String string2, String string3);

	ScriptFileComponentBuilder newScriptFile(String string);

	ScriptFileComponentBuilder newScriptFile(String string1, String string2);

	AppenderComponentBuilder newAppender(String string1, String string2);

	AppenderRefComponentBuilder newAppenderRef(String string);

	LoggerComponentBuilder newAsyncLogger(String string, Level level);

	LoggerComponentBuilder newAsyncLogger(String string, Level level, boolean boolean3);

	LoggerComponentBuilder newAsyncLogger(String string1, String string2);

	LoggerComponentBuilder newAsyncLogger(String string1, String string2, boolean boolean3);

	RootLoggerComponentBuilder newAsyncRootLogger(Level level);

	RootLoggerComponentBuilder newAsyncRootLogger(Level level, boolean boolean2);

	RootLoggerComponentBuilder newAsyncRootLogger(String string);

	RootLoggerComponentBuilder newAsyncRootLogger(String string, boolean boolean2);

	<B extends ComponentBuilder<B>> ComponentBuilder<B> newComponent(String string);

	<B extends ComponentBuilder<B>> ComponentBuilder<B> newComponent(String string1, String string2);

	<B extends ComponentBuilder<B>> ComponentBuilder<B> newComponent(String string1, String string2, String string3);

	CustomLevelComponentBuilder newCustomLevel(String string, int integer);

	FilterComponentBuilder newFilter(String string, Result result2, Result result3);

	FilterComponentBuilder newFilter(String string1, String string2, String string3);

	LayoutComponentBuilder newLayout(String string);

	LoggerComponentBuilder newLogger(String string, Level level);

	LoggerComponentBuilder newLogger(String string, Level level, boolean boolean3);

	LoggerComponentBuilder newLogger(String string1, String string2);

	LoggerComponentBuilder newLogger(String string1, String string2, boolean boolean3);

	RootLoggerComponentBuilder newRootLogger(Level level);

	RootLoggerComponentBuilder newRootLogger(Level level, boolean boolean2);

	RootLoggerComponentBuilder newRootLogger(String string);

	RootLoggerComponentBuilder newRootLogger(String string, boolean boolean2);

	ConfigurationBuilder<T> setAdvertiser(String string);

	ConfigurationBuilder<T> setConfigurationName(String string);

	ConfigurationBuilder<T> setConfigurationSource(ConfigurationSource configurationSource);

	ConfigurationBuilder<T> setMonitorInterval(String string);

	ConfigurationBuilder<T> setPackages(String string);

	ConfigurationBuilder<T> setShutdownHook(String string);

	ConfigurationBuilder<T> setShutdownTimeout(long long1, TimeUnit timeUnit);

	ConfigurationBuilder<T> setStatusLevel(Level level);

	ConfigurationBuilder<T> setVerbosity(String string);

	ConfigurationBuilder<T> setDestination(String string);

	void setLoggerContext(LoggerContext loggerContext);

	ConfigurationBuilder<T> addRootProperty(String string1, String string2);

	T build(boolean boolean1);

	void writeXmlConfiguration(OutputStream outputStream) throws IOException;

	String toXmlConfiguration();
}
