package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.message.MapMessage;
import org.apache.logging.log4j.util.IndexedReadOnlyStringMap;

@Plugin(
	name = "MapPatternConverter",
	category = "Converter"
)
@ConverterKeys({"K", "map", "MAP"})
public final class MapPatternConverter extends LogEventPatternConverter {
	private final String key;

	private MapPatternConverter(String[] options) {
		super(options != null && options.length > 0 ? "MAP{" + options[0] + '}' : "MAP", "map");
		this.key = options != null && options.length > 0 ? options[0] : null;
	}

	public static MapPatternConverter newInstance(String[] options) {
		return new MapPatternConverter(options);
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {
		if (event.getMessage() instanceof MapMessage) {
			MapMessage msg = (MapMessage)event.getMessage();
			IndexedReadOnlyStringMap sortedMap = msg.getIndexedReadOnlyStringMap();
			if (this.key == null) {
				if (sortedMap.isEmpty()) {
					toAppendTo.append("{}");
					return;
				}

				toAppendTo.append("{");

				for (int i = 0; i < sortedMap.size(); i++) {
					if (i > 0) {
						toAppendTo.append(", ");
					}

					toAppendTo.append(sortedMap.getKeyAt(i)).append('=').append(sortedMap.getValueAt(i));
				}

				toAppendTo.append('}');
			} else {
				String val = sortedMap.getValue(this.key);
				if (val != null) {
					toAppendTo.append(val);
				}
			}
		}
	}
}
