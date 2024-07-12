import java.util.Random;
import java.util.function.Predicate;

public abstract class bcb extends apg implements bbt {
	protected bcb(aoq<? extends bcb> aoq, bqb bqb) {
		super(aoq, bqb);
		this.f = 5;
	}

	@Override
	public acm ct() {
		return acm.HOSTILE;
	}

	@Override
	public void k() {
		this.dz();
		this.eR();
		super.k();
	}

	protected void eR() {
		float float2 = this.aO();
		if (float2 > 0.5F) {
			this.aP += 2;
		}
	}

	@Override
	protected boolean L() {
		return true;
	}

	@Override
	protected ack aq() {
		return acl.gi;
	}

	@Override
	protected ack ar() {
		return acl.gh;
	}

	@Override
	public boolean a(anw anw, float float2) {
		return this.b(anw) ? false : super.a(anw, float2);
	}

	@Override
	protected ack e(anw anw) {
		return acl.gf;
	}

	@Override
	protected ack dp() {
		return acl.ge;
	}

	@Override
	protected ack o(int integer) {
		return integer > 4 ? acl.gd : acl.gg;
	}

	@Override
	public float a(fu fu, bqd bqd) {
		return 0.5F - bqd.y(fu);
	}

	public static boolean a(bqc bqc, fu fu, Random random) {
		if (bqc.a(bqi.SKY, fu) > random.nextInt(32)) {
			return false;
		} else {
			int integer4 = bqc.n().T() ? bqc.c(fu, 10) : bqc.B(fu);
			return integer4 <= random.nextInt(8);
		}
	}

	public static boolean c(aoq<? extends bcb> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return bqc.ac() != and.PEACEFUL && a(bqc, fu, random) && a(aoq, bqc, apb, fu, random);
	}

	public static boolean d(aoq<? extends bcb> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return bqc.ac() != and.PEACEFUL && a(aoq, bqc, apb, fu, random);
	}

	public static apw.a eS() {
		return aoz.p().a(apx.f);
	}

	@Override
	protected boolean cU() {
		return true;
	}

	@Override
	protected boolean cV() {
		return true;
	}

	public boolean f(bec bec) {
		return true;
	}

	@Override
	public bki f(bki bki) {
		if (bki.b() instanceof bkv) {
			Predicate<bki> predicate3 = ((bkv)bki.b()).e();
			bki bki4 = bkv.a(this, predicate3);
			return bki4.a() ? new bki(bkk.kg) : bki4;
		} else {
			return bki.b;
		}
	}
}
