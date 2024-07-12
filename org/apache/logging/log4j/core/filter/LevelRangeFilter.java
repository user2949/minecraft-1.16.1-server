package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(
	name = "LevelRangeFilter",
	category = "Core",
	elementType = "filter",
	printObject = true
)
@PerformanceSensitive({"allocation"})
public final class LevelRangeFilter extends AbstractFilter {
	private final Level maxLevel;
	private final Level minLevel;

	@PluginFactory
	public static LevelRangeFilter createFilter(
		@PluginAttribute("minLevel") Level minLevel,
		@PluginAttribute("maxLevel") Level maxLevel,
		@PluginAttribute("onMatch") Result match,
		@PluginAttribute("onMismatch") Result mismatch
	) {
		Level actualMinLevel = minLevel == null ? Level.ERROR : minLevel;
		Level actualMaxLevel = maxLevel == null ? Level.ERROR : maxLevel;
		Result onMatch = match == null ? Result.NEUTRAL : match;
		Result onMismatch = mismatch == null ? Result.DENY : mismatch;
		return new LevelRangeFilter(actualMinLevel, actualMaxLevel, onMatch, onMismatch);
	}

	private LevelRangeFilter(Level minLevel, Level maxLevel, Result onMatch, Result onMismatch) {
		super(onMatch, onMismatch);
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
	}

	private Result filter(Level level) {
		return level.isInRange(this.minLevel, this.maxLevel) ? this.onMatch : this.onMismatch;
	}

	@Override
	public Result filter(LogEvent event) {
		return this.filter(event.getLevel());
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		return this.filter(level);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		return this.filter(level);
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7
	) {
		return this.filter(level);
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8
	) {
		return this.filter(level);
	}

	@Override
	public Result filter(
		Logger logger,
		Level level,
		Marker marker,
		String msg,
		Object p0,
		Object p1,
		Object p2,
		Object p3,
		Object p4,
		Object p5,
		Object p6,
		Object p7,
		Object p8,
		Object p9
	) {
		return this.filter(level);
	}

	public Level getMinLevel() {
		return this.minLevel;
	}

	@Override
	public String toString() {
		return this.minLevel.toString();
	}
}
