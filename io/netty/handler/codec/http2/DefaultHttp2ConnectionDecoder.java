package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpStatusClass;
import io.netty.handler.codec.http2.Http2Connection.Endpoint;
import io.netty.handler.codec.http2.Http2FrameReader.Configuration;
import io.netty.handler.codec.http2.Http2Stream.State;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.List;

public class DefaultHttp2ConnectionDecoder implements Http2ConnectionDecoder {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultHttp2ConnectionDecoder.class);
	private Http2FrameListener internalFrameListener = new DefaultHttp2ConnectionDecoder.PrefaceFrameListener();
	private final Http2Connection connection;
	private Http2LifecycleManager lifecycleManager;
	private final Http2ConnectionEncoder encoder;
	private final Http2FrameReader frameReader;
	private Http2FrameListener listener;
	private final Http2PromisedRequestVerifier requestVerifier;

	public DefaultHttp2ConnectionDecoder(Http2Connection connection, Http2ConnectionEncoder encoder, Http2FrameReader frameReader) {
		this(connection, encoder, frameReader, Http2PromisedRequestVerifier.ALWAYS_VERIFY);
	}

	public DefaultHttp2ConnectionDecoder(
		Http2Connection connection, Http2ConnectionEncoder encoder, Http2FrameReader frameReader, Http2PromisedRequestVerifier requestVerifier
	) {
		this.connection = ObjectUtil.checkNotNull(connection, "connection");
		this.frameReader = ObjectUtil.checkNotNull(frameReader, "frameReader");
		this.encoder = ObjectUtil.checkNotNull(encoder, "encoder");
		this.requestVerifier = ObjectUtil.checkNotNull(requestVerifier, "requestVerifier");
		if (connection.local().flowController() == null) {
			connection.local().flowController(new DefaultHttp2LocalFlowController(connection));
		}

		connection.local().flowController().frameWriter(encoder.frameWriter());
	}

	@Override
	public void lifecycleManager(Http2LifecycleManager lifecycleManager) {
		this.lifecycleManager = ObjectUtil.checkNotNull(lifecycleManager, "lifecycleManager");
	}

	@Override
	public Http2Connection connection() {
		return this.connection;
	}

	@Override
	public final Http2LocalFlowController flowController() {
		return this.connection.local().flowController();
	}

	@Override
	public void frameListener(Http2FrameListener listener) {
		this.listener = ObjectUtil.checkNotNull(listener, "listener");
	}

	@Override
	public Http2FrameListener frameListener() {
		return this.listener;
	}

	Http2FrameListener internalFrameListener() {
		return this.internalFrameListener;
	}

	@Override
	public boolean prefaceReceived() {
		return DefaultHttp2ConnectionDecoder.FrameReadListener.class == this.internalFrameListener.getClass();
	}

	@Override
	public void decodeFrame(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Http2Exception {
		this.frameReader.readFrame(ctx, in, this.internalFrameListener);
	}

	@Override
	public Http2Settings localSettings() {
		Http2Settings settings = new Http2Settings();
		Configuration config = this.frameReader.configuration();
		io.netty.handler.codec.http2.Http2HeadersDecoder.Configuration headersConfig = config.headersConfiguration();
		Http2FrameSizePolicy frameSizePolicy = config.frameSizePolicy();
		settings.initialWindowSize(this.flowController().initialWindowSize());
		settings.maxConcurrentStreams((long)this.connection.remote().maxActiveStreams());
		settings.headerTableSize(headersConfig.maxHeaderTableSize());
		settings.maxFrameSize(frameSizePolicy.maxFrameSize());
		settings.maxHeaderListSize(headersConfig.maxHeaderListSize());
		if (!this.connection.isServer()) {
			settings.pushEnabled(this.connection.local().allowPushTo());
		}

		return settings;
	}

	@Override
	public void close() {
		this.frameReader.close();
	}

	protected long calculateMaxHeaderListSizeGoAway(long maxHeaderListSize) {
		return Http2CodecUtil.calculateMaxHeaderListSizeGoAway(maxHeaderListSize);
	}

	private int unconsumedBytes(Http2Stream stream) {
		return this.flowController().unconsumedBytes(stream);
	}

	void onGoAwayRead0(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData) throws Http2Exception {
		if (this.connection.goAwayReceived() && this.connection.local().lastStreamKnownByPeer() < lastStreamId) {
			throw Http2Exception.connectionError(
				Http2Error.PROTOCOL_ERROR, "lastStreamId MUST NOT increase. Current value: %d new value: %d", this.connection.local().lastStreamKnownByPeer(), lastStreamId
			);
		} else {
			this.listener.onGoAwayRead(ctx, lastStreamId, errorCode, debugData);
			this.connection.goAwayReceived(lastStreamId, errorCode, debugData);
		}
	}

	void onUnknownFrame0(ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf payload) throws Http2Exception {
		this.listener.onUnknownFrame(ctx, frameType, streamId, flags, payload);
	}

	private final class FrameReadListener implements Http2FrameListener {
		private FrameReadListener() {
		}

		@Override
		public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream) throws Http2Exception {
			Http2Stream stream = DefaultHttp2ConnectionDecoder.this.connection.stream(streamId);
			Http2LocalFlowController flowController = DefaultHttp2ConnectionDecoder.this.flowController();
			int bytesToReturn = data.readableBytes() + padding;

			boolean shouldIgnore;
			try {
				shouldIgnore = this.shouldIgnoreHeadersOrDataFrame(ctx, streamId, stream, "DATA");
			} catch (Http2Exception var20) {
				flowController.receiveFlowControlledFrame(stream, data, padding, endOfStream);
				flowController.consumeBytes(stream, bytesToReturn);
				throw var20;
			} catch (Throwable var21) {
				throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, var21, "Unhandled error on data stream id %d", streamId);
			}

			if (shouldIgnore) {
				flowController.receiveFlowControlledFrame(stream, data, padding, endOfStream);
				flowController.consumeBytes(stream, bytesToReturn);
				this.verifyStreamMayHaveExisted(streamId);
				return bytesToReturn;
			} else {
				Http2Exception error = null;
				switch (stream.state()) {
					case OPEN:
					case HALF_CLOSED_LOCAL:
						break;
					case HALF_CLOSED_REMOTE:
					case CLOSED:
						error = Http2Exception.streamError(stream.id(), Http2Error.STREAM_CLOSED, "Stream %d in unexpected state: %s", stream.id(), stream.state());
						break;
					default:
						error = Http2Exception.streamError(stream.id(), Http2Error.PROTOCOL_ERROR, "Stream %d in unexpected state: %s", stream.id(), stream.state());
				}

				int unconsumedBytes = DefaultHttp2ConnectionDecoder.this.unconsumedBytes(stream);

				int e;
				try {
					flowController.receiveFlowControlledFrame(stream, data, padding, endOfStream);
					unconsumedBytes = DefaultHttp2ConnectionDecoder.this.unconsumedBytes(stream);
					if (error != null) {
						throw error;
					}

					bytesToReturn = DefaultHttp2ConnectionDecoder.this.listener.onDataRead(ctx, streamId, data, padding, endOfStream);
					e = bytesToReturn;
				} catch (Http2Exception var22) {
					int delta = unconsumedBytes - DefaultHttp2ConnectionDecoder.this.unconsumedBytes(stream);
					bytesToReturn -= delta;
					throw var22;
				} catch (RuntimeException var23) {
					int deltax = unconsumedBytes - DefaultHttp2ConnectionDecoder.this.unconsumedBytes(stream);
					bytesToReturn -= deltax;
					throw var23;
				} finally {
					flowController.consumeBytes(stream, bytesToReturn);
					if (endOfStream) {
						DefaultHttp2ConnectionDecoder.this.lifecycleManager.closeStreamRemote(stream, ctx.newSucceededFuture());
					}
				}

				return e;
			}
		}

		@Override
		public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endOfStream) throws Http2Exception {
			this.onHeadersRead(ctx, streamId, headers, 0, (short)16, false, padding, endOfStream);
		}

		@Override
		public void onHeadersRead(
			ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endOfStream
		) throws Http2Exception {
			Http2Stream stream = DefaultHttp2ConnectionDecoder.this.connection.stream(streamId);
			boolean allowHalfClosedRemote = false;
			if (stream == null && !DefaultHttp2ConnectionDecoder.this.connection.streamMayHaveExisted(streamId)) {
				stream = DefaultHttp2ConnectionDecoder.this.connection.remote().createStream(streamId, endOfStream);
				allowHalfClosedRemote = stream.state() == State.HALF_CLOSED_REMOTE;
			}

			if (!this.shouldIgnoreHeadersOrDataFrame(ctx, streamId, stream, "HEADERS")) {
				boolean isInformational = !DefaultHttp2ConnectionDecoder.this.connection.isServer()
					&& HttpStatusClass.valueOf(headers.status()) == HttpStatusClass.INFORMATIONAL;
				if ((!isInformational && endOfStream || !stream.isHeadersReceived()) && !stream.isTrailersReceived()) {
					switch (stream.state()) {
						case OPEN:
						case HALF_CLOSED_LOCAL:
							break;
						case HALF_CLOSED_REMOTE:
							if (!allowHalfClosedRemote) {
								throw Http2Exception.streamError(stream.id(), Http2Error.STREAM_CLOSED, "Stream %d in unexpected state: %s", stream.id(), stream.state());
							}
							break;
						case CLOSED:
							throw Http2Exception.streamError(stream.id(), Http2Error.STREAM_CLOSED, "Stream %d in unexpected state: %s", stream.id(), stream.state());
						case RESERVED_REMOTE:
							stream.open(endOfStream);
							break;
						default:
							throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d in unexpected state: %s", stream.id(), stream.state());
					}

					stream.headersReceived(isInformational);
					DefaultHttp2ConnectionDecoder.this.encoder.flowController().updateDependencyTree(streamId, streamDependency, weight, exclusive);
					DefaultHttp2ConnectionDecoder.this.listener.onHeadersRead(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endOfStream);
					if (endOfStream) {
						DefaultHttp2ConnectionDecoder.this.lifecycleManager.closeStreamRemote(stream, ctx.newSucceededFuture());
					}
				} else {
					throw Http2Exception.streamError(
						streamId, Http2Error.PROTOCOL_ERROR, "Stream %d received too many headers EOS: %s state: %s", streamId, endOfStream, stream.state()
					);
				}
			}
		}

		@Override
		public void onPriorityRead(ChannelHandlerContext ctx, int streamId, int streamDependency, short weight, boolean exclusive) throws Http2Exception {
			DefaultHttp2ConnectionDecoder.this.encoder.flowController().updateDependencyTree(streamId, streamDependency, weight, exclusive);
			DefaultHttp2ConnectionDecoder.this.listener.onPriorityRead(ctx, streamId, streamDependency, weight, exclusive);
		}

		@Override
		public void onRstStreamRead(ChannelHandlerContext ctx, int streamId, long errorCode) throws Http2Exception {
			Http2Stream stream = DefaultHttp2ConnectionDecoder.this.connection.stream(streamId);
			if (stream == null) {
				this.verifyStreamMayHaveExisted(streamId);
			} else {
				switch (stream.state()) {
					case CLOSED:
						return;
					case IDLE:
						throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "RST_STREAM received for IDLE stream %d", streamId);
					default:
						DefaultHttp2ConnectionDecoder.this.listener.onRstStreamRead(ctx, streamId, errorCode);
						DefaultHttp2ConnectionDecoder.this.lifecycleManager.closeStream(stream, ctx.newSucceededFuture());
				}
			}
		}

		@Override
		public void onSettingsAckRead(ChannelHandlerContext ctx) throws Http2Exception {
			Http2Settings settings = DefaultHttp2ConnectionDecoder.this.encoder.pollSentSettings();
			if (settings != null) {
				this.applyLocalSettings(settings);
			}

			DefaultHttp2ConnectionDecoder.this.listener.onSettingsAckRead(ctx);
		}

		private void applyLocalSettings(Http2Settings settings) throws Http2Exception {
			Boolean pushEnabled = settings.pushEnabled();
			Configuration config = DefaultHttp2ConnectionDecoder.this.frameReader.configuration();
			io.netty.handler.codec.http2.Http2HeadersDecoder.Configuration headerConfig = config.headersConfiguration();
			Http2FrameSizePolicy frameSizePolicy = config.frameSizePolicy();
			if (pushEnabled != null) {
				if (DefaultHttp2ConnectionDecoder.this.connection.isServer()) {
					throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server sending SETTINGS frame with ENABLE_PUSH specified");
				}

				DefaultHttp2ConnectionDecoder.this.connection.local().allowPushTo(pushEnabled);
			}

			Long maxConcurrentStreams = settings.maxConcurrentStreams();
			if (maxConcurrentStreams != null) {
				DefaultHttp2ConnectionDecoder.this.connection.remote().maxActiveStreams((int)Math.min(maxConcurrentStreams, 2147483647L));
			}

			Long headerTableSize = settings.headerTableSize();
			if (headerTableSize != null) {
				headerConfig.maxHeaderTableSize(headerTableSize);
			}

			Long maxHeaderListSize = settings.maxHeaderListSize();
			if (maxHeaderListSize != null) {
				headerConfig.maxHeaderListSize(maxHeaderListSize, DefaultHttp2ConnectionDecoder.this.calculateMaxHeaderListSizeGoAway(maxHeaderListSize));
			}

			Integer maxFrameSize = settings.maxFrameSize();
			if (maxFrameSize != null) {
				frameSizePolicy.maxFrameSize(maxFrameSize);
			}

			Integer initialWindowSize = settings.initialWindowSize();
			if (initialWindowSize != null) {
				DefaultHttp2ConnectionDecoder.this.flowController().initialWindowSize(initialWindowSize);
			}
		}

		@Override
		public void onSettingsRead(ChannelHandlerContext ctx, Http2Settings settings) throws Http2Exception {
			DefaultHttp2ConnectionDecoder.this.encoder.writeSettingsAck(ctx, ctx.newPromise());
			DefaultHttp2ConnectionDecoder.this.encoder.remoteSettings(settings);
			DefaultHttp2ConnectionDecoder.this.listener.onSettingsRead(ctx, settings);
		}

		@Override
		public void onPingRead(ChannelHandlerContext ctx, long data) throws Http2Exception {
			DefaultHttp2ConnectionDecoder.this.encoder.writePing(ctx, true, data, ctx.newPromise());
			DefaultHttp2ConnectionDecoder.this.listener.onPingRead(ctx, data);
		}

		@Override
		public void onPingAckRead(ChannelHandlerContext ctx, long data) throws Http2Exception {
			DefaultHttp2ConnectionDecoder.this.listener.onPingAckRead(ctx, data);
		}

		@Override
		public void onPushPromiseRead(ChannelHandlerContext ctx, int streamId, int promisedStreamId, Http2Headers headers, int padding) throws Http2Exception {
			if (DefaultHttp2ConnectionDecoder.this.connection().isServer()) {
				throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A client cannot push.");
			} else {
				Http2Stream parentStream = DefaultHttp2ConnectionDecoder.this.connection.stream(streamId);
				if (!this.shouldIgnoreHeadersOrDataFrame(ctx, streamId, parentStream, "PUSH_PROMISE")) {
					if (parentStream == null) {
						throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d does not exist", streamId);
					} else {
						switch (parentStream.state()) {
							case OPEN:
							case HALF_CLOSED_LOCAL:
								if (!DefaultHttp2ConnectionDecoder.this.requestVerifier.isAuthoritative(ctx, headers)) {
									throw Http2Exception.streamError(
										promisedStreamId, Http2Error.PROTOCOL_ERROR, "Promised request on stream %d for promised stream %d is not authoritative", streamId, promisedStreamId
									);
								} else if (!DefaultHttp2ConnectionDecoder.this.requestVerifier.isCacheable(headers)) {
									throw Http2Exception.streamError(
										promisedStreamId,
										Http2Error.PROTOCOL_ERROR,
										"Promised request on stream %d for promised stream %d is not known to be cacheable",
										streamId,
										promisedStreamId
									);
								} else {
									if (!DefaultHttp2ConnectionDecoder.this.requestVerifier.isSafe(headers)) {
										throw Http2Exception.streamError(
											promisedStreamId,
											Http2Error.PROTOCOL_ERROR,
											"Promised request on stream %d for promised stream %d is not known to be safe",
											streamId,
											promisedStreamId
										);
									}

									DefaultHttp2ConnectionDecoder.this.connection.remote().reservePushStream(promisedStreamId, parentStream);
									DefaultHttp2ConnectionDecoder.this.listener.onPushPromiseRead(ctx, streamId, promisedStreamId, headers, padding);
									return;
								}
							default:
								throw Http2Exception.connectionError(
									Http2Error.PROTOCOL_ERROR, "Stream %d in unexpected state for receiving push promise: %s", parentStream.id(), parentStream.state()
								);
						}
					}
				}
			}
		}

		@Override
		public void onGoAwayRead(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData) throws Http2Exception {
			DefaultHttp2ConnectionDecoder.this.onGoAwayRead0(ctx, lastStreamId, errorCode, debugData);
		}

		@Override
		public void onWindowUpdateRead(ChannelHandlerContext ctx, int streamId, int windowSizeIncrement) throws Http2Exception {
			Http2Stream stream = DefaultHttp2ConnectionDecoder.this.connection.stream(streamId);
			if (stream != null && stream.state() != State.CLOSED && !this.streamCreatedAfterGoAwaySent(streamId)) {
				DefaultHttp2ConnectionDecoder.this.encoder.flowController().incrementWindowSize(stream, windowSizeIncrement);
				DefaultHttp2ConnectionDecoder.this.listener.onWindowUpdateRead(ctx, streamId, windowSizeIncrement);
			} else {
				this.verifyStreamMayHaveExisted(streamId);
			}
		}

		@Override
		public void onUnknownFrame(ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf payload) throws Http2Exception {
			DefaultHttp2ConnectionDecoder.this.onUnknownFrame0(ctx, frameType, streamId, flags, payload);
		}

		private boolean shouldIgnoreHeadersOrDataFrame(ChannelHandlerContext ctx, int streamId, Http2Stream stream, String frameName) throws Http2Exception {
			if (stream == null) {
				if (this.streamCreatedAfterGoAwaySent(streamId)) {
					DefaultHttp2ConnectionDecoder.logger.info("{} ignoring {} frame for stream {}. Stream sent after GOAWAY sent", ctx.channel(), frameName, streamId);
					return true;
				} else {
					throw Http2Exception.streamError(streamId, Http2Error.STREAM_CLOSED, "Received %s frame for an unknown stream %d", frameName, streamId);
				}
			} else if (!stream.isResetSent() && !this.streamCreatedAfterGoAwaySent(streamId)) {
				return false;
			} else {
				if (DefaultHttp2ConnectionDecoder.logger.isInfoEnabled()) {
					DefaultHttp2ConnectionDecoder.logger
						.info(
							"{} ignoring {} frame for stream {} {}",
							ctx.channel(),
							frameName,
							stream.isResetSent()
								? "RST_STREAM sent."
								: "Stream created after GOAWAY sent. Last known stream by peer " + DefaultHttp2ConnectionDecoder.this.connection.remote().lastStreamKnownByPeer()
						);
				}

				return true;
			}
		}

		private boolean streamCreatedAfterGoAwaySent(int streamId) {
			Endpoint<?> remote = DefaultHttp2ConnectionDecoder.this.connection.remote();
			return DefaultHttp2ConnectionDecoder.this.connection.goAwaySent() && remote.isValidStreamId(streamId) && streamId > remote.lastStreamKnownByPeer();
		}

		private void verifyStreamMayHaveExisted(int streamId) throws Http2Exception {
			if (!DefaultHttp2ConnectionDecoder.this.connection.streamMayHaveExisted(streamId)) {
				throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d does not exist", streamId);
			}
		}
	}

	private final class PrefaceFrameListener implements Http2FrameListener {
		private PrefaceFrameListener() {
		}

		private void verifyPrefaceReceived() throws Http2Exception {
			if (!DefaultHttp2ConnectionDecoder.this.prefaceReceived()) {
				throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Received non-SETTINGS as first frame.");
			}
		}

		@Override
		public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream) throws Http2Exception {
			this.verifyPrefaceReceived();
			return DefaultHttp2ConnectionDecoder.this.internalFrameListener.onDataRead(ctx, streamId, data, padding, endOfStream);
		}

		@Override
		public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endOfStream) throws Http2Exception {
			this.verifyPrefaceReceived();
			DefaultHttp2ConnectionDecoder.this.internalFrameListener.onHeadersRead(ctx, streamId, headers, padding, endOfStream);
		}

		@Override
		public void onHeadersRead(
			ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endOfStream
		) throws Http2Exception {
			this.verifyPrefaceReceived();
			DefaultHttp2ConnectionDecoder.this.internalFrameListener.onHeadersRead(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endOfStream);
		}

		@Override
		public void onPriorityRead(ChannelHandlerContext ctx, int streamId, int streamDependency, short weight, boolean exclusive) throws Http2Exception {
			this.verifyPrefaceReceived();
			DefaultHttp2ConnectionDecoder.this.internalFrameListener.onPriorityRead(ctx, streamId, streamDependency, weight, exclusive);
		}

		@Override
		public void onRstStreamRead(ChannelHandlerContext ctx, int streamId, long errorCode) throws Http2Exception {
			this.verifyPrefaceReceived();
			DefaultHttp2ConnectionDecoder.this.internalFrameListener.onRstStreamRead(ctx, streamId, errorCode);
		}

		@Override
		public void onSettingsAckRead(ChannelHandlerContext ctx) throws Http2Exception {
			this.verifyPrefaceReceived();
			DefaultHttp2ConnectionDecoder.this.internalFrameListener.onSettingsAckRead(ctx);
		}

		@Override
		public void onSettingsRead(ChannelHandlerContext ctx, Http2Settings settings) throws Http2Exception {
			if (!DefaultHttp2ConnectionDecoder.this.prefaceReceived()) {
				DefaultHttp2ConnectionDecoder.this.internalFrameListener = DefaultHttp2ConnectionDecoder.this.new FrameReadListener();
			}

			DefaultHttp2ConnectionDecoder.this.internalFrameListener.onSettingsRead(ctx, settings);
		}

		@Override
		public void onPingRead(ChannelHandlerContext ctx, long data) throws Http2Exception {
			this.verifyPrefaceReceived();
			DefaultHttp2ConnectionDecoder.this.internalFrameListener.onPingRead(ctx, data);
		}

		@Override
		public void onPingAckRead(ChannelHandlerContext ctx, long data) throws Http2Exception {
			this.verifyPrefaceReceived();
			DefaultHttp2ConnectionDecoder.this.internalFrameListener.onPingAckRead(ctx, data);
		}

		@Override
		public void onPushPromiseRead(ChannelHandlerContext ctx, int streamId, int promisedStreamId, Http2Headers headers, int padding) throws Http2Exception {
			this.verifyPrefaceReceived();
			DefaultHttp2ConnectionDecoder.this.internalFrameListener.onPushPromiseRead(ctx, streamId, promisedStreamId, headers, padding);
		}

		@Override
		public void onGoAwayRead(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData) throws Http2Exception {
			DefaultHttp2ConnectionDecoder.this.onGoAwayRead0(ctx, lastStreamId, errorCode, debugData);
		}

		@Override
		public void onWindowUpdateRead(ChannelHandlerContext ctx, int streamId, int windowSizeIncrement) throws Http2Exception {
			this.verifyPrefaceReceived();
			DefaultHttp2ConnectionDecoder.this.internalFrameListener.onWindowUpdateRead(ctx, streamId, windowSizeIncrement);
		}

		@Override
		public void onUnknownFrame(ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf payload) throws Http2Exception {
			DefaultHttp2ConnectionDecoder.this.onUnknownFrame0(ctx, frameType, streamId, flags, payload);
		}
	}
}
