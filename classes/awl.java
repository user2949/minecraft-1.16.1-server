import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class awl {
	private final Map<UUID, awl.a> a = Maps.<UUID, awl.a>newHashMap();

	public void b() {
		Iterator<awl.a> iterator2 = this.a.values().iterator();

		while (iterator2.hasNext()) {
			awl.a a3 = (awl.a)iterator2.next();
			a3.a();
			if (a3.b()) {
				iterator2.remove();
			}
		}
	}

	private Stream<awl.b> c() {
		return this.a.entrySet().stream().flatMap(entry -> ((awl.a)entry.getValue()).a((UUID)entry.getKey()));
	}

	private Collection<awl.b> a(Random random, int integer) {
		List<awl.b> list4 = (List<awl.b>)this.c().collect(Collectors.toList());
		if (list4.isEmpty()) {
			return Collections.emptyList();
		} else {
			int[] arr5 = new int[list4.size()];
			int integer6 = 0;

			for (int integer7 = 0; integer7 < list4.size(); integer7++) {
				awl.b b8 = (awl.b)list4.get(integer7);
				integer6 += Math.abs(b8.a());
				arr5[integer7] = integer6 - 1;
			}

			Set<awl.b> set7 = Sets.newIdentityHashSet();

			for (int integer8 = 0; integer8 < integer; integer8++) {
				int integer9 = random.nextInt(integer6);
				int integer10 = Arrays.binarySearch(arr5, integer9);
				set7.add(list4.get(integer10 < 0 ? -integer10 - 1 : integer10));
			}

			return set7;
		}
	}

	private awl.a a(UUID uUID) {
		return (awl.a)this.a.computeIfAbsent(uUID, uUIDx -> new awl.a());
	}

	public void a(awl awl, Random random, int integer) {
		Collection<awl.b> collection5 = awl.a(random, integer);
		collection5.forEach(b -> {
			int integer3 = b.c - b.b.j;
			if (integer3 >= 2) {
				this.a(b.a).a.mergeInt(b.b, integer3, awl::a);
			}
		});
	}

	public int a(UUID uUID, Predicate<awm> predicate) {
		awl.a a4 = (awl.a)this.a.get(uUID);
		return a4 != null ? a4.a(predicate) : 0;
	}

	public void a(UUID uUID, awm awm, int integer) {
		awl.a a5 = this.a(uUID);
		a5.a.mergeInt(awm, integer, (integer2, integer3) -> this.a(awm, integer2.intValue(), integer3.intValue()));
		a5.a(awm);
		if (a5.b()) {
			this.a.remove(uUID);
		}
	}

	public <T> Dynamic<T> a(DynamicOps<T> dynamicOps) {
		return new Dynamic<>(dynamicOps, dynamicOps.createList(this.c().map(b -> b.a(dynamicOps)).map(Dynamic::getValue)));
	}

	public void a(Dynamic<?> dynamic) {
		dynamic.asStream().map(awl.b::a).flatMap(dataResult -> v.a(dataResult.result())).forEach(b -> this.a(b.a).a.put(b.b, b.c));
	}

	private static int a(int integer1, int integer2) {
		return Math.max(integer1, integer2);
	}

	private int a(awm awm, int integer2, int integer3) {
		int integer5 = integer2 + integer3;
		return integer5 > awm.h ? Math.max(awm.h, integer2) : integer5;
	}

	static class a {
		private final Object2IntMap<awm> a = new Object2IntOpenHashMap<>();

		private a() {
		}

		public int a(Predicate<awm> predicate) {
			return this.a
				.object2IntEntrySet()
				.stream()
				.filter(entry -> predicate.test(entry.getKey()))
				.mapToInt(entry -> entry.getIntValue() * ((awm)entry.getKey()).g)
				.sum();
		}

		public Stream<awl.b> a(UUID uUID) {
			return this.a.object2IntEntrySet().stream().map(entry -> new awl.b(uUID, (awm)entry.getKey(), entry.getIntValue()));
		}

		public void a() {
			ObjectIterator<Entry<awm>> objectIterator2 = this.a.object2IntEntrySet().iterator();

			while (objectIterator2.hasNext()) {
				Entry<awm> entry3 = (Entry<awm>)objectIterator2.next();
				int integer4 = entry3.getIntValue() - ((awm)entry3.getKey()).i;
				if (integer4 < 2) {
					objectIterator2.remove();
				} else {
					entry3.setValue(integer4);
				}
			}
		}

		public boolean b() {
			return this.a.isEmpty();
		}

		public void a(awm awm) {
			int integer3 = this.a.getInt(awm);
			if (integer3 > awm.h) {
				this.a.put(awm, awm.h);
			}

			if (integer3 < 2) {
				this.b(awm);
			}
		}

		public void b(awm awm) {
			this.a.removeInt(awm);
		}
	}

	static class b {
		public final UUID a;
		public final awm b;
		public final int c;

		public b(UUID uUID, awm awm, int integer) {
			this.a = uUID;
			this.b = awm;
			this.c = integer;
		}

		public int a() {
			return this.c * this.b.g;
		}

		public String toString() {
			return "GossipEntry{target=" + this.a + ", type=" + this.b + ", value=" + this.c + '}';
		}

		public <T> Dynamic<T> a(DynamicOps<T> dynamicOps) {
			return new Dynamic<>(
				dynamicOps,
				dynamicOps.createMap(
					ImmutableMap.of(
						dynamicOps.createString("Target"),
						(T)gp.a.encodeStart(dynamicOps, this.a).result().orElseThrow(RuntimeException::new),
						dynamicOps.createString("Type"),
						dynamicOps.createString(this.b.f),
						dynamicOps.createString("Value"),
						dynamicOps.createInt(this.c)
					)
				)
			);
		}

		public static DataResult<awl.b> a(Dynamic<?> dynamic) {
			return DataResult.unbox(
				DataResult.instance()
					.group(dynamic.get("Target").read(gp.a), dynamic.get("Type").asString().map(awm::a), dynamic.get("Value").asNumber().map(Number::intValue))
					.apply(DataResult.instance(), awl.b::new)
			);
		}
	}
}
