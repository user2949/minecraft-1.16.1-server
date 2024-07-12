import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class yq<T> {
	public static final int a = yp.a + 2;
	private final List<Long2ObjectLinkedOpenHashMap<List<Optional<T>>>> b = (List<Long2ObjectLinkedOpenHashMap<List<Optional<T>>>>)IntStream.range(0, a)
		.mapToObj(integerx -> new Long2ObjectLinkedOpenHashMap())
		.collect(Collectors.toList());
	private volatile int c = a;
	private final String d;
	private final LongSet e = new LongOpenHashSet();
	private final int f;

	public yq(String string, int integer) {
		this.d = string;
		this.f = integer;
	}

	protected void a(int integer1, bph bph, int integer3) {
		if (integer1 < a) {
			Long2ObjectLinkedOpenHashMap<List<Optional<T>>> long2ObjectLinkedOpenHashMap5 = (Long2ObjectLinkedOpenHashMap<List<Optional<T>>>)this.b.get(integer1);
			List<Optional<T>> list6 = long2ObjectLinkedOpenHashMap5.remove(bph.a());
			if (integer1 == this.c) {
				while (this.c < a && ((Long2ObjectLinkedOpenHashMap)this.b.get(this.c)).isEmpty()) {
					this.c++;
				}
			}

			if (list6 != null && !list6.isEmpty()) {
				((List)((Long2ObjectLinkedOpenHashMap)this.b.get(integer3)).computeIfAbsent(bph.a(), long1 -> Lists.newArrayList())).addAll(list6);
				this.c = Math.min(this.c, integer3);
			}
		}
	}

	protected void a(Optional<T> optional, long long2, int integer) {
		((List)((Long2ObjectLinkedOpenHashMap)this.b.get(integer)).computeIfAbsent(long2, long1 -> Lists.newArrayList())).add(optional);
		this.c = Math.min(this.c, integer);
	}

	protected void a(long long1, boolean boolean2) {
		for (Long2ObjectLinkedOpenHashMap<List<Optional<T>>> long2ObjectLinkedOpenHashMap6 : this.b) {
			List<Optional<T>> list7 = long2ObjectLinkedOpenHashMap6.get(long1);
			if (list7 != null) {
				if (boolean2) {
					list7.clear();
				} else {
					list7.removeIf(optional -> !optional.isPresent());
				}

				if (list7.isEmpty()) {
					long2ObjectLinkedOpenHashMap6.remove(long1);
				}
			}
		}

		while (this.c < a && ((Long2ObjectLinkedOpenHashMap)this.b.get(this.c)).isEmpty()) {
			this.c++;
		}

		this.e.remove(long1);
	}

	private Runnable a(long long1) {
		return () -> this.e.add(long1);
	}

	@Nullable
	public Stream<Either<T, Runnable>> a() {
		if (this.e.size() >= this.f) {
			return null;
		} else if (this.c >= a) {
			return null;
		} else {
			int integer2 = this.c;
			Long2ObjectLinkedOpenHashMap<List<Optional<T>>> long2ObjectLinkedOpenHashMap3 = (Long2ObjectLinkedOpenHashMap<List<Optional<T>>>)this.b.get(integer2);
			long long4 = long2ObjectLinkedOpenHashMap3.firstLongKey();
			List<Optional<T>> list6 = long2ObjectLinkedOpenHashMap3.removeFirst();

			while (this.c < a && ((Long2ObjectLinkedOpenHashMap)this.b.get(this.c)).isEmpty()) {
				this.c++;
			}

			return list6.stream().map(optional -> (Either)optional.map(Either::left).orElseGet(() -> Either.right(this.a(long4))));
		}
	}

	public String toString() {
		return this.d + " " + this.c + "...";
	}

	@VisibleForTesting
	LongSet b() {
		return new LongOpenHashSet(this.e);
	}
}
