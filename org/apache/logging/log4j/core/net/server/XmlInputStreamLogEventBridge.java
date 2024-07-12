package org.apache.logging.log4j.core.net.server;

import java.nio.charset.Charset;
import org.apache.logging.log4j.core.jackson.Log4jXmlObjectMapper;

public class XmlInputStreamLogEventBridge extends InputStreamLogEventBridge {
	private static final String EVENT_END = "</Event>";
	private static final String EVENT_START_NS_N = "<Event>";
	private static final String EVENT_START_NS_Y = "<Event ";

	public XmlInputStreamLogEventBridge() {
		this(1024, Charset.defaultCharset());
	}

	public XmlInputStreamLogEventBridge(int bufferSize, Charset charset) {
		super(new Log4jXmlObjectMapper(), bufferSize, charset, "</Event>");
	}

	@Override
	protected int[] getEventIndices(String text, int beginIndex) {
		int start = text.indexOf("<Event ", beginIndex);
		int startLen = "<Event ".length();
		if (start < 0) {
			start = text.indexOf("<Event>", beginIndex);
			startLen = "<Event>".length();
		}

		int end = start < 0 ? -1 : text.indexOf("</Event>", start + startLen);
		return new int[]{start, end};
	}
}
