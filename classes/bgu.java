public class bgu implements amz, bhz {
	private final gi<bki> a;
	private final int b;
	private final int c;
	private final bgi d;

	public bgu(bgi bgi, int integer2, int integer3) {
		this.a = gi.a(integer2 * integer3, bki.b);
		this.d = bgi;
		this.b = integer2;
		this.c = integer3;
	}

	@Override
	public int ab_() {
		return this.a.size();
	}

	@Override
	public boolean c() {
		for (bki bki3 : this.a) {
			if (!bki3.a()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public bki a(int integer) {
		return integer >= this.ab_() ? bki.b : this.a.get(integer);
	}

	@Override
	public bki b(int integer) {
		return ana.a(this.a, integer);
	}

	@Override
	public bki a(int integer1, int integer2) {
		bki bki4 = ana.a(this.a, integer1, integer2);
		if (!bki4.a()) {
			this.d.a(this);
		}

		return bki4;
	}

	@Override
	public void a(int integer, bki bki) {
		this.a.set(integer, bki);
		this.d.a(this);
	}

	@Override
	public void Z_() {
	}

	@Override
	public boolean a(bec bec) {
		return true;
	}

	@Override
	public void aa_() {
		this.a.clear();
	}

	public int f() {
		return this.c;
	}

	public int g() {
		return this.b;
	}

	@Override
	public void a(bee bee) {
		for (bki bki4 : this.a) {
			bee.a(bki4);
		}
	}
}
