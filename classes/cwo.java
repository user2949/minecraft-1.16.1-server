import java.util.Arrays;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableInt;

public abstract class cwo<M extends cwl<M>, S extends cwq<M>> extends cwm implements cwp {
	private static final fz[] e = fz.values();
	protected final chl a;
	protected final bqi b;
	protected final S c;
	private boolean f;
	protected final fu.a d = new fu.a();
	private final long[] g = new long[2];
	private final bpg[] h = new bpg[2];

	public cwo(chl chl, bqi bqi, S cwq) {
		super(16, 256, 8192);
		this.a = chl;
		this.b = bqi;
		this.c = cwq;
		this.d();
	}

	@Override
	protected void f(long long1) {
		this.c.d();
		if (this.c.g(go.e(long1))) {
			super.f(long1);
		}
	}

	@Nullable
	private bpg a(int integer1, int integer2) {
		long long4 = bph.a(integer1, integer2);

		for (int integer6 = 0; integer6 < 2; integer6++) {
			if (long4 == this.g[integer6]) {
				return this.h[integer6];
			}
		}

		bpg bpg6 = this.a.c(integer1, integer2);

		for (int integer7 = 1; integer7 > 0; integer7--) {
			this.g[integer7] = this.g[integer7 - 1];
			this.h[integer7] = this.h[integer7 - 1];
		}

		this.g[0] = long4;
		this.h[0] = bpg6;
		return bpg6;
	}

	private void d() {
		Arrays.fill(this.g, bph.a);
		Arrays.fill(this.h, null);
	}

	protected cfj a(long long1, @Nullable MutableInt mutableInt) {
		if (long1 == Long.MAX_VALUE) {
			if (mutableInt != null) {
				mutableInt.setValue(0);
			}

			return bvs.a.n();
		} else {
			int integer5 = go.a(fu.b(long1));
			int integer6 = go.a(fu.d(long1));
			bpg bpg7 = this.a(integer5, integer6);
			if (bpg7 == null) {
				if (mutableInt != null) {
					mutableInt.setValue(16);
				}

				return bvs.z.n();
			} else {
				this.d.g(long1);
				cfj cfj8 = bpg7.d_(this.d);
				boolean boolean9 = cfj8.l() && cfj8.e();
				if (mutableInt != null) {
					mutableInt.setValue(cfj8.b(this.a.m(), this.d));
				}

				return boolean9 ? cfj8 : bvs.a.n();
			}
		}
	}

	protected dfg a(cfj cfj, long long2, fz fz) {
		return cfj.l() ? cfj.a(this.a.m(), this.d.g(long2), fz) : dfd.a();
	}

	public static int a(bpg bpg, cfj cfj2, fu fu3, cfj cfj4, fu fu5, fz fz, int integer) {
		boolean boolean8 = cfj2.l() && cfj2.e();
		boolean boolean9 = cfj4.l() && cfj4.e();
		if (!boolean8 && !boolean9) {
			return integer;
		} else {
			dfg dfg10 = boolean8 ? cfj2.c(bpg, fu3) : dfd.a();
			dfg dfg11 = boolean9 ? cfj4.c(bpg, fu5) : dfd.a();
			return dfd.b(dfg10, dfg11, fz) ? 16 : integer;
		}
	}

	@Override
	protected boolean a(long long1) {
		return long1 == Long.MAX_VALUE;
	}

	@Override
	protected int a(long long1, long long2, int integer) {
		return 0;
	}

	@Override
	protected int c(long long1) {
		return long1 == Long.MAX_VALUE ? 0 : 15 - this.c.i(long1);
	}

	protected int a(chd chd, long long2) {
		return 15 - chd.a(go.b(fu.b(long2)), go.b(fu.c(long2)), go.b(fu.d(long2)));
	}

	@Override
	protected void a(long long1, int integer) {
		this.c.b(long1, Math.min(15, 15 - integer));
	}

	@Override
	protected int b(long long1, long long2, int integer) {
		return 0;
	}

	public boolean a() {
		return this.b() || this.c.b() || this.c.a();
	}

	public int a(int integer, boolean boolean2, boolean boolean3) {
		if (!this.f) {
			if (this.c.b()) {
				integer = this.c.b(integer);
				if (integer == 0) {
					return integer;
				}
			}

			this.c.a(this, boolean2, boolean3);
		}

		this.f = true;
		if (this.b()) {
			integer = this.b(integer);
			this.d();
			if (integer == 0) {
				return integer;
			}
		}

		this.f = false;
		this.c.e();
		return integer;
	}

	protected void a(long long1, @Nullable chd chd, boolean boolean3) {
		this.c.a(long1, chd, boolean3);
	}

	@Nullable
	@Override
	public chd a(go go) {
		return this.c.h(go.s());
	}

	@Override
	public int b(fu fu) {
		return this.c.d(fu.a());
	}

	public void a(fu fu) {
		long long3 = fu.a();
		this.f(long3);

		for (fz fz8 : e) {
			this.f(fu.a(long3, fz8));
		}
	}

	public void a(fu fu, int integer) {
	}

	@Override
	public void a(go go, boolean boolean2) {
		this.c.d(go.s(), boolean2);
	}

	public void a(bph bph, boolean boolean2) {
		long long4 = go.f(go.b(bph.b, 0, bph.c));
		this.c.b(long4, boolean2);
	}

	public void b(bph bph, boolean boolean2) {
		long long4 = go.f(go.b(bph.b, 0, bph.c));
		this.c.c(long4, boolean2);
	}
}
