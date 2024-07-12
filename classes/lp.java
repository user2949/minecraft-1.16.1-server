import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.PeekingIterator;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.RecordBuilder.AbstractStringBuilder;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class lp implements DynamicOps<lu> {
	public static final lp a = new lp();

	protected lp() {
	}

	public lu empty() {
		return lg.b;
	}

	public <U> U convertTo(DynamicOps<U> dynamicOps, lu lu) {
		switch (lu.a()) {
			case 0:
				return dynamicOps.empty();
			case 1:
				return dynamicOps.createByte(((lr)lu).h());
			case 2:
				return dynamicOps.createShort(((lr)lu).g());
			case 3:
				return dynamicOps.createInt(((lr)lu).f());
			case 4:
				return dynamicOps.createLong(((lr)lu).e());
			case 5:
				return dynamicOps.createFloat(((lr)lu).j());
			case 6:
				return dynamicOps.createDouble(((lr)lu).i());
			case 7:
				return dynamicOps.createByteList(ByteBuffer.wrap(((lb)lu).d()));
			case 8:
				return dynamicOps.createString(lu.f_());
			case 9:
				return this.convertList(dynamicOps, lu);
			case 10:
				return this.convertMap(dynamicOps, lu);
			case 11:
				return dynamicOps.createIntList(Arrays.stream(((li)lu).g()));
			case 12:
				return dynamicOps.createLongList(Arrays.stream(((ll)lu).g()));
			default:
				throw new IllegalStateException("Unknown tag type: " + lu);
		}
	}

	public DataResult<Number> getNumberValue(lu lu) {
		return lu instanceof lr ? DataResult.success(((lr)lu).k()) : DataResult.error("Not a number");
	}

	public lu createNumeric(Number number) {
		return lf.a(number.doubleValue());
	}

	public lu createByte(byte byte1) {
		return lc.a(byte1);
	}

	public lu createShort(short short1) {
		return ls.a(short1);
	}

	public lu createInt(int integer) {
		return lj.a(integer);
	}

	public lu createLong(long long1) {
		return lm.a(long1);
	}

	public lu createFloat(float float1) {
		return lh.a(float1);
	}

	public lu createDouble(double double1) {
		return lf.a(double1);
	}

	public lu createBoolean(boolean boolean1) {
		return lc.a(boolean1);
	}

	public DataResult<String> getStringValue(lu lu) {
		return lu instanceof lt ? DataResult.success(lu.f_()) : DataResult.error("Not a string");
	}

	public lu createString(String string) {
		return lt.a(string);
	}

	private static ld<?> a(byte byte1, byte byte2) {
		if (a(byte1, byte2, (byte)4)) {
			return new ll(new long[0]);
		} else if (a(byte1, byte2, (byte)1)) {
			return new lb(new byte[0]);
		} else {
			return (ld<?>)(a(byte1, byte2, (byte)3) ? new li(new int[0]) : new lk());
		}
	}

	private static boolean a(byte byte1, byte byte2, byte byte3) {
		return (byte1 == byte3 || byte1 == 0) && (byte2 == byte3 || byte2 == 0);
	}

	private static <T extends lu> void a(ld<T> ld, lu lu2, lu lu3) {
		if (lu2 instanceof ld) {
			ld<?> ld4 = (ld<?>)lu2;
			ld4.forEach(lu -> ld.add(lu));
		}

		ld.add(lu3);
	}

	private static <T extends lu> void a(ld<T> ld, lu lu, List<lu> list) {
		if (lu instanceof ld) {
			ld<?> ld4 = (ld<?>)lu;
			ld4.forEach(lux -> ld.add(lux));
		}

		list.forEach(lux -> ld.add(lux));
	}

	public DataResult<lu> mergeToList(lu lu1, lu lu2) {
		if (!(lu1 instanceof ld) && !(lu1 instanceof lg)) {
			return DataResult.error("mergeToList called with not a list: " + lu1, lu1);
		} else {
			ld<?> ld4 = a(lu1 instanceof ld ? ((ld)lu1).d_() : 0, lu2.a());
			a(ld4, lu1, lu2);
			return DataResult.success(ld4);
		}
	}

	public DataResult<lu> mergeToList(lu lu, List<lu> list) {
		if (!(lu instanceof ld) && !(lu instanceof lg)) {
			return DataResult.error("mergeToList called with not a list: " + lu, lu);
		} else {
			ld<?> ld4 = a(lu instanceof ld ? ((ld)lu).d_() : 0, (Byte)list.stream().findFirst().map(lu::a).orElse((byte)0));
			a(ld4, lu, list);
			return DataResult.success(ld4);
		}
	}

	public DataResult<lu> mergeToMap(lu lu1, lu lu2, lu lu3) {
		if (!(lu1 instanceof le) && !(lu1 instanceof lg)) {
			return DataResult.error("mergeToMap called with not a map: " + lu1, lu1);
		} else if (!(lu2 instanceof lt)) {
			return DataResult.error("key is not a string: " + lu2, lu1);
		} else {
			le le5 = new le();
			if (lu1 instanceof le) {
				le le6 = (le)lu1;
				le6.d().forEach(string -> le5.a(string, le6.c(string)));
			}

			le5.a(lu2.f_(), lu3);
			return DataResult.success(le5);
		}
	}

	public DataResult<lu> mergeToMap(lu lu, MapLike<lu> mapLike) {
		if (!(lu instanceof le) && !(lu instanceof lg)) {
			return DataResult.error("mergeToMap called with not a map: " + lu, lu);
		} else {
			le le4 = new le();
			if (lu instanceof le) {
				le le5 = (le)lu;
				le5.d().forEach(string -> le4.a(string, le5.c(string)));
			}

			List<lu> list5 = Lists.<lu>newArrayList();
			mapLike.entries().forEach(pair -> {
				lu lu4 = (lu)pair.getFirst();
				if (!(lu4 instanceof lt)) {
					list5.add(lu4);
				} else {
					le4.a(lu4.f_(), (lu)pair.getSecond());
				}
			});
			return !list5.isEmpty() ? DataResult.error("some keys are not strings: " + list5, le4) : DataResult.success(le4);
		}
	}

	public DataResult<Stream<Pair<lu, lu>>> getMapValues(lu lu) {
		if (!(lu instanceof le)) {
			return DataResult.error("Not a map: " + lu);
		} else {
			le le3 = (le)lu;
			return DataResult.success(le3.d().stream().map(string -> Pair.of(this.a(string), le3.c(string))));
		}
	}

	public DataResult<Consumer<BiConsumer<lu, lu>>> getMapEntries(lu lu) {
		if (!(lu instanceof le)) {
			return DataResult.error("Not a map: " + lu);
		} else {
			le le3 = (le)lu;
			return DataResult.success(biConsumer -> le3.d().forEach(string -> biConsumer.accept(this.a(string), le3.c(string))));
		}
	}

	public DataResult<MapLike<lu>> getMap(lu lu) {
		if (!(lu instanceof le)) {
			return DataResult.error("Not a map: " + lu);
		} else {
			final le le3 = (le)lu;
			return DataResult.success(new MapLike<lu>() {
				@Nullable
				public lu get(lu lu) {
					return le3.c(lu.f_());
				}

				@Nullable
				public lu get(String string) {
					return le3.c(string);
				}

				@Override
				public Stream<Pair<lu, lu>> entries() {
					return le3.d().stream().map(string -> Pair.of(lp.this.a(string), le3.c(string)));
				}

				public String toString() {
					return "MapLike[" + le3 + "]";
				}
			});
		}
	}

	public lu createMap(Stream<Pair<lu, lu>> stream) {
		le le3 = new le();
		stream.forEach(pair -> le3.a(((lu)pair.getFirst()).f_(), (lu)pair.getSecond()));
		return le3;
	}

	public DataResult<Stream<lu>> getStream(lu lu) {
		return lu instanceof ld ? DataResult.success(((ld)lu).stream().map(lux -> lux)) : DataResult.error("Not a list");
	}

	public DataResult<Consumer<Consumer<lu>>> getList(lu lu) {
		if (lu instanceof ld) {
			ld<?> ld3 = (ld<?>)lu;
			return DataResult.success(ld3::forEach);
		} else {
			return DataResult.error("Not a list: " + lu);
		}
	}

	public DataResult<ByteBuffer> getByteBuffer(lu lu) {
		return lu instanceof lb ? DataResult.success(ByteBuffer.wrap(((lb)lu).d())) : DynamicOps.super.getByteBuffer(lu);
	}

	public lu createByteList(ByteBuffer byteBuffer) {
		return new lb(DataFixUtils.toArray(byteBuffer));
	}

	public DataResult<IntStream> getIntStream(lu lu) {
		return lu instanceof li ? DataResult.success(Arrays.stream(((li)lu).g())) : DynamicOps.super.getIntStream(lu);
	}

	public lu createIntList(IntStream intStream) {
		return new li(intStream.toArray());
	}

	public DataResult<LongStream> getLongStream(lu lu) {
		return lu instanceof ll ? DataResult.success(Arrays.stream(((ll)lu).g())) : DynamicOps.super.getLongStream(lu);
	}

	public lu createLongList(LongStream longStream) {
		return new ll(longStream.toArray());
	}

	public lu createList(Stream<lu> stream) {
		PeekingIterator<lu> peekingIterator3 = Iterators.peekingIterator(stream.iterator());
		if (!peekingIterator3.hasNext()) {
			return new lk();
		} else {
			lu lu4 = peekingIterator3.peek();
			if (lu4 instanceof lc) {
				List<Byte> list5 = Lists.<Byte>newArrayList(Iterators.transform(peekingIterator3, lu -> ((lc)lu).h()));
				return new lb(list5);
			} else if (lu4 instanceof lj) {
				List<Integer> list5 = Lists.<Integer>newArrayList(Iterators.transform(peekingIterator3, lu -> ((lj)lu).f()));
				return new li(list5);
			} else if (lu4 instanceof lm) {
				List<Long> list5 = Lists.<Long>newArrayList(Iterators.transform(peekingIterator3, lu -> ((lm)lu).e()));
				return new ll(list5);
			} else {
				lk lk5 = new lk();

				while (peekingIterator3.hasNext()) {
					lu lu6 = peekingIterator3.next();
					if (!(lu6 instanceof lg)) {
						lk5.add(lu6);
					}
				}

				return lk5;
			}
		}
	}

	public lu remove(lu lu, String string) {
		if (lu instanceof le) {
			le le4 = (le)lu;
			le le5 = new le();
			le4.d().stream().filter(string2 -> !Objects.equals(string2, string)).forEach(stringx -> le5.a(stringx, le4.c(stringx)));
			return le5;
		} else {
			return lu;
		}
	}

	public String toString() {
		return "NBT";
	}

	@Override
	public RecordBuilder<lu> mapBuilder() {
		return new lp.a();
	}

	class a extends AbstractStringBuilder<lu, le> {
		protected a() {
			super(lp.this);
		}

		protected le initBuilder() {
			return new le();
		}

		protected le append(String string, lu lu, le le) {
			le.a(string, lu);
			return le;
		}

		protected DataResult<lu> build(le le, lu lu) {
			if (lu == null || lu == lg.b) {
				return DataResult.success(le);
			} else if (!(lu instanceof le)) {
				return DataResult.error("mergeToMap called with not a map: " + lu, lu);
			} else {
				le le4 = new le(Maps.<String, lu>newHashMap(((le)lu).h()));

				for (Entry<String, lu> entry6 : le.h().entrySet()) {
					le4.a((String)entry6.getKey(), (lu)entry6.getValue());
				}

				return DataResult.success(le4);
			}
		}
	}
}
