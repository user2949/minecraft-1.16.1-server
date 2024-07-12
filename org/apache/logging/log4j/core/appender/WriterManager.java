package org.apache.logging.log4j.core.appender;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.StringLayout;

public class WriterManager extends AbstractManager {
	protected final StringLayout layout;
	private volatile Writer writer;

	public static <T> WriterManager getManager(String name, T data, ManagerFactory<? extends WriterManager, T> factory) {
		return AbstractManager.getManager(name, factory, data);
	}

	public WriterManager(Writer writer, String streamName, StringLayout layout, boolean writeHeader) {
		super(null, streamName);
		this.writer = writer;
		this.layout = layout;
		if (writeHeader && layout != null) {
			byte[] header = layout.getHeader();
			if (header != null) {
				try {
					this.writer.write(new String(header, layout.getCharset()));
				} catch (IOException var7) {
					this.logError("Unable to write header", var7);
				}
			}
		}
	}

	protected synchronized void closeWriter() {
		Writer w = this.writer;

		try {
			w.close();
		} catch (IOException var3) {
			this.logError("Unable to close stream", var3);
		}
	}

	public synchronized void flush() {
		try {
			this.writer.flush();
		} catch (IOException var3) {
			String msg = "Error flushing stream " + this.getName();
			throw new AppenderLoggingException(msg, var3);
		}
	}

	protected Writer getWriter() {
		return this.writer;
	}

	public boolean isOpen() {
		return this.getCount() > 0;
	}

	@Override
	public boolean releaseSub(long timeout, TimeUnit timeUnit) {
		this.writeFooter();
		this.closeWriter();
		return true;
	}

	protected void setWriter(Writer writer) {
		byte[] header = this.layout.getHeader();
		if (header != null) {
			try {
				writer.write(new String(header, this.layout.getCharset()));
				this.writer = writer;
			} catch (IOException var4) {
				this.logError("Unable to write header", var4);
			}
		} else {
			this.writer = writer;
		}
	}

	protected synchronized void write(String str) {
		try {
			this.writer.write(str);
		} catch (IOException var4) {
			String msg = "Error writing to stream " + this.getName();
			throw new AppenderLoggingException(msg, var4);
		}
	}

	protected void writeFooter() {
		if (this.layout != null) {
			byte[] footer = this.layout.getFooter();
			if (footer != null && footer.length > 0) {
				this.write(new String(footer, this.layout.getCharset()));
			}
		}
	}
}
