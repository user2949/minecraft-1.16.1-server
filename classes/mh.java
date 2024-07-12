import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class mh extends ByteToMessageDecoder {
	private static final Logger a = LogManager.getLogger();
	private static final Marker b = MarkerManager.getMarker("PACKET_RECEIVED", me.b);
	private final nj c;

	public mh(nj nj) {
		this.c = nj;
	}

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		if (byteBuf.readableBytes() != 0) {
			mg mg5 = new mg(byteBuf);
			int integer6 = mg5.i();
			ni<?> ni7 = channelHandlerContext.channel().attr(me.c).get().a(this.c, integer6);
			if (ni7 == null) {
				throw new IOException("Bad packet id " + integer6);
			} else {
				ni7.a(mg5);
				if (mg5.readableBytes() > 0) {
					throw new IOException(
						"Packet "
							+ channelHandlerContext.channel().attr(me.c).get().a()
							+ "/"
							+ integer6
							+ " ("
							+ ni7.getClass().getSimpleName()
							+ ") was larger than I expected, found "
							+ mg5.readableBytes()
							+ " bytes extra whilst reading packet "
							+ integer6
					);
				} else {
					list.add(ni7);
					if (a.isDebugEnabled()) {
						a.debug(b, " IN: [{}:{}] {}", channelHandlerContext.channel().attr(me.c).get(), integer6, ni7.getClass().getName());
					}
				}
			}
		}
	}
}
