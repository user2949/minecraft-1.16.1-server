package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.internal.AppendableCharSequence;
import java.util.List;
import java.util.Locale;

public class StompSubframeDecoder extends ReplayingDecoder<StompSubframeDecoder.State> {
	private static final int DEFAULT_CHUNK_SIZE = 8132;
	private static final int DEFAULT_MAX_LINE_LENGTH = 1024;
	private final int maxLineLength;
	private final int maxChunkSize;
	private final boolean validateHeaders;
	private int alreadyReadChunkSize;
	private LastStompContentSubframe lastContent;
	private long contentLength = -1L;

	public StompSubframeDecoder() {
		this(1024, 8132);
	}

	public StompSubframeDecoder(boolean validateHeaders) {
		this(1024, 8132, validateHeaders);
	}

	public StompSubframeDecoder(int maxLineLength, int maxChunkSize) {
		this(maxLineLength, maxChunkSize, false);
	}

	public StompSubframeDecoder(int maxLineLength, int maxChunkSize, boolean validateHeaders) {
		super(StompSubframeDecoder.State.SKIP_CONTROL_CHARACTERS);
		if (maxLineLength <= 0) {
			throw new IllegalArgumentException("maxLineLength must be a positive integer: " + maxLineLength);
		} else if (maxChunkSize <= 0) {
			throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize);
		} else {
			this.maxChunkSize = maxChunkSize;
			this.maxLineLength = maxLineLength;
			this.validateHeaders = validateHeaders;
		}
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		switch ((StompSubframeDecoder.State)this.state()) {
			case SKIP_CONTROL_CHARACTERS:
				skipControlCharacters(in);
				this.checkpoint(StompSubframeDecoder.State.READ_HEADERS);
			case READ_HEADERS:
				StompCommand command = StompCommand.UNKNOWN;
				StompHeadersSubframe frame = null;

				try {
					command = this.readCommand(in);
					frame = new DefaultStompHeadersSubframe(command);
					this.checkpoint(this.readHeaders(in, frame.headers()));
					out.add(frame);
				} catch (Exception var8) {
					if (frame == null) {
						frame = new DefaultStompHeadersSubframe(command);
					}

					frame.setDecoderResult(DecoderResult.failure(var8));
					out.add(frame);
					this.checkpoint(StompSubframeDecoder.State.BAD_FRAME);
					return;
				}
			default:
				try {
					switch ((StompSubframeDecoder.State)this.state()) {
						case READ_CONTENT:
							int toRead = in.readableBytes();
							if (toRead == 0) {
								return;
							}

							if (toRead > this.maxChunkSize) {
								toRead = this.maxChunkSize;
							}

							if (this.contentLength >= 0L) {
								int remainingLength = (int)(this.contentLength - (long)this.alreadyReadChunkSize);
								if (toRead > remainingLength) {
									toRead = remainingLength;
								}

								ByteBuf chunkBuffer = ByteBufUtil.readBytes(ctx.alloc(), in, toRead);
								if ((long)(this.alreadyReadChunkSize += toRead) < this.contentLength) {
									out.add(new DefaultStompContentSubframe(chunkBuffer));
									return;
								}

								this.lastContent = new DefaultLastStompContentSubframe(chunkBuffer);
								this.checkpoint(StompSubframeDecoder.State.FINALIZE_FRAME_READ);
							} else {
								int nulIndex = ByteBufUtil.indexOf(in, in.readerIndex(), in.writerIndex(), (byte)0);
								if (nulIndex == in.readerIndex()) {
									this.checkpoint(StompSubframeDecoder.State.FINALIZE_FRAME_READ);
								} else {
									if (nulIndex > 0) {
										toRead = nulIndex - in.readerIndex();
									} else {
										toRead = in.writerIndex() - in.readerIndex();
									}

									ByteBuf chunkBuffer = ByteBufUtil.readBytes(ctx.alloc(), in, toRead);
									this.alreadyReadChunkSize += toRead;
									if (nulIndex <= 0) {
										out.add(new DefaultStompContentSubframe(chunkBuffer));
										return;
									}

									this.lastContent = new DefaultLastStompContentSubframe(chunkBuffer);
									this.checkpoint(StompSubframeDecoder.State.FINALIZE_FRAME_READ);
								}
							}
						case FINALIZE_FRAME_READ:
							skipNullCharacter(in);
							if (this.lastContent == null) {
								this.lastContent = LastStompContentSubframe.EMPTY_LAST_CONTENT;
							}

							out.add(this.lastContent);
							this.resetDecoder();
					}
				} catch (Exception var7) {
					StompContentSubframe errorContent = new DefaultLastStompContentSubframe(Unpooled.EMPTY_BUFFER);
					errorContent.setDecoderResult(DecoderResult.failure(var7));
					out.add(errorContent);
					this.checkpoint(StompSubframeDecoder.State.BAD_FRAME);
				}

				return;
			case BAD_FRAME:
				in.skipBytes(this.actualReadableBytes());
		}
	}

	private StompCommand readCommand(ByteBuf in) {
		String commandStr = this.readLine(in, 16);
		StompCommand command = null;

		try {
			command = StompCommand.valueOf(commandStr);
		} catch (IllegalArgumentException var6) {
		}

		if (command == null) {
			commandStr = commandStr.toUpperCase(Locale.US);

			try {
				command = StompCommand.valueOf(commandStr);
			} catch (IllegalArgumentException var5) {
			}
		}

		if (command == null) {
			throw new DecoderException("failed to read command from channel");
		} else {
			return command;
		}
	}

	private StompSubframeDecoder.State readHeaders(ByteBuf buffer, StompHeaders headers) {
		AppendableCharSequence buf = new AppendableCharSequence(128);

		boolean headerRead;
		do {
			headerRead = this.readHeader(headers, buf, buffer);
		} while (headerRead);

		if (headers.contains(StompHeaders.CONTENT_LENGTH)) {
			this.contentLength = getContentLength(headers, 0L);
			if (this.contentLength == 0L) {
				return StompSubframeDecoder.State.FINALIZE_FRAME_READ;
			}
		}

		return StompSubframeDecoder.State.READ_CONTENT;
	}

	private static long getContentLength(StompHeaders headers, long defaultValue) {
		long contentLength = headers.getLong(StompHeaders.CONTENT_LENGTH, defaultValue);
		if (contentLength < 0L) {
			throw new DecoderException(StompHeaders.CONTENT_LENGTH + " must be non-negative");
		} else {
			return contentLength;
		}
	}

	private static void skipNullCharacter(ByteBuf buffer) {
		byte b = buffer.readByte();
		if (b != 0) {
			throw new IllegalStateException("unexpected byte in buffer " + b + " while expecting NULL byte");
		}
	}

	private static void skipControlCharacters(ByteBuf buffer) {
		byte b;
		do {
			b = buffer.readByte();
		} while (b == 13 || b == 10);

		buffer.readerIndex(buffer.readerIndex() - 1);
	}

	private String readLine(ByteBuf buffer, int initialBufferSize) {
		AppendableCharSequence buf = new AppendableCharSequence(initialBufferSize);
		int lineLength = 0;

		while (true) {
			byte nextByte = buffer.readByte();
			if (nextByte != 13) {
				if (nextByte == 10) {
					return buf.toString();
				}

				if (lineLength >= this.maxLineLength) {
					this.invalidLineLength();
				}

				lineLength++;
				buf.append((char)nextByte);
			}
		}
	}

	private boolean readHeader(StompHeaders headers, AppendableCharSequence buf, ByteBuf buffer) {
		buf.reset();
		int lineLength = 0;
		String key = null;
		boolean valid = false;

		while (true) {
			byte nextByte = buffer.readByte();
			if (nextByte == 58 && key == null) {
				key = buf.toString();
				valid = true;
				buf.reset();
			} else if (nextByte != 13) {
				if (nextByte == 10) {
					if (key == null && lineLength == 0) {
						return false;
					}

					if (valid) {
						headers.add(key, buf.toString());
					} else if (this.validateHeaders) {
						this.invalidHeader(key, buf.toString());
					}

					return true;
				}

				if (lineLength >= this.maxLineLength) {
					this.invalidLineLength();
				}

				if (nextByte == 58 && key != null) {
					valid = false;
				}

				lineLength++;
				buf.append((char)nextByte);
			}
		}
	}

	private void invalidHeader(String key, String value) {
		String line = key != null ? key + ":" + value : value;
		throw new IllegalArgumentException("a header value or name contains a prohibited character ':', " + line);
	}

	private void invalidLineLength() {
		throw new TooLongFrameException("An STOMP line is larger than " + this.maxLineLength + " bytes.");
	}

	private void resetDecoder() {
		this.checkpoint(StompSubframeDecoder.State.SKIP_CONTROL_CHARACTERS);
		this.contentLength = -1L;
		this.alreadyReadChunkSize = 0;
		this.lastContent = null;
	}

	static enum State {
		SKIP_CONTROL_CHARACTERS,
		READ_HEADERS,
		READ_CONTENT,
		FINALIZE_FRAME_READ,
		BAD_FRAME,
		INVALID_CHUNK;
	}
}
