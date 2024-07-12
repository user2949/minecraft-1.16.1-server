import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;

public class bz {
	public static final bz a = new bz(null);
	@Nullable
	private final le b;

	public bz(@Nullable le le) {
		this.b = le;
	}

	public boolean a(bki bki) {
		return this == a ? true : this.a(bki.o());
	}

	public boolean a(aom aom) {
		return this == a ? true : this.a(b(aom));
	}

	public boolean a(@Nullable lu lu) {
		return lu == null ? this == a : this.b == null || lq.a(this.b, lu, true);
	}

	public JsonElement a() {
		return (JsonElement)(this != a && this.b != null ? new JsonPrimitive(this.b.toString()) : JsonNull.INSTANCE);
	}

	public static bz a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			le le2;
			try {
				le2 = lv.a(adt.a(jsonElement, "nbt"));
			} catch (CommandSyntaxException var3) {
				throw new JsonSyntaxException("Invalid nbt tag: " + var3.getMessage());
			}

			return new bz(le2);
		} else {
			return a;
		}
	}

	public static le b(aom aom) {
		le le2 = aom.e(new le());
		if (aom instanceof bec) {
			bki bki3 = ((bec)aom).bt.f();
			if (!bki3.a()) {
				le2.a("SelectedItem", bki3.b(new le()));
			}
		}

		return le2;
	}
}
