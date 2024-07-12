import io.netty.util.internal.ThreadLocalRandom;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class apv {
	private static final Logger a = LogManager.getLogger();
	private final double b;
	private final apv.a c;
	private final Supplier<String> d;
	private final UUID e;

	public apv(String string, double double2, apv.a a) {
		this(aec.a(ThreadLocalRandom.current()), (Supplier<String>)(() -> string), double2, a);
	}

	public apv(UUID uUID, String string, double double3, apv.a a) {
		this(uUID, (Supplier<String>)(() -> string), double3, a);
	}

	public apv(UUID uUID, Supplier<String> supplier, double double3, apv.a a) {
		this.e = uUID;
		this.d = supplier;
		this.b = double3;
		this.c = a;
	}

	public UUID a() {
		return this.e;
	}

	public String b() {
		return (String)this.d.get();
	}

	public apv.a c() {
		return this.c;
	}

	public double d() {
		return this.b;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			apv apv3 = (apv)object;
			return Objects.equals(this.e, apv3.e);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.e.hashCode();
	}

	public String toString() {
		return "AttributeModifier{amount=" + this.b + ", operation=" + this.c + ", name='" + (String)this.d.get() + '\'' + ", id=" + this.e + '}';
	}

	public le e() {
		le le2 = new le();
		le2.a("Name", this.b());
		le2.a("Amount", this.b);
		le2.b("Operation", this.c.a());
		le2.a("UUID", this.e);
		return le2;
	}

	@Nullable
	public static apv a(le le) {
		try {
			UUID uUID2 = le.a("UUID");
			apv.a a3 = apv.a.a(le.h("Operation"));
			return new apv(uUID2, le.l("Name"), le.k("Amount"), a3);
		} catch (Exception var3) {
			a.warn("Unable to create attribute: {}", var3.getMessage());
			return null;
		}
	}

	public static enum a {
		ADDITION(0),
		MULTIPLY_BASE(1),
		MULTIPLY_TOTAL(2);

		private static final apv.a[] d = new apv.a[]{ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL};
		private final int e;

		private a(int integer3) {
			this.e = integer3;
		}

		public int a() {
			return this.e;
		}

		public static apv.a a(int integer) {
			if (integer >= 0 && integer < d.length) {
				return d[integer];
			} else {
				throw new IllegalArgumentException("No operation with value " + integer);
			}
		}
	}
}
