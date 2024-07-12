import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class jg {
	private static final Logger a = LogManager.getLogger();
	private final bke b;
	private final int c;
	private final List<String> d = Lists.<String>newArrayList();
	private final Map<Character, bmr> e = Maps.<Character, bmr>newLinkedHashMap();
	private final w.a f = w.a.a();
	private String g;

	public jg(bqa bqa, int integer) {
		this.b = bqa.h();
		this.c = integer;
	}

	public static jg a(bqa bqa) {
		return a(bqa, 1);
	}

	public static jg a(bqa bqa, int integer) {
		return new jg(bqa, integer);
	}

	public jg a(Character character, adf<bke> adf) {
		return this.a(character, bmr.a(adf));
	}

	public jg a(Character character, bqa bqa) {
		return this.a(character, bmr.a(bqa));
	}

	public jg a(Character character, bmr bmr) {
		if (this.e.containsKey(character)) {
			throw new IllegalArgumentException("Symbol '" + character + "' is already defined!");
		} else if (character == ' ') {
			throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
		} else {
			this.e.put(character, bmr);
			return this;
		}
	}

	public jg a(String string) {
		if (!this.d.isEmpty() && string.length() != ((String)this.d.get(0)).length()) {
			throw new IllegalArgumentException("Pattern must be the same width on every line!");
		} else {
			this.d.add(string);
			return this;
		}
	}

	public jg a(String string, ae ae) {
		this.f.a(string, ae);
		return this;
	}

	public jg b(String string) {
		this.g = string;
		return this;
	}

	public void a(Consumer<je> consumer) {
		this.a(consumer, gl.am.b(this.b));
	}

	public void a(Consumer<je> consumer, String string) {
		uh uh4 = gl.am.b(this.b);
		if (new uh(string).equals(uh4)) {
			throw new IllegalStateException("Shaped Recipe " + string + " should remove its 'save' argument");
		} else {
			this.a(consumer, new uh(string));
		}
	}

	public void a(Consumer<je> consumer, uh uh) {
		this.a(uh);
		this.f.a(new uh("recipes/root")).a("has_the_recipe", cf.a(uh)).a(z.a.c(uh)).a(ah.b);
		consumer.accept(
			new jg.a(uh, this.b, this.c, this.g == null ? "" : this.g, this.d, this.e, this.f, new uh(uh.b(), "recipes/" + this.b.q().c() + "/" + uh.a()))
		);
	}

	private void a(uh uh) {
		if (this.d.isEmpty()) {
			throw new IllegalStateException("No pattern is defined for shaped recipe " + uh + "!");
		} else {
			Set<Character> set3 = Sets.<Character>newHashSet(this.e.keySet());
			set3.remove(' ');

			for (String string5 : this.d) {
				for (int integer6 = 0; integer6 < string5.length(); integer6++) {
					char character7 = string5.charAt(integer6);
					if (!this.e.containsKey(character7) && character7 != ' ') {
						throw new IllegalStateException("Pattern in recipe " + uh + " uses undefined symbol '" + character7 + "'");
					}

					set3.remove(character7);
				}
			}

			if (!set3.isEmpty()) {
				throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + uh);
			} else if (this.d.size() == 1 && ((String)this.d.get(0)).length() == 1) {
				throw new IllegalStateException("Shaped recipe " + uh + " only takes in a single item - should it be a shapeless recipe instead?");
			} else if (this.f.c().isEmpty()) {
				throw new IllegalStateException("No way of obtaining recipe " + uh);
			}
		}
	}

	class a implements je {
		private final uh b;
		private final bke c;
		private final int d;
		private final String e;
		private final List<String> f;
		private final Map<Character, bmr> g;
		private final w.a h;
		private final uh i;

		public a(uh uh2, bke bke, int integer, String string, List<String> list, Map<Character, bmr> map, w.a a, uh uh9) {
			this.b = uh2;
			this.c = bke;
			this.d = integer;
			this.e = string;
			this.f = list;
			this.g = map;
			this.h = a;
			this.i = uh9;
		}

		@Override
		public void a(JsonObject jsonObject) {
			if (!this.e.isEmpty()) {
				jsonObject.addProperty("group", this.e);
			}

			JsonArray jsonArray3 = new JsonArray();

			for (String string5 : this.f) {
				jsonArray3.add(string5);
			}

			jsonObject.add("pattern", jsonArray3);
			JsonObject jsonObject4 = new JsonObject();

			for (Entry<Character, bmr> entry6 : this.g.entrySet()) {
				jsonObject4.add(String.valueOf(entry6.getKey()), ((bmr)entry6.getValue()).c());
			}

			jsonObject.add("key", jsonObject4);
			JsonObject jsonObject5 = new JsonObject();
			jsonObject5.addProperty("item", gl.am.b(this.c).toString());
			if (this.d > 1) {
				jsonObject5.addProperty("count", this.d);
			}

			jsonObject.add("result", jsonObject5);
		}

		@Override
		public bmw<?> c() {
			return bmw.a;
		}

		@Override
		public uh b() {
			return this.b;
		}

		@Nullable
		@Override
		public JsonObject d() {
			return this.h.b();
		}

		@Nullable
		@Override
		public uh e() {
			return this.i;
		}
	}
}
