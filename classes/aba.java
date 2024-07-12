import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface aba extends abc, AutoCloseable {
	default CompletableFuture<ael> a(Executor executor1, Executor executor2, List<aae> list, CompletableFuture<ael> completableFuture) {
		return this.a(executor1, executor2, completableFuture, list).a();
	}

	aaz a(Executor executor1, Executor executor2, CompletableFuture<ael> completableFuture, List<aae> list);

	void a(aax aax);

	void close();
}
