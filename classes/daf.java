import java.io.File;

public class daf implements Comparable<daf> {
	private final bqe a;
	private final dag b;
	private final String c;
	private final boolean d;
	private final boolean e;
	private final File f;

	public daf(bqe bqe, dag dag, String string, boolean boolean4, boolean boolean5, File file) {
		this.a = bqe;
		this.b = dag;
		this.c = string;
		this.e = boolean5;
		this.f = file;
		this.d = boolean4;
	}

	public int compareTo(daf daf) {
		if (this.b.b() < daf.b.b()) {
			return 1;
		} else {
			return this.b.b() > daf.b.b() ? -1 : this.c.compareTo(daf.c);
		}
	}

	public dag k() {
		return this.b;
	}
}
