import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class jv<T> implements hl {
	private static final Logger d = LogManager.getLogger();
	private static final Gson e = new GsonBuilder().setPrettyPrinting().create();
	protected final hk b;
	protected final gl<T> c;
	private final Map<uh, adf.a> f = Maps.<uh, adf.a>newLinkedHashMap();

	protected jv(hk hk, gl<T> gl) {
		this.b = hk;
		this.c = gl;
	}

	protected abstract void b();

	@Override
	public void a(hm hm) {
		this.f.clear();
		this.b();
		adf<T> adf3 = adc.a();
		Function<uh, adf<T>> function4 = uh -> this.f.containsKey(uh) ? adf3 : null;
		Function<uh, T> function5 = uh -> this.c.b(uh).orElse(null);
		this.f
			.forEach(
				(uh, a) -> {
					List<adf.b> list7 = (List<adf.b>)a.b(function4, function5).collect(Collectors.toList());
					if (!list7.isEmpty()) {
						throw new IllegalArgumentException(
							String.format(
								"Couldn't define tag %s as it is missing following references: %s", uh, list7.stream().map(Objects::toString).collect(Collectors.joining(","))
							)
						);
					} else {
						JsonObject jsonObject8 = a.c();
						Path path9 = this.a(uh);
		
						try {
							String string10 = e.toJson((JsonElement)jsonObject8);
							String string11 = jv.a.hashUnencodedChars(string10).toString();
							if (!Objects.equals(hm.a(path9), string11) || !Files.exists(path9, new LinkOption[0])) {
								Files.createDirectories(path9.getParent());
								BufferedWriter bufferedWriter12 = Files.newBufferedWriter(path9);
								Throwable var12 = null;
		
								try {
									bufferedWriter12.write(string10);
								} catch (Throwable var22) {
									var12 = var22;
									throw var22;
								} finally {
									if (bufferedWriter12 != null) {
										if (var12 != null) {
											try {
												bufferedWriter12.close();
											} catch (Throwable var21) {
												var12.addSuppressed(var21);
											}
										} else {
											bufferedWriter12.close();
										}
									}
								}
							}
		
							hm.a(path9, string11);
						} catch (IOException var24) {
							d.error("Couldn't save tags to {}", path9, var24);
						}
					}
				}
			);
	}

	protected abstract Path a(uh uh);

	protected jv.a<T> a(adf.e<T> e) {
		adf.a a3 = this.b(e);
		return new jv.a<>(a3, this.c, "vanilla");
	}

	protected adf.a b(adf.e<T> e) {
		return (adf.a)this.f.computeIfAbsent(e.a(), uh -> new adf.a());
	}

	public static class a<T> {
		private final adf.a a;
		private final gl<T> b;
		private final String c;

		private a(adf.a a, gl<T> gl, String string) {
			this.a = a;
			this.b = gl;
			this.c = string;
		}

		public jv.a<T> a(T object) {
			this.a.a(this.b.b(object), this.c);
			return this;
		}

		public jv.a<T> a(adf.e<T> e) {
			this.a.b(e.a(), this.c);
			return this;
		}

		@SafeVarargs
		public final jv.a<T> a(T... arr) {
			Stream.of(arr).map(this.b::b).forEach(uh -> this.a.a(uh, this.c));
			return this;
		}
	}
}
