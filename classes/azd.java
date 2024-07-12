import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class azd extends ayk implements apn {
	private static final tq<Byte> bv = tt.a(azd.class, ts.a);
	private static final Map<bje, bqa> bw = v.a(Maps.newEnumMap(bje.class), enumMap -> {
		enumMap.put(bje.WHITE, bvs.aY);
		enumMap.put(bje.ORANGE, bvs.aZ);
		enumMap.put(bje.MAGENTA, bvs.ba);
		enumMap.put(bje.LIGHT_BLUE, bvs.bb);
		enumMap.put(bje.YELLOW, bvs.bc);
		enumMap.put(bje.LIME, bvs.bd);
		enumMap.put(bje.PINK, bvs.be);
		enumMap.put(bje.GRAY, bvs.bf);
		enumMap.put(bje.LIGHT_GRAY, bvs.bg);
		enumMap.put(bje.CYAN, bvs.bh);
		enumMap.put(bje.PURPLE, bvs.bi);
		enumMap.put(bje.BLUE, bvs.bj);
		enumMap.put(bje.BROWN, bvs.bk);
		enumMap.put(bje.GREEN, bvs.bl);
		enumMap.put(bje.RED, bvs.bm);
		enumMap.put(bje.BLACK, bvs.bn);
	});
	private static final Map<bje, float[]> bx = Maps.newEnumMap((Map)Arrays.stream(bje.values()).collect(Collectors.toMap(bje -> bje, azd::c)));
	private int by;
	private aty bz;

	private static float[] c(bje bje) {
		if (bje == bje.WHITE) {
			return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
		} else {
			float[] arr2 = bje.e();
			float float3 = 0.75F;
			return new float[]{arr2[0] * 0.75F, arr2[1] * 0.75F, arr2[2] * 0.75F};
		}
	}

	public azd(aoq<? extends azd> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		this.bz = new aty(this);
		this.br.a(0, new aua(this));
		this.br.a(1, new avb(this, 1.25));
		this.br.a(2, new att(this, 1.0));
		this.br.a(3, new avr(this, 1.1, bmr.a(bkk.kW), false));
		this.br.a(4, new auf(this, 1.1));
		this.br.a(5, this.bz);
		this.br.a(6, new avw(this, 1.0));
		this.br.a(7, new auo(this, bec.class, 6.0F));
		this.br.a(8, new ave(this));
	}

	@Override
	protected void N() {
		this.by = this.bz.g();
		super.N();
	}

	@Override
	public void k() {
		if (this.l.v) {
			this.by = Math.max(0, this.by - 1);
		}

		super.k();
	}

	public static apw.a eL() {
		return aoz.p().a(apx.a, 8.0).a(apx.d, 0.23F);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bv, (byte)0);
	}

	@Override
	public uh J() {
		if (this.eN()) {
			return this.U().i();
		} else {
			switch (this.eM()) {
				case WHITE:
				default:
					return dao.Q;
				case ORANGE:
					return dao.R;
				case MAGENTA:
					return dao.S;
				case LIGHT_BLUE:
					return dao.T;
				case YELLOW:
					return dao.U;
				case LIME:
					return dao.V;
				case PINK:
					return dao.W;
				case GRAY:
					return dao.X;
				case LIGHT_GRAY:
					return dao.Y;
				case CYAN:
					return dao.Z;
				case PURPLE:
					return dao.aa;
				case BLUE:
					return dao.ab;
				case BROWN:
					return dao.ac;
				case GREEN:
					return dao.ad;
				case RED:
					return dao.ae;
				case BLACK:
					return dao.af;
			}
		}
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (bki4.b() == bkk.ng) {
			if (!this.l.v && this.L_()) {
				this.a(acm.PLAYERS);
				bki4.a(1, bec, becx -> becx.d(anf));
				return ang.SUCCESS;
			} else {
				return ang.CONSUME;
			}
		} else {
			return super.b(bec, anf);
		}
	}

	@Override
	public void a(acm acm) {
		this.l.a(null, this, acl.mL, acm, 1.0F, 1.0F);
		this.t(true);
		int integer3 = 1 + this.J.nextInt(3);

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			bbg bbg5 = this.a((bqa)bw.get(this.eM()), 1);
			if (bbg5 != null) {
				bbg5.e(
					bbg5.cB()
						.b(
							(double)((this.J.nextFloat() - this.J.nextFloat()) * 0.1F),
							(double)(this.J.nextFloat() * 0.05F),
							(double)((this.J.nextFloat() - this.J.nextFloat()) * 0.1F)
						)
				);
			}
		}
	}

	@Override
	public boolean L_() {
		return this.aU() && !this.eN() && !this.x_();
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("Sheared", this.eN());
		le.a("Color", (byte)this.eM().b());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.t(le.q("Sheared"));
		this.b(bje.a(le.f("Color")));
	}

	@Override
	protected ack I() {
		return acl.mI;
	}

	@Override
	protected ack e(anw anw) {
		return acl.mK;
	}

	@Override
	protected ack dp() {
		return acl.mJ;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.mM, 0.15F, 1.0F);
	}

	public bje eM() {
		return bje.a(this.S.a(bv) & 15);
	}

	public void b(bje bje) {
		byte byte3 = this.S.a(bv);
		this.S.b(bv, (byte)(byte3 & 240 | bje.b() & 15));
	}

	public boolean eN() {
		return (this.S.a(bv) & 16) != 0;
	}

	public void t(boolean boolean1) {
		byte byte3 = this.S.a(bv);
		if (boolean1) {
			this.S.b(bv, (byte)(byte3 | 16));
		} else {
			this.S.b(bv, (byte)(byte3 & -17));
		}
	}

	public static bje a(Random random) {
		int integer2 = random.nextInt(100);
		if (integer2 < 5) {
			return bje.BLACK;
		} else if (integer2 < 10) {
			return bje.GRAY;
		} else if (integer2 < 15) {
			return bje.LIGHT_GRAY;
		} else if (integer2 < 18) {
			return bje.BROWN;
		} else {
			return random.nextInt(500) == 0 ? bje.PINK : bje.WHITE;
		}
	}

	public azd a(aok aok) {
		azd azd3 = (azd)aok;
		azd azd4 = aoq.aq.a(this.l);
		azd4.b(this.a(this, azd3));
		return azd4;
	}

	@Override
	public void B() {
		this.t(false);
		if (this.x_()) {
			this.a(60);
		}
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		this.b(a(bqc.v_()));
		return super.a(bqc, ane, apb, apo, le);
	}

	private bje a(ayk ayk1, ayk ayk2) {
		bje bje4 = ((azd)ayk1).eM();
		bje bje5 = ((azd)ayk2).eM();
		bgu bgu6 = a(bje4, bje5);
		return (bje)this.l
			.o()
			.a(bmx.a, bgu6, this.l)
			.map(bmm -> bmm.a(bgu6))
			.map(bki::b)
			.filter(bjf.class::isInstance)
			.map(bjf.class::cast)
			.map(bjf::d)
			.orElseGet(() -> this.l.t.nextBoolean() ? bje4 : bje5);
	}

	private static bgu a(bje bje1, bje bje2) {
		bgu bgu3 = new bgu(new bgi(null, -1) {
			@Override
			public boolean a(bec bec) {
				return false;
			}
		}, 2, 1);
		bgu3.a(0, new bki(bjf.a(bje1)));
		bgu3.a(1, new bki(bjf.a(bje2)));
		return bgu3;
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 0.95F * aon.b;
	}
}
