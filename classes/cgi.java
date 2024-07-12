import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class cgi extends cgl<Integer> {
	private final ImmutableSet<Integer> a;

	protected cgi(String string, int integer2, int integer3) {
		super(string, Integer.class);
		if (integer2 < 0) {
			throw new IllegalArgumentException("Min value of " + string + " must be 0 or greater");
		} else if (integer3 <= integer2) {
			throw new IllegalArgumentException("Max value of " + string + " must be greater than min (" + integer2 + ")");
		} else {
			Set<Integer> set5 = Sets.<Integer>newHashSet();

			for (int integer6 = integer2; integer6 <= integer3; integer6++) {
				set5.add(integer6);
			}

			this.a = ImmutableSet.copyOf(set5);
		}
	}

	@Override
	public Collection<Integer> a() {
		return this.a;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof cgi && super.equals(object)) {
			cgi cgi3 = (cgi)object;
			return this.a.equals(cgi3.a);
		} else {
			return false;
		}
	}

	@Override
	public int b() {
		return 31 * super.b() + this.a.hashCode();
	}

	public static cgi a(String string, int integer2, int integer3) {
		return new cgi(string, integer2, integer3);
	}

	@Override
	public Optional<Integer> b(String string) {
		try {
			Integer integer3 = Integer.valueOf(string);
			return this.a.contains(integer3) ? Optional.of(integer3) : Optional.empty();
		} catch (NumberFormatException var3) {
			return Optional.empty();
		}
	}

	public String a(Integer integer) {
		return integer.toString();
	}
}
