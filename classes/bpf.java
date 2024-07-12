public class bpf {
	private final fu a;
	private final bvr b;
	private final int c;
	private final int d;

	public bpf(fu fu, bvr bvr, int integer3, int integer4) {
		this.a = fu;
		this.b = bvr;
		this.c = integer3;
		this.d = integer4;
	}

	public fu a() {
		return this.a;
	}

	public bvr b() {
		return this.b;
	}

	public int c() {
		return this.c;
	}

	public int d() {
		return this.d;
	}

	public boolean equals(Object object) {
		if (!(object instanceof bpf)) {
			return false;
		} else {
			bpf bpf3 = (bpf)object;
			return this.a.equals(bpf3.a) && this.c == bpf3.c && this.d == bpf3.d && this.b == bpf3.b;
		}
	}

	public int hashCode() {
		int integer2 = this.a.hashCode();
		integer2 = 31 * integer2 + this.b.hashCode();
		integer2 = 31 * integer2 + this.c;
		return 31 * integer2 + this.d;
	}

	public String toString() {
		return "TE(" + this.a + ")," + this.c + "," + this.d + "," + this.b;
	}
}
