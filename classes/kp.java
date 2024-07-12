import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class kp implements kx {
	private static final Logger a = LogManager.getLogger();

	@Override
	public void a(kg kg) {
		if (kg.q()) {
			a.error(kg.c() + " failed! " + v.d(kg.n()));
		} else {
			a.warn("(optional) " + kg.c() + " failed. " + v.d(kg.n()));
		}
	}
}
