import com.google.common.collect.Queues;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class amn<R extends Runnable> implements amp<R>, Executor {
	private final String b;
	private static final Logger c = LogManager.getLogger();
	private final Queue<R> d = Queues.<R>newConcurrentLinkedQueue();
	private int e;

	protected amn(String string) {
		this.b = string;
	}

	protected abstract R e(Runnable runnable);

	protected abstract boolean d(R runnable);

	public boolean bf() {
		return Thread.currentThread() == this.au();
	}

	protected abstract Thread au();

	protected boolean at() {
		return !this.bf();
	}

	public int bg() {
		return this.d.size();
	}

	@Override
	public String bh() {
		return this.b;
	}

	private CompletableFuture<Void> a(Runnable runnable) {
		return CompletableFuture.supplyAsync(() -> {
			runnable.run();
			return null;
		}, this);
	}

	public CompletableFuture<Void> f(Runnable runnable) {
		if (this.at()) {
			return this.a(runnable);
		} else {
			runnable.run();
			return CompletableFuture.completedFuture(null);
		}
	}

	public void g(Runnable runnable) {
		if (!this.bf()) {
			this.a(runnable).join();
		} else {
			runnable.run();
		}
	}

	public void a(R runnable) {
		this.d.add(runnable);
		LockSupport.unpark(this.au());
	}

	public void execute(Runnable runnable) {
		if (this.at()) {
			this.h(this.e(runnable));
		} else {
			runnable.run();
		}
	}

	protected void bj() {
		while (this.x()) {
		}
	}

	protected boolean x() {
		R runnable2 = (R)this.d.peek();
		if (runnable2 == null) {
			return false;
		} else if (this.e == 0 && !this.d(runnable2)) {
			return false;
		} else {
			this.c((R)this.d.remove());
			return true;
		}
	}

	public void c(BooleanSupplier booleanSupplier) {
		this.e++;

		try {
			while (!booleanSupplier.getAsBoolean()) {
				if (!this.x()) {
					this.bk();
				}
			}
		} finally {
			this.e--;
		}
	}

	protected void bk() {
		Thread.yield();
		LockSupport.parkNanos("waiting for tasks", 100000L);
	}

	protected void c(R runnable) {
		try {
			runnable.run();
		} catch (Exception var3) {
			c.fatal("Error executing task on {}", this.bh(), var3);
		}
	}
}
