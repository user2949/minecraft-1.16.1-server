import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;

public class sy implements ni<sw> {
	private GameProfile a;

	public sy() {
	}

	public sy(GameProfile gameProfile) {
		this.a = gameProfile;
	}

	@Override
	public void a(mg mg) throws IOException {
		int[] arr3 = new int[4];

		for (int integer4 = 0; integer4 < arr3.length; integer4++) {
			arr3[integer4] = mg.readInt();
		}

		UUID uUID4 = gp.a(arr3);
		String string5 = mg.e(16);
		this.a = new GameProfile(uUID4, string5);
	}

	@Override
	public void b(mg mg) throws IOException {
		for (int integer6 : gp.a(this.a.getId())) {
			mg.writeInt(integer6);
		}

		mg.a(this.a.getName());
	}

	public void a(sw sw) {
		sw.a(this);
	}
}
