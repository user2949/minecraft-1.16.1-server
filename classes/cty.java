import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class cty {
	protected static final cfj m = bvs.lb.n();
	protected ctd n;
	@Nullable
	private fz a;
	private bzj b;
	private cap c;
	protected int o;
	private final cmm d;
	private static final Set<bvr> e = ImmutableSet.<bvr>builder()
		.add(bvs.dW)
		.add(bvs.bL)
		.add(bvs.bM)
		.add(bvs.cJ)
		.add(bvs.im)
		.add(bvs.iq)
		.add(bvs.ip)
		.add(bvs.in)
		.add(bvs.io)
		.add(bvs.cg)
		.add(bvs.dH)
		.build();

	protected cty(cmm cmm, int integer) {
		this.d = cmm;
		this.o = integer;
	}

	public cty(cmm cmm, le le) {
		this(cmm, le.h("GD"));
		if (le.e("BB")) {
			this.n = new ctd(le.n("BB"));
		}

		int integer4 = le.h("O");
		this.a(integer4 == -1 ? null : fz.b(integer4));
	}

	public final le f() {
		le le2 = new le();
		le2.a("id", gl.aH.b(this.k()).toString());
		le2.a("BB", this.n.h());
		fz fz3 = this.i();
		le2.b("O", fz3 == null ? -1 : fz3.d());
		le2.b("GD", this.o);
		this.a(le2);
		return le2;
	}

	protected abstract void a(le le);

	public void a(cty cty, List<cty> list, Random random) {
	}

	public abstract boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu);

	public ctd g() {
		return this.n;
	}

	public int h() {
		return this.o;
	}

	public boolean a(bph bph, int integer) {
		int integer4 = bph.b << 4;
		int integer5 = bph.c << 4;
		return this.n.a(integer4 - integer, integer5 - integer, integer4 + 15 + integer, integer5 + 15 + integer);
	}

	public static cty a(List<cty> list, ctd ctd) {
		for (cty cty4 : list) {
			if (cty4.g() != null && cty4.g().b(ctd)) {
				return cty4;
			}
		}

		return null;
	}

	protected boolean a(bpg bpg, ctd ctd) {
		int integer4 = Math.max(this.n.a - 1, ctd.a);
		int integer5 = Math.max(this.n.b - 1, ctd.b);
		int integer6 = Math.max(this.n.c - 1, ctd.c);
		int integer7 = Math.min(this.n.d + 1, ctd.d);
		int integer8 = Math.min(this.n.e + 1, ctd.e);
		int integer9 = Math.min(this.n.f + 1, ctd.f);
		fu.a a10 = new fu.a();

		for (int integer11 = integer4; integer11 <= integer7; integer11++) {
			for (int integer12 = integer6; integer12 <= integer9; integer12++) {
				if (bpg.d_(a10.d(integer11, integer5, integer12)).c().a()) {
					return true;
				}

				if (bpg.d_(a10.d(integer11, integer8, integer12)).c().a()) {
					return true;
				}
			}
		}

		for (int integer11 = integer4; integer11 <= integer7; integer11++) {
			for (int integer12 = integer5; integer12 <= integer8; integer12++) {
				if (bpg.d_(a10.d(integer11, integer12, integer6)).c().a()) {
					return true;
				}

				if (bpg.d_(a10.d(integer11, integer12, integer9)).c().a()) {
					return true;
				}
			}
		}

		for (int integer11 = integer6; integer11 <= integer9; integer11++) {
			for (int integer12 = integer5; integer12 <= integer8; integer12++) {
				if (bpg.d_(a10.d(integer4, integer12, integer11)).c().a()) {
					return true;
				}

				if (bpg.d_(a10.d(integer7, integer12, integer11)).c().a()) {
					return true;
				}
			}
		}

		return false;
	}

	protected int a(int integer1, int integer2) {
		fz fz4 = this.i();
		if (fz4 == null) {
			return integer1;
		} else {
			switch (fz4) {
				case NORTH:
				case SOUTH:
					return this.n.a + integer1;
				case WEST:
					return this.n.d - integer2;
				case EAST:
					return this.n.a + integer2;
				default:
					return integer1;
			}
		}
	}

	protected int d(int integer) {
		return this.i() == null ? integer : integer + this.n.b;
	}

	protected int b(int integer1, int integer2) {
		fz fz4 = this.i();
		if (fz4 == null) {
			return integer2;
		} else {
			switch (fz4) {
				case NORTH:
					return this.n.f - integer2;
				case SOUTH:
					return this.n.c + integer2;
				case WEST:
				case EAST:
					return this.n.c + integer1;
				default:
					return integer2;
			}
		}
	}

	protected void a(bqc bqc, cfj cfj, int integer3, int integer4, int integer5, ctd ctd) {
		fu fu8 = new fu(this.a(integer3, integer5), this.d(integer4), this.b(integer3, integer5));
		if (ctd.b(fu8)) {
			if (this.b != bzj.NONE) {
				cfj = cfj.a(this.b);
			}

			if (this.c != cap.NONE) {
				cfj = cfj.a(this.c);
			}

			bqc.a(fu8, cfj, 2);
			cxa cxa9 = bqc.b(fu8);
			if (!cxa9.c()) {
				bqc.F().a(fu8, cxa9.a(), 0);
			}

			if (e.contains(cfj.b())) {
				bqc.z(fu8).e(fu8);
			}
		}
	}

	protected cfj a(bpg bpg, int integer2, int integer3, int integer4, ctd ctd) {
		int integer7 = this.a(integer2, integer4);
		int integer8 = this.d(integer3);
		int integer9 = this.b(integer2, integer4);
		fu fu10 = new fu(integer7, integer8, integer9);
		return !ctd.b(fu10) ? bvs.a.n() : bpg.d_(fu10);
	}

	protected boolean a(bqd bqd, int integer2, int integer3, int integer4, ctd ctd) {
		int integer7 = this.a(integer2, integer4);
		int integer8 = this.d(integer3 + 1);
		int integer9 = this.b(integer2, integer4);
		fu fu10 = new fu(integer7, integer8, integer9);
		return !ctd.b(fu10) ? false : integer8 < bqd.a(cio.a.OCEAN_FLOOR_WG, integer7, integer9);
	}

	protected void b(bqc bqc, ctd ctd, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8) {
		for (int integer10 = integer4; integer10 <= integer7; integer10++) {
			for (int integer11 = integer3; integer11 <= integer6; integer11++) {
				for (int integer12 = integer5; integer12 <= integer8; integer12++) {
					this.a(bqc, bvs.a.n(), integer11, integer10, integer12, ctd);
				}
			}
		}
	}

	protected void a(bqc bqc, ctd ctd, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, cfj cfj9, cfj cfj10, boolean boolean11) {
		for (int integer13 = integer4; integer13 <= integer7; integer13++) {
			for (int integer14 = integer3; integer14 <= integer6; integer14++) {
				for (int integer15 = integer5; integer15 <= integer8; integer15++) {
					if (!boolean11 || !this.a((bpg)bqc, integer14, integer13, integer15, ctd).g()) {
						if (integer13 != integer4 && integer13 != integer7 && integer14 != integer3 && integer14 != integer6 && integer15 != integer5 && integer15 != integer8) {
							this.a(bqc, cfj10, integer14, integer13, integer15, ctd);
						} else {
							this.a(bqc, cfj9, integer14, integer13, integer15, ctd);
						}
					}
				}
			}
		}
	}

	protected void a(
		bqc bqc, ctd ctd, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, boolean boolean9, Random random, cty.a a
	) {
		for (int integer13 = integer4; integer13 <= integer7; integer13++) {
			for (int integer14 = integer3; integer14 <= integer6; integer14++) {
				for (int integer15 = integer5; integer15 <= integer8; integer15++) {
					if (!boolean9 || !this.a((bpg)bqc, integer14, integer13, integer15, ctd).g()) {
						a.a(
							random,
							integer14,
							integer13,
							integer15,
							integer13 == integer4 || integer13 == integer7 || integer14 == integer3 || integer14 == integer6 || integer15 == integer5 || integer15 == integer8
						);
						this.a(bqc, a.a(), integer14, integer13, integer15, ctd);
					}
				}
			}
		}
	}

	protected void a(
		bqc bqc,
		ctd ctd,
		Random random,
		float float4,
		int integer5,
		int integer6,
		int integer7,
		int integer8,
		int integer9,
		int integer10,
		cfj cfj11,
		cfj cfj12,
		boolean boolean13,
		boolean boolean14
	) {
		for (int integer16 = integer6; integer16 <= integer9; integer16++) {
			for (int integer17 = integer5; integer17 <= integer8; integer17++) {
				for (int integer18 = integer7; integer18 <= integer10; integer18++) {
					if (!(random.nextFloat() > float4)
						&& (!boolean13 || !this.a((bpg)bqc, integer17, integer16, integer18, ctd).g())
						&& (!boolean14 || this.a((bqd)bqc, integer17, integer16, integer18, ctd))) {
						if (integer16 != integer6 && integer16 != integer9 && integer17 != integer5 && integer17 != integer8 && integer18 != integer7 && integer18 != integer10) {
							this.a(bqc, cfj12, integer17, integer16, integer18, ctd);
						} else {
							this.a(bqc, cfj11, integer17, integer16, integer18, ctd);
						}
					}
				}
			}
		}
	}

	protected void a(bqc bqc, ctd ctd, Random random, float float4, int integer5, int integer6, int integer7, cfj cfj) {
		if (random.nextFloat() < float4) {
			this.a(bqc, cfj, integer5, integer6, integer7, ctd);
		}
	}

	protected void a(bqc bqc, ctd ctd, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, cfj cfj, boolean boolean10) {
		float float12 = (float)(integer6 - integer3 + 1);
		float float13 = (float)(integer7 - integer4 + 1);
		float float14 = (float)(integer8 - integer5 + 1);
		float float15 = (float)integer3 + float12 / 2.0F;
		float float16 = (float)integer5 + float14 / 2.0F;

		for (int integer17 = integer4; integer17 <= integer7; integer17++) {
			float float18 = (float)(integer17 - integer4) / float13;

			for (int integer19 = integer3; integer19 <= integer6; integer19++) {
				float float20 = ((float)integer19 - float15) / (float12 * 0.5F);

				for (int integer21 = integer5; integer21 <= integer8; integer21++) {
					float float22 = ((float)integer21 - float16) / (float14 * 0.5F);
					if (!boolean10 || !this.a((bpg)bqc, integer19, integer17, integer21, ctd).g()) {
						float float23 = float20 * float20 + float18 * float18 + float22 * float22;
						if (float23 <= 1.05F) {
							this.a(bqc, cfj, integer19, integer17, integer21, ctd);
						}
					}
				}
			}
		}
	}

	protected void b(bqc bqc, cfj cfj, int integer3, int integer4, int integer5, ctd ctd) {
		int integer8 = this.a(integer3, integer5);
		int integer9 = this.d(integer4);
		int integer10 = this.b(integer3, integer5);
		if (ctd.b(new fu(integer8, integer9, integer10))) {
			while ((bqc.w(new fu(integer8, integer9, integer10)) || bqc.d_(new fu(integer8, integer9, integer10)).c().a()) && integer9 > 1) {
				bqc.a(new fu(integer8, integer9, integer10), cfj, 2);
				integer9--;
			}
		}
	}

	protected boolean a(bqc bqc, ctd ctd, Random random, int integer4, int integer5, int integer6, uh uh) {
		fu fu9 = new fu(this.a(integer4, integer6), this.d(integer5), this.b(integer4, integer6));
		return this.a(bqc, ctd, random, fu9, uh, null);
	}

	public static cfj a(bpg bpg, fu fu, cfj cfj) {
		fz fz4 = null;

		for (fz fz6 : fz.c.HORIZONTAL) {
			fu fu7 = fu.a(fz6);
			cfj cfj8 = bpg.d_(fu7);
			if (cfj8.a(bvs.bR)) {
				return cfj;
			}

			if (cfj8.i(bpg, fu7)) {
				if (fz4 != null) {
					fz4 = null;
					break;
				}

				fz4 = fz6;
			}
		}

		if (fz4 != null) {
			return cfj.a(byp.aq, fz4.f());
		} else {
			fz fz5 = cfj.c(byp.aq);
			fu fu6 = fu.a(fz5);
			if (bpg.d_(fu6).i(bpg, fu6)) {
				fz5 = fz5.f();
				fu6 = fu.a(fz5);
			}

			if (bpg.d_(fu6).i(bpg, fu6)) {
				fz5 = fz5.g();
				fu6 = fu.a(fz5);
			}

			if (bpg.d_(fu6).i(bpg, fu6)) {
				fz5 = fz5.f();
				fu6 = fu.a(fz5);
			}

			return cfj.a(byp.aq, fz5);
		}
	}

	protected boolean a(bqc bqc, ctd ctd, Random random, fu fu, uh uh, @Nullable cfj cfj) {
		if (ctd.b(fu) && !bqc.d_(fu).a(bvs.bR)) {
			if (cfj == null) {
				cfj = a(bqc, fu, bvs.bR.n());
			}

			bqc.a(fu, cfj, 2);
			cdl cdl8 = bqc.c(fu);
			if (cdl8 instanceof cdp) {
				((cdp)cdl8).a(uh, random.nextLong());
			}

			return true;
		} else {
			return false;
		}
	}

	protected boolean a(bqc bqc, ctd ctd, Random random, int integer4, int integer5, int integer6, fz fz, uh uh) {
		fu fu10 = new fu(this.a(integer4, integer6), this.d(integer5), this.b(integer4, integer6));
		if (ctd.b(fu10) && !bqc.d_(fu10).a(bvs.as)) {
			this.a(bqc, bvs.as.n().a(bxd.a, fz), integer4, integer5, integer6, ctd);
			cdl cdl11 = bqc.c(fu10);
			if (cdl11 instanceof cdu) {
				((cdu)cdl11).a(uh, random.nextLong());
			}

			return true;
		} else {
			return false;
		}
	}

	public void a(int integer1, int integer2, int integer3) {
		this.n.a(integer1, integer2, integer3);
	}

	@Nullable
	public fz i() {
		return this.a;
	}

	public void a(@Nullable fz fz) {
		this.a = fz;
		if (fz == null) {
			this.c = cap.NONE;
			this.b = bzj.NONE;
		} else {
			switch (fz) {
				case SOUTH:
					this.b = bzj.LEFT_RIGHT;
					this.c = cap.NONE;
					break;
				case WEST:
					this.b = bzj.LEFT_RIGHT;
					this.c = cap.CLOCKWISE_90;
					break;
				case EAST:
					this.b = bzj.NONE;
					this.c = cap.CLOCKWISE_90;
					break;
				default:
					this.b = bzj.NONE;
					this.c = cap.NONE;
			}
		}
	}

	public cap ap_() {
		return this.c;
	}

	public cmm k() {
		return this.d;
	}

	public abstract static class a {
		protected cfj a = bvs.a.n();

		protected a() {
		}

		public abstract void a(Random random, int integer2, int integer3, int integer4, boolean boolean5);

		public cfj a() {
			return this.a;
		}
	}
}
