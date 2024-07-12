public class amy implements amz {
	private final amz a;
	private final amz b;

	public amy(amz amz1, amz amz2) {
		if (amz1 == null) {
			amz1 = amz2;
		}

		if (amz2 == null) {
			amz2 = amz1;
		}

		this.a = amz1;
		this.b = amz2;
	}

	@Override
	public int ab_() {
		return this.a.ab_() + this.b.ab_();
	}

	@Override
	public boolean c() {
		return this.a.c() && this.b.c();
	}

	public boolean a(amz amz) {
		return this.a == amz || this.b == amz;
	}

	@Override
	public bki a(int integer) {
		return integer >= this.a.ab_() ? this.b.a(integer - this.a.ab_()) : this.a.a(integer);
	}

	@Override
	public bki a(int integer1, int integer2) {
		return integer1 >= this.a.ab_() ? this.b.a(integer1 - this.a.ab_(), integer2) : this.a.a(integer1, integer2);
	}

	@Override
	public bki b(int integer) {
		return integer >= this.a.ab_() ? this.b.b(integer - this.a.ab_()) : this.a.b(integer);
	}

	@Override
	public void a(int integer, bki bki) {
		if (integer >= this.a.ab_()) {
			this.b.a(integer - this.a.ab_(), bki);
		} else {
			this.a.a(integer, bki);
		}
	}

	@Override
	public int X_() {
		return this.a.X_();
	}

	@Override
	public void Z_() {
		this.a.Z_();
		this.b.Z_();
	}

	@Override
	public boolean a(bec bec) {
		return this.a.a(bec) && this.b.a(bec);
	}

	@Override
	public void c_(bec bec) {
		this.a.c_(bec);
		this.b.c_(bec);
	}

	@Override
	public void b_(bec bec) {
		this.a.b_(bec);
		this.b.b_(bec);
	}

	@Override
	public boolean b(int integer, bki bki) {
		return integer >= this.a.ab_() ? this.b.b(integer - this.a.ab_(), bki) : this.a.b(integer, bki);
	}

	@Override
	public void aa_() {
		this.a.aa_();
		this.b.aa_();
	}
}
