import javax.annotation.Nullable;

public class bin extends blv {
	private final fu g;
	protected boolean a = true;

	public bin(bec bec, anf anf, bki bki, deh deh) {
		this(bec.l, bec, anf, bki, deh);
	}

	public bin(blv blv) {
		this(blv.o(), blv.m(), blv.n(), blv.l(), blv.d);
	}

	protected bin(bqb bqb, @Nullable bec bec, anf anf, bki bki, deh deh) {
		super(bqb, bec, anf, bki, deh);
		this.g = deh.a().a(deh.b());
		this.a = bqb.d_(deh.a()).a(this);
	}

	public static bin a(bin bin, fu fu, fz fz) {
		return new bin(
			bin.o(),
			bin.m(),
			bin.n(),
			bin.l(),
			new deh(
				new dem((double)fu.u() + 0.5 + (double)fz.i() * 0.5, (double)fu.v() + 0.5 + (double)fz.j() * 0.5, (double)fu.w() + 0.5 + (double)fz.k() * 0.5),
				fz,
				fu,
				false
			)
		);
	}

	@Override
	public fu a() {
		return this.a ? super.a() : this.g;
	}

	public boolean b() {
		return this.a || this.o().d_(this.a()).a(this);
	}

	public boolean c() {
		return this.a;
	}

	public fz d() {
		return fz.a(this.b)[0];
	}

	public fz[] e() {
		fz[] arr2 = fz.a(this.b);
		if (this.a) {
			return arr2;
		} else {
			fz fz3 = this.i();
			int integer4 = 0;

			while (integer4 < arr2.length && arr2[integer4] != fz3.f()) {
				integer4++;
			}

			if (integer4 > 0) {
				System.arraycopy(arr2, 0, arr2, 1, integer4);
				arr2[0] = fz3.f();
			}

			return arr2;
		}
	}
}
