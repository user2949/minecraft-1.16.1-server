import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class ji {
	private final bke a;
	private final bmr b;
	private final float c;
	private final int d;
	private final w.a e = w.a.a();
	private String f;
	private final bnd<?> g;

	private ji(bqa bqa, bmr bmr, float float3, int integer, bnd<?> bnd) {
		this.a = bqa.h();
		this.b = bmr;
		this.c = float3;
		this.d = integer;
		this.g = bnd;
	}

	public static ji a(bmr bmr, bqa bqa, float float3, int integer, bnd<?> bnd) {
		return new ji(bqa, bmr, float3, integer, bnd);
	}

	public static ji b(bmr bmr, bqa bqa, float float3, int integer) {
		return a(bmr, bqa, float3, integer, bmw.q);
	}

	public static ji c(bmr bmr, bqa bqa, float float3, int integer) {
		return a(bmr, bqa, float3, integer, bmw.p);
	}

	public ji a(String string, ae ae) {
		this.e.a(string, ae);
		return this;
	}

	public void a(Consumer<je> consumer) {
		this.a(consumer, gl.am.b(this.a));
	}

	public void a(Consumer<je> consumer, String string) {
		uh uh4 = gl.am.b(this.a);
		uh uh5 = new uh(string);
		if (uh5.equals(uh4)) {
			throw new IllegalStateException("Recipe " + uh5 + " should remove its 'save' argument");
		} else {
			this.a(consumer, uh5);
		}
	}

	public void a(Consumer<je> consumer, uh uh) {
		this.a(uh);
		this.e.a(new uh("recipes/root")).a("has_the_recipe", cf.a(uh)).a(z.a.c(uh)).a(ah.b);
		consumer.accept(
			new ji.a(
				uh,
				this.f == null ? "" : this.f,
				this.b,
				this.a,
				this.c,
				this.d,
				this.e,
				new uh(uh.b(), "recipes/" + this.a.q().c() + "/" + uh.a()),
				(bmw<? extends bmg>)this.g
			)
		);
	}

	private void a(uh uh) {
		if (this.e.c().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + uh);
		}
	}

	public static class a implements je {
		private final uh a;
		private final String b;
		private final bmr c;
		private final bke d;
		private final float e;
		private final int f;
		private final w.a g;
		private final uh h;
		private final bmw<? extends bmg> i;

		public a(uh uh1, String string, bmr bmr, bke bke, float float5, int integer, w.a a, uh uh8, bmw<? extends bmg> bmw) {
			this.a = uh1;
			this.b = string;
			this.c = bmr;
			this.d = bke;
			this.e = float5;
			this.f = integer;
			this.g = a;
			this.h = uh8;
			this.i = bmw;
		}

		@Override
		public void a(JsonObject jsonObject) {
			if (!this.b.isEmpty()) {
				jsonObject.addProperty("group", this.b);
			}

			jsonObject.add("ingredient", this.c.c());
			jsonObject.addProperty("result", gl.am.b(this.d).toString());
			jsonObject.addProperty("experience", this.e);
			jsonObject.addProperty("cookingtime", this.f);
		}

		@Override
		public bmw<?> c() {
			return this.i;
		}

		@Override
		public uh b() {
			return this.a;
		}

		@Nullable
		@Override
		public JsonObject d() {
			return this.g.b();
		}

		@Nullable
		@Override
		public uh e() {
			return this.h;
		}
	}
}
