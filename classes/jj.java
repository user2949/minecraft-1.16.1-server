import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class jj {
	private final bke a;
	private final bmr b;
	private final int c;
	private final w.a d = w.a.a();
	private String e;
	private final bmw<?> f;

	public jj(bmw<?> bmw, bmr bmr, bqa bqa, int integer) {
		this.f = bmw;
		this.a = bqa.h();
		this.b = bmr;
		this.c = integer;
	}

	public static jj a(bmr bmr, bqa bqa) {
		return new jj(bmw.t, bmr, bqa, 1);
	}

	public static jj a(bmr bmr, bqa bqa, int integer) {
		return new jj(bmw.t, bmr, bqa, integer);
	}

	public jj a(String string, ae ae) {
		this.d.a(string, ae);
		return this;
	}

	public void a(Consumer<je> consumer, String string) {
		uh uh4 = gl.am.b(this.a);
		if (new uh(string).equals(uh4)) {
			throw new IllegalStateException("Single Item Recipe " + string + " should remove its 'save' argument");
		} else {
			this.a(consumer, new uh(string));
		}
	}

	public void a(Consumer<je> consumer, uh uh) {
		this.a(uh);
		this.d.a(new uh("recipes/root")).a("has_the_recipe", cf.a(uh)).a(z.a.c(uh)).a(ah.b);
		consumer.accept(
			new jj.a(uh, this.f, this.e == null ? "" : this.e, this.b, this.a, this.c, this.d, new uh(uh.b(), "recipes/" + this.a.q().c() + "/" + uh.a()))
		);
	}

	private void a(uh uh) {
		if (this.d.c().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + uh);
		}
	}

	public static class a implements je {
		private final uh a;
		private final String b;
		private final bmr c;
		private final bke d;
		private final int e;
		private final w.a f;
		private final uh g;
		private final bmw<?> h;

		public a(uh uh1, bmw<?> bmw, String string, bmr bmr, bke bke, int integer, w.a a, uh uh8) {
			this.a = uh1;
			this.h = bmw;
			this.b = string;
			this.c = bmr;
			this.d = bke;
			this.e = integer;
			this.f = a;
			this.g = uh8;
		}

		@Override
		public void a(JsonObject jsonObject) {
			if (!this.b.isEmpty()) {
				jsonObject.addProperty("group", this.b);
			}

			jsonObject.add("ingredient", this.c.c());
			jsonObject.addProperty("result", gl.am.b(this.d).toString());
			jsonObject.addProperty("count", this.e);
		}

		@Override
		public uh b() {
			return this.a;
		}

		@Override
		public bmw<?> c() {
			return this.h;
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
