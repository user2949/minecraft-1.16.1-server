import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class mm extends MessageToByteEncoder<ByteBuf> {
	protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf2, ByteBuf byteBuf3) throws Exception {
		int integer5 = byteBuf2.readableBytes();
		int integer6 = mg.a(integer5);
		if (integer6 > 3) {
			throw new IllegalArgumentException("unable to fit " + integer5 + " into " + 3);
		} else {
			mg mg7 = new mg(byteBuf3);
			mg7.ensureWritable(integer6 + integer5);
			mg7.d(integer5);
			mg7.writeBytes(byteBuf2, byteBuf2.readerIndex(), integer5);
		}
	}
}
