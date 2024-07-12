import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ayx extends aze implements ayr {
	private static final tq<Integer> bB = tt.a(ayx.class, ts.b);
	private static final Predicate<aoz> bC = new Predicate<aoz>() {
		public boolean test(@Nullable aoz aoz) {
			return aoz != null && ayx.bF.containsKey(aoz.U());
		}
	};
	private static final bke bD = bkk.ne;
	private static final Set<bke> bE = Sets.<bke>newHashSet(bkk.kV, bkk.nk, bkk.nj, bkk.qf);
	private static final Map<aoq<?>, ack> bF = v.a(Maps.<aoq<?>, ack>newHashMap(), hashMap -> {
		hashMap.put(aoq.f, acl.ka);
		hashMap.put(aoq.i, acl.kv);
		hashMap.put(aoq.m, acl.kb);
		hashMap.put(aoq.q, acl.kc);
		hashMap.put(aoq.r, acl.kd);
		hashMap.put(aoq.t, acl.ke);
		hashMap.put(aoq.v, acl.kf);
		hashMap.put(aoq.w, acl.kg);
		hashMap.put(aoq.D, acl.kh);
		hashMap.put(aoq.F, acl.ki);
		hashMap.put(aoq.G, acl.kj);
		hashMap.put(aoq.I, acl.kk);
		hashMap.put(aoq.J, acl.kl);
		hashMap.put(aoq.S, acl.km);
		hashMap.put(aoq.ag, acl.kn);
		hashMap.put(aoq.ai, acl.ko);
		hashMap.put(aoq.aj, acl.kp);
		hashMap.put(aoq.ao, acl.kq);
		hashMap.put(aoq.ar, acl.kr);
		hashMap.put(aoq.at, acl.ks);
		hashMap.put(aoq.au, acl.kt);
		hashMap.put(aoq.aw, acl.ku);
		hashMap.put(aoq.aB, acl.kv);
		hashMap.put(aoq.aD, acl.kw);
		hashMap.put(aoq.aN, acl.kx);
		hashMap.put(aoq.aP, acl.ky);
		hashMap.put(aoq.aR, acl.kz);
		hashMap.put(aoq.aS, acl.kA);
		hashMap.put(aoq.aT, acl.kB);
		hashMap.put(aoq.aW, acl.kC);
		hashMap.put(aoq.aX, acl.kD);
		hashMap.put(aoq.aZ, acl.kE);
	});
	public float bx;
	public float by;
	public float bz;
	public float bA;
	private float bG = 1.0F;
	private boolean bH;
	private fu bI;

	public ayx(aoq<? extends ayx> aoq, bqb bqb) {
		super(aoq, bqb);
		this.bo = new atj(this, 10, false);
		this.a(czb.DANGER_FIRE, -1.0F);
		this.a(czb.DAMAGE_FIRE, -1.0F);
		this.a(czb.COCOA, -1.0F);
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.t(this.J.nextInt(5));
		if (apo == null) {
			apo = new aok.a();
			((aok.a)apo).a(false);
		}

		return super.a(bqc, ane, apb, apo, le);
	}

	@Override
	public boolean x_() {
		return false;
	}

	@Override
	protected void o() {
		this.br.a(0, new avb(this, 1.25));
		this.br.a(0, new aua(this));
		this.br.a(1, new auo(this, bec.class, 8.0F));
		this.br.a(2, new avn(this));
		this.br.a(2, new aue(this, 1.0, 5.0F, 1.0F, true));
		this.br.a(2, new avv(this, 1.0));
		this.br.a(3, new aul(this));
		this.br.a(3, new aud(this, 1.0, 3.0F, 7.0F));
	}

	public static apw.a eV() {
		return aoz.p().a(apx.a, 6.0).a(apx.e, 0.4F).a(apx.d, 0.2F);
	}

	@Override
	protected awv b(bqb bqb) {
		awt awt3 = new awt(this, bqb);
		awt3.a(false);
		awt3.d(true);
		awt3.b(true);
		return awt3;
	}

	@Override
	protected float b(apj apj, aon aon) {
		return aon.b * 0.6F;
	}

	@Override
	public void k() {
		if (this.bI == null || !this.bI.a(this.cz(), 3.46) || !this.l.d_(this.bI).a(bvs.cI)) {
			this.bH = false;
			this.bI = null;
		}

		if (this.l.t.nextInt(400) == 0) {
			a(this.l, this);
		}

		super.k();
		this.fa();
	}

	private void fa() {
		this.bA = this.bx;
		this.bz = this.by;
		this.by = (float)((double)this.by + (double)(!this.t && !this.bn() ? 4 : -1) * 0.3);
		this.by = aec.a(this.by, 0.0F, 1.0F);
		if (!this.t && this.bG < 1.0F) {
			this.bG = 1.0F;
		}

		this.bG = (float)((double)this.bG * 0.9);
		dem dem2 = this.cB();
		if (!this.t && dem2.c < 0.0) {
			this.e(dem2.d(1.0, 0.6, 1.0));
		}

		this.bx = this.bx + this.bG * 2.0F;
	}

	public static boolean a(bqb bqb, aom aom) {
		if (aom.aU() && !aom.av() && bqb.t.nextInt(2) == 0) {
			List<aoz> list3 = bqb.a(aoz.class, aom.cb().g(20.0), bC);
			if (!list3.isEmpty()) {
				aoz aoz4 = (aoz)list3.get(bqb.t.nextInt(list3.size()));
				if (!aoz4.av()) {
					ack ack5 = c(aoz4.U());
					bqb.a(null, aom.cC(), aom.cD(), aom.cG(), ack5, aom.ct(), 0.7F, a(bqb.t));
					return true;
				}
			}

			return false;
		} else {
			return false;
		}
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (!this.eL() && bE.contains(bki4.b())) {
			if (!bec.bJ.d) {
				bki4.g(1);
			}

			if (!this.av()) {
				this.l.a(null, this.cC(), this.cD(), this.cG(), acl.jX, this.ct(), 1.0F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F);
			}

			if (!this.l.v) {
				if (this.J.nextInt(10) == 0) {
					this.f(bec);
					this.l.a(this, (byte)7);
				} else {
					this.l.a(this, (byte)6);
				}
			}

			return ang.a(this.l.v);
		} else if (bki4.b() == bD) {
			if (!bec.bJ.d) {
				bki4.g(1);
			}

			this.c(new aog(aoi.s, 900));
			if (bec.b_() || !this.bI()) {
				this.a(anw.a(bec), Float.MAX_VALUE);
			}

			return ang.a(this.l.v);
		} else if (!this.fb() && this.eL() && this.j(bec)) {
			if (!this.l.v) {
				this.w(!this.eP());
			}

			return ang.a(this.l.v);
		} else {
			return super.b(bec, anf);
		}
	}

	@Override
	public boolean k(bki bki) {
		return false;
	}

	public static boolean c(aoq<ayx> aoq, bqc bqc, apb apb, fu fu, Random random) {
		cfj cfj6 = bqc.d_(fu.c());
		return (cfj6.a(acx.H) || cfj6.a(bvs.i) || cfj6.a(acx.r) || cfj6.a(bvs.a)) && bqc.b(fu, 0) > 8;
	}

	@Override
	public boolean b(float float1, float float2) {
		return false;
	}

	@Override
	protected void a(double double1, boolean boolean2, cfj cfj, fu fu) {
	}

	@Override
	public boolean a(ayk ayk) {
		return false;
	}

	@Nullable
	@Override
	public aok a(aok aok) {
		return null;
	}

	@Override
	public boolean B(aom aom) {
		return aom.a(anw.c(this), 3.0F);
	}

	@Nullable
	@Override
	public ack I() {
		return a(this.l, this.l.t);
	}

	public static ack a(bqb bqb, Random random) {
		if (bqb.ac() != and.PEACEFUL && random.nextInt(1000) == 0) {
			List<aoq<?>> list3 = Lists.<aoq<?>>newArrayList(bF.keySet());
			return c((aoq<?>)list3.get(random.nextInt(list3.size())));
		} else {
			return acl.jV;
		}
	}

	private static ack c(aoq<?> aoq) {
		return (ack)bF.getOrDefault(aoq, acl.jV);
	}

	@Override
	protected ack e(anw anw) {
		return acl.jZ;
	}

	@Override
	protected ack dp() {
		return acl.jW;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.kF, 0.15F, 1.0F);
	}

	@Override
	protected float e(float float1) {
		this.a(acl.jY, 0.15F, 1.0F);
		return float1 + this.by / 2.0F;
	}

	@Override
	protected boolean au() {
		return true;
	}

	@Override
	protected float dG() {
		return a(this.J);
	}

	public static float a(Random random) {
		return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
	}

	@Override
	public acm ct() {
		return acm.NEUTRAL;
	}

	@Override
	public boolean aR() {
		return true;
	}

	@Override
	protected void C(aom aom) {
		if (!(aom instanceof bec)) {
			super.C(aom);
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else {
			this.w(false);
			return super.a(anw, float2);
		}
	}

	public int eX() {
		return aec.a(this.S.a(bB), 0, 4);
	}

	public void t(int integer) {
		this.S.b(bB, integer);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bB, 0);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("Variant", this.eX());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.t(le.h("Variant"));
	}

	public boolean fb() {
		return !this.t;
	}
}
