import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface abd extends aax {
	@Override
	default CompletableFuture<Void> a(aax.a a, abc abc, ami ami3, ami ami4, Executor executor5, Executor executor6) {
		return a.a(ael.INSTANCE).thenRunAsync(() -> {
			ami4.a();
			ami4.a("listener");
			this.a(abc);
			ami4.c();
			ami4.b();
		}, executor6);
	}

	void a(abc abc);
}
