import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Locale;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dcc extends dcg {
	private static final Logger d = LogManager.getLogger();
	public static final cml<?> a = cml.p;
	public static final czs.a b = czs.a.MANSION;
	private final cml<?> e;
	private final czs.a f;
	private final byte g;
	private final int h;
	private final boolean i;

	private dcc(ddm[] arr, cml<?> cml, czs.a a, byte byte4, int integer, boolean boolean6) {
		super(arr);
		this.e = cml;
		this.f = a;
		this.g = byte4;
		this.h = integer;
		this.i = boolean6;
	}

	@Override
	public dci b() {
		return dcj.k;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.f);
	}

	@Override
	public bki a(bki bki, dat dat) {
		if (bki.b() != bkk.pb) {
			return bki;
		} else {
			fu fu4 = dat.c(dda.f);
			if (fu4 != null) {
				zd zd5 = dat.c();
				fu fu6 = zd5.a(this.e, fu4, this.h, this.i);
				if (fu6 != null) {
					bki bki7 = bko.a(zd5, fu6.u(), fu6.w(), this.g, true, true);
					bko.a(zd5, bki7);
					czv.a(bki7, fu6, "+", this.f);
					bki7.a(new ne("filled_map." + this.e.i().toLowerCase(Locale.ROOT)));
					return bki7;
				}
			}

			return bki;
		}
	}

	public static dcc.a c() {
		return new dcc.a();
	}

	public static class a extends dcg.a<dcc.a> {
		private cml<?> a;
		private czs.a b;
		private byte c;
		private int d;
		private boolean e;

		public a() {
			this.a = dcc.a;
			this.b = dcc.b;
			this.c = 2;
			this.d = 50;
			this.e = true;
		}

		protected dcc.a d() {
			return this;
		}

		public dcc.a a(cml<?> cml) {
			this.a = cml;
			return this;
		}

		public dcc.a a(czs.a a) {
			this.b = a;
			return this;
		}

		public dcc.a a(byte byte1) {
			this.c = byte1;
			return this;
		}

		public dcc.a a(boolean boolean1) {
			this.e = boolean1;
			return this;
		}

		@Override
		public dch b() {
			return new dcc(this.g(), this.a, this.b, this.c, this.d, this.e);
		}
	}

	public static class b extends dcg.c<dcc> {
		public void a(JsonObject jsonObject, dcc dcc, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dcc, jsonSerializationContext);
			if (!dcc.e.equals(dcc.a)) {
				jsonObject.add("destination", jsonSerializationContext.serialize(dcc.e.i()));
			}

			if (dcc.f != dcc.b) {
				jsonObject.add("decoration", jsonSerializationContext.serialize(dcc.f.toString().toLowerCase(Locale.ROOT)));
			}

			if (dcc.g != 2) {
				jsonObject.addProperty("zoom", dcc.g);
			}

			if (dcc.h != 50) {
				jsonObject.addProperty("search_radius", dcc.h);
			}

			if (!dcc.i) {
				jsonObject.addProperty("skip_existing_chunks", dcc.i);
			}
		}

		public dcc b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			cml<?> cml5 = a(jsonObject);
			String string6 = jsonObject.has("decoration") ? adt.h(jsonObject, "decoration") : "mansion";
			czs.a a7 = dcc.b;

			try {
				a7 = czs.a.valueOf(string6.toUpperCase(Locale.ROOT));
			} catch (IllegalArgumentException var10) {
				dcc.d.error("Error while parsing loot table decoration entry. Found {}. Defaulting to " + dcc.b, string6);
			}

			byte byte8 = adt.a(jsonObject, "zoom", (byte)2);
			int integer9 = adt.a(jsonObject, "search_radius", 50);
			boolean boolean10 = adt.a(jsonObject, "skip_existing_chunks", true);
			return new dcc(arr, cml5, a7, byte8, integer9, boolean10);
		}

		private static cml<?> a(JsonObject jsonObject) {
			if (jsonObject.has("destination")) {
				String string2 = adt.h(jsonObject, "destination");
				cml<?> cml3 = (cml<?>)cml.a.get(string2.toLowerCase(Locale.ROOT));
				if (cml3 != null) {
					return cml3;
				}
			}

			return dcc.a;
		}
	}
}
