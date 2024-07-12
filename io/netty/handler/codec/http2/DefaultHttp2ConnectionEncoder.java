package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.CoalescingBufferQueue;
import io.netty.handler.codec.http.HttpStatusClass;
import io.netty.handler.codec.http2.Http2FrameWriter.Configuration;
import io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;

public class DefaultHttp2ConnectionEncoder implements Http2ConnectionEncoder {
	private final Http2FrameWriter frameWriter;
	private final Http2Connection connection;
	private Http2LifecycleManager lifecycleManager;
	private final ArrayDeque<Http2Settings> outstandingLocalSettingsQueue = new ArrayDeque(4);

	public DefaultHttp2ConnectionEncoder(Http2Connection connection, Http2FrameWriter frameWriter) {
		this.connection = ObjectUtil.checkNotNull(connection, "connection");
		this.frameWriter = ObjectUtil.checkNotNull(frameWriter, "frameWriter");
		if (connection.remote().flowController() == null) {
			connection.remote().flowController(new DefaultHttp2RemoteFlowController(connection));
		}
	}

	@Override
	public void lifecycleManager(Http2LifecycleManager lifecycleManager) {
		this.lifecycleManager = ObjectUtil.checkNotNull(lifecycleManager, "lifecycleManager");
	}

	@Override
	public Http2FrameWriter frameWriter() {
		return this.frameWriter;
	}

	@Override
	public Http2Connection connection() {
		return this.connection;
	}

	@Override
	public final Http2RemoteFlowController flowController() {
		return this.connection().remote().flowController();
	}

	@Override
	public void remoteSettings(Http2Settings settings) throws Http2Exception {
		Boolean pushEnabled = settings.pushEnabled();
		Configuration config = this.configuration();
		Http2HeadersEncoder.Configuration outboundHeaderConfig = config.headersConfiguration();
		Http2FrameSizePolicy outboundFrameSizePolicy = config.frameSizePolicy();
		if (pushEnabled != null) {
			if (!this.connection.isServer() && pushEnabled) {
				throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Client received a value of ENABLE_PUSH specified to other than 0");
			}

			this.connection.remote().allowPushTo(pushEnabled);
		}

		Long maxConcurrentStreams = settings.maxConcurrentStreams();
		if (maxConcurrentStreams != null) {
			this.connection.local().maxActiveStreams((int)Math.min(maxConcurrentStreams, 2147483647L));
		}

		Long headerTableSize = settings.headerTableSize();
		if (headerTableSize != null) {
			outboundHeaderConfig.maxHeaderTableSize((long)((int)Math.min(headerTableSize, 2147483647L)));
		}

		Long maxHeaderListSize = settings.maxHeaderListSize();
		if (maxHeaderListSize != null) {
			outboundHeaderConfig.maxHeaderListSize(maxHeaderListSize);
		}

		Integer maxFrameSize = settings.maxFrameSize();
		if (maxFrameSize != null) {
			outboundFrameSizePolicy.maxFrameSize(maxFrameSize);
		}

		Integer initialWindowSize = settings.initialWindowSize();
		if (initialWindowSize != null) {
			this.flowController().initialWindowSize(initialWindowSize);
		}
	}

	@Override
	public ChannelFuture writeData(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream, ChannelPromise promise) {
		Http2Stream stream;
		try {
			stream = this.requireStream(streamId);
			switch (stream.state()) {
				case OPEN:
				case HALF_CLOSED_REMOTE:
					break;
				default:
					throw new IllegalStateException("Stream " + stream.id() + " in unexpected state " + stream.state());
			}
		} catch (Throwable var9) {
			data.release();
			return promise.setFailure(var9);
		}

		this.flowController().addFlowControlled(stream, new DefaultHttp2ConnectionEncoder.FlowControlledData(stream, data, padding, endOfStream, promise));
		return promise;
	}

	@Override
	public ChannelFuture writeHeaders(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endStream, ChannelPromise promise) {
		return this.writeHeaders(ctx, streamId, headers, 0, (short)16, false, padding, endStream, promise);
	}

