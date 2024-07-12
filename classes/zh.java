import java.util.Objects;

public final class zh<T> implements Comparable<zh<?>> {
	private final zi<T> a;
	private final int b;
	private final T c;
	private long d;

	protected zh(zi<T> zi, int integer, T object) {
		this.a = zi;
		this.b = integer;
		this.c = object;
	}

	public int compareTo(zh<?> zh) {
		int integer3 = Integer.compare(this.b, zh.b);
		if (integer3 != 0) {
			return integer3;
		} else {
			int integer4 = Integer.compare(System.identityHashCode(this.a), System.identityHashCode(zh.a));
			return integer4 != 0 ? integer4 : this.a.a().compare(this.c, zh.c);
		}
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof zh)) {
			return false;
		} else {
			zh<?> zh3 = (zh<?>)object;
			return this.b == zh3.b && Objects.equals(this.a, zh3.a) && Objects.equals(this.c, zh3.c);
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.a, this.b, this.c});
	}

	public String toString() {
		return "Ticket[" + this.a + " " + this.b + " (" + this.c + ")] at " + this.d;
	}

	public zi<T> a() {
		return this.a;
	}

	public int b() {
		return this.b;
	}

	protected void a(long long1) {
		this.d = long1;
	}

	protected boolean b(long long1) {
		long long4 = this.a.b();
		return long4 != 0L && long1 - this.d > long4;
	}
}
