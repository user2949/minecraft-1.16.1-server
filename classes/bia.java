import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.BiConsumer;

public class bia extends bgi {
	private final bgs f;
	private final bgw g = bgw.a();
	private final bqb h;
	private List<bni> i = Lists.<bni>newArrayList();
	private bki j = bki.b;
	private long k;
	final bhw c;
	final bhw d;
	private Runnable l = () -> {
	};
	public final amz e = new anm(1) {
		@Override
		public void Z_() {
			super.Z_();
			bia.this.a(this);
			bia.this.l.run();
		}
	};
	private final bhr m = new bhr();

	public bia(int integer, beb beb) {
		this(integer, beb, bgs.a);
	}

	public bia(int integer, beb beb, bgs bgs) {
		super(bhk.x, integer);
		this.f = bgs;
		this.h = beb.e.l;
		this.c = this.a(new bhw(this.e, 0, 20, 33));
		this.d = this.a(new bhw(this.m, 1, 143, 33) {
			@Override
			public boolean a(bki bki) {
				return false;
			}

			@Override
			public bki a(bec bec, bki bki) {
				bki bki4 = bia.this.c.a(1);
				if (!bki4.a()) {
					bia.this.i();
				}

				bki.b().b(bki, bec.l, bec);
				bgs.a((BiConsumer<bqb, fu>)((bqb, fu) -> {
					long long4 = bqb.Q();
					if (bia.this.k != long4) {
						bqb.a(null, fu, acl.pC, acm.BLOCKS, 1.0F, 1.0F);
						bia.this.k = long4;
					}
				}));
				return super.a(bec, bki);
			}
		});

		for (int integer5 = 0; integer5 < 3; integer5++) {
			for (int integer6 = 0; integer6 < 9; integer6++) {
				this.a(new bhw(beb, integer6 + integer5 * 9 + 9, 8 + integer6 * 18, 84 + integer5 * 18));
			}
		}

		for (int integer5 = 0; integer5 < 9; integer5++) {
			this.a(new bhw(beb, integer5, 8 + integer5 * 18, 142));
		}

		this.a(this.g);
	}

	@Override
	public boolean a(bec bec) {
		return a(this.f, bec, bvs.ma);
	}

	@Override
	public boolean a(bec bec, int integer) {
		if (this.d(integer)) {
			this.g.a(integer);
			this.i();
		}

		return true;
	}

	private boolean d(int integer) {
		return integer >= 0 && integer < this.i.size();
	}

	@Override
	public void a(amz amz) {
		bki bki3 = this.c.e();
		if (bki3.b() != this.j.b()) {
			this.j = bki3.i();
			this.a(amz, bki3);
		}
	}

	private void a(amz amz, bki bki) {
		this.i.clear();
		this.g.a(-1);
		this.d.d(bki.b);
		if (!bki.a()) {
			this.i = this.h.o().b(bmx.f, amz, this.h);
		}
	}

	private void i() {
		if (!this.i.isEmpty() && this.d(this.g.b())) {
			bni bni2 = (bni)this.i.get(this.g.b());
			this.d.d(bni2.a(this.e));
		} else {
			this.d.d(bki.b);
		}

		this.c();
	}

	@Override
	public bhk<?> a() {
		return bhk.x;
	}

	@Override
	public boolean a(bki bki, bhw bhw) {
		return bhw.c != this.m && super.a(bki, bhw);
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bke bke7 = bki6.b();
			bki4 = bki6.i();
			if (integer == 1) {
				bke7.b(bki6, bec.l, bec);
				if (!this.a(bki6, 2, 38, true)) {
					return bki.b;
				}

				bhw5.a(bki6, bki4);
			} else if (integer == 0) {
				if (!this.a(bki6, 2, 38, false)) {
					return bki.b;
				}
			} else if (this.h.o().a(bmx.f, new anm(bki6), this.h).isPresent()) {
				if (!this.a(bki6, 0, 1, false)) {
					return bki.b;
				}
			} else if (integer >= 2 && integer < 29) {
				if (!this.a(bki6, 29, 38, false)) {
					return bki.b;
				}
			} else if (integer >= 29 && integer < 38 && !this.a(bki6, 2, 29, false)) {
				return bki.b;
			}

			if (bki6.a()) {
				bhw5.d(bki.b);
			}

			bhw5.d();
			if (bki6.E() == bki4.E()) {
				return bki.b;
			}

			bhw5.a(bec, bki6);
			this.c();
		}

		return bki4;
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		this.m.b(1);
		this.f.a((BiConsumer<bqb, fu>)((bqb, fu) -> this.a(bec, bec.l, this.e)));
	}
}
