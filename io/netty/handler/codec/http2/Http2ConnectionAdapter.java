package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.Http2Connection.Listener;

public class Http2ConnectionAdapter implements Listener {
	@Override
	public void onStreamAdded(Http2Stream stream) {
	}

	@Override
	public void onStreamActive(Http2Stream stream) {
	}

	@Override
	public void onStreamHalfClosed(Http2Stream stream) {
	}

	@Override
	public void onStreamClosed(Http2Stream stream) {
	}

	@Override
	public void onStreamRemoved(Http2Stream stream) {
	}

	@Override
	public void onGoAwaySent(int lastStreamId, long errorCode, ByteBuf debugData) {
	}

	@Override
	public void onGoAwayReceived(int lastStreamId, long errorCode, ByteBuf debugData) {
	}
}
