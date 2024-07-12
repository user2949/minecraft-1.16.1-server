package io.netty.handler.codec.stomp;

import io.netty.handler.codec.DecoderResult;

public class DefaultStompHeadersSubframe implements StompHeadersSubframe {
	protected final StompCommand command;
	protected DecoderResult decoderResult = DecoderResult.SUCCESS;
	protected final DefaultStompHeaders headers;

	public DefaultStompHeadersSubframe(StompCommand command) {
		this(command, null);
	}

	DefaultStompHeadersSubframe(StompCommand command, DefaultStompHeaders headers) {
		if (command == null) {
			throw new NullPointerException("command");
		} else {
			this.command = command;
			this.headers = headers == null ? new DefaultStompHeaders() : headers;
		}
	}

	@Override
	public StompCommand command() {
		return this.command;
	}

	@Override
	public StompHeaders headers() {
		return this.headers;
	}

	@Override
	public DecoderResult decoderResult() {
		return this.decoderResult;
	}

	@Override
	public void setDecoderResult(DecoderResult decoderResult) {
		this.decoderResult = decoderResult;
	}

	public String toString() {
		return "StompFrame{command=" + this.command + ", headers=" + this.headers + '}';
	}
}
