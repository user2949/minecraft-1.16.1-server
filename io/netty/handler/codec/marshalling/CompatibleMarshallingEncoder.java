package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;
import org.jboss.marshalling.Marshaller;

@Sharable
public class CompatibleMarshallingEncoder extends MessageToByteEncoder<Object> {
	private final MarshallerProvider provider;

	public CompatibleMarshallingEncoder(MarshallerProvider provider) {
		this.provider = provider;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		Marshaller marshaller = this.provider.getMarshaller(ctx);
		marshaller.start(new ChannelBufferByteOutput(out));
		marshaller.writeObject(msg);
		marshaller.finish();
		marshaller.close();
	}
}
