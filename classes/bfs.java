import javax.annotation.Nullable;

public abstract class bfs extends bfr implements amz, anj {
	private gi<bki> b = gi.a(36, bki.b);
	private boolean c = true;
	@Nullable
	private uh d;
	private long e;

	protected bfs(aoq<?> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	protected bfs(aoq<?> aoq, double double2, double double3, double double4, bqb bqb) {
		super(aoq, bqb, double2, double3, double4);
	}

	@Override
	public void a(anw anw) {
		super.a(anw);
		if (this.l.S().b(bpx.g)) {
			anc.a(this.l, this, this);
		}
	}

	@Override
	public boolean c() {
		for (bki bki3 : this.b) {
			if (!bki3.a()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public bki a(int integer) {
		this.d(null);
		return this.b.get(integer);
	}

	@Override
	public bki a(int integer1, int integer2) {
		this.d(null);
		return ana.a(this.b, integer1, integer2);
	}

	@Override
	public bki b(int integer) {
		this.d(null);
		bki bki3 = this.b.get(integer);
		if (bki3.a()) {
			return bki.b;
		} else {
			this.b.set(integer, bki.b);
			return bki3;
		}
	}

	@Override
	public void a(int integer, bki bki) {
		this.d(null);
		this.b.set(integer, bki);
		if (!bki.a() && bki.E() > this.X_()) {
			bki.e(this.X_());
		}
	}

	@Override
	public boolean a_(int integer, bki bki) {
		if (integer >= 0 && integer < this.ab_()) {
			this.a(integer, bki);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void Z_() {
	}

	@Override
	public boolean a(bec bec) {
		return this.y ? false : !(bec.h(this) > 64.0);
	}

	@Nullable
	@Override
	public aom a(zd zd) {
		this.c = false;
		return super.a(zd);
	}

	@Override
	public void aa() {
		if (!this.l.v && this.c) {
			anc.a(this.l, this, this);
		}

		super.aa();
	}

	@Override
	protected void b(le le) {
		super.b(le);
		if (this.d != null) {
			le.a("LootTable", this.d.toString());
			if (this.e != 0L) {
				le.a("LootTableSeed", this.e);
			}
		} else {
			ana.a(le, this.b);
		}
	}

	@Override
	protected void a(le le) {
		super.a(le);
		this.b = gi.a(this.ab_(), bki.b);
		if (le.c("LootTable", 8)) {
			this.d = new uh(le.l("LootTable"));
			this.e = le.i("LootTableSeed");
		} else {
			ana.b(le, this.b);
		}
	}

	@Override
	public ang a(bec bec, anf anf) {
		bec.a((anj)this);
		return ang.a(this.l.v);
	}

	@Override
	protected void i() {
		float float2 = 0.98F;
		if (this.d == null) {
			int integer3 = 15 - bgi.b(this);
			float2 += (float)integer3 * 0.001F;
		}

		this.e(this.cB().d((double)float2, 0.0, (double)float2));
	}

	public void d(@Nullable bec bec) {
		if (this.d != null && this.l.l() != null) {
			daw daw3 = this.l.l().aH().a(this.d);
			if (bec instanceof ze) {
				aa.N.a((ze)bec, this.d);
			}

			this.d = null;
			dat.a a4 = new dat.a((zd)this.l).a(dda.f, this.cA()).a(this.e);
			if (bec != null) {
				a4.a(bec.eU()).a(dda.a, bec);
			}

			daw3.a(this, a4.a(dcz.b));
		}
	}

	@Override
	public void aa_() {
		this.d(null);
		this.b.clear();
	}

	public void a(uh uh, long long2) {
		this.d = uh;
		this.e = long2;
	}

	@Nullable
	@Override
	public bgi createMenu(int integer, beb beb, bec bec) {
		if (this.d != null && bec.a_()) {
			return null;
		} else {
			this.d(beb.e);
			return this.a(integer, beb);
		}
	}

	protected abstract bgi a(int integer, beb beb);
}
