import com.google.gson.JsonObject;
import java.util.Date;
import javax.annotation.Nullable;

public class abn extends abk<String> {
	public abn(String string) {
		this(string, null, null, null, null);
	}

	public abn(String string1, @Nullable Date date2, @Nullable String string3, @Nullable Date date4, @Nullable String string5) {
		super(string1, date2, string3, date4, string5);
	}

	@Override
	public mr e() {
		return new nd(this.g());
	}

	public abn(JsonObject jsonObject) {
		super(b(jsonObject), jsonObject);
	}

	private static String b(JsonObject jsonObject) {
		return jsonObject.has("ip") ? jsonObject.get("ip").getAsString() : null;
	}

	@Override
	protected void a(JsonObject jsonObject) {
		if (this.g() != null) {
			jsonObject.addProperty("ip", this.g());
			super.a(jsonObject);
		}
	}
}
