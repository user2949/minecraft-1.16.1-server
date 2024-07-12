import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class abx extends abs<GameProfile> {
	public abx(GameProfile gameProfile) {
		super(gameProfile);
	}

	public abx(JsonObject jsonObject) {
		super(b(jsonObject));
	}

	@Override
	protected void a(JsonObject jsonObject) {
		if (this.g() != null) {
			jsonObject.addProperty("uuid", this.g().getId() == null ? "" : this.g().getId().toString());
			jsonObject.addProperty("name", this.g().getName());
		}
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
