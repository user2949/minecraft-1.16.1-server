import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class bce extends bbj implements bbo {
	private static final tq<Boolean> b = tt.a(bce.class, ts.i);
	private final anm bv = new anm(5);

	public bce(aoq<? extends bce> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(0, new aua(this));
		this.br.a(2, new bfi.a(this, this, 10.0F));
		this.br.a(3, new avj<>(this, 1.0, 8.0F));
		this.br.a(8, new avf(this, 0.6));
		this.br.a(9, new auo(this, bec.class, 15.0F, 1.0F));
		this.br.a(10, new auo(this, aoz.class, 15.0F));
		this.bs.a(1, new awb(this, bfi.class).a());
		this.bs.a(2, new awc(this, bec.class, true));
		this.bs.a(3, new awc(this, bdk.class, false));
		this.bs.a(3, new awc(this, ayt.class, true));
	}

	public static apw.a eL() {
		return bcb.eS().a(apx.d, 0.35F).a(apx.a, 24.0).a(apx.f, 5.0).a(apx.b, 32.0);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, false);
	}

	@Override
	public boolean a(bkv bkv) {
		return bkv == bkk.qP;
	}

	@Override
	public void b(boolean boolean1) {
		this.S.b(b, boolean1);
	}

	@Override
	public void V_() {
		this.aP = 0;
	}

	@Override
	public void b(le le) {
		super.b(le);
		lk lk3 = new lk();

		for (int integer4 = 0; integer4 < this.bv.ab_(); integer4++) {
			bki bki5 = this.bv.a(integer4);
			if (!bki5.a()) {
				lk3.add(bki5.b(new le()));
			}
		}

		le.a("Inventory", lk3);
	}

	@Override
	public void a(le le) {
		super.a(le);
		lk lk3 = le.d("Inventory", 10);

		for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
			bki bki5 = bki.a(lk3.a(integer4));
			if (!bki5.a()) {
				this.bv.a(bki5);
			}
		}

		this.p(true);
	}

	@Override
	public float a(fu fu, bqd bqd) {
		cfj cfj4 = bqd.d_(fu.c());
		return !cfj4.a(bvs.i) && !cfj4.a(bvs.C) ? 0.5F - bqd.y(fu) : 10.0F;
	}

	@Override
	public int er() {
		return 1;
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.a(ane);
		this.b(ane);
		return super.a(bqc, ane, apb, apo, le);
	}

	@Override
	protected void a(ane ane) {
		bki bki3 = new bki(bkk.qP);
		if (this.J.nextInt(300) == 0) {
			Map<bnw, Integer> map4 = Maps.<bnw, Integer>newHashMap();
			map4.put(boa.J, 1);
			bny.a(map4, bki3);
		}

		this.a(aor.MAINHAND, bki3);
	}

	@Override
	public boolean r(aom aom) {
		if (super.r(aom)) {
			return true;
		} else {
			return aom instanceof aoy && ((aoy)aom).dB() == apc.d ? this.bC() == null && aom.bC() == null : false;
		}
	}

	@Override
	protected ack I() {
		return acl.lb;
	}

	@Override
	protected ack dp() {
		return acl.ld;
	}

	@Override
	protected ack e(anw anw) {
		return acl.le;
	}

	@Override
	public void a(aoy aoy, float float2) {
		this.b(this, 1.6F);
	}

	@Override
	public void a(aoy aoy, bki bki, bes bes, float float4) {
		this.a(this, aoy, bes, float4, 1.6F);
	}

	@Override
	protected void b(bbg bbg) {
		bki bki3 = bbg.g();
		if (bki3.b() instanceof bij) {
			super.b(bbg);
		} else {
			bke bke4 = bki3.b();
			if (this.b(bke4)) {
				this.a(bbg);
				bki bki5 = this.bv.a(bki3);
				if (bki5.a()) {
					bbg.aa();
				} else {
					bki3.e(bki5.E());
				}
			}
		}
	}

	private boolean b(bke bke) {
		return this.fc() && bke == bkk.pL;
	}

	@Override
	public boolean a_(int integer, bki bki) {
		if (super.a_(integer, bki)) {
			return true;
		} else {
			int integer4 = integer - 300;
			if (integer4 >= 0 && integer4 < this.bv.ab_()) {
				this.bv.a(integer4, bki);
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public void a(int integer, boolean boolean2) {
		bfh bfh4 = this.fb();
		boolean boolean5 = this.J.nextFloat() <= bfh4.w();
		if (boolean5) {
			bki bki6 = new bki(bkk.qP);
			Map<bnw, Integer> map7 = Maps.<bnw, Integer>newHashMap();
			if (integer > bfh4.a(and.NORMAL)) {
				map7.put(boa.I, 2);
			} else if (integer > bfh4.a(and.EASY)) {
				map7.put(boa.I, 1);
			}

			map7.put(boa.H, 1);
			bny.a(map7, bki6);
			this.a(aor.MAINHAND, bki6);
		}
	}

	@Override
	public ack eM() {
		return acl.lc;
	}
}
