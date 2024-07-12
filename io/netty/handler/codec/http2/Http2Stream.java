package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Connection.PropertyKey;

public interface Http2Stream {
	int id();

	Http2Stream.State state();

	Http2Stream open(boolean boolean1) throws Http2Exception;

	Http2Stream close();

	Http2Stream closeLocalSide();

	Http2Stream closeRemoteSide();

	boolean isResetSent();

	Http2Stream resetSent();

	<V> V setProperty(PropertyKey propertyKey, V object);

	<V> V getProperty(PropertyKey propertyKey);

	<V> V removeProperty(PropertyKey propertyKey);

	Http2Stream headersSent(boolean boolean1);

	boolean isHeadersSent();

	boolean isTrailersSent();

	Http2Stream headersReceived(boolean boolean1);

	boolean isHeadersReceived();

	boolean isTrailersReceived();

	Http2Stream pushPromiseSent();

	boolean isPushPromiseSent();

	public static enum State {
		IDLE(false, false),
		RESERVED_LOCAL(false, false),
		RESERVED_REMOTE(false, false),
		OPEN(true, true),
		HALF_CLOSED_LOCAL(false, true),
		HALF_CLOSED_REMOTE(true, false),
		CLOSED(false, false);

		private final boolean localSideOpen;
		private final boolean remoteSideOpen;

		private State(boolean localSideOpen, boolean remoteSideOpen) {
			this.localSideOpen = localSideOpen;
			this.remoteSideOpen = remoteSideOpen;
		}

		public boolean localSideOpen() {
			return this.localSideOpen;
		}

		public boolean remoteSideOpen() {
			return this.remoteSideOpen;
		}
	}
}
