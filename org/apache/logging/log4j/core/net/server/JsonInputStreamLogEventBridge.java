package org.apache.logging.log4j.core.net.server;

import java.nio.charset.Charset;
import org.apache.logging.log4j.core.jackson.Log4jJsonObjectMapper;

public class JsonInputStreamLogEventBridge extends InputStreamLogEventBridge {
	private static final int[] END_PAIR = new int[]{-1, -1};
	private static final char EVENT_END_MARKER = '}';
	private static final char EVENT_START_MARKER = '{';
	private static final char JSON_ESC = '\\';
	private static final char JSON_STR_DELIM = '"';
	private static final boolean THREAD_CONTEXT_MAP_AS_LIST = false;

	public JsonInputStreamLogEventBridge() {
		this(1024, Charset.defaultCharset());
	}

	public JsonInputStreamLogEventBridge(int bufferSize, Charset charset) {
		super(new Log4jJsonObjectMapper(false, true), bufferSize, charset, String.valueOf('}'));
	}

	@Override
	protected int[] getEventIndices(String text, int beginIndex) {
		int start = text.indexOf(123, beginIndex);
		if (start == -1) {
			return END_PAIR;
		} else {
			char[] charArray = text.toCharArray();
			int stack = 0;
			boolean inStr = false;
			boolean inEsc = false;

			for (int i = start; i < charArray.length; i++) {
				char c = charArray[i];
				if (inEsc) {
					inEsc = false;
				} else {
					switch (c) {
						case '"':
							inStr = !inStr;
							break;
						case '\\':
							inEsc = true;
							break;
						case '{':
							if (!inStr) {
								stack++;
							}
							break;
						case '}':
							if (!inStr) {
								stack--;
							}
					}

					if (stack == 0) {
						return new int[]{start, i};
					}
				}
			}

			return END_PAIR;
		}
	}
}
