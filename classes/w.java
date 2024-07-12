import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;

public class w {
	private final w a;
	private final af b;
	private final z c;
	private final uh d;
	private final Map<String, ab> e;
	private final String[][] f;
	private final Set<w> g = Sets.<w>newLinkedHashSet();
	private final mr h;

	public w(uh uh, @Nullable w w, @Nullable af af, z z, Map<String, ab> map, String[][] arr) {
		this.d = uh;
		this.b = af;
		this.e = ImmutableMap.copyOf(map);
		this.a = w;
		this.c = z;
		this.f = arr;
		if (w != null) {
			w.a(this);
		}

		if (af == null) {
			this.h = new nd(uh.toString());
		} else {
			mr mr8 = af.a();
			i i9 = af.e().c();
			mr mr10 = ms.a(mr8.e(), nb.b.a(i9)).c("\n").a(af.b());
			mr mr11 = mr8.e().a(nb -> nb.a(new mv(mv.a.a, mr10)));
			this.h = new nd("[").a(mr11).c("]").a(i9);
		}
	}

	public w.a a() {
		return new w.a(this.a == null ? null : this.a.h(), this.b, this.c, this.e, this.f);
	}

	@Nullable
	public w b() {
		return this.a;
	}

	@Nullable
	public af c() {
		return this.b;
	}

	public z d() {
		return this.c;
	}

	public String toString() {
		return "SimpleAdvancement{id="
			+ this.h()
			+ ", parent="
			+ (this.a == null ? "null" : this.a.h())
			+ ", display="
			+ this.b
			+ ", rewards="
			+ this.c
			+ ", criteria="
			+ this.e
			+ ", requirements="
			+ Arrays.deepToString(this.f)
			+ '}';
	}

	public Iterable<w> e() {
		return this.g;
	}

	public Map<String, ab> f() {
		return this.e;
	}

	public void a(w w) {
		this.g.add(w);
	}

