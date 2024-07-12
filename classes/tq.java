public class tq<T> {
	private final int a;
	private final tr<T> b;

	public tq(int integer, tr<T> tr) {
		this.a = integer;
		this.b = tr;
	}

	public int a() {
		return this.a;
	}

	public tr<T> b() {
		return this.b;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			tq<?> tq3 = (tq<?>)object;
			return this.a == tq3.a;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.a;
	}

	public String toString() {
		return "<entity data: " + this.a + ">";
	}
}