	private static boolean validateHeadersSentState(Http2Stream stream, Http2Headers headers, boolean isServer, boolean endOfStream) {
		boolean isInformational = isServer && HttpStatusClass.valueOf(headers.status()) == HttpStatusClass.INFORMATIONAL;
		if ((!isInformational && endOfStream || !stream.isHeadersSent()) && !stream.isTrailersSent()) {
			return isInformational;
		} else {
			throw new IllegalStateException("Stream " + stream.id() + " sent too many headers EOS: " + endOfStream);
		}
	}

	@Override
	public ChannelFuture writeHeaders(
		ChannelHandlerContext ctx,
		int streamId,
		Http2Headers headers,
		int streamDependency,
		short weight,
		boolean exclusive,
		int padding,
		boolean endOfStream,
		ChannelPromise promise
	) {
		try {
			Http2Stream stream = this.connection.stream(streamId);
			if (stream == null) {
				try {
					stream = this.connection.local().createStream(streamId, endOfStream);
				} catch (Http2Exception var15) {
					if (this.connection.remote().mayHaveCreatedStream(streamId)) {
						promise.tryFailure(new IllegalStateException("Stream no longer exists: " + streamId, var15));
						return promise;
					}

					throw var15;
				}
			} else {
				switch (stream.state()) {
					case OPEN:
					case HALF_CLOSED_REMOTE:
						break;
					case RESERVED_LOCAL:
						stream.open(endOfStream);
						break;
					default:
						throw new IllegalStateException("Stream " + stream.id() + " in unexpected state " + stream.state());
				}
			}

			Http2RemoteFlowController flowController = this.flowController();
			if (endOfStream && flowController.hasFlowControlled(stream)) {
				flowController.addFlowControlled(
					stream, new DefaultHttp2ConnectionEncoder.FlowControlledHeaders(stream, headers, streamDependency, weight, exclusive, padding, true, promise)
				);
				return promise;
			} else {
				boolean isInformational = validateHeadersSentState(stream, headers, this.connection.isServer(), endOfStream);
				if (endOfStream) {
					final Http2Stream finalStream = stream;
					ChannelFutureListener closeStreamLocalListener = new ChannelFutureListener() {
						public void operationComplete(ChannelFuture future) throws Exception {
							DefaultHttp2ConnectionEncoder.this.lifecycleManager.closeStreamLocal(finalStream, future);
						}
					};
					promise = promise.unvoid().addListener(closeStreamLocalListener);
				}

				ChannelFuture future = this.frameWriter.writeHeaders(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endOfStream, promise);
				Throwable failureCause = future.cause();
				if (failureCause == null) {
					stream.headersSent(isInformational);
					if (!future.isSuccess()) {
						this.notifyLifecycleManagerOnError(future, ctx);
					}
				} else {
					this.lifecycleManager.onError(ctx, true, failureCause);
				}

				return future;
			}
		} catch (Throwable var16) {
			this.lifecycleManager.onError(ctx, true, var16);
			promise.tryFailure(var16);
			return promise;
		}
	}

	@Override
	public ChannelFuture writePriority(ChannelHandlerContext ctx, int streamId, int streamDependency, short weight, boolean exclusive, ChannelPromise promise) {
		return this.frameWriter.writePriority(ctx, streamId, streamDependency, weight, exclusive, promise);
	}

	@Override
	public ChannelFuture writeRstStream(ChannelHandlerContext ctx, int streamId, long errorCode, ChannelPromise promise) {
		return this.lifecycleManager.resetStream(ctx, streamId, errorCode, promise);
	}

	@Override
	public ChannelFuture writeSettings(ChannelHandlerContext ctx, Http2Settings settings, ChannelPromise promise) {
		this.outstandingLocalSettingsQueue.add(settings);

		try {
			Boolean pushEnabled = settings.pushEnabled();
			if (pushEnabled != null && this.connection.isServer()) {
				throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server sending SETTINGS frame with ENABLE_PUSH specified");
			}
		} catch (Throwable var5) {
			return promise.setFailure(var5);
		}

		return this.frameWriter.writeSettings(ctx, settings, promise);
	}

	@Override
	public ChannelFuture writeSettingsAck(ChannelHandlerContext ctx, ChannelPromise promise) {
		return this.frameWriter.writeSettingsAck(ctx, promise);
	}

