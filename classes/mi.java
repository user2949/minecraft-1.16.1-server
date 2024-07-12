import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class mi extends MessageToByteEncoder<ni<?>> {
	private static final Logger a = LogManager.getLogger();
	private static final Marker b = MarkerManager.getMarker("PACKET_SENT", me.b);
	private final nj c;

	public mi(nj nj) {
		this.c = nj;
	}

	protected void encode(ChannelHandlerContext channelHandlerContext, ni<?> ni, ByteBuf byteBuf) throws Exception {
		mf mf5 = channelHandlerContext.channel().attr(me.c).get();
		if (mf5 == null) {
			throw new RuntimeException("ConnectionProtocol unknown: " + ni);
		} else {
			Integer integer6 = mf5.a(this.c, ni);
			if (a.isDebugEnabled()) {
				a.debug(b, "OUT: [{}:{}] {}", channelHandlerContext.channel().attr(me.c).get(), integer6, ni.getClass().getName());
			}

			if (integer6 == null) {
				throw new IOException("Can't serialize unregistered packet");
			} else {
				mg mg7 = new mg(byteBuf);
				mg7.d(integer6);

				try {
					ni.b(mg7);
				} catch (Throwable var8) {
					a.error(var8);
					if (ni.a()) {
						throw new mk(var8);
					} else {
						throw var8;
					}
				}
			}
		}
	}
}
