import com.mojang.authlib.GameProfile;
import org.apache.commons.lang3.StringUtils;

public class bkt extends blk {
	public bkt(bvr bvr1, bvr bvr2, bke.a a) {
		super(bvr1, bvr2, a);
	}

	@Override
	public mr h(bki bki) {
		if (bki.b() == bkk.pf && bki.n()) {
			String string3 = null;
			le le4 = bki.o();
			if (le4.c("SkullOwner", 8)) {
				string3 = le4.l("SkullOwner");
			} else if (le4.c("SkullOwner", 10)) {
				le le5 = le4.p("SkullOwner");
				if (le5.c("Name", 8)) {
					string3 = le5.l("Name");
				}
			}

			if (string3 != null) {
				return new ne(this.a() + ".named", string3);
			}
		}

		return super.h(bki);
	}

	@Override
	public boolean b(le le) {
		super.b(le);
		if (le.c("SkullOwner", 8) && !StringUtils.isBlank(le.l("SkullOwner"))) {
			GameProfile gameProfile3 = new GameProfile(null, le.l("SkullOwner"));
			gameProfile3 = cei.b(gameProfile3);
			le.a("SkullOwner", lq.a(new le(), gameProfile3));
			return true;
		} else {
			return false;
		}
	}
}
