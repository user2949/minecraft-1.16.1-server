package org.apache.logging.log4j.core.layout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(
	name = "SerializedLayout",
	category = "Core",
	elementType = "layout",
	printObject = true
)
public final class SerializedLayout extends AbstractLayout<LogEvent> {
	private static byte[] serializedHeader;

	private SerializedLayout() {
		super(null, null, null);
	}

	@Override
	public byte[] toByteArray(LogEvent event) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			ObjectOutputStream oos = new SerializedLayout.PrivateObjectOutputStream(baos);
			Throwable var4 = null;

			try {
				oos.writeObject(event);
				oos.reset();
			} catch (Throwable var14) {
				var4 = var14;
				throw var14;
			} finally {
				if (oos != null) {
					if (var4 != null) {
						try {
							oos.close();
						} catch (Throwable var13) {
							var4.addSuppressed(var13);
						}
					} else {
						oos.close();
					}
				}
			}
		} catch (IOException var16) {
			LOGGER.error("Serialization of LogEvent failed.", (Throwable)var16);
		}

		return baos.toByteArray();
	}

	public LogEvent toSerializable(LogEvent event) {
		return event;
	}

	@PluginFactory
	public static SerializedLayout createLayout() {
		return new SerializedLayout();
	}

	@Override
	public byte[] getHeader() {
		return serializedHeader;
	}

	@Override
	public String getContentType() {
		return "application/octet-stream";
	}

	static {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			new ObjectOutputStream(baos).close();
			serializedHeader = baos.toByteArray();
		} catch (Exception var2) {
			LOGGER.error("Unable to generate Object stream header", (Throwable)var2);
		}
	}

	private class PrivateObjectOutputStream extends ObjectOutputStream {
		public PrivateObjectOutputStream(OutputStream os) throws IOException {
			super(os);
		}

		protected void writeStreamHeader() {
		}
	}
}
