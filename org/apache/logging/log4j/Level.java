package org.apache.logging.log4j;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.spi.StandardLevel;
import org.apache.logging.log4j.util.Strings;

public final class Level implements Comparable<Level>, Serializable {
	public static final Level OFF = new Level("OFF", StandardLevel.OFF.intLevel());
	public static final Level FATAL = new Level("FATAL", StandardLevel.FATAL.intLevel());
	public static final Level ERROR = new Level("ERROR", StandardLevel.ERROR.intLevel());
	public static final Level WARN = new Level("WARN", StandardLevel.WARN.intLevel());
	public static final Level INFO = new Level("INFO", StandardLevel.INFO.intLevel());
	public static final Level DEBUG = new Level("DEBUG", StandardLevel.DEBUG.intLevel());
	public static final Level TRACE = new Level("TRACE", StandardLevel.TRACE.intLevel());
	public static final Level ALL = new Level("ALL", StandardLevel.ALL.intLevel());
	public static final String CATEGORY = "Level";
	private static final ConcurrentMap<String, Level> LEVELS = new ConcurrentHashMap();
	private static final long serialVersionUID = 1581082L;
	private final String name;
	private final int intLevel;
	private final StandardLevel standardLevel;

	private Level(String name, int intLevel) {
		if (Strings.isEmpty(name)) {
			throw new IllegalArgumentException("Illegal null or empty Level name.");
		} else if (intLevel < 0) {
			throw new IllegalArgumentException("Illegal Level int less than zero.");
		} else {
			this.name = name;
			this.intLevel = intLevel;
			this.standardLevel = StandardLevel.getStandardLevel(intLevel);
			if (LEVELS.putIfAbsent(name, this) != null) {
				throw new IllegalStateException("Level " + name + " has already been defined.");
			}
		}
	}

	public int intLevel() {
		return this.intLevel;
	}

	public StandardLevel getStandardLevel() {
		return this.standardLevel;
	}

	public boolean isInRange(Level minLevel, Level maxLevel) {
		return this.intLevel >= minLevel.intLevel && this.intLevel <= maxLevel.intLevel;
	}

	public boolean isLessSpecificThan(Level level) {
		return this.intLevel >= level.intLevel;
	}

	public boolean isMoreSpecificThan(Level level) {
		return this.intLevel <= level.intLevel;
	}

	public Level clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public int compareTo(Level other) {
		return this.intLevel < other.intLevel ? -1 : (this.intLevel > other.intLevel ? 1 : 0);
	}

	public boolean equals(Object other) {
		return other instanceof Level && other == this;
	}

	public Class<Level> getDeclaringClass() {
		return Level.class;
	}

	public int hashCode() {
		return this.name.hashCode();
	}

	public String name() {
		return this.name;
	}

	public String toString() {
		return this.name;
	}

	public static Level forName(String name, int intValue) {
		Level level = (Level)LEVELS.get(name);
		if (level != null) {
			return level;
		} else {
			try {
				return new Level(name, intValue);
			} catch (IllegalStateException var4) {
				return (Level)LEVELS.get(name);
			}
		}
	}

	public static Level getLevel(String name) {
		return (Level)LEVELS.get(name);
	}

	public static Level toLevel(String sArg) {
		return toLevel(sArg, DEBUG);
	}

	public static Level toLevel(String name, Level defaultLevel) {
		if (name == null) {
			return defaultLevel;
		} else {
			Level level = (Level)LEVELS.get(name.toUpperCase(Locale.ENGLISH));
			return level == null ? defaultLevel : level;
		}
	}

	public static Level[] values() {
		Collection<Level> values = LEVELS.values();
		return (Level[])values.toArray(new Level[values.size()]);
	}

	public static Level valueOf(String name) {
		Objects.requireNonNull(name, "No level name given.");
		String levelName = name.toUpperCase(Locale.ENGLISH);
		Level level = (Level)LEVELS.get(levelName);
		if (level != null) {
			return level;
		} else {
			throw new IllegalArgumentException("Unknown level constant [" + levelName + "].");
		}
	}

	public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
		return (T)Enum.valueOf(enumType, name);
	}

	protected Object readResolve() {
		return valueOf(this.name);
	}
}
