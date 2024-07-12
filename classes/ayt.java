import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class ayt extends ayi implements ape {
	protected static final tq<Byte> b = tt.a(ayt.class, ts.a);
	private int c;
	private int d;
	private static final adx bv = aej.a(20, 39);
	private int bw;
	private UUID bx;

	public ayt(aoq<? extends ayt> aoq, bqb bqb) {
		super(aoq, bqb);
		this.G = 1.0F;
	}

	@Override
	protected void o() {
		this.br.a(1, new auq(this, 1.0, true));
		this.br.a(2, new auw(this, 0.9, 32.0F));
		this.br.a(2, new aur(this, 0.6, false));
		this.br.a(4, new aui(this, 0.6));
		this.br.a(5, new auz(this));
		this.br.a(7, new auo(this, bec.class, 6.0F));
		this.br.a(8, new ave(this));
		this.bs.a(1, new awa(this));
		this.bs.a(2, new awb(this));
		this.bs.a(3, new awc(this, bec.class, 10, true, false, this::b));
		this.bs.a(3, new awc(this, aoz.class, 5, false, false, aoy -> aoy instanceof bbt && !(aoy instanceof bbn)));
		this.bs.a(4, new awi<>(this, false));
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(b, (byte)0);
	}

	public static apw.a m() {
		return aoz.p().a(apx.a, 100.0).a(apx.d, 0.25).a(apx.c, 1.0).a(apx.f, 15.0);
	}

	@Override
	protected int l(int integer) {
		return integer;
	}

	@Override
	protected void C(aom aom) {
		if (aom instanceof bbt && !(aom instanceof bbn) && this.cX().nextInt(20) == 0) {
			this.i((aoy)aom);
		}

		super.C(aom);
	}

	@Override
	public void k() {
		super.k();
		if (this.c > 0) {
			this.c--;
		}

		if (this.d > 0) {
			this.d--;
		}

		if (b(this.cB()) > 2.5000003E-7F && this.J.nextInt(5) == 0) {
			int integer2 = aec.c(this.cC());
			int integer3 = aec.c(this.cD() - 0.2F);
			int integer4 = aec.c(this.cG());
			cfj cfj5 = this.l.d_(new fu(integer2, integer3, integer4));
			if (!cfj5.g()) {
				this.l
					.a(
						new hc(hh.d, cfj5),
						this.cC() + ((double)this.J.nextFloat() - 0.5) * (double)this.cx(),
						this.cD() + 0.1,
						this.cG() + ((double)this.J.nextFloat() - 0.5) * (double)this.cx(),
						4.0 * ((double)this.J.nextFloat() - 0.5),
						0.5,
						((double)this.J.nextFloat() - 0.5) * 4.0
					);
			}
		}

		if (!this.l.v) {
			this.a((zd)this.l, true);
		}
	}

	@Override
	public boolean a(aoq<?> aoq) {
		if (this.eO() && aoq == aoq.bb) {
			return false;
		} else {
			return aoq == aoq.m ? false : super.a(aoq);
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("PlayerCreated", this.eO());
		this.c(le);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.u(le.q("PlayerCreated"));
		this.a((zd)this.l, le);
	}

	@Override
	public void H_() {
		this.a_(bv.a(this.J));
	}

	@Override
	public void a_(int integer) {
		this.bw = integer;
	}

	@Override
	public int F_() {
		return this.bw;
	}

	@Override
	public void a(@Nullable UUID uUID) {
		this.bx = uUID;
	}

	@Override
	public UUID G_() {
		return this.bx;
	}

	private float eP() {
		return (float)this.b(apx.f);
	}

	@Override
	public boolean B(aom aom) {
		this.c = 10;
		this.l.a(this, (byte)4);
		float float3 = this.eP();
		float float4 = (int)float3 > 0 ? float3 / 2.0F + (float)this.J.nextInt((int)float3) : float3;
		boolean boolean5 = aom.a(anw.c(this), float4);
		if (boolean5) {
			aom.e(aom.cB().b(0.0, 0.4F, 0.0));
			this.a(this, aom);
		}

		this.a(acl.gx, 1.0F, 1.0F);
		return boolean5;
	}

	@Override
	public boolean a(anw anw, float float2) {
		ayt.a a4 = this.eL();
		boolean boolean5 = super.a(anw, float2);
		if (boolean5 && this.eL() != a4) {
			this.a(acl.gy, 1.0F, 1.0F);
		}

		return boolean5;
	}

	public ayt.a eL() {
		return ayt.a.a(this.dj() / this.dw());
	}

	public void t(boolean boolean1) {
		if (boolean1) {
			this.d = 400;
			this.l.a(this, (byte)11);
		} else {
			this.d = 0;
			this.l.a(this, (byte)34);
		}
	}

	@Override
	protected ack e(anw anw) {
		return acl.gA;
	}

	@Override
	protected ack dp() {
		return acl.gz;
	}

	@Override
	protected ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		bke bke5 = bki4.b();
		if (bke5 != bkk.kk) {
			return ang.PASS;
		} else {
			float float6 = this.dj();
			this.b(25.0F);
			if (this.dj() == float6) {
				return ang.PASS;
			} else {
				float float7 = 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F;
				this.a(acl.gB, 1.0F, float7);
				if (!bec.bJ.d) {
					bki4.g(1);
				}

				return ang.a(this.l.v);
			}
		}
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.gC, 1.0F, 1.0F);
	}

	public boolean eO() {
		return (this.S.a(b) & 1) != 0;
	}

	public void u(boolean boolean1) {
		byte byte3 = this.S.a(b);
		if (boolean1) {
			this.S.b(b, (byte)(byte3 | 1));
		} else {
			this.S.b(b, (byte)(byte3 & -2));
		}
	}

	@Override
	public void a(anw anw) {
		super.a(anw);
	}

	@Override
	public boolean a(bqd bqd) {
		fu fu3 = this.cA();
		fu fu4 = fu3.c();
		cfj cfj5 = bqd.d_(fu4);
		if (!cfj5.a(bqd, fu4, this)) {
			return false;
		} else {
			for (int integer6 = 1; integer6 < 3; integer6++) {
				fu fu7 = fu3.b(integer6);
				cfj cfj8 = bqd.d_(fu7);
				if (!bqj.a(bqd, fu7, cfj8, cfj8.m(), aoq.K)) {
					return false;
				}
			}

			return bqj.a(bqd, fu3, bqd.d_(fu3), cxb.a.h(), aoq.K) && bqd.i(this);
		}
	}

	public static enum a {
		NONE(1.0F),
		LOW(0.75F),
		MEDIUM(0.5F),
		HIGH(0.25F);

		private static final List<ayt.a> e = (List<ayt.a>)Stream.of(values())
			.sorted(Comparator.comparingDouble(a -> (double)a.f))
			.collect(ImmutableList.toImmutableList());
		private final float f;

		private a(float float3) {
			this.f = float3;
		}

		public static ayt.a a(float float1) {
			for (ayt.a a3 : e) {
				if (float1 < a3.f) {
					return a3;
				}
			}

			return NONE;
		}
	}
}
