import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class cfk<O, S extends cfl<O, S>> {
	private static final Pattern a = Pattern.compile("^[a-z0-9_]+$");
	private final O b;
	private final ImmutableSortedMap<String, cgl<?>> c;
	private final ImmutableList<S> d;

	protected cfk(Function<O, S> function, O object, cfk.b<O, S> b, Map<String, cgl<?>> map) {
		this.b = object;
		this.c = ImmutableSortedMap.copyOf(map);
		Supplier<S> supplier6 = () -> (cfl)function.apply(object);
		MapCodec<S> mapCodec7 = MapCodec.of(Encoder.empty(), Decoder.unit(supplier6));

		for (Entry<String, cgl<?>> entry9 : this.c.entrySet()) {
			mapCodec7 = a(mapCodec7, supplier6, (String)entry9.getKey(), (cgl)entry9.getValue());
		}

		MapCodec<S> mapCodec8 = mapCodec7;
		Map<Map<cgl<?>, Comparable<?>>, S> map9 = Maps.<Map<cgl<?>, Comparable<?>>, S>newLinkedHashMap();
		List<S> list10 = Lists.<S>newArrayList();
		Stream<List<Pair<cgl<?>, Comparable<?>>>> stream11 = Stream.of(Collections.emptyList());

		for (cgl<?> cgl13 : this.c.values()) {
			stream11 = stream11.flatMap(list -> cgl13.a().stream().map(comparable -> {
					List<Pair<cgl<?>, Comparable<?>>> list4 = Lists.<Pair<cgl<?>, Comparable<?>>>newArrayList(list);
					list4.add(Pair.of(cgl13, comparable));
					return list4;
				}));
		}

		stream11.forEach(
			list6 -> {
				ImmutableMap<cgl<?>, Comparable<?>> immutableMap7 = (ImmutableMap<cgl<?>, Comparable<?>>)list6.stream()
					.collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
				S cfl8 = b.create(object, immutableMap7, mapCodec8);
				map9.put(immutableMap7, cfl8);
				list10.add(cfl8);
			}
		);

		for (S cfl13 : list10) {
			cfl13.a(map9);
		}

		this.d = ImmutableList.copyOf(list10);
	}

	private static <S extends cfl<?, S>, T extends Comparable<T>> MapCodec<S> a(MapCodec<S> mapCodec, Supplier<S> supplier, String string, cgl<T> cgl) {
		return Codec.mapPair(mapCodec, adl.a(cgl.e().fieldOf(string), () -> cgl.a((cfl<?, ?>)supplier.get())))
			.xmap(pair -> (cfl)((cfl)pair.getFirst()).a(cgl, ((cgl.a)pair.getSecond()).b()), cfl -> Pair.of(cfl, cgl.a(cfl)));
	}

	public ImmutableList<S> a() {
		return this.d;
	}

	public S b() {
		return (S)this.d.get(0);
	}

	public O c() {
		return this.b;
	}

	public Collection<cgl<?>> d() {
		return this.c.values();
	}

	public String toString() {
		return MoreObjects.toStringHelper(this).add("block", this.b).add("properties", this.c.values().stream().map(cgl::f).collect(Collectors.toList())).toString();
	}

	@Nullable
	public cgl<?> a(String string) {
		return this.c.get(string);
	}

	public static class a<O, S extends cfl<O, S>> {
		private final O a;
		private final Map<String, cgl<?>> b = Maps.<String, cgl<?>>newHashMap();

		public a(O object) {
			this.a = object;
		}

		public cfk.a<O, S> a(cgl<?>... arr) {
			for (cgl<?> cgl6 : arr) {
				this.a(cgl6);
				this.b.put(cgl6.f(), cgl6);
			}

			return this;
		}

		private <T extends Comparable<T>> void a(cgl<T> cgl) {
			String string3 = cgl.f();
			if (!cfk.a.matcher(string3).matches()) {
				throw new IllegalArgumentException(this.a + " has invalidly named property: " + string3);
			} else {
				Collection<T> collection4 = cgl.a();
				if (collection4.size() <= 1) {
					throw new IllegalArgumentException(this.a + " attempted use property " + string3 + " with <= 1 possible values");
				} else {
					for (T comparable6 : collection4) {
						String string7 = cgl.a(comparable6);
						if (!cfk.a.matcher(string7).matches()) {
							throw new IllegalArgumentException(this.a + " has property: " + string3 + " with invalidly named value: " + string7);
						}
					}

					if (this.b.containsKey(string3)) {
						throw new IllegalArgumentException(this.a + " has duplicate property: " + string3);
					}
				}
			}
		}

		public cfk<O, S> a(Function<O, S> function, cfk.b<O, S> b) {
			return new cfk<>(function, this.a, b, this.b);
		}
	}

	public interface b<O, S> {
		S create(O object, ImmutableMap<cgl<?>, Comparable<?>> immutableMap, MapCodec<S> mapCodec);
	}
}
