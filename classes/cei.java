import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import javax.annotation.Nullable;

public class cei extends cdl implements ceo {
	@Nullable
	private static abl a;
	@Nullable
	private static MinecraftSessionService b;
	@Nullable
	private GameProfile c;
	private int g;
	private boolean h;

	public cei() {
		super(cdm.o);
	}

	public static void a(abl abl) {
		a = abl;
	}

	public static void a(MinecraftSessionService minecraftSessionService) {
		b = minecraftSessionService;
	}

	@Override
	public le a(le le) {
		super.a(le);
		if (this.c != null) {
			le le3 = new le();
			lq.a(le3, this.c);
			le.a("SkullOwner", le3);
		}

		return le;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		if (le.c("SkullOwner", 10)) {
			this.a(lq.a(le.p("SkullOwner")));
		} else if (le.c("ExtraType", 8)) {
			String string4 = le.l("ExtraType");
			if (!aei.b(string4)) {
				this.a(new GameProfile(null, string4));
			}
		}
	}

	@Override
	public void al_() {
		cfj cfj2 = this.p();
		if (cfj2.a(bvs.fm) || cfj2.a(bvs.fn)) {
			if (this.d.r(this.e)) {
				this.h = true;
				this.g++;
			} else {
				this.h = false;
			}
		}
	}

	@Nullable
	@Override
	public nv a() {
		return new nv(this.e, 4, this.b());
	}

	@Override
	public le b() {
		return this.a(new le());
	}

	public void a(@Nullable GameProfile gameProfile) {
		this.c = gameProfile;
		this.f();
	}

	private void f() {
		this.c = b(this.c);
		this.Z_();
	}

	@Nullable
	public static GameProfile b(@Nullable GameProfile gameProfile) {
		if (gameProfile != null && !aei.b(gameProfile.getName())) {
			if (gameProfile.isComplete() && gameProfile.getProperties().containsKey("textures")) {
				return gameProfile;
			} else if (a != null && b != null) {
				GameProfile gameProfile2 = a.a(gameProfile.getName());
				if (gameProfile2 == null) {
					return gameProfile;
				} else {
					Property property3 = Iterables.getFirst(gameProfile2.getProperties().get("textures"), null);
					if (property3 == null) {
						gameProfile2 = b.fillProfileProperties(gameProfile2, true);
					}

					return gameProfile2;
				}
			} else {
				return gameProfile;
			}
		} else {
			return gameProfile;
		}
	}
}
