package org.apache.logging.log4j.spi;

import java.util.EnumSet;

public enum StandardLevel {
	OFF(0),
	FATAL(100),
	ERROR(200),
	WARN(300),
	INFO(400),
	DEBUG(500),
	TRACE(600),
	ALL(Integer.MAX_VALUE);

	private static final EnumSet<StandardLevel> LEVELSET = EnumSet.allOf(StandardLevel.class);
	private final int intLevel;

	private StandardLevel(int val) {
		this.intLevel = val;
	}

	public int intLevel() {
		return this.intLevel;
	}

	public static StandardLevel getStandardLevel(int intLevel) {
		StandardLevel level = OFF;

		for (StandardLevel lvl : LEVELSET) {
			if (lvl.intLevel() > intLevel) {
				break;
			}

			level = lvl;
		}

		return level;
	}
}
