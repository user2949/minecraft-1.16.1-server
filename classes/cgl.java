import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class cgl<T extends Comparable<T>> {
	private final Class<T> a;
	private final String b;
	private Integer c;
	private final Codec<T> d = Codec.STRING
		.comapFlatMap(
			stringx -> (DataResult)this.b(stringx)
					.map(DataResult::success)
					.orElseGet(() -> DataResult.error("Unable to read property: " + this + " with value: " + stringx)),
			this::a
		);
	private final Codec<cgl.a<T>> e = this.d.xmap(this::b, cgl.a::b);

	protected cgl(String string, Class<T> class2) {
		this.a = class2;
		this.b = string;
	}

	public cgl.a<T> b(T comparable) {
		return new cgl.a<>(this, comparable);
	}

	public cgl.a<T> a(cfl<?, ?> cfl) {
		return new cgl.a<>(this, cfl.c(this));
	}

	public Stream<cgl.a<T>> c() {
		return this.a().stream().map(this::b);
	}

	public Codec<cgl.a<T>> e() {
		return this.e;
	}

	public String f() {
		return this.b;
	}

	public Class<T> g() {
		return this.a;
	}

	public abstract Collection<T> a();

	public abstract String a(T comparable);

	public abstract Optional<T> b(String string);

	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", this.b).add("clazz", this.a).add("values", this.a()).toString();
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof cgl)) {
			return false;
		} else {
			cgl<?> cgl3 = (cgl<?>)object;
			return this.a.equals(cgl3.a) && this.b.equals(cgl3.b);
		}
	}

	public final int hashCode() {
		if (this.c == null) {
			this.c = this.b();
		}

		return this.c;
	}

	public int b() {
		return 31 * this.a.hashCode() + this.b.hashCode();
	}

	public static final class a<T extends Comparable<T>> {
		private final cgl<T> a;
		private final T b;

		private a(cgl<T> cgl, T comparable) {
			if (!cgl.a().contains(comparable)) {
				throw new IllegalArgumentException("Value " + comparable + " does not belong to property " + cgl);
			} else {
				this.a = cgl;
				this.b = comparable;
			}
		}

		public cgl<T> a() {
			return this.a;
		}

		public T b() {
			return this.b;
		}

		public String toString() {
			return this.a.f() + "=" + this.a.a(this.b);
		}

		public boolean equals(Object object) {
			if (this == object) {
				return true;
			} else if (!(object instanceof cgl.a)) {
				return false;
			} else {
				cgl.a<?> a3 = (cgl.a<?>)object;
				return this.a == a3.a && this.b.equals(a3.b);
			}
		}

		public int hashCode() {
			int integer2 = this.a.hashCode();
			return 31 * integer2 + this.b.hashCode();
		}
	}
}
