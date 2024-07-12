import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.MutableInt;

public class dav {
	private final dbo[] a;
	private final ddm[] b;
	private final Predicate<dat> c;
	private final dch[] d;
	private final BiFunction<bki, dat, bki> e;
	private final daz f;
	private final dbb g;

	private dav(dbo[] arr, ddm[] arr, dch[] arr, daz daz, dbb dbb) {
		this.a = arr;
		this.b = arr;
		this.c = ddo.a(arr);
		this.d = arr;
		this.e = dcj.a(arr);
		this.f = daz;
		this.g = dbb;
	}

	private void b(Consumer<bki> consumer, dat dat) {
		Random random4 = dat.a();
		List<dbn> list5 = Lists.<dbn>newArrayList();
		MutableInt mutableInt6 = new MutableInt();

		for (dbo dbo10 : this.a) {
			dbo10.expand(dat, dbn -> {
				int integer5 = dbn.a(dat.b());
				if (integer5 > 0) {
					list5.add(dbn);
					mutableInt6.add(integer5);
				}
			});
		}

		int integer7 = list5.size();
		if (mutableInt6.intValue() != 0 && integer7 != 0) {
			if (integer7 == 1) {
				((dbn)list5.get(0)).a(consumer, dat);
			} else {
				int integer8 = random4.nextInt(mutableInt6.intValue());

				for (dbn dbn10 : list5) {
					integer8 -= dbn10.a(dat.b());
					if (integer8 < 0) {
						dbn10.a(consumer, dat);
						return;
					}
				}
			}
		}
	}

	public void a(Consumer<bki> consumer, dat dat) {
		if (this.c.test(dat)) {
			Consumer<bki> consumer4 = dch.a(this.e, consumer, dat);
			Random random5 = dat.a();
			int integer6 = this.f.a(random5) + aec.d(this.g.b(random5) * dat.b());

			for (int integer7 = 0; integer7 < integer6; integer7++) {
				this.b(consumer4, dat);
			}
		}
	}

	public void a(dbe dbe) {
		for (int integer3 = 0; integer3 < this.b.length; integer3++) {
			this.b[integer3].a(dbe.b(".condition[" + integer3 + "]"));
		}

		for (int integer3 = 0; integer3 < this.d.length; integer3++) {
			this.d[integer3].a(dbe.b(".functions[" + integer3 + "]"));
		}

		for (int integer3 = 0; integer3 < this.a.length; integer3++) {
			this.a[integer3].a(dbe.b(".entries[" + integer3 + "]"));
		}
	}

	public static dav.a a() {
		return new dav.a();
	}

	public static class a implements dce<dav.a>, ddf<dav.a> {
		private final List<dbo> a = Lists.<dbo>newArrayList();
		private final List<ddm> b = Lists.<ddm>newArrayList();
		private final List<dch> c = Lists.<dch>newArrayList();
		private daz d = new dbb(1.0F);
		private dbb e = new dbb(0.0F, 0.0F);

		public dav.a a(daz daz) {
			this.d = daz;
			return this;
		}

		public dav.a c() {
			return this;
		}

		public dav.a a(dbo.a<?> a) {
			this.a.add(a.b());
			return this;
		}

		public dav.a b(ddm.a a) {
			this.b.add(a.build());
			return this;
		}

		public dav.a b(dch.a a) {
			this.c.add(a.b());
			return this;
		}

		public dav b() {
			if (this.d == null) {
				throw new IllegalArgumentException("Rolls not set");
			} else {
				return new dav((dbo[])this.a.toArray(new dbo[0]), (ddm[])this.b.toArray(new ddm[0]), (dch[])this.c.toArray(new dch[0]), this.d, this.e);
			}
		}
	}

	public static class b implements JsonDeserializer<dav>, JsonSerializer<dav> {
		public dav deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			JsonObject jsonObject5 = adt.m(jsonElement, "loot pool");
			dbo[] arr6 = adt.a(jsonObject5, "entries", jsonDeserializationContext, dbo[].class);
			ddm[] arr7 = adt.a(jsonObject5, "conditions", new ddm[0], jsonDeserializationContext, ddm[].class);
			dch[] arr8 = adt.a(jsonObject5, "functions", new dch[0], jsonDeserializationContext, dch[].class);
			daz daz9 = dba.a(jsonObject5.get("rolls"), jsonDeserializationContext);
			dbb dbb10 = adt.a(jsonObject5, "bonus_rolls", new dbb(0.0F, 0.0F), jsonDeserializationContext, dbb.class);
			return new dav(arr6, arr7, arr8, daz9, dbb10);
		}

		public JsonElement serialize(dav dav, Type type, JsonSerializationContext jsonSerializationContext) {
			JsonObject jsonObject5 = new JsonObject();
			jsonObject5.add("rolls", dba.a(dav.f, jsonSerializationContext));
			jsonObject5.add("entries", jsonSerializationContext.serialize(dav.a));
			if (dav.g.b() != 0.0F && dav.g.c() != 0.0F) {
				jsonObject5.add("bonus_rolls", jsonSerializationContext.serialize(dav.g));
			}

			if (!ArrayUtils.isEmpty((Object[])dav.b)) {
				jsonObject5.add("conditions", jsonSerializationContext.serialize(dav.b));
			}

			if (!ArrayUtils.isEmpty((Object[])dav.d)) {
				jsonObject5.add("functions", jsonSerializationContext.serialize(dav.d));
			}

			return jsonObject5;
		}
	}
}
