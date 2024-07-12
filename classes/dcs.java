import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class dcs extends dcg {
	private final le a;

	private dcs(ddm[] arr, le le) {
		super(arr);
		this.a = le;
	}

	@Override
	public dci b() {
		return dcj.e;
	}

	@Override
	public bki a(bki bki, dat dat) {
		bki.p().a(this.a);
		return bki;
	}

	public static dcg.a<?> a(le le) {
		return a(arr -> new dcs(arr, le));
	}

	public static class a extends dcg.c<dcs> {
		public void a(JsonObject jsonObject, dcs dcs, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dcs, jsonSerializationContext);
			jsonObject.addProperty("tag", dcs.a.toString());
		}

		public dcs b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			try {
				le le5 = lv.a(adt.h(jsonObject, "tag"));
				return new dcs(arr, le5);
			} catch (CommandSyntaxException var5) {
				throw new JsonSyntaxException(var5.getMessage());
			}
		}
	}
}
