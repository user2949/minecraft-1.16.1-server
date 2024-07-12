import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class nk {
	private static final Logger a = LogManager.getLogger();

	public static <T extends mj> void a(ni<T> ni, T mj, zd zd) throws ur {
		a(ni, mj, zd.l());
	}

	public static <T extends mj> void a(ni<T> ni, T mj, amn<?> amn) throws ur {
		if (!amn.bf()) {
			amn.execute(() -> {
				if (mj.a().g()) {
					ni.a(mj);
				} else {
					a.debug("Ignoring packet due to disconnection: " + ni);
				}
			});
			throw ur.a;
		}
	}
}
