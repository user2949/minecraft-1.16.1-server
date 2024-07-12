import java.util.List;
import java.util.function.Predicate;

public class bbq extends bbx {
	public static final float b = aoq.r.j() / aoq.F.j();

	public bbq(aoq<? extends bbq> aoq, bqb bqb) {
		super(aoq, bqb);
		this.et();
		if (this.c != null) {
			this.c.a(400);
		}
	}

	public static apw.a m() {
		return bbx.eN().a(apx.d, 0.3F).a(apx.f, 8.0).a(apx.a, 80.0);
	}

	@Override
	public int eL() {
		return 60;
	}

	@Override
	protected ack I() {
		return this.aD() ? acl.dd : acl.de;
	}

	@Override
	protected ack e(anw anw) {
		return this.aD() ? acl.dj : acl.dk;
	}

	@Override
	protected ack dp() {
		return this.aD() ? acl.dg : acl.dh;
	}

	@Override
	protected ack eM() {
		return acl.di;
	}

	@Override
	protected void N() {
		super.N();
		int integer2 = 1200;
		if ((this.K + this.V()) % 1200 == 0) {
			aoe aoe3 = aoi.d;
			List<ze> list4 = ((zd)this.l).a((Predicate<? super ze>)(ze -> this.h(ze) < 2500.0 && ze.d.d()));
			int integer5 = 2;
			int integer6 = 6000;
			int integer7 = 1200;

			for (ze ze9 : list4) {
				if (!ze9.a(aoe3) || ze9.b(aoe3).c() < 2 || ze9.b(aoe3).b() < 1200) {
					ze9.b.a(new oq(oq.k, this.av() ? 0.0F : 1.0F));
					ze9.c(new aog(aoe3, 6000, 2));
				}
			}
		}

		if (!this.eA()) {
			this.a(this.cA(), 16);
		}
	}
}
