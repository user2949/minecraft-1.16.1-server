import com.google.gson.JsonObject;

public interface ad<T extends ae> {
	uh a();

	void a(uq uq, ad.a<T> a);

	void b(uq uq, ad.a<T> a);

	void a(uq uq);

	T a(JsonObject jsonObject, av av);

	public static class a<T extends ae> {
		private final T a;
		private final w b;
		private final String c;

		public a(T ae, w w, String string) {
			this.a = ae;
			this.b = w;
			this.c = string;
		}

		public T a() {
			return this.a;
		}

		public void a(uq uq) {
			uq.a(this.b, this.c);
		}

		public boolean equals(Object object) {
			if (this == object) {
				return true;
			} else if (object != null && this.getClass() == object.getClass()) {
				ad.a<?> a3 = (ad.a<?>)object;
				if (!this.a.equals(a3.a)) {
					return false;
				} else {
					return !this.b.equals(a3.b) ? false : this.c.equals(a3.c);
				}
			} else {
				return false;
			}
		}

		public int hashCode() {
			int integer2 = this.a.hashCode();
			integer2 = 31 * integer2 + this.b.hashCode();
			return 31 * integer2 + this.c.hashCode();
		}
	}
}
