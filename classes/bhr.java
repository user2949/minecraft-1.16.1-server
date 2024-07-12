import javax.annotation.Nullable;

public class bhr implements amz, bhq {
	private final gi<bki> a = gi.a(1, bki.b);
	private bmu<?> b;

	@Override
	public int ab_() {
		return 1;
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
		return this.a.get(0);
	}

	@Override
	public bki a(int integer1, int integer2) {
		return ana.a(this.a, 0);
	}

	@Override
	public bki b(int integer) {
		return ana.a(this.a, 0);
	}

	@Override
	public void a(int integer, bki bki) {
		this.a.set(0, bki);
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

	@Override
	public void a(@Nullable bmu<?> bmu) {
		this.b = bmu;
	}

	@Nullable
	@Override
	public bmu<?> am_() {
		return this.b;
	}
}
