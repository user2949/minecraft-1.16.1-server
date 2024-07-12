import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class abr extends abs<GameProfile> {
	private final int a;
	private final boolean b;

	public abr(GameProfile gameProfile, int integer, boolean boolean3) {
		super(gameProfile);
		this.a = integer;
		this.b = boolean3;
	}

	public abr(JsonObject jsonObject) {
		super(b(jsonObject));
		this.a = jsonObject.has("level") ? jsonObject.get("level").getAsInt() : 0;
		this.b = jsonObject.has("bypassesPlayerLimit") && jsonObject.get("bypassesPlayerLimit").getAsBoolean();
	}

	public int a() {
		return this.a;
	}

	public boolean b() {
		return this.b;
	}

	@Override
	protected void a(JsonObject jsonObject) {
		if (this.g() != null) {
			jsonObject.addProperty("uuid", this.g().getId() == null ? "" : this.g().getId().toString());
			jsonObject.addProperty("name", this.g().getName());
			jsonObject.addProperty("level", this.a);
			jsonObject.addProperty("bypassesPlayerLimit", this.b);
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
