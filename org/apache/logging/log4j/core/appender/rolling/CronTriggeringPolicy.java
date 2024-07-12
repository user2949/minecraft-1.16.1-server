package org.apache.logging.log4j.core.appender.rolling;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationScheduler;
import org.apache.logging.log4j.core.config.CronScheduledFuture;
import org.apache.logging.log4j.core.config.Scheduled;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.CronExpression;

@Plugin(
	name = "CronTriggeringPolicy",
	category = "Core",
	printObject = true
)
@Scheduled
public final class CronTriggeringPolicy extends AbstractTriggeringPolicy {
	private static final String defaultSchedule = "0 0 0 * * ?";
	private RollingFileManager manager;
	private final CronExpression cronExpression;
	private final Configuration configuration;
	private final boolean checkOnStartup;
	private volatile Date lastRollDate;
	private CronScheduledFuture<?> future;

	private CronTriggeringPolicy(CronExpression schedule, boolean checkOnStartup, Configuration configuration) {
		this.cronExpression = (CronExpression)Objects.requireNonNull(schedule, "schedule");
		this.configuration = (Configuration)Objects.requireNonNull(configuration, "configuration");
		this.checkOnStartup = checkOnStartup;
	}

	@Override
	public void initialize(RollingFileManager aManager) {
		this.manager = aManager;
		Date now = new Date();
		Date lastRollForFile = this.cronExpression.getPrevFireTime(new Date(this.manager.getFileTime()));
		Date lastRegularRoll = this.cronExpression.getPrevFireTime(new Date());
		aManager.getPatternProcessor().setCurrentFileTime(lastRegularRoll.getTime());
		LOGGER.debug("LastRollForFile {}, LastRegularRole {}", lastRollForFile, lastRegularRoll);
		aManager.getPatternProcessor().setPrevFileTime(lastRegularRoll.getTime());
		if (this.checkOnStartup && lastRollForFile != null && lastRegularRoll != null && lastRollForFile.before(lastRegularRoll)) {
			this.lastRollDate = lastRollForFile;
			this.rollover();
		}

		ConfigurationScheduler scheduler = this.configuration.getScheduler();
		if (!scheduler.isExecutorServiceSet()) {
			scheduler.incrementScheduledItems();
		}

		if (!scheduler.isStarted()) {
			scheduler.start();
		}

		this.lastRollDate = lastRegularRoll;
		this.future = scheduler.scheduleWithCron(this.cronExpression, now, new CronTriggeringPolicy.CronTrigger());
		LOGGER.debug(scheduler.toString());
	}

	@Override
	public boolean isTriggeringEvent(LogEvent event) {
		return false;
	}

	public CronExpression getCronExpression() {
		return this.cronExpression;
	}

	@PluginFactory
	public static CronTriggeringPolicy createPolicy(
		@PluginConfiguration Configuration configuration,
		@PluginAttribute("evaluateOnStartup") String evaluateOnStartup,
		@PluginAttribute("schedule") String schedule
	) {
		boolean checkOnStartup = Boolean.parseBoolean(evaluateOnStartup);
		CronExpression cronExpression;
		if (schedule == null) {
			LOGGER.info("No schedule specified, defaulting to Daily");
			cronExpression = getSchedule("0 0 0 * * ?");
		} else {
			cronExpression = getSchedule(schedule);
			if (cronExpression == null) {
				LOGGER.error("Invalid expression specified. Defaulting to Daily");
				cronExpression = getSchedule("0 0 0 * * ?");
			}
		}

		return new CronTriggeringPolicy(cronExpression, checkOnStartup, configuration);
	}

	private static CronExpression getSchedule(String expression) {
		try {
			return new CronExpression(expression);
		} catch (ParseException var2) {
			LOGGER.error("Invalid cron expression - " + expression, (Throwable)var2);
			return null;
		}
	}

	private void rollover() {
		this.manager.getPatternProcessor().setPrevFileTime(this.lastRollDate.getTime());
		Date thisRoll = this.cronExpression.getPrevFireTime(new Date());
		this.manager.getPatternProcessor().setCurrentFileTime(thisRoll.getTime());
		this.manager.rollover();
		if (this.future != null) {
			this.lastRollDate = this.future.getFireTime();
		}
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		this.setStopping();
		boolean stopped = this.stop(this.future);
		this.setStopped();
		return stopped;
	}

	public String toString() {
		return "CronTriggeringPolicy(schedule=" + this.cronExpression.getCronExpression() + ")";
	}

	private class CronTrigger implements Runnable {
		private CronTrigger() {
		}

		public void run() {
			CronTriggeringPolicy.this.rollover();
		}
	}
}
