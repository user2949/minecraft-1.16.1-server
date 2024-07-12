import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class abq extends abt<GameProfile, abr> {
	public abq(File file) {
		super(file);
	}

	@Override
	protected abs<GameProfile> a(JsonObject jsonObject) {
		return new abr(jsonObject);
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

	public boolean b(GameProfile gameProfile) {
		abr abr3 = this.b(gameProfile);
		return abr3 != null ? abr3.b() : false;
	}

	protected String a(GameProfile gameProfile) {
		return gameProfile.getId().toString();
	}
}
