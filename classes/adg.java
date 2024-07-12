import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class adg<T> {
	private static final Logger a = LogManager.getLogger();
	private static final Gson b = new Gson();
	private static final int c = ".json".length();
	private final adf<T> d = adc.a();
	private volatile BiMap<uh, adf<T>> e = HashBiMap.create();
	private final Function<uh, Optional<T>> f;
	private final String g;
	private final String h;

	public adg(Function<uh, Optional<T>> function, String string2, String string3) {
		this.f = function;
		this.g = string2;
		this.h = string3;
	}

	@Nullable
	public adf<T> a(uh uh) {
		return (adf<T>)this.e.get(uh);
	}

	public adf<T> b(uh uh) {
		return (adf<T>)this.e.getOrDefault(uh, this.d);
	}

	@Nullable
	public uh a(adf<T> adf) {
		return adf instanceof adf.e ? ((adf.e)adf).a() : (uh)this.e.inverse().get(adf);
	}

	public uh b(adf<T> adf) {
		uh uh3 = this.a(adf);
		if (uh3 == null) {
			throw new IllegalStateException("Unrecognized tag");
		} else {
			return uh3;
		}
	}

	public Collection<uh> a() {
		return this.e.keySet();
	}

	public CompletableFuture<Map<uh, adf.a>> a(abc abc, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			Map<uh, adf.a> map3 = Maps.<uh, adf.a>newHashMap();

			for (uh uh5 : abc.a(this.g, string -> string.endsWith(".json"))) {
				String string6 = uh5.a();
				uh uh7 = new uh(uh5.b(), string6.substring(this.g.length() + 1, string6.length() - c));

				try {
					for (abb abb9 : abc.c(uh5)) {
						try {
							InputStream inputStream10 = abb9.b();
							Throwable var10 = null;

							try {
								Reader reader12 = new BufferedReader(new InputStreamReader(inputStream10, StandardCharsets.UTF_8));
								Throwable var12 = null;

								try {
									JsonObject jsonObject14 = adt.a(b, reader12, JsonObject.class);
									if (jsonObject14 == null) {
										a.error("Couldn't load {} tag list {} from {} in data pack {} as it is empty or null", this.h, uh7, uh5, abb9.d());
									} else {
										((adf.a)map3.computeIfAbsent(uh7, uh -> adf.a.a())).a(jsonObject14, abb9.d());
									}
								} catch (Throwable var53) {
									var12 = var53;
									throw var53;
								} finally {
									if (reader12 != null) {
										if (var12 != null) {
											try {
												reader12.close();
											} catch (Throwable var52) {
												var12.addSuppressed(var52);
											}
										} else {
											reader12.close();
										}
									}
								}
							} catch (Throwable var55) {
								var10 = var55;
								throw var55;
							} finally {
								if (inputStream10 != null) {
									if (var10 != null) {
										try {
											inputStream10.close();
										} catch (Throwable var51) {
											var10.addSuppressed(var51);
										}
									} else {
										inputStream10.close();
									}
								}
							}
						} catch (RuntimeException | IOException var57) {
							a.error("Couldn't read {} tag list {} from {} in data pack {}", this.h, uh7, uh5, abb9.d(), var57);
						} finally {
							IOUtils.closeQuietly(abb9);
						}
					}
				} catch (IOException var59) {
					a.error("Couldn't read {} tag list {} from {}", this.h, uh7, uh5, var59);
				}
			}

			return map3;
		}, executor);
	}

	public void a(Map<uh, adf.a> map) {
		Map<uh, adf<T>> map3 = Maps.<uh, adf<T>>newHashMap();
		Function<uh, adf<T>> function4 = map3::get;
		Function<uh, T> function5 = uh -> ((Optional)this.f.apply(uh)).orElse(null);

		while (!map.isEmpty()) {
			boolean boolean6 = false;
			Iterator<Entry<uh, adf.a>> iterator7 = map.entrySet().iterator();

			while (iterator7.hasNext()) {
				Entry<uh, adf.a> entry8 = (Entry<uh, adf.a>)iterator7.next();
				Optional<adf<T>> optional9 = ((adf.a)entry8.getValue()).a(function4, function5);
				if (optional9.isPresent()) {
					map3.put(entry8.getKey(), optional9.get());
					iterator7.remove();
					boolean6 = true;
				}
			}

			if (!boolean6) {
				break;
			}
		}

		map.forEach(
			(uh, a) -> adg.a
					.error(
						"Couldn't load {} tag {} as it is missing following references: {}",
						this.h,
						uh,
						a.b(function4, function5).map(Objects::toString).collect(Collectors.joining(","))
					)
		);
		this.b(map3);
	}

	protected void b(Map<uh, adf<T>> map) {
		this.e = ImmutableBiMap.copyOf(map);
	}

	public Map<uh, adf<T>> b() {
		return this.e;
	}
}
