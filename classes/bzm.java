import com.google.common.cache.LoadingCache;
import java.util.Random;
import javax.annotation.Nullable;

public class bzm extends bvr {
	public static final cgg<fz.a> a = cfz.E;
	protected static final dfg b = bvr.a(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
	protected static final dfg c = bvr.a(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

	public bzm(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.a.X));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		switch ((fz.a)cfj.c(a)) {
			case Z:
				return c;
			case X:
			default:
				return b;
		}
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (zd.m().g() && zd.S().b(bpx.d) && random.nextInt(2000) < zd.ac().a()) {
			while (zd.d_(fu).a(this)) {
				fu = fu.c();
			}

			if (zd.d_(fu).a(zd, fu, aoq.ba)) {
				aom aom6 = aoq.ba.a(zd, null, null, null, fu.b(), apb.STRUCTURE, false, false);
				if (aom6 != null) {
					aom6.ae = aom6.bh();
				}
			}
		}
	}

	public static boolean a(bqc bqc, fu fu) {
		bzm.a a3 = b(bqc, fu);
		if (a3 != null) {
			a3.e();
			return true;
		} else {
			return false;
		}
	}

	@Nullable
	public static bzm.a b(bqc bqc, fu fu) {
		bzm.a a3 = new bzm.a(bqc, fu, fz.a.X);
		if (a3.d() && a3.e == 0) {
			return a3;
		} else {
			bzm.a a4 = new bzm.a(bqc, fu, fz.a.Z);
			return a4.d() && a4.e == 0 ? a4 : null;
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		fz.a a8 = fz.n();
		fz.a a9 = cfj1.c(a);
		boolean boolean10 = a9 != a8 && a8.d();
		return !boolean10 && !cfj3.a(this) && !new bzm.a(bqc, fu5, a9).f() ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (!aom.bn() && !aom.bo() && aom.bK()) {
			aom.d(fu);
		}
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		switch (cap) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch ((fz.a)cfj.c(a)) {
					case Z:
						return cfj.a(a, fz.a.X);
					case X:
						return cfj.a(a, fz.a.Z);
					default:
						return cfj;
				}
			default:
				return cfj;
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bzm.a);
	}

	public static cfo.b c(bqc bqc, fu fu) {
		fz.a a3 = fz.a.Z;
		bzm.a a4 = new bzm.a(bqc, fu, fz.a.X);
		LoadingCache<fu, cfn> loadingCache5 = cfo.a(bqc, true);
		if (!a4.d()) {
			a3 = fz.a.X;
			a4 = new bzm.a(bqc, fu, fz.a.Z);
		}

		if (!a4.d()) {
			return new cfo.b(fu, fz.NORTH, fz.UP, loadingCache5, 1, 1, 1);
		} else {
			int[] arr6 = new int[fz.b.values().length];
			fz fz7 = a4.c.h();
			fu fu8 = a4.f.b(a4.a() - 1);

			for (fz.b b12 : fz.b.values()) {
				cfo.b b13 = new cfo.b(fz7.e() == b12 ? fu8 : fu8.a(a4.c, a4.b() - 1), fz.a(b12, a3), fz.UP, loadingCache5, a4.b(), a4.a(), 1);

				for (int integer14 = 0; integer14 < a4.b(); integer14++) {
					for (int integer15 = 0; integer15 < a4.a(); integer15++) {
						cfn cfn16 = b13.a(integer14, integer15, 1);
						if (!cfn16.a().g()) {
							arr6[b12.ordinal()]++;
						}
					}
				}
			}

			fz.b b9 = fz.b.POSITIVE;

			for (fz.b b13 : fz.b.values()) {
				if (arr6[b13.ordinal()] < arr6[b9.ordinal()]) {
					b9 = b13;
				}
			}

			return new cfo.b(fz7.e() == b9 ? fu8 : fu8.a(a4.c, a4.b() - 1), fz.a(b9, a3), fz.UP, loadingCache5, a4.b(), a4.a(), 1);
		}
	}

	public static class a {
		private final bqc a;
		private final fz.a b;
		private final fz c;
		private final fz d;
		private int e;
		@Nullable
		private fu f;
		private int g;
		private int h;

		public a(bqc bqc, fu fu, fz.a a) {
			this.a = bqc;
			this.b = a;
			if (a == fz.a.X) {
				this.d = fz.EAST;
				this.c = fz.WEST;
			} else {
				this.d = fz.NORTH;
				this.c = fz.SOUTH;
			}

			fu fu5 = fu;

			while (fu.v() > fu5.v() - 21 && fu.v() > 0 && this.a(bqc.d_(fu.c()))) {
				fu = fu.c();
			}

			int integer6 = this.a(fu, this.d) - 1;
			if (integer6 >= 0) {
				this.f = fu.a(this.d, integer6);
				this.h = this.a(this.f, this.c);
				if (this.h < 2 || this.h > 21) {
					this.f = null;
					this.h = 0;
				}
			}

			if (this.f != null) {
				this.g = this.c();
			}
		}

		protected int a(fu fu, fz fz) {
			int integer4;
			for (integer4 = 0; integer4 < 22; integer4++) {
				fu fu5 = fu.a(fz, integer4);
				if (!this.a(this.a.d_(fu5)) || !this.a.d_(fu5.c()).a(bvs.bK)) {
					break;
				}
			}

			return this.a.d_(fu.a(fz, integer4)).a(bvs.bK) ? integer4 : 0;
		}

		public int a() {
			return this.g;
		}

		public int b() {
			return this.h;
		}

		protected int c() {
			label56:
			for (this.g = 0; this.g < 21; this.g++) {
				for (int integer2 = 0; integer2 < this.h; integer2++) {
					fu fu3 = this.f.a(this.c, integer2).b(this.g);
					cfj cfj4 = this.a.d_(fu3);
					if (!this.a(cfj4)) {
						break label56;
					}

					if (cfj4.a(bvs.cT)) {
						this.e++;
					}

					if (integer2 == 0 ? !this.a.d_(fu3.a(this.d)).a(bvs.bK) : integer2 == this.h - 1 && !this.a.d_(fu3.a(this.c)).a(bvs.bK)) {
						break label56;
					}
				}
			}

			for (int integer2 = 0; integer2 < this.h; integer2++) {
				if (!this.a.d_(this.f.a(this.c, integer2).b(this.g)).a(bvs.bK)) {
					this.g = 0;
					break;
				}
			}

			if (this.g <= 21 && this.g >= 3) {
				return this.g;
			} else {
				this.f = null;
				this.h = 0;
				this.g = 0;
				return 0;
			}
		}

		protected boolean a(cfj cfj) {
			return cfj.g() || cfj.a(acx.am) || cfj.a(bvs.cT);
		}

		public boolean d() {
			return this.f != null && this.h >= 2 && this.h <= 21 && this.g >= 3 && this.g <= 21;
		}

		public void e() {
			for (int integer2 = 0; integer2 < this.h; integer2++) {
				fu fu3 = this.f.a(this.c, integer2);

				for (int integer4 = 0; integer4 < this.g; integer4++) {
					this.a.a(fu3.b(integer4), bvs.cT.n().a(bzm.a, this.b), 18);
				}
			}
		}

		private boolean g() {
			return this.e >= this.h * this.g;
		}

		public boolean f() {
			return this.d() && this.g();
		}
	}
}
