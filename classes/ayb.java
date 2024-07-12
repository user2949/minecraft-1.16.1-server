import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ayb {
	private static final Logger a = LogManager.getLogger();
	private final Short2ObjectMap<aya> b = new Short2ObjectOpenHashMap<>();
	private final Map<ayc, Set<aya>> c = Maps.<ayc, Set<aya>>newHashMap();
	private final Runnable d;
	private boolean e;

	public static Codec<ayb> a(Runnable runnable) {
		return RecordCodecBuilder.<ayb>create(
				instance -> instance.group(
							RecordCodecBuilder.point(runnable),
							Codec.BOOL.optionalFieldOf("Valid", Boolean.valueOf(false)).forGetter(ayb -> ayb.e),
							aya.a(runnable).listOf().fieldOf("Records").forGetter(ayb -> ImmutableList.copyOf(ayb.b.values()))
						)
						.apply(instance, ayb::new)
			)
			.withDefault(v.a("Failed to read POI section: ", a::error), (Supplier<? extends ayb>)(() -> new ayb(runnable, false, ImmutableList.of())));
	}

	public ayb(Runnable runnable) {
		this(runnable, true, ImmutableList.of());
	}

	private ayb(Runnable runnable, boolean boolean2, List<aya> list) {
		this.d = runnable;
		this.e = boolean2;
		list.forEach(this::a);
	}

	public Stream<aya> a(Predicate<ayc> predicate, axz.b b) {
		return this.c.entrySet().stream().filter(entry -> predicate.test(entry.getKey())).flatMap(entry -> ((Set)entry.getValue()).stream()).filter(b.a());
	}

	public void a(fu fu, ayc ayc) {
		if (this.a(new aya(fu, ayc, this.d))) {
			a.debug("Added POI of type {} @ {}", () -> ayc, () -> fu);
			this.d.run();
		}
	}

	private boolean a(aya aya) {
		fu fu3 = aya.f();
		ayc ayc4 = aya.g();
		short short5 = go.b(fu3);
		aya aya6 = this.b.get(short5);
		if (aya6 != null) {
			if (ayc4.equals(aya6.g())) {
				return false;
			} else {
				throw (IllegalStateException)v.c(new IllegalStateException("POI data mismatch: already registered at " + fu3));
			}
		} else {
			this.b.put(short5, aya);
			((Set)this.c.computeIfAbsent(ayc4, ayc -> Sets.newHashSet())).add(aya);
			return true;
		}
	}

	public void a(fu fu) {
		aya aya3 = this.b.remove(go.b(fu));
		if (aya3 == null) {
			a.error("POI data mismatch: never registered at " + fu);
		} else {
			((Set)this.c.get(aya3.g())).remove(aya3);
			a.debug("Removed POI of type {} @ {}", aya3::g, aya3::f);
			this.d.run();
		}
	}

	public boolean c(fu fu) {
		aya aya3 = this.b.get(go.b(fu));
		if (aya3 == null) {
			throw (IllegalStateException)v.c(new IllegalStateException("POI never registered at " + fu));
		} else {
			boolean boolean4 = aya3.c();
			this.d.run();
			return boolean4;
		}
	}

	public boolean a(fu fu, Predicate<ayc> predicate) {
		short short4 = go.b(fu);
		aya aya5 = this.b.get(short4);
		return aya5 != null && predicate.test(aya5.g());
	}

	public Optional<ayc> d(fu fu) {
		short short3 = go.b(fu);
		aya aya4 = this.b.get(short3);
		return aya4 != null ? Optional.of(aya4.g()) : Optional.empty();
	}

	public void a(Consumer<BiConsumer<fu, ayc>> consumer) {
		if (!this.e) {
			Short2ObjectMap<aya> short2ObjectMap3 = new Short2ObjectOpenHashMap<>(this.b);
			this.b();
			consumer.accept((BiConsumer)(fu, ayc) -> {
				short short5 = go.b(fu);
				aya aya6 = short2ObjectMap3.computeIfAbsent(short5, integer -> new aya(fu, ayc, this.d));
				this.a(aya6);
			});
			this.e = true;
			this.d.run();
		}
	}

	private void b() {
		this.b.clear();
		this.c.clear();
	}

	boolean a() {
		return this.e;
	}
}
