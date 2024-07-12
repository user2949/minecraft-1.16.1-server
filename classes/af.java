import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;

public class af {
	private final mr a;
	private final mr b;
	private final bki c;
	private final uh d;
	private final ag e;
	private final boolean f;
	private final boolean g;
	private final boolean h;
	private float i;
	private float j;

	public af(bki bki, mr mr2, mr mr3, @Nullable uh uh, ag ag, boolean boolean6, boolean boolean7, boolean boolean8) {
		this.a = mr2;
		this.b = mr3;
		this.c = bki;
		this.d = uh;
		this.e = ag;
		this.f = boolean6;
		this.g = boolean7;
		this.h = boolean8;
	}

	public void a(float float1, float float2) {
		this.i = float1;
		this.j = float2;
	}

	public mr a() {
		return this.a;
	}

	public mr b() {
		return this.b;
	}

	public ag e() {
		return this.e;
	}

	public boolean i() {
		return this.g;
	}

	public boolean j() {
		return this.h;
	}

	public static af a(JsonObject jsonObject) {
		mr mr2 = mr.a.a(jsonObject.get("title"));
		mr mr3 = mr.a.a(jsonObject.get("description"));
		if (mr2 != null && mr3 != null) {
			bki bki4 = b(adt.t(jsonObject, "icon"));
			uh uh5 = jsonObject.has("background") ? new uh(adt.h(jsonObject, "background")) : null;
			ag ag6 = jsonObject.has("frame") ? ag.a(adt.h(jsonObject, "frame")) : ag.TASK;
			boolean boolean7 = adt.a(jsonObject, "show_toast", true);
			boolean boolean8 = adt.a(jsonObject, "announce_to_chat", true);
			boolean boolean9 = adt.a(jsonObject, "hidden", false);
			return new af(bki4, mr2, mr3, uh5, ag6, boolean7, boolean8, boolean9);
		} else {
			throw new JsonSyntaxException("Both title and description must be set");
		}
	}

	private static bki b(JsonObject jsonObject) {
		if (!jsonObject.has("item")) {
			throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
		} else {
			bke bke2 = adt.i(jsonObject, "item");
			if (jsonObject.has("data")) {
				throw new JsonParseException("Disallowed data tag found");
			} else {
				bki bki3 = new bki(bke2);
				if (jsonObject.has("nbt")) {
					try {
						le le4 = lv.a(adt.a(jsonObject.get("nbt"), "nbt"));
						bki3.c(le4);
					} catch (CommandSyntaxException var4) {
						throw new JsonSyntaxException("Invalid nbt tag: " + var4.getMessage());
					}
				}

				return bki3;
			}
		}
	}

	public void a(mg mg) {
		mg.a(this.a);
		mg.a(this.b);
		mg.a(this.c);
		mg.a(this.e);
		int integer3 = 0;
		if (this.d != null) {
			integer3 |= 1;
		}

		if (this.f) {
			integer3 |= 2;
		}

		if (this.h) {
			integer3 |= 4;
		}

		mg.writeInt(integer3);
		if (this.d != null) {
			mg.a(this.d);
		}

		mg.writeFloat(this.i);
		mg.writeFloat(this.j);
	}

	public static af b(mg mg) {
		mr mr2 = mg.h();
		mr mr3 = mg.h();
		bki bki4 = mg.m();
		ag ag5 = mg.a(ag.class);
		int integer6 = mg.readInt();
		uh uh7 = (integer6 & 1) != 0 ? mg.o() : null;
		boolean boolean8 = (integer6 & 2) != 0;
		boolean boolean9 = (integer6 & 4) != 0;
		af af10 = new af(bki4, mr2, mr3, uh7, ag5, boolean8, false, boolean9);
		af10.a(mg.readFloat(), mg.readFloat());
		return af10;
	}

	public JsonElement k() {
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.add("icon", this.l());
		jsonObject2.add("title", mr.a.b(this.a));
		jsonObject2.add("description", mr.a.b(this.b));
		jsonObject2.addProperty("frame", this.e.a());
		jsonObject2.addProperty("show_toast", this.f);
		jsonObject2.addProperty("announce_to_chat", this.g);
		jsonObject2.addProperty("hidden", this.h);
		if (this.d != null) {
			jsonObject2.addProperty("background", this.d.toString());
		}

		return jsonObject2;
	}

	private JsonObject l() {
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.addProperty("item", gl.am.b(this.c.b()).toString());
		if (this.c.n()) {
			jsonObject2.addProperty("nbt", this.c.o().toString());
		}

		return jsonObject2;
	}
}
