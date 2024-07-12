package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

@GwtIncompatible
public abstract class CharSink {
	protected CharSink() {
	}

	public abstract Writer openStream() throws IOException;

	public Writer openBufferedStream() throws IOException {
		Writer writer = this.openStream();
		return writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer);
	}

	public void write(CharSequence charSequence) throws IOException {
		Preconditions.checkNotNull(charSequence);
		Closer closer = Closer.create();

		try {
			Writer out = closer.register(this.openStream());
			out.append(charSequence);
			out.flush();
		} catch (Throwable var7) {
			throw closer.rethrow(var7);
		} finally {
			closer.close();
		}
	}

	public void writeLines(Iterable<? extends CharSequence> lines) throws IOException {
		this.writeLines(lines, System.getProperty("line.separator"));
	}

	public void writeLines(Iterable<? extends CharSequence> lines, String lineSeparator) throws IOException {
		Preconditions.checkNotNull(lines);
		Preconditions.checkNotNull(lineSeparator);
		Closer closer = Closer.create();

		try {
			Writer out = closer.register(this.openBufferedStream());

			for (CharSequence line : lines) {
				out.append(line).append(lineSeparator);
			}

			out.flush();
		} catch (Throwable var10) {
			throw closer.rethrow(var10);
		} finally {
			closer.close();
		}
	}

	@CanIgnoreReturnValue
	public long writeFrom(Readable readable) throws IOException {
		Preconditions.checkNotNull(readable);
		Closer closer = Closer.create();

		long var6;
		try {
			Writer out = closer.register(this.openStream());
			long written = CharStreams.copy(readable, out);
			out.flush();
			var6 = written;
		} catch (Throwable var11) {
			throw closer.rethrow(var11);
		} finally {
			closer.close();
		}

		return var6;
	}
}
