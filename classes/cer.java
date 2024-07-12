import java.util.Random;
import javax.annotation.Nullable;

public abstract class cer extends ces {
	@Override
	public boolean a(zd zd, cha cha, fu fu, cfj cfj, Random random) {
		for (int integer7 = 0; integer7 >= -1; integer7--) {
			for (int integer8 = 0; integer8 >= -1; integer8--) {
				if (a(cfj, zd, fu, integer7, integer8)) {
					return this.a(zd, cha, fu, cfj, random, integer7, integer8);
				}
			}
		}

		return super.a(zd, cha, fu, cfj, random);
	}

	@Nullable
	protected abstract ckb<cou, ?> a(Random random);

	public boolean a(zd zd, cha cha, fu fu, cfj cfj, Random random, int integer6, int integer7) {
		ckb<cou, ?> ckb9 = this.a(random);
		if (ckb9 == null) {
			return false;
		} else {
			ckb9.e.a();
			cfj cfj10 = bvs.a.n();
			zd.a(fu.b(integer6, 0, integer7), cfj10, 4);
			zd.a(fu.b(integer6 + 1, 0, integer7), cfj10, 4);
			zd.a(fu.b(integer6, 0, integer7 + 1), cfj10, 4);
			zd.a(fu.b(integer6 + 1, 0, integer7 + 1), cfj10, 4);
			if (ckb9.a(zd, zd.a(), cha, random, fu.b(integer6, 0, integer7))) {
				return true;
			} else {
				zd.a(fu.b(integer6, 0, integer7), cfj, 4);
				zd.a(fu.b(integer6 + 1, 0, integer7), cfj, 4);
				zd.a(fu.b(integer6, 0, integer7 + 1), cfj, 4);
				zd.a(fu.b(integer6 + 1, 0, integer7 + 1), cfj, 4);
				return false;
			}
		}
	}

	public static boolean a(cfj cfj, bpg bpg, fu fu, int integer4, int integer5) {
		bvr bvr6 = cfj.b();
		return bvr6 == bpg.d_(fu.b(integer4, 0, integer5)).b()
			&& bvr6 == bpg.d_(fu.b(integer4 + 1, 0, integer5)).b()
			&& bvr6 == bpg.d_(fu.b(integer4, 0, integer5 + 1)).b()
			&& bvr6 == bpg.d_(fu.b(integer4 + 1, 0, integer5 + 1)).b();
	}
}
