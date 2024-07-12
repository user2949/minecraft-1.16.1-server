package org.apache.logging.log4j.core.net.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LogEventListener;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;

public abstract class InputStreamLogEventBridge extends AbstractLogEventBridge<InputStream> {
	private final int bufferSize;
	private final Charset charset;
	private final String eventEndMarker;
	private final ObjectReader objectReader;

	public InputStreamLogEventBridge(ObjectMapper mapper, int bufferSize, Charset charset, String eventEndMarker) {
		this.bufferSize = bufferSize;
		this.charset = charset;
		this.eventEndMarker = eventEndMarker;
		this.objectReader = mapper.readerFor(Log4jLogEvent.class);
	}

	protected abstract int[] getEventIndices(String string, int integer);

	@Override
	public void logEvents(InputStream inputStream, LogEventListener logEventListener) throws IOException {
		String workingText = "";

		try {
			byte[] buffer = new byte[this.bufferSize];
			workingText = "";
			String textRemains = "";

			while (true) {
				int streamReadLength = inputStream.read(buffer);
				if (streamReadLength == -1) {
					break;
				}

				String text = workingText = textRemains + new String(buffer, 0, streamReadLength, this.charset);
				int beginIndex = 0;

				while (true) {
					int[] pair = this.getEventIndices(text, beginIndex);
					int eventStartMarkerIndex = pair[0];
					if (eventStartMarkerIndex < 0) {
						textRemains = text.substring(beginIndex);
						break;
					}

					int eventEndMarkerIndex = pair[1];
					if (eventEndMarkerIndex <= 0) {
						textRemains = text.substring(beginIndex);
						break;
					}

					int eventEndXmlIndex = eventEndMarkerIndex + this.eventEndMarker.length();
					String textEvent = workingText = text.substring(eventStartMarkerIndex, eventEndXmlIndex);
					LogEvent logEvent = this.unmarshal(textEvent);
					logEventListener.log(logEvent);
					beginIndex = eventEndXmlIndex;
				}
			}
		} catch (IOException var15) {
			logger.error(workingText, (Throwable)var15);
		}
	}

	protected Log4jLogEvent unmarshal(String jsonEvent) throws IOException {
		return (Log4jLogEvent)this.objectReader.readValue(jsonEvent);
	}
}
