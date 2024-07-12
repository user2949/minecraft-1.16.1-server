import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dde implements ddm {
	private static final Logger a = LogManager.getLogger();
	private final uh b;

	private dde(uh uh) {
		this.b = uh;
	}

	@Override
	public ddn b() {
		return ddo.o;
	}

	@Override
	public void a(dbe dbe) {
		if (dbe.b(this.b)) {
			dbe.a("Condition " + this.b + " is recursively called");
		} else {
			ddm.super.a(dbe);
			ddm ddm3 = dbe.d(this.b);
			if (ddm3 == null) {
				dbe.a("Unknown condition table called " + this.b);
			} else {
				ddm3.a(dbe.a(".{" + this.b + "}", this.b));
			}
		}
	}

	public boolean test(dat dat) {
		ddm ddm3 = dat.b(this.b);
		if (dat.a(ddm3)) {
			boolean var3;
			try {
				var3 = ddm3.test(dat);
			} finally {
				dat.b(ddm3);
			}

			return var3;
		} else {
			a.warn("Detected infinite loop in loot tables");
			return false;
		}
	}

	public static class a implements dbc<dde> {
		public void a(JsonObject jsonObject, dde dde, JsonSerializationContext jsonSerializationContext) {
			jsonObject.addProperty("name", dde.b.toString());
		}

		public dde a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			uh uh4 = new uh(adt.h(jsonObject, "name"));
			return new dde(uh4);
		}
	}
}
