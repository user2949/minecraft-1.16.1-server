import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class bpi<T> implements bqr<T> {
	private final List<bpi.a<T>> a;
	private final Function<T, uh> b;

	public bpi(Function<T, uh> function, List<bqs<T>> list, long long3) {
		this(function, (List<bpi.a<T>>)list.stream().map(bqs -> new bpi.a(bqs.b(), bqs.a, (int)(bqs.b - long3), bqs.c)).collect(Collectors.toList()));
	}

	private bpi(Function<T, uh> function, List<bpi.a<T>> list) {
		this.a = list;
		this.b = function;
	}

	@Override
	public boolean a(fu fu, T object) {
		return false;
	}

	@Override
	public void a(fu fu, T object, int integer, bqt bqt) {
		this.a.add(new bpi.a(object, fu, integer, bqt));
	}

	@Override
	public boolean b(fu fu, T object) {
		return false;
	}

	public lk b() {
		lk lk2 = new lk();

		for (bpi.a<T> a4 : this.a) {
			le le5 = new le();
			le5.a("i", ((uh)this.b.apply(a4.d)).toString());
			le5.b("x", a4.a.u());
			le5.b("y", a4.a.v());
			le5.b("z", a4.a.w());
			le5.b("t", a4.b);
			le5.b("p", a4.c.a());
			lk2.add(le5);
		}

		return lk2;
	}

	public static <T> bpi<T> a(lk lk, Function<T, uh> function2, Function<uh, T> function3) {
		List<bpi.a<T>> list4 = Lists.<bpi.a<T>>newArrayList();

		for (int integer5 = 0; integer5 < lk.size(); integer5++) {
			le le6 = lk.a(integer5);
			T object7 = (T)function3.apply(new uh(le6.l("i")));
			if (object7 != null) {
				fu fu8 = new fu(le6.h("x"), le6.h("y"), le6.h("z"));
				list4.add(new bpi.a(object7, fu8, le6.h("t"), bqt.a(le6.h("p"))));
			}
		}

		return new bpi<>(function2, list4);
	}

	public void a(bqr<T> bqr) {
		this.a.forEach(a -> bqr.a(a.a, a.d, a.b, a.c));
	}

	static class a<T> {
		private final T d;
		public final fu a;
		public final int b;
		public final bqt c;

		private a(T object, fu fu, int integer, bqt bqt) {
			this.d = object;
			this.a = fu;
			this.b = integer;
			this.c = bqt;
		}

		public String toString() {
			return this.d + ": " + this.a + ", " + this.b + ", " + this.c;
		}
	}
}
