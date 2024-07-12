import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public abstract class abf<T> implements aax {
	@Override
	public final CompletableFuture<Void> a(aax.a a, abc abc, ami ami3, ami ami4, Executor executor5, Executor executor6) {
		return CompletableFuture.supplyAsync(() -> this.b(abc, ami3), executor5).thenCompose(a::a).thenAcceptAsync(object -> this.a((T)object, abc, ami4), executor6);
	}

	protected abstract T b(abc abc, ami ami);

	protected abstract void a(T object, abc abc, ami ami);
}
