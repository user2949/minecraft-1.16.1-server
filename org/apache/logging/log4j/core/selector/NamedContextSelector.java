package org.apache.logging.log4j.core.selector;

import java.net.URI;
import org.apache.logging.log4j.core.LoggerContext;

public interface NamedContextSelector extends ContextSelector {
	LoggerContext locateContext(String string, Object object, URI uRI);

	LoggerContext removeContext(String string);
}
