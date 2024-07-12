package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.HttpServerUpgradeHandler.UpgradeEvent;
import io.netty.handler.codec.http2.Http2Connection.PropertyKey;
import io.netty.handler.codec.http2.Http2Exception.StreamException;
import io.netty.handler.codec.http2.Http2RemoteFlowController.Listener;
import io.netty.handler.codec.http2.Http2Stream.State;
import io.netty.handler.codec.http2.HttpConversionUtil.ExtensionHeaderNames;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class Http2FrameCodec extends Http2ConnectionHandler {
	private static final InternalLogger LOG = InternalLoggerFactory.getInstance(Http2FrameCodec.class);
	private final PropertyKey streamKey;
	private final PropertyKey upgradeKey;
	private final Integer initialFlowControlWindowSize;
	private ChannelHandlerContext ctx;
	private int numBufferedStreams;
	private Http2FrameCodec.DefaultHttp2FrameStream frameStreamToInitialize;

	Http2FrameCodec(Http2ConnectionEncoder encoder, Http2ConnectionDecoder decoder, Http2Settings initialSettings) {
		super(decoder, encoder, initialSettings);
		decoder.frameListener(new Http2FrameCodec.FrameListener());
		this.connection().addListener(new Http2FrameCodec.ConnectionListener());
		this.connection().remote().flowController().listener(new Http2FrameCodec.Http2RemoteFlowControllerListener());
		this.streamKey = this.connection().newKey();
		this.upgradeKey = this.connection().newKey();
		this.initialFlowControlWindowSize = initialSettings.initialWindowSize();
	}

	Http2FrameCodec.DefaultHttp2FrameStream newStream() {
		return new Http2FrameCodec.DefaultHttp2FrameStream();
	}

	final void forEachActiveStream(Http2FrameStreamVisitor streamVisitor) throws Http2Exception {
		assert this.ctx.executor().inEventLoop();

		this.connection().forEachActiveStream(new Http2StreamVisitor() {
			@Override
			public boolean visit(Http2Stream stream) {
				try {
					return streamVisitor.visit(stream.getProperty(Http2FrameCodec.this.streamKey));
				} catch (Throwable var3) {
					Http2FrameCodec.this.onError(Http2FrameCodec.this.ctx, false, var3);
					return false;
				}
			}
		});
	}

	@Override
	public final void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
		super.handlerAdded(ctx);
		this.handlerAdded0(ctx);
		Http2Connection connection = this.connection();
		if (connection.isServer()) {
			this.tryExpandConnectionFlowControlWindow(connection);
		}
	}

	private void tryExpandConnectionFlowControlWindow(Http2Connection connection) throws Http2Exception {
		if (this.initialFlowControlWindowSize != null) {
			Http2Stream connectionStream = connection.connectionStream();
			Http2LocalFlowController localFlowController = connection.local().flowController();
			int delta = this.initialFlowControlWindowSize - localFlowController.initialWindowSize(connectionStream);
			if (delta > 0) {
				localFlowController.incrementWindowSize(connectionStream, Math.max(delta << 1, delta));
				this.flush(this.ctx);
			}
		}
	}

	void handlerAdded0(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public final void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt == Http2ConnectionPrefaceAndSettingsFrameWrittenEvent.INSTANCE) {
			this.tryExpandConnectionFlowControlWindow(this.connection());
		} else if (evt instanceof UpgradeEvent) {
			UpgradeEvent upgrade = (UpgradeEvent)evt;

			try {
				this.onUpgradeEvent(ctx, upgrade.retain());
				Http2Stream stream = this.connection().stream(1);
				if (stream.getProperty(this.streamKey) == null) {
					this.onStreamActive0(stream);
				}

				upgrade.upgradeRequest().headers().setInt(ExtensionHeaderNames.STREAM_ID.text(), 1);
				stream.setProperty(this.upgradeKey, true);
				InboundHttpToHttp2Adapter.handle(ctx, this.connection(), this.decoder().frameListener(), upgrade.upgradeRequest().retain());
			} finally {
				upgrade.release();
			}

			return;
		}

		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
		if (msg instanceof Http2DataFrame) {
			Http2DataFrame dataFrame = (Http2DataFrame)msg;
			this.encoder().writeData(ctx, dataFrame.stream().id(), dataFrame.content(), dataFrame.padding(), dataFrame.isEndStream(), promise);
		} else if (msg instanceof Http2HeadersFrame) {
			this.writeHeadersFrame(ctx, (Http2HeadersFrame)msg, promise);
		} else if (msg instanceof Http2WindowUpdateFrame) {
			Http2WindowUpdateFrame frame = (Http2WindowUpdateFrame)msg;
			Http2FrameStream frameStream = frame.stream();

			try {
				if (frameStream == null) {
					this.increaseInitialConnectionWindow(frame.windowSizeIncrement());
				} else {
					this.consumeBytes(frameStream.id(), frame.windowSizeIncrement());
				}

				promise.setSuccess();
			} catch (Throwable var7) {
				promise.setFailure(var7);
			}
		} else if (msg instanceof Http2ResetFrame) {
			Http2ResetFrame rstFrame = (Http2ResetFrame)msg;
			this.encoder().writeRstStream(ctx, rstFrame.stream().id(), rstFrame.errorCode(), promise);
		} else if (msg instanceof Http2PingFrame) {
			Http2PingFrame frame = (Http2PingFrame)msg;
			this.encoder().writePing(ctx, frame.ack(), frame.content(), promise);
		} else if (msg instanceof Http2SettingsFrame) {
			this.encoder().writeSettings(ctx, ((Http2SettingsFrame)msg).settings(), promise);
		} else if (msg instanceof Http2GoAwayFrame) {
			this.writeGoAwayFrame(ctx, (Http2GoAwayFrame)msg, promise);
		} else if (msg instanceof Http2UnknownFrame) {
			Http2UnknownFrame unknownFrame = (Http2UnknownFrame)msg;
			this.encoder().writeFrame(ctx, unknownFrame.frameType(), unknownFrame.stream().id(), unknownFrame.flags(), unknownFrame.content(), promise);
		} else {
			if (msg instanceof Http2Frame) {
				ReferenceCountUtil.release(msg);
				throw new UnsupportedMessageTypeException(msg);
			}

			ctx.write(msg, promise);
		}
	}

	private void increaseInitialConnectionWindow(int deltaBytes) throws Http2Exception {
		this.connection().local().flowController().incrementWindowSize(this.connection().connectionStream(), deltaBytes);
	}

	final boolean consumeBytes(int streamId, int bytes) throws Http2Exception {
		Http2Stream stream = this.connection().stream(streamId);
		if (stream != null && streamId == 1) {
			Boolean upgraded = stream.getProperty(this.upgradeKey);
			if (Boolean.TRUE.equals(upgraded)) {
				return false;
			}
		}

		return this.connection().local().flowController().consumeBytes(stream, bytes);
	}

	private void writeGoAwayFrame(ChannelHandlerContext ctx, Http2GoAwayFrame frame, ChannelPromise promise) {
		if (frame.lastStreamId() > -1) {
			frame.release();
			throw new IllegalArgumentException("Last stream id must not be set on GOAWAY frame");
		} else {
			int lastStreamCreated = this.connection().remote().lastStreamCreated();
			long lastStreamId = (long)lastStreamCreated + (long)frame.extraStreamIds() * 2L;
			if (lastStreamId > 2147483647L) {
				lastStreamId = 2147483647L;
			}

			this.goAway(ctx, (int)lastStreamId, frame.errorCode(), frame.content(), promise);
		}
	}

	private void writeHeadersFrame(ChannelHandlerContext ctx, Http2HeadersFrame headersFrame, ChannelPromise promise) {
		if (Http2CodecUtil.isStreamIdValid(headersFrame.stream().id())) {
			this.encoder().writeHeaders(ctx, headersFrame.stream().id(), headersFrame.headers(), headersFrame.padding(), headersFrame.isEndStream(), promise);
		} else {
			Http2FrameCodec.DefaultHttp2FrameStream stream = (Http2FrameCodec.DefaultHttp2FrameStream)headersFrame.stream();
			Http2Connection connection = this.connection();
			int streamId = connection.local().incrementAndGetNextStreamId();
			if (streamId < 0) {
				promise.setFailure(new Http2NoMoreStreamIdsException());
				return;
			}

			stream.id = streamId;

			assert this.frameStreamToInitialize == null;

			this.frameStreamToInitialize = stream;
			ChannelPromise writePromise = ctx.newPromise();
			this.encoder().writeHeaders(ctx, streamId, headersFrame.headers(), headersFrame.padding(), headersFrame.isEndStream(), writePromise);
			if (writePromise.isDone()) {
				notifyHeaderWritePromise(writePromise, promise);
			} else {
				this.numBufferedStreams++;
				writePromise.addListener(new ChannelFutureListener() {
					public void operationComplete(ChannelFuture future) throws Exception {
						Http2FrameCodec.this.numBufferedStreams--;
						Http2FrameCodec.notifyHeaderWritePromise(future, promise);
					}
				});
			}
		}
	}

	private static void notifyHeaderWritePromise(ChannelFuture future, ChannelPromise promise) {
		Throwable cause = future.cause();
		if (cause == null) {
			promise.setSuccess();
		} else {
			promise.setFailure(cause);
		}
	}

	private void onStreamActive0(Http2Stream stream) {
		if (!this.connection().local().isValidStreamId(stream.id())) {
			Http2FrameCodec.DefaultHttp2FrameStream stream2 = this.newStream().setStreamAndProperty(this.streamKey, stream);
			this.onHttp2StreamStateChanged(this.ctx, stream2);
		}
	}

	@Override
	protected void onConnectionError(ChannelHandlerContext ctx, boolean outbound, Throwable cause, Http2Exception http2Ex) {
		if (!outbound) {
			ctx.fireExceptionCaught(cause);
		}

		super.onConnectionError(ctx, outbound, cause, http2Ex);
	}

	@Override
	protected final void onStreamError(ChannelHandlerContext ctx, boolean outbound, Throwable cause, StreamException streamException) {
		int streamId = streamException.streamId();
		Http2Stream connectionStream = this.connection().stream(streamId);
		if (connectionStream == null) {
			this.onHttp2UnknownStreamError(ctx, cause, streamException);
			super.onStreamError(ctx, outbound, cause, streamException);
		} else {
			Http2FrameStream stream = connectionStream.getProperty(this.streamKey);
			if (stream == null) {
				LOG.warn("Stream exception thrown without stream object attached.", cause);
				super.onStreamError(ctx, outbound, cause, streamException);
			} else {
				if (!outbound) {
					this.onHttp2FrameStreamException(ctx, new Http2FrameStreamException(stream, streamException.error(), cause));
				}
			}
		}
	}

	void onHttp2UnknownStreamError(ChannelHandlerContext ctx, Throwable cause, StreamException streamException) {
		LOG.warn("Stream exception thrown for unkown stream {}.", streamException.streamId(), cause);
	}

	@Override
	protected final boolean isGracefulShutdownComplete() {
		return super.isGracefulShutdownComplete() && this.numBufferedStreams == 0;
	}

	void onUpgradeEvent(ChannelHandlerContext ctx, UpgradeEvent evt) {
		ctx.fireUserEventTriggered(evt);
	}

	void onHttp2StreamWritabilityChanged(ChannelHandlerContext ctx, Http2FrameStream stream, boolean writable) {
		ctx.fireUserEventTriggered(Http2FrameStreamEvent.writabilityChanged(stream));
	}

	void onHttp2StreamStateChanged(ChannelHandlerContext ctx, Http2FrameStream stream) {
		ctx.fireUserEventTriggered(Http2FrameStreamEvent.stateChanged(stream));
	}

	void onHttp2Frame(ChannelHandlerContext ctx, Http2Frame frame) {
		ctx.fireChannelRead(frame);
	}

	void onHttp2FrameStreamException(ChannelHandlerContext ctx, Http2FrameStreamException cause) {
		ctx.fireExceptionCaught(cause);
	}

	final boolean isWritable(Http2FrameCodec.DefaultHttp2FrameStream stream) {
		Http2Stream s = stream.stream;
		return s != null && this.connection().remote().flowController().isWritable(s);
	}

	private final class ConnectionListener extends Http2ConnectionAdapter {
		private ConnectionListener() {
		}

		@Override
		public void onStreamAdded(Http2Stream stream) {
			if (Http2FrameCodec.this.frameStreamToInitialize != null && stream.id() == Http2FrameCodec.this.frameStreamToInitialize.id()) {
				Http2FrameCodec.this.frameStreamToInitialize.setStreamAndProperty(Http2FrameCodec.this.streamKey, stream);
				Http2FrameCodec.this.frameStreamToInitialize = null;
			}
		}

		@Override
		public void onStreamActive(Http2Stream stream) {
			Http2FrameCodec.this.onStreamActive0(stream);
		}

		@Override
		public void onStreamClosed(Http2Stream stream) {
			Http2FrameCodec.DefaultHttp2FrameStream stream2 = stream.getProperty(Http2FrameCodec.this.streamKey);
			if (stream2 != null) {
				Http2FrameCodec.this.onHttp2StreamStateChanged(Http2FrameCodec.this.ctx, stream2);
			}
		}

		@Override
		public void onStreamHalfClosed(Http2Stream stream) {
			Http2FrameCodec.DefaultHttp2FrameStream stream2 = stream.getProperty(Http2FrameCodec.this.streamKey);
			if (stream2 != null) {
				Http2FrameCodec.this.onHttp2StreamStateChanged(Http2FrameCodec.this.ctx, stream2);
			}
		}
	}

	static class DefaultHttp2FrameStream implements Http2FrameStream {
		private volatile int id = -1;
		volatile Http2Stream stream;

		Http2FrameCodec.DefaultHttp2FrameStream setStreamAndProperty(PropertyKey streamKey, Http2Stream stream) {
			assert this.id == -1 || stream.id() == this.id;

			this.stream = stream;
			stream.setProperty(streamKey, this);
			return this;
		}

		@Override
		public int id() {
			Http2Stream stream = this.stream;
			return stream == null ? this.id : stream.id();
		}

		@Override
		public State state() {
			Http2Stream stream = this.stream;
			return stream == null ? State.IDLE : stream.state();
		}

		public String toString() {
			return String.valueOf(this.id());
		}
	}

	private final class FrameListener implements Http2FrameListener {
		private FrameListener() {
		}

		@Override
		public void onUnknownFrame(ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf payload) {
			Http2FrameCodec.this.onHttp2Frame(ctx, new DefaultHttp2UnknownFrame(frameType, flags, payload).stream(this.requireStream(streamId)).retain());
		}

		@Override
		public void onSettingsRead(ChannelHandlerContext ctx, Http2Settings settings) {
			Http2FrameCodec.this.onHttp2Frame(ctx, new DefaultHttp2SettingsFrame(settings));
		}

		@Override
		public void onPingRead(ChannelHandlerContext ctx, long data) {
			Http2FrameCodec.this.onHttp2Frame(ctx, new DefaultHttp2PingFrame(data, false));
		}

		@Override
		public void onPingAckRead(ChannelHandlerContext ctx, long data) {
			Http2FrameCodec.this.onHttp2Frame(ctx, new DefaultHttp2PingFrame(data, true));
		}

		@Override
		public void onRstStreamRead(ChannelHandlerContext ctx, int streamId, long errorCode) {
			Http2FrameCodec.this.onHttp2Frame(ctx, new DefaultHttp2ResetFrame(errorCode).stream(this.requireStream(streamId)));
		}

		@Override
		public void onWindowUpdateRead(ChannelHandlerContext ctx, int streamId, int windowSizeIncrement) {
			if (streamId != 0) {
				Http2FrameCodec.this.onHttp2Frame(ctx, new DefaultHttp2WindowUpdateFrame(windowSizeIncrement).stream(this.requireStream(streamId)));
			}
		}

		@Override
		public void onHeadersRead(
			ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endStream
		) {
			this.onHeadersRead(ctx, streamId, headers, padding, endStream);
		}

		@Override
		public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endOfStream) {
			Http2FrameCodec.this.onHttp2Frame(ctx, new DefaultHttp2HeadersFrame(headers, endOfStream, padding).stream(this.requireStream(streamId)));
		}

		@Override
		public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream) {
			Http2FrameCodec.this.onHttp2Frame(ctx, new DefaultHttp2DataFrame(data, endOfStream, padding).stream(this.requireStream(streamId)).retain());
			return 0;
		}

		@Override
		public void onGoAwayRead(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData) {
			Http2FrameCodec.this.onHttp2Frame(ctx, new DefaultHttp2GoAwayFrame(lastStreamId, errorCode, debugData).retain());
		}

		@Override
		public void onPriorityRead(ChannelHandlerContext ctx, int streamId, int streamDependency, short weight, boolean exclusive) {
		}

		@Override
		public void onSettingsAckRead(ChannelHandlerContext ctx) {
		}

		@Override
		public void onPushPromiseRead(ChannelHandlerContext ctx, int streamId, int promisedStreamId, Http2Headers headers, int padding) {
		}

		private Http2FrameStream requireStream(int streamId) {
			Http2FrameStream stream = Http2FrameCodec.this.connection().stream(streamId).getProperty(Http2FrameCodec.this.streamKey);
			if (stream == null) {
				throw new IllegalStateException("Stream object required for identifier: " + streamId);
			} else {
				return stream;
			}
		}
	}

	private final class Http2RemoteFlowControllerListener implements Listener {
		private Http2RemoteFlowControllerListener() {
		}

		@Override
		public void writabilityChanged(Http2Stream stream) {
			Http2FrameStream frameStream = stream.getProperty(Http2FrameCodec.this.streamKey);
			if (frameStream != null) {
				Http2FrameCodec.this.onHttp2StreamWritabilityChanged(
					Http2FrameCodec.this.ctx, frameStream, Http2FrameCodec.this.connection().remote().flowController().isWritable(stream)
				);
			}
		}
	}
}
