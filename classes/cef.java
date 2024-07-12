import java.util.Random;
import javax.annotation.Nullable;

public abstract class cef extends cdf {
	@Nullable
	protected uh g;
	protected long h;

	protected cef(cdm<?> cdm) {
		super(cdm);
	}

	public static void a(bpg bpg, Random random, fu fu, uh uh) {
		cdl cdl5 = bpg.c(fu);
		if (cdl5 instanceof cef) {
			((cef)cdl5).a(uh, random.nextLong());
		}
	}

	protected boolean b(le le) {
		if (le.c("LootTable", 8)) {
			this.g = new uh(le.l("LootTable"));
			this.h = le.i("LootTableSeed");
			return true;
		} else {
			return false;
		}
	}

	protected boolean c(le le) {
		if (this.g == null) {
			return false;
		} else {
			le.a("LootTable", this.g.toString());
			if (this.h != 0L) {
				le.a("LootTableSeed", this.h);
			}

			return true;
		}
	}

	public void d(@Nullable bec bec) {
		if (this.g != null && this.d.l() != null) {
			daw daw3 = this.d.l().aH().a(this.g);
			if (bec instanceof ze) {
				aa.N.a((ze)bec, this.g);
			}

			this.g = null;
			dat.a a4 = new dat.a((zd)this.d).a(dda.f, new fu(this.e)).a(this.h);
			if (bec != null) {
				a4.a(bec.eU()).a(dda.a, bec);
			}

			daw3.a(this, a4.a(dcz.b));
		}
	}

	public void a(uh uh, long long2) {
		this.g = uh;
		this.h = long2;
	}

	@Override
	public boolean c() {
		this.d(null);
		return this.f().stream().allMatch(bki::a);
	}

	@Override
	public bki a(int integer) {
		this.d(null);
		return this.f().get(integer);
	}

	@Override
	public bki a(int integer1, int integer2) {
		this.d(null);
		bki bki4 = ana.a(this.f(), integer1, integer2);
		if (!bki4.a()) {
			this.Z_();
		}

		return bki4;
	}

	@Override
	public bki b(int integer) {
		this.d(null);
		return ana.a(this.f(), integer);
	}

	@Override
	public void a(int integer, bki bki) {
		this.d(null);
		this.f().set(integer, bki);
		if (bki.E() > this.X_()) {
			bki.e(this.X_());
		}

		this.Z_();
	}

	@Override
	public boolean a(bec bec) {
		return this.d.c(this.e) != this ? false : !(bec.g((double)this.e.u() + 0.5, (double)this.e.v() + 0.5, (double)this.e.w() + 0.5) > 64.0);
	}

	@Override
	public void aa_() {
		this.f().clear();
	}

	protected abstract gi<bki> f();

	protected abstract void a(gi<bki> gi);

	@Override
	public boolean e(bec bec) {
		return super.e(bec) && (this.g == null || !bec.a_());
	}

	@Nullable
	@Override
	public bgi createMenu(int integer, beb beb, bec bec) {
		if (this.e(bec)) {
			this.d(beb.e);
			return this.a(integer, beb);
		} else {
			return null;
		}
	}
}
