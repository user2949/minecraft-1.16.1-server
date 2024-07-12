import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class chx implements AutoCloseable {
	private static final Logger a = LogManager.getLogger();
	private final AtomicBoolean b = new AtomicBoolean();
	private final amq<ams.b> c;
	private final cib d;
	private final Map<bph, chx.a> e = Maps.<bph, chx.a>newLinkedHashMap();

	protected chx(File file, boolean boolean2, String string) {
		this.d = new cib(file, boolean2);
		this.c = new amq<>(new ams.a(chx.b.values().length), v.g(), "IOWorker-" + string);
	}

	public CompletableFuture<Void> a(bph bph, le le) {
		return this.a((Supplier)(() -> {
			chx.a a4 = (chx.a)this.e.computeIfAbsent(bph, bphxx -> new chx.a(le));
			a4.a = le;
			return Either.left(a4.b);
		})).thenCompose(Function.identity());
	}

	@Nullable
	public le a(bph bph) throws IOException {
		CompletableFuture<le> completableFuture3 = this.a((Supplier<Either<le, Exception>>)(() -> {
			chx.a a3 = (chx.a)this.e.get(bph);
			if (a3 != null) {
				return Either.left(a3.a);
			} else {
				try {
					le le4 = this.d.a(bph);
					return Either.left(le4);
				} catch (Exception var4x) {
					a.warn("Failed to read chunk {}", bph, var4x);
					return Either.right(var4x);
				}
			}
		}));

		try {
			return (le)completableFuture3.join();
		} catch (CompletionException var4) {
			if (var4.getCause() instanceof IOException) {
				throw (IOException)var4.getCause();
			} else {
				throw var4;
			}
		}
	}

	public CompletableFuture<Void> a() {
		CompletableFuture<Void> completableFuture2 = this.a(
				(Supplier)(() -> Either.left(CompletableFuture.allOf((CompletableFuture[])this.e.values().stream().map(a -> a.b).toArray(CompletableFuture[]::new))))
			)
			.thenCompose(Function.identity());
		return completableFuture2.thenCompose(void1 -> this.a((Supplier)(() -> {
				try {
					this.d.a();
					return Either.left(null);
				} catch (Exception var2) {
					a.warn("Failed to synchronized chunks", (Throwable)var2);
					return Either.right(var2);
				}
			})));
	}

	private <T> CompletableFuture<T> a(Supplier<Either<T, Exception>> supplier) {
		return this.c.c(amp -> new ams.b(chx.b.HIGH.ordinal(), () -> {
				if (!this.b.get()) {
					amp.a(supplier.get());
				}

				this.c();
			}));
	}

	private void b() {
		Iterator<Entry<bph, chx.a>> iterator2 = this.e.entrySet().iterator();
		if (iterator2.hasNext()) {
			Entry<bph, chx.a> entry3 = (Entry<bph, chx.a>)iterator2.next();
			iterator2.remove();
			this.a((bph)entry3.getKey(), (chx.a)entry3.getValue());
			this.c();
		}
	}

	private void c() {
		this.c.a(new ams.b(chx.b.LOW.ordinal(), this::b));
	}

	private void a(bph bph, chx.a a) {
		try {
			this.d.a(bph, a.a);
			a.b.complete(null);
		} catch (Exception var4) {
			chx.a.error("Failed to store chunk {}", bph, var4);
			a.b.completeExceptionally(var4);
		}
	}

	public void close() throws IOException {
		if (this.b.compareAndSet(false, true)) {
			CompletableFuture<ael> completableFuture2 = this.c.b(amp -> new ams.b(chx.b.HIGH.ordinal(), () -> amp.a(ael.INSTANCE)));

			try {
				completableFuture2.join();
			} catch (CompletionException var4) {
				if (var4.getCause() instanceof IOException) {
					throw (IOException)var4.getCause();
				}

				throw var4;
			}

			this.c.close();
			this.e.forEach(this::a);
			this.e.clear();

			try {
				this.d.close();
			} catch (Exception var3) {
				a.error("Failed to close storage", (Throwable)var3);
			}
		}
	}

	static class a {
		private le a;
		private final CompletableFuture<Void> b = new CompletableFuture();

		public a(le le) {
			this.a = le;
		}
	}

	static enum b {
		HIGH,
		LOW;
	}
}
