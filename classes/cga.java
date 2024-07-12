import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Optional;

public class cga extends cgl<Boolean> {
	private final ImmutableSet<Boolean> a = ImmutableSet.of(true, false);

	protected cga(String string) {
		super(string, Boolean.class);
	}

	@Override
	public Collection<Boolean> a() {
		return this.a;
	}

	public static cga a(String string) {
		return new cga(string);
	}

	@Override
	public Optional<Boolean> b(String string) {
		return !"true".equals(string) && !"false".equals(string) ? Optional.empty() : Optional.of(Boolean.valueOf(string));
	}

	public String a(Boolean boolean1) {
		return boolean1.toString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof cga && super.equals(object)) {
			cga cga3 = (cga)object;
			return this.a.equals(cga3.a);
		} else {
			return false;
		}
	}

	@Override
	public int b() {
		return 31 * super.b() + this.a.hashCode();
	}
}
