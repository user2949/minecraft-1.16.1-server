import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class jl {
	private final bmr a;
	private final bmr b;
	private final bke c;
	private final w.a d = w.a.a();
	private final bmw<?> e;

	public jl(bmw<?> bmw, bmr bmr2, bmr bmr3, bke bke) {
		this.e = bmw;
		this.a = bmr2;
		this.b = bmr3;
		this.c = bke;
	}

	public static jl a(bmr bmr1, bmr bmr2, bke bke) {
		return new jl(bmw.u, bmr1, bmr2, bke);
	}

	public jl a(String string, ae ae) {
		this.d.a(string, ae);
		return this;
	}

	public void a(Consumer<je> consumer, String string) {
		this.a(consumer, new uh(string));
	}

	public void a(Consumer<je> consumer, uh uh) {
		this.a(uh);
		this.d.a(new uh("recipes/root")).a("has_the_recipe", cf.a(uh)).a(z.a.c(uh)).a(ah.b);
		consumer.accept(new jl.a(uh, this.e, this.a, this.b, this.c, this.d, new uh(uh.b(), "recipes/" + this.c.q().c() + "/" + uh.a())));
	}

	private void a(uh uh) {
		if (this.d.c().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + uh);
		}
	}

	public static class a implements je {
		private final uh a;
		private final bmr b;
		private final bmr c;
		private final bke d;
		private final w.a e;
		private final uh f;
		private final bmw<?> g;

		public a(uh uh1, bmw<?> bmw, bmr bmr3, bmr bmr4, bke bke, w.a a, uh uh7) {
			this.a = uh1;
			this.g = bmw;
			this.b = bmr3;
			this.c = bmr4;
			this.d = bke;
			this.e = a;
			this.f = uh7;
		}

		@Override
		public void a(JsonObject jsonObject) {
			jsonObject.add("base", this.b.c());
			jsonObject.add("addition", this.c.c());
			JsonObject jsonObject3 = new JsonObject();
			jsonObject3.addProperty("item", gl.am.b(this.d).toString());
			jsonObject.add("result", jsonObject3);
		}

		@Override
		public uh b() {
			return this.a;
		}

		@Override
		public bmw<?> c() {
			return this.g;
		}

		@Nullable
		@Override
		public JsonObject d() {
			return this.e.b();
		}

		@Nullable
		@Override
		public uh e() {
			return this.f;
		}
	}
}
