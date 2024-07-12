import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class jp implements jo.a {
	private static final Logger a = LogManager.getLogger();

	@Override
	public le a(String string, le le) {
		return string.startsWith("data/minecraft/structures/") ? b(string, a(le)) : le;
	}

	private static le a(le le) {
		if (!le.c("DataVersion", 99)) {
			le.b("DataVersion", 500);
		}

		return le;
	}

	private static le b(String string, le le) {
		cve cve3 = new cve();
		int integer4 = le.h("DataVersion");
		int integer5 = 2532;
		if (integer4 < 2532) {
			a.warn("SNBT Too old, do not forget to update: " + integer4 + " < " + 2532 + ": " + string);
		}

		le le6 = lq.a(aep.a(), aeo.STRUCTURE, le, integer4);
		cve3.b(le6);
		return cve3.a(new le());
	}
}
