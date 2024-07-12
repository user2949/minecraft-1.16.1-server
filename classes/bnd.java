import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class bnd<T extends bmg> implements bmw<T> {
	private final int v;
	private final bnd.a<T> w;

	public bnd(bnd.a<T> a, int integer) {
		this.v = integer;
		this.w = a;
	}

	public T a(uh uh, JsonObject jsonObject) {
		String string4 = adt.a(jsonObject, "group", "");
		JsonElement jsonElement5 = (JsonElement)(adt.d(jsonObject, "ingredient") ? adt.u(jsonObject, "ingredient") : adt.t(jsonObject, "ingredient"));
		bmr bmr6 = bmr.a(jsonElement5);
		String string7 = adt.h(jsonObject, "result");
		uh uh8 = new uh(string7);
		bki bki9 = new bki((bqa)gl.am.b(uh8).orElseThrow(() -> new IllegalStateException("Item: " + string7 + " does not exist")));
		float float10 = adt.a(jsonObject, "experience", 0.0F);
		int integer11 = adt.a(jsonObject, "cookingtime", this.v);
		return this.w.create(uh, string4, bmr6, bki9, float10, integer11);
	}

	public T a(uh uh, mg mg) {
		String string4 = mg.e(32767);
		bmr bmr5 = bmr.b(mg);
		bki bki6 = mg.m();
		float float7 = mg.readFloat();
		int integer8 = mg.i();
		return this.w.create(uh, string4, bmr5, bki6, float7, integer8);
	}

	public void a(mg mg, T bmg) {
		mg.a(bmg.c);
		bmg.d.a(mg);
		mg.a(bmg.e);
		mg.writeFloat(bmg.f);
		mg.d(bmg.g);
	}

	interface a<T extends bmg> {
		T create(uh uh, String string, bmr bmr, bki bki, float float5, int integer);
	}
}
