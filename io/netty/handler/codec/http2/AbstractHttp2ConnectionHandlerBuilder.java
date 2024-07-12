package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2HeadersEncoder.SensitivityDetector;
import io.netty.util.internal.ObjectUtil;

public abstract class AbstractHttp2ConnectionHandlerBuilder<T extends Http2ConnectionHandler, B extends AbstractHttp2ConnectionHandlerBuilder<T, B>> {
	private static final SensitivityDetector DEFAULT_HEADER_SENSITIVITY_DETECTOR = Http2HeadersEncoder.NEVER_SENSITIVE;
	private Http2Settings initialSettings = Http2Settings.defaultSettings();
	private Http2FrameListener frameListener;
	private long gracefulShutdownTimeoutMillis = Http2CodecUtil.DEFAULT_GRACEFUL_SHUTDOWN_TIMEOUT_MILLIS;
	private Boolean isServer;
	private Integer maxReservedStreams;
	private Http2Connection connection;
	private Http2ConnectionDecoder decoder;
	private Http2ConnectionEncoder encoder;
	private Boolean validateHeaders;
	private Http2FrameLogger frameLogger;
	private SensitivityDetector headerSensitivityDetector;
	private Boolean encoderEnforceMaxConcurrentStreams;
	private Boolean encoderIgnoreMaxHeaderListSize;
	private int initialHuffmanDecodeCapacity = 32;

	protected Http2Settings initialSettings() {
		return this.initialSettings;
	}

	protected B initialSettings(Http2Settings settings) {
		this.initialSettings = ObjectUtil.checkNotNull(settings, "settings");
		return this.self();
	}

	protected Http2FrameListener frameListener() {
		return this.frameListener;
	}

	protected B frameListener(Http2FrameListener frameListener) {
		this.frameListener = ObjectUtil.checkNotNull(frameListener, "frameListener");
		return this.self();
	}

	protected long gracefulShutdownTimeoutMillis() {
		return this.gracefulShutdownTimeoutMillis;
	}

	protected B gracefulShutdownTimeoutMillis(long gracefulShutdownTimeoutMillis) {
		if (gracefulShutdownTimeoutMillis < -1L) {
			throw new IllegalArgumentException("gracefulShutdownTimeoutMillis: " + gracefulShutdownTimeoutMillis + " (expected: -1 for indefinite or >= 0)");
		} else {
			this.gracefulShutdownTimeoutMillis = gracefulShutdownTimeoutMillis;
			return this.self();
		}
	}

	protected boolean isServer() {
		return this.isServer != null ? this.isServer : true;
	}

	protected B server(boolean isServer) {
		enforceConstraint("server", "connection", this.connection);
		enforceConstraint("server", "codec", this.decoder);
		enforceConstraint("server", "codec", this.encoder);
		this.isServer = isServer;
		return this.self();
	}

	protected int maxReservedStreams() {
		return this.maxReservedStreams != null ? this.maxReservedStreams : 100;
	}

	protected B maxReservedStreams(int maxReservedStreams) {
		enforceConstraint("server", "connection", this.connection);
		enforceConstraint("server", "codec", this.decoder);
		enforceConstraint("server", "codec", this.encoder);
		this.maxReservedStreams = ObjectUtil.checkPositiveOrZero(maxReservedStreams, "maxReservedStreams");
		return this.self();
	}

	protected Http2Connection connection() {
		return this.connection;
	}

	protected B connection(Http2Connection connection) {
		enforceConstraint("connection", "maxReservedStreams", this.maxReservedStreams);
		enforceConstraint("connection", "server", this.isServer);
		enforceConstraint("connection", "codec", this.decoder);
		enforceConstraint("connection", "codec", this.encoder);
		this.connection = ObjectUtil.checkNotNull(connection, "connection");
		return this.self();
	}

	protected Http2ConnectionDecoder decoder() {
		return this.decoder;
	}

	protected Http2ConnectionEncoder encoder() {
		return this.encoder;
	}

	protected B codec(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder) {
		enforceConstraint("codec", "server", this.isServer);
		enforceConstraint("codec", "maxReservedStreams", this.maxReservedStreams);
		enforceConstraint("codec", "connection", this.connection);
		enforceConstraint("codec", "frameLogger", this.frameLogger);
		enforceConstraint("codec", "validateHeaders", this.validateHeaders);
		enforceConstraint("codec", "headerSensitivityDetector", this.headerSensitivityDetector);
		enforceConstraint("codec", "encoderEnforceMaxConcurrentStreams", this.encoderEnforceMaxConcurrentStreams);
		ObjectUtil.checkNotNull(decoder, "decoder");
		ObjectUtil.checkNotNull(encoder, "encoder");
		if (decoder.connection() != encoder.connection()) {
			throw new IllegalArgumentException("The specified encoder and decoder have different connections.");
		} else {
			this.decoder = decoder;
			this.encoder = encoder;
			return this.self();
		}
	}

	protected boolean isValidateHeaders() {
		return this.validateHeaders != null ? this.validateHeaders : true;
	}

	protected B validateHeaders(boolean validateHeaders) {
		this.enforceNonCodecConstraints("validateHeaders");
		this.validateHeaders = validateHeaders;
		return this.self();
	}

