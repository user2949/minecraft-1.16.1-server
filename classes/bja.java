import java.util.Collection;
import javax.annotation.Nullable;

public class bja extends bke {
	public bja(bke.a a) {
		super(a);
	}

	@Override
	public boolean e(bki bki) {
		return true;
	}

	@Override
	public boolean a(cfj cfj, bqb bqb, fu fu, bec bec) {
		if (!bqb.v) {
			this.a(bec, cfj, bqb, fu, false, bec.b(anf.MAIN_HAND));
		}

		return false;
	}

	@Override
	public ang a(blv blv) {
		bec bec3 = blv.m();
		bqb bqb4 = blv.o();
		if (!bqb4.v && bec3 != null) {
			fu fu5 = blv.a();
			this.a(bec3, bqb4.d_(fu5), bqb4, fu5, true, blv.l());
		}

		return ang.a(bqb4.v);
	}

	private void a(bec bec, cfj cfj, bqc bqc, fu fu, boolean boolean5, bki bki) {
		if (bec.eV()) {
			bvr bvr8 = cfj.b();
			cfk<bvr, cfj> cfk9 = bvr8.m();
			Collection<cgl<?>> collection10 = cfk9.d();
			String string11 = gl.aj.b(bvr8).toString();
			if (collection10.isEmpty()) {
				a(bec, new ne(this.a() + ".empty", string11));
			} else {
				le le12 = bki.a("DebugProperty");
				String string13 = le12.l(string11);
				cgl<?> cgl14 = cfk9.a(string13);
				if (boolean5) {
					if (cgl14 == null) {
						cgl14 = (cgl<?>)collection10.iterator().next();
					}

					cfj cfj15 = a(cfj, cgl14, bec.ep());
					bqc.a(fu, cfj15, 18);
					a(bec, new ne(this.a() + ".update", cgl14.f(), a(cfj15, cgl14)));
				} else {
					cgl14 = a(collection10, cgl14, bec.ep());
					String string15 = cgl14.f();
					le12.a(string11, string15);
					a(bec, new ne(this.a() + ".select", string15, a(cfj, cgl14)));
				}
			}
		}
	}

	private static <T extends Comparable<T>> cfj a(cfj cfj, cgl<T> cgl, boolean boolean3) {
		return cfj.a(cgl, a(cgl.a(), cfj.c(cgl), boolean3));
	}

	private static <T> T a(Iterable<T> iterable, @Nullable T object, boolean boolean3) {
		return boolean3 ? v.b(iterable, object) : v.a(iterable, object);
	}

	private static void a(bec bec, mr mr) {
		((ze)bec).a(mr, mo.GAME_INFO, v.b);
	}

	private static <T extends Comparable<T>> String a(cfj cfj, cgl<T> cgl) {
		return cgl.a(cfj.c(cgl));
	}
}
