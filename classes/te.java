import com.mojang.authlib.GameProfile;
import java.io.IOException;

public class te implements ni<tc> {
	private GameProfile a;

	public te() {
	}

	public te(GameProfile gameProfile) {
		this.a = gameProfile;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = new GameProfile(null, mg.e(16));
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a.getName());
	}

	public void a(tc tc) {
		tc.a(this);
	}

	public GameProfile b() {
		return this.a;
	}
}
