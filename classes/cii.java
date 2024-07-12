import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cii {
	private static final Logger a = LogManager.getLogger();
	private static final Predicate<aom> b = aop.a.and(aop.a(0.0, 128.0, 0.0, 192.0));
	private final za c = (za)new za(new ne("entity.minecraft.ender_dragon"), amw.a.PINK, amw.b.PROGRESS).b(true).c(true);
	private final zd d;
	private final List<Integer> e = Lists.<Integer>newArrayList();
	private final cfo f;
	private int g;
	private int h;
	private int i;
	private int j;
	private boolean k;
	private boolean l;
	private UUID m;
	private boolean n = true;
	private fu o;
	private cih p;
	private int q;
	private List<bab> r;

	public cii(zd zd, long long2, le le) {
		this.d = zd;
		if (le.c("DragonKilled", 99)) {
			if (le.b("Dragon")) {
				this.m = le.a("Dragon");
			}

			this.k = le.q("DragonKilled");
			this.l = le.q("PreviouslyKilled");
			if (le.q("IsRespawning")) {
				this.p = cih.START;
			}

			if (le.c("ExitPortalLocation", 10)) {
				this.o = lq.b(le.p("ExitPortalLocation"));
			}
		} else {
			this.k = true;
			this.l = true;
		}

		if (le.c("Gateways", 9)) {
			lk lk6 = le.d("Gateways", 3);

			for (int integer7 = 0; integer7 < lk6.size(); integer7++) {
				this.e.add(lk6.e(integer7));
			}
		} else {
			this.e.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
			Collections.shuffle(this.e, new Random(long2));
		}

		this.f = cfp.a()
			.a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
			.a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
			.a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
			.a("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ")
			.a("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ")
			.a('#', cfn.a(cfs.a(bvs.z)))
			.b();
	}

	public le a() {
		le le2 = new le();
		if (this.m != null) {
			le2.a("Dragon", this.m);
		}

		le2.a("DragonKilled", this.k);
		le2.a("PreviouslyKilled", this.l);
		if (this.o != null) {
			le2.a("ExitPortalLocation", lq.a(this.o));
		}

		lk lk3 = new lk();

		for (int integer5 : this.e) {
			lk3.add(lj.a(integer5));
		}

		le2.a("Gateways", lk3);
		return le2;
	}

	public void b() {
		this.c.d(!this.k);
		if (++this.j >= 20) {
			this.l();
			this.j = 0;
		}

		if (!this.c.h().isEmpty()) {
			this.d.i().a(zi.b, new bph(0, 0), 9, ael.INSTANCE);
			boolean boolean2 = this.k();
			if (this.n && boolean2) {
				this.g();
				this.n = false;
			}

			if (this.p != null) {
				if (this.r == null && boolean2) {
					this.p = null;
					this.e();
				}

				this.p.a(this.d, this, this.r, this.q++, this.o);
			}

			if (!this.k) {
				if ((this.m == null || ++this.g >= 1200) && boolean2) {
					this.h();
					this.g = 0;
				}

				if (++this.i >= 100 && boolean2) {
					this.m();
					this.i = 0;
				}
			}
		} else {
			this.d.i().b(zi.b, new bph(0, 0), 9, ael.INSTANCE);
		}
	}

	private void g() {
		a.info("Scanning for legacy world dragon fight...");
		boolean boolean2 = this.i();
		if (boolean2) {
			a.info("Found that the dragon has been killed in this world already.");
			this.l = true;
		} else {
			a.info("Found that the dragon has not yet been killed in this world.");
			this.l = false;
			if (this.j() == null) {
				this.a(false);
			}
		}

		List<bac> list3 = this.d.g();
		if (list3.isEmpty()) {
			this.k = true;
		} else {
			bac bac4 = (bac)list3.get(0);
			this.m = bac4.bR();
			a.info("Found that there's a dragon still alive ({})", bac4);
			this.k = false;
			if (!boolean2) {
				a.info("But we didn't have a portal, let's remove it.");
				bac4.aa();
				this.m = null;
			}
		}

		if (!this.l && this.k) {
			this.k = false;
		}
	}

	private void h() {
		List<bac> list2 = this.d.g();
		if (list2.isEmpty()) {
			a.debug("Haven't seen the dragon, respawning it");
			this.o();
		} else {
			a.debug("Haven't seen our dragon, but found another one to use.");
			this.m = ((bac)list2.get(0)).bR();
		}
	}

	protected void a(cih cih) {
		if (this.p == null) {
			throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
		} else {
			this.q = 0;
			if (cih == cih.END) {
				this.p = null;
				this.k = false;
				bac bac3 = this.o();

				for (ze ze5 : this.c.h()) {
					aa.n.a(ze5, bac3);
				}
			} else {
				this.p = cih;
			}
		}
	}

	private boolean i() {
		for (int integer2 = -8; integer2 <= 8; integer2++) {
			for (int integer3 = -8; integer3 <= 8; integer3++) {
				chj chj4 = this.d.d(integer2, integer3);

				for (cdl cdl6 : chj4.y().values()) {
					if (cdl6 instanceof cen) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@Nullable
	private cfo.b j() {
		for (int integer2 = -8; integer2 <= 8; integer2++) {
			for (int integer3 = -8; integer3 <= 8; integer3++) {
				chj chj4 = this.d.d(integer2, integer3);

				for (cdl cdl6 : chj4.y().values()) {
					if (cdl6 instanceof cen) {
						cfo.b b7 = this.f.a(this.d, cdl6.o());
						if (b7 != null) {
							fu fu8 = b7.a(3, 3, 3).d();
							if (this.o == null && fu8.u() == 0 && fu8.w() == 0) {
								this.o = fu8;
							}

							return b7;
						}
					}
				}
			}
		}

		int integer2 = this.d.a(cio.a.MOTION_BLOCKING, cks.a).v();

		for (int integer3 = integer2; integer3 >= 0; integer3--) {
			cfo.b b4 = this.f.a(this.d, new fu(cks.a.u(), integer3, cks.a.w()));
			if (b4 != null) {
				if (this.o == null) {
					this.o = b4.a(3, 3, 3).d();
				}

				return b4;
			}
		}

		return null;
	}

	private boolean k() {
		for (int integer2 = -8; integer2 <= 8; integer2++) {
			for (int integer3 = 8; integer3 <= 8; integer3++) {
				cgy cgy4 = this.d.a(integer2, integer3, chc.m, false);
				if (!(cgy4 instanceof chj)) {
					return false;
				}

				yo.b b5 = ((chj)cgy4).u();
				if (!b5.a(yo.b.TICKING)) {
					return false;
				}
			}
		}

		return true;
	}

	private void l() {
		Set<ze> set2 = Sets.<ze>newHashSet();

		for (ze ze4 : this.d.a(b)) {
			this.c.a(ze4);
			set2.add(ze4);
		}

		Set<ze> set3 = Sets.<ze>newHashSet(this.c.h());
		set3.removeAll(set2);

		for (ze ze5 : set3) {
			this.c.b(ze5);
		}
	}

	private void m() {
		this.i = 0;
		this.h = 0;

		for (cmi.a a3 : cmi.a(this.d)) {
			this.h = this.h + this.d.a(bab.class, a3.f()).size();
		}

		a.debug("Found {} end crystals still alive", this.h);
	}

	public void a(bac bac) {
		if (bac.bR().equals(this.m)) {
			this.c.a(0.0F);
			this.c.d(false);
			this.a(true);
			this.n();
			if (!this.l) {
				this.d.a(this.d.a(cio.a.MOTION_BLOCKING, cks.a), bvs.ef.n());
			}

			this.l = true;
			this.k = true;
		}
	}

	private void n() {
		if (!this.e.isEmpty()) {
			int integer2 = (Integer)this.e.remove(this.e.size() - 1);
			int integer3 = aec.c(96.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 20) * (double)integer2)));
			int integer4 = aec.c(96.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 20) * (double)integer2)));
			this.a(new fu(integer3, 75, integer4));
		}
	}

	private void a(fu fu) {
		this.d.c(3000, fu, 0);
		ckt.C.b(cnq.a()).a(this.d, this.d.a(), this.d.i().g(), new Random(), fu);
	}

	private void a(boolean boolean1) {
		cks cks3 = new cks(boolean1);
		if (this.o == null) {
			this.o = this.d.a(cio.a.MOTION_BLOCKING_NO_LEAVES, cks.a).c();

			while (this.d.d_(this.o).a(bvs.z) && this.o.v() > this.d.t_()) {
				this.o = this.o.c();
			}
		}

		cks3.b(cnr.k).a(this.d, this.d.a(), this.d.i().g(), new Random(), this.o);
	}

	private bac o() {
		this.d.n(new fu(0, 128, 0));
		bac bac2 = aoq.t.a(this.d);
		bac2.eL().a(bas.a);
		bac2.b(0.0, 128.0, 0.0, this.d.t.nextFloat() * 360.0F, 0.0F);
		this.d.c(bac2);
		this.m = bac2.bR();
		return bac2;
	}

	public void b(bac bac) {
		if (bac.bR().equals(this.m)) {
			this.c.a(bac.dj() / bac.dw());
			this.g = 0;
			if (bac.Q()) {
				this.c.a(bac.d());
			}
		}
	}

	public int c() {
		return this.h;
	}

	public void a(bab bab, anw anw) {
		if (this.p != null && this.r.contains(bab)) {
			a.debug("Aborting respawn sequence");
			this.p = null;
			this.q = 0;
			this.f();
			this.a(true);
		} else {
			this.m();
			aom aom4 = this.d.a(this.m);
			if (aom4 instanceof bac) {
				((bac)aom4).a(bab, bab.cA(), anw);
			}
		}
	}

	public boolean d() {
		return this.l;
	}

	public void e() {
		if (this.k && this.p == null) {
			fu fu2 = this.o;
			if (fu2 == null) {
				a.debug("Tried to respawn, but need to find the portal first.");
				cfo.b b3 = this.j();
				if (b3 == null) {
					a.debug("Couldn't find a portal, so we made one.");
					this.a(true);
				} else {
					a.debug("Found the exit portal & temporarily using it.");
				}

				fu2 = this.o;
			}

			List<bab> list3 = Lists.<bab>newArrayList();
			fu fu4 = fu2.b(1);

			for (fz fz6 : fz.c.HORIZONTAL) {
				List<bab> list7 = this.d.a(bab.class, new deg(fu4.a(fz6, 2)));
				if (list7.isEmpty()) {
					return;
				}

				list3.addAll(list7);
			}

			a.debug("Found all crystals, respawning dragon.");
			this.a(list3);
		}
	}

	private void a(List<bab> list) {
		if (this.k && this.p == null) {
			for (cfo.b b3 = this.j(); b3 != null; b3 = this.j()) {
				for (int integer4 = 0; integer4 < this.f.c(); integer4++) {
					for (int integer5 = 0; integer5 < this.f.b(); integer5++) {
						for (int integer6 = 0; integer6 < this.f.a(); integer6++) {
							cfn cfn7 = b3.a(integer4, integer5, integer6);
							if (cfn7.a().a(bvs.z) || cfn7.a().a(bvs.ec)) {
								this.d.a(cfn7.d(), bvs.ee.n());
							}
						}
					}
				}
			}

			this.p = cih.START;
			this.q = 0;
			this.a(false);
			this.r = list;
		}
	}

	public void f() {
		for (cmi.a a3 : cmi.a(this.d)) {
			for (bab bab6 : this.d.a(bab.class, a3.f())) {
				bab6.m(false);
				bab6.a(null);
			}
		}
	}
}
