import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

public class ml extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		byteBuf.markReaderIndex();
		byte[] arr5 = new byte[3];

		for (int integer6 = 0; integer6 < arr5.length; integer6++) {
			if (!byteBuf.isReadable()) {
				byteBuf.resetReaderIndex();
				return;
			}

			arr5[integer6] = byteBuf.readByte();
			if (arr5[integer6] >= 0) {
				mg mg7 = new mg(Unpooled.wrappedBuffer(arr5));

				try {
					int integer8 = mg7.i();
					if (byteBuf.readableBytes() >= integer8) {
						list.add(byteBuf.readBytes(integer8));
						return;
					}

					byteBuf.resetReaderIndex();
				} finally {
					mg7.release();
				}

				return;
			}
		}

		throw new CorruptedFrameException("length wider than 21-bit");
	}
}
