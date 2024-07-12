import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class abu extends abt<GameProfile, abv> {
	public abu(File file) {
		super(file);
	}

	@Override
	protected abs<GameProfile> a(JsonObject jsonObject) {
		return new abv(jsonObject);
	}

	public boolean a(GameProfile gameProfile) {
		return this.d(gameProfile);
	}

	@Override
	public String[] a() {
		String[] arr2 = new String[this.d().size()];
		int integer3 = 0;

		for (abs<GameProfile> abs5 : this.d()) {
			arr2[integer3++] = abs5.g().getName();
		}

		return arr2;
	}

	protected String a(GameProfile gameProfile) {
		return gameProfile.getId().toString();
	}
}
