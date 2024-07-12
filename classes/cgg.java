import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class cgg<T extends Enum<T> & aeh> extends cgl<T> {
	private final ImmutableSet<T> a;
	private final Map<String, T> b = Maps.<String, T>newHashMap();

	protected cgg(String string, Class<T> class2, Collection<T> collection) {
		super(string, class2);
		this.a = ImmutableSet.copyOf(collection);

		for (T enum6 : collection) {
			String string7 = enum6.a();
			if (this.b.containsKey(string7)) {
				throw new IllegalArgumentException("Multiple values have the same name '" + string7 + "'");
			}

			this.b.put(string7, enum6);
		}
	}

	@Override
	public Collection<T> a() {
		return this.a;
	}

	@Override
	public Optional<T> b(String string) {
		return Optional.ofNullable(this.b.get(string));
	}

	public String a(T enum1) {
		return enum1.a();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof cgg && super.equals(object)) {
			cgg<?> cgg3 = (cgg<?>)object;
			return this.a.equals(cgg3.a) && this.b.equals(cgg3.b);
		} else {
			return false;
		}
	}

	@Override
	public int b() {
		int integer2 = super.b();
		integer2 = 31 * integer2 + this.a.hashCode();
		return 31 * integer2 + this.b.hashCode();
	}

	public static <T extends Enum<T> & aeh> cgg<T> a(String string, Class<T> class2) {
		return a(string, class2, Predicates.alwaysTrue());
	}

	public static <T extends Enum<T> & aeh> cgg<T> a(String string, Class<T> class2, Predicate<T> predicate) {
		return a(string, class2, (Collection<T>)Arrays.stream(class2.getEnumConstants()).filter(predicate).collect(Collectors.toList()));
	}

	public static <T extends Enum<T> & aeh> cgg<T> a(String string, Class<T> class2, T... arr) {
		return a(string, class2, Lists.<T>newArrayList(arr));
	}

	public static <T extends Enum<T> & aeh> cgg<T> a(String string, Class<T> class2, Collection<T> collection) {
		return new cgg<>(string, class2, collection);
	}
}
