package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.LogEvent;

public interface StrLookup {
	String CATEGORY = "Lookup";

	String lookup(String string);

	String lookup(LogEvent logEvent, String string);
}
