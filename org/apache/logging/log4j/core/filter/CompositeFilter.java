package org.apache.logging.log4j.core.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.ObjectArrayIterator;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(
	name = "filters",
	category = "Core",
	printObject = true
)
@PerformanceSensitive({"allocation"})
public final class CompositeFilter extends AbstractLifeCycle implements Iterable<Filter>, Filter {
	private static final Filter[] EMPTY_FILTERS = new Filter[0];
	private final Filter[] filters;

	private CompositeFilter() {
		this.filters = EMPTY_FILTERS;
	}

	private CompositeFilter(Filter[] filters) {
		this.filters = filters == null ? EMPTY_FILTERS : filters;
	}

	public CompositeFilter addFilter(Filter filter) {
		if (filter == null) {
			return this;
		} else if (!(filter instanceof CompositeFilter)) {
			Filter[] copy = (Filter[])Arrays.copyOf(this.filters, this.filters.length + 1);
			copy[this.filters.length] = filter;
			return new CompositeFilter(copy);
		} else {
			int size = this.filters.length + ((CompositeFilter)filter).size();
			Filter[] copy = (Filter[])Arrays.copyOf(this.filters, size);
			int index = this.filters.length;

			for (Filter currentFilter : ((CompositeFilter)filter).filters) {
				copy[index] = currentFilter;
			}

			return new CompositeFilter(copy);
		}
	}

	public CompositeFilter removeFilter(Filter filter) {
		if (filter == null) {
			return this;
		} else {
			List<Filter> filterList = new ArrayList(Arrays.asList(this.filters));
			if (filter instanceof CompositeFilter) {
				for (Filter currentFilter : ((CompositeFilter)filter).filters) {
					filterList.remove(currentFilter);
				}
			} else {
				filterList.remove(filter);
			}

			return new CompositeFilter((Filter[])filterList.toArray(new Filter[this.filters.length - 1]));
		}
	}

	public Iterator<Filter> iterator() {
		return new ObjectArrayIterator<>(this.filters);
	}

	@Deprecated
	public List<Filter> getFilters() {
		return Arrays.asList(this.filters);
	}

	public Filter[] getFiltersArray() {
		return this.filters;
	}

	public boolean isEmpty() {
		return this.filters.length == 0;
	}

	public int size() {
		return this.filters.length;
	}

	@Override
	public void start() {
		this.setStarting();

		for (Filter filter : this.filters) {
			filter.start();
		}

		this.setStarted();
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		this.setStopping();

		for (Filter filter : this.filters) {
			if (filter instanceof LifeCycle2) {
				((LifeCycle2)filter).stop(timeout, timeUnit);
			} else {
				filter.stop();
			}
		}

		this.setStopped();
		return true;
	}

	@Override
	public Result getOnMismatch() {
		return Result.NEUTRAL;
	}

	@Override
	public Result getOnMatch() {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, params);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, p0);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, p0, p1);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7
	) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6, p7);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8
	) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6, p7, p8);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
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
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, t);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(logger, level, marker, msg, t);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	@Override
	public Result filter(LogEvent event) {
		Result result = Result.NEUTRAL;

		for (int i = 0; i < this.filters.length; i++) {
			result = this.filters[i].filter(event);
			if (result == Result.ACCEPT || result == Result.DENY) {
				return result;
			}
		}

		return result;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < this.filters.length; i++) {
			if (sb.length() == 0) {
				sb.append('{');
			} else {
				sb.append(", ");
			}

			sb.append(this.filters[i].toString());
		}

		if (sb.length() > 0) {
			sb.append('}');
		}

		return sb.toString();
	}

	@PluginFactory
	public static CompositeFilter createFilters(@PluginElement("Filters") Filter[] filters) {
		return new CompositeFilter(filters);
	}
}
