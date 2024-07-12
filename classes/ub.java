import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class ub<T> implements DynamicOps<T> {
	protected final DynamicOps<T> a;

	protected ub(DynamicOps<T> dynamicOps) {
		this.a = dynamicOps;
	}

	@Override
	public T empty() {
		return this.a.empty();
	}

	@Override
	public <U> U convertTo(DynamicOps<U> dynamicOps, T object) {
		return this.a.convertTo(dynamicOps, object);
	}

	@Override
	public DataResult<Number> getNumberValue(T object) {
		return this.a.getNumberValue(object);
	}

	@Override
	public T createNumeric(Number number) {
		return this.a.createNumeric(number);
	}

	@Override
	public T createByte(byte byte1) {
		return this.a.createByte(byte1);
	}

	@Override
	public T createShort(short short1) {
		return this.a.createShort(short1);
	}

	@Override
	public T createInt(int integer) {
		return this.a.createInt(integer);
	}

	@Override
	public T createLong(long long1) {
		return this.a.createLong(long1);
	}

	@Override
	public T createFloat(float float1) {
		return this.a.createFloat(float1);
	}

	@Override
	public T createDouble(double double1) {
		return this.a.createDouble(double1);
	}

	@Override
	public DataResult<Boolean> getBooleanValue(T object) {
		return this.a.getBooleanValue(object);
	}

	@Override
	public T createBoolean(boolean boolean1) {
		return this.a.createBoolean(boolean1);
	}

	@Override
	public DataResult<String> getStringValue(T object) {
		return this.a.getStringValue(object);
	}

	@Override
	public T createString(String string) {
		return this.a.createString(string);
	}

	@Override
	public DataResult<T> mergeToList(T object1, T object2) {
		return this.a.mergeToList(object1, object2);
	}

	@Override
	public DataResult<T> mergeToList(T object, List<T> list) {
		return this.a.mergeToList(object, list);
	}

	@Override
	public DataResult<T> mergeToMap(T object1, T object2, T object3) {
		return this.a.mergeToMap(object1, object2, object3);
	}

	@Override
	public DataResult<T> mergeToMap(T object, MapLike<T> mapLike) {
		return this.a.mergeToMap(object, mapLike);
	}

	@Override
	public DataResult<Stream<Pair<T, T>>> getMapValues(T object) {
		return this.a.getMapValues(object);
	}

	@Override
	public DataResult<Consumer<BiConsumer<T, T>>> getMapEntries(T object) {
		return this.a.getMapEntries(object);
	}

	@Override
	public T createMap(Stream<Pair<T, T>> stream) {
		return this.a.createMap(stream);
	}

	@Override
	public DataResult<MapLike<T>> getMap(T object) {
		return this.a.getMap(object);
	}

	@Override
	public DataResult<Stream<T>> getStream(T object) {
		return this.a.getStream(object);
	}

	@Override
	public DataResult<Consumer<Consumer<T>>> getList(T object) {
		return this.a.getList(object);
	}

	@Override
	public T createList(Stream<T> stream) {
		return this.a.createList(stream);
	}

	@Override
	public DataResult<ByteBuffer> getByteBuffer(T object) {
		return this.a.getByteBuffer(object);
	}

	@Override
	public T createByteList(ByteBuffer byteBuffer) {
		return this.a.createByteList(byteBuffer);
	}

	@Override
	public DataResult<IntStream> getIntStream(T object) {
		return this.a.getIntStream(object);
	}

	@Override
	public T createIntList(IntStream intStream) {
		return this.a.createIntList(intStream);
	}

	@Override
	public DataResult<LongStream> getLongStream(T object) {
		return this.a.getLongStream(object);
	}

	@Override
	public T createLongList(LongStream longStream) {
		return this.a.createLongList(longStream);
	}

	@Override
	public T remove(T object, String string) {
		return this.a.remove(object, string);
	}

	@Override
	public boolean compressMaps() {
		return this.a.compressMaps();
	}

	@Override
	public ListBuilder<T> listBuilder() {
		return this.a.listBuilder();
	}

	@Override
	public RecordBuilder<T> mapBuilder() {
		return this.a.mapBuilder();
	}
}
