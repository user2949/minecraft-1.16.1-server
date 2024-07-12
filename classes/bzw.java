import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class bzw extends cay {
	protected bzw(cfi.c c) {
		super(cay.b.PLAYER, c);
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, @Nullable aoy aoy, bki bki) {
		super.a(bqb, fu, cfj, aoy, bki);
		cdl cdl7 = bqb.c(fu);
		if (cdl7 instanceof cei) {
			cei cei8 = (cei)cdl7;
			GameProfile gameProfile9 = null;
			if (bki.n()) {
				le le10 = bki.o();
				if (le10.c("SkullOwner", 10)) {
					gameProfile9 = lq.a(le10.p("SkullOwner"));
				} else if (le10.c("SkullOwner", 8) && !StringUtils.isBlank(le10.l("SkullOwner"))) {
					gameProfile9 = new GameProfile(null, le10.l("SkullOwner"));
				}
			}

			cei8.a(gameProfile9);
		}
	}
}
