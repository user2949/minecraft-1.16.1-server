import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;

public class pi implements ni<nl> {
	private pi.a a;
	private final List<pi.b> b = Lists.<pi.b>newArrayList();

	public pi() {
	}

	public pi(pi.a a, ze... arr) {
		this.a = a;

		for (ze ze7 : arr) {
			this.b.add(new pi.b(ze7.ez(), ze7.f, ze7.d.b(), ze7.G()));
		}
	}

	public pi(pi.a a, Iterable<ze> iterable) {
		this.a = a;

		for (ze ze5 : iterable) {
			this.b.add(new pi.b(ze5.ez(), ze5.f, ze5.d.b(), ze5.G()));
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.a(pi.a.class);
		int integer3 = mg.i();

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			GameProfile gameProfile5 = null;
			int integer6 = 0;
			bpy bpy7 = null;
			mr mr8 = null;
			switch (this.a) {
				case ADD_PLAYER:
					gameProfile5 = new GameProfile(mg.k(), mg.e(16));
					int integer9 = mg.i();
					int integer10 = 0;

					for (; integer10 < integer9; integer10++) {
						String string11 = mg.e(32767);
						String string12 = mg.e(32767);
						if (mg.readBoolean()) {
							gameProfile5.getProperties().put(string11, new Property(string11, string12, mg.e(32767)));
						} else {
							gameProfile5.getProperties().put(string11, new Property(string11, string12));
						}
					}

					bpy7 = bpy.a(mg.i());
					integer6 = mg.i();
					if (mg.readBoolean()) {
						mr8 = mg.h();
					}
					break;
				case UPDATE_GAME_MODE:
					gameProfile5 = new GameProfile(mg.k(), null);
					bpy7 = bpy.a(mg.i());
					break;
				case UPDATE_LATENCY:
					gameProfile5 = new GameProfile(mg.k(), null);
					integer6 = mg.i();
					break;
				case UPDATE_DISPLAY_NAME:
					gameProfile5 = new GameProfile(mg.k(), null);
					if (mg.readBoolean()) {
						mr8 = mg.h();
					}
					break;
				case REMOVE_PLAYER:
					gameProfile5 = new GameProfile(mg.k(), null);
			}

			this.b.add(new pi.b(gameProfile5, integer6, bpy7, mr8));
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.d(this.b.size());

		for (pi.b b4 : this.b) {
			switch (this.a) {
				case ADD_PLAYER:
					mg.a(b4.a().getId());
					mg.a(b4.a().getName());
					mg.d(b4.a().getProperties().size());

					for (Property property6 : b4.a().getProperties().values()) {
						mg.a(property6.getName());
						mg.a(property6.getValue());
						if (property6.hasSignature()) {
							mg.writeBoolean(true);
							mg.a(property6.getSignature());
						} else {
							mg.writeBoolean(false);
						}
					}

					mg.d(b4.c().a());
					mg.d(b4.b());
					if (b4.d() == null) {
						mg.writeBoolean(false);
					} else {
						mg.writeBoolean(true);
						mg.a(b4.d());
					}
					break;
				case UPDATE_GAME_MODE:
					mg.a(b4.a().getId());
					mg.d(b4.c().a());
					break;
				case UPDATE_LATENCY:
					mg.a(b4.a().getId());
					mg.d(b4.b());
					break;
				case UPDATE_DISPLAY_NAME:
					mg.a(b4.a().getId());
					if (b4.d() == null) {
						mg.writeBoolean(false);
					} else {
						mg.writeBoolean(true);
						mg.a(b4.d());
					}
					break;
				case REMOVE_PLAYER:
					mg.a(b4.a().getId());
			}
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}

	public String toString() {
		return MoreObjects.toStringHelper(this).add("action", this.a).add("entries", this.b).toString();
	}

	public static enum a {
		ADD_PLAYER,
		UPDATE_GAME_MODE,
		UPDATE_LATENCY,
		UPDATE_DISPLAY_NAME,
		REMOVE_PLAYER;
	}

	public class b {
		private final int b;
		private final bpy c;
		private final GameProfile d;
		private final mr e;

		public b(GameProfile gameProfile, int integer, bpy bpy, @Nullable mr mr) {
			this.d = gameProfile;
			this.b = integer;
			this.c = bpy;
			this.e = mr;
		}

		public GameProfile a() {
			return this.d;
		}

		public int b() {
			return this.b;
		}

		public bpy c() {
			return this.c;
		}

		@Nullable
		public mr d() {
			return this.e;
		}

		public String toString() {
			return MoreObjects.toStringHelper(this)
				.add("latency", this.b)
				.add("gameMode", this.c)
				.add("profile", this.d)
				.add("displayName", this.e == null ? null : mr.a.a(this.e))
				.toString();
		}
	}
}
