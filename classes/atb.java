import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class atb<U> {
	protected final List<atb.a<U>> a;
	private final Random b = new Random();

	public atb() {
		this(Lists.<atb.a<U>>newArrayList());
	}

	private atb(List<atb.a<U>> list) {
		this.a = Lists.<atb.a<U>>newArrayList(list);
	}

	public static <U> Codec<atb<U>> a(Codec<U> codec) {
		return atb.a.a(codec).listOf().xmap(atb::new, atb -> atb.a);
	}

	public atb<U> a(U object, int integer) {
		this.a.add(new atb.a(object, integer));
		return this;
	}

	public atb<U> a() {
		return this.a(this.b);
	}

	public atb<U> a(Random random) {
		this.a.forEach(a -> a.a(random.nextFloat()));
		this.a.sort(Comparator.comparingDouble(object -> ((atb.a)object).c()));
		return this;
	}

	public boolean b() {
		return this.a.isEmpty();
	}

	public Stream<U> c() {
		return this.a.stream().map(atb.a::a);
	}

	public U b(Random random) {
		return (U)this.a(random).c().findFirst().orElseThrow(RuntimeException::new);
	}

	public String toString() {
		return "WeightedList[" + this.a + "]";
	}

	public static class a<T> {
		private final T a;
		private final int b;
		private double c;

		private a(T object, int integer) {
			this.b = integer;
			this.a = object;
		}

		private double c() {
			return this.c;
		}

		private void a(float float1) {
			this.c = -Math.pow((double)float1, (double)(1.0F / (float)this.b));
		}

		public T a() {
			return this.a;
		}

		public String toString() {
			return "" + this.b + ":" + this.a;
		}

		public static <E> Codec<atb.a<E>> a(Codec<E> codec) {
			return new Codec<atb.a<E>>() {
				@Override
				public <T> DataResult<Pair<atb.a<E>, T>> decode(DynamicOps<T> dynamicOps, T object) {
					Dynamic<T> dynamic4 = new Dynamic<>(dynamicOps, object);
					return dynamic4.get("data")
						.flatMap(codec::parse)
						.map(objectx -> new atb.a(objectx, dynamic4.get("weight").asInt(1)))
						.map(a -> Pair.of(a, dynamicOps.empty()));
				}

				public <T> DataResult<T> encode(atb.a<E> a, DynamicOps<T> dynamicOps, T object) {
					return dynamicOps.mapBuilder().add("weight", dynamicOps.createInt(a.b)).add("data", codec.encodeStart(dynamicOps, a.a)).build(object);
				}
			};
		}
	}
}
