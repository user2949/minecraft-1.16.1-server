import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class av {
	private static final Logger a = LogManager.getLogger();
	private final uh b;
	private final day c;
	private final Gson d = daq.a().create();

	public av(uh uh, day day) {
		this.b = uh;
		this.c = day;
	}

	public final ddm[] a(JsonArray jsonArray, String string, dcy dcy) {
		ddm[] arr5 = this.d.fromJson(jsonArray, ddm[].class);
		dbe dbe6 = new dbe(dcy, this.c::a, uh -> null);

		for (ddm ddm10 : arr5) {
			ddm10.a(dbe6);
			dbe6.a().forEach((string2, string3) -> a.warn("Found validation problem in advancement trigger {}/{}: {}", string, string2, string3));
		}

		return arr5;
	}

	public uh a() {
		return this.b;
	}
}
