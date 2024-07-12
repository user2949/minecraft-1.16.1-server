package it.unimi.dsi.fastutil.io;

import java.io.IOException;

public interface RepositionableStream {
	void position(long long1) throws IOException;

	long position() throws IOException;
}
