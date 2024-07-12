package org.apache.logging.log4j.core.layout;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.JacksonFactory.XML;

@Plugin(
	name = "XmlLayout",
	category = "Core",
	elementType = "layout",
	printObject = true
)
public final class XmlLayout extends AbstractJacksonLayout {
	private static final String ROOT_TAG = "Events";

	protected XmlLayout(boolean locationInfo, boolean properties, boolean complete, boolean compact, Charset charset, boolean includeStacktrace) {
		super(null, new XML(includeStacktrace).newWriter(locationInfo, properties, compact), charset, compact, complete, false, null, null);
	}

	@Override
	public byte[] getHeader() {
		if (!this.complete) {
			return null;
		} else {
			StringBuilder buf = new StringBuilder();
			buf.append("<?xml version=\"1.0\" encoding=\"");
			buf.append(this.getCharset().name());
			buf.append("\"?>");
			buf.append(this.eol);
			buf.append('<');
			buf.append("Events");
			buf.append(" xmlns=\"http://logging.apache.org/log4j/2.0/events\">");
			buf.append(this.eol);
			return buf.toString().getBytes(this.getCharset());
		}
	}

	@Override
	public byte[] getFooter() {
		return !this.complete ? null : this.getBytes("</Events>" + this.eol);
	}

	@Override
	public Map<String, String> getContentFormat() {
		Map<String, String> result = new HashMap();
		result.put("xsd", "log4j-events.xsd");
		result.put("version", "2.0");
		return result;
	}

	@Override
	public String getContentType() {
		return "text/xml; charset=" + this.getCharset();
	}

	@PluginFactory
	public static XmlLayout createLayout(
		@PluginAttribute("locationInfo") boolean locationInfo,
		@PluginAttribute("properties") boolean properties,
		@PluginAttribute("complete") boolean complete,
		@PluginAttribute("compact") boolean compact,
		@PluginAttribute(value = "charset",defaultString = "UTF-8") Charset charset,
		@PluginAttribute(value = "includeStacktrace",defaultBoolean = true) boolean includeStacktrace
	) {
		return new XmlLayout(locationInfo, properties, complete, compact, charset, includeStacktrace);
	}

	public static XmlLayout createDefaultLayout() {
		return new XmlLayout(false, false, false, false, StandardCharsets.UTF_8, true);
	}
}
