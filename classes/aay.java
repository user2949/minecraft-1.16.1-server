import com.google.common.base.Stopwatch;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aay extends abg<aay.a> {
	private static final Logger d = LogManager.getLogger();
	private final Stopwatch e = Stopwatch.createUnstarted();

	public aay(abc abc, List<aax> list, Executor executor3, Executor executor4, CompletableFuture<ael> completableFuture) {
		super(executor3, executor4, abc, list, (a, abcx, aax, executor5, executor6) -> {
			AtomicLong atomicLong7 = new AtomicLong();
			AtomicLong atomicLong8 = new AtomicLong();
			amb amb9 = new amb(v.a, () -> 0, false);
			amb amb10 = new amb(v.a, () -> 0, false);
			CompletableFuture<Void> completableFuture11 = aax.a(a, abcx, amb9, amb10, runnable -> executor5.execute(() -> {
					long long3 = v.c();
					runnable.run();
					atomicLong7.addAndGet(v.c() - long3);
				}), runnable -> executor6.execute(() -> {
					long long3 = v.c();
					runnable.run();
					atomicLong8.addAndGet(v.c() - long3);
				}));
			return completableFuture11.thenApplyAsync(void6 -> new aay.a(aax.c(), amb9.d(), amb10.d(), atomicLong7, atomicLong8), executor4);
		}, completableFuture);
		this.e.start();
		this.c.thenAcceptAsync(this::a, executor4);
	}

	private void a(List<aay.a> list) {
		this.e.stop();
		int integer3 = 0;
		d.info("Resource reload finished after " + this.e.elapsed(TimeUnit.MILLISECONDS) + " ms");

		for (aay.a a5 : list) {
			amh amh6 = a5.b;
			amh amh7 = a5.c;
			int integer8 = (int)((double)a5.d.get() / 1000000.0);
			int integer9 = (int)((double)a5.e.get() / 1000000.0);
			int integer10 = integer8 + integer9;
			String string11 = a5.a;
			d.info(string11 + " took approximately " + integer10 + " ms (" + integer8 + " ms preparing, " + integer9 + " ms applying)");
			integer3 += integer9;
		}

		d.info("Total blocking time: " + integer3 + " ms");
	}

	public static class a {
		private final String a;
		private final amh b;
		private final amh c;
		private final AtomicLong d;
		private final AtomicLong e;

		private a(String string, amh amh2, amh amh3, AtomicLong atomicLong4, AtomicLong atomicLong5) {
			this.a = string;
			this.b = amh2;
			this.c = amh3;
			this.d = atomicLong4;
			this.e = atomicLong5;
		}
	}
}
