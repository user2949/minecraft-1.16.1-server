import java.util.function.Predicate;

public class bpj {
	private final dem a;
	private final dem b;
	private final bpj.a c;
	private final bpj.b d;
	private final der e;

	public bpj(dem dem1, dem dem2, bpj.a a, bpj.b b, aom aom) {
		this.a = dem1;
		this.b = dem2;
		this.c = a;
		this.d = b;
		this.e = der.a(aom);
	}

	public dem a() {
		return this.b;
	}

	public dem b() {
		return this.a;
	}

	public dfg a(cfj cfj, bpg bpg, fu fu) {
		return this.c.get(cfj, bpg, fu, this.e);
	}

	public dfg a(cxa cxa, bpg bpg, fu fu) {
		return this.d.a(cxa) ? cxa.d(bpg, fu) : dfd.a();
	}

	public static enum a implements bpj.c {
		COLLIDER(cfi.a::b),
		OUTLINE(cfi.a::a),
		VISUAL(cfi.a::c);

		private final bpj.c d;

		private a(bpj.c c) {
			this.d = c;
		}

		@Override
		public dfg get(cfj cfj, bpg bpg, fu fu, der der) {
			return this.d.get(cfj, bpg, fu, der);
		}
	}

	public static enum b {
		NONE(cxa -> false),
		SOURCE_ONLY(cxa::b),
		ANY(cxa -> !cxa.c());

		private final Predicate<cxa> d;

		private b(Predicate<cxa> predicate) {
			this.d = predicate;
		}

		public boolean a(cxa cxa) {
			return this.d.test(cxa);
		}
	}

	public interface c {
		dfg get(cfj cfj, bpg bpg, fu fu, der der);
	}
}
