import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class amq<T> implements amp<T>, AutoCloseable, Runnable {
	private static final Logger b = LogManager.getLogger();
	private final AtomicInteger c = new AtomicInteger(0);
	public final ams<? super T, ? extends Runnable> a;
	private final Executor d;
	private final String e;

	public static amq<Runnable> a(Executor executor, String string) {
		return new amq<>(new ams.c<>(new ConcurrentLinkedQueue()), executor, string);
	}

	public amq(ams<? super T, ? extends Runnable> ams, Executor executor, String string) {
		this.d = executor;
		this.a = ams;
		this.e = string;
	}

	private boolean a() {
		int integer2;
		do {
			integer2 = this.c.get();
			if ((integer2 & 3) != 0) {
				return false;
			}
		} while (!this.c.compareAndSet(integer2, integer2 | 2));

		return true;
	}

	private void b() {
		int integer2;
		do {
			integer2 = this.c.get();
		} while (!this.c.compareAndSet(integer2, integer2 & -3));
	}

	private boolean c() {
		return (this.c.get() & 1) != 0 ? false : !this.a.b();
	}

	@Override
	public void close() {
		int integer2;
		do {
			integer2 = this.c.get();
		} while (!this.c.compareAndSet(integer2, integer2 | 1));
	}

	private boolean d() {
		return (this.c.get() & 2) != 0;
	}

	private boolean e() {
		if (!this.d()) {
			return false;
		} else {
			Runnable runnable2 = this.a.a();
			if (runnable2 == null) {
				return false;
			} else {
				String string3;
				Thread thread4;
				if (u.d) {
					thread4 = Thread.currentThread();
					string3 = thread4.getName();
					thread4.setName(this.e);
				} else {
					thread4 = null;
					string3 = null;
				}

				runnable2.run();
				if (thread4 != null) {
					thread4.setName(string3);
				}

				return true;
			}
		}
	}

	public void run() {
		try {
			this.a((Int2BooleanFunction)(integer -> integer == 0));
		} finally {
			this.b();
			this.f();
		}
	}

	@Override
	public void a(T object) {
		this.a.a(object);
		this.f();
	}

	private void f() {
		if (this.c() && this.a()) {
			try {
				this.d.execute(this);
			} catch (RejectedExecutionException var4) {
				try {
					this.d.execute(this);
				} catch (RejectedExecutionException var3) {
					b.error("Cound not schedule mailbox", (Throwable)var3);
				}
			}
		}
	}

	private int a(Int2BooleanFunction int2BooleanFunction) {
		int integer3 = 0;

		while (int2BooleanFunction.get(integer3) && this.e()) {
			integer3++;
		}

		return integer3;
	}

	public String toString() {
		return this.e + " " + this.c.get() + " " + this.a.b();
	}

	@Override
	public String bh() {
		return this.e;
	}
}
