import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Objects;
import javax.annotation.Nullable;

public class nb {
	public static final uh a = new uh("minecraft", "default");
	public static final nb b = new nb(null, null, null, null, null, null, null, null, null, null);
	@Nullable
	private final nc c;
	@Nullable
	private final Boolean d;
	@Nullable
	private final Boolean e;
	@Nullable
	private final Boolean f;
	@Nullable
	private final Boolean g;
	@Nullable
	private final Boolean h;
	@Nullable
	private final mp i;
	@Nullable
	private final mv j;
	@Nullable
	private final String k;
	@Nullable
	private final uh l;

	private nb(
		@Nullable nc nc,
		@Nullable Boolean boolean2,
		@Nullable Boolean boolean3,
		@Nullable Boolean boolean4,
		@Nullable Boolean boolean5,
		@Nullable Boolean boolean6,
		@Nullable mp mp,
		@Nullable mv mv,
		@Nullable String string,
		@Nullable uh uh
	) {
		this.c = nc;
		this.d = boolean2;
		this.e = boolean3;
		this.f = boolean4;
		this.g = boolean5;
		this.h = boolean6;
		this.i = mp;
		this.j = mv;
		this.k = string;
		this.l = uh;
	}

	@Nullable
	public nc a() {
		return this.c;
	}

	public boolean b() {
		return this.d == Boolean.TRUE;
	}

	public boolean c() {
		return this.e == Boolean.TRUE;
	}

	public boolean d() {
		return this.g == Boolean.TRUE;
	}

	public boolean e() {
		return this.f == Boolean.TRUE;
	}

	public boolean f() {
		return this.h == Boolean.TRUE;
	}

	public boolean g() {
		return this == b;
	}

	@Nullable
	public mp h() {
		return this.i;
	}

	@Nullable
	public mv i() {
		return this.j;
	}

	@Nullable
	public String j() {
		return this.k;
	}

	public uh k() {
		return this.l != null ? this.l : a;
	}

	public nb a(@Nullable nc nc) {
		return new nb(nc, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l);
	}

	public nb a(@Nullable i i) {
		return this.a(i != null ? nc.a(i) : null);
	}

	public nb a(@Nullable Boolean boolean1) {
		return new nb(this.c, boolean1, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l);
	}

	public nb b(@Nullable Boolean boolean1) {
		return new nb(this.c, this.d, boolean1, this.f, this.g, this.h, this.i, this.j, this.k, this.l);
	}

	public nb a(@Nullable mp mp) {
		return new nb(this.c, this.d, this.e, this.f, this.g, this.h, mp, this.j, this.k, this.l);
	}

	public nb a(@Nullable mv mv) {
		return new nb(this.c, this.d, this.e, this.f, this.g, this.h, this.i, mv, this.k, this.l);
	}

	public nb a(@Nullable String string) {
		return new nb(this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, string, this.l);
	}

	public nb b(i i) {
		nc nc3 = this.c;
		Boolean boolean4 = this.d;
		Boolean boolean5 = this.e;
		Boolean boolean6 = this.g;
		Boolean boolean7 = this.f;
		Boolean boolean8 = this.h;
		switch (i) {
			case OBFUSCATED:
				boolean8 = true;
				break;
			case BOLD:
				boolean4 = true;
				break;
			case STRIKETHROUGH:
				boolean6 = true;
				break;
			case UNDERLINE:
				boolean7 = true;
				break;
			case ITALIC:
				boolean5 = true;
				break;
			case RESET:
				return b;
			default:
				nc3 = nc.a(i);
		}

		return new nb(nc3, boolean4, boolean5, boolean7, boolean6, boolean8, this.i, this.j, this.k, this.l);
	}

	public nb a(i... arr) {
		nc nc3 = this.c;
		Boolean boolean4 = this.d;
		Boolean boolean5 = this.e;
		Boolean boolean6 = this.g;
		Boolean boolean7 = this.f;
		Boolean boolean8 = this.h;

		for (i i12 : arr) {
			switch (i12) {
				case OBFUSCATED:
					boolean8 = true;
					break;
				case BOLD:
					boolean4 = true;
					break;
				case STRIKETHROUGH:
					boolean6 = true;
					break;
				case UNDERLINE:
					boolean7 = true;
					break;
				case ITALIC:
					boolean5 = true;
					break;
				case RESET:
					return b;
				default:
					nc3 = nc.a(i12);
			}
		}

		return new nb(nc3, boolean4, boolean5, boolean7, boolean6, boolean8, this.i, this.j, this.k, this.l);
	}

	public nb a(nb nb) {
		if (this == b) {
			return nb;
		} else {
			return nb == b
				? this
				: new nb(
					this.c != null ? this.c : nb.c,
					this.d != null ? this.d : nb.d,
					this.e != null ? this.e : nb.e,
					this.f != null ? this.f : nb.f,
					this.g != null ? this.g : nb.g,
					this.h != null ? this.h : nb.h,
					this.i != null ? this.i : nb.i,
					this.j != null ? this.j : nb.j,
					this.k != null ? this.k : nb.k,
					this.l != null ? this.l : nb.l
				);
		}
	}

