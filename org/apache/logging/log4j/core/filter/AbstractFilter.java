package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.message.Message;

public abstract class AbstractFilter extends AbstractLifeCycle implements Filter {
	protected final Result onMatch;
	protected final Result onMismatch;

	protected AbstractFilter() {
		this(null, null);
	}

	protected AbstractFilter(Result onMatch, Result onMismatch) {
		this.onMatch = onMatch == null ? Result.NEUTRAL : onMatch;
		this.onMismatch = onMismatch == null ? Result.DENY : onMismatch;
	}

	@Override
	protected boolean equalsImpl(Object obj) {
		if (this == obj) {
			return true;
		} else if (!super.equalsImpl(obj)) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			AbstractFilter other = (AbstractFilter)obj;
			return this.onMatch != other.onMatch ? false : this.onMismatch == other.onMismatch;
		}
	}

	@Override
	public Result filter(LogEvent event) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
		return this.filter(logger, level, marker, msg, p0);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
		return this.filter(logger, level, marker, msg, p0, p1);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
		return this.filter(logger, level, marker, msg, p0, p1, p2);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
		return this.filter(logger, level, marker, msg, p0, p1, p2, p3);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
		return this.filter(logger, level, marker, msg, p0, p1, p2, p3, p4);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		return this.filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		return this.filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6);
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7
	) {
		return this.filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6, p7);
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8
	) {
		return this.filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6, p7, p8);
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
		return this.filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
	}

	@Override
	public final Result getOnMatch() {
		return this.onMatch;
	}

	@Override
	public final Result getOnMismatch() {
		return this.onMismatch;
	}

	@Override
	protected int hashCodeImpl() {
		int prime = 31;
		int result = super.hashCodeImpl();
		result = 31 * result + (this.onMatch == null ? 0 : this.onMatch.hashCode());
		return 31 * result + (this.onMismatch == null ? 0 : this.onMismatch.hashCode());
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}
}
