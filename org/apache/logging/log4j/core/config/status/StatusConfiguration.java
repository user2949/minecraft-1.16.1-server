package org.apache.logging.log4j.core.config.status;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.status.StatusConsoleListener;
import org.apache.logging.log4j.status.StatusListener;
import org.apache.logging.log4j.status.StatusLogger;

public class StatusConfiguration {
	private static final PrintStream DEFAULT_STREAM = System.out;
	private static final Level DEFAULT_STATUS = Level.ERROR;
	private static final StatusConfiguration.Verbosity DEFAULT_VERBOSITY = StatusConfiguration.Verbosity.QUIET;
	private final Collection<String> errorMessages = Collections.synchronizedCollection(new LinkedList());
	private final StatusLogger logger = StatusLogger.getLogger();
	private volatile boolean initialized = false;
	private PrintStream destination = DEFAULT_STREAM;
	private Level status = DEFAULT_STATUS;
	private StatusConfiguration.Verbosity verbosity = DEFAULT_VERBOSITY;
	private String[] verboseClasses;

	public void error(String message) {
		if (!this.initialized) {
			this.errorMessages.add(message);
		} else {
			this.logger.error(message);
		}
	}

	public StatusConfiguration withDestination(String destination) {
		try {
			this.destination = this.parseStreamName(destination);
		} catch (URISyntaxException var3) {
			this.error("Could not parse URI [" + destination + "]. Falling back to default of stdout.");
			this.destination = DEFAULT_STREAM;
		} catch (FileNotFoundException var4) {
			this.error("File could not be found at [" + destination + "]. Falling back to default of stdout.");
			this.destination = DEFAULT_STREAM;
		}

		return this;
	}

	private PrintStream parseStreamName(String name) throws URISyntaxException, FileNotFoundException {
		if (name == null || name.equalsIgnoreCase("out")) {
			return DEFAULT_STREAM;
		} else if (name.equalsIgnoreCase("err")) {
			return System.err;
		} else {
			URI destUri = NetUtils.toURI(name);
			File output = FileUtils.fileFromUri(destUri);
			if (output == null) {
				return DEFAULT_STREAM;
			} else {
				FileOutputStream fos = new FileOutputStream(output);
				return new PrintStream(fos, true);
			}
		}
	}

	public StatusConfiguration withStatus(String status) {
		this.status = Level.toLevel(status, null);
		if (this.status == null) {
			this.error("Invalid status level specified: " + status + ". Defaulting to ERROR.");
			this.status = Level.ERROR;
		}

		return this;
	}

	public StatusConfiguration withStatus(Level status) {
		this.status = status;
		return this;
	}

	public StatusConfiguration withVerbosity(String verbosity) {
		this.verbosity = StatusConfiguration.Verbosity.toVerbosity(verbosity);
		return this;
	}

	public StatusConfiguration withVerboseClasses(String... verboseClasses) {
		this.verboseClasses = verboseClasses;
		return this;
	}

	public void initialize() {
		if (!this.initialized) {
			if (this.status == Level.OFF) {
				this.initialized = true;
			} else {
				boolean configured = this.configureExistingStatusConsoleListener();
				if (!configured) {
					this.registerNewStatusConsoleListener();
				}

				this.migrateSavedLogMessages();
			}
		}
	}

	private boolean configureExistingStatusConsoleListener() {
		boolean configured = false;

		for (StatusListener statusListener : this.logger.getListeners()) {
			if (statusListener instanceof StatusConsoleListener) {
				StatusConsoleListener listener = (StatusConsoleListener)statusListener;
				listener.setLevel(this.status);
				this.logger.updateListenerLevel(this.status);
				if (this.verbosity == StatusConfiguration.Verbosity.QUIET) {
					listener.setFilters(this.verboseClasses);
				}

				configured = true;
			}
		}

		return configured;
	}

	private void registerNewStatusConsoleListener() {
		StatusConsoleListener listener = new StatusConsoleListener(this.status, this.destination);
		if (this.verbosity == StatusConfiguration.Verbosity.QUIET) {
			listener.setFilters(this.verboseClasses);
		}

		this.logger.registerListener(listener);
	}

	private void migrateSavedLogMessages() {
		for (String message : this.errorMessages) {
			this.logger.error(message);
		}

		this.initialized = true;
		this.errorMessages.clear();
	}

	public static enum Verbosity {
		QUIET,
		VERBOSE;

		public static StatusConfiguration.Verbosity toVerbosity(String value) {
			return Boolean.parseBoolean(value) ? VERBOSE : QUIET;
		}
	}
}
