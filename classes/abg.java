import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class abg<S> implements aaz {
	protected final abc a;
	protected final CompletableFuture<ael> b = new CompletableFuture();
	protected final CompletableFuture<List<S>> c;
	private final Set<aax> d;
	private final int e;
	private int f;
	private int g;
	private final AtomicInteger h = new AtomicInteger();
	private final AtomicInteger i = new AtomicInteger();

	public static abg<Void> a(abc abc, List<aax> list, Executor executor3, Executor executor4, CompletableFuture<ael> completableFuture) {
		return new abg<>(
			executor3, executor4, abc, list, (a, abcx, aax, executor5, executor6) -> aax.a(a, abcx, amf.a, amf.a, executor3, executor6), completableFuture
		);
	}

	protected abg(Executor executor1, Executor executor2, abc abc, List<aax> list, abg.a<S> a, CompletableFuture<ael> completableFuture) {
		this.a = abc;
		this.e = list.size();
		this.h.incrementAndGet();
		completableFuture.thenRun(this.i::incrementAndGet);
		List<CompletableFuture<S>> list8 = Lists.<CompletableFuture<S>>newArrayList();
		CompletableFuture<?> completableFuture9 = completableFuture;
		this.d = Sets.<aax>newHashSet(list);

		for (final aax aax11 : list) {
			final CompletableFuture<?> completableFuture12 = completableFuture9;
			CompletableFuture<S> completableFuture13 = a.create(new aax.a() {
				@Override
				public <T> CompletableFuture<T> a(T object) {
					executor2.execute(() -> {
						abg.this.d.remove(aax11);
						if (abg.this.d.isEmpty()) {
							abg.this.b.complete(ael.INSTANCE);
						}
					});
					return abg.this.b.thenCombine(completableFuture12, (ael, object3) -> object);
				}
			}, abc, aax11, runnable -> {
				this.h.incrementAndGet();
				executor1.execute(() -> {
					runnable.run();
					this.i.incrementAndGet();
				});
			}, runnable -> {
				this.f++;
				executor2.execute(() -> {
					runnable.run();
					this.g++;
				});
			});
			list8.add(completableFuture13);
			completableFuture9 = completableFuture13;
		}

		this.c = v.b(list8);
	}

	@Override
	public CompletableFuture<ael> a() {
		return this.c.thenApply(list -> ael.INSTANCE);
	}

	public interface a<S> {
		CompletableFuture<S> create(aax.a a, abc abc, aax aax, Executor executor4, Executor executor5);
	}
}
