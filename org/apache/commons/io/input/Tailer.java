package org.apache.commons.io.input;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Tailer implements Runnable {
	private static final int DEFAULT_DELAY_MILLIS = 1000;
	private static final String RAF_MODE = "r";
	private static final int DEFAULT_BUFSIZE = 4096;
	private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
	private final byte[] inbuf;
	private final File file;
	private final Charset cset;
	private final long delayMillis;
	private final boolean end;
	private final TailerListener listener;
	private final boolean reOpen;
	private volatile boolean run = true;

	public Tailer(File file, TailerListener listener) {
		this(file, listener, 1000L);
	}

	public Tailer(File file, TailerListener listener, long delayMillis) {
		this(file, listener, delayMillis, false);
	}

	public Tailer(File file, TailerListener listener, long delayMillis, boolean end) {
		this(file, listener, delayMillis, end, 4096);
	}

	public Tailer(File file, TailerListener listener, long delayMillis, boolean end, boolean reOpen) {
		this(file, listener, delayMillis, end, reOpen, 4096);
	}

	public Tailer(File file, TailerListener listener, long delayMillis, boolean end, int bufSize) {
		this(file, listener, delayMillis, end, false, bufSize);
	}

	public Tailer(File file, TailerListener listener, long delayMillis, boolean end, boolean reOpen, int bufSize) {
		this(file, DEFAULT_CHARSET, listener, delayMillis, end, reOpen, bufSize);
	}

	public Tailer(File file, Charset cset, TailerListener listener, long delayMillis, boolean end, boolean reOpen, int bufSize) {
		this.file = file;
		this.delayMillis = delayMillis;
		this.end = end;
		this.inbuf = new byte[bufSize];
		this.listener = listener;
		listener.init(this);
		this.reOpen = reOpen;
		this.cset = cset;
	}

	public static Tailer create(File file, TailerListener listener, long delayMillis, boolean end, int bufSize) {
		return create(file, listener, delayMillis, end, false, bufSize);
	}

	public static Tailer create(File file, TailerListener listener, long delayMillis, boolean end, boolean reOpen, int bufSize) {
		return create(file, DEFAULT_CHARSET, listener, delayMillis, end, reOpen, bufSize);
	}

	public static Tailer create(File file, Charset charset, TailerListener listener, long delayMillis, boolean end, boolean reOpen, int bufSize) {
		Tailer tailer = new Tailer(file, charset, listener, delayMillis, end, reOpen, bufSize);
		Thread thread = new Thread(tailer);
		thread.setDaemon(true);
		thread.start();
		return tailer;
	}

	public static Tailer create(File file, TailerListener listener, long delayMillis, boolean end) {
		return create(file, listener, delayMillis, end, 4096);
	}

	public static Tailer create(File file, TailerListener listener, long delayMillis, boolean end, boolean reOpen) {
		return create(file, listener, delayMillis, end, reOpen, 4096);
	}

	public static Tailer create(File file, TailerListener listener, long delayMillis) {
		return create(file, listener, delayMillis, false);
	}

	public static Tailer create(File file, TailerListener listener) {
		return create(file, listener, 1000L, false);
	}

	public File getFile() {
		return this.file;
	}

	protected boolean getRun() {
		return this.run;
	}

	public long getDelay() {
		return this.delayMillis;
	}

	public void run() {
		RandomAccessFile reader = null;

		try {
			long last = 0L;
			long position = 0L;

			while (this.getRun() && reader == null) {
				try {
					reader = new RandomAccessFile(this.file, "r");
				} catch (FileNotFoundException var20) {
					this.listener.fileNotFound();
				}

				if (reader == null) {
					Thread.sleep(this.delayMillis);
				} else {
					position = this.end ? this.file.length() : 0L;
					last = this.file.lastModified();
					reader.seek(position);
				}
			}

			while (this.getRun()) {
				boolean newer = FileUtils.isFileNewer(this.file, last);
				long length = this.file.length();
				if (length < position) {
					this.listener.fileRotated();

					try {
						RandomAccessFile save = reader;
						reader = new RandomAccessFile(this.file, "r");

						try {
							this.readLines(save);
						} catch (IOException var18) {
							this.listener.handle(var18);
						}

						position = 0L;
						IOUtils.closeQuietly(save);
					} catch (FileNotFoundException var19) {
						this.listener.fileNotFound();
					}
				} else {
					if (length > position) {
						position = this.readLines(reader);
						last = this.file.lastModified();
					} else if (newer) {
						position = 0L;
						reader.seek(position);
						position = this.readLines(reader);
						last = this.file.lastModified();
					}

					if (this.reOpen) {
						IOUtils.closeQuietly(reader);
					}

					Thread.sleep(this.delayMillis);
					if (this.getRun() && this.reOpen) {
						reader = new RandomAccessFile(this.file, "r");
						reader.seek(position);
					}
				}
			}
		} catch (InterruptedException var21) {
			Thread.currentThread().interrupt();
			this.stop(var21);
		} catch (Exception var22) {
			this.stop(var22);
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}

	private void stop(Exception e) {
		this.listener.handle(e);
		this.stop();
	}

	public void stop() {
		this.run = false;
	}

	private long readLines(RandomAccessFile reader) throws IOException {
		ByteArrayOutputStream lineBuf = new ByteArrayOutputStream(64);
		long pos = reader.getFilePointer();
		long rePos = pos;

		int num;
		for (boolean seenCR = false; this.getRun() && (num = reader.read(this.inbuf)) != -1; pos = reader.getFilePointer()) {
			for (int i = 0; i < num; i++) {
				byte ch = this.inbuf[i];
				switch (ch) {
					case 10:
						seenCR = false;
						this.listener.handle(new String(lineBuf.toByteArray(), this.cset));
						lineBuf.reset();
						rePos = pos + (long)i + 1L;
						break;
					case 13:
						if (seenCR) {
							lineBuf.write(13);
						}

						seenCR = true;
						break;
					default:
						if (seenCR) {
							seenCR = false;
							this.listener.handle(new String(lineBuf.toByteArray(), this.cset));
							lineBuf.reset();
							rePos = pos + (long)i + 1L;
						}

						lineBuf.write(ch);
				}
			}
		}

		IOUtils.closeQuietly(lineBuf);
		reader.seek(rePos);
		if (this.listener instanceof TailerListenerAdapter) {
			((TailerListenerAdapter)this.listener).endOfFileReached();
		}

		return rePos;
	}
}
