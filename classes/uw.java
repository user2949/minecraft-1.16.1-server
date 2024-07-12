import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class uw implements AutoCloseable {
	private static final CompletableFuture<ael> a = CompletableFuture.completedFuture(ael.INSTANCE);
	private final aba b = new abh(aaf.SERVER_DATA);
	private final da c;
	private final bmv d = new bmv();
	private final adh e = new adh();
	private final day f = new day();
	private final dax g = new dax(this.f);
	private final us h = new us(this.f);
	private final ut i;

	public uw(da.a a, int integer) {
		this.c = new da(a);
		this.i = new ut(integer, this.c.a());
		this.b.a(this.e);
		this.b.a(this.f);
		this.b.a(this.d);
		this.b.a(this.g);
		this.b.a(this.i);
		this.b.a(this.h);
	}

	public ut a() {
		return this.i;
	}

	public day b() {
		return this.f;
	}

	public dax c() {
		return this.g;
	}

	public adh d() {
		return this.e;
	}

	public bmv e() {
		return this.d;
	}

	public da f() {
		return this.c;
	}

	public us g() {
		return this.h;
	}

	public abc h() {
		return this.b;
	}

	public static CompletableFuture<uw> a(List<aae> list, da.a a, int integer, Executor executor4, Executor executor5) {
		uw uw6 = new uw(a, integer);
		CompletableFuture<ael> completableFuture7 = uw6.b.a(executor4, executor5, list, uw.a);
		return completableFuture7.whenComplete((ael, throwable) -> {
			if (throwable != null) {
				uw6.close();
			}
		}).thenApply(ael -> uw6);
	}

	public void i() {
		this.e.f();
	}

	public void close() {
		this.b.close();
	}
}