	public uh h() {
		return this.d;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof w)) {
			return false;
		} else {
			w w3 = (w)object;
			return this.d.equals(w3.d);
		}
	}

	public int hashCode() {
		return this.d.hashCode();
	}

	public String[][] i() {
		return this.f;
	}

	public mr j() {
		return this.h;
	}

	public static class a {
		private uh a;
		private w b;
		private af c;
		private z d = z.a;
		private Map<String, ab> e = Maps.<String, ab>newLinkedHashMap();
		private String[][] f;
		private ah g = ah.a;

		private a(@Nullable uh uh, @Nullable af af, z z, Map<String, ab> map, String[][] arr) {
			this.a = uh;
			this.c = af;
			this.d = z;
			this.e = map;
			this.f = arr;
		}

		private a() {
		}

		public static w.a a() {
			return new w.a();
		}

		public w.a a(w w) {
			this.b = w;
			return this;
		}

		public w.a a(uh uh) {
			this.a = uh;
			return this;
		}

		public w.a a(bki bki, mr mr2, mr mr3, @Nullable uh uh, ag ag, boolean boolean6, boolean boolean7, boolean boolean8) {
			return this.a(new af(bki, mr2, mr3, uh, ag, boolean6, boolean7, boolean8));
		}

		public w.a a(bqa bqa, mr mr2, mr mr3, @Nullable uh uh, ag ag, boolean boolean6, boolean boolean7, boolean boolean8) {
			return this.a(new af(new bki(bqa.h()), mr2, mr3, uh, ag, boolean6, boolean7, boolean8));
		}

		public w.a a(af af) {
			this.c = af;
			return this;
		}

		public w.a a(z.a a) {
			return this.a(a.a());
		}

		public w.a a(z z) {
			this.d = z;
			return this;
		}

		public w.a a(String string, ae ae) {
			return this.a(string, new ab(ae));
		}

		public w.a a(String string, ab ab) {
			if (this.e.containsKey(string)) {
				throw new IllegalArgumentException("Duplicate criterion " + string);
			} else {
				this.e.put(string, ab);
				return this;
			}
		}

		public w.a a(ah ah) {
			this.g = ah;
			return this;
		}

		public boolean a(Function<uh, w> function) {
			if (this.a == null) {
				return true;
			} else {
				if (this.b == null) {
					this.b = (w)function.apply(this.a);
				}

				return this.b != null;
			}
		}

		public w b(uh uh) {
			if (!this.a(uhx -> null)) {
				throw new IllegalStateException("Tried to build incomplete advancement!");
			} else {
				if (this.f == null) {
					this.f = this.g.createRequirements(this.e.keySet());
				}

				return new w(uh, this.b, this.c, this.d, this.e, this.f);
			}
		}

		public w a(Consumer<w> consumer, String string) {
			w w4 = this.b(new uh(string));
			consumer.accept(w4);
			return w4;
		}

		public JsonObject b() {
			if (this.f == null) {
				this.f = this.g.createRequirements(this.e.keySet());
			}

			JsonObject jsonObject2 = new JsonObject();
			if (this.b != null) {
				jsonObject2.addProperty("parent", this.b.h().toString());
			} else if (this.a != null) {
				jsonObject2.addProperty("parent", this.a.toString());
			}

			if (this.c != null) {
				jsonObject2.add("display", this.c.k());
			}

			jsonObject2.add("rewards", this.d.b());
			JsonObject jsonObject3 = new JsonObject();

			for (Entry<String, ab> entry5 : this.e.entrySet()) {
				jsonObject3.add((String)entry5.getKey(), ((ab)entry5.getValue()).b());
			}

			jsonObject2.add("criteria", jsonObject3);
			JsonArray jsonArray4 = new JsonArray();

			for (String[] arr8 : this.f) {
				JsonArray jsonArray9 = new JsonArray();

				for (String string13 : arr8) {
					jsonArray9.add(string13);
				}

				jsonArray4.add(jsonArray9);
			}

			jsonObject2.add("requirements", jsonArray4);
			return jsonObject2;
		}

		public void a(mg mg) {
			if (this.a == null) {
				mg.writeBoolean(false);
			} else {
				mg.writeBoolean(true);
				mg.a(this.a);
			}

			if (this.c == null) {
				mg.writeBoolean(false);
			} else {
				mg.writeBoolean(true);
				this.c.a(mg);
			}

			ab.a(this.e, mg);
			mg.d(this.f.length);

			for (String[] arr6 : this.f) {
				mg.d(arr6.length);

				for (String string10 : arr6) {
					mg.a(string10);
				}
			}
		}

		public String toString() {
			return "Task Advancement{parentId="
				+ this.a
				+ ", display="
				+ this.c
				+ ", rewards="
				+ this.d
				+ ", criteria="
				+ this.e
				+ ", requirements="
				+ Arrays.deepToString(this.f)
				+ '}';
		}

		public static w.a a(JsonObject jsonObject, av av) {
			uh uh3 = jsonObject.has("parent") ? new uh(adt.h(jsonObject, "parent")) : null;
			af af4 = jsonObject.has("display") ? af.a(adt.t(jsonObject, "display")) : null;
			z z5 = jsonObject.has("rewards") ? z.a(adt.t(jsonObject, "rewards")) : z.a;
			Map<String, ab> map6 = ab.b(adt.t(jsonObject, "criteria"), av);
			if (map6.isEmpty()) {
				throw new JsonSyntaxException("Advancement criteria cannot be empty");
			} else {
				JsonArray jsonArray7 = adt.a(jsonObject, "requirements", new JsonArray());
				String[][] arr8 = new String[jsonArray7.size()][];

				for (int integer9 = 0; integer9 < jsonArray7.size(); integer9++) {
					JsonArray jsonArray10 = adt.n(jsonArray7.get(integer9), "requirements[" + integer9 + "]");
					arr8[integer9] = new String[jsonArray10.size()];

					for (int integer11 = 0; integer11 < jsonArray10.size(); integer11++) {
						arr8[integer9][integer11] = adt.a(jsonArray10.get(integer11), "requirements[" + integer9 + "][" + integer11 + "]");
					}
				}

				if (arr8.length == 0) {
					arr8 = new String[map6.size()][];
					int integer9 = 0;

					for (String string11 : map6.keySet()) {
						arr8[integer9++] = new String[]{string11};
					}
				}

				for (String[] arr12 : arr8) {
					if (arr12.length == 0 && map6.isEmpty()) {
						throw new JsonSyntaxException("Requirement entry cannot be empty");
					}

					for (String string16 : arr12) {
						if (!map6.containsKey(string16)) {
							throw new JsonSyntaxException("Unknown required criterion '" + string16 + "'");
						}
					}
				}

				for (String string10 : map6.keySet()) {
					boolean boolean11 = false;

					for (String[] arr15 : arr8) {
						if (ArrayUtils.contains(arr15, string10)) {
							boolean11 = true;
							break;
						}
					}

					if (!boolean11) {
						throw new JsonSyntaxException(
							"Criterion '" + string10 + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required."
						);
					}
				}

				return new w.a(uh3, af4, z5, map6, arr8);
			}
		}

		public static w.a b(mg mg) {
			uh uh2 = mg.readBoolean() ? mg.o() : null;
			af af3 = mg.readBoolean() ? af.b(mg) : null;
			Map<String, ab> map4 = ab.c(mg);
			String[][] arr5 = new String[mg.i()][];

			for (int integer6 = 0; integer6 < arr5.length; integer6++) {
				arr5[integer6] = new String[mg.i()];

				for (int integer7 = 0; integer7 < arr5[integer6].length; integer7++) {
					arr5[integer6][integer7] = mg.e(32767);
				}
			}

			return new w.a(uh2, af3, z.a, map4, arr5);
		}

		public Map<String, ab> c() {
			return this.e;
		}
	}
}
