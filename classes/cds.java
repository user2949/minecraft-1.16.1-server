import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class cds extends cdl implements ceo {
	private static final bvr[] b = new bvr[]{bvs.gq, bvs.gr, bvs.gz, bvs.gs};
	public int a;
	private float c;
	private boolean g;
	private boolean h;
	private final List<fu> i = Lists.<fu>newArrayList();
	@Nullable
	private aoy j;
	@Nullable
	private UUID k;
	private long l;

	public cds() {
		this(cdm.y);
	}

	public cds(cdm<?> cdm) {
		super(cdm);
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		if (le.b("Target")) {
			this.k = le.a("Target");
		} else {
			this.k = null;
		}
	}

	@Override
	public le a(le le) {
		super.a(le);
		if (this.j != null) {
			le.a("Target", this.j.bR());
		}

		return le;
	}

	@Nullable
	@Override
	public nv a() {
		return new nv(this.e, 5, this.b());
	}

	@Override
	public le b() {
		return this.a(new le());
	}

	@Override
	public void al_() {
		this.a++;
		long long2 = this.d.Q();
		if (long2 % 40L == 0L) {
			this.a(this.h());
			if (!this.d.v && this.d()) {
				this.j();
				this.k();
			}
		}

		if (long2 % 80L == 0L && this.d()) {
			this.a(acl.bZ);
		}

		if (long2 > this.l && this.d()) {
			this.l = long2 + 60L + (long)this.d.v_().nextInt(40);
			this.a(acl.ca);
		}

		if (this.d.v) {
			this.l();
			this.y();
			if (this.d()) {
				this.c++;
			}
		}
	}

	private boolean h() {
		this.i.clear();

		for (int integer2 = -1; integer2 <= 1; integer2++) {
			for (int integer3 = -1; integer3 <= 1; integer3++) {
				for (int integer4 = -1; integer4 <= 1; integer4++) {
					fu fu5 = this.e.b(integer2, integer3, integer4);
					if (!this.d.A(fu5)) {
						return false;
					}
				}
			}
		}

		for (int integer2 = -2; integer2 <= 2; integer2++) {
			for (int integer3 = -2; integer3 <= 2; integer3++) {
				for (int integer4x = -2; integer4x <= 2; integer4x++) {
					int integer5 = Math.abs(integer2);
					int integer6 = Math.abs(integer3);
					int integer7 = Math.abs(integer4x);
					if ((integer5 > 1 || integer6 > 1 || integer7 > 1)
						&& (
							integer2 == 0 && (integer6 == 2 || integer7 == 2)
								|| integer3 == 0 && (integer5 == 2 || integer7 == 2)
								|| integer4x == 0 && (integer5 == 2 || integer6 == 2)
						)) {
						fu fu8 = this.e.b(integer2, integer3, integer4x);
						cfj cfj9 = this.d.d_(fu8);

						for (bvr bvr13 : b) {
							if (cfj9.a(bvr13)) {
								this.i.add(fu8);
							}
						}
					}
				}
			}
		}

		this.b(this.i.size() >= 42);
		return this.i.size() >= 16;
	}

	private void j() {
		int integer2 = this.i.size();
		int integer3 = integer2 / 7 * 16;
		int integer4 = this.e.u();
		int integer5 = this.e.v();
		int integer6 = this.e.w();
		deg deg7 = new deg((double)integer4, (double)integer5, (double)integer6, (double)(integer4 + 1), (double)(integer5 + 1), (double)(integer6 + 1))
			.g((double)integer3)
			.b(0.0, (double)this.d.I(), 0.0);
		List<bec> list8 = this.d.a(bec.class, deg7);
		if (!list8.isEmpty()) {
			for (bec bec10 : list8) {
				if (this.e.a(bec10.cA(), (double)integer3) && bec10.aB()) {
					bec10.c(new aog(aoi.C, 260, 0, true, true));
				}
			}
		}
	}

	private void k() {
		aoy aoy2 = this.j;
		int integer3 = this.i.size();
		if (integer3 < 42) {
			this.j = null;
		} else if (this.j == null && this.k != null) {
			this.j = this.x();
			this.k = null;
		} else if (this.j == null) {
			List<aoy> list4 = this.d.a(aoy.class, this.m(), aoy -> aoy instanceof bbt && aoy.aB());
			if (!list4.isEmpty()) {
				this.j = (aoy)list4.get(this.d.t.nextInt(list4.size()));
			}
		} else if (!this.j.aU() || !this.e.a(this.j.cA(), 8.0)) {
			this.j = null;
		}

		if (this.j != null) {
			this.d.a(null, this.j.cC(), this.j.cD(), this.j.cG(), acl.cb, acm.BLOCKS, 1.0F, 1.0F);
			this.j.a(anw.o, 4.0F);
		}

		if (aoy2 != this.j) {
			cfj cfj4 = this.p();
			this.d.a(this.e, cfj4, cfj4, 2);
		}
	}

	private void l() {
		if (this.k == null) {
			this.j = null;
		} else if (this.j == null || !this.j.bR().equals(this.k)) {
			this.j = this.x();
			if (this.j == null) {
				this.k = null;
			}
		}
	}

	private deg m() {
		int integer2 = this.e.u();
		int integer3 = this.e.v();
		int integer4 = this.e.w();
		return new deg((double)integer2, (double)integer3, (double)integer4, (double)(integer2 + 1), (double)(integer3 + 1), (double)(integer4 + 1)).g(8.0);
	}

	@Nullable
	private aoy x() {
		List<aoy> list2 = this.d.a(aoy.class, this.m(), aoy -> aoy.bR().equals(this.k));
		return list2.size() == 1 ? (aoy)list2.get(0) : null;
	}

	private void y() {
		Random random2 = this.d.t;
		double double3 = (double)(aec.a((float)(this.a + 35) * 0.1F) / 2.0F + 0.5F);
		double3 = (double3 * double3 + double3) * 0.3F;
		dem dem5 = new dem((double)this.e.u() + 0.5, (double)this.e.v() + 1.5 + double3, (double)this.e.w() + 0.5);

		for (fu fu7 : this.i) {
			if (random2.nextInt(50) == 0) {
				float float8 = -0.5F + random2.nextFloat();
				float float9 = -2.0F + random2.nextFloat();
				float float10 = -0.5F + random2.nextFloat();
				fu fu11 = fu7.b(this.e);
				dem dem12 = new dem((double)float8, (double)float9, (double)float10).b((double)fu11.u(), (double)fu11.v(), (double)fu11.w());
				this.d.a(hh.ae, dem5.b, dem5.c, dem5.d, dem12.b, dem12.c, dem12.d);
			}
		}

		if (this.j != null) {
			dem dem6 = new dem(this.j.cC(), this.j.cF(), this.j.cG());
			float float7 = (-0.5F + random2.nextFloat()) * (3.0F + this.j.cx());
			float float8 = -1.0F + random2.nextFloat() * this.j.cy();
			float float9 = (-0.5F + random2.nextFloat()) * (3.0F + this.j.cx());
			dem dem10 = new dem((double)float7, (double)float8, (double)float9);
			this.d.a(hh.ae, dem6.b, dem6.c, dem6.d, dem10.b, dem10.c, dem10.d);
		}
	}

	public boolean d() {
		return this.g;
	}

	private void a(boolean boolean1) {
		if (boolean1 != this.g) {
			this.a(boolean1 ? acl.bY : acl.cc);
		}

		this.g = boolean1;
	}

	private void b(boolean boolean1) {
		this.h = boolean1;
	}

	public void a(ack ack) {
		this.d.a(null, this.e, ack, acm.BLOCKS, 1.0F, 1.0F);
	}
}
