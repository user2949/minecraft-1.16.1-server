import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public abstract class cfl<O, S> {
	private static final Function<Entry<cgl<?>, Comparable<?>>, String> a = new Function<Entry<cgl<?>, Comparable<?>>, String>() {
		public String apply(@Nullable Entry<cgl<?>, Comparable<?>> entry) {
			if (entry == null) {
				return "<NULL>";
			} else {
				cgl<?> cgl3 = (cgl<?>)entry.getKey();
				return cgl3.f() + "=" + this.a(cgl3, (Comparable<?>)entry.getValue());
			}
		}

		private <T extends Comparable<T>> String a(cgl<T> cgl, Comparable<?> comparable) {
			return cgl.a((T)comparable);
		}
	};
	protected final O c;
	private final ImmutableMap<cgl<?>, Comparable<?>> b;
	private Table<cgl<?>, Comparable<?>, S> e;
	protected final MapCodec<S> d;

	protected cfl(O object, ImmutableMap<cgl<?>, Comparable<?>> immutableMap, MapCodec<S> mapCodec) {
		this.c = object;
		this.b = immutableMap;
		this.d = mapCodec;
	}

	public <T extends Comparable<T>> S a(cgl<T> cgl) {
		return this.a(cgl, a(cgl.a(), this.c(cgl)));
	}

	protected static <T> T a(Collection<T> collection, T object) {
		Iterator<T> iterator3 = collection.iterator();

		while (iterator3.hasNext()) {
			if (iterator3.next().equals(object)) {
				if (iterator3.hasNext()) {
					return (T)iterator3.next();
				}

				return (T)collection.iterator().next();
			}
		}

		return (T)iterator3.next();
	}

	public String toString() {
		StringBuilder stringBuilder2 = new StringBuilder();
		stringBuilder2.append(this.c);
		if (!this.s().isEmpty()) {
			stringBuilder2.append('[');
			stringBuilder2.append((String)this.s().entrySet().stream().map(a).collect(Collectors.joining(",")));
			stringBuilder2.append(']');
		}

		return stringBuilder2.toString();
	}

	public Collection<cgl<?>> r() {
		return Collections.unmodifiableCollection(this.b.keySet());
	}

	public <T extends Comparable<T>> boolean b(cgl<T> cgl) {
		return this.b.containsKey(cgl);
	}

	public <T extends Comparable<T>> T c(cgl<T> cgl) {
		Comparable<?> comparable3 = this.b.get(cgl);
		if (comparable3 == null) {
			throw new IllegalArgumentException("Cannot get property " + cgl + " as it does not exist in " + this.c);
		} else {
			return (T)cgl.g().cast(comparable3);
		}
	}

	public <T extends Comparable<T>> Optional<T> d(cgl<T> cgl) {
		Comparable<?> comparable3 = this.b.get(cgl);
		return comparable3 == null ? Optional.empty() : Optional.of(cgl.g().cast(comparable3));
	}

	public <T extends Comparable<T>, V extends T> S a(cgl<T> cgl, V comparable) {
		Comparable<?> comparable4 = this.b.get(cgl);
		if (comparable4 == null) {
			throw new IllegalArgumentException("Cannot set property " + cgl + " as it does not exist in " + this.c);
		} else if (comparable4 == comparable) {
			return (S)this;
		} else {
			S object5 = this.e.get(cgl, comparable);
			if (object5 == null) {
				throw new IllegalArgumentException("Cannot set property " + cgl + " to " + comparable + " on " + this.c + ", it is not an allowed value");
			} else {
				return object5;
			}
		}
	}

	public void a(Map<Map<cgl<?>, Comparable<?>>, S> map) {
		if (this.e != null) {
			throw new IllegalStateException();
		} else {
			Table<cgl<?>, Comparable<?>, S> table3 = HashBasedTable.create();

			for (Entry<cgl<?>, Comparable<?>> entry5 : this.b.entrySet()) {
				cgl<?> cgl6 = (cgl<?>)entry5.getKey();

				for (Comparable<?> comparable8 : cgl6.a()) {
					if (comparable8 != entry5.getValue()) {
						table3.put(cgl6, comparable8, (S)map.get(this.b(cgl6, comparable8)));
					}
				}
			}

			this.e = (Table<cgl<?>, Comparable<?>, S>)(table3.isEmpty() ? table3 : ArrayTable.create(table3));
		}
	}

	private Map<cgl<?>, Comparable<?>> b(cgl<?> cgl, Comparable<?> comparable) {
		Map<cgl<?>, Comparable<?>> map4 = Maps.<cgl<?>, Comparable<?>>newHashMap(this.b);
		map4.put(cgl, comparable);
		return map4;
	}

	public ImmutableMap<cgl<?>, Comparable<?>> s() {
		return this.b;
	}

	protected static <O, S extends cfl<O, S>> Codec<S> a(Codec<O> codec, Function<O, S> function) {
		return codec.dispatch("Name", cfl -> cfl.c, object -> {
			S cfl3 = (S)function.apply(object);
			return cfl3.s().isEmpty() ? Codec.unit(cfl3) : cfl3.d.fieldOf("Properties").codec();
		});
	}
}
