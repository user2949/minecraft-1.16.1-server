import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class ge<T> implements gd<T> {
	private int a;
	private final IdentityHashMap<T, Integer> b;
	private final List<T> c;

	public ge() {
		this(512);
	}

	public ge(int integer) {
		this.c = Lists.<T>newArrayListWithExpectedSize(integer);
		this.b = new IdentityHashMap(integer);
	}

	public void a(T object, int integer) {
		this.b.put(object, integer);

		while (this.c.size() <= integer) {
			this.c.add(null);
		}

		this.c.set(integer, object);
		if (this.a <= integer) {
			this.a = integer + 1;
		}
	}

	public void b(T object) {
		this.a(object, this.a);
	}

	public int a(T object) {
		Integer integer3 = (Integer)this.b.get(object);
		return integer3 == null ? -1 : integer3;
	}

	@Nullable
	@Override
	public final T a(int integer) {
		return (T)(integer >= 0 && integer < this.c.size() ? this.c.get(integer) : null);
	}

	public Iterator<T> iterator() {
		return Iterators.filter(this.c.iterator(), Predicates.notNull());
	}

	public int a() {
		return this.b.size();
	}
}
