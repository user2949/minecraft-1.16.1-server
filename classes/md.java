import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;

public class md extends MessageToByteEncoder<ByteBuf> {
	private final byte[] a = new byte[8192];
	private final Deflater b;
	private int c;

	public md(int integer) {
		this.c = integer;
		this.b = new Deflater();
	}

	protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf2, ByteBuf byteBuf3) throws Exception {
		int integer5 = byteBuf2.readableBytes();
		mg mg6 = new mg(byteBuf3);
		if (integer5 < this.c) {
			mg6.d(0);
			mg6.writeBytes(byteBuf2);
		} else {
			byte[] arr7 = new byte[integer5];
			byteBuf2.readBytes(arr7);
			mg6.d(arr7.length);
			this.b.setInput(arr7, 0, integer5);
			this.b.finish();

			while (!this.b.finished()) {
				int integer8 = this.b.deflate(this.a);
				mg6.writeBytes(this.a, 0, integer8);
			}

			this.b.reset();
		}
	}

	public void a(int integer) {
		this.c = integer;
	}
}
