import com.google.common.collect.Maps;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bcq extends bbj {
	private static final Predicate<and> b = and -> and == and.NORMAL || and == and.HARD;
	private boolean bv;

	public bcq(aoq<? extends bcq> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(0, new aua(this));
		this.br.a(1, new bcq.a(this));
		this.br.a(2, new bbj.b(this, this));
		this.br.a(3, new bfi.a(this, this, 10.0F));
		this.br.a(4, new bcq.c(this));
		this.bs.a(1, new awb(this, bfi.class).a());
		this.bs.a(2, new awc(this, bec.class, true));
		this.bs.a(3, new awc(this, bdk.class, true));
		this.bs.a(3, new awc(this, ayt.class, true));
		this.bs.a(4, new bcq.b(this));
		this.br.a(8, new avf(this, 0.6));
		this.br.a(9, new auo(this, bec.class, 3.0F, 1.0F));
		this.br.a(10, new auo(this, aoz.class, 8.0F));
	}

	@Override
	protected void N() {
		if (!this.eE()) {
			awv awv2 = this.x();
			if (awv2 instanceof awu) {
				boolean boolean3 = ((zd)this.l).e(this.cA());
				((awu)awv2).a(boolean3);
			}
		}

		super.N();
	}

	public static apw.a eL() {
		return bcb.eS().a(apx.d, 0.35F).a(apx.b, 12.0).a(apx.a, 24.0).a(apx.f, 5.0);
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (this.bv) {
			le.a("Johnny", true);
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("Johnny", 99)) {
			this.bv = le.q("Johnny");
		}
	}

	@Override
	public ack eM() {
		return acl.qg;
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		apo apo7 = super.a(bqc, ane, apb, apo, le);
		((awu)this.x()).a(true);
		this.a(ane);
		this.b(ane);
		return apo7;
	}

	@Override
	protected void a(ane ane) {
		if (this.fb() == null) {
			this.a(aor.MAINHAND, new bki(bkk.kc));
		}
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
	public void a(@Nullable mr mr) {
		super.a(mr);
		if (!this.bv && mr != null && mr.getString().equals("Johnny")) {
			this.bv = true;
		}
	}

	@Override
	protected ack I() {
		return acl.qf;
	}

	@Override
	protected ack dp() {
		return acl.qh;
	}

	@Override
	protected ack e(anw anw) {
		return acl.qi;
	}

	@Override
	public void a(int integer, boolean boolean2) {
		bki bki4 = new bki(bkk.kc);
		bfh bfh5 = this.fb();
		int integer6 = 1;
		if (integer > bfh5.a(and.NORMAL)) {
			integer6 = 2;
		}

		boolean boolean7 = this.J.nextFloat() <= bfh5.w();
		if (boolean7) {
			Map<bnw, Integer> map8 = Maps.<bnw, Integer>newHashMap();
			map8.put(boa.m, integer6);
			bny.a(map8, bki4);
		}

		this.a(aor.MAINHAND, bki4);
	}

	static class a extends atr {
		public a(aoz aoz) {
			super(aoz, 6, bcq.b);
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean b() {
			bcq bcq2 = (bcq)this.d;
			return bcq2.fc() && super.b();
		}

		@Override
		public boolean a() {
			bcq bcq2 = (bcq)this.d;
			return bcq2.fc() && bcq2.J.nextInt(10) == 0 && super.a();
		}

		@Override
		public void c() {
			super.c();
			this.d.n(0);
		}
	}

	static class b extends awc<aoy> {
		public b(bcq bcq) {
			super(bcq, aoy.class, 0, true, true, aoy::eh);
		}

		@Override
		public boolean a() {
			return ((bcq)this.e).bv && super.a();
		}

		@Override
		public void c() {
			super.c();
			this.e.n(0);
		}
	}

	class c extends auq {
		public c(bcq bcq2) {
			super(bcq2, 1.0, false);
		}

		@Override
		protected double a(aoy aoy) {
			if (this.a.cs() instanceof bcg) {
				float float3 = this.a.cs().cx() - 0.1F;
				return (double)(float3 * 2.0F * float3 * 2.0F + aoy.cx());
			} else {
				return super.a(aoy);
			}
		}
	}
}
