import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class adl {
	private static Function<Integer, DataResult<Integer>> b(int integer1, int integer2) {
		return integer -> integer >= integer1 && integer <= integer2
				? DataResult.success(integer)
				: DataResult.error("Value " + integer + " outside of range [" + integer1 + ":" + integer2 + "]", integer);
	}

	public static Codec<Integer> a(int integer1, int integer2) {
		Function<Integer, DataResult<Integer>> function3 = b(integer1, integer2);
		return Codec.INT.flatXmap(function3, function3);
	}

	private static Function<Double, DataResult<Double>> b(double double1, double double2) {
		return double3 -> double3 >= double1 && double3 <= double2
				? DataResult.success(double3)
				: DataResult.error("Value " + double3 + " outside of range [" + double1 + ":" + double2 + "]", double3);
	}

	public static Codec<Double> a(double double1, double double2) {
		Function<Double, DataResult<Double>> function5 = b(double1, double2);
		return Codec.DOUBLE.flatXmap(function5, function5);
	}

	public static <T> MapCodec<Pair<ug<T>, T>> a(ug<gl<T>> ug, MapCodec<T> mapCodec) {
		return Codec.mapPair(uh.a.<ug<T>>xmap(ug.a(ug), ug::a).fieldOf("name"), mapCodec);
	}

	private static <A> MapCodec<A> a(MapCodec<A> mapCodec, adl.a<A> a) {
		return new MapCodec<A>() {
			@Override
			public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
				return mapCodec.keys(dynamicOps);
			}

			@Override
			public <T> RecordBuilder<T> encode(A object, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
				return a.a(dynamicOps, object, mapCodec.encode(object, dynamicOps, recordBuilder));
			}

			@Override
			public <T> DataResult<A> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
				return a.a(dynamicOps, mapLike, mapCodec.decode(dynamicOps, mapLike));
			}

			public String toString() {
				return mapCodec + "[mapResult " + a + "]";
			}
		};
	}

	public static <A> MapCodec<A> a(MapCodec<A> mapCodec, Consumer<String> consumer, Supplier<? extends A> supplier) {
		return a(mapCodec, new adl.a<A>() {
			@Override
			public <T> DataResult<A> a(DynamicOps<T> dynamicOps, MapLike<T> mapLike, DataResult<A> dataResult) {
				return DataResult.success((A)dataResult.resultOrPartial(consumer).orElseGet(supplier));
			}

			@Override
			public <T> RecordBuilder<T> a(DynamicOps<T> dynamicOps, A object, RecordBuilder<T> recordBuilder) {
				return recordBuilder;
			}

			public String toString() {
				return "WithDefault[" + supplier.get() + "]";
			}
		});
	}

	public static <A> MapCodec<A> a(MapCodec<A> mapCodec, Supplier<A> supplier) {
		return a(mapCodec, new adl.a<A>() {
			@Override
			public <T> DataResult<A> a(DynamicOps<T> dynamicOps, MapLike<T> mapLike, DataResult<A> dataResult) {
				return dataResult.setPartial(supplier);
			}

			@Override
			public <T> RecordBuilder<T> a(DynamicOps<T> dynamicOps, A object, RecordBuilder<T> recordBuilder) {
				return recordBuilder;
			}

			public String toString() {
				return "SetPartial[" + supplier + "]";
			}
		});
	}

	interface a<A> {
		<T> DataResult<A> a(DynamicOps<T> dynamicOps, MapLike<T> mapLike, DataResult<A> dataResult);

		<T> RecordBuilder<T> a(DynamicOps<T> dynamicOps, A object, RecordBuilder<T> recordBuilder);
	}
}
