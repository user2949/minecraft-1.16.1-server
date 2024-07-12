import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class apr<E extends aoy> {
	private static final Logger a = LogManager.getLogger();
	private final Supplier<Codec<apr<E>>> b;
	private final Map<awp<?>, Optional<? extends awo<?>>> c = Maps.<awp<?>, Optional<? extends awo<?>>>newHashMap();
	private final Map<axo<? extends axn<? super E>>, axn<? super E>> d = Maps.<axo<? extends axn<? super E>>, axn<? super E>>newLinkedHashMap();
	private final Map<Integer, Map<bfl, Set<aqh<? super E>>>> e = Maps.newTreeMap();
	private bfn f = bfn.a;
	private final Map<bfl, Set<Pair<awp<?>, awq>>> g = Maps.<bfl, Set<Pair<awp<?>, awq>>>newHashMap();
	private final Map<bfl, Set<awp<?>>> h = Maps.<bfl, Set<awp<?>>>newHashMap();
	private Set<bfl> i = Sets.<bfl>newHashSet();
	private final Set<bfl> j = Sets.<bfl>newHashSet();
	private bfl k = bfl.b;
	private long l = -9999L;

	public static <E extends aoy> apr.b<E> a(Collection<? extends awp<?>> collection1, Collection<? extends axo<? extends axn<? super E>>> collection2) {
		return new apr.b<>(collection1, collection2);
	}

	public static <E extends aoy> Codec<apr<E>> b(Collection<? extends awp<?>> collection1, Collection<? extends axo<? extends axn<? super E>>> collection2) {
		final MutableObject<Codec<apr<E>>> mutableObject3 = new MutableObject<>();
		mutableObject3.setValue(
			(new MapCodec<apr<E>>() {
					@Override
					public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
						return collection1.stream().flatMap(awp -> v.a(awp.a().map(codec -> gl.aU.b(awp)))).map(uh -> dynamicOps.createString(uh.toString()));
					}
		
					@Override
					public <T> DataResult<apr<E>> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
						MutableObject<DataResult<Builder<apr.a<?>>>> mutableObject4 = new MutableObject<>(DataResult.success(ImmutableList.builder()));
						mapLike.entries().forEach(pair -> {
							DataResult<awp<?>> dataResult5 = gl.aU.parse(dynamicOps, (T)pair.getFirst());
							DataResult<? extends apr.a<?>> dataResult6 = dataResult5.flatMap(awp -> this.a(awp, dynamicOps, (T)pair.getSecond()));
							mutableObject4.setValue(mutableObject4.getValue().apply2(Builder::add, dataResult6));
						});
						ImmutableList<apr.a<?>> immutableList5 = (ImmutableList<apr.a<?>>)mutableObject4.getValue()
							.resultOrPartial(apr.a::error)
							.map(Builder::build)
							.orElseGet(ImmutableList::of);
						return DataResult.success(new apr<>(collection1, collection2, immutableList5, mutableObject3::getValue));
					}
		
					private <T, U> DataResult<apr.a<U>> a(awp<U> awp, DynamicOps<T> dynamicOps, T object) {
						return ((DataResult)awp.a().map(DataResult::success).orElseGet(() -> DataResult.error("No codec for memory: " + awp)))
							.flatMap(codec -> codec.parse(dynamicOps, object))
							.map(awo -> new apr.a(awp, Optional.of(awo)));
					}
		
					public <T> RecordBuilder<T> encode(apr<E> apr, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
						apr.j().forEach(a -> a.a(dynamicOps, recordBuilder));
						return recordBuilder;
					}
				})
				.fieldOf("memories")
				.codec()
		);
		return mutableObject3.getValue();
	}

	public apr(
		Collection<? extends awp<?>> collection1,
		Collection<? extends axo<? extends axn<? super E>>> collection2,
		ImmutableList<apr.a<?>> immutableList,
		Supplier<Codec<apr<E>>> supplier
	) {
		this.b = supplier;

		for (awp<?> awp7 : collection1) {
			this.c.put(awp7, Optional.empty());
		}

		for (axo<? extends axn<? super E>> axo7 : collection2) {
			this.d.put(axo7, axo7.a());
		}

		for (axn<? super E> axn7 : this.d.values()) {
			for (awp<?> awp9 : axn7.a()) {
				this.c.put(awp9, Optional.empty());
			}
		}

		for (apr.a<?> a7 : immutableList) {
			a7.a(this);
		}
	}

	public <T> DataResult<T> a(DynamicOps<T> dynamicOps) {
		return ((Codec)this.b.get()).encodeStart(dynamicOps, this);
	}

	private Stream<apr.a<?>> j() {
		return this.c.entrySet().stream().map(entry -> apr.a.b((awp)entry.getKey(), (Optional<? extends awo<?>>)entry.getValue()));
	}

	public boolean a(awp<?> awp) {
		return this.a(awp, awq.VALUE_PRESENT);
	}

	public <U> void b(awp<U> awp) {
		this.a(awp, Optional.empty());
	}

	public <U> void a(awp<U> awp, @Nullable U object) {
		this.a(awp, Optional.ofNullable(object));
	}

	public <U> void a(awp<U> awp, U object, long long3) {
		this.b(awp, Optional.of(awo.a(object, long3)));
	}

	public <U> void a(awp<U> awp, Optional<? extends U> optional) {
		this.b(awp, optional.map(awo::a));
	}

	private <U> void b(awp<U> awp, Optional<? extends awo<?>> optional) {
		if (this.c.containsKey(awp)) {
			if (optional.isPresent() && this.a(((awo)optional.get()).c())) {
				this.b(awp);
			} else {
				this.c.put(awp, optional);
			}
		}
	}

	public <U> Optional<U> c(awp<U> awp) {
		return ((Optional)this.c.get(awp)).map(awo::c);
	}

	public <U> boolean b(awp<U> awp, U object) {
		return !this.a(awp) ? false : this.c(awp).filter(object2 -> object2.equals(object)).isPresent();
	}

	public boolean a(awp<?> awp, awq awq) {
		Optional<? extends awo<?>> optional4 = (Optional<? extends awo<?>>)this.c.get(awp);
		return optional4 == null
			? false
			: awq == awq.REGISTERED || awq == awq.VALUE_PRESENT && optional4.isPresent() || awq == awq.VALUE_ABSENT && !optional4.isPresent();
	}

	public bfn b() {
		return this.f;
	}

	public void a(bfn bfn) {
		this.f = bfn;
	}

	public void a(Set<bfl> set) {
		this.i = set;
	}

	@Deprecated
	public List<aqh<? super E>> d() {
		List<aqh<? super E>> list2 = new ObjectArrayList<>();

		for (Map<bfl, Set<aqh<? super E>>> map4 : this.e.values()) {
			for (Set<aqh<? super E>> set6 : map4.values()) {
				for (aqh<? super E> aqh8 : set6) {
					if (aqh8.a() == aqh.a.RUNNING) {
						list2.add(aqh8);
					}
				}
			}
		}

		return list2;
	}

	public void e() {
		this.d(this.k);
	}

	public Optional<bfl> f() {
		for (bfl bfl3 : this.j) {
			if (!this.i.contains(bfl3)) {
				return Optional.of(bfl3);
			}
		}

		return Optional.empty();
	}

	public void a(bfl bfl) {
		if (this.f(bfl)) {
			this.d(bfl);
		} else {
			this.e();
		}
	}

	private void d(bfl bfl) {
		if (!this.c(bfl)) {
			this.e(bfl);
			this.j.clear();
			this.j.addAll(this.i);
			this.j.add(bfl);
		}
	}

	private void e(bfl bfl) {
		for (bfl bfl4 : this.j) {
			if (bfl4 != bfl) {
				Set<awp<?>> set5 = (Set<awp<?>>)this.h.get(bfl4);
				if (set5 != null) {
					for (awp<?> awp7 : set5) {
						this.b(awp7);
					}
				}
			}
		}
	}

	public void a(long long1, long long2) {
		if (long2 - this.l > 20L) {
			this.l = long2;
			bfl bfl6 = this.b().a((int)(long1 % 24000L));
			if (!this.j.contains(bfl6)) {
				this.a(bfl6);
			}
		}
	}

	public void a(List<bfl> list) {
		for (bfl bfl4 : list) {
			if (this.f(bfl4)) {
				this.d(bfl4);
				break;
			}
		}
	}

	public void b(bfl bfl) {
		this.k = bfl;
	}

	public void a(bfl bfl, int integer, ImmutableList<? extends aqh<? super E>> immutableList) {
		this.a(bfl, this.a(integer, immutableList));
	}

	public void a(bfl bfl, int integer, ImmutableList<? extends aqh<? super E>> immutableList, awp<?> awp) {
		Set<Pair<awp<?>, awq>> set6 = ImmutableSet.of(Pair.of(awp, awq.VALUE_PRESENT));
		Set<awp<?>> set7 = ImmutableSet.of(awp);
		this.a(bfl, this.a(integer, immutableList), set6, set7);
	}

	public void a(bfl bfl, ImmutableList<? extends Pair<Integer, ? extends aqh<? super E>>> immutableList) {
		this.a(bfl, immutableList, ImmutableSet.of(), Sets.<awp<?>>newHashSet());
	}

	public void a(bfl bfl, ImmutableList<? extends Pair<Integer, ? extends aqh<? super E>>> immutableList, Set<Pair<awp<?>, awq>> set) {
		this.a(bfl, immutableList, set, Sets.<awp<?>>newHashSet());
	}

	private void a(bfl bfl, ImmutableList<? extends Pair<Integer, ? extends aqh<? super E>>> immutableList, Set<Pair<awp<?>, awq>> set3, Set<awp<?>> set4) {
		this.g.put(bfl, set3);
		if (!set4.isEmpty()) {
			this.h.put(bfl, set4);
		}

		for (Pair<Integer, ? extends aqh<? super E>> pair7 : immutableList) {
			((Set)((Map)this.e.computeIfAbsent(pair7.getFirst(), integer -> Maps.newHashMap())).computeIfAbsent(bfl, bflx -> Sets.newLinkedHashSet()))
				.add(pair7.getSecond());
		}
	}

	public boolean c(bfl bfl) {
		return this.j.contains(bfl);
	}

	public apr<E> h() {
		apr<E> apr2 = new apr<>(this.c.keySet(), this.d.keySet(), ImmutableList.of(), this.b);

		for (Entry<awp<?>, Optional<? extends awo<?>>> entry4 : this.c.entrySet()) {
			awp<?> awp5 = (awp<?>)entry4.getKey();
			if (((Optional)entry4.getValue()).isPresent()) {
				apr2.c.put(awp5, entry4.getValue());
			}
		}

		return apr2;
	}

	public void a(zd zd, E aoy) {
		this.k();
		this.c(zd, aoy);
		this.d(zd, aoy);
		this.e(zd, aoy);
	}

	private void c(zd zd, E aoy) {
		for (axn<? super E> axn5 : this.d.values()) {
			axn5.b(zd, aoy);
		}
	}

	private void k() {
		for (Entry<awp<?>, Optional<? extends awo<?>>> entry3 : this.c.entrySet()) {
			if (((Optional)entry3.getValue()).isPresent()) {
				awo<?> awo4 = (awo<?>)((Optional)entry3.getValue()).get();
				awo4.a();
				if (awo4.d()) {
					this.b((awp)entry3.getKey());
				}
			}
		}
	}

	public void b(zd zd, E aoy) {
		long long4 = aoy.l.Q();

		for (aqh<? super E> aqh7 : this.d()) {
			aqh7.g(zd, aoy, long4);
		}
	}

	private void d(zd zd, E aoy) {
		long long4 = zd.Q();

		for (Map<bfl, Set<aqh<? super E>>> map7 : this.e.values()) {
			for (Entry<bfl, Set<aqh<? super E>>> entry9 : map7.entrySet()) {
				bfl bfl10 = (bfl)entry9.getKey();
				if (this.j.contains(bfl10)) {
					for (aqh<? super E> aqh13 : (Set)entry9.getValue()) {
						if (aqh13.a() == aqh.a.STOPPED) {
							aqh13.e(zd, aoy, long4);
						}
					}
				}
			}
		}
	}

	private void e(zd zd, E aoy) {
		long long4 = zd.Q();

		for (aqh<? super E> aqh7 : this.d()) {
			aqh7.f(zd, aoy, long4);
		}
	}

	private boolean f(bfl bfl) {
		if (!this.g.containsKey(bfl)) {
			return false;
		} else {
			for (Pair<awp<?>, awq> pair4 : (Set)this.g.get(bfl)) {
				awp<?> awp5 = pair4.getFirst();
				awq awq6 = pair4.getSecond();
				if (!this.a(awp5, awq6)) {
					return false;
				}
			}

			return true;
		}
	}

	private boolean a(Object object) {
		return object instanceof Collection && ((Collection)object).isEmpty();
	}

	ImmutableList<? extends Pair<Integer, ? extends aqh<? super E>>> a(int integer, ImmutableList<? extends aqh<? super E>> immutableList) {
		int integer4 = integer;
		Builder<Pair<Integer, ? extends aqh<? super E>>> builder5 = ImmutableList.builder();

		for (aqh<? super E> aqh7 : immutableList) {
			builder5.add(Pair.of(integer4++, aqh7));
		}

		return builder5.build();
	}

	static final class a<U> {
		private final awp<U> a;
		private final Optional<? extends awo<U>> b;

		private static <U> apr.a<U> b(awp<U> awp, Optional<? extends awo<?>> optional) {
			return new apr.a<>(awp, (Optional<? extends awo<U>>)optional);
		}

		private a(awp<U> awp, Optional<? extends awo<U>> optional) {
			this.a = awp;
			this.b = optional;
		}

		private void a(apr<?> apr) {
			apr.b(this.a, this.b);
		}

		public <T> void a(DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
			this.a.a().ifPresent(codec -> this.b.ifPresent(awo -> recordBuilder.add(gl.aU.encodeStart(dynamicOps, (T)this.a), codec.encodeStart(dynamicOps, awo))));
		}
	}

	public static final class b<E extends aoy> {
		private final Collection<? extends awp<?>> a;
		private final Collection<? extends axo<? extends axn<? super E>>> b;
		private final Codec<apr<E>> c;

		private b(Collection<? extends awp<?>> collection1, Collection<? extends axo<? extends axn<? super E>>> collection2) {
			this.a = collection1;
			this.b = collection2;
			this.c = apr.b(collection1, collection2);
		}

		public apr<E> a(Dynamic<?> dynamic) {
			return (apr<E>)this.c.parse(dynamic).resultOrPartial(apr.a::error).orElseGet(() -> new apr(this.a, this.b, ImmutableList.of(), () -> this.c));
		}
	}
}
