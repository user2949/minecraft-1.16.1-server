import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;

public class abv extends abk<GameProfile> {
	public abv(GameProfile gameProfile) {
		this(gameProfile, null, null, null, null);
	}

	public abv(GameProfile gameProfile, @Nullable Date date2, @Nullable String string3, @Nullable Date date4, @Nullable String string5) {
		super(gameProfile, date2, string3, date4, string5);
	}

	public abv(JsonObject jsonObject) {
		super(b(jsonObject), jsonObject);
	}

	@Override
	protected void a(JsonObject jsonObject) {
		if (this.g() != null) {
			jsonObject.addProperty("uuid", this.g().getId() == null ? "" : this.g().getId().toString());
			jsonObject.addProperty("name", this.g().getName());
			super.a(jsonObject);
		}
	}

	@Override
	public mr e() {
		GameProfile gameProfile2 = this.g();
		return new nd(gameProfile2.getName() != null ? gameProfile2.getName() : Objects.toString(gameProfile2.getId(), "(Unknown)"));
	}

	private static GameProfile b(JsonObject jsonObject) {
		if (jsonObject.has("uuid") && jsonObject.has("name")) {
			String string2 = jsonObject.get("uuid").getAsString();

			UUID uUID3;
			try {
				uUID3 = UUID.fromString(string2);
			} catch (Throwable var4) {
				return null;
			}

			return new GameProfile(uUID3, jsonObject.get("name").getAsString());
		} else {
			return null;
		}
	}
}
