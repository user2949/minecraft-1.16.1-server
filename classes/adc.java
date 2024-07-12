import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;

public class adc<T> implements adf<T> {
	private final ImmutableList<T> b;
	private final Set<T> c;
	@VisibleForTesting
	protected final Class<?> a;

	protected adc(Set<T> set, Class<?> class2) {
		this.a = class2;
		this.c = set;
		this.b = ImmutableList.copyOf(set);
	}

	public static <T> adc<T> a() {
		return new adc<>(ImmutableSet.of(), Void.class);
	}

	public static <T> adc<T> a(Set<T> set) {
		return new adc<>(set, c(set));
	}

	@Override
	public boolean a(T object) {
		return this.a.isInstance(object) && this.c.contains(object);
	}

	@Override
	public List<T> b() {
		return this.b;
	}

	private static <T> Class<?> c(Set<T> set) {
		if (set.isEmpty()) {
			return Void.class;
		} else {
			Class<?> class2 = null;

			for (T object4 : set) {
				if (class2 == null) {
					class2 = object4.getClass();
				} else {
					class2 = a(class2, object4.getClass());
				}
			}

			return class2;
		}
	}

	private static Class<?> a(Class<?> class1, Class<?> class2) {
		while (!class1.isAssignableFrom(class2)) {
			class1 = class1.getSuperclass();
		}

		return class1;
	}
}
