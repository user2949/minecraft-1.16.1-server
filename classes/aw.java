import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class aw {
	public static final aw a = new aw(bx.c.e, bx.c.e, bx.c.e, bx.c.e, bx.c.e);
	private final bx.c b;
	private final bx.c c;
	private final bx.c d;
	private final bx.c e;
	private final bx.c f;

	public aw(bx.c c1, bx.c c2, bx.c c3, bx.c c4, bx.c c5) {
		this.b = c1;
		this.c = c2;
		this.d = c3;
		this.e = c4;
		this.f = c5;
	}

	public static aw a(bx.c c) {
		return new aw(bx.c.e, bx.c.e, bx.c.e, c, bx.c.e);
	}

	public static aw b(bx.c c) {
		return new aw(bx.c.e, c, bx.c.e, bx.c.e, bx.c.e);
	}

	public boolean a(double double1, double double2, double double3, double double4, double double5, double double6) {
		float float14 = (float)(double1 - double4);
		float float15 = (float)(double2 - double5);
		float float16 = (float)(double3 - double6);
		if (!this.b.d(aec.e(float14)) || !this.c.d(aec.e(float15)) || !this.d.d(aec.e(float16))) {
			return false;
		} else {
			return !this.e.a((double)(float14 * float14 + float16 * float16)) ? false : this.f.a((double)(float14 * float14 + float15 * float15 + float16 * float16));
		}
	}

	public static aw a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "distance");
			bx.c c3 = bx.c.a(jsonObject2.get("x"));
			bx.c c4 = bx.c.a(jsonObject2.get("y"));
			bx.c c5 = bx.c.a(jsonObject2.get("z"));
			bx.c c6 = bx.c.a(jsonObject2.get("horizontal"));
			bx.c c7 = bx.c.a(jsonObject2.get("absolute"));
			return new aw(c3, c4, c5, c6, c7);
		} else {
			return a;
		}
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.add("x", this.b.d());
			jsonObject2.add("y", this.c.d());
			jsonObject2.add("z", this.d.d());
			jsonObject2.add("horizontal", this.e.d());
			jsonObject2.add("absolute", this.f.d());
			return jsonObject2;
		}
	}
}
