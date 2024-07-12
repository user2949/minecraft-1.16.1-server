import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface aeh {
	String a();

	static <E extends Enum<E> & aeh> Codec<E> a(Supplier<E[]> supplier, Function<? super String, ? extends E> function) {
		E[] arr3 = (E[])supplier.get();
		return a(Enum::ordinal, integer -> arr3[integer], function);
	}

	static <E extends aeh> Codec<E> a(ToIntFunction<E> toIntFunction, IntFunction<E> intFunction, Function<? super String, ? extends E> function) {
		return new Codec<E>() {
			public <T> DataResult<T> encode(E aeh, DynamicOps<T> dynamicOps, T object) {
				return dynamicOps.compressMaps()
					? dynamicOps.mergeToPrimitive(object, dynamicOps.createInt(toIntFunction.applyAsInt(aeh)))
					: dynamicOps.mergeToPrimitive(object, dynamicOps.createString(aeh.a()));
			}

			@Override
			public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> dynamicOps, T object) {
				return dynamicOps.compressMaps()
					? dynamicOps.getNumberValue(object)
						.flatMap(
							number -> (DataResult)Optional.ofNullable(intFunction.apply(number.intValue()))
									.map(DataResult::success)
									.orElseGet(() -> DataResult.error("Unknown element id: " + number))
						)
						.map(aeh -> Pair.of(aeh, dynamicOps.empty()))
					: dynamicOps.getStringValue(object)
						.flatMap(
							string -> (DataResult)Optional.ofNullable(function.apply(string))
									.map(DataResult::success)
									.orElseGet(() -> DataResult.error("Unknown element name: " + string))
						)
						.map(aeh -> Pair.of(aeh, dynamicOps.empty()));
			}

			public String toString() {
				return "StringRepresentable[" + toIntFunction + "]";
			}
		};
	}

	static Keyable a(aeh[] arr) {
		return new Keyable() {
			@Override
			public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
				return dynamicOps.compressMaps()
					? IntStream.range(0, arr.length).mapToObj(dynamicOps::createInt)
					: Arrays.stream(arr).map(aeh::a).map(dynamicOps::createString);
			}
		};
	}
}
