import java.util.Optional;
import java.util.function.BiConsumer;

public class bgv extends bhp<bgu> {
	private final bgu c = new bgu(this, 3, 3);
	private final bhr d = new bhr();
	private final bgs e;
	private final bec f;

	public bgv(int integer, beb beb) {
		this(integer, beb, bgs.a);
	}

	public bgv(int integer, beb beb, bgs bgs) {
		super(bhk.l, integer);
		this.e = bgs;
		this.f = beb.e;
		this.a(new bhs(beb.e, this.c, this.d, 0, 124, 35));

		for (int integer5 = 0; integer5 < 3; integer5++) {
			for (int integer6 = 0; integer6 < 3; integer6++) {
				this.a(new bhw(this.c, integer6 + integer5 * 3, 30 + integer6 * 18, 17 + integer5 * 18));
			}
		}

		for (int integer5 = 0; integer5 < 3; integer5++) {
			for (int integer6 = 0; integer6 < 9; integer6++) {
				this.a(new bhw(beb, integer6 + integer5 * 9 + 9, 8 + integer6 * 18, 84 + integer5 * 18));
			}
		}

		for (int integer5 = 0; integer5 < 9; integer5++) {
			this.a(new bhw(beb, integer5, 8 + integer5 * 18, 142));
		}
	}

	protected static void a(int integer, bqb bqb, bec bec, bgu bgu, bhr bhr) {
		if (!bqb.v) {
			ze ze6 = (ze)bec;
			bki bki7 = bki.b;
			Optional<bmm> optional8 = bqb.l().aD().a(bmx.a, bgu, bqb);
			if (optional8.isPresent()) {
				bmm bmm9 = (bmm)optional8.get();
				if (bhr.a(bqb, ze6, bmm9)) {
					bki7 = bmm9.a(bgu);
				}
			}

			bhr.a(0, bki7);
			ze6.b.a(new oi(integer, 0, bki7));
		}
	}

	@Override
	public void a(amz amz) {
		this.e.a((BiConsumer<bqb, fu>)((bqb, fu) -> a(this.b, bqb, this.f, this.c, this.d)));
	}

	@Override
	public void a(bee bee) {
		this.c.a(bee);
	}

	@Override
	public void e() {
		this.c.aa_();
		this.d.aa_();
	}

	@Override
	public boolean a(bmu<? super bgu> bmu) {
		return bmu.a(this.c, this.f.l);
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		this.e.a((BiConsumer<bqb, fu>)((bqb, fu) -> this.a(bec, bqb, this.c)));
	}

	@Override
	public boolean a(bec bec) {
		return a(this.e, bec, bvs.bV);
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bki4 = bki6.i();
			if (integer == 0) {
				this.e.a((BiConsumer<bqb, fu>)((bqb, fu) -> bki6.b().b(bki6, bqb, bec)));
				if (!this.a(bki6, 10, 46, true)) {
					return bki.b;
				}

				bhw5.a(bki6, bki4);
			} else if (integer >= 10 && integer < 46) {
				if (!this.a(bki6, 1, 10, false)) {
					if (integer < 37) {
						if (!this.a(bki6, 37, 46, false)) {
							return bki.b;
						}
					} else if (!this.a(bki6, 10, 37, false)) {
						return bki.b;
					}
				}
			} else if (!this.a(bki6, 10, 46, false)) {
				return bki.b;
			}

			if (bki6.a()) {
				bhw5.d(bki.b);
			} else {
				bhw5.d();
			}

			if (bki6.E() == bki4.E()) {
				return bki.b;
			}

			bki bki7 = bhw5.a(bec, bki6);
			if (integer == 0) {
				bec.a(bki7, false);
			}
		}

		return bki4;
	}

	@Override
	public boolean a(bki bki, bhw bhw) {
		return bhw.c != this.d && super.a(bki, bhw);
	}

	@Override
	public int f() {
		return 0;
	}

	@Override
	public int g() {
		return this.c.g();
	}

	@Override
	public int h() {
		return this.c.f();
	}
}
