import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cid<R> implements AutoCloseable {
	private static final Logger a = LogManager.getLogger();
	private final chx b;
	private final Long2ObjectMap<Optional<R>> c = new Long2ObjectOpenHashMap<>();
	private final LongLinkedOpenHashSet d = new LongLinkedOpenHashSet();
	private final Function<Runnable, Codec<R>> e;
	private final Function<Runnable, R> f;
	private final DataFixer g;
	private final aeo h;

	public cid(File file, Function<Runnable, Codec<R>> function2, Function<Runnable, R> function3, DataFixer dataFixer, aeo aeo, boolean boolean6) {
		this.e = function2;
		this.f = function3;
		this.g = dataFixer;
		this.h = aeo;
		this.b = new chx(file, boolean6, file.getName());
	}

	protected void a(BooleanSupplier booleanSupplier) {
		while (!this.d.isEmpty() && booleanSupplier.getAsBoolean()) {
			bph bph3 = go.a(this.d.firstLong()).r();
			this.d(bph3);
		}
	}

	@Nullable
	protected Optional<R> c(long long1) {
		return this.c.get(long1);
	}

	protected Optional<R> d(long long1) {
		go go4 = go.a(long1);
		if (this.b(go4)) {
			return Optional.empty();
		} else {
			Optional<R> optional5 = this.c(long1);
			if (optional5 != null) {
				return optional5;
			} else {
				this.b(go4.r());
				optional5 = this.c(long1);
				if (optional5 == null) {
					throw (IllegalStateException)v.c(new IllegalStateException());
				} else {
					return optional5;
				}
			}
		}
	}

	protected boolean b(go go) {
		return bqb.b(go.c(go.b()));
	}

	protected R e(long long1) {
		Optional<R> optional4 = this.d(long1);
		if (optional4.isPresent()) {
			return (R)optional4.get();
		} else {
			R object5 = (R)this.f.apply((Runnable)() -> this.a(long1));
			this.c.put(long1, Optional.of(object5));
			return object5;
		}
	}

	private void b(bph bph) {
		this.a(bph, lp.a, this.c(bph));
	}

	@Nullable
	private le c(bph bph) {
		try {
			return this.b.a(bph);
		} catch (IOException var3) {
			a.error("Error reading chunk {} data from disk", bph, var3);
			return null;
		}
	}

	private <T> void a(bph bph, DynamicOps<T> dynamicOps, @Nullable T object) {
		if (object == null) {
			for (int integer5 = 0; integer5 < 16; integer5++) {
				this.c.put(go.a(bph, integer5).s(), Optional.empty());
			}
		} else {
			Dynamic<T> dynamic5 = new Dynamic<>(dynamicOps, object);
			int integer6 = a(dynamic5);
			int integer7 = u.a().getWorldVersion();
			boolean boolean8 = integer6 != integer7;
			Dynamic<T> dynamic9 = this.g.update(this.h.a(), dynamic5, integer6, integer7);
			OptionalDynamic<T> optionalDynamic10 = dynamic9.get("Sections");

			for (int integer11 = 0; integer11 < 16; integer11++) {
				long long12 = go.a(bph, integer11).s();
				Optional<R> optional14 = optionalDynamic10.get(Integer.toString(integer11))
					.result()
					.flatMap(dynamic -> ((Codec)this.e.apply((Runnable)() -> this.a(long12))).parse(dynamic).resultOrPartial(a::error));
				this.c.put(long12, optional14);
				optional14.ifPresent(objectx -> {
					this.b(long12);
					if (boolean8) {
						this.a(long12);
					}
				});
			}
		}
	}

	private void d(bph bph) {
		Dynamic<lu> dynamic3 = this.a(bph, lp.a);
		lu lu4 = dynamic3.getValue();
		if (lu4 instanceof le) {
			this.b.a(bph, (le)lu4);
		} else {
			a.error("Expected compound tag, got {}", lu4);
		}
	}

	private <T> Dynamic<T> a(bph bph, DynamicOps<T> dynamicOps) {
		Map<T, T> map4 = Maps.<T, T>newHashMap();

		for (int integer5 = 0; integer5 < 16; integer5++) {
			long long6 = go.a(bph, integer5).s();
			this.d.remove(long6);
			Optional<R> optional8 = this.c.get(long6);
			if (optional8 != null && optional8.isPresent()) {
				DataResult<T> dataResult9 = ((Codec)this.e.apply((Runnable)() -> this.a(long6))).encodeStart(dynamicOps, optional8.get());
				String string10 = Integer.toString(integer5);
				dataResult9.resultOrPartial(a::error).ifPresent(object -> map4.put(dynamicOps.createString(string10), object));
			}
		}

		return new Dynamic<>(
			dynamicOps,
			dynamicOps.createMap(
				ImmutableMap.of(
					dynamicOps.createString("Sections"), dynamicOps.createMap(map4), dynamicOps.createString("DataVersion"), dynamicOps.createInt(u.a().getWorldVersion())
				)
			)
		);
	}

	protected void b(long long1) {
	}

	protected void a(long long1) {
		Optional<R> optional4 = this.c.get(long1);
		if (optional4 != null && optional4.isPresent()) {
			this.d.add(long1);
		} else {
			a.warn("No data for position: {}", go.a(long1));
		}
	}

	private static int a(Dynamic<?> dynamic) {
		return dynamic.get("DataVersion").asInt(1945);
	}

	public void a(bph bph) {
		if (!this.d.isEmpty()) {
			for (int integer3 = 0; integer3 < 16; integer3++) {
				long long4 = go.a(bph, integer3).s();
				if (this.d.contains(long4)) {
					this.d(bph);
					return;
				}
			}
		}
	}

	public void close() throws IOException {
		this.b.close();
	}
}
