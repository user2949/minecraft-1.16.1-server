package org.apache.logging.log4j.core.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class ConfigurationSource {
	public static final ConfigurationSource NULL_SOURCE = new ConfigurationSource(new byte[0]);
	private final File file;
	private final URL url;
	private final String location;
	private final InputStream stream;
	private final byte[] data;

	public ConfigurationSource(InputStream stream, File file) {
		this.stream = (InputStream)Objects.requireNonNull(stream, "stream is null");
		this.file = (File)Objects.requireNonNull(file, "file is null");
		this.location = file.getAbsolutePath();
		this.url = null;
		this.data = null;
	}

	public ConfigurationSource(InputStream stream, URL url) {
		this.stream = (InputStream)Objects.requireNonNull(stream, "stream is null");
		this.url = (URL)Objects.requireNonNull(url, "URL is null");
		this.location = url.toString();
		this.file = null;
		this.data = null;
	}

	public ConfigurationSource(InputStream stream) throws IOException {
		this(toByteArray(stream));
	}

	private ConfigurationSource(byte[] data) {
		this.data = (byte[])Objects.requireNonNull(data, "data is null");
		this.stream = new ByteArrayInputStream(data);
		this.file = null;
		this.url = null;
		this.location = null;
	}

	private static byte[] toByteArray(InputStream inputStream) throws IOException {
		int buffSize = Math.max(4096, inputStream.available());
		ByteArrayOutputStream contents = new ByteArrayOutputStream(buffSize);
		byte[] buff = new byte[buffSize];

		for (int length = inputStream.read(buff); length > 0; length = inputStream.read(buff)) {
			contents.write(buff, 0, length);
		}

		return contents.toByteArray();
	}

	public File getFile() {
		return this.file;
	}

	public URL getURL() {
		return this.url;
	}

	public URI getURI() {
		URI sourceURI = null;
		if (this.url != null) {
			try {
				sourceURI = this.url.toURI();
			} catch (URISyntaxException var6) {
			}
		}

		if (sourceURI == null && this.file != null) {
			sourceURI = this.file.toURI();
		}

		if (sourceURI == null && this.location != null) {
			try {
				sourceURI = new URI(this.location);
			} catch (URISyntaxException var5) {
				try {
					sourceURI = new URI("file://" + this.location);
				} catch (URISyntaxException var4) {
				}
			}
		}

		return sourceURI;
	}

	public String getLocation() {
		return this.location;
	}

	public InputStream getInputStream() {
		return this.stream;
	}

	public ConfigurationSource resetInputStream() throws IOException {
		if (this.file != null) {
			return new ConfigurationSource(new FileInputStream(this.file), this.file);
		} else {
			return this.url != null ? new ConfigurationSource(this.url.openStream(), this.url) : new ConfigurationSource(this.data);
		}
	}

	public String toString() {
		if (this.location != null) {
			return this.location;
		} else if (this == NULL_SOURCE) {
			return "NULL_SOURCE";
		} else {
			int length = this.data == null ? -1 : this.data.length;
			return "stream (" + length + " bytes, unknown location)";
		}
	}
}
