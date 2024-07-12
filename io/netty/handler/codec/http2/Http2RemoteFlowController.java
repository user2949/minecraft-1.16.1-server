package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;

public interface Http2RemoteFlowController extends Http2FlowController {
	ChannelHandlerContext channelHandlerContext();

	void addFlowControlled(Http2Stream http2Stream, Http2RemoteFlowController.FlowControlled flowControlled);

	boolean hasFlowControlled(Http2Stream http2Stream);

	void writePendingBytes() throws Http2Exception;

	void listener(Http2RemoteFlowController.Listener listener);

	boolean isWritable(Http2Stream http2Stream);

	void channelWritabilityChanged() throws Http2Exception;

	void updateDependencyTree(int integer1, int integer2, short short3, boolean boolean4);

	public interface FlowControlled {
		int size();

		void error(ChannelHandlerContext channelHandlerContext, Throwable throwable);

		void writeComplete();

		void write(ChannelHandlerContext channelHandlerContext, int integer);

		boolean merge(ChannelHandlerContext channelHandlerContext, Http2RemoteFlowController.FlowControlled flowControlled);
	}

	public interface Listener {
		void writabilityChanged(Http2Stream http2Stream);
	}
}
