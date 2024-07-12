import java.util.Random;
import javax.annotation.Nullable;

public abstract class ces {
	@Nullable
	protected abstract ckb<cou, ?> a(Random random, boolean boolean2);

	public boolean a(zd zd, cha cha, fu fu, cfj cfj, Random random) {
		ckb<cou, ?> ckb7 = this.a(random, this.a(zd, fu));
		if (ckb7 == null) {
			return false;
		} else {
			zd.a(fu, bvs.a.n(), 4);
			ckb7.e.a();
			if (ckb7.a(zd, zd.a(), cha, random, fu)) {
				return true;
			} else {
				zd.a(fu, cfj, 4);
				return false;
			}
		}
	}

	private boolean a(bqc bqc, fu fu) {
		for (fu fu5 : fu.a.a(fu.c().d(2).f(2), fu.b().e(2).g(2))) {
			if (bqc.d_(fu5).a(acx.N)) {
				return true;
			}
		}

		return false;
	}
}
