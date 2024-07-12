import java.io.IOException;
import java.util.List;

public class qg implements ni<nl> {
	private int a;
	private int[] b;

	public qg() {
	}

	public qg(aom aom) {
		this.a = aom.V();
		List<aom> list3 = aom.cm();
		this.b = new int[list3.size()];

		for (int integer4 = 0; integer4 < list3.size(); integer4++) {
			this.b[integer4] = ((aom)list3.get(integer4)).V();
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.b();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
