import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;

public class bct extends bcb implements bbt, bcz {
	private static final tq<Boolean> d = tt.a(bct.class, ts.i);
	private int bv;
	protected static final ImmutableList<? extends axo<? extends axn<? super bct>>> b = ImmutableList.of(axo.c, axo.d);
	protected static final ImmutableList<? extends awp<?>> c = ImmutableList.of(awp.g, awp.h, awp.k, awp.l, awp.n, awp.m, awp.D, awp.t, awp.o, awp.p);

	public bct(aoq<? extends bct> aoq, bqb bqb) {
		super(aoq, bqb);
		this.f = 5;
	}

	@Override
	protected apr.b<bct> cJ() {
		return apr.a(c, b);
	}

	@Override
	protected apr<?> a(Dynamic<?> dynamic) {
		apr<bct> apr3 = this.cJ().a(dynamic);
		a(apr3);
		b(apr3);
		c(apr3);
		apr3.a(ImmutableSet.of(bfl.a));
		apr3.b(bfl.b);
		apr3.e();
		return apr3;
	}

	private static void a(apr<bct> apr) {
		apr.a(bfl.a, 0, ImmutableList.of(new arg(45, 90), new ark(200)));
	}

	private static void b(apr<bct> apr) {
		apr.a(
			bfl.b,
			10,
			ImmutableList.of(
				new asi<>(bct::eP),
				new arv(new arx(8.0F), adx.a(30, 60)),
				new aru(ImmutableList.of(Pair.of(new aro(0.4F), 2), Pair.of(new ase(0.4F, 3), 2), Pair.of(new aqo(30, 60), 1)))
			)
		);
	}

	private static void c(apr<bct> apr) {
		apr.a(bfl.k, 10, ImmutableList.of(new asc(1.0F), new art<>(bct::eL, new arh(40)), new art<>(bct::x_, new arh(15)), new ask()), awp.o);
	}

	private Optional<? extends aoy> eP() {
		return ((List)this.cI().c(awp.h).orElse(ImmutableList.of())).stream().filter(bct::j).findFirst();
	}

	private static boolean j(aoy aoy) {
		aoq<?> aoq2 = aoy.U();
		return aoq2 != aoq.aW && aoq2 != aoq.m && aop.f.test(aoy);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(d, false);
	}

	@Override
	public void a(tq<?> tq) {
		super.a(tq);
		if (d.equals(tq)) {
			this.y_();
		}
	}

	public static apw.a m() {
		return bcb.eS().a(apx.a, 40.0).a(apx.d, 0.3F).a(apx.c, 0.6F).a(apx.g, 1.0).a(apx.f, 6.0);
	}

	public boolean eL() {
		return !this.x_();
	}

	@Override
	public boolean B(aom aom) {
		if (!(aom instanceof aoy)) {
			return false;
		} else {
			this.bv = 10;
			this.l.a(this, (byte)4);
			this.a(acl.rw, 1.0F, this.dG());
			return bcz.a(this, (aoy)aom);
		}
	}

	@Override
	protected void f(aoy aoy) {
		if (!this.x_()) {
			bcz.b(this, aoy);
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		boolean boolean4 = super.a(anw, float2);
		if (this.l.v) {
			return false;
		} else if (boolean4 && anw.k() instanceof aoy) {
			aoy aoy5 = (aoy)anw.k();
			if (aop.f.test(aoy5) && !aqi.a(this, aoy5, 4.0)) {
				this.k(aoy5);
			}

			return boolean4;
		} else {
			return boolean4;
		}
	}

	private void k(aoy aoy) {
		this.bn.b(awp.D);
		this.bn.a(awp.o, aoy, 200L);
	}

	@Override
	public apr<bct> cI() {
		return (apr<bct>)super.cI();
	}

	protected void eM() {
		bfl bfl2 = (bfl)this.bn.f().orElse(null);
		this.bn.a(ImmutableList.of(bfl.k, bfl.b));
		bfl bfl3 = (bfl)this.bn.f().orElse(null);
		if (bfl3 == bfl.k && bfl2 != bfl.k) {
			this.eO();
		}

		this.s(this.bn.a(awp.o));
	}

	@Override
	protected void N() {
		this.l.X().a("zoglinBrain");
		this.cI().a((zd)this.l, this);
		this.l.X().c();
		this.eM();
	}

	@Override
	public void a(boolean boolean1) {
		this.Y().b(d, boolean1);
		if (!this.l.v && boolean1) {
			this.a(apx.f).a(0.5);
		}
	}

	@Override
	public boolean x_() {
		return this.Y().a(d);
	}

	@Override
	public void k() {
		if (this.bv > 0) {
			this.bv--;
		}

		super.k();
	}

	@Override
	protected ack I() {
		if (this.l.v) {
			return null;
		} else {
			return this.bn.a(awp.o) ? acl.rv : acl.ru;
		}
	}

	@Override
	protected ack e(anw anw) {
		return acl.ry;
	}

	@Override
	protected ack dp() {
		return acl.rx;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.rz, 0.15F, 1.0F);
	}

	protected void eO() {
		this.a(acl.rv, 1.0F, this.dG());
	}

	@Override
	protected void M() {
		super.M();
		qy.a(this);
	}

	@Override
	public apc dB() {
		return apc.b;
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (this.x_()) {
			le.a("IsBaby", true);
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.q("IsBaby")) {
			this.a(true);
		}
	}
}
