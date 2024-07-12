package org.apache.logging.log4j.core.config;

import java.io.File;
import java.util.List;
import org.apache.logging.log4j.core.util.FileWatcher;
import org.apache.logging.log4j.core.util.Log4jThreadFactory;

public class ConfiguratonFileWatcher implements FileWatcher {
	private final Reconfigurable reconfigurable;
	private final List<ConfigurationListener> configurationListeners;
	private final Log4jThreadFactory threadFactory;

	public ConfiguratonFileWatcher(Reconfigurable reconfigurable, List<ConfigurationListener> configurationListeners) {
		this.reconfigurable = reconfigurable;
		this.configurationListeners = configurationListeners;
		this.threadFactory = Log4jThreadFactory.createDaemonThreadFactory("ConfiguratonFileWatcher");
	}

	public List<ConfigurationListener> getListeners() {
		return this.configurationListeners;
	}

	@Override
	public void fileModified(File file) {
		for (ConfigurationListener configurationListener : this.configurationListeners) {
			Thread thread = this.threadFactory.newThread(new ConfiguratonFileWatcher.ReconfigurationRunnable(configurationListener, this.reconfigurable));
			thread.start();
		}
	}

	private static class ReconfigurationRunnable implements Runnable {
		private final ConfigurationListener configurationListener;
		private final Reconfigurable reconfigurable;

		public ReconfigurationRunnable(ConfigurationListener configurationListener, Reconfigurable reconfigurable) {
			this.configurationListener = configurationListener;
			this.reconfigurable = reconfigurable;
		}

		public void run() {
			this.configurationListener.onChange(this.reconfigurable);
		}
	}
}
