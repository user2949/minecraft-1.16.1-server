import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dfn extends czq {
	private static final Logger a = LogManager.getLogger();
	private dfm b;
	private le c;

	public dfn() {
		super("scoreboard");
	}

	public void a(dfm dfm) {
		this.b = dfm;
		if (this.c != null) {
			this.a(this.c);
		}
	}

	@Override
	public void a(le le) {
		if (this.b == null) {
			this.c = le;
		} else {
			this.b(le.d("Objectives", 10));
			this.b.a(le.d("PlayerScores", 10));
			if (le.c("DisplaySlots", 10)) {
				this.c(le.p("DisplaySlots"));
			}

			if (le.c("Teams", 9)) {
				this.a(le.d("Teams", 10));
			}
		}
	}

	protected void a(lk lk) {
		for (int integer3 = 0; integer3 < lk.size(); integer3++) {
			le le4 = lk.a(integer3);
			String string5 = le4.l("Name");
			if (string5.length() > 16) {
				string5 = string5.substring(0, 16);
			}

			dfk dfk6 = this.b.g(string5);
			mr mr7 = mr.a.a(le4.l("DisplayName"));
			if (mr7 != null) {
				dfk6.a(mr7);
			}

			if (le4.c("TeamColor", 8)) {
				dfk6.a(i.b(le4.l("TeamColor")));
			}

			if (le4.c("AllowFriendlyFire", 99)) {
				dfk6.a(le4.q("AllowFriendlyFire"));
			}

			if (le4.c("SeeFriendlyInvisibles", 99)) {
				dfk6.b(le4.q("SeeFriendlyInvisibles"));
			}

			if (le4.c("MemberNamePrefix", 8)) {
				mr mr8 = mr.a.a(le4.l("MemberNamePrefix"));
				if (mr8 != null) {
					dfk6.b(mr8);
				}
			}

			if (le4.c("MemberNameSuffix", 8)) {
				mr mr8 = mr.a.a(le4.l("MemberNameSuffix"));
				if (mr8 != null) {
					dfk6.c(mr8);
				}
			}

			if (le4.c("NameTagVisibility", 8)) {
				dfo.b b8 = dfo.b.a(le4.l("NameTagVisibility"));
				if (b8 != null) {
					dfk6.a(b8);
				}
			}

			if (le4.c("DeathMessageVisibility", 8)) {
				dfo.b b8 = dfo.b.a(le4.l("DeathMessageVisibility"));
				if (b8 != null) {
					dfk6.b(b8);
				}
			}

			if (le4.c("CollisionRule", 8)) {
				dfo.a a8 = dfo.a.a(le4.l("CollisionRule"));
				if (a8 != null) {
					dfk6.a(a8);
				}
			}

			this.a(dfk6, le4.d("Players", 8));
		}
	}

	protected void a(dfk dfk, lk lk) {
		for (int integer4 = 0; integer4 < lk.size(); integer4++) {
			this.b.a(lk.j(integer4), dfk);
		}
	}

	protected void c(le le) {
		for (int integer3 = 0; integer3 < 19; integer3++) {
			if (le.c("slot_" + integer3, 8)) {
				String string4 = le.l("slot_" + integer3);
				dfj dfj5 = this.b.d(string4);
				this.b.a(integer3, dfj5);
			}
		}
	}

	protected void b(lk lk) {
		for (int integer3 = 0; integer3 < lk.size(); integer3++) {
			le le4 = lk.a(integer3);
			dfp.a(le4.l("CriteriaName")).ifPresent(dfp -> {
				String string4 = le4.l("Name");
				if (string4.length() > 16) {
					string4 = string4.substring(0, 16);
				}

				mr mr5 = mr.a.a(le4.l("DisplayName"));
				dfp.a a6 = dfp.a.a(le4.l("RenderType"));
				this.b.a(string4, dfp, mr5, a6);
			});
		}
	}

	@Override
	public le b(le le) {
		if (this.b == null) {
			a.warn("Tried to save scoreboard without having a scoreboard...");
			return le;
		} else {
			le.a("Objectives", this.e());
			le.a("PlayerScores", this.b.i());
			le.a("Teams", this.a());
			this.d(le);
			return le;
		}
	}

	protected lk a() {
		lk lk2 = new lk();

		for (dfk dfk5 : this.b.g()) {
			le le6 = new le();
			le6.a("Name", dfk5.b());
			le6.a("DisplayName", mr.a.a(dfk5.c()));
			if (dfk5.n().b() >= 0) {
				le6.a("TeamColor", dfk5.n().f());
			}

			le6.a("AllowFriendlyFire", dfk5.h());
			le6.a("SeeFriendlyInvisibles", dfk5.i());
			le6.a("MemberNamePrefix", mr.a.a(dfk5.e()));
			le6.a("MemberNameSuffix", mr.a.a(dfk5.f()));
			le6.a("NameTagVisibility", dfk5.j().e);
			le6.a("DeathMessageVisibility", dfk5.k().e);
			le6.a("CollisionRule", dfk5.l().e);
			lk lk7 = new lk();

			for (String string9 : dfk5.g()) {
				lk7.add(lt.a(string9));
			}

			le6.a("Players", lk7);
			lk2.add(le6);
		}

		return lk2;
	}

	protected void d(le le) {
		le le3 = new le();
		boolean boolean4 = false;

		for (int integer5 = 0; integer5 < 19; integer5++) {
			dfj dfj6 = this.b.a(integer5);
			if (dfj6 != null) {
				le3.a("slot_" + integer5, dfj6.b());
				boolean4 = true;
			}
		}

		if (boolean4) {
			le.a("DisplaySlots", le3);
		}
	}

	protected lk e() {
		lk lk2 = new lk();

		for (dfj dfj5 : this.b.c()) {
			if (dfj5.c() != null) {
				le le6 = new le();
				le6.a("Name", dfj5.b());
				le6.a("CriteriaName", dfj5.c().c());
				le6.a("DisplayName", mr.a.a(dfj5.d()));
				le6.a("RenderType", dfj5.f().a());
				lk2.add(le6);
			}
		}

		return lk2;
	}
}
