import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.Hash.Strategy;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class v {
	private static final AtomicInteger c = new AtomicInteger(1);
	private static final ExecutorService d = a("Bootstrap");
	private static final ExecutorService e = a("Main");
	private static final ExecutorService f = n();
	public static LongSupplier a = System::nanoTime;
	public static final UUID b = new UUID(0L, 0L);
	private static final Logger g = LogManager.getLogger();

	public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> a() {
		return Collectors.toMap(Entry::getKey, Entry::getValue);
	}

	public static <T extends Comparable<T>> String a(cgl<T> cgl, Object object) {
		return cgl.a((T)object);
	}

	public static String a(String string, @Nullable uh uh) {
		return uh == null ? string + ".unregistered_sadface" : string + '.' + uh.b() + '.' + uh.a().replace('/', '.');
	}

	public static long b() {
		return c() / 1000000L;
	}

	public static long c() {
		return a.getAsLong();
	}

	public static long d() {
		return Instant.now().toEpochMilli();
	}

	private static ExecutorService a(String string) {
		int integer2 = aec.a(Runtime.getRuntime().availableProcessors() - 1, 1, 7);
		ExecutorService executorService3;
		if (integer2 <= 0) {
			executorService3 = MoreExecutors.newDirectExecutorService();
		} else {
			executorService3 = new ForkJoinPool(integer2, forkJoinPool -> {
				ForkJoinWorkerThread forkJoinWorkerThread3 = new ForkJoinWorkerThread(forkJoinPool) {
					protected void onTermination(Throwable throwable) {
						if (throwable != null) {
							v.g.warn("{} died", this.getName(), throwable);
						} else {
							v.g.debug("{} shutdown", this.getName());
						}

						super.onTermination(throwable);
					}
				};
				forkJoinWorkerThread3.setName("Worker-" + string + "-" + c.getAndIncrement());
				return forkJoinWorkerThread3;
			}, v::a, true);
		}

		return executorService3;
	}

	public static Executor e() {
		return d;
	}

	public static Executor f() {
		return e;
	}

	public static Executor g() {
		return f;
	}

	public static void h() {
		a(e);
		a(f);
	}

	private static void a(ExecutorService executorService) {
		executorService.shutdown();

		boolean boolean2;
		try {
			boolean2 = executorService.awaitTermination(3L, TimeUnit.SECONDS);
		} catch (InterruptedException var3) {
			boolean2 = false;
		}

		if (!boolean2) {
			executorService.shutdownNow();
		}
	}

	private static ExecutorService n() {
		return Executors.newCachedThreadPool(runnable -> {
			Thread thread2 = new Thread(runnable);
			thread2.setName("IO-Worker-" + c.getAndIncrement());
			thread2.setUncaughtExceptionHandler(v::a);
			return thread2;
		});
	}

	private static void a(Thread thread, Throwable throwable) {
		c(throwable);
		if (throwable instanceof CompletionException) {
			throwable = throwable.getCause();
		}

		if (throwable instanceof s) {
			uj.a(((s)throwable).a().e());
			System.exit(-1);
		}

		g.error(String.format("Caught exception in thread %s", thread), throwable);
	}

	@Nullable
	public static Type<?> a(TypeReference typeReference, String string) {
		return !u.c ? null : b(typeReference, string);
	}

	@Nullable
	private static Type<?> b(TypeReference typeReference, String string) {
		Type<?> type3 = null;

		try {
			type3 = aep.a().getSchema(DataFixUtils.makeKey(u.a().getWorldVersion())).getChoiceType(typeReference, string);
		} catch (IllegalArgumentException var4) {
			g.error("No data fixer registered for {}", string);
			if (u.d) {
				throw var4;
			}
		}

		return type3;
	}

	public static v.b i() {
		String string1 = System.getProperty("os.name").toLowerCase(Locale.ROOT);
		if (string1.contains("win")) {
			return v.b.WINDOWS;
		} else if (string1.contains("mac")) {
			return v.b.OSX;
		} else if (string1.contains("solaris")) {
			return v.b.SOLARIS;
		} else if (string1.contains("sunos")) {
			return v.b.SOLARIS;
		} else if (string1.contains("linux")) {
			return v.b.LINUX;
		} else {
			return string1.contains("unix") ? v.b.LINUX : v.b.UNKNOWN;
		}
	}

	public static Stream<String> j() {
		RuntimeMXBean runtimeMXBean1 = ManagementFactory.getRuntimeMXBean();
		return runtimeMXBean1.getInputArguments().stream().filter(string -> string.startsWith("-X"));
	}

	public static <T> T a(List<T> list) {
		return (T)list.get(list.size() - 1);
	}

	public static <T> T a(Iterable<T> iterable, @Nullable T object) {
		Iterator<T> iterator3 = iterable.iterator();
		T object4 = (T)iterator3.next();
		if (object != null) {
			T object5 = object4;

			while (object5 != object) {
				if (iterator3.hasNext()) {
					object5 = (T)iterator3.next();
				}
			}

			if (iterator3.hasNext()) {
				return (T)iterator3.next();
			}
		}

		return object4;
	}

	public static <T> T b(Iterable<T> iterable, @Nullable T object) {
		Iterator<T> iterator3 = iterable.iterator();
		T object4 = null;

		while (iterator3.hasNext()) {
			T object5 = (T)iterator3.next();
			if (object5 == object) {
				if (object4 == null) {
					object4 = iterator3.hasNext() ? Iterators.getLast(iterator3) : object;
				}
				break;
			}

			object4 = object5;
		}

		return object4;
	}

	public static <T> T a(Supplier<T> supplier) {
		return (T)supplier.get();
	}

	public static <T> T a(T object, Consumer<T> consumer) {
		consumer.accept(object);
		return object;
	}

	public static <K> Strategy<K> k() {
		return v.a.INSTANCE;
	}

	public static <V> CompletableFuture<List<V>> b(List<? extends CompletableFuture<? extends V>> list) {
		List<V> list2 = Lists.<V>newArrayListWithCapacity(list.size());
		CompletableFuture<?>[] arr3 = new CompletableFuture[list.size()];
		CompletableFuture<Void> completableFuture4 = new CompletableFuture();
		list.forEach(completableFuture4x -> {
			int integer5 = list2.size();
			list2.add(null);
			arr3[integer5] = completableFuture4x.whenComplete((object, throwable) -> {
				if (throwable != null) {
					completableFuture4.completeExceptionally(throwable);
				} else {
					list2.set(integer5, object);
				}
			});
		});
		return CompletableFuture.allOf(arr3).applyToEither(completableFuture4, void2 -> list2);
	}

	public static <T> Stream<T> a(Optional<? extends T> optional) {
		return DataFixUtils.orElseGet(optional.map(Stream::of), Stream::empty);
	}

	public static <T> Optional<T> a(Optional<T> optional, Consumer<T> consumer, Runnable runnable) {
		if (optional.isPresent()) {
			consumer.accept(optional.get());
		} else {
			runnable.run();
		}

		return optional;
	}

	public static Runnable a(Runnable runnable, Supplier<String> supplier) {
		return runnable;
	}

	public static <T extends Throwable> T c(T throwable) {
		if (u.d) {
			g.error("Trying to throw a fatal exception, pausing in IDE", throwable);

			while (true) {
				try {
					Thread.sleep(1000L);
					g.error("paused");
				} catch (InterruptedException var2) {
					return throwable;
				}
			}
		} else {
			return throwable;
		}
	}

	public static String d(Throwable throwable) {
		if (throwable.getCause() != null) {
			return d(throwable.getCause());
		} else {
			return throwable.getMessage() != null ? throwable.getMessage() : throwable.toString();
		}
	}

	public static <T> T a(T[] arr, Random random) {
		return arr[random.nextInt(arr.length)];
	}

	public static int a(int[] arr, Random random) {
		return arr[random.nextInt(arr.length)];
	}

	public static void a(File file1, File file2, File file3) {
		if (file3.exists()) {
			file3.delete();
		}

		file1.renameTo(file3);
		if (file1.exists()) {
			file1.delete();
		}

		file2.renameTo(file1);
		if (file2.exists()) {
			file2.delete();
		}
	}

	public static Consumer<String> a(String string, Consumer<String> consumer) {
		return string3 -> consumer.accept(string + string3);
	}

	public static DataResult<int[]> a(IntStream intStream, int integer) {
		int[] arr3 = intStream.limit((long)(integer + 1)).toArray();
		if (arr3.length != integer) {
			String string4 = "Input is not a list of " + integer + " ints";
			return arr3.length >= integer ? DataResult.error(string4, Arrays.copyOf(arr3, integer)) : DataResult.error(string4);
		} else {
			return DataResult.success(arr3);
		}
	}

	public static void l() {
		Thread thread1 = new Thread("Timer hack thread") {
			public void run() {
				while (true) {
					try {
						Thread.sleep(2147483647L);
					} catch (InterruptedException var2) {
						v.g.warn("Timer hack thread interrupted, that really should not happen");
						return;
					}
				}
			}
		};
		thread1.setDaemon(true);
		thread1.setUncaughtExceptionHandler(new m(g));
		thread1.start();
	}

	static enum a implements Strategy<Object> {
		INSTANCE;

		@Override
		public int hashCode(Object object) {
			return System.identityHashCode(object);
		}

		@Override
		public boolean equals(Object object1, Object object2) {
			return object1 == object2;
		}
	}

	public static enum b {
		LINUX,
		SOLARIS,
		WINDOWS {
		},
		OSX {
		},
		UNKNOWN;

		private b() {
		}
	}
}
