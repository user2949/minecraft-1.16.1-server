import bdt.f;
import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class bdk extends aok implements bdo, boy {
	private static final tq<Integer> bw = tt.a(bdk.class, ts.b);
	@Nullable
	private bec bx;
	@Nullable
	protected bpa bv;
	private final anm by = new anm(8);

	public bdk(aoq<? extends bdk> aoq, bqb bqb) {
		super(aoq, bqb);
		this.a(czb.DANGER_FIRE, 16.0F);
		this.a(czb.DAMAGE_FIRE, -1.0F);
	}

	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		if (apo == null) {
			apo = new aok.a();
			((aok.a)apo).a(false);
		}

		return super.a(bqc, ane, apb, apo, le);
	}

	public int eL() {
		return this.S.a(bw);
	}

	public void s(int integer) {
		this.S.b(bw, integer);
	}

	@Override
	public int eM() {
		return 0;
	}

	@Override
	protected float b(apj apj, aon aon) {
		return this.x_() ? 0.81F : 1.62F;
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bw, 0);
	}

	@Override
	public void f(@Nullable bec bec) {
		this.bx = bec;
	}

	@Nullable
	@Override
	public bec eN() {
		return this.bx;
	}

	public boolean eO() {
		return this.bx != null;
	}

	@Override
	public bpa eP() {
		if (this.bv == null) {
			this.bv = new bpa();
			this.eW();
		}

		return this.bv;
	}

	@Override
	public void t(int integer) {
	}

	@Override
	public void a(boz boz) {
		boz.j();
		this.e = -this.D();
		this.b(boz);
		if (this.bx instanceof ze) {
			aa.s.a((ze)this.bx, this, boz.d());
		}
	}

	protected abstract void b(boz boz);

	@Override
	public boolean eQ() {
		return true;
	}

	@Override
	public void k(bki bki) {
		if (!this.l.v && this.e > -this.D() + 20) {
			this.e = -this.D();
			this.a(this.t(!bki.a()), this.dF(), this.dG());
		}
	}

	@Override
	public ack eR() {
		return acl.pR;
	}

	protected ack t(boolean boolean1) {
		return boolean1 ? acl.pR : acl.pP;
	}

	public void eS() {
		this.a(acl.pM, this.dF(), this.dG());
	}

	@Override
	public void b(le le) {
		super.b(le);
		bpa bpa3 = this.eP();
		if (!bpa3.isEmpty()) {
			le.a("Offers", bpa3.a());
		}

		le.a("Inventory", this.by.g());
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("Offers", 10)) {
			this.bv = new bpa(le.p("Offers"));
		}

		this.by.a(le.d("Inventory", 10));
	}

	@Nullable
	@Override
	public aom a(zd zd) {
		this.eT();
		return super.a(zd);
	}

	protected void eT() {
		this.f(null);
	}

	@Override
	public void a(anw anw) {
		super.a(anw);
		this.eT();
	}

	@Override
	public boolean a(bec bec) {
		return false;
	}

	public anm eU() {
		return this.by;
	}

	@Override
	public boolean a_(int integer, bki bki) {
		if (super.a_(integer, bki)) {
			return true;
		} else {
			int integer4 = integer - 300;
			if (integer4 >= 0 && integer4 < this.by.ab_()) {
				this.by.a(integer4, bki);
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public bqb eV() {
		return this.l;
	}

	protected abstract void eW();

	protected void a(bpa bpa, f[] arr, int integer) {
		Set<Integer> set5 = Sets.<Integer>newHashSet();
		if (arr.length > integer) {
			while (set5.size() < integer) {
				set5.add(this.J.nextInt(arr.length));
			}
		} else {
			for (int integer6 = 0; integer6 < arr.length; integer6++) {
				set5.add(integer6);
			}
		}

		for (Integer integer7 : set5) {
			f f8 = arr[integer7];
			boz boz9 = f8.a(this, this.J);
			if (boz9 != null) {
				bpa.add(boz9);
			}
		}
	}
}
