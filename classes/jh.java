import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class jh {
	private static final Logger a = LogManager.getLogger();
	private final bke b;
	private final int c;
	private final List<bmr> d = Lists.<bmr>newArrayList();
	private final w.a e = w.a.a();
	private String f;

	public jh(bqa bqa, int integer) {
		this.b = bqa.h();
		this.c = integer;
	}

	public static jh a(bqa bqa) {
		return new jh(bqa, 1);
	}

	public static jh a(bqa bqa, int integer) {
		return new jh(bqa, integer);
	}

	public jh a(adf<bke> adf) {
		return this.a(bmr.a(adf));
	}

	public jh b(bqa bqa) {
		return this.b(bqa, 1);
	}

	public jh b(bqa bqa, int integer) {
		for (int integer4 = 0; integer4 < integer; integer4++) {
			this.a(bmr.a(bqa));
		}

		return this;
	}

	public jh a(bmr bmr) {
		return this.a(bmr, 1);
	}

	public jh a(bmr bmr, int integer) {
		for (int integer4 = 0; integer4 < integer; integer4++) {
			this.d.add(bmr);
		}

		return this;
	}

	public jh a(String string, ae ae) {
		this.e.a(string, ae);
		return this;
	}

	public jh a(String string) {
		this.f = string;
		return this;
	}

	public void a(Consumer<je> consumer) {
		this.a(consumer, gl.am.b(this.b));
	}

	public void a(Consumer<je> consumer, String string) {
		uh uh4 = gl.am.b(this.b);
		if (new uh(string).equals(uh4)) {
			throw new IllegalStateException("Shapeless Recipe " + string + " should remove its 'save' argument");
		} else {
			this.a(consumer, new uh(string));
		}
	}

	public void a(Consumer<je> consumer, uh uh) {
		this.a(uh);
		this.e.a(new uh("recipes/root")).a("has_the_recipe", cf.a(uh)).a(z.a.c(uh)).a(ah.b);
		consumer.accept(new jh.a(uh, this.b, this.c, this.f == null ? "" : this.f, this.d, this.e, new uh(uh.b(), "recipes/" + this.b.q().c() + "/" + uh.a())));
	}

	private void a(uh uh) {
		if (this.e.c().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + uh);
		}
	}

	public static class a implements je {
		private final uh a;
		private final bke b;
		private final int c;
		private final String d;
		private final List<bmr> e;
		private final w.a f;
		private final uh g;

		public a(uh uh1, bke bke, int integer, String string, List<bmr> list, w.a a, uh uh7) {
			this.a = uh1;
			this.b = bke;
			this.c = integer;
			this.d = string;
			this.e = list;
			this.f = a;
			this.g = uh7;
		}

		@Override
		public void a(JsonObject jsonObject) {
			if (!this.d.isEmpty()) {
				jsonObject.addProperty("group", this.d);
			}

			JsonArray jsonArray3 = new JsonArray();

			for (bmr bmr5 : this.e) {
				jsonArray3.add(bmr5.c());
			}

			jsonObject.add("ingredients", jsonArray3);
			JsonObject jsonObject4 = new JsonObject();
			jsonObject4.addProperty("item", gl.am.b(this.b).toString());
			if (this.c > 1) {
				jsonObject4.addProperty("count", this.c);
			}

			jsonObject.add("result", jsonObject4);
		}

		@Override
		public bmw<?> c() {
			return bmw.b;
		}

		@Override
		public uh b() {
			return this.a;
		}

		@Nullable
		@Override
		public JsonObject d() {
			return this.f.b();
		}

		@Nullable
		@Override
		public uh e() {
			return this.g;
		}
	}
}
