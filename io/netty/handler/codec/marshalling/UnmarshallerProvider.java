package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.Unmarshaller;

public interface UnmarshallerProvider {
	Unmarshaller getUnmarshaller(ChannelHandlerContext channelHandlerContext) throws Exception;
}
