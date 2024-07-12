import java.io.DataInput;
import java.io.IOException;

public interface lw<T extends lu> {
	T b(DataInput dataInput, int integer, ln ln) throws IOException;

	default boolean c() {
		return false;
	}

	String a();

	String b();

	static lw<lg> a(int integer) {
		return new lw<lg>() {
			public lg b(DataInput dataInput, int integer, ln ln) throws IOException {
				throw new IllegalArgumentException("Invalid tag id: " + integer);
			}

			@Override
			public String a() {
				return "INVALID[" + integer + "]";
			}

			@Override
			public String b() {
				return "UNKNOWN_" + integer;
			}
		};
	}
}
