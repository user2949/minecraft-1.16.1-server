import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

public class cai extends ccc {
	public static final cga a = cfz.r;
	private static final Map<bpg, List<cai.a>> b = new WeakHashMap();

	protected cai(cfi.c c) {
		super(c, hd.a);
		this.j(this.n.b().a(a, Boolean.valueOf(true)));
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		for (fz fz10 : fz.values()) {
			bqb.b(fu.a(fz10), this);
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!boolean5) {
			for (fz fz10 : fz.values()) {
				bqb.b(fu.a(fz10), this);
			}
		}
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.c(a) && fz.UP != fz ? 15 : 0;
	}

	protected boolean a(bqb bqb, fu fu, cfj cfj) {
		return bqb.a(fu.c(), fz.DOWN);
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		boolean boolean6 = this.a(zd, fu, cfj);
		List<cai.a> list7 = (List<cai.a>)b.get(zd);

		while (list7 != null && !list7.isEmpty() && zd.Q() - ((cai.a)list7.get(0)).b > 60L) {
			list7.remove(0);
		}

		if ((Boolean)cfj.c(a)) {
			if (boolean6) {
				zd.a(fu, cfj.a(a, Boolean.valueOf(false)), 3);
				if (a(zd, fu, true)) {
					zd.c(1502, fu, 0);
					zd.j().a(fu, zd.d_(fu).b(), 160);
				}
			}
		} else if (!boolean6 && !a(zd, fu, false)) {
			zd.a(fu, cfj.a(a, Boolean.valueOf(true)), 3);
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if ((Boolean)cfj.c(a) == this.a(bqb, fu3, cfj) && !bqb.G().b(fu3, this)) {
			bqb.G().a(fu3, this, 2);
		}
	}

	@Override
	public int b(cfj cfj, bpg bpg, fu fu, fz fz) {
		return fz == fz.DOWN ? cfj.b(bpg, fu, fz) : 0;
	}

	@Override
	public boolean b_(cfj cfj) {
		return true;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cai.a);
	}

	private static boolean a(bqb bqb, fu fu, boolean boolean3) {
		List<cai.a> list4 = (List<cai.a>)b.computeIfAbsent(bqb, bpg -> Lists.newArrayList());
		if (boolean3) {
			list4.add(new cai.a(fu.h(), bqb.Q()));
		}

		int integer5 = 0;

		for (int integer6 = 0; integer6 < list4.size(); integer6++) {
			cai.a a7 = (cai.a)list4.get(integer6);
			if (a7.a.equals(fu)) {
				if (++integer5 >= 8) {
					return true;
				}
			}
		}

		return false;
	}

	public static class a {
		private final fu a;
		private final long b;

		public a(fu fu, long long2) {
			this.a = fu;
			this.b = long2;
		}
	}
}
