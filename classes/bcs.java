import javax.annotation.Nullable;

public class bcs extends bbk {
	public bcs(aoq<? extends bcs> aoq, bqb bqb) {
		super(aoq, bqb);
		this.a(czb.LAVA, 8.0F);
	}

	@Override
	protected void o() {
		this.bs.a(3, new awc(this, bdc.class, true));
		super.o();
	}

	@Override
	protected ack I() {
		return acl.qO;
	}

	@Override
	protected ack e(anw anw) {
		return acl.qQ;
	}

	@Override
	protected ack dp() {
		return acl.qP;
	}

	@Override
	ack eL() {
		return acl.qR;
	}

	@Override
	protected void a(anw anw, int integer, boolean boolean3) {
		super.a(anw, integer, boolean3);
		aom aom5 = anw.k();
		if (aom5 instanceof bbn) {
			bbn bbn6 = (bbn)aom5;
			if (bbn6.eO()) {
				bbn6.eP();
				this.a(bkk.pe);
			}
		}
	}

	@Override
	protected void a(ane ane) {
		this.a(aor.MAINHAND, new bki(bkk.kt));
	}

	@Override
	protected void b(ane ane) {
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		apo apo7 = super.a(bqc, ane, apb, apo, le);
		this.a(apx.f).a(4.0);
		this.eM();
		return apo7;
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 2.1F;
	}

	@Override
	public boolean B(aom aom) {
		if (!super.B(aom)) {
			return false;
		} else {
			if (aom instanceof aoy) {
				((aoy)aom).c(new aog(aoi.t, 200));
			}

			return true;
		}
	}

	@Override
	protected beg b(bki bki, float float2) {
		beg beg4 = super.b(bki, float2);
		beg4.f(100);
		return beg4;
	}

	@Override
	public boolean d(aog aog) {
		return aog.a() == aoi.t ? false : super.d(aog);
	}
}
