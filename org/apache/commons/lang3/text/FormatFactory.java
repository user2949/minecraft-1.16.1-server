package org.apache.commons.lang3.text;

import java.text.Format;
import java.util.Locale;

public interface FormatFactory {
	Format getFormat(String string1, String string2, Locale locale);
}
