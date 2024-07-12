import java.util.Map;

public class byq extends bvr {
	public static final cga a = bzv.a;
	public static final cga b = bzv.b;
	public static final cga c = bzv.c;
	public static final cga d = bzv.d;
	public static final cga e = bzv.e;
	public static final cga f = bzv.f;
	private static final Map<fz, cga> g = bzv.g;

	public byq(cfi.c c) {
		super(c);
		this.j(
			this.n
				.b()
				.a(a, Boolean.valueOf(true))
				.a(b, Boolean.valueOf(true))
				.a(byq.c, Boolean.valueOf(true))
				.a(d, Boolean.valueOf(true))
				.a(e, Boolean.valueOf(true))
				.a(f, Boolean.valueOf(true))
		);
	}

	@Override
	public cfj a(bin bin) {
		bpg bpg3 = bin.o();
		fu fu4 = bin.a();
		return this.n()
			.a(f, Boolean.valueOf(this != bpg3.d_(fu4.c()).b()))
			.a(e, Boolean.valueOf(this != bpg3.d_(fu4.b()).b()))
			.a(a, Boolean.valueOf(this != bpg3.d_(fu4.d()).b()))
			.a(b, Boolean.valueOf(this != bpg3.d_(fu4.g()).b()))
			.a(c, Boolean.valueOf(this != bpg3.d_(fu4.e()).b()))
			.a(d, Boolean.valueOf(this != bpg3.d_(fu4.f()).b()));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return cfj3.a(this) ? cfj1.a((cgl)g.get(fz), Boolean.valueOf(false)) : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a((cgl)g.get(cap.a(fz.NORTH)), cfj.c(a))
			.a((cgl)g.get(cap.a(fz.SOUTH)), cfj.c(c))
			.a((cgl)g.get(cap.a(fz.EAST)), cfj.c(b))
			.a((cgl)g.get(cap.a(fz.WEST)), cfj.c(d))
			.a((cgl)g.get(cap.a(fz.UP)), cfj.c(e))
			.a((cgl)g.get(cap.a(fz.DOWN)), cfj.c(f));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a((cgl)g.get(bzj.b(fz.NORTH)), cfj.c(a))
			.a((cgl)g.get(bzj.b(fz.SOUTH)), cfj.c(c))
			.a((cgl)g.get(bzj.b(fz.EAST)), cfj.c(b))
			.a((cgl)g.get(bzj.b(fz.WEST)), cfj.c(d))
			.a((cgl)g.get(bzj.b(fz.UP)), cfj.c(e))
			.a((cgl)g.get(bzj.b(fz.DOWN)), cfj.c(f));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(e, f, byq.a, b, c, d);
	}
}
