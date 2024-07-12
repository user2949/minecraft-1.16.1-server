package org.apache.logging.log4j.core.filter;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.config.plugins.PluginElement;

public abstract class AbstractFilterable extends AbstractLifeCycle implements Filterable {
	private volatile Filter filter;

	protected AbstractFilterable(Filter filter) {
		this.filter = filter;
	}

	protected AbstractFilterable() {
	}

	@Override
	public Filter getFilter() {
		return this.filter;
	}

	@Override
	public synchronized void addFilter(Filter filter) {
		if (filter != null) {
			if (this.filter == null) {
				this.filter = filter;
			} else if (this.filter instanceof CompositeFilter) {
				this.filter = ((CompositeFilter)this.filter).addFilter(filter);
			} else {
				Filter[] filters = new Filter[]{this.filter, filter};
				this.filter = CompositeFilter.createFilters(filters);
			}
		}
	}

	@Override
	public synchronized void removeFilter(Filter filter) {
		if (this.filter != null && filter != null) {
			if (this.filter == filter || this.filter.equals(filter)) {
				this.filter = null;
			} else if (this.filter instanceof CompositeFilter) {
				CompositeFilter composite = (CompositeFilter)this.filter;
				composite = composite.removeFilter(filter);
				if (composite.size() > 1) {
					this.filter = composite;
				} else if (composite.size() == 1) {
					Iterator<Filter> iter = composite.iterator();
					this.filter = (Filter)iter.next();
				} else {
					this.filter = null;
				}
			}
		}
	}

	@Override
	public boolean hasFilter() {
		return this.filter != null;
	}

	@Override
	public void start() {
		this.setStarting();
		if (this.filter != null) {
			this.filter.start();
		}

		this.setStarted();
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		return this.stop(timeout, timeUnit, true);
	}

	protected boolean stop(long timeout, TimeUnit timeUnit, boolean changeLifeCycleState) {
		if (changeLifeCycleState) {
			this.setStopping();
		}

		boolean stopped = true;
		if (this.filter != null) {
			if (this.filter instanceof LifeCycle2) {
				stopped = ((LifeCycle2)this.filter).stop(timeout, timeUnit);
			} else {
				this.filter.stop();
				stopped = true;
			}
		}

		if (changeLifeCycleState) {
			this.setStopped();
		}

		return stopped;
	}

	@Override
	public boolean isFiltered(LogEvent event) {
		return this.filter != null && this.filter.filter(event) == Result.DENY;
	}

	public abstract static class Builder<B extends AbstractFilterable.Builder<B>> {
		@PluginElement("Filter")
		private Filter filter;

		public Filter getFilter() {
			return this.filter;
		}

		public B asBuilder() {
			return (B)this;
		}

		public B withFilter(Filter filter) {
			this.filter = filter;
			return this.asBuilder();
		}
	}
}
