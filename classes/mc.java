import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;

public class mc extends ByteToMessageDecoder {
	private final Inflater a;
	private int b;

	public mc(int integer) {
		this.b = integer;
		this.a = new Inflater();
	}

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		if (byteBuf.readableBytes() != 0) {
			mg mg5 = new mg(byteBuf);
			int integer6 = mg5.i();
			if (integer6 == 0) {
				list.add(mg5.readBytes(mg5.readableBytes()));
			} else {
				if (integer6 < this.b) {
					throw new DecoderException("Badly compressed packet - size of " + integer6 + " is below server threshold of " + this.b);
				}

				if (integer6 > 2097152) {
					throw new DecoderException("Badly compressed packet - size of " + integer6 + " is larger than protocol maximum of " + 2097152);
				}

				byte[] arr7 = new byte[mg5.readableBytes()];
				mg5.readBytes(arr7);
				this.a.setInput(arr7);
				byte[] arr8 = new byte[integer6];
				this.a.inflate(arr8);
				list.add(Unpooled.wrappedBuffer(arr8));
				this.a.reset();
			}
		}
	}

	public void a(int integer) {
		this.b = integer;
	}
}
