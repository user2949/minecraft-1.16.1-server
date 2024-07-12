package io.netty.handler.codec.http2;

public class DefaultHttp2WindowUpdateFrame extends AbstractHttp2StreamFrame implements Http2WindowUpdateFrame {
	private final int windowUpdateIncrement;

	public DefaultHttp2WindowUpdateFrame(int windowUpdateIncrement) {
		this.windowUpdateIncrement = windowUpdateIncrement;
	}

	public DefaultHttp2WindowUpdateFrame stream(Http2FrameStream stream) {
		super.stream(stream);
		return this;
	}

	@Override
	public String name() {
		return "WINDOW_UPDATE";
	}

	@Override
	public int windowSizeIncrement() {
		return this.windowUpdateIncrement;
	}
}