	public String toString() {
		return "Style{ color="
			+ this.c
			+ ", bold="
			+ this.d
			+ ", italic="
			+ this.e
			+ ", underlined="
			+ this.f
			+ ", strikethrough="
			+ this.g
			+ ", obfuscated="
			+ this.h
			+ ", clickEvent="
			+ this.h()
			+ ", hoverEvent="
			+ this.i()
			+ ", insertion="
			+ this.j()
			+ ", font="
			+ this.k()
			+ '}';
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof nb)) {
			return false;
		} else {
			nb nb3 = (nb)object;
			return this.b() == nb3.b()
				&& Objects.equals(this.a(), nb3.a())
				&& this.c() == nb3.c()
				&& this.f() == nb3.f()
				&& this.d() == nb3.d()
				&& this.e() == nb3.e()
				&& Objects.equals(this.h(), nb3.h())
				&& Objects.equals(this.i(), nb3.i())
				&& Objects.equals(this.j(), nb3.j())
				&& Objects.equals(this.k(), nb3.k());
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k});
	}

	public static class a implements JsonDeserializer<nb>, JsonSerializer<nb> {
		@Nullable
		public nb deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			if (jsonElement.isJsonObject()) {
				JsonObject jsonObject5 = jsonElement.getAsJsonObject();
				if (jsonObject5 == null) {
					return null;
				} else {
					Boolean boolean6 = a(jsonObject5, "bold");
					Boolean boolean7 = a(jsonObject5, "italic");
					Boolean boolean8 = a(jsonObject5, "underlined");
					Boolean boolean9 = a(jsonObject5, "strikethrough");
					Boolean boolean10 = a(jsonObject5, "obfuscated");
					nc nc11 = e(jsonObject5);
					String string12 = d(jsonObject5);
					mp mp13 = c(jsonObject5);
					mv mv14 = b(jsonObject5);
					uh uh15 = a(jsonObject5);
					return new nb(nc11, boolean6, boolean7, boolean8, boolean9, boolean10, mp13, mv14, string12, uh15);
				}
			} else {
				return null;
			}
		}

		@Nullable
		private static uh a(JsonObject jsonObject) {
			if (jsonObject.has("font")) {
				String string2 = adt.h(jsonObject, "font");

				try {
					return new uh(string2);
				} catch (t var3) {
					throw new JsonSyntaxException("Invalid font name: " + string2);
				}
			} else {
				return null;
			}
		}

		@Nullable
		private static mv b(JsonObject jsonObject) {
			if (jsonObject.has("hoverEvent")) {
				JsonObject jsonObject2 = adt.t(jsonObject, "hoverEvent");
				mv mv3 = mv.a(jsonObject2);
				if (mv3 != null && mv3.a().a()) {
					return mv3;
				}
			}

			return null;
		}

		@Nullable
		private static mp c(JsonObject jsonObject) {
			if (jsonObject.has("clickEvent")) {
				JsonObject jsonObject2 = adt.t(jsonObject, "clickEvent");
				String string3 = adt.a(jsonObject2, "action", null);
				mp.a a4 = string3 == null ? null : mp.a.a(string3);
				String string5 = adt.a(jsonObject2, "value", null);
				if (a4 != null && string5 != null && a4.a()) {
					return new mp(a4, string5);
				}
			}

			return null;
		}

		@Nullable
		private static String d(JsonObject jsonObject) {
			return adt.a(jsonObject, "insertion", null);
		}

		@Nullable
		private static nc e(JsonObject jsonObject) {
			if (jsonObject.has("color")) {
				String string2 = adt.h(jsonObject, "color");
				return nc.a(string2);
			} else {
				return null;
			}
		}

		@Nullable
		private static Boolean a(JsonObject jsonObject, String string) {
			return jsonObject.has(string) ? jsonObject.get(string).getAsBoolean() : null;
		}

		@Nullable
		public JsonElement serialize(nb nb, Type type, JsonSerializationContext jsonSerializationContext) {
			if (nb.g()) {
				return null;
			} else {
				JsonObject jsonObject5 = new JsonObject();
				if (nb.d != null) {
					jsonObject5.addProperty("bold", nb.d);
				}

				if (nb.e != null) {
					jsonObject5.addProperty("italic", nb.e);
				}

				if (nb.f != null) {
					jsonObject5.addProperty("underlined", nb.f);
				}

				if (nb.g != null) {
					jsonObject5.addProperty("strikethrough", nb.g);
				}

				if (nb.h != null) {
					jsonObject5.addProperty("obfuscated", nb.h);
				}

				if (nb.c != null) {
					jsonObject5.addProperty("color", nb.c.b());
				}

				if (nb.k != null) {
					jsonObject5.add("insertion", jsonSerializationContext.serialize(nb.k));
				}

				if (nb.i != null) {
					JsonObject jsonObject6 = new JsonObject();
					jsonObject6.addProperty("action", nb.i.a().b());
					jsonObject6.addProperty("value", nb.i.b());
					jsonObject5.add("clickEvent", jsonObject6);
				}

				if (nb.j != null) {
					jsonObject5.add("hoverEvent", nb.j.b());
				}

				if (nb.l != null) {
					jsonObject5.addProperty("font", nb.l.toString());
				}

				return jsonObject5;
			}
		}
	}
}
