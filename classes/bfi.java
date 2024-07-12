import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class bfi extends bcc {
	protected static final tq<Boolean> c = tt.a(bfi.class, ts.i);
	private static final Predicate<bbg> b = bbg -> !bbg.p() && bbg.aU() && bki.b(bbg.g(), bfh.s());
	@Nullable
	protected bfh d;
	private int bv;
	private boolean bw;
	private int bx;

	protected bfi(aoq<? extends bfi> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(1, new bfi.b<>(this));
		this.br.a(3, new avc<>(this));
		this.br.a(4, new bfi.d(this, 1.05F, 1));
		this.br.a(5, new bfi.c(this));
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(c, false);
	}

	public abstract void a(int integer, boolean boolean2);

	public boolean fa() {
		return this.bw;
	}

	public void w(boolean boolean1) {
		this.bw = boolean1;
	}

	@Override
	public void k() {
		if (this.l instanceof zd && this.aU()) {
			bfh bfh2 = this.fb();
			if (this.fa()) {
				if (bfh2 == null) {
					if (this.l.Q() % 20L == 0L) {
						bfh bfh3 = ((zd)this.l).c_(this.cA());
						if (bfh3 != null && bfj.a(this, bfh3)) {
							bfh3.a(bfh3.k(), this, null, true);
						}
					}
				} else {
					aoy aoy3 = this.A();
					if (aoy3 != null && (aoy3.U() == aoq.bb || aoy3.U() == aoq.K)) {
						this.aP = 0;
					}
				}
			}
		}

		super.k();
	}

	@Override
	protected void eR() {
		this.aP += 2;
	}

	@Override
	public void a(anw anw) {
		if (this.l instanceof zd) {
			aom aom3 = anw.k();
			bfh bfh4 = this.fb();
			if (bfh4 != null) {
				if (this.eT()) {
					bfh4.c(this.fd());
				}

				if (aom3 != null && aom3.U() == aoq.bb) {
					bfh4.a(aom3);
				}

				bfh4.a(this, false);
			}

			if (this.eT() && bfh4 == null && ((zd)this.l).c_(this.cA()) == null) {
				bki bki5 = this.b(aor.HEAD);
				bec bec6 = null;
				if (aom3 instanceof bec) {
					bec6 = (bec)aom3;
				} else if (aom3 instanceof azk) {
					azk azk8 = (azk)aom3;
					aoy aoy9 = azk8.eO();
					if (azk8.eL() && aoy9 instanceof bec) {
						bec6 = (bec)aoy9;
					}
				}

				if (!bki5.a() && bki.b(bki5, bfh.s()) && bec6 != null) {
					aog aog8 = bec6.b(aoi.E);
					int integer9 = 1;
					if (aog8 != null) {
						integer9 += aog8.c();
						bec6.c(aoi.E);
					} else {
						integer9--;
					}

					integer9 = aec.a(integer9, 0, 4);
					aog aog10 = new aog(aoi.E, 120000, integer9, false, false, true);
					if (!this.l.S().b(bpx.x)) {
						bec6.c(aog10);
					}
				}
			}
		}

		super.a(anw);
	}

	@Override
	public boolean eU() {
		return !this.fc();
	}

	public void a(@Nullable bfh bfh) {
		this.d = bfh;
	}

	@Nullable
	public bfh fb() {
		return this.d;
	}

	public boolean fc() {
		return this.fb() != null && this.fb().v();
	}

	public void a(int integer) {
		this.bv = integer;
	}

	public int fd() {
		return this.bv;
	}

	public void x(boolean boolean1) {
		this.S.b(c, boolean1);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("Wave", this.bv);
		le.a("CanJoinRaid", this.bw);
		if (this.d != null) {
			le.b("RaidId", this.d.u());
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.bv = le.h("Wave");
		this.bw = le.q("CanJoinRaid");
		if (le.c("RaidId", 3)) {
			if (this.l instanceof zd) {
				this.d = ((zd)this.l).y().a(le.h("RaidId"));
			}

			if (this.d != null) {
				this.d.a(this.bv, this, false);
				if (this.eT()) {
					this.d.a(this.bv, this);
				}
			}
		}
	}

	@Override
	protected void b(bbg bbg) {
		bki bki3 = bbg.g();
		boolean boolean4 = this.fc() && this.fb().b(this.fd()) != null;
		if (this.fc() && !boolean4 && bki.b(bki3, bfh.s())) {
			aor aor5 = aor.HEAD;
			bki bki6 = this.b(aor5);
			double double7 = (double)this.e(aor5);
			if (!bki6.a() && (double)Math.max(this.J.nextFloat() - 0.1F, 0.0F) < double7) {
				this.a(bki6);
			}

			this.a(bbg);
			this.a(aor5, bki3);
			this.a(bbg, bki3.E());
			bbg.aa();
			this.fb().a(this.fd(), this);
			this.t(true);
		} else {
			super.b(bbg);
		}
	}

	@Override
	public boolean h(double double1) {
		return this.fb() == null ? super.h(double1) : false;
	}

	@Override
	public boolean K() {
		return super.K() || this.fb() != null;
	}

	public int ff() {
		return this.bx;
	}

	public void b(int integer) {
		this.bx = integer;
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.fc()) {
			this.fb().p();
		}

		return super.a(anw, float2);
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.w(this.U() != aoq.aR || apb != apb.NATURAL);
		return super.a(bqc, ane, apb, apo, le);
	}

	public abstract ack eM();

	public class a extends aug {
		private final bfi c;
		private final float d;
		public final axs a = new axs().a(8.0).d().a().b().c().e();

		public a(bbj bbj, float float3) {
			this.c = bbj;
			this.d = float3 * float3;
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		}

		@Override
		public boolean a() {
			aoy aoy2 = this.c.cY();
			return this.c.fb() == null && this.c.eW() && this.c.A() != null && !this.c.eG() && (aoy2 == null || aoy2.U() != aoq.bb);
		}

		@Override
		public void c() {
			super.c();
			this.c.x().o();

			for (bfi bfi4 : this.c.l.a(bfi.class, this.a, this.c, this.c.cb().c(8.0, 8.0, 8.0))) {
				bfi4.i(this.c.A());
			}
		}

		@Override
		public void d() {
			super.d();
			aoy aoy2 = this.c.A();
			if (aoy2 != null) {
				for (bfi bfi5 : this.c.l.a(bfi.class, this.a, this.c, this.c.cb().c(8.0, 8.0, 8.0))) {
					bfi5.i(aoy2);
					bfi5.s(true);
				}

				this.c.s(true);
			}
		}

		@Override
		public void e() {
			aoy aoy2 = this.c.A();
			if (aoy2 != null) {
				if (this.c.h((aom)aoy2) > (double)this.d) {
					this.c.t().a(aoy2, 30.0F, 30.0F);
					if (this.c.J.nextInt(50) == 0) {
						this.c.F();
					}
				} else {
					this.c.s(true);
				}

				super.e();
			}
		}
	}

	public class b<T extends bfi> extends aug {
		private final T b;

		public b(T bfi2) {
			this.b = bfi2;
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			bfh bfh2 = this.b.fb();
			if (this.b.fc() && !this.b.fb().a() && this.b.eO() && !bki.b(this.b.b(aor.HEAD), bfh.s())) {
				bfi bfi3 = bfh2.b(this.b.fd());
				if (bfi3 == null || !bfi3.aU()) {
					List<bbg> list4 = this.b.l.a(bbg.class, this.b.cb().c(16.0, 8.0, 16.0), bfi.b);
					if (!list4.isEmpty()) {
						return this.b.x().a((aom)list4.get(0), 1.15F);
					}
				}

				return false;
			} else {
				return false;
			}
		}

		@Override
		public void e() {
			if (this.b.x().h().a(this.b.cz(), 1.414)) {
				List<bbg> list2 = this.b.l.a(bbg.class, this.b.cb().c(4.0, 4.0, 4.0), bfi.b);
				if (!list2.isEmpty()) {
					this.b.b((bbg)list2.get(0));
				}
			}
		}
	}

	public class c extends aug {
		private final bfi b;

		c(bfi bfi2) {
			this.b = bfi2;
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			bfh bfh2 = this.b.fb();
			return this.b.aU() && this.b.A() == null && bfh2 != null && bfh2.f();
		}

		@Override
		public void c() {
			this.b.x(true);
			super.c();
		}

		@Override
		public void d() {
			this.b.x(false);
			super.d();
		}

		@Override
		public void e() {
			if (!this.b.av() && this.b.J.nextInt(100) == 0) {
				bfi.this.a(bfi.this.eM(), bfi.this.dF(), bfi.this.dG());
			}

			if (!this.b.bn() && this.b.J.nextInt(50) == 0) {
				this.b.v().a();
			}

			super.e();
		}
	}

	static class d extends aug {
		private final bfi a;
		private final double b;
		private fu c;
		private final List<fu> d = Lists.<fu>newArrayList();
		private final int e;
		private boolean f;

		public d(bfi bfi, double double2, int integer) {
			this.a = bfi;
			this.b = double2;
			this.e = integer;
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			this.j();
			return this.g() && this.h() && this.a.A() == null;
		}

		private boolean g() {
			return this.a.fc() && !this.a.fb().a();
		}

		private boolean h() {
			zd zd2 = (zd)this.a.l;
			fu fu3 = this.a.cA();
			Optional<fu> optional4 = zd2.x().a(ayc -> ayc == ayc.r, this::a, axz.b.ANY, fu3, 48, this.a.J);
			if (!optional4.isPresent()) {
				return false;
			} else {
				this.c = ((fu)optional4.get()).h();
				return true;
			}
		}

		@Override
		public boolean b() {
			return this.a.x().m() ? false : this.a.A() == null && !this.c.a(this.a.cz(), (double)(this.a.cx() + (float)this.e)) && !this.f;
		}

		@Override
		public void d() {
			if (this.c.a(this.a.cz(), (double)this.e)) {
				this.d.add(this.c);
			}
		}

		@Override
		public void c() {
			super.c();
			this.a.n(0);
			this.a.x().a((double)this.c.u(), (double)this.c.v(), (double)this.c.w(), this.b);
			this.f = false;
		}

		@Override
		public void e() {
			if (this.a.x().m()) {
				dem dem2 = dem.c(this.c);
				dem dem3 = axu.a(this.a, 16, 7, dem2, (float) (Math.PI / 10));
				if (dem3 == null) {
					dem3 = axu.b(this.a, 8, 7, dem2);
				}

				if (dem3 == null) {
					this.f = true;
					return;
				}

				this.a.x().a(dem3.b, dem3.c, dem3.d, this.b);
			}
		}

		private boolean a(fu fu) {
			for (fu fu4 : this.d) {
				if (Objects.equals(fu, fu4)) {
					return false;
				}
			}

			return true;
		}

		private void j() {
			if (this.d.size() > 2) {
				this.d.remove(0);
			}
		}
	}
}
