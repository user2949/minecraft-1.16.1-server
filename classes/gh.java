import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class gh<T> extends gs<T> {
	protected static final Logger a = LogManager.getLogger();
	protected final adm<T> b = new adm<>(256);
	protected final BiMap<uh, T> c = HashBiMap.create();
	private final BiMap<ug<T>, T> bb = HashBiMap.create();
	private final Set<ug<T>> bc = Sets.newIdentityHashSet();
	protected Object[] d;
	private int bd;

	public gh(ug<gl<T>> ug, Lifecycle lifecycle) {
		super(ug, lifecycle);
	}

	@Override
	public <V extends T> V a(int integer, ug<T> ug, V object) {
		this.b.a((T)object, integer);
		Validate.notNull(ug);
		Validate.notNull(object);
		this.d = null;
		if (this.bb.containsKey(ug)) {
			a.debug("Adding duplicate key '{}' to registry", ug);
		}

		this.c.put(ug.a(), (T)object);
		this.bb.put(ug, (T)object);
		if (this.bd <= integer) {
			this.bd = integer + 1;
		}

		return object;
	}

	@Override
	public <V extends T> V a(ug<T> ug, V object) {
		return this.a(this.bd, ug, object);
	}

	@Nullable
	@Override
	public uh b(T object) {
		return (uh)this.c.inverse().get(object);
	}

	@Override
	public Optional<ug<T>> c(T object) {
		return Optional.ofNullable(this.bb.inverse().get(object));
	}

	@Override
	public int a(@Nullable T object) {
		return this.b.a(object);
	}

	@Nullable
	@Override
	public T a(@Nullable ug<T> ug) {
		return (T)this.bb.get(ug);
	}

	@Nullable
	@Override
	public T a(int integer) {
		return this.b.a(integer);
	}

	public Iterator<T> iterator() {
		return this.b.iterator();
	}

	@Nullable
	@Override
	public T a(@Nullable uh uh) {
		return (T)this.c.get(uh);
	}

	@Override
	public Optional<T> b(@Nullable uh uh) {
		return Optional.ofNullable(this.c.get(uh));
	}

	@Override
	public Set<uh> b() {
		return Collections.unmodifiableSet(this.c.keySet());
	}

	public Set<Entry<ug<T>, T>> c() {
		return Collections.unmodifiableMap(this.bb).entrySet();
	}

	@Nullable
	public T a(Random random) {
		if (this.d == null) {
			Collection<?> collection3 = this.c.values();
			if (collection3.isEmpty()) {
				return null;
			}

			this.d = collection3.toArray(new Object[collection3.size()]);
		}

		return v.a((T[])this.d, random);
	}

	@Override
	public boolean c(uh uh) {
		return this.c.containsKey(uh);
	}

	@Override
	public boolean b(int integer) {
		return this.b.b(integer);
	}

	@Override
	public boolean c(ug<T> ug) {
		return this.bc.contains(ug);
	}

	@Override
	public void d(ug<T> ug) {
		this.bc.add(ug);
	}

	public static <T> Codec<gh<T>> a(ug<gl<T>> ug, Lifecycle lifecycle, MapCodec<T> mapCodec) {
		return adl.a(ug, mapCodec).codec().listOf().xmap(list -> {
			gh<T> gh4 = new gh<>(ug, lifecycle);

			for (Pair<ug<T>, T> pair6 : list) {
				gh4.a(pair6.getFirst(), pair6.getSecond());
			}

			return gh4;
		}, gh -> {
			com.google.common.collect.ImmutableList.Builder<Pair<ug<T>, T>> builder2 = ImmutableList.builder();

			for (T object4 : gh.b) {
				builder2.add(Pair.of((ug<T>)gh.c(object4).get(), object4));
			}

			return builder2.build();
		});
	}

	public static <T> Codec<gh<T>> b(ug<gl<T>> ug, Lifecycle lifecycle, MapCodec<T> mapCodec) {
		return uc.a(ug, lifecycle, mapCodec);
	}

	public static <T> Codec<gh<T>> c(ug<gl<T>> ug, Lifecycle lifecycle, MapCodec<T> mapCodec) {
		return Codec.unboundedMap(uh.a.xmap(ug.a(ug), ug::a), mapCodec.codec()).xmap(map -> {
			gh<T> gh4 = new gh<>(ug, lifecycle);
			map.forEach((ugxx, object) -> {
				gh4.a(gh4.bd, ugxx, object);
				gh4.d(ugxx);
			});
			return gh4;
		}, gh -> {
			Builder<ug<T>, T> builder2 = ImmutableMap.builder();
			gh.bb.entrySet().stream().filter(entry -> gh.c((ug<T>)entry.getKey())).forEach(builder2::put);
			return builder2.build();
		});
	}
}
