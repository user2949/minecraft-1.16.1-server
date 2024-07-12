import com.google.common.base.Joiner;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public abstract class bf {
	public static final bf a = new bf() {
		@Override
		public boolean a(aoq<?> aoq) {
			return true;
		}

		@Override
		public JsonElement a() {
			return JsonNull.INSTANCE;
		}
	};
	private static final Joiner b = Joiner.on(", ");

	public abstract boolean a(aoq<?> aoq);

	public abstract JsonElement a();

	public static bf a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			String string2 = adt.a(jsonElement, "type");
			if (string2.startsWith("#")) {
				uh uh3 = new uh(string2.substring(1));
				return new bf.a(adb.e().d().b(uh3));
			} else {
				uh uh3 = new uh(string2);
				aoq<?> aoq4 = (aoq<?>)gl.al.b(uh3).orElseThrow(() -> new JsonSyntaxException("Unknown entity type '" + uh3 + "', valid types are: " + b.join(gl.al.b())));
				return new bf.b(aoq4);
			}
		} else {
			return a;
		}
	}

	public static bf b(aoq<?> aoq) {
		return new bf.b(aoq);
	}

	public static bf a(adf<aoq<?>> adf) {
		return new bf.a(adf);
	}

	static class a extends bf {
		private final adf<aoq<?>> b;

		public a(adf<aoq<?>> adf) {
			this.b = adf;
		}

		@Override
		public boolean a(aoq<?> aoq) {
			return this.b.a(aoq);
		}

		@Override
		public JsonElement a() {
			return new JsonPrimitive("#" + adb.e().d().b(this.b));
		}
	}

	static class b extends bf {
		private final aoq<?> b;

		public b(aoq<?> aoq) {
			this.b = aoq;
		}

		@Override
		public boolean a(aoq<?> aoq) {
			return this.b == aoq;
		}

		@Override
		public JsonElement a() {
			return new JsonPrimitive(gl.al.b(this.b).toString());
		}
	}
}
