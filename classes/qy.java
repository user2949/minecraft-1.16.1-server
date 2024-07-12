import io.netty.buffer.Unpooled;
import java.util.Collection;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class qy {
	private static final Logger a = LogManager.getLogger();

	public static void a(zd zd, fu fu, String string, int integer4, int integer5) {
		mg mg6 = new mg(Unpooled.buffer());
		mg6.a(fu);
		mg6.writeInt(integer4);
		mg6.a(string);
		mg6.writeInt(integer5);
		a(zd, mg6, ok.o);
	}

	public static void a(zd zd) {
		mg mg2 = new mg(Unpooled.buffer());
		a(zd, mg2, ok.p);
	}

	public static void a(zd zd, bph bph) {
	}

	public static void a(zd zd, fu fu) {
		d(zd, fu);
	}

	public static void b(zd zd, fu fu) {
		d(zd, fu);
	}

	public static void c(zd zd, fu fu) {
		d(zd, fu);
	}

	private static void d(zd zd, fu fu) {
	}

	public static void a(bqb bqb, aoz aoz, @Nullable czf czf, float float4) {
	}

	public static void a(bqb bqb, fu fu) {
	}

	public static void a(bqc bqc, ctz<?> ctz) {
	}

	public static void a(bqb bqb, aoz aoz, auh auh) {
	}

	public static void a(zd zd, Collection<bfh> collection) {
	}

	public static void a(aoy aoy) {
	}

	public static void a(ayl ayl) {
	}

	public static void a(cdi cdi) {
	}

	private static void a(zd zd, mg mg, uh uh) {
		ni<?> ni4 = new ok(uh, mg);

		for (bec bec6 : zd.n().w()) {
			((ze)bec6).b.a(ni4);
		}
	}
}
