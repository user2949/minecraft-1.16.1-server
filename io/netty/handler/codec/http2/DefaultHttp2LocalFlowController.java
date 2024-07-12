package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Connection.PropertyKey;
import io.netty.handler.codec.http2.Http2Exception.CompositeStreamException;
import io.netty.handler.codec.http2.Http2Exception.StreamException;
import io.netty.handler.codec.http2.Http2Stream.State;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;

public class DefaultHttp2LocalFlowController implements Http2LocalFlowController {
	public static final float DEFAULT_WINDOW_UPDATE_RATIO = 0.5F;
	private final Http2Connection connection;
	private final PropertyKey stateKey;
	private Http2FrameWriter frameWriter;
	private ChannelHandlerContext ctx;
	private float windowUpdateRatio;
	private int initialWindowSize = 65535;
	private static final DefaultHttp2LocalFlowController.FlowState REDUCED_FLOW_STATE = new DefaultHttp2LocalFlowController.FlowState() {
		@Override
		public int windowSize() {
			return 0;
		}

		@Override
		public int initialWindowSize() {
			return 0;
		}

		@Override
		public void window(int initialWindowSize) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void incrementInitialStreamWindow(int delta) {
		}

		@Override
		public boolean writeWindowUpdateIfNeeded() throws Http2Exception {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean consumeBytes(int numBytes) throws Http2Exception {
			return false;
		}

		@Override
		public int unconsumedBytes() {
			return 0;
		}

		@Override
		public float windowUpdateRatio() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void windowUpdateRatio(float ratio) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void receiveFlowControlledFrame(int dataLength) throws Http2Exception {
			throw new UnsupportedOperationException();
		}

		@Override
		public void incrementFlowControlWindows(int delta) throws Http2Exception {
		}

		@Override
		public void endOfStream(boolean endOfStream) {
			throw new UnsupportedOperationException();
		}
	};

	public DefaultHttp2LocalFlowController(Http2Connection connection) {
		this(connection, 0.5F, false);
	}

	public DefaultHttp2LocalFlowController(Http2Connection connection, float windowUpdateRatio, boolean autoRefillConnectionWindow) {
		this.connection = ObjectUtil.checkNotNull(connection, "connection");
		this.windowUpdateRatio(windowUpdateRatio);
		this.stateKey = connection.newKey();
		DefaultHttp2LocalFlowController.FlowState connectionState = (DefaultHttp2LocalFlowController.FlowState)(autoRefillConnectionWindow
			? new DefaultHttp2LocalFlowController.AutoRefillState(connection.connectionStream(), this.initialWindowSize)
			: new DefaultHttp2LocalFlowController.DefaultState(connection.connectionStream(), this.initialWindowSize));
		connection.connectionStream().setProperty(this.stateKey, connectionState);
		connection.addListener(
			new Http2ConnectionAdapter() {
				@Override
				public void onStreamAdded(Http2Stream stream) {
					stream.setProperty(DefaultHttp2LocalFlowController.this.stateKey, DefaultHttp2LocalFlowController.REDUCED_FLOW_STATE);
				}
	
				@Override
				public void onStreamActive(Http2Stream stream) {
					stream.setProperty(
						DefaultHttp2LocalFlowController.this.stateKey,
						DefaultHttp2LocalFlowController.this.new DefaultState(stream, DefaultHttp2LocalFlowController.this.initialWindowSize)
					);
				}
	
				@Override
				public void onStreamClosed(Http2Stream stream) {
					try {
						DefaultHttp2LocalFlowController.FlowState state = DefaultHttp2LocalFlowController.this.state(stream);
						int unconsumedBytes = state.unconsumedBytes();
						if (DefaultHttp2LocalFlowController.this.ctx != null && unconsumedBytes > 0) {
							DefaultHttp2LocalFlowController.this.connectionState().consumeBytes(unconsumedBytes);
							state.consumeBytes(unconsumedBytes);
						}
					} catch (Http2Exception var7) {
						PlatformDependent.throwException(var7);
					} finally {
						stream.setProperty(DefaultHttp2LocalFlowController.this.stateKey, DefaultHttp2LocalFlowController.REDUCED_FLOW_STATE);
					}
				}
			}
		);
	}

	public DefaultHttp2LocalFlowController frameWriter(Http2FrameWriter frameWriter) {
		this.frameWriter = ObjectUtil.checkNotNull(frameWriter, "frameWriter");
		return this;
	}

	@Override
	public void channelHandlerContext(ChannelHandlerContext ctx) {
		this.ctx = ObjectUtil.checkNotNull(ctx, "ctx");
	}

	@Override
	public void initialWindowSize(int newWindowSize) throws Http2Exception {
		assert this.ctx == null || this.ctx.executor().inEventLoop();

		int delta = newWindowSize - this.initialWindowSize;
		this.initialWindowSize = newWindowSize;
		DefaultHttp2LocalFlowController.WindowUpdateVisitor visitor = new DefaultHttp2LocalFlowController.WindowUpdateVisitor(delta);
		this.connection.forEachActiveStream(visitor);
		visitor.throwIfError();
	}

	@Override
	public int initialWindowSize() {
		return this.initialWindowSize;
	}

	@Override
	public int windowSize(Http2Stream stream) {
		return this.state(stream).windowSize();
	}

	@Override
	public int initialWindowSize(Http2Stream stream) {
		return this.state(stream).initialWindowSize();
	}

	@Override
	public void incrementWindowSize(Http2Stream stream, int delta) throws Http2Exception {
		assert this.ctx != null && this.ctx.executor().inEventLoop();

		DefaultHttp2LocalFlowController.FlowState state = this.state(stream);
		state.incrementInitialStreamWindow(delta);
		state.writeWindowUpdateIfNeeded();
	}

	@Override
	public boolean consumeBytes(Http2Stream stream, int numBytes) throws Http2Exception {
		assert this.ctx != null && this.ctx.executor().inEventLoop();

		if (numBytes < 0) {
			throw new IllegalArgumentException("numBytes must not be negative");
		} else if (numBytes == 0) {
			return false;
		} else if (stream == null || isClosed(stream)) {
			return false;
		} else if (stream.id() == 0) {
			throw new UnsupportedOperationException("Returning bytes for the connection window is not supported");
		} else {
			boolean windowUpdateSent = this.connectionState().consumeBytes(numBytes);
			return windowUpdateSent | this.state(stream).consumeBytes(numBytes);
		}
	}

	@Override
	public int unconsumedBytes(Http2Stream stream) {
		return this.state(stream).unconsumedBytes();
	}

	private static void checkValidRatio(float ratio) {
		if (Double.compare((double)ratio, 0.0) <= 0 || Double.compare((double)ratio, 1.0) >= 0) {
			throw new IllegalArgumentException("Invalid ratio: " + ratio);
		}
	}

	public void windowUpdateRatio(float ratio) {
		assert this.ctx == null || this.ctx.executor().inEventLoop();

		checkValidRatio(ratio);
		this.windowUpdateRatio = ratio;
	}

	public float windowUpdateRatio() {
		return this.windowUpdateRatio;
	}

	public void windowUpdateRatio(Http2Stream stream, float ratio) throws Http2Exception {
		assert this.ctx != null && this.ctx.executor().inEventLoop();

		checkValidRatio(ratio);
		DefaultHttp2LocalFlowController.FlowState state = this.state(stream);
		state.windowUpdateRatio(ratio);
		state.writeWindowUpdateIfNeeded();
	}

	public float windowUpdateRatio(Http2Stream stream) throws Http2Exception {
		return this.state(stream).windowUpdateRatio();
	}

	@Override
	public void receiveFlowControlledFrame(Http2Stream stream, ByteBuf data, int padding, boolean endOfStream) throws Http2Exception {
		assert this.ctx != null && this.ctx.executor().inEventLoop();

		int dataLength = data.readableBytes() + padding;
		DefaultHttp2LocalFlowController.FlowState connectionState = this.connectionState();
		connectionState.receiveFlowControlledFrame(dataLength);
		if (stream != null && !isClosed(stream)) {
			DefaultHttp2LocalFlowController.FlowState state = this.state(stream);
			state.endOfStream(endOfStream);
			state.receiveFlowControlledFrame(dataLength);
		} else if (dataLength > 0) {
			connectionState.consumeBytes(dataLength);
		}
	}

	private DefaultHttp2LocalFlowController.FlowState connectionState() {
		return this.connection.connectionStream().getProperty(this.stateKey);
	}

	private DefaultHttp2LocalFlowController.FlowState state(Http2Stream stream) {
		return stream.getProperty(this.stateKey);
	}

	private static boolean isClosed(Http2Stream stream) {
		return stream.state() == State.CLOSED;
	}

	private final class AutoRefillState extends DefaultHttp2LocalFlowController.DefaultState {
		public AutoRefillState(Http2Stream stream, int initialWindowSize) {
			super(stream, initialWindowSize);
		}

		@Override
		public void receiveFlowControlledFrame(int dataLength) throws Http2Exception {
			super.receiveFlowControlledFrame(dataLength);
			super.consumeBytes(dataLength);
		}

		@Override
		public boolean consumeBytes(int numBytes) throws Http2Exception {
			return false;
		}
	}

	private class DefaultState implements DefaultHttp2LocalFlowController.FlowState {
		private final Http2Stream stream;
		private int window;
		private int processedWindow;
		private int initialStreamWindowSize;
		private float streamWindowUpdateRatio;
		private int lowerBound;
		private boolean endOfStream;

		public DefaultState(Http2Stream stream, int initialWindowSize) {
			this.stream = stream;
			this.window(initialWindowSize);
			this.streamWindowUpdateRatio = DefaultHttp2LocalFlowController.this.windowUpdateRatio;
		}

		@Override
		public void window(int initialWindowSize) {
			assert DefaultHttp2LocalFlowController.this.ctx == null || DefaultHttp2LocalFlowController.this.ctx.executor().inEventLoop();

			this.window = this.processedWindow = this.initialStreamWindowSize = initialWindowSize;
		}

		@Override
		public int windowSize() {
			return this.window;
		}

		@Override
		public int initialWindowSize() {
			return this.initialStreamWindowSize;
		}

		@Override
		public void endOfStream(boolean endOfStream) {
			this.endOfStream = endOfStream;
		}

		@Override
		public float windowUpdateRatio() {
			return this.streamWindowUpdateRatio;
		}

		@Override
		public void windowUpdateRatio(float ratio) {
			assert DefaultHttp2LocalFlowController.this.ctx == null || DefaultHttp2LocalFlowController.this.ctx.executor().inEventLoop();

			this.streamWindowUpdateRatio = ratio;
		}

		@Override
		public void incrementInitialStreamWindow(int delta) {
			int newValue = (int)Math.min(2147483647L, Math.max(0L, (long)this.initialStreamWindowSize + (long)delta));
			delta = newValue - this.initialStreamWindowSize;
			this.initialStreamWindowSize += delta;
		}

		@Override
		public void incrementFlowControlWindows(int delta) throws Http2Exception {
			if (delta > 0 && this.window > Integer.MAX_VALUE - delta) {
				throw Http2Exception.streamError(this.stream.id(), Http2Error.FLOW_CONTROL_ERROR, "Flow control window overflowed for stream: %d", this.stream.id());
			} else {
				this.window += delta;
				this.processedWindow += delta;
				this.lowerBound = delta < 0 ? delta : 0;
			}
		}

		@Override
		public void receiveFlowControlledFrame(int dataLength) throws Http2Exception {
			assert dataLength >= 0;

			this.window -= dataLength;
			if (this.window < this.lowerBound) {
				throw Http2Exception.streamError(this.stream.id(), Http2Error.FLOW_CONTROL_ERROR, "Flow control window exceeded for stream: %d", this.stream.id());
			}
		}

		private void returnProcessedBytes(int delta) throws Http2Exception {
			if (this.processedWindow - delta < this.window) {
				throw Http2Exception.streamError(this.stream.id(), Http2Error.INTERNAL_ERROR, "Attempting to return too many bytes for stream %d", this.stream.id());
			} else {
				this.processedWindow -= delta;
			}
		}

		@Override
		public boolean consumeBytes(int numBytes) throws Http2Exception {
			this.returnProcessedBytes(numBytes);
			return this.writeWindowUpdateIfNeeded();
		}

		@Override
		public int unconsumedBytes() {
			return this.processedWindow - this.window;
		}

		@Override
		public boolean writeWindowUpdateIfNeeded() throws Http2Exception {
			if (!this.endOfStream && this.initialStreamWindowSize > 0) {
				int threshold = (int)((float)this.initialStreamWindowSize * this.streamWindowUpdateRatio);
				if (this.processedWindow <= threshold) {
					this.writeWindowUpdate();
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		private void writeWindowUpdate() throws Http2Exception {
			int deltaWindowSize = this.initialStreamWindowSize - this.processedWindow;

			try {
				this.incrementFlowControlWindows(deltaWindowSize);
			} catch (Throwable var3) {
				throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, var3, "Attempting to return too many bytes for stream %d", this.stream.id());
			}

			DefaultHttp2LocalFlowController.this.frameWriter
				.writeWindowUpdate(DefaultHttp2LocalFlowController.this.ctx, this.stream.id(), deltaWindowSize, DefaultHttp2LocalFlowController.this.ctx.newPromise());
		}
	}

	private interface FlowState {
		int windowSize();

		int initialWindowSize();

		void window(int integer);

		void incrementInitialStreamWindow(int integer);

		boolean writeWindowUpdateIfNeeded() throws Http2Exception;

		boolean consumeBytes(int integer) throws Http2Exception;

		int unconsumedBytes();

		float windowUpdateRatio();

		void windowUpdateRatio(float float1);

		void receiveFlowControlledFrame(int integer) throws Http2Exception;

		void incrementFlowControlWindows(int integer) throws Http2Exception;

		void endOfStream(boolean boolean1);
	}

	private final class WindowUpdateVisitor implements Http2StreamVisitor {
		private CompositeStreamException compositeException;
		private final int delta;

		public WindowUpdateVisitor(int delta) {
			this.delta = delta;
		}

		@Override
		public boolean visit(Http2Stream stream) throws Http2Exception {
			try {
				DefaultHttp2LocalFlowController.FlowState state = DefaultHttp2LocalFlowController.this.state(stream);
				state.incrementFlowControlWindows(this.delta);
				state.incrementInitialStreamWindow(this.delta);
			} catch (StreamException var3) {
				if (this.compositeException == null) {
					this.compositeException = new CompositeStreamException(var3.error(), 4);
				}

				this.compositeException.add(var3);
			}

			return true;
		}

		public void throwIfError() throws CompositeStreamException {
			if (this.compositeException != null) {
				throw this.compositeException;
			}
		}
	}
}
