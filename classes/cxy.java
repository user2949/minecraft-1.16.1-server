import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cxy {
	private static final Logger a = LogManager.getLogger();
	private final cxk b;

	public cxy(cxj<cxk> cxj) {
		this.b = cxj.make();
	}

	private bre a(int integer) {
		bre bre3 = gl.as.a(integer);
		if (bre3 == null) {
			if (u.d) {
				throw (IllegalStateException)v.c(new IllegalStateException("Unknown biome id: " + integer));
			} else {
				a.warn("Unknown biome id: ", integer);
				return brk.b;
			}
		} else {
			return bre3;
		}
	}

	public bre a(int integer1, int integer2) {
		return this.a(this.b.a(integer1, integer2));
	}
}
