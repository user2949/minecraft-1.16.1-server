package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2HeadersEncoder.SensitivityDetector;
import io.netty.util.internal.ObjectUtil;

public class Http2FrameCodecBuilder extends AbstractHttp2ConnectionHandlerBuilder<Http2FrameCodec, Http2FrameCodecBuilder> {
	private Http2FrameWriter frameWriter;

	Http2FrameCodecBuilder(boolean server) {
		this.server(server);
	}

	public static Http2FrameCodecBuilder forClient() {
		return new Http2FrameCodecBuilder(false);
	}

	public static Http2FrameCodecBuilder forServer() {
		return new Http2FrameCodecBuilder(true);
	}

	Http2FrameCodecBuilder frameWriter(Http2FrameWriter frameWriter) {
		this.frameWriter = ObjectUtil.checkNotNull(frameWriter, "frameWriter");
		return this;
	}

	@Override
	public Http2Settings initialSettings() {
		return super.initialSettings();
	}

	public Http2FrameCodecBuilder initialSettings(Http2Settings settings) {
		return (Http2FrameCodecBuilder)super.initialSettings(settings);
	}

	@Override
	public long gracefulShutdownTimeoutMillis() {
		return super.gracefulShutdownTimeoutMillis();
	}

	public Http2FrameCodecBuilder gracefulShutdownTimeoutMillis(long gracefulShutdownTimeoutMillis) {
		return (Http2FrameCodecBuilder)super.gracefulShutdownTimeoutMillis(gracefulShutdownTimeoutMillis);
	}

	@Override
	public boolean isServer() {
		return super.isServer();
	}

	@Override
	public int maxReservedStreams() {
		return super.maxReservedStreams();
	}

	public Http2FrameCodecBuilder maxReservedStreams(int maxReservedStreams) {
		return (Http2FrameCodecBuilder)super.maxReservedStreams(maxReservedStreams);
	}

	@Override
	public boolean isValidateHeaders() {
		return super.isValidateHeaders();
	}

	public Http2FrameCodecBuilder validateHeaders(boolean validateHeaders) {
		return (Http2FrameCodecBuilder)super.validateHeaders(validateHeaders);
	}

	@Override
	public Http2FrameLogger frameLogger() {
		return super.frameLogger();
	}

	public Http2FrameCodecBuilder frameLogger(Http2FrameLogger frameLogger) {
		return (Http2FrameCodecBuilder)super.frameLogger(frameLogger);
	}

	@Override
	public boolean encoderEnforceMaxConcurrentStreams() {
		return super.encoderEnforceMaxConcurrentStreams();
	}

	public Http2FrameCodecBuilder encoderEnforceMaxConcurrentStreams(boolean encoderEnforceMaxConcurrentStreams) {
		return (Http2FrameCodecBuilder)super.encoderEnforceMaxConcurrentStreams(encoderEnforceMaxConcurrentStreams);
	}

	@Override
	public SensitivityDetector headerSensitivityDetector() {
		return super.headerSensitivityDetector();
	}

	public Http2FrameCodecBuilder headerSensitivityDetector(SensitivityDetector headerSensitivityDetector) {
		return (Http2FrameCodecBuilder)super.headerSensitivityDetector(headerSensitivityDetector);
	}

	public Http2FrameCodecBuilder encoderIgnoreMaxHeaderListSize(boolean ignoreMaxHeaderListSize) {
		return (Http2FrameCodecBuilder)super.encoderIgnoreMaxHeaderListSize(ignoreMaxHeaderListSize);
	}

	public Http2FrameCodecBuilder initialHuffmanDecodeCapacity(int initialHuffmanDecodeCapacity) {
		return (Http2FrameCodecBuilder)super.initialHuffmanDecodeCapacity(initialHuffmanDecodeCapacity);
	}

	public Http2FrameCodec build() {
		Http2FrameWriter frameWriter = this.frameWriter;
		if (frameWriter != null) {
			DefaultHttp2Connection connection = new DefaultHttp2Connection(this.isServer(), this.maxReservedStreams());
			Long maxHeaderListSize = this.initialSettings().maxHeaderListSize();
			Http2FrameReader frameReader = new DefaultHttp2FrameReader(
				maxHeaderListSize == null ? new DefaultHttp2HeadersDecoder(true) : new DefaultHttp2HeadersDecoder(true, maxHeaderListSize)
			);
			if (this.frameLogger() != null) {
				frameWriter = new Http2OutboundFrameLogger(frameWriter, this.frameLogger());
				frameReader = new Http2InboundFrameLogger(frameReader, this.frameLogger());
			}

			Http2ConnectionEncoder encoder = new DefaultHttp2ConnectionEncoder(connection, frameWriter);
			if (this.encoderEnforceMaxConcurrentStreams()) {
				encoder = new StreamBufferingEncoder(encoder);
			}

			Http2ConnectionDecoder decoder = new DefaultHttp2ConnectionDecoder(connection, encoder, frameReader);
			return this.build(decoder, encoder, this.initialSettings());
		} else {
			return (Http2FrameCodec)super.build();
		}
	}

	protected Http2FrameCodec build(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder, Http2Settings initialSettings) {
		return new Http2FrameCodec(encoder, decoder, initialSettings);
	}
}
