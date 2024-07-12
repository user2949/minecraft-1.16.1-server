import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ut implements aax {
	private static final Logger a = LogManager.getLogger();
	private static final int b = "functions/".length();
	private static final int c = ".mcfunction".length();
	private volatile Map<uh, cw> d = ImmutableMap.of();
	private final adg<cw> e = new adg<>(this::a, "tags/functions", "function");
	private final int f;
	private final CommandDispatcher<cz> g;

	public Optional<cw> a(uh uh) {
		return Optional.ofNullable(this.d.get(uh));
	}

	public Map<uh, cw> a() {
		return this.d;
	}

	public adg<cw> b() {
		return this.e;
	}

	public adf<cw> b(uh uh) {
		return this.e.b(uh);
	}

	public ut(int integer, CommandDispatcher<cz> commandDispatcher) {
		this.f = integer;
		this.g = commandDispatcher;
	}

	@Override
	public CompletableFuture<Void> a(aax.a a, abc abc, ami ami3, ami ami4, Executor executor5, Executor executor6) {
		CompletableFuture<Map<uh, adf.a>> completableFuture8 = this.e.a(abc, executor5);
		CompletableFuture<Map<uh, CompletableFuture<cw>>> completableFuture9 = CompletableFuture.supplyAsync(
				() -> abc.a("functions", string -> string.endsWith(".mcfunction")), executor5
			)
			.thenCompose(collection -> {
				Map<uh, CompletableFuture<cw>> map5 = Maps.<uh, CompletableFuture<cw>>newHashMap();
				cz cz6 = new cz(cy.a_, dem.a, del.a, null, this.f, "", nd.d, null, null);
	
				for (uh uh8 : collection) {
					String string9 = uh8.a();
					uh uh10 = new uh(uh8.b(), string9.substring(b, string9.length() - c));
					map5.put(uh10, CompletableFuture.supplyAsync(() -> {
						List<String> list6 = a(abc, uh8);
						return cw.a(uh10, this.g, cz6, list6);
					}, executor5));
				}
	
				CompletableFuture<?>[] arr7 = (CompletableFuture<?>[])map5.values().toArray(new CompletableFuture[0]);
				return CompletableFuture.allOf(arr7).handle((void2, throwable) -> map5);
			});
		return completableFuture8.thenCombine(completableFuture9, Pair::of).thenCompose(a::a).thenAcceptAsync(pair -> {
			Map<uh, CompletableFuture<cw>> map3 = (Map<uh, CompletableFuture<cw>>)pair.getSecond();
			Builder<uh, cw> builder4 = ImmutableMap.builder();
			map3.forEach((uh, completableFuture) -> completableFuture.handle((cw, throwable) -> {
					if (throwable != null) {
						a.error("Failed to load function {}", uh, throwable);
					} else {
						builder4.put(uh, cw);
					}

					return null;
				}).join());
			this.d = builder4.build();
			this.e.a((Map<uh, adf.a>)pair.getFirst());
		}, executor6);
	}

	private static List<String> a(abc abc, uh uh) {
		try {
			abb abb3 = abc.a(uh);
			Throwable var3 = null;

			List var4;
			try {
				var4 = IOUtils.readLines(abb3.b(), StandardCharsets.UTF_8);
			} catch (Throwable var14) {
				var3 = var14;
				throw var14;
			} finally {
				if (abb3 != null) {
					if (var3 != null) {
						try {
							abb3.close();
						} catch (Throwable var13) {
							var3.addSuppressed(var13);
						}
					} else {
						abb3.close();
					}
				}
			}

			return var4;
		} catch (IOException var16) {
			throw new CompletionException(var16);
		}
	}
}
