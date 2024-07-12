import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class yr implements AutoCloseable, yo.c {
	private static final Logger a = LogManager.getLogger();
	private final Map<amp<?>, yq<? extends Function<amp<ael>, ?>>> b;
	private final Set<amp<?>> c;
	private final amq<ams.b> d;

	public yr(List<amp<?>> list, Executor executor, int integer) {
		this.b = (Map<amp<?>, yq<? extends Function<amp<ael>, ?>>>)list.stream()
			.collect(Collectors.toMap(Function.identity(), amp -> new yq(amp.bh() + "_queue", integer)));
		this.c = Sets.<amp<?>>newHashSet(list);
		this.d = new amq<>(new ams.a(4), executor, "sorter");
	}

	public static yr.a<Runnable> a(Runnable runnable, long long2, IntSupplier intSupplier) {
		return new yr.a<>(amp -> () -> {
				runnable.run();
				amp.a(ael.INSTANCE);
			}, long2, intSupplier);
	}

	public static yr.a<Runnable> a(yo yo, Runnable runnable) {
		return a(runnable, yo.i().a(), yo::k);
	}

	public static yr.b a(Runnable runnable, long long2, boolean boolean3) {
		return new yr.b(runnable, long2, boolean3);
	}

	public <T> amp<yr.a<T>> a(amp<T> amp, boolean boolean2) {
		return (amp<yr.a<T>>)this.d.b(amp3 -> new ams.b(0, () -> {
				this.b(amp);
				amp3.a(amp.a("chunk priority sorter around " + amp.bh(), a -> this.a(amp, a.a, a.b, a.c, boolean2)));
			})).join();
	}

	public amp<yr.b> a(amp<Runnable> amp) {
		return (amp<yr.b>)this.d.b(amp2 -> new ams.b(0, () -> amp2.a(amp.a("chunk priority sorter around " + amp.bh(), b -> this.a(amp, b.b, b.a, b.c))))).join();
	}

	@Override
	public void a(bph bph, IntSupplier intSupplier, int integer, IntConsumer intConsumer) {
		this.d.a(new ams.b(0, () -> {
			int integer6 = intSupplier.getAsInt();
			this.b.values().forEach(yq -> yq.a(integer6, bph, integer));
			intConsumer.accept(integer);
		}));
	}

	private <T> void a(amp<T> amp, long long2, Runnable runnable, boolean boolean4) {
		this.d.a(new ams.b(1, () -> {
			yq<Function<amp<ael>, T>> yq7 = this.b(amp);
			yq7.a(long2, boolean4);
			if (this.c.remove(amp)) {
				this.a(yq7, amp);
			}

			runnable.run();
		}));
	}

	private <T> void a(amp<T> amp, Function<amp<ael>, T> function, long long3, IntSupplier intSupplier, boolean boolean5) {
		this.d.a(new ams.b(2, () -> {
			yq<Function<amp<ael>, T>> yq8 = this.b(amp);
			int integer9 = intSupplier.getAsInt();
			yq8.a(Optional.of(function), long3, integer9);
			if (boolean5) {
				yq8.a(Optional.empty(), long3, integer9);
			}

			if (this.c.remove(amp)) {
				this.a(yq8, amp);
			}
		}));
	}

	private <T> void a(yq<Function<amp<ael>, T>> yq, amp<T> amp) {
		this.d.a(new ams.b(3, () -> {
			Stream<Either<Function<amp<ael>, T>, Runnable>> stream4 = yq.a();
			if (stream4 == null) {
				this.c.add(amp);
			} else {
				v.b((List)stream4.map(either -> either.map(amp::b, runnable -> {
						runnable.run();
						return CompletableFuture.completedFuture(ael.INSTANCE);
					})).collect(Collectors.toList())).thenAccept(list -> this.a(yq, amp));
			}
		}));
	}

	private <T> yq<Function<amp<ael>, T>> b(amp<T> amp) {
		yq<? extends Function<amp<ael>, ?>> yq3 = (yq<? extends Function<amp<ael>, ?>>)this.b.get(amp);
		if (yq3 == null) {
			throw (IllegalArgumentException)v.c((T)(new IllegalArgumentException("No queue for: " + amp)));
		} else {
			return (yq<Function<amp<ael>, T>>)yq3;
		}
	}

	@VisibleForTesting
	public String a() {
		return (String)this.b
				.entrySet()
				.stream()
				.map(
					entry -> ((amp)entry.getKey()).bh()
							+ "=["
							+ (String)((yq)entry.getValue()).b().stream().map(long1 -> long1 + ":" + new bph(long1)).collect(Collectors.joining(","))
							+ "]"
				)
				.collect(Collectors.joining(","))
			+ ", s="
			+ this.c.size();
	}

	public void close() {
		this.b.keySet().forEach(amp::close);
	}

	public static final class a<T> {
		private final Function<amp<ael>, T> a;
		private final long b;
		private final IntSupplier c;

		private a(Function<amp<ael>, T> function, long long2, IntSupplier intSupplier) {
			this.a = function;
			this.b = long2;
			this.c = intSupplier;
		}
	}

	public static final class b {
		private final Runnable a;
		private final long b;
		private final boolean c;

		private b(Runnable runnable, long long2, boolean boolean3) {
			this.a = runnable;
			this.b = long2;
			this.c = boolean3;
		}
	}
}
