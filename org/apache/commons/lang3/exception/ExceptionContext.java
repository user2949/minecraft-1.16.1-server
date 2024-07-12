package org.apache.commons.lang3.exception;

import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public interface ExceptionContext {
	ExceptionContext addContextValue(String string, Object object);

	ExceptionContext setContextValue(String string, Object object);

	List<Object> getContextValues(String string);

	Object getFirstContextValue(String string);

	Set<String> getContextLabels();

	List<Pair<String, Object>> getContextEntries();

	String getFormattedExceptionMessage(String string);
}
