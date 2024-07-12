package org.apache.logging.log4j.core.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.message.MapMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.IndexedReadOnlyStringMap;
import org.apache.logging.log4j.util.IndexedStringMap;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.SortedArrayStringMap;

@Plugin(
	name = "MapFilter",
	category = "Core",
	elementType = "filter",
	printObject = true
)
@PerformanceSensitive({"allocation"})
public class MapFilter extends AbstractFilter {
	private final IndexedStringMap map;
	private final boolean isAnd;

	protected MapFilter(Map<String, List<String>> map, boolean oper, Result onMatch, Result onMismatch) {
		super(onMatch, onMismatch);
		this.isAnd = oper;
		Objects.requireNonNull(map, "map cannot be null");
		this.map = new SortedArrayStringMap(map.size());

		for (Entry<String, List<String>> entry : map.entrySet()) {
			this.map.putValue((String)entry.getKey(), entry.getValue());
		}
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
		if (msg instanceof MapMessage) {
			return this.filter((MapMessage)msg) ? this.onMatch : this.onMismatch;
		} else {
			return Result.NEUTRAL;
		}
	}

	@Override
	public Result filter(LogEvent event) {
		Message msg = event.getMessage();
		if (msg instanceof MapMessage) {
			return this.filter((MapMessage)msg) ? this.onMatch : this.onMismatch;
		} else {
			return Result.NEUTRAL;
		}
	}

	protected boolean filter(MapMessage mapMessage) {
		boolean match = false;

		for (int i = 0; i < this.map.size(); i++) {
			String toMatch = mapMessage.get(this.map.getKeyAt(i));
			match = toMatch != null && this.map.<List>getValueAt(i).contains(toMatch);
			if (!this.isAnd && match || this.isAnd && !match) {
				break;
			}
		}

		return match;
	}

	protected boolean filter(Map<String, String> data) {
		boolean match = false;

		for (int i = 0; i < this.map.size(); i++) {
			String toMatch = (String)data.get(this.map.getKeyAt(i));
			match = toMatch != null && this.map.<List>getValueAt(i).contains(toMatch);
			if (!this.isAnd && match || this.isAnd && !match) {
				break;
			}
		}

		return match;
	}

	protected boolean filter(ReadOnlyStringMap data) {
		boolean match = false;

		for (int i = 0; i < this.map.size(); i++) {
			String toMatch = data.getValue(this.map.getKeyAt(i));
			match = toMatch != null && this.map.<List>getValueAt(i).contains(toMatch);
			if (!this.isAnd && match || this.isAnd && !match) {
				break;
			}
		}

		return match;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7
	) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(
		Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8
	) {
		return Result.NEUTRAL;
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
		return Result.NEUTRAL;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("isAnd=").append(this.isAnd);
		if (this.map.size() > 0) {
			sb.append(", {");

			for (int i = 0; i < this.map.size(); i++) {
				if (i > 0) {
					sb.append(", ");
				}

				List<String> list = this.map.getValueAt(i);
				String value = list.size() > 1 ? (String)list.get(0) : list.toString();
				sb.append(this.map.getKeyAt(i)).append('=').append(value);
			}

			sb.append('}');
		}

		return sb.toString();
	}

	protected boolean isAnd() {
		return this.isAnd;
	}

	@Deprecated
	protected Map<String, List<String>> getMap() {
		final Map<String, List<String>> result = new HashMap(this.map.size());
		this.map.forEach(new BiConsumer<String, List<String>>() {
			public void accept(String key, List<String> value) {
				result.put(key, value);
			}
		});
		return result;
	}

	protected IndexedReadOnlyStringMap getStringMap() {
		return this.map;
	}

	@PluginFactory
	public static MapFilter createFilter(
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
				LOGGER.error("MapFilter is not configured with any valid key value pairs");
				return null;
			} else {
				boolean isAnd = oper == null || !oper.equalsIgnoreCase("or");
				return new MapFilter(map, isAnd, match, mismatch);
			}
		} else {
			LOGGER.error("keys and values must be specified for the MapFilter");
			return null;
		}
	}
}
