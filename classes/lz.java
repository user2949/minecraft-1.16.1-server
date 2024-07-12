import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class lz {
	private final Cipher a;
	private byte[] b = new byte[0];
	private byte[] c = new byte[0];

	protected lz(Cipher cipher) {
		this.a = cipher;
	}

	private byte[] a(ByteBuf byteBuf) {
		int integer3 = byteBuf.readableBytes();
		if (this.b.length < integer3) {
			this.b = new byte[integer3];
		}

		byteBuf.readBytes(this.b, 0, integer3);
		return this.b;
	}

	protected ByteBuf a(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws ShortBufferException {
		int integer4 = byteBuf.readableBytes();
		byte[] arr5 = this.a(byteBuf);
		ByteBuf byteBuf6 = channelHandlerContext.alloc().heapBuffer(this.a.getOutputSize(integer4));
		byteBuf6.writerIndex(this.a.update(arr5, 0, integer4, byteBuf6.array(), byteBuf6.arrayOffset()));
		return byteBuf6;
	}

	protected void a(ByteBuf byteBuf1, ByteBuf byteBuf2) throws ShortBufferException {
		int integer4 = byteBuf1.readableBytes();
		byte[] arr5 = this.a(byteBuf1);
		int integer6 = this.a.getOutputSize(integer4);
		if (this.c.length < integer6) {
			this.c = new byte[integer6];
		}

		byteBuf2.writeBytes(this.c, 0, this.a.update(arr5, 0, integer4, this.c));
	}
}
