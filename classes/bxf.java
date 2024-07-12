import java.util.function.BiPredicate;
import java.util.function.Function;

public class bxf {
	public static <S extends cdl> bxf.c<S> a(
		cdm<S> cdm, Function<cfj, bxf.a> function2, Function<cfj, fz> function3, cgd cgd, cfj cfj, bqc bqc, fu fu, BiPredicate<bqc, fu> biPredicate
	) {
		S cdl9 = cdm.a(bqc, fu);
		if (cdl9 == null) {
			return bxf.b::b;
		} else if (biPredicate.test(bqc, fu)) {
			return bxf.b::b;
		} else {
			bxf.a a10 = (bxf.a)function2.apply(cfj);
			boolean boolean11 = a10 == bxf.a.SINGLE;
			boolean boolean12 = a10 == bxf.a.FIRST;
			if (boolean11) {
				return new bxf.c.b<>(cdl9);
			} else {
				fu fu13 = fu.a((fz)function3.apply(cfj));
				cfj cfj14 = bqc.d_(fu13);
				if (cfj14.a(cfj.b())) {
					bxf.a a15 = (bxf.a)function2.apply(cfj14);
					if (a15 != bxf.a.SINGLE && a10 != a15 && cfj14.c(cgd) == cfj.c(cgd)) {
						if (biPredicate.test(bqc, fu13)) {
							return bxf.b::b;
						}

						S cdl16 = cdm.a(bqc, fu13);
						if (cdl16 != null) {
							S cdl17 = boolean12 ? cdl9 : cdl16;
							S cdl18 = boolean12 ? cdl16 : cdl9;
							return new bxf.c.a<>(cdl17, cdl18);
						}
					}
				}

				return new bxf.c.b<>(cdl9);
			}
		}
	}

	public static enum a {
		SINGLE,
		FIRST,
		SECOND;
	}

	public interface b<S, T> {
		T a(S object1, S object2);

		T a(S object);

		T b();
	}

	public interface c<S> {
		<T> T apply(bxf.b<? super S, T> b);

		public static final class a<S> implements bxf.c<S> {
			private final S a;
			private final S b;

			public a(S object1, S object2) {
				this.a = object1;
				this.b = object2;
			}

			@Override
			public <T> T apply(bxf.b<? super S, T> b) {
				return b.a(this.a, this.b);
			}
		}

		public static final class b<S> implements bxf.c<S> {
			private final S a;

			public b(S object) {
				this.a = object;
			}

			@Override
			public <T> T apply(bxf.b<? super S, T> b) {
				return b.a(this.a);
			}
		}
	}
}