	@Override
	public ChannelFuture writePing(ChannelHandlerContext ctx, boolean ack, long data, ChannelPromise promise) {
		return this.frameWriter.writePing(ctx, ack, data, promise);
	}

	@Override
	public ChannelFuture writePushPromise(ChannelHandlerContext ctx, int streamId, int promisedStreamId, Http2Headers headers, int padding, ChannelPromise promise) {
		try {
			if (this.connection.goAwayReceived()) {
				throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Sending PUSH_PROMISE after GO_AWAY received.");
			} else {
				Http2Stream stream = this.requireStream(streamId);
				this.connection.local().reservePushStream(promisedStreamId, stream);
				ChannelFuture future = this.frameWriter.writePushPromise(ctx, streamId, promisedStreamId, headers, padding, promise);
				Throwable failureCause = future.cause();
				if (failureCause == null) {
					stream.pushPromiseSent();
					if (!future.isSuccess()) {
						this.notifyLifecycleManagerOnError(future, ctx);
					}
				} else {
					this.lifecycleManager.onError(ctx, true, failureCause);
				}

				return future;
			}
		} catch (Throwable var10) {
			this.lifecycleManager.onError(ctx, true, var10);
			promise.tryFailure(var10);
			return promise;
		}
	}

	@Override
	public ChannelFuture writeGoAway(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData, ChannelPromise promise) {
		return this.lifecycleManager.goAway(ctx, lastStreamId, errorCode, debugData, promise);
	}

	@Override
	public ChannelFuture writeWindowUpdate(ChannelHandlerContext ctx, int streamId, int windowSizeIncrement, ChannelPromise promise) {
		return promise.setFailure(new UnsupportedOperationException("Use the Http2[Inbound|Outbound]FlowController objects to control window sizes"));
	}

	@Override
	public ChannelFuture writeFrame(ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf payload, ChannelPromise promise) {
		return this.frameWriter.writeFrame(ctx, frameType, streamId, flags, payload, promise);
	}

	@Override
	public void close() {
		this.frameWriter.close();
	}

	@Override
	public Http2Settings pollSentSettings() {
		return (Http2Settings)this.outstandingLocalSettingsQueue.poll();
	}

	@Override
	public Configuration configuration() {
		return this.frameWriter.configuration();
	}

	private Http2Stream requireStream(int streamId) {
		Http2Stream stream = this.connection.stream(streamId);
		if (stream == null) {
			String message;
			if (this.connection.streamMayHaveExisted(streamId)) {
				message = "Stream no longer exists: " + streamId;
			} else {
				message = "Stream does not exist: " + streamId;
			}

			throw new IllegalArgumentException(message);
		} else {
			return stream;
		}
	}

