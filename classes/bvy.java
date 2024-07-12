import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public abstract class bvy extends bxq {
	public static final cga a = cfz.w;
	protected static final dfg b = bvr.a(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
	protected static final dfg c = bvr.a(5.0, 14.0, 6.0, 11.0, 16.0, 10.0);
	protected static final dfg d = bvr.a(6.0, 0.0, 5.0, 10.0, 2.0, 11.0);
	protected static final dfg e = bvr.a(5.0, 0.0, 6.0, 11.0, 2.0, 10.0);
	protected static final dfg f = bvr.a(5.0, 6.0, 14.0, 11.0, 10.0, 16.0);
	protected static final dfg g = bvr.a(5.0, 6.0, 0.0, 11.0, 10.0, 2.0);
	protected static final dfg h = bvr.a(14.0, 6.0, 5.0, 16.0, 10.0, 11.0);
	protected static final dfg i = bvr.a(0.0, 6.0, 5.0, 2.0, 10.0, 11.0);
	protected static final dfg j = bvr.a(6.0, 15.0, 5.0, 10.0, 16.0, 11.0);
	protected static final dfg k = bvr.a(5.0, 15.0, 6.0, 11.0, 16.0, 10.0);
	protected static final dfg o = bvr.a(6.0, 0.0, 5.0, 10.0, 1.0, 11.0);
	protected static final dfg p = bvr.a(5.0, 0.0, 6.0, 11.0, 1.0, 10.0);
	protected static final dfg q = bvr.a(5.0, 6.0, 15.0, 11.0, 10.0, 16.0);
	protected static final dfg r = bvr.a(5.0, 6.0, 0.0, 11.0, 10.0, 1.0);
	protected static final dfg s = bvr.a(15.0, 6.0, 5.0, 16.0, 10.0, 11.0);
	protected static final dfg t = bvr.a(0.0, 6.0, 5.0, 1.0, 10.0, 11.0);
	private final boolean v;

	protected bvy(boolean boolean1, cfi.c c) {
		super(c);
		this.j(this.n.b().a(aq, fz.NORTH).a(a, Boolean.valueOf(false)).a(u, cfv.WALL));
		this.v = boolean1;
	}

	private int c() {
		return this.v ? 30 : 20;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		fz fz6 = cfj.c(aq);
		boolean boolean7 = (Boolean)cfj.c(a);
		switch ((cfv)cfj.c(u)) {
			case FLOOR:
				if (fz6.n() == fz.a.X) {
					return boolean7 ? o : d;
				}

				return boolean7 ? p : e;
			case WALL:
				switch (fz6) {
					case EAST:
						return boolean7 ? t : i;
					case WEST:
						return boolean7 ? s : h;
					case SOUTH:
						return boolean7 ? r : g;
					case NORTH:
					default:
						return boolean7 ? q : f;
				}
			case CEILING:
			default:
				if (fz6.n() == fz.a.X) {
					return boolean7 ? j : b;
				} else {
					return boolean7 ? k : c;
				}
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if ((Boolean)cfj.c(a)) {
			return ang.CONSUME;
		} else {
			this.d(cfj, bqb, fu);
			this.a(bec, bqb, fu, true);
			return ang.a(bqb.v);
		}
	}

	public void d(cfj cfj, bqb bqb, fu fu) {
		bqb.a(fu, cfj.a(a, Boolean.valueOf(true)), 3);
		this.f(cfj, bqb, fu);
		bqb.G().a(fu, this, this.c());
	}

	protected void a(@Nullable bec bec, bqc bqc, fu fu, boolean boolean4) {
		bqc.a(boolean4 ? bec : null, fu, this.a(boolean4), acm.BLOCKS, 0.3F, boolean4 ? 0.6F : 0.5F);
	}

	protected abstract ack a(boolean boolean1);

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!boolean5 && !cfj1.a(cfj4.b())) {
			if ((Boolean)cfj1.c(a)) {
				this.f(cfj1, bqb, fu);
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.c(a) ? 15 : 0;
	}

	@Override
	public int b(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.c(a) && h(cfj) == fz ? 15 : 0;
	}

	@Override
	public boolean b_(cfj cfj) {
		return true;
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if ((Boolean)cfj.c(a)) {
			if (this.v) {
				this.e(cfj, zd, fu);
			} else {
				zd.a(fu, cfj.a(a, Boolean.valueOf(false)), 3);
				this.f(cfj, zd, fu);
				this.a(null, zd, fu, false);
			}
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (!bqb.v && this.v && !(Boolean)cfj.c(a)) {
			this.e(cfj, bqb, fu);
		}
	}

	private void e(cfj cfj, bqb bqb, fu fu) {
		List<? extends aom> list5 = bqb.a(beg.class, cfj.j(bqb, fu).a().a(fu));
		boolean boolean6 = !list5.isEmpty();
		boolean boolean7 = (Boolean)cfj.c(a);
		if (boolean6 != boolean7) {
			bqb.a(fu, cfj.a(a, Boolean.valueOf(boolean6)), 3);
			this.f(cfj, bqb, fu);
			this.a(null, bqb, fu, boolean6);
		}

		if (boolean6) {
			bqb.G().a(new fu(fu), this, this.c());
		}
	}

	private void f(cfj cfj, bqb bqb, fu fu) {
		bqb.b(fu, this);
		bqb.b(fu.a(h(cfj).f()), this);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(aq, bvy.a, u);
	}
}
