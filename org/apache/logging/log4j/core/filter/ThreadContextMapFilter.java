package org.apache.logging.log4j.core.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.ContextDataInjectorFactory;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.IndexedReadOnlyStringMap;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

@Plugin(
	name = "ThreadContextMapFilter",
	category = "Core",
	elementType = "filter",
	printObject = true
)
@PluginAliases({"ContextMapFilter"})
@PerformanceSensitive({"allocation"})
public class ThreadContextMapFilter extends MapFilter {
	private final ContextDataInjector injector = ContextDataInjectorFactory.createInjector();
	private final String key;
	private final String value;
	private final boolean useMap;

	public ThreadContextMapFilter(Map<String, List<String>> pairs, boolean oper, Result onMatch, Result onMismatch) {
		super(pairs, oper, onMatch, onMismatch);
		if (pairs.size() == 1) {
			Iterator<Entry<String, List<String>>> iter = pairs.entrySet().iterator();
			Entry<String, List<String>> entry = (Entry<String, List<String>>)iter.next();
			if (((List)entry.getValue()).size() == 1) {
				this.key = (String)entry.getKey();
				this.value = (String)((List)entry.getValue()).get(0);
				this.useMap = false;
			} else {
				this.key = null;
				this.value = null;
				this.useMap = true;
			}
		} else {
			this.key = null;
			this.value = null;
			this.useMap = true;
		}
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
		return this.filter();
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
		return this.filter();
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
		return this.filter();
	}

	private Result filter() {
		boolean match = false;
		if (this.useMap) {
			ReadOnlyStringMap currentContextData = null;
			IndexedReadOnlyStringMap map = this.getStringMap();

			for (int i = 0; i < map.size(); i++) {
				if (currentContextData == null) {
					currentContextData = this.currentContextData();
				}

				String toMatch = currentContextData.getValue(map.getKeyAt(i));
				match = toMatch != null && map.<List>getValueAt(i).contains(toMatch);
				if (!this.isAnd() && match || this.isAnd() && !match) {
					break;
				}
			}
		} else {
			match = this.value.equals(this.currentContextData().getValue(this.key));
		}

		return match ? this.onMatch : this.onMismatch;
	}

	private ReadOnlyStringMap currentContextData() {
		return this.injector.rawContextData();
	}

	@Override
	public Result filter(LogEvent event) {
		return super.filter(event.getContextData()) ? this.onMatch : this.onMismatch;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
		return this.filter();
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
		return this.filter();
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
		return this.filter();
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
		return this.filter();
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
		return this.filter();
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		return this.filter();
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		return this.filter();
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7
	) {
		return this.filter();
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8
	) {
		return this.filter();
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
		return this.filter();
	}

	@PluginFactory
	public static ThreadContextMapFilter createFilter(
		@PluginElement("Pairs") KeyValuePair[] pairs,
		@PluginAttribute("operator") String oper,
		@PluginAttribute("onMatch") Result match,
		@PluginAttribute("onMismatch") Result mismatch
	) {
		if (pairs != null && pairs.length != 0) {
			Map<String, List<String>> map = new HashMap();

			for (KeyValuePair pair : pairs) {
				String key = pair.getKey();
				if (key == null) {
					LOGGER.error("A null key is not valid in MapFilter");
				} else {
					String value = pair.getValue();
					if (value == null) {
						LOGGER.error("A null value for key " + key + " is not allowed in MapFilter");
					} else {
						List<String> list = (List<String>)map.get(pair.getKey());
						if (list != null) {
							list.add(value);
						} else {
							List<String> var13 = new ArrayList();
							var13.add(value);
							map.put(pair.getKey(), var13);
						}
					}
				}
			}

			if (map.isEmpty()) {
				LOGGER.error("ThreadContextMapFilter is not configured with any valid key value pairs");
				return null;
			} else {
				boolean isAnd = oper == null || !oper.equalsIgnoreCase("or");
				return new ThreadContextMapFilter(map, isAnd, match, mismatch);
			}
		} else {
			LOGGER.error("key and value pairs must be specified for the ThreadContextMapFilter");
			return null;
		}
	}
}
