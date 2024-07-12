import java.util.Random;

public class cdu extends cef {
	private static final Random a = new Random();
	private gi<bki> b = gi.a(9, bki.b);

	protected cdu(cdm<?> cdm) {
		super(cdm);
	}

	public cdu() {
		this(cdm.f);
	}

	@Override
	public int ab_() {
		return 9;
	}

	public int h() {
		this.d(null);
		int integer2 = -1;
		int integer3 = 1;

		for (int integer4 = 0; integer4 < this.b.size(); integer4++) {
			if (!this.b.get(integer4).a() && a.nextInt(integer3++) == 0) {
				integer2 = integer4;
			}
		}

		return integer2;
	}

	public int a(bki bki) {
		for (int integer3 = 0; integer3 < this.b.size(); integer3++) {
			if (this.b.get(integer3).a()) {
				this.a(integer3, bki);
				return integer3;
			}
		}

		return -1;
	}

	@Override
	protected mr g() {
		return new ne("container.dispenser");
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.b = gi.a(this.ab_(), bki.b);
		if (!this.b(le)) {
			ana.b(le, this.b);
		}
	}

	@Override
	public le a(le le) {
		super.a(le);
		if (!this.c(le)) {
			ana.a(le, this.b);
		}

		return le;
	}

	@Override
	protected gi<bki> f() {
		return this.b;
	}

	@Override
	protected void a(gi<bki> gi) {
		this.b = gi;
	}

	@Override
	protected bgi a(int integer, beb beb) {
		return new bgx(integer, beb, this);
	}
}
