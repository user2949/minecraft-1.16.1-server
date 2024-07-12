import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;

public class mb extends MessageToByteEncoder<ByteBuf> {
	private final lz a;

	public mb(Cipher cipher) {
		this.a = new lz(cipher);
	}

	protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf2, ByteBuf byteBuf3) throws Exception {
		this.a.a(byteBuf2, byteBuf3);
	}
}
