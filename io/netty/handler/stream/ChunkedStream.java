package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class ChunkedStream implements ChunkedInput<ByteBuf> {
	static final int DEFAULT_CHUNK_SIZE = 8192;
	private final PushbackInputStream in;
	private final int chunkSize;
	private long offset;
	private boolean closed;

	public ChunkedStream(InputStream in) {
		this(in, 8192);
	}

	public ChunkedStream(InputStream in, int chunkSize) {
		if (in == null) {
			throw new NullPointerException("in");
		} else if (chunkSize <= 0) {
			throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
		} else {
			if (in instanceof PushbackInputStream) {
				this.in = (PushbackInputStream)in;
			} else {
				this.in = new PushbackInputStream(in);
			}

			this.chunkSize = chunkSize;
		}
	}

	public long transferredBytes() {
		return this.offset;
	}

	@Override
	public boolean isEndOfInput() throws Exception {
		if (this.closed) {
			return true;
		} else {
			int b = this.in.read();
			if (b < 0) {
				return true;
			} else {
				this.in.unread(b);
				return false;
			}
		}
	}

	@Override
	public void close() throws Exception {
		this.closed = true;
		this.in.close();
	}

	@Deprecated
	public ByteBuf readChunk(ChannelHandlerContext ctx) throws Exception {
		return this.readChunk(ctx.alloc());
	}

	public ByteBuf readChunk(ByteBufAllocator allocator) throws Exception {
		if (this.isEndOfInput()) {
			return null;
		} else {
			int availableBytes = this.in.available();
			int chunkSize;
			if (availableBytes <= 0) {
				chunkSize = this.chunkSize;
			} else {
				chunkSize = Math.min(this.chunkSize, this.in.available());
			}

			boolean release = true;
			ByteBuf buffer = allocator.buffer(chunkSize);

			ByteBuf var6;
			try {
				this.offset = this.offset + (long)buffer.writeBytes(this.in, chunkSize);
				release = false;
				var6 = buffer;
			} finally {
				if (release) {
					buffer.release();
				}
			}

			return var6;
		}
	}

	@Override
	public long length() {
		return -1L;
	}

	@Override
	public long progress() {
		return this.offset;
	}
}
