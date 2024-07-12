import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dn.h;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class dbz extends dcg {
	private final dbz.c a;
	private final List<dbz.b> b;
	private static final Function<aom, lu> d = bz::b;
	private static final Function<cdl, lu> e = cdl -> cdl.a(new le());

	private dbz(ddm[] arr, dbz.c c, List<dbz.b> list) {
		super(arr);
		this.a = c;
		this.b = ImmutableList.copyOf(list);
	}

	@Override
	public dci b() {
		return dcj.u;
	}

	private static h b(String string) {
		try {
			return new dn().a(new StringReader(string));
		} catch (CommandSyntaxException var2) {
			throw new IllegalArgumentException("Failed to parse path " + string, var2);
		}
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(this.a.f);
	}

	@Override
	public bki a(bki bki, dat dat) {
		lu lu4 = (lu)this.a.g.apply(dat);
		if (lu4 != null) {
			this.b.forEach(b -> b.a(bki::p, lu4));
		}

		return bki;
	}

	public static dbz.a a(dbz.c c) {
		return new dbz.a(c);
	}

	public static class a extends dcg.a<dbz.a> {
		private final dbz.c a;
		private final List<dbz.b> b = Lists.<dbz.b>newArrayList();

		private a(dbz.c c) {
			this.a = c;
		}

		public dbz.a a(String string1, String string2, dbz.d d) {
			this.b.add(new dbz.b(string1, string2, d));
			return this;
		}

		public dbz.a a(String string1, String string2) {
			return this.a(string1, string2, dbz.d.REPLACE);
		}

		protected dbz.a d() {
			return this;
		}

		@Override
		public dch b() {
			return new dbz(this.g(), this.a, this.b);
		}
	}

	static class b {
		private final String a;
		private final h b;
		private final String c;
		private final h d;
		private final dbz.d e;

		private b(String string1, String string2, dbz.d d) {
			this.a = string1;
			this.b = dbz.b(string1);
			this.c = string2;
			this.d = dbz.b(string2);
			this.e = d;
		}

		public void a(Supplier<lu> supplier, lu lu) {
			try {
				List<lu> list4 = this.b.a(lu);
				if (!list4.isEmpty()) {
					this.e.a((lu)supplier.get(), this.d, list4);
				}
			} catch (CommandSyntaxException var4) {
			}
		}

		public JsonObject a() {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("source", this.a);
			jsonObject2.addProperty("target", this.c);
			jsonObject2.addProperty("op", this.e.d);
			return jsonObject2;
		}

		public static dbz.b a(JsonObject jsonObject) {
			String string2 = adt.h(jsonObject, "source");
			String string3 = adt.h(jsonObject, "target");
			dbz.d d4 = dbz.d.a(adt.h(jsonObject, "op"));
			return new dbz.b(string2, string3, d4);
		}
	}

	public static enum c {
		THIS("this", dda.a, dbz.d),
		KILLER("killer", dda.d, dbz.d),
		KILLER_PLAYER("killer_player", dda.b, dbz.d),
		BLOCK_ENTITY("block_entity", dda.i, dbz.e);

		public final String e;
		public final dcx<?> f;
		public final Function<dat, lu> g;

		private <T> c(String string3, dcx<T> dcx, Function<? super T, lu> function) {
			this.e = string3;
			this.f = dcx;
			this.g = dat -> {
				T object4 = dat.c(dcx);
				return object4 != null ? (lu)function.apply(object4) : null;
			};
		}

		public static dbz.c a(String string) {
			for (dbz.c c5 : values()) {
				if (c5.e.equals(string)) {
					return c5;
				}
			}

			throw new IllegalArgumentException("Invalid tag source " + string);
		}
	}

	public static enum d {
		REPLACE("replace") {
			@Override
			public void a(lu lu, h h, List<lu> list) throws CommandSyntaxException {
				h.b(lu, Iterables.getLast(list)::c);
			}
		},
		APPEND("append") {
			@Override
			public void a(lu lu, h h, List<lu> list) throws CommandSyntaxException {
				List<lu> list5 = h.a(lu, lk::new);
				list5.forEach(lux -> {
					if (lux instanceof lk) {
						list.forEach(lu2 -> ((lk)lux).add(lu2.c()));
					}
				});
			}
		},
		MERGE("merge") {
			@Override
			public void a(lu lu, h h, List<lu> list) throws CommandSyntaxException {
				List<lu> list5 = h.a(lu, le::new);
				list5.forEach(lux -> {
					if (lux instanceof le) {
						list.forEach(lu2 -> {
							if (lu2 instanceof le) {
								((le)lux).a((le)lu2);
							}
						});
					}
				});
			}
		};

		private final String d;

		public abstract void a(lu lu, h h, List<lu> list) throws CommandSyntaxException;

		private d(String string3) {
			this.d = string3;
		}

		public static dbz.d a(String string) {
			for (dbz.d d5 : values()) {
				if (d5.d.equals(string)) {
					return d5;
				}
			}

			throw new IllegalArgumentException("Invalid merge strategy" + string);
		}
	}

	public static class e extends dcg.c<dbz> {
		public void a(JsonObject jsonObject, dbz dbz, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dbz, jsonSerializationContext);
			jsonObject.addProperty("source", dbz.a.e);
			JsonArray jsonArray5 = new JsonArray();
			dbz.b.stream().map(dbz.b::a).forEach(jsonArray5::add);
			jsonObject.add("ops", jsonArray5);
		}

		public dbz b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			dbz.c c5 = dbz.c.a(adt.h(jsonObject, "source"));
			List<dbz.b> list6 = Lists.<dbz.b>newArrayList();

			for (JsonElement jsonElement9 : adt.u(jsonObject, "ops")) {
				JsonObject jsonObject10 = adt.m(jsonElement9, "op");
				list6.add(dbz.b.a(jsonObject10));
			}

			return new dbz(arr, c5, list6);
		}
	}
}