	protected Http2FrameLogger frameLogger() {
		return this.frameLogger;
	}

	protected B frameLogger(Http2FrameLogger frameLogger) {
		this.enforceNonCodecConstraints("frameLogger");
		this.frameLogger = ObjectUtil.checkNotNull(frameLogger, "frameLogger");
		return this.self();
	}

	protected boolean encoderEnforceMaxConcurrentStreams() {
		return this.encoderEnforceMaxConcurrentStreams != null ? this.encoderEnforceMaxConcurrentStreams : false;
	}

	protected B encoderEnforceMaxConcurrentStreams(boolean encoderEnforceMaxConcurrentStreams) {
		this.enforceNonCodecConstraints("encoderEnforceMaxConcurrentStreams");
		this.encoderEnforceMaxConcurrentStreams = encoderEnforceMaxConcurrentStreams;
		return this.self();
	}

	protected SensitivityDetector headerSensitivityDetector() {
		return this.headerSensitivityDetector != null ? this.headerSensitivityDetector : DEFAULT_HEADER_SENSITIVITY_DETECTOR;
	}

	protected B headerSensitivityDetector(SensitivityDetector headerSensitivityDetector) {
		this.enforceNonCodecConstraints("headerSensitivityDetector");
		this.headerSensitivityDetector = ObjectUtil.checkNotNull(headerSensitivityDetector, "headerSensitivityDetector");
		return this.self();
	}

	protected B encoderIgnoreMaxHeaderListSize(boolean ignoreMaxHeaderListSize) {
		this.enforceNonCodecConstraints("encoderIgnoreMaxHeaderListSize");
		this.encoderIgnoreMaxHeaderListSize = ignoreMaxHeaderListSize;
		return this.self();
	}

	protected B initialHuffmanDecodeCapacity(int initialHuffmanDecodeCapacity) {
		this.enforceNonCodecConstraints("initialHuffmanDecodeCapacity");
		this.initialHuffmanDecodeCapacity = ObjectUtil.checkPositive(initialHuffmanDecodeCapacity, "initialHuffmanDecodeCapacity");
		return this.self();
	}

	protected T build() {
		if (this.encoder != null) {
			assert this.decoder != null;

			return this.buildFromCodec(this.decoder, this.encoder);
		} else {
			Http2Connection connection = this.connection;
			if (connection == null) {
				connection = new DefaultHttp2Connection(this.isServer(), this.maxReservedStreams());
			}

			return this.buildFromConnection(connection);
		}
	}

	private T buildFromConnection(Http2Connection connection) {
		Long maxHeaderListSize = this.initialSettings.maxHeaderListSize();
		Http2FrameReader reader = new DefaultHttp2FrameReader(
			new DefaultHttp2HeadersDecoder(this.isValidateHeaders(), maxHeaderListSize == null ? 8192L : maxHeaderListSize, this.initialHuffmanDecodeCapacity)
		);
		Http2FrameWriter writer = this.encoderIgnoreMaxHeaderListSize == null
			? new DefaultHttp2FrameWriter(this.headerSensitivityDetector())
			: new DefaultHttp2FrameWriter(this.headerSensitivityDetector(), this.encoderIgnoreMaxHeaderListSize);
		if (this.frameLogger != null) {
			reader = new Http2InboundFrameLogger(reader, this.frameLogger);
			writer = new Http2OutboundFrameLogger(writer, this.frameLogger);
		}

		Http2ConnectionEncoder encoder = new DefaultHttp2ConnectionEncoder(connection, writer);
		boolean encoderEnforceMaxConcurrentStreams = this.encoderEnforceMaxConcurrentStreams();
		if (encoderEnforceMaxConcurrentStreams) {
			if (connection.isServer()) {
				encoder.close();
				reader.close();
				throw new IllegalArgumentException("encoderEnforceMaxConcurrentStreams: " + encoderEnforceMaxConcurrentStreams + " not supported for server");
			}

			encoder = new StreamBufferingEncoder(encoder);
		}

		Http2ConnectionDecoder decoder = new DefaultHttp2ConnectionDecoder(connection, encoder, reader);
		return this.buildFromCodec(decoder, encoder);
	}

	private T buildFromCodec(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder) {
		T handler;
		try {
			handler = this.build(decoder, encoder, this.initialSettings);
		} catch (Throwable var5) {
			encoder.close();
			decoder.close();
			throw new IllegalStateException("failed to build a Http2ConnectionHandler", var5);
		}

		handler.gracefulShutdownTimeoutMillis(this.gracefulShutdownTimeoutMillis);
		if (handler.decoder().frameListener() == null) {
			handler.decoder().frameListener(this.frameListener);
		}

		return handler;
	}

	protected abstract T build(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) throws Exception;

	protected final B self() {
		return (B)this;
	}

	private void enforceNonCodecConstraints(String rejected) {
		enforceConstraint(rejected, "server/connection", this.decoder);
		enforceConstraint(rejected, "server/connection", this.encoder);
	}

	private static void enforceConstraint(String methodName, String rejectorName, Object value) {
		if (value != null) {
			throw new IllegalStateException(methodName + "() cannot be called because " + rejectorName + "() has been called already.");
		}
	}
}
