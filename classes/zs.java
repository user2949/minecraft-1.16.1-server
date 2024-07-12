import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zs extends ChannelInboundHandlerAdapter {
	private static final Logger a = LogManager.getLogger();
	private final zu b;

	public zs(zu zu) {
		this.b = zu;
	}

	@Override
	public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
		ByteBuf byteBuf4 = (ByteBuf)object;
		byteBuf4.markReaderIndex();
		boolean boolean5 = true;

		try {
			try {
				if (byteBuf4.readUnsignedByte() != 254) {
					return;
				}

				InetSocketAddress inetSocketAddress6 = (InetSocketAddress)channelHandlerContext.channel().remoteAddress();
				MinecraftServer minecraftServer7 = this.b.d();
				int integer8 = byteBuf4.readableBytes();
				switch (integer8) {
					case 0: {
						a.debug("Ping: (<1.3.x) from {}:{}", inetSocketAddress6.getAddress(), inetSocketAddress6.getPort());
						String string9 = String.format("%s§%d§%d", minecraftServer7.Z(), minecraftServer7.H(), minecraftServer7.I());
						this.a(channelHandlerContext, this.a(string9));
						break;
					}
					case 1: {
						if (byteBuf4.readUnsignedByte() != 1) {
							return;
						}

						a.debug("Ping: (1.4-1.5.x) from {}:{}", inetSocketAddress6.getAddress(), inetSocketAddress6.getPort());
						String string9 = String.format(
							"§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, minecraftServer7.G(), minecraftServer7.Z(), minecraftServer7.H(), minecraftServer7.I()
						);
						this.a(channelHandlerContext, this.a(string9));
						break;
					}
					default:
						boolean boolean9 = byteBuf4.readUnsignedByte() == 1;
						boolean9 &= byteBuf4.readUnsignedByte() == 250;
						boolean9 &= "MC|PingHost".equals(new String(byteBuf4.readBytes(byteBuf4.readShort() * 2).array(), StandardCharsets.UTF_16BE));
						int integer10 = byteBuf4.readUnsignedShort();
						boolean9 &= byteBuf4.readUnsignedByte() >= 73;
						boolean9 &= 3 + byteBuf4.readBytes(byteBuf4.readShort() * 2).array().length + 4 == integer10;
						boolean9 &= byteBuf4.readInt() <= 65535;
						boolean9 &= byteBuf4.readableBytes() == 0;
						if (!boolean9) {
							return;
						}

						a.debug("Ping: (1.6) from {}:{}", inetSocketAddress6.getAddress(), inetSocketAddress6.getPort());
						String string11 = String.format(
							"§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, minecraftServer7.G(), minecraftServer7.Z(), minecraftServer7.H(), minecraftServer7.I()
						);
						ByteBuf byteBuf12 = this.a(string11);

						try {
							this.a(channelHandlerContext, byteBuf12);
						} finally {
							byteBuf12.release();
						}
				}

				byteBuf4.release();
				boolean5 = false;
			} catch (RuntimeException var21) {
			}
		} finally {
			if (boolean5) {
				byteBuf4.resetReaderIndex();
				channelHandlerContext.channel().pipeline().remove("legacy_query");
				channelHandlerContext.fireChannelRead(object);
			}
		}
	}

	private void a(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
		channelHandlerContext.pipeline().firstContext().writeAndFlush(byteBuf).addListener(ChannelFutureListener.CLOSE);
	}

	private ByteBuf a(String string) {
		ByteBuf byteBuf3 = Unpooled.buffer();
		byteBuf3.writeByte(255);
		char[] arr4 = string.toCharArray();
		byteBuf3.writeShort(arr4.length);

		for (char character8 : arr4) {
			byteBuf3.writeChar(character8);
		}

		return byteBuf3;
	}
}
