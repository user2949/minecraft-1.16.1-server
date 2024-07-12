public class czj extends czd {
	private float m = Float.MAX_VALUE;
	private czd n;
	private boolean o;

	public czj(czd czd) {
		super(czd.a, czd.b, czd.c);
	}

	public void a(float float1, czd czd) {
		if (float1 < this.m) {
			this.m = float1;
			this.n = czd;
		}
	}

	public czd d() {
		return this.n;
	}

	public void e() {
		this.o = true;
	}
}
