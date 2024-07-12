import java.io.IOException;

public class pm implements ni<nl> {
	private int[] a;

	public pm() {
	}

	public pm(int... arr) {
		this.a = arr;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = new int[mg.i()];

		for (int integer3 = 0; integer3 < this.a.length; integer3++) {
			this.a[integer3] = mg.i();
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a.length);

		for (int integer6 : this.a) {
			mg.d(integer6);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
