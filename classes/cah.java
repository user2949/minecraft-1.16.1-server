import java.util.Random;
import javax.annotation.Nullable;

public class cah extends bvr {
	public static final cga a = cai.a;

	public cah(cfi.c c) {
		super(c);
		this.j(this.n().a(a, Boolean.valueOf(false)));
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		return this.n().a(a, Boolean.valueOf(bin.o().r(bin.a())));
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (!bqb.v) {
			boolean boolean8 = (Boolean)cfj.c(a);
			if (boolean8 != bqb.r(fu3)) {
				if (boolean8) {
					bqb.G().a(fu3, this, 4);
				} else {
					bqb.a(fu3, cfj.a(a), 2);
				}
			}
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if ((Boolean)cfj.c(a) && !zd.r(fu)) {
			zd.a(fu, cfj.a(a), 2);
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cah.a);
	}
}
