public class yt {
	public final int a;
	public final int b;

	public yt(int integer1, int integer2) {
		this.a = integer1;
		this.b = integer2;
	}

	public yt(fu fu) {
		this.a = fu.u();
		this.b = fu.w();
	}

	public String toString() {
		return "[" + this.a + ", " + this.b + "]";
	}

	public int hashCode() {
		int integer2 = 1664525 * this.a + 1013904223;
		int integer3 = 1664525 * (this.b ^ -559038737) + 1013904223;
		return integer2 ^ integer3;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof yt)) {
			return false;
		} else {
			yt yt3 = (yt)object;
			return this.a == yt3.a && this.b == yt3.b;
		}
	}
}
