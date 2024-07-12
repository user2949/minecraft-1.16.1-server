package org.apache.logging.log4j.core.appender.routing;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationScheduler;
import org.apache.logging.log4j.core.config.Scheduled;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(
	name = "IdlePurgePolicy",
	category = "Core",
	printObject = true
)
@Scheduled
public class IdlePurgePolicy extends AbstractLifeCycle implements PurgePolicy, Runnable {
	private final long timeToLive;
	private final long checkInterval;
	private final ConcurrentMap<String, Long> appendersUsage = new ConcurrentHashMap();
	private RoutingAppender routingAppender;
	private final ConfigurationScheduler scheduler;
	private volatile ScheduledFuture<?> future;

	public IdlePurgePolicy(long timeToLive, long checkInterval, ConfigurationScheduler scheduler) {
		this.timeToLive = timeToLive;
		this.checkInterval = checkInterval;
		this.scheduler = scheduler;
	}

	@Override
	public void initialize(RoutingAppender routingAppender) {
		this.routingAppender = routingAppender;
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		this.setStopping();
		boolean stopped = this.stop(this.future);
		this.setStopped();
		return stopped;
	}

	@Override
	public void purge() {
		long createTime = System.currentTimeMillis() - this.timeToLive;

		for (Entry<String, Long> entry : this.appendersUsage.entrySet()) {
			if ((Long)entry.getValue() < createTime) {
				LOGGER.debug("Removing appender " + (String)entry.getKey());
				if (this.appendersUsage.remove(entry.getKey(), entry.getValue())) {
					this.routingAppender.deleteAppender((String)entry.getKey());
				}
			}
		}
	}

	@Override
	public void update(String key, LogEvent event) {
		long now = System.currentTimeMillis();
		this.appendersUsage.put(key, now);
		if (this.future == null) {
			synchronized (this) {
				if (this.future == null) {
					this.scheduleNext();
				}
			}
		}
	}

	public void run() {
		this.purge();
		this.scheduleNext();
	}

	private void scheduleNext() {
		long updateTime = Long.MAX_VALUE;

		for (Entry<String, Long> entry : this.appendersUsage.entrySet()) {
			if ((Long)entry.getValue() < updateTime) {
				updateTime = (Long)entry.getValue();
			}
		}

		if (updateTime < Long.MAX_VALUE) {
			long interval = this.timeToLive - (System.currentTimeMillis() - updateTime);
			this.future = this.scheduler.schedule(this, interval, TimeUnit.MILLISECONDS);
		} else {
			this.future = this.scheduler.schedule(this, this.checkInterval, TimeUnit.MILLISECONDS);
		}
	}

	@PluginFactory
	public static PurgePolicy createPurgePolicy(
		@PluginAttribute("timeToLive") String timeToLive,
		@PluginAttribute("checkInterval") String checkInterval,
		@PluginAttribute("timeUnit") String timeUnit,
		@PluginConfiguration Configuration configuration
	) {
		if (timeToLive == null) {
			LOGGER.error("A timeToLive value is required");
			return null;
		} else {
			TimeUnit units;
			if (timeUnit == null) {
				units = TimeUnit.MINUTES;
			} else {
				try {
					units = TimeUnit.valueOf(timeUnit.toUpperCase());
				} catch (Exception var9) {
					LOGGER.error("Invalid timeUnit value {}. timeUnit set to MINUTES", timeUnit, var9);
					units = TimeUnit.MINUTES;
				}
			}

			long ttl = units.toMillis(Long.parseLong(timeToLive));
			if (ttl < 0L) {
				LOGGER.error("timeToLive must be positive. timeToLive set to 0");
				ttl = 0L;
			}

			long ci;
			if (checkInterval == null) {
				ci = ttl;
			} else {
				ci = units.toMillis(Long.parseLong(checkInterval));
				if (ci < 0L) {
					LOGGER.error("checkInterval must be positive. checkInterval set equal to timeToLive = {}", ttl);
					ci = ttl;
				}
			}

			return new IdlePurgePolicy(ttl, ci, configuration.getScheduler());
		}
	}

	public String toString() {
		return "timeToLive=" + this.timeToLive;
	}
}
