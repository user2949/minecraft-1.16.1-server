package io.netty.handler.codec.http2;

public interface StreamByteDistributor {
	void updateStreamableBytes(StreamByteDistributor.StreamState streamState);

	void updateDependencyTree(int integer1, int integer2, short short3, boolean boolean4);

	boolean distribute(int integer, StreamByteDistributor.Writer writer) throws Http2Exception;

	public interface StreamState {
		Http2Stream stream();

		long pendingBytes();

		boolean hasFrame();

		int windowSize();
	}

	public interface Writer {
		void write(Http2Stream http2Stream, int integer);
	}
}