	private void notifyLifecycleManagerOnError(ChannelFuture future, ChannelHandlerContext ctx) {
		future.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) throws Exception {
				Throwable cause = future.cause();
				if (cause != null) {
					DefaultHttp2ConnectionEncoder.this.lifecycleManager.onError(ctx, true, cause);
				}
			}
		});
	}

	public abstract class FlowControlledBase implements FlowControlled, ChannelFutureListener {
		protected final Http2Stream stream;
		protected ChannelPromise promise;
		protected boolean endOfStream;
		protected int padding;

		FlowControlledBase(Http2Stream stream, int padding, boolean endOfStream, ChannelPromise promise) {
			if (padding < 0) {
				throw new IllegalArgumentException("padding must be >= 0");
			} else {
				this.padding = padding;
				this.endOfStream = endOfStream;
				this.stream = stream;
				this.promise = promise;
			}
		}

		@Override
		public void writeComplete() {
			if (this.endOfStream) {
				DefaultHttp2ConnectionEncoder.this.lifecycleManager.closeStreamLocal(this.stream, this.promise);
			}
		}

		public void operationComplete(ChannelFuture future) throws Exception {
			if (!future.isSuccess()) {
				this.error(DefaultHttp2ConnectionEncoder.this.flowController().channelHandlerContext(), future.cause());
			}
		}
	}

	private final class FlowControlledData extends DefaultHttp2ConnectionEncoder.FlowControlledBase {
		private final CoalescingBufferQueue queue;
		private int dataSize;

		FlowControlledData(Http2Stream stream, ByteBuf buf, int padding, boolean endOfStream, ChannelPromise promise) {
			super(stream, padding, endOfStream, promise);
			this.queue = new CoalescingBufferQueue(promise.channel());
			this.queue.add(buf, promise);
			this.dataSize = this.queue.readableBytes();
		}

		@Override
		public int size() {
			return this.dataSize + this.padding;
		}

		@Override
		public void error(ChannelHandlerContext ctx, Throwable cause) {
			this.queue.releaseAndFailAll(cause);
			DefaultHttp2ConnectionEncoder.this.lifecycleManager.onError(ctx, true, cause);
		}

		@Override
		public void write(ChannelHandlerContext ctx, int allowedBytes) {
			int queuedData = this.queue.readableBytes();
			if (!this.endOfStream) {
				if (queuedData == 0) {
					ChannelPromise writePromise = ctx.newPromise().addListener(this);
					ctx.write(this.queue.remove(0, writePromise), writePromise);
					return;
				}

				if (allowedBytes == 0) {
					return;
				}
			}

			int writableData = Math.min(queuedData, allowedBytes);
			ChannelPromise writePromise = ctx.newPromise().addListener(this);
			ByteBuf toWrite = this.queue.remove(writableData, writePromise);
			this.dataSize = this.queue.readableBytes();
			int writablePadding = Math.min(allowedBytes - writableData, this.padding);
			this.padding -= writablePadding;
			DefaultHttp2ConnectionEncoder.this.frameWriter()
				.writeData(ctx, this.stream.id(), toWrite, writablePadding, this.endOfStream && this.size() == 0, writePromise);
		}

		@Override
		public boolean merge(ChannelHandlerContext ctx, FlowControlled next) {
			DefaultHttp2ConnectionEncoder.FlowControlledData nextData;
			if (DefaultHttp2ConnectionEncoder.FlowControlledData.class == next.getClass()
				&& Integer.MAX_VALUE - (nextData = (DefaultHttp2ConnectionEncoder.FlowControlledData)next).size() >= this.size()) {
				nextData.queue.copyTo(this.queue);
				this.dataSize = this.queue.readableBytes();
				this.padding = Math.max(this.padding, nextData.padding);
				this.endOfStream = nextData.endOfStream;
				return true;
			} else {
				return false;
			}
		}
	}

	private final class FlowControlledHeaders extends DefaultHttp2ConnectionEncoder.FlowControlledBase {
		private final Http2Headers headers;
		private final int streamDependency;
		private final short weight;
		private final boolean exclusive;

		FlowControlledHeaders(
			Http2Stream stream, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endOfStream, ChannelPromise promise
		) {
			super(stream, padding, endOfStream, promise);
			this.headers = headers;
			this.streamDependency = streamDependency;
			this.weight = weight;
			this.exclusive = exclusive;
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public void error(ChannelHandlerContext ctx, Throwable cause) {
			if (ctx != null) {
				DefaultHttp2ConnectionEncoder.this.lifecycleManager.onError(ctx, true, cause);
			}

			this.promise.tryFailure(cause);
		}

		@Override
		public void write(ChannelHandlerContext ctx, int allowedBytes) {
			boolean isInformational = DefaultHttp2ConnectionEncoder.validateHeadersSentState(
				this.stream, this.headers, DefaultHttp2ConnectionEncoder.this.connection.isServer(), this.endOfStream
			);
			if (this.promise.isVoid()) {
				this.promise = ctx.newPromise();
			}

			this.promise.addListener(this);
			ChannelFuture f = DefaultHttp2ConnectionEncoder.this.frameWriter
				.writeHeaders(ctx, this.stream.id(), this.headers, this.streamDependency, this.weight, this.exclusive, this.padding, this.endOfStream, this.promise);
			Throwable failureCause = f.cause();
			if (failureCause == null) {
				this.stream.headersSent(isInformational);
			}
		}

		@Override
		public boolean merge(ChannelHandlerContext ctx, FlowControlled next) {
			return false;
		}
	}
}
