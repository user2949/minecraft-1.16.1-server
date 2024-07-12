package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

public interface Http2Connection {
	Future<Void> close(Promise<Void> promise);

	Http2Connection.PropertyKey newKey();

	void addListener(Http2Connection.Listener listener);

	void removeListener(Http2Connection.Listener listener);

	Http2Stream stream(int integer);

	boolean streamMayHaveExisted(int integer);

	Http2Stream connectionStream();

	int numActiveStreams();

	Http2Stream forEachActiveStream(Http2StreamVisitor http2StreamVisitor) throws Http2Exception;

	boolean isServer();

	Http2Connection.Endpoint<Http2LocalFlowController> local();

	Http2Connection.Endpoint<Http2RemoteFlowController> remote();

	boolean goAwayReceived();

	void goAwayReceived(int integer, long long2, ByteBuf byteBuf);

	boolean goAwaySent();

	void goAwaySent(int integer, long long2, ByteBuf byteBuf);

	public interface Endpoint<F extends Http2FlowController> {
		int incrementAndGetNextStreamId();

		boolean isValidStreamId(int integer);

		boolean mayHaveCreatedStream(int integer);

		boolean created(Http2Stream http2Stream);

		boolean canOpenStream();

		Http2Stream createStream(int integer, boolean boolean2) throws Http2Exception;

		Http2Stream reservePushStream(int integer, Http2Stream http2Stream) throws Http2Exception;

		boolean isServer();

		void allowPushTo(boolean boolean1);

		boolean allowPushTo();

		int numActiveStreams();

		int maxActiveStreams();

		void maxActiveStreams(int integer);

		int lastStreamCreated();

		int lastStreamKnownByPeer();

		F flowController();

		void flowController(F http2FlowController);

		Http2Connection.Endpoint<? extends Http2FlowController> opposite();
	}

	public interface Listener {
		void onStreamAdded(Http2Stream http2Stream);

		void onStreamActive(Http2Stream http2Stream);

		void onStreamHalfClosed(Http2Stream http2Stream);

		void onStreamClosed(Http2Stream http2Stream);

		void onStreamRemoved(Http2Stream http2Stream);

		void onGoAwaySent(int integer, long long2, ByteBuf byteBuf);

		void onGoAwayReceived(int integer, long long2, ByteBuf byteBuf);
	}

	public interface PropertyKey {
	}
}
