import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ue<T> extends ub<T> {
	private static final Logger b = LogManager.getLogger();
	private final abc c;
	private final gm d;
	private final Map<ug<? extends gl<?>>, ue.a<?>> e = Maps.<ug<? extends gl<?>>, ue.a<?>>newIdentityHashMap();

	public static <T> ue<T> a(DynamicOps<T> dynamicOps, abc abc, gm gm) {
		return new ue<>(dynamicOps, abc, gm);
	}

	private ue(DynamicOps<T> dynamicOps, abc abc, gm gm) {
		super(dynamicOps);
		this.c = abc;
		this.d = gm;
	}

	protected <E> DataResult<Pair<Supplier<E>, T>> a(T object, ug<gl<E>> ug, MapCodec<E> mapCodec) {
		Optional<gs<E>> optional5 = this.d.a(ug);
		if (!optional5.isPresent()) {
			return DataResult.error("Unknown registry: " + ug);
		} else {
			gs<E> gs6 = (gs<E>)optional5.get();
			DataResult<Pair<uh, T>> dataResult7 = uh.a.decode(this.a, object);
			if (!dataResult7.result().isPresent()) {
				return adl.a(ug, mapCodec).codec().decode(this.a, object).map(pair -> pair.mapFirst(pairx -> {
						gs6.a((ug<E>)pairx.getFirst(), pairx.getSecond());
						gs6.d((ug<E>)pairx.getFirst());
						return pairx::getSecond;
					}));
			} else {
				Pair<uh, T> pair8 = (Pair<uh, T>)dataResult7.result().get();
				uh uh9 = pair8.getFirst();
				return this.a(ug, gs6, mapCodec, uh9).map(supplier -> Pair.of(supplier, pair8.getSecond()));
			}
		}
	}

	public <E> DataResult<gh<E>> a(gh<E> gh, ug<gl<E>> ug, MapCodec<E> mapCodec) {
		uh uh5 = ug.a();
		Collection<uh> collection6 = this.c.a(uh5, string -> string.endsWith(".json"));
		DataResult<gh<E>> dataResult7 = DataResult.success(gh, Lifecycle.stable());

		for (uh uh9 : collection6) {
			String string10 = uh9.a();
			if (!string10.endsWith(".json")) {
				b.warn("Skipping resource {} since it is not a json file", uh9);
			} else if (!string10.startsWith(uh5.a() + "/")) {
				b.warn("Skipping resource {} since it does not have a registry name prefix", uh9);
			} else {
				String string11 = string10.substring(0, string10.length() - ".json".length()).substring(uh5.a().length() + 1);
				int integer12 = string11.indexOf(47);
				if (integer12 < 0) {
					b.warn("Skipping resource {} since it does not have a namespace", uh9);
				} else {
					String string13 = string11.substring(0, integer12);
					String string14 = string11.substring(integer12 + 1);
					uh uh15 = new uh(string13, string14);
					dataResult7 = dataResult7.flatMap(ghx -> this.a(ug, ghx, mapCodec, uh15).map(supplier -> ghx));
				}
			}
		}

		return dataResult7.setPartial(gh);
	}

	private <E> DataResult<Supplier<E>> a(ug<gl<E>> ug, gs<E> gs, MapCodec<E> mapCodec, uh uh) {
		ug<E> ug6 = ug.a(ug, uh);
		E object7 = gs.a(ug6);
		if (object7 != null) {
			return DataResult.success(() -> object7, Lifecycle.stable());
		} else {
			ue.a<E> a8 = this.a(ug);
			DataResult<Supplier<E>> dataResult9 = (DataResult<Supplier<E>>)a8.a.get(ug6);
			if (dataResult9 != null) {
				return dataResult9;
			} else {
				Supplier<E> supplier10 = Suppliers.memoize(() -> {
					E object3 = gs.a(ug6);
					if (object3 == null) {
						throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + ug6);
					} else {
						return (T)object3;
					}
				});
				a8.a.put(ug6, DataResult.success(supplier10));
				DataResult<E> dataResult11 = this.a(ug, ug6, mapCodec);
				dataResult11.result().ifPresent(object -> gs.a(ug6, object));
				DataResult<Supplier<E>> dataResult12 = dataResult11.map(object -> () -> object);
				a8.a.put(ug6, dataResult12);
				return dataResult12;
			}
		}
	}

	private <E> DataResult<E> a(ug<gl<E>> ug1, ug<E> ug2, MapCodec<E> mapCodec) {
		uh uh5 = new uh(ug1.a().b(), ug1.a().a() + "/" + ug2.a().b() + "/" + ug2.a().a() + ".json");

		try {
			abb abb6 = this.c.a(uh5);
			Throwable var6 = null;

			DataResult var11;
			try {
				Reader reader8 = new InputStreamReader(abb6.b(), StandardCharsets.UTF_8);
				Throwable var8 = null;

				try {
					JsonParser jsonParser10 = new JsonParser();
					JsonElement jsonElement11 = jsonParser10.parse(reader8);
					var11 = mapCodec.codec().parse(new ue<>(JsonOps.INSTANCE, this.c, this.d), jsonElement11);
				} catch (Throwable var36) {
					var8 = var36;
					throw var36;
				} finally {
					if (reader8 != null) {
						if (var8 != null) {
							try {
								reader8.close();
							} catch (Throwable var35) {
								var8.addSuppressed(var35);
							}
						} else {
							reader8.close();
						}
					}
				}
			} catch (Throwable var38) {
				var6 = var38;
				throw var38;
			} finally {
				if (abb6 != null) {
					if (var6 != null) {
						try {
							abb6.close();
						} catch (Throwable var34) {
							var6.addSuppressed(var34);
						}
					} else {
						abb6.close();
					}
				}
			}

			return var11;
		} catch (JsonIOException | JsonSyntaxException | IOException var40) {
			return DataResult.error("Failed to parse file: " + var40.getMessage());
		}
	}

	private <E> ue.a<E> a(ug<gl<E>> ug) {
		return (ue.a<E>)this.e.computeIfAbsent(ug, ugx -> new ue.a());
	}

	static final class a<E> {
		private final Map<ug<E>, DataResult<Supplier<E>>> a = Maps.<ug<E>, DataResult<Supplier<E>>>newIdentityHashMap();

		private a() {
		}
	}
}
